package com.dobbinsoft.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.core.annotation.param.NotNull;
import com.dobbinsoft.fw.support.domain.SuperDO;
import com.dobbinsoft.unimall.data.enums.StatusType;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by rize on 2019/4/8.
 */
@ApiEntity(description = "管理员领域模型")
@TableName("unimall_admin")
@Data
public class AdminDO extends SuperDO {

    @NotNull
    @ApiField(description = "登录用户名")
    private String username;

    @NotNull
    @ApiField(description = "登录密码")
    private String password;

    @NotNull
    @ApiField(description = "密码盐值")
    private String salt;

    @NotNull
    @ApiField(description = "手机号 会用于登录验证")
    private String phone;

    @NotNull
    @ApiField(description = "真实姓名")
    private String realname;

    @ApiField(description = "管理员头像(暂时没用)")
    private String avatarUrl;

    /**
     * 管理员角色 JSON 数据
     */
    @NotNull
    @ApiField(description = "管理员角色 JSON 数据 [1, 2]")
    private String roleIds;

    /**
     * 管理员状态
     */
    @NotNull
    @ApiField(description = "状态", enums = StatusType.class)
    private Integer status;

    @ApiField(description = "最后登录IP")
    private String lastLoginIp;

    @ApiField(description = "最后登录时间")
    private LocalDateTime gmtLastLogin;

}
