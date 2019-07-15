package com.iotechn.unimall.data.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by rize on 2019/4/12.
 * 角色授权上报数据DTO
 */
@Data
public class RoleSetPermissionDTO {

    private Long roleId;

    private List<String> permissions;

}
