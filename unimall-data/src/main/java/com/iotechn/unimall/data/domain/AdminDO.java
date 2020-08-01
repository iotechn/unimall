package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Created by rize on 2019/4/8.
 */
@TableName("unimall_admin")
@Data
public class AdminDO extends SuperDO {

    /**
     * 管理员名
     */
    private String username;

    /**
     * 管理员登录密码
     */
    private String password;

    private String phone;

    private String realname;

    @TableField("avatar_url")
    private String avatarUrl;

    /**
     * 管理员角色 JSON 数据
     */
    @TableField("role_ids")
    private String roleIds;

    /**
     * 管理员状态
     */
    private Integer status;

    @TableField("last_login_ip")
    private String lastLoginIp;

    @TableField("gmt_last_login")
    private String gmtLastLogin;

}
