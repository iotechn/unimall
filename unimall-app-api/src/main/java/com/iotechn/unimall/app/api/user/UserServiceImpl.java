package com.iotechn.unimall.app.api.user;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.iotechn.unimall.app.exception.AppExceptionDefinition;
import com.iotechn.unimall.app.exception.AppServiceException;
import com.iotechn.unimall.core.Const;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.core.notify.SMSClient;
import com.iotechn.unimall.core.notify.SMSResult;
import com.iotechn.unimall.core.util.GeneratorUtil;
import com.iotechn.unimall.data.component.CacheComponent;
import com.iotechn.unimall.data.domain.UserDO;
import com.iotechn.unimall.data.dto.UserDTO;
import com.iotechn.unimall.data.mapper.UserMapper;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import sun.security.provider.MD5;

import java.util.Date;
import java.util.List;

/**
 * Created by rize on 2019/6/30.
 */
@Service
public class UserServiceImpl implements UserService {

    private static final String VERIFY_CODE_PREFIX = "VERIFY_CODE_";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SMSClient smsClient;

    @Autowired
    private CacheComponent cacheComponent;

    @Autowired
    private StringRedisTemplate userRedisTemplate;

    @Override
    public String sendVerifyCode(String phone) throws ServiceException {
        String verifyCode = GeneratorUtil.genSixVerifyCode();
        SMSResult res = smsClient.sendRegisterVerify(phone, verifyCode);
        if (res.isSucc()) {
            cacheComponent.putRaw(VERIFY_CODE_PREFIX + phone, verifyCode, 300);
            return "ok";
        } else {
            throw new AppServiceException(res.getMsg(), AppExceptionDefinition.USER_SEND_VERIFY_FAILED.getCode());
        }

    }

    @Override
    @Transactional
    public String register(String phone, String password, String verifyCode, String ip) throws ServiceException {
        //1.校验验证码
        checkVerifyCode(phone, verifyCode);
        //2.校验用户是否存在
        Integer count = userMapper.selectCount(
                new EntityWrapper<UserDO>()
                        .eq("phone", phone));
        if (count > 0) {
            throw new AppServiceException(AppExceptionDefinition.USER_PHONE_HAS_EXISTED);
        }
        //3.校验成功，注册用户
        Date now = new Date();
        UserDO userDO = new UserDO();
        userDO.setPhone(phone);
        userDO.setPassword(Md5Crypt.md5Crypt(password.getBytes(), "$1$" + phone.substring(0,7)));
        userDO.setLastLoginIp(ip);
        userDO.setGmtLastLogin(now);
        userDO.setGmtUpdate(now);
        userDO.setGmtCreate(now);
        userMapper.insert(userDO);
        //返回用户DTO
        cacheComponent.del(VERIFY_CODE_PREFIX + phone);
        return null;
    }

    @Override
    @Transactional
    public String bindPhone(String phone, String password, String verifyCode, Long userId) throws ServiceException {
        //1.校验验证码
        checkVerifyCode(phone, verifyCode);
        //2.校验用户是否存在
        Integer count = userMapper.selectCount(
                new EntityWrapper<UserDO>()
                        .eq("phone", phone));
        if (count > 0) {
            throw new AppServiceException(AppExceptionDefinition.USER_PHONE_HAS_EXISTED);
        }
        //3.校验成功，绑定手机
        UserDO updateUserDO = new UserDO();
        updateUserDO.setId(userId);
        updateUserDO.setPhone(phone);
        updateUserDO.setGmtUpdate(new Date());
        if (userMapper.updateById(updateUserDO) > 0) {
            cacheComponent.del(VERIFY_CODE_PREFIX + phone);
            return "ok";
        }
        throw new AppServiceException(AppExceptionDefinition.USER_UNKNOWN_EXCEPTION);
    }

    @Override
    @Transactional
    public String resetPassword(String phone, String password, String verifyCode) throws ServiceException {
        //1.校验验证码
        checkVerifyCode(phone, verifyCode);
        //2.校验用户是否存在
        List<UserDO> targetUserList = userMapper.selectList(
                new EntityWrapper<UserDO>()
                        .eq("phone", phone));
        if (CollectionUtils.isEmpty(targetUserList)) {
            throw new AppServiceException(AppExceptionDefinition.USER_PHONE_NOT_EXIST);
        }
        Long id = targetUserList.get(0).getId();
        //3.校验成功，重置密码
        UserDO updateUserDO = new UserDO();
        updateUserDO.setId(id);
        updateUserDO.setPassword(Md5Crypt.md5Crypt(password.getBytes(),"$1$" + phone.substring(0,7)));
        updateUserDO.setGmtUpdate(new Date());
        if (userMapper.updateById(updateUserDO) > 0) {
            cacheComponent.del(VERIFY_CODE_PREFIX + phone);
            return "ok";
        }
        throw new AppServiceException(AppExceptionDefinition.USER_UNKNOWN_EXCEPTION);
    }

    /**
     * 验证码抽取校验
     * @param phone
     * @param verifyCode
     * @throws ServiceException
     */
    private void checkVerifyCode(String phone, String verifyCode) throws ServiceException {
        String raw = cacheComponent.getRaw(VERIFY_CODE_PREFIX + phone);
        if (StringUtils.isEmpty(raw)) {
            throw new AppServiceException(AppExceptionDefinition.USER_VERIFY_CODE_NOT_EXIST);
        }
        if (!raw.equals(verifyCode)) {
            throw new AppServiceException(AppExceptionDefinition.USER_VERIFY_CODE_NOT_CORRECT);
        }
    }

    @Override
    public UserDTO login(String phone, String password) throws ServiceException {
        String cryptPassword = Md5Crypt.md5Crypt(password.getBytes(), "$1$" + phone.substring(0,7));
        UserDTO userDTO = userMapper.login(phone, cryptPassword);
        if (userDTO == null) {
            throw new AppServiceException(AppExceptionDefinition.USER_PHONE_OR_PASSWORD_NOT_CORRECT);
        }
        String accessToken = GeneratorUtil.genSessionId();
        //放入SESSION专用Redis数据源中
        userRedisTemplate.opsForValue().set(Const.USER_REDIS_PREFIX + accessToken, JSONObject.toJSONString(userDTO));
        userDTO.setAccessToken(accessToken);
        return userDTO;
    }

    @Override
    public String logout(String accessToken, Long userId) throws ServiceException {
        userRedisTemplate.delete(accessToken);
        return "ok";
    }

    @Override
    public String wechatLogin(String code) throws ServiceException {
        return null;
    }


}
