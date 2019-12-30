package com.iotechn.unimall.admin.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.iotechn.unimall.core.Const;
import com.iotechn.unimall.core.exception.AdminServiceException;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.core.exception.ThirdPartServiceException;
import com.iotechn.unimall.core.notify.SMSClient;
import com.iotechn.unimall.core.notify.SMSResult;
import com.iotechn.unimall.core.util.GeneratorUtil;
import com.iotechn.unimall.core.util.MD5Util;
import com.iotechn.unimall.core.util.SHAUtil;
import com.iotechn.unimall.data.component.CacheComponent;
import com.iotechn.unimall.data.domain.AdminDO;
import com.iotechn.unimall.data.domain.RoleDO;
import com.iotechn.unimall.data.domain.RolePermissionDO;
import com.iotechn.unimall.data.dto.AdminDTO;
import com.iotechn.unimall.data.enums.AdminStatusType;
import com.iotechn.unimall.data.enums.RoleStatusType;
import com.iotechn.unimall.data.mapper.AdminMapper;
import com.iotechn.unimall.data.mapper.RoleMapper;
import com.iotechn.unimall.data.mapper.RolePermissionMapper;
import com.iotechn.unimall.data.model.Page;
import com.iotechn.unimall.data.util.SessionUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by rize on 2019/4/8.
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private StringRedisTemplate userRedisTemplate;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private CacheComponent cacheComponent;

    @Autowired
    private SMSClient smsClient;

    @Value("${com.iotechn.admin.notify.uninotify.url}")
    private String uniNotifyUrl;

    @Value("${com.iotechn.admin.notify.uninotify.app-secret}")
    private String uniNotifyAppSecret;

    @Value("${com.iotechn.admin.notify.uninotify.app-id}")
    private String uniNotifyAppId;

    private final static String ADMIN_MSG_CODE = "admin_msg_code_";

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Override
    public String login(String username, String password,String verifyCode) throws ServiceException {
        String accessToken = generateAccessToken();
        //数据库查管理员
        List<AdminDO> adminDOS = adminMapper.selectList(
                new EntityWrapper<AdminDO>()
                        .eq("username", username));
        if (CollectionUtils.isEmpty(adminDOS)) {
            throw new AdminServiceException(ExceptionDefinition.ADMIN_NOT_EXIST);
        }
        AdminDO adminDO = adminDOS.get(0);
        //短信验证码
        String code = cacheComponent.getRaw(ADMIN_MSG_CODE+adminDO.getPhone() );
        if(!"guest".equals(username) && (code == null || verifyCode==null || !code.equals(verifyCode))){
            throw new AdminServiceException(ExceptionDefinition.ADMIN_VERIFYCODE_ERROR);
        }

        if (!MD5Util.verify(password, username, adminDO.getPassword())) {
            throw new AdminServiceException(ExceptionDefinition.ADMIN_PASSWORD_ERROR);
        }
        List<Long> ids = JSONObject.parseArray(adminDO.getRoleIds(), Long.class);
        if (CollectionUtils.isEmpty(ids)) {
            throw new AdminServiceException(ExceptionDefinition.ADMIN_ROLE_IS_EMPTY);
        }
        List<RoleDO> roleDOList = roleMapper.selectList(
                new EntityWrapper<RoleDO>()
                        .in("id", ids)
                        .eq("status", RoleStatusType.ACTIVE.getCode()));
        List<String> roleNames = new LinkedList<>();
        roleDOList.forEach(item -> {
            roleNames.add(item.getName());
        });
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setRoles(roleNames);
        BeanUtils.copyProperties(adminDO, adminDTO);
        adminDTO.setRoleIds(JSONObject.parseArray(adminDO.getRoleIds(), Long.class));
        adminDTO.setPassword(null);
        List<RolePermissionDO> rolePermissionDOList = rolePermissionMapper.selectList(
                new EntityWrapper<RolePermissionDO>()
                        .in("role_id", ids)
                        .eq("deleted", 0));
        List<String> permissionNames = new LinkedList<>();
        rolePermissionDOList.forEach(item -> {
            permissionNames.add(item.getPermission());
        });
        adminDTO.setPerms(permissionNames);
        userRedisTemplate.opsForValue().set(Const.ADMIN_REDIS_PREFIX + accessToken, JSONObject.toJSONString(adminDTO), 30, TimeUnit.MINUTES);
        return accessToken;
    }

    @Override
    public String logout(String accessToken, Long adminId) throws ServiceException {
        userRedisTemplate.delete(Const.ADMIN_REDIS_PREFIX  + accessToken);
        return "ok";
    }

    @Override
    public AdminDTO info(Long adminId) throws ServiceException {
        return SessionUtil.getAdmin();
    }

    @Override
    public Page<AdminDTO> list(String name, Integer page, Integer limit, Long adminId) throws ServiceException {
        Wrapper<AdminDO> wrapper = new EntityWrapper<AdminDO>();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("username", name);
        }
        wrapper.orderBy("id", false);
        Integer count = adminMapper.selectCount(wrapper);
        List<AdminDO> adminDOS = adminMapper.selectPage(new RowBounds((page - 1) * limit, limit), wrapper);
        List<AdminDTO> adminDTOS = new ArrayList<AdminDTO>(adminDOS.size());
        for (AdminDO adminDO : adminDOS) {
            AdminDTO adminDTO = new AdminDTO();
            BeanUtils.copyProperties(adminDO, adminDTO);
            adminDTO.setRoleIds(JSONObject.parseArray(adminDO.getRoleIds(), Long.class));
            adminDTO.setPassword(null);
            adminDTOS.add(adminDTO);
        }
        return new Page<>(adminDTOS, page, limit, count);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AdminDTO create(AdminDTO adminDTO, Long adminId) throws ServiceException {
        AdminDO adminDO = new AdminDO();
        Integer count = adminMapper.selectCount(
                new EntityWrapper<AdminDO>()
                        .eq("username", adminDTO.getUsername()));
        if (count > 0) {
            throw new AdminServiceException(ExceptionDefinition.ADMIN_USER_NAME_REPEAT);
        }
        BeanUtils.copyProperties(adminDTO, adminDO);
        adminDO.setPassword(MD5Util.md5(adminDO.getPassword(), adminDO.getUsername()));
        adminDO.setRoleIds(JSONObject.toJSONString(adminDTO.getRoleIds()));
        adminDO.setGmtUpdate(new Date());
        adminDO.setGmtCreate(adminDO.getGmtUpdate());
        adminDO.setStatus(AdminStatusType.ACTIVE.getCode());
        adminDO.setLastLoginIp("0.0.0.0");
        adminDO.setGmtLastLogin("1997-01-20 00:00:00");
        if (adminMapper.insert(adminDO) > 0) {
            adminDTO.setId(adminDO.getId());
            return adminDTO;
        }
        throw new AdminServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String update(AdminDTO adminDTO, Long adminId) throws ServiceException {
        Long id = adminDTO.getId();
        if (id == null) {
            throw new AdminServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
        }
        AdminDO adminDO = new AdminDO();
        BeanUtils.copyProperties(adminDTO, adminDO);
        adminDO.setGmtUpdate(new Date());
        AdminDO adminDOExist = adminMapper.selectById(id);
        if (!StringUtils.isEmpty(adminDO.getPassword()) && !StringUtils.isEmpty(adminDOExist.getUsername())) {
            adminDO.setPassword(MD5Util.md5(adminDO.getPassword(), adminDOExist.getUsername()));
        }
        adminDO.setUsername(null);
        if (!CollectionUtils.isEmpty(adminDTO.getRoleIds())) {
            adminDO.setRoleIds(JSONObject.toJSONString(adminDTO.getRoleIds()));
        }
        if (adminMapper.updateById(adminDO) > 0) {
            return "ok";
        }
        throw new AdminServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String delete(Long id, Long adminId) throws ServiceException {
        if (adminMapper.deleteById(id) > 0) {
            return "ok";
        }
        throw new AdminServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String newPassword(String accessToken, String oldPassword, String newPassword, Long adminId) throws ServiceException {
        AdminDO adminDOExist = adminMapper.selectById(adminId);
        if (!MD5Util.md5(oldPassword, adminDOExist.getUsername()).equals(adminDOExist.getPassword())) {
            throw new AdminServiceException(ExceptionDefinition.ADMIN_PASSWORD_ERROR);
        }
        AdminDO adminDO = new AdminDO();
        adminDO.setId(adminId);
        adminDO.setPassword(MD5Util.md5(newPassword, adminDOExist.getUsername()));
        if (adminMapper.updateById(adminDO) > 0) {
            //logout(accessToken, adminId);
            return "ok";
        }
        throw new AdminServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
    }

    @Override
    public Boolean sendLoginMsg(String username,String password) throws ServiceException {
        if ("guest".equals(username)) {
            throw new AdminServiceException(ExceptionDefinition.ADMIN_GUEST_NOT_NEED_VERIFY_CODE);
        }
        AdminDO adminDO = new AdminDO();
        adminDO.setUsername(username);
        adminDO.setPassword(MD5Util.md5(password,username));
        AdminDO admin = adminMapper.selectOne(adminDO);
        if(admin == null){
            throw new AdminServiceException(ExceptionDefinition.ADMIN_USER_NOT_EXITS);
        }
        String code = GeneratorUtil.genSixVerifyCode();
        cacheComponent.putRaw(ADMIN_MSG_CODE+admin.getPhone(), code,300 );
        SMSResult smsResult = smsClient.sendAdminLoginVerify(admin.getPhone(), code);
        if(!smsResult.isSucc()){
            throw new ThirdPartServiceException(smsResult.getMsg(), ExceptionDefinition.ADMIN_VERIFY_CODE_SEND_FAIL.getCode());
        }
        return true;
    }

    @Override
    public String bindUniNotify(Long adminId) throws ServiceException {
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            TreeSet<String> set = new TreeSet<>();
            set.add("getRegisterUrl");
            long timestamp = System.currentTimeMillis();
            set.add(timestamp + "");
            set.add("developer");
            set.add(SessionUtil.getAdmin().getUsername());
            set.add(this.uniNotifyAppSecret);
            set.add(this.uniNotifyAppId);
            String json = okHttpClient
                    .newCall(new Request.Builder()
                            .get()
                            .url(this.uniNotifyUrl + "?_gp=developer&_mt=getRegisterUrl&userId=" + SessionUtil.getAdmin().getUsername()
                                    + "&appId=" + this.uniNotifyAppId + "&timestamp=" + timestamp + "&sign=" + SHAUtil.shaEncode(URLEncoder.encode(set.stream().collect(Collectors.joining()), "utf-8")))
                            .build()).execute().body().string();
            JSONObject jsonObject = JSONObject.parseObject(json);
            Integer errcode = jsonObject.getInteger("errno");
            if (errcode == 200) {
                return jsonObject.getString("data");
            }
            throw new AdminServiceException(jsonObject.getString("errmsg"), ExceptionDefinition.THIRD_PART_SERVICE_EXCEPTION.getCode());
        } catch (ServiceException e) {
            throw e;
        } catch (IOException e) {
            throw new AdminServiceException(ExceptionDefinition.THIRD_PART_IO_EXCEPTION);
        } catch (Exception e) {
            logger.error("[绑定通知] 异常", e);
            throw new AdminServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
        }
    }

    private String generateAccessToken() {
        return (UUID.randomUUID().toString().replace("-", ""));
    }


}
