package com.iotechn.unimall.app.api.user;

import com.alipay.easysdk.base.oauth.models.AlipaySystemOauthTokenResponse;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dobbinsoft.fw.core.Const;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.pay.enums.PayPlatformType;
import com.dobbinsoft.fw.support.component.CacheComponent;
import com.dobbinsoft.fw.support.properties.FwSystemProperties;
import com.dobbinsoft.fw.support.service.BaseService;
import com.dobbinsoft.fw.support.session.SessionStorage;
import com.dobbinsoft.fw.support.sms.SMSClient;
import com.dobbinsoft.fw.support.sms.SMSResult;
import com.dobbinsoft.fw.support.utils.DigestUtils;
import com.dobbinsoft.fw.support.utils.JacksonUtil;
import com.dobbinsoft.fw.support.utils.RandomStringUtils;
import com.dobbinsoft.fw.support.utils.StringUtils;
import com.iotechn.unimall.biz.service.user.UserBizService;
import com.iotechn.unimall.data.constant.CacheConst;
import com.iotechn.unimall.data.domain.UserDO;
import com.iotechn.unimall.data.dto.UserDTO;
import com.iotechn.unimall.data.dto.admin.AdminDTO;
import com.iotechn.unimall.data.dto.user.*;
import com.iotechn.unimall.data.enums.UserLoginType;
import com.iotechn.unimall.data.enums.UserStatusType;
import com.iotechn.unimall.data.exception.ExceptionDefinition;
import com.iotechn.unimall.data.mapper.UserMapper;
import com.iotechn.unimall.data.properties.UnimallAliAppProperties;
import com.iotechn.unimall.data.properties.UnimallWxAppProperties;
import com.iotechn.unimall.data.util.PKCS7Encoder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rize on 2019/6/30.
 */
