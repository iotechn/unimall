package com.dobbinsoft.unimall.admin.api.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dobbinsoft.fw.core.Const;
import com.dobbinsoft.fw.core.exception.CoreExceptionDefinition;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.core.model.GatewayResponse;
import com.dobbinsoft.fw.support.component.CacheComponent;
import com.dobbinsoft.fw.support.model.Page;
import com.dobbinsoft.fw.support.properties.FwSystemProperties;
import com.dobbinsoft.fw.support.service.BaseService;
import com.dobbinsoft.fw.support.session.SessionStorage;
import com.dobbinsoft.fw.support.sms.SMSClient;
import com.dobbinsoft.fw.support.sms.SMSResult;
import com.dobbinsoft.fw.support.utils.DigestUtils;
import com.dobbinsoft.fw.support.utils.JacksonUtil;
import com.dobbinsoft.fw.support.utils.RandomStringUtils;
import com.dobbinsoft.fw.support.utils.StringUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.dobbinsoft.unimall.data.constant.CacheConst;
import com.dobbinsoft.unimall.data.domain.AdminDO;
import com.dobbinsoft.unimall.data.domain.RoleDO;
import com.dobbinsoft.unimall.data.domain.RolePermissionDO;
import com.dobbinsoft.unimall.data.dto.UserDTO;
import com.dobbinsoft.unimall.data.dto.admin.AdminDTO;
import com.dobbinsoft.unimall.data.enums.AdminStatusType;
import com.dobbinsoft.unimall.data.enums.RoleStatusType;
import com.dobbinsoft.unimall.data.exception.ExceptionDefinition;
import com.dobbinsoft.unimall.data.mapper.AdminMapper;
import com.dobbinsoft.unimall.data.mapper.RoleMapper;
import com.dobbinsoft.unimall.data.mapper.RolePermissionMapper;
import com.dobbinsoft.unimall.data.properties.UnimallAdminNotifyProperties;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by rize on 2019/4/8.
 */
@Service
public class AdminServiceImpl extends BaseService<UserDTO, AdminDTO> implements AdminService {

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

    @Autowired
    private SessionStorage sessionStorage;

    @Autowired
    private UnimallAdminNotifyProperties unimallAdminNotifyProperties;

    @Autowired
    private FwSystemProperties fwSystemProperties;

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Override
    public String login(String username, String password, String verifyCode) throws ServiceException {
        String accessToken = generateAccessToken();
        //数据库查管理员
        AdminDO adminDO = adminMapper.selectOne(
                new QueryWrapper<AdminDO>()
                        .eq("username", username));
        if (adminDO == null) {
            throw new ServiceException(ExceptionDefinition.ADMIN_NOT_EXIST);
        }
        //短信验证码
        String code = cacheComponent.getRaw(CacheConst.ADMIN_MSG_CODE + adminDO.getPhone());
        boolean isGuest = "guest".equals(username);
        if (!isGuest && (code == null || !code.equals(verifyCode))) {
            throw new ServiceException(ExceptionDefinition.ADMIN_VERIFYCODE_ERROR);
        }
        if (!DigestUtils.md5Hex(password + adminDO.getSalt()).equalsIgnoreCase(adminDO.getPassword())) {
            throw new ServiceException(ExceptionDefinition.ADMIN_PASSWORD_ERROR);
        }
        List<Long> ids = JacksonUtil.parseArray(adminDO.getRoleIds(), Long.class);
        if (CollectionUtils.isEmpty(ids)) {
            throw new ServiceException(ExceptionDefinition.ADMIN_ROLE_IS_EMPTY);
        }
        List<RoleDO> roleDOList = roleMapper.selectList(
                new QueryWrapper<RoleDO>()
                        .in("id", ids)
                        .eq("status", RoleStatusType.ACTIVE.getCode()));
        List<String> roleNames = new LinkedList<>();
        roleDOList.forEach(item -> {
            roleNames.add(item.getName());
        });
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setRoles(roleNames);
        BeanUtils.copyProperties(adminDO, adminDTO);
        adminDTO.setRoleIds(JacksonUtil.parseArray(adminDO.getRoleIds(), Long.class));
        adminDTO.setPassword(null);
        List<RolePermissionDO> rolePermissionDOList = rolePermissionMapper.selectList(
                new QueryWrapper<RolePermissionDO>()
                        .in("role_id", ids));
        List<String> permissionNames = new LinkedList<>();
        rolePermissionDOList.forEach(item -> {
            permissionNames.add(item.getPermission());
        });
        adminDTO.setPerms(permissionNames);
        sessionStorage.save(Const.ADMIN_REDIS_PREFIX, accessToken, adminDTO, fwSystemProperties.getAdminSessionPeriod());
        return accessToken;
    }

