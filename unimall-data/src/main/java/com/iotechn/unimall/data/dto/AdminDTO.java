package com.iotechn.unimall.data.dto;

import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.core.entiy.SuperDTO;
import com.dobbinsoft.fw.core.entiy.inter.PermissionOwner;
import com.iotechn.unimall.data.enums.StatusType;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by rize on 2019/4/8.
 */
@Data
@ApiEntity(description = "管理员传输实体")
public class AdminDTO extends SuperDTO implements PermissionOwner {

    @ApiField(description = "登录用户名")
    private String username;

    @ApiField(description = "登录密码")
    private String password;

    @ApiField(description = "手机号 会用于登录验证")
    private String phone;

    @ApiField(description = "真实姓名")
    private String realname;

    @ApiField(description = "管理员头像(暂时没用)")
    private String avatarUrl;

    /**
     * 管理员角色 JSON 数据
     */
//    private String roleIds;

    /**
     * 管理员状态
     */
    @ApiField(description = "状态", enums = StatusType.class)
    private Integer status;

    @ApiField(description = "最后登录IP")
    private String lastLoginIp;

    @ApiField(description = "最后登录时间")
    private Date gmtLastLogin;

    @ApiField(description = "角色名称")
    private List<String> roles;

    @ApiField(description = "角色ID")
    private List<Long> roleIds;

    @ApiField(description = "管理员拥有权限点")
    private List<String> perms;

}