@Service
public class UserServiceImpl extends BaseService<UserDTO, AdminDTO> implements UserService, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SMSClient smsClient;

    @Autowired
    private CacheComponent cacheComponent;

    @Autowired
    private UserBizService userBizService;

    @Autowired
    private SessionStorage sessionStorage;

    @Autowired
    private UnimallWxAppProperties unimallWxAppProperties;

    @Autowired
    private UnimallAliAppProperties unimallAliAppProperties;

    @Autowired
    private FwSystemProperties fwSystemProperties;

    private final OkHttpClient okHttpClient = new OkHttpClient();

    private Config aliMiniCode;

    @Override
    public void afterPropertiesSet() throws Exception {
        Config aliMiniConfig = new Config();
        aliMiniConfig.protocol = "https";
        aliMiniConfig.appId = unimallAliAppProperties.getMiniAppId();
        aliMiniConfig.gatewayHost = unimallAliAppProperties.getAliGateway();
        aliMiniConfig.signType = "RSA2";
        aliMiniConfig.alipayPublicKey = unimallAliAppProperties.getMiniAppPublicKey1();
        aliMiniConfig.merchantPrivateKey = unimallAliAppProperties.getMiniAppPrivateKey2();
//        config.notifyUrl = fwAliAppProperties.getAliNotifyUrl();
        this.aliMiniCode = aliMiniConfig;
        Factory.setOptions(aliMiniConfig);
    }

    @Override
    public String sendVerifyCode(String phone) throws ServiceException {
        String verifyCode = RandomStringUtils.randomNumeric(6);
        SMSResult res = smsClient.sendRegisterVerify(phone, verifyCode);
        if (res.isSuccess()) {
            cacheComponent.putRaw(CacheConst.USER_VERIFY_CODE_PREFIX + phone, verifyCode, 300);
            return "ok";
        } else {
            throw new ServiceException(res.getMessage(), ExceptionDefinition.USER_SEND_VERIFY_FAILED.getCode());
        }

    }

    @Override
    @Transactional
    public String register(String phone, String password, String verifyCode, String ip) throws ServiceException {
        //1.校验验证码
        checkVerifyCode(phone, verifyCode);
        //2.校验用户是否存在
        Long count = userMapper.selectCount(
                new QueryWrapper<UserDO>()
                        .eq("phone", phone));
        if (count > 0) {
            throw new ServiceException(ExceptionDefinition.USER_PHONE_HAS_EXISTED);
        }
        //3.校验成功，注册用户
        LocalDateTime now = LocalDateTime.now();
        UserDO userDO = new UserDO();
        userDO.setPhone(phone);
        String salt = RandomStringUtils.randomNumeric(6);
        userDO.setPassword(DigestUtils.md5Hex(password + salt));
        userDO.setLastLoginIp(ip);
        userDO.setGmtLastLogin(now);
        userDO.setGmtUpdate(now);
        userDO.setGmtCreate(now);
        userDO.setSalt(salt);
        userMapper.insert(userDO);
        //返回用户DTO
        cacheComponent.del(CacheConst.USER_VERIFY_CODE_PREFIX + phone);
        return "ok";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String bindPhone(String phone, String verifyCode, String accessToken, Long userId) throws ServiceException {
        //1.校验验证码
        checkVerifyCode(phone, verifyCode);
        cacheComponent.del(CacheConst.USER_VERIFY_CODE_PREFIX + phone);
        // 开始业务
        UserDO userDO = userMapper.selectById(userId);
        UserDO existUserDO = userMapper.selectOne(
                new QueryWrapper<UserDO>()
                        .eq("phone", phone));
        if (userDO.getStatus().intValue() == UserStatusType.WAIT_PHONE.getCode()) {
            //2. 绑定 或 合成逻辑
            if (tryMergeUser(phone, accessToken, userDO, existUserDO)) return "ok";
        } else if (userDO.getStatus().intValue() == UserStatusType.ACTIVE.getCode()) {
            //3. 更换绑定手机逻辑
            //3.1. 校验用户是否存在
            if (existUserDO != null) {
                throw new ServiceException(ExceptionDefinition.USER_PHONE_HAS_EXISTED);
            }
            //3.2. 校验成功，绑定手机
            UserDO updateUserDO = new UserDO();
            updateUserDO.setId(userId);
            updateUserDO.setPhone(phone);
            updateUserDO.setStatus(UserStatusType.ACTIVE.getCode());
            if (userMapper.updateById(updateUserDO) > 0) {
                UserDTO user = sessionUtil.getUser();
                user.setPhone(phone);
                user.setStatus(UserStatusType.ACTIVE.getCode());
                sessionStorage.refresh(Const.USER_REDIS_PREFIX, user);
                return "ok";
            }
        }
        throw new ServiceException(ExceptionDefinition.USER_UNKNOWN_EXCEPTION);
    }

    /**
     * 待绑定手机用户，绑定手机 | 合并用户处理
     * @param phone
     * @param accessToken
     * @param currentUserDO
     * @param existUserDO
     * @return
     */
    private boolean tryMergeUser(String phone, String accessToken, UserDO currentUserDO, UserDO existUserDO) {
        UserDO updateUserDO = new UserDO();
        if (existUserDO != null) {
            // 2.1. 合成逻辑
            if (!ObjectUtils.isEmpty(currentUserDO.getWxMpOpenId())) {
                updateUserDO.setWxMpOpenId(currentUserDO.getWxMpOpenId());
                existUserDO.setWxMpOpenId(currentUserDO.getWxMpOpenId());
            }
            if (!ObjectUtils.isEmpty(currentUserDO.getWxAppOpenId())) {
                updateUserDO.setWxAppOpenId(currentUserDO.getWxAppOpenId());
                existUserDO.setWxAppOpenId(currentUserDO.getWxAppOpenId());
            }
            if (!ObjectUtils.isEmpty(currentUserDO.getWxH5OpenId())) {
                updateUserDO.setWxH5OpenId(currentUserDO.getWxH5OpenId());
                existUserDO.setWxH5OpenId(currentUserDO.getWxH5OpenId());
            }
            if (!ObjectUtils.isEmpty(currentUserDO.getAliMpOpenId())) {
                updateUserDO.setAliMpOpenId(currentUserDO.getAliMpOpenId());
                existUserDO.setAliMpOpenId(currentUserDO.getAliMpOpenId());
            }
            // 2.2. 删除旧的user，并更新session
            userMapper.deleteById(currentUserDO.getId());
            userMapper.update(updateUserDO,
                    new QueryWrapper<UserDO>().eq("phone", phone));
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(existUserDO, userDTO);
            sessionStorage.refresh(Const.USER_REDIS_PREFIX, userDTO);
            return true;
        } else {
            // 2.2. 绑定逻辑
            updateUserDO.setId(currentUserDO.getId());
            updateUserDO.setPhone(phone);
            updateUserDO.setStatus(UserStatusType.ACTIVE.getCode());
            if (userMapper.updateById(updateUserDO) > 0) {
                UserDTO user = sessionUtil.getUser();
                user.setPhone(phone);
                sessionStorage.refresh(Const.USER_REDIS_PREFIX, user);
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String resetPassword(String phone, String password, String verifyCode) throws ServiceException {
        //1.校验验证码
        checkVerifyCode(phone, verifyCode);
        //2.校验用户是否存在
        List<UserDO> targetUserList = userMapper.selectList(
                new QueryWrapper<UserDO>()
                        .eq("phone", phone));
        if (CollectionUtils.isEmpty(targetUserList)) {
            throw new ServiceException(ExceptionDefinition.USER_PHONE_NOT_EXIST);
        }
        Long id = targetUserList.get(0).getId();
        //3.校验成功，重置密码
        UserDO updateUserDO = new UserDO();
        updateUserDO.setId(id);
        String salt = RandomStringUtils.randomNumeric(6);
        updateUserDO.setSalt(salt);
        updateUserDO.setPassword(DigestUtils.md5Hex(password + salt));
        updateUserDO.setGmtUpdate(LocalDateTime.now());
        if (userMapper.updateById(updateUserDO) > 0) {
            cacheComponent.del(CacheConst.USER_VERIFY_CODE_PREFIX + phone);
            return "ok";
        }
        throw new ServiceException(ExceptionDefinition.USER_UNKNOWN_EXCEPTION);
    }

    /**
     * 验证码抽取校验
     *
     * @param phone
     * @param verifyCode
     * @throws ServiceException
     */
    private void checkVerifyCode(String phone, String verifyCode) throws ServiceException {
        String raw = cacheComponent.getRaw(CacheConst.USER_VERIFY_CODE_PREFIX + phone);
        if (ObjectUtils.isEmpty(raw)) {
            throw new ServiceException(ExceptionDefinition.USER_VERIFY_CODE_NOT_EXIST);
        }
        if (!raw.equals(verifyCode)) {
            throw new ServiceException(ExceptionDefinition.USER_VERIFY_CODE_NOT_CORRECT);
        }
    }

    @Override
    @Transactional
    public UserDTO login(String phone, String password, Integer platform, String ip) throws ServiceException {
        UserDO userDO = userMapper.selectOne(new QueryWrapper<UserDO>().eq("phone", phone));
        if (userDO == null) {
            throw new ServiceException(ExceptionDefinition.USER_PHONE_OR_PASSWORD_NOT_CORRECT);
        }
        String cryptPassword = DigestUtils.md5Hex(password + userDO.getSalt());
        if (!cryptPassword.equals(userDO.getPassword())) {
            throw new ServiceException(ExceptionDefinition.USER_PHONE_OR_PASSWORD_NOT_CORRECT);
        }
        //检查帐号是否已经冻结
        if (userDO.getStatus() == 0) {
            throw new ServiceException(ExceptionDefinition.USER_CAN_NOT_ACTIVE);
        }
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userDO, userDTO);
        userDTO.setPlatform(platform);
        String accessToken = StringUtils.uuid();
        userDTO.setAccessToken(accessToken);
        UserDO userUpdateDO = new UserDO();
        userUpdateDO.setId(userDTO.getId());
        userUpdateDO.setGmtLastLogin(LocalDateTime.now());
        userUpdateDO.setLastLoginIp(ip);
        userMapper.updateById(userUpdateDO);
        //放入SESSION专用Redis数据源中
        sessionStorage.save(Const.USER_REDIS_PREFIX, accessToken, userDTO, fwSystemProperties.getUserSessionPeriod());
        return userDTO;
    }

    @Override
    public String logout(String accessToken, Long userId) throws ServiceException {
        sessionStorage.logout(Const.USER_REDIS_PREFIX, accessToken);
        return "ok";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDTO thirdPartLogin(Integer loginType, Integer platform, String ip, String raw) throws ServiceException {
        try {
            if (UserLoginType.MP_WEIXIN.getCode().intValue() == loginType) {
                return wechatSafeLogin(UserLoginType.MP_WEIXIN.getCode(), platform, ip, raw);
            } else if (UserLoginType.H5_WEIXIN.getCode().intValue() == loginType) {
                return wechatH5Login(ip, platform, raw);
            } else if (UserLoginType.APP_WEIXIN.getCode().intValue() == loginType) {
                return wechatAppLogin(ip, platform, raw);
            } else if (UserLoginType.MP_ALI.getCode().intValue() == loginType) {
                return alipayMpLogin(ip, platform, raw);
            } else {
                throw new ServiceException(ExceptionDefinition.USER_THIRD_PART_NOT_SUPPORT);
            }
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            logger.error("[用户第三方登录] 异常", e);
            throw new ServiceException(ExceptionDefinition.USER_THIRD_PART_LOGIN_FAILED);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String getWxPhone(WxLoginRequestDTO raw, String encryptedData, String iv, String accessToken, Long userId) throws ServiceException {
        try {
            String body = okHttpClient.newCall(new Request.Builder()
                    .url("https://api.weixin.qq.com/sns/jscode2session?appid=" + this.unimallWxAppProperties.getMiniAppId()
                            + "&secret=" + this.unimallWxAppProperties.getMiniAppSecret()
                            + "&grant_type=authorization_code&js_code=" + raw.getCode()).get().build()).execute().body().string();
            WxLoginResultDTO wxLoginResultDTO = JacksonUtil.parseObject(body, WxLoginResultDTO.class);
            Integer errcode = wxLoginResultDTO.getErrcode();
            if (errcode == null || errcode == 0) {
                String decrypt = PKCS7Encoder.decrypt(encryptedData, iv, wxLoginResultDTO.getSessionKey());
                WxPhoneNumberResultDTO wxPhoneNumberResultDTO = JacksonUtil.parseObject(decrypt, WxPhoneNumberResultDTO.class);
                String purePhoneNumber = wxPhoneNumberResultDTO.getPurePhoneNumber();
                if (purePhoneNumber == null || purePhoneNumber.length() != 11) {
                    logger.error("[decrypt原文] ");
                    throw new ServiceException(ExceptionDefinition.USER_THIRD_UNEXPECT_RESPONSE);
                }
                //绑定手机
                UserDO existUserDO = userMapper.selectOne(new QueryWrapper<UserDO>().eq("phone", purePhoneNumber));
                UserDO userDO = userMapper.selectById(userId);
                if (userDO.getStatus().intValue() == UserStatusType.WAIT_PHONE.getCode()) {
                    this.tryMergeUser(purePhoneNumber, accessToken, userDO, existUserDO);
                    // 更新用户Session信息
                    UserDTO user = sessionUtil.getUser();
                    user.setPhone(purePhoneNumber);
                    user.setStatus(UserStatusType.ACTIVE.getCode());
                    user.setPlatform(PayPlatformType.MP.getCode());
                    sessionStorage.refresh(Const.USER_REDIS_PREFIX, user);
                }
                return purePhoneNumber;
            }
            logger.info("[获取微信手机号] 回复失败 回复报文：" + body);
            throw new ServiceException(ExceptionDefinition.USER_THIRD_PART_NOT_SUPPORT);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            logger.error("[获取微信手机号]: 异常", e);
            throw new ServiceException(ExceptionDefinition.USER_UNKNOWN_EXCEPTION);
        }
    }

    @Override
    public String syncUserInfo(String nickname, String avatarUrl, String province, String city, String county, Integer gender, LocalDateTime birthday, String accessToken, Long userId) throws ServiceException {
        UserDO updateUserDO = new UserDO();
        updateUserDO.setId(userId);
        updateUserDO.setNickname(nickname);
        updateUserDO.setAvatarUrl(avatarUrl);
        updateUserDO.setProvince(province);
        updateUserDO.setCity(city);
        updateUserDO.setCounty(county);
        updateUserDO.setGender(gender);
        updateUserDO.setGmtUpdate(LocalDateTime.now());
        if (birthday != null)
            updateUserDO.setBirthday(birthday);
        if (userMapper.updateById(updateUserDO) > 0) {
            //更新SESSION缓存
            UserDTO user = sessionUtil.getUser();
            if (!ObjectUtils.isEmpty(nickname)) {
                user.setNickname(nickname);
            }
            if (!ObjectUtils.isEmpty(avatarUrl)) {
                user.setAvatarUrl(avatarUrl);
            }
            if (birthday != null) {
                user.setBirthday(birthday);
            }
            if (gender != null) {
                user.setGender(gender);
            }
            sessionStorage.refresh(Const.USER_REDIS_PREFIX, user);
            return "ok";
        }
        throw new ServiceException(ExceptionDefinition.USER_UNKNOWN_EXCEPTION);
    }

    @Override
    public Object getH5Sign(String url) throws ServiceException {
        try {
            String wxH5AccessToken = userBizService.getWxH5AccessToken();
            //我也不知道为什么微信这里要换两次
            String wxH5Ticket = userBizService.getWxH5Ticket(wxH5AccessToken);
            String noncestr = StringUtils.uuid();
            long timestamp = System.currentTimeMillis();
            StringBuilder sb = new StringBuilder();
            sb.append("jsapi_ticket=");
            sb.append(wxH5Ticket);
            sb.append("&noncestr=");
            sb.append(noncestr);
            sb.append("&timestamp=");
            sb.append(timestamp);
            sb.append("&url=");
            sb.append(url);
            //明文
            String content = sb.toString();
            String signature = DigestUtils.sha256Hex(content);
            Map<String, Object> obj = new HashMap<>();
            obj.put("noncestr", noncestr);
            obj.put("timestamp", timestamp);
            obj.put("sign", signature);
            return obj;
        } catch (Exception e) {
            logger.info("[获取H5签名] 异常", e);
            throw new ServiceException(ExceptionDefinition.APP_UNKNOWN_EXCEPTION);
        }
    }

    @Override
    public Boolean checkLogin(String accessToken) throws ServiceException {
        return sessionStorage.get(Const.USER_REDIS_PREFIX, accessToken, UserDTO.class) != null;
    }

    @Override
    public UserDTO info(String accessToken, Long userId) throws ServiceException {
        UserDTO user = sessionUtil.getUser();
        user.setAccessToken(accessToken);
        return user;
    }


    /*************************第三方登录 START******************************/

    /**
     * 微信小程序第三方登录，或微信APP安全登录
     *
     * @param loginType MP_WEIXIN(1, "WX小程序登录"), APP_WEIXIN(2, "WX第三方登录")
     * @param ip
     * @param raw
     * @return
     * @throws Exception
     */
    private UserDTO wechatSafeLogin(Integer loginType, Integer platform, String ip, String raw) throws Exception {
        //微信第三方登录
        WxLoginRequestDTO wxLoginRequestDTO = JacksonUtil.parseObject(raw, WxLoginRequestDTO.class);
        String body = okHttpClient.newCall(new Request.Builder()
                .url("https://api.weixin.qq.com/sns/jscode2session?appid=" + (UserLoginType.MP_WEIXIN.getCode().intValue() == loginType ? this.unimallWxAppProperties.getMiniAppId() : this.unimallWxAppProperties.getAppId()) +
                        "&secret=" + (UserLoginType.MP_WEIXIN.getCode().intValue() == loginType ? this.unimallWxAppProperties.getMiniAppSecret() : this.unimallWxAppProperties.getAppSecret()) +
                        "&grant_type=authorization_code&js_code=" + wxLoginRequestDTO.getCode()).get().build()).execute().body().string();
        WxLoginResultDTO wxLoginResultDTO = JacksonUtil.parseObject(body, WxLoginResultDTO.class);
        Integer errcode = wxLoginResultDTO.getErrcode();
        if (errcode == null || errcode == 0) {
            String openid = wxLoginResultDTO.getOpenid();
            UserDO userDO;
            if (UserLoginType.MP_WEIXIN.getCode().intValue() == loginType) {
                userDO = userMapper.selectOne(new QueryWrapper<UserDO>().eq("wx_mp_open_id", openid));
            } else {
                userDO = userMapper.selectOne(new QueryWrapper<UserDO>().eq("wx_app_open_id", openid));
            }
            LocalDateTime now = LocalDateTime.now();
            if (userDO == null) {
                //若用户为空，则注册此用户
                UserDO newUserDO = new UserDO();
                newUserDO.setWxMpOpenId(openid);
                newUserDO.setLastLoginIp(ip);
                newUserDO.setGmtLastLogin(now);
                newUserDO.setStatus(UserStatusType.WAIT_PHONE.getCode());
                newUserDO.setSalt(RandomStringUtils.randomNumeric(6));
                userMapper.insert(newUserDO);
                //这一步是为了封装上数据库上配置的默认值
                userDO = userMapper.selectById(newUserDO.getId());
            } else {
                UserDO userUpdateDO = new UserDO();
                userUpdateDO.setId(userDO.getId());
                userUpdateDO.setGmtLastLogin(now);
                userUpdateDO.setLastLoginIp(ip);
                userMapper.updateById(userUpdateDO);
            }
            //检查帐号是否已经冻结
            if (userDO.getStatus().intValue() == UserStatusType.LOCK.getCode()) {
                throw new ServiceException(ExceptionDefinition.USER_CAN_NOT_ACTIVE);
            }
            String accessToken = StringUtils.uuid();
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(userDO, userDTO);
            userDTO.setPlatform(platform);
            userDTO.setAccessToken(accessToken);
            sessionStorage.save(Const.USER_REDIS_PREFIX, accessToken, userDTO, fwSystemProperties.getUserSessionPeriod());
            return userDTO;
        } else {
            logger.info("[微信登录] 回复失败 回复报文：" + body);
            throw new ServiceException(ExceptionDefinition.USER_THIRD_UNEXPECT_RESPONSE);
        }

    }

    /**
     * 微信公众号浏览器H5登录
     *
     * @param ip
     * @param raw
     * @return
     * @throws Exception
     */
    private UserDTO wechatH5Login(String ip, Integer platform, String raw) throws Exception {
        //H5 微信公众号网页登录
        String json = okHttpClient.newCall(
                new Request.Builder().url("https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                        + this.unimallWxAppProperties.getH5AppId() + "&secret=" + this.unimallWxAppProperties.getH5AppSecret() + "&code=" + raw + "&grant_type=authorization_code").build()).execute().body().string();
        WxLoginResultDTO wxLoginResultDTO = JacksonUtil.parseObject(json, WxLoginResultDTO.class);
        Integer errcode = wxLoginResultDTO.getErrcode();
        if (errcode == null || errcode == 0) {
            String openid = wxLoginResultDTO.getOpenid();
            UserDO userDO = userMapper.selectOne(new QueryWrapper<UserDO>().eq("wx_h5_open_id", openid));
            if (userDO != null) {
                //若用户已经注册，则直接返回用户
                String accessToken = RandomStringUtils.randomNumeric(6);
                UserDTO userDTO = new UserDTO();
                BeanUtils.copyProperties(userDO, userDTO);
                userDTO.setPlatform(platform);
                userDTO.setAccessToken(accessToken);
                sessionStorage.save(Const.USER_REDIS_PREFIX, accessToken, userDTO, fwSystemProperties.getUserSessionPeriod());
                return userDTO;
            } else {
                String userAccessToken = wxLoginResultDTO.getAccessToken();
                //通过用户AccessToken换取用户信息
                String userInfoJson = okHttpClient.newCall(
                        new Request.Builder().url("https://api.weixin.qq.com/sns/userinfo?access_token="
                                + userAccessToken + "&openid=" + openid + "&lang=zh_CN").build()).execute().body().string();
                WxH5UserInfoResultDTO wxH5UserInfoResultDTO = JacksonUtil.parseObject(userInfoJson, WxH5UserInfoResultDTO.class);
                LocalDateTime now = LocalDateTime.now();
                UserDO newUserDO = new UserDO();
                newUserDO.setNickname(wxH5UserInfoResultDTO.getNickname());
                newUserDO.setAvatarUrl(wxH5UserInfoResultDTO.getHeadimgurl());
                newUserDO.setGender(wxH5UserInfoResultDTO.getSex());
                newUserDO.setWxH5OpenId(openid);
                newUserDO.setLastLoginIp(ip);
                newUserDO.setStatus(UserStatusType.WAIT_PHONE.getCode());
                newUserDO.setGmtLastLogin(now);
                newUserDO.setSalt(RandomStringUtils.randomNumeric(6));
                userMapper.insert(newUserDO);
                //这一步是为了封装上数据库上配置的默认值
                userDO = userMapper.selectById(newUserDO.getId());
                String accessToken = StringUtils.uuid();
                UserDTO userDTO = new UserDTO();
                BeanUtils.copyProperties(userDO, userDTO);
                userDTO.setPlatform(platform);
                userDTO.setAccessToken(accessToken);
                sessionStorage.save(Const.USER_REDIS_PREFIX, accessToken, userDTO, fwSystemProperties.getUserSessionPeriod());
                return userDTO;
            }
        } else {
            throw new ServiceException(ExceptionDefinition.USER_THIRD_PART_LOGIN_FAILED);
        }
    }

    /**
     * 微信APP登录
     *
     * @param ip
     * @param raw
     * @return
     * @throws Exception
     */
    private UserDTO wechatAppLogin(String ip, Integer platform, String raw) throws Exception {
        //UNI-APP 的 微信APP登录 APPSecret是保存在前端的。这点非常不安全。但是用了他的框架，也没有办法
        WxAppLoginResultDTO jsonObject = JacksonUtil.parseObject(raw, WxAppLoginResultDTO.class);
        WxLoginResultDTO authResult = jsonObject.getAuthResult();
        String openid = authResult.getOpenid();
        UserDO userDO = userMapper.selectOne(new QueryWrapper<UserDO>().eq("wx_app_open_id", openid));
        if (userDO == null) {
            //创建新用户
            LocalDateTime now = LocalDateTime.now();
            UserDO newUserDO = new UserDO();
            newUserDO.setWxAppOpenId(openid);
            newUserDO.setLastLoginIp(ip);
            newUserDO.setGmtLastLogin(now);
            newUserDO.setStatus(UserStatusType.WAIT_PHONE.getCode());
            newUserDO.setSalt(RandomStringUtils.randomNumeric(6));
            userMapper.insert(newUserDO);
            //这一步是为了封装上数据库上配置的默认值
            userDO = userMapper.selectById(newUserDO.getId());
        } else {
            UserDO userUpdateDO = new UserDO();
            userUpdateDO.setId(userDO.getId());
            userUpdateDO.setGmtLastLogin(LocalDateTime.now());
            userUpdateDO.setLastLoginIp(ip);
            userMapper.updateById(userUpdateDO);
        }
        //检查帐号是否已经冻结
        if (userDO.getStatus().intValue() == UserStatusType.LOCK.getCode()) {
            throw new ServiceException(ExceptionDefinition.USER_CAN_NOT_ACTIVE);
        }
        String accessToken = StringUtils.uuid();
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userDO, userDTO);
        userDTO.setPlatform(platform);
        userDTO.setAccessToken(accessToken);
        sessionStorage.save(Const.USER_REDIS_PREFIX, accessToken, userDTO, fwSystemProperties.getUserSessionPeriod());
        return userDTO;
    }

    /**
     * 支付宝小程序第三方登录
     * @param ip
     * @param raw
     * @return
     */
    private UserDTO alipayMpLogin(String ip, Integer platform, String raw) throws Exception {
        Factory.setOptions(this.aliMiniCode);
        AliMpLoginResultDTO aliMpLoginResultDTO = JacksonUtil.parseObject(raw, AliMpLoginResultDTO.class);
        AlipaySystemOauthTokenResponse response = Factory.Base.OAuth().getToken(aliMpLoginResultDTO.getCode());
        String userId = response.getUserId();
        UserDO userDO = userMapper.selectOne(new QueryWrapper<UserDO>().eq("ali_mp_open_id", userId));
        if (userDO == null) {
            //创建新用户
            UserDO newUserDO = new UserDO();
            newUserDO.setAliMpOpenId(userId);
            newUserDO.setLastLoginIp(ip);
            newUserDO.setGmtLastLogin(LocalDateTime.now());
            newUserDO.setStatus(UserStatusType.WAIT_PHONE.getCode());
            newUserDO.setSalt(RandomStringUtils.randomNumeric(6));
            userMapper.insert(newUserDO);
            userDO = userMapper.selectById(newUserDO.getId());
        } else {
            UserDO userUpdateDO = new UserDO();
            userUpdateDO.setId(userDO.getId());
            userUpdateDO.setGmtLastLogin(LocalDateTime.now());
            userUpdateDO.setLastLoginIp(ip);
        }
        //检查帐号是否已经冻结
        if (userDO.getStatus().intValue() == UserStatusType.LOCK.getCode()) {
            throw new ServiceException(ExceptionDefinition.USER_CAN_NOT_ACTIVE);
        }
        String accessToken = StringUtils.uuid();
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userDO, userDTO);
        userDTO.setPlatform(platform);
        userDTO.setAccessToken(accessToken);
        sessionStorage.save(Const.USER_REDIS_PREFIX, accessToken, userDTO, fwSystemProperties.getUserSessionPeriod());
        return userDTO;
    }

    /*************************第三方登录 END******************************/

}