    @Override
    public String logout(String accessToken, Long adminId) throws ServiceException {
        sessionStorage.logout(Const.ADMIN_REDIS_PREFIX, accessToken);
        return "ok";
    }

    @Override
    public AdminDTO info(Long adminId) throws ServiceException {
        return sessionUtil.getAdmin();
    }

    @Override
    public Page<AdminDTO> list(String username, Integer page, Integer limit, Long adminId) throws ServiceException {
        QueryWrapper<AdminDO> wrapper = new QueryWrapper<AdminDO>();
        if (!StringUtils.isEmpty(username)) {
            wrapper.like("username", username);
        }
        wrapper.orderByDesc("id");
        Page<AdminDO> selectPage = adminMapper.selectPage(Page.div(page, limit, AdminDO.class), wrapper);
        return selectPage.trans(item -> {
            AdminDTO adminDTO = new AdminDTO();
            BeanUtils.copyProperties(item, adminDTO);
            adminDTO.setRoleIds(JacksonUtil.parseArray(item.getRoleIds(), Long.class));
            adminDTO.setPassword(null);
            return adminDTO;
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AdminDTO create(AdminDTO adminDTO, String ip, Long adminId) throws ServiceException {
        AdminDO adminDO = new AdminDO();
        Long count = adminMapper.selectCount(
                new QueryWrapper<AdminDO>()
                        .eq("username", adminDTO.getUsername()));
        if (count > 0) {
            throw new ServiceException(ExceptionDefinition.ADMIN_USER_NAME_REPEAT);
        }
        BeanUtils.copyProperties(adminDTO, adminDO);
        adminDO.setSalt(RandomStringUtils.randomNumeric(8));
        adminDO.setPassword(DigestUtils.md5Hex(adminDO.getPassword() + adminDO.getSalt()));
        adminDO.setRoleIds(JacksonUtil.toJSONString(adminDTO.getRoleIds()));
        adminDO.setGmtUpdate(LocalDateTime.now());
        adminDO.setGmtCreate(adminDO.getGmtUpdate());
        adminDO.setStatus(AdminStatusType.ACTIVE.getCode());
        adminDO.setLastLoginIp(ip);
        adminDO.setGmtLastLogin(LocalDateTime.now());
        if (adminMapper.insert(adminDO) > 0) {
            adminDTO.setId(adminDO.getId());
            return adminDTO;
        }
        throw new ServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String edit(AdminDTO adminDTO, Long adminId) throws ServiceException {
        Long id = adminDTO.getId();
        if (id == null) {
            throw new ServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
        }
        AdminDO adminDO = new AdminDO();
        BeanUtils.copyProperties(adminDTO, adminDO);
        adminDO.setGmtUpdate(LocalDateTime.now());
        AdminDO adminDOExist = adminMapper.selectById(id);
        if (StringUtils.isNotBlank(adminDTO.getPassword())) {
            adminDO.setPassword(DigestUtils.md5Hex(adminDO.getPassword() + adminDOExist.getSalt()));
        }
        adminDO.setUsername(null);
        if (!CollectionUtils.isEmpty(adminDTO.getRoleIds())) {
            adminDO.setRoleIds(JacksonUtil.toJSONString(adminDTO.getRoleIds()));
        }
        if (adminMapper.updateById(adminDO) > 0) {
            return "ok";
        }
        throw new ServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String delete(Long id, Long adminId) throws ServiceException {
        if (adminMapper.deleteById(id) > 0) {
            return "ok";
        }
        throw new ServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String newPassword(String accessToken, String oldPassword, String newPassword, Long adminId) throws ServiceException {
        AdminDO adminDOExist = adminMapper.selectById(adminId);
        if (!DigestUtils.md5Hex(adminDOExist.getPassword() + adminDOExist.getSalt()).equals(adminDOExist.getPassword())) {
            throw new ServiceException(ExceptionDefinition.ADMIN_PASSWORD_ERROR);
        }
        AdminDO adminDO = new AdminDO();
        adminDO.setId(adminId);
        adminDO.setPassword(DigestUtils.md5Hex(adminDOExist.getPassword() + adminDOExist.getSalt()));
        if (adminMapper.updateById(adminDO) > 0) {
            return "ok";
        }
        throw new ServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
    }

    @Override
    public Boolean sendLoginMsg(String username, String password) throws ServiceException {
        if ("guest".equals(username)) {
            throw new ServiceException(ExceptionDefinition.ADMIN_GUEST_NOT_NEED_VERIFY_CODE);
        }
        AdminDO admin = adminMapper.selectOne(
                new QueryWrapper<AdminDO>()
                        .eq("username", username));
        if (admin == null) {
            throw new ServiceException(ExceptionDefinition.ADMIN_USER_NOT_EXIST);
        }
        if (!DigestUtils.md5Hex(password + admin.getSalt()).equals(admin.getPassword())) {
            throw new ServiceException(ExceptionDefinition.ADMIN_PASSWORD_ERROR);
        }
        String code = RandomStringUtils.randomNumeric(6);
        cacheComponent.putRaw(CacheConst.ADMIN_MSG_CODE + admin.getPhone(), code, 300);
        SMSResult smsResult = smsClient.sendAdminLoginVerify(admin.getPhone(), code);
        if (!smsResult.isSuccess()) {
            throw new ServiceException(smsResult.getMessage(), ExceptionDefinition.ADMIN_VERIFY_CODE_SEND_FAIL.getCode());
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
            set.add(sessionUtil.getAdmin().getUsername());
            set.add(this.unimallAdminNotifyProperties.getUniNotifyAppSecret());
            set.add(this.unimallAdminNotifyProperties.getUniNotifyAppId());
            String json = okHttpClient
                    .newCall(new Request.Builder()
                            .get()
                            .url(this.unimallAdminNotifyProperties.getUniNotifyUrl() + "?_gp=developer&_mt=getRegisterUrl&userId=" + this.sessionUtil.getAdmin().getUsername()
                                    + "&appId=" + this.unimallAdminNotifyProperties.getUniNotifyAppId() + "&timestamp=" + timestamp + "&sign=" + DigestUtils.sha256Hex(URLEncoder.encode(String.join("", set), "utf-8")))
                            .build()).execute().body().string();

            GatewayResponse<String> response = JacksonUtil.parseObject(json, new TypeReference<GatewayResponse<String>>() {
            });
            if (response.getErrno() == 200) {
                return response.getData();
            }
            throw new ServiceException(response.getErrmsg(), CoreExceptionDefinition.THIRD_PART_SERVICE_EXCEPTION.getCode());
        } catch (ServiceException e) {
            throw e;
        } catch (IOException e) {
            throw new ServiceException(CoreExceptionDefinition.THIRD_PART_IO_EXCEPTION);
        } catch (Exception e) {
            logger.error("[绑定通知] 异常", e);
            throw new ServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
        }
    }

    private String generateAccessToken() {
        return (UUID.randomUUID().toString().replace("-", ""));
    }


}
