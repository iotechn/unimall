package com.iotechn.unimall.admin.api.role;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iotechn.unimall.core.exception.AdminServiceException;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.domain.AdminDO;
import com.iotechn.unimall.data.domain.RoleDO;
import com.iotechn.unimall.data.domain.RolePermissionDO;
import com.iotechn.unimall.data.dto.PermissionPointDTO;
import com.iotechn.unimall.data.dto.RoleSetPermissionDTO;
import com.iotechn.unimall.data.enums.RoleStatusType;
import com.iotechn.unimall.data.mapper.AdminMapper;
import com.iotechn.unimall.data.mapper.RoleMapper;
import com.iotechn.unimall.data.mapper.RolePermissionMapper;
import com.iotechn.unimall.data.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by rize on 2019/4/11.
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    public static List<PermissionPointDTO> permDTOs = new LinkedList<>();

    public static Set<String> allPermPoint = new HashSet<>();

    @Override
    public Page<RoleDO> list(String name, Integer page, Integer limit, Long adminId) throws ServiceException {
        QueryWrapper<RoleDO> wrapper = new QueryWrapper<RoleDO>();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        return roleMapper.selectPage(Page.div(page,limit,RoleDO.class),wrapper);
    }

    @Override
    public RoleDO create(RoleDO roleDO, Long adminId) throws ServiceException {
        Date now = new Date();
        roleDO.setStatus(RoleStatusType.ACTIVE.getCode());
        roleDO.setGmtUpdate(now);
        roleDO.setGmtCreate(now);
        if (roleMapper.insert(roleDO) > 0) {
            return roleDO;
        }
        throw new AdminServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String delete(Long roleId, Long adminId) throws ServiceException {
        // 列举出所有管理员，并校验是否存在关联此角色的管理员
        List<List<Long>> roleIds = adminMapper.selectList(new QueryWrapper<AdminDO>().select("role_ids")).stream().map(item -> JSONObject.parseArray(item.getRoleIds(), Long.class)).collect(Collectors.toList());
        for (List<Long> list : roleIds) {
            for (Long id : list) {
                if (roleId.longValue() == id.longValue()) {
                    throw new AdminServiceException(ExceptionDefinition.ADMIN_ROLE_UNION_ADMIN);
                }
            }
        }
        if (roleMapper.deleteById(roleId) > 0) {
            return "ok";
        }
        throw new AdminServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
    }

    @Override
    public RoleDO update(RoleDO roleDO, Long adminId) throws ServiceException {
        if (roleMapper.updateById(roleDO) > 0) {
            return roleDO;
        }
        throw new AdminServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String permissionSet(RoleSetPermissionDTO roleSetPermissionDTO, Long adminId) throws ServiceException {
        Long roleId = roleSetPermissionDTO.getRoleId();
        if (roleId == null) {
            throw new AdminServiceException(ExceptionDefinition.PARAM_CHECK_FAILED);
        }
        rolePermissionMapper.delete(new QueryWrapper<RolePermissionDO>().eq("role_id", roleId));
        //构建插入
        List<String> permissions = roleSetPermissionDTO.getPermissions();
        if (!CollectionUtils.isEmpty(permissions)) {
            Date now = new Date();
            for (String permission : permissions) {
                RolePermissionDO rolePermissionDO = new RolePermissionDO();
                rolePermissionDO.setRoleId(roleId);
                rolePermissionDO.setDeleted(0);
                rolePermissionDO.setPermission(permission);
                rolePermissionDO.setGmtCreate(now);
                rolePermissionDO.setGmtUpdate(now);
                rolePermissionMapper.insert(rolePermissionDO);
            }
        }
        return "ok";
    }

    @Override
    public Map<String,Object> permissionList(Long roleId, Long adminId) throws ServiceException {
        List<RolePermissionDO> rolePermissionDOList =
                rolePermissionMapper.selectList(
                        new QueryWrapper<RolePermissionDO>()
                                .eq("role_id", roleId));

        Map<String,Object> map = new HashMap<>();
        Set<String> permissionPoint = new HashSet<>();
        for (RolePermissionDO permissionDO : rolePermissionDOList) {
            if ("*".equals(permissionDO.getPermission())) {
                //若为超级管理员，则直接push所有权限进去
                permissionPoint.addAll(allPermPoint);
                break;
            } else {
                permissionPoint.add(permissionDO.getPermission());
            }
        }
        map.put("systemPermissions", permDTOs);
        map.put("assignedPermissions", permissionPoint);
        return map;
    }

    @Override
    public List<Map<String, Object>> options(Long adminId) throws ServiceException {
        List<RoleDO> roleDOS = roleMapper.selectList(new QueryWrapper<>());
        List<Map<String,Object>> list = new LinkedList<>();
        roleDOS.forEach(item -> {
            Map<String,Object> map = new HashMap<>();
            map.put("value", item.getId());
            map.put("label", item.getName());
            list.add(map);
        });
        return list;
    }

}
