package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.support.domain.SuperDO;
import com.iotechn.unimall.data.enums.StatusType;
import com.iotechn.unimall.data.enums.UserGenderType;
import com.iotechn.unimall.data.enums.UserLevelType;
import lombok.Data;

import java.util.Date;

/**
 * Created by rize on 2019/6/30.
 */
@Data
@ApiEntity(description = "用户领域模型表")
@TableName("unimall_user")
public class UserDO extends SuperDO {

    @ApiField(description = "用户手机号")
    private String phone;

    @ApiField(description = "用户登录密码 可空")
    private String password;

    @ApiField(description = "密码盐值")
    private String salt;

    @ApiField(description = "支付宝小程序OPENID")
    private String aliMpOpenId;

    /**
     * 需要扩展其他平台，使用横向扩展字段
     */
    @ApiField(description = "wx小程序OPENID")
    private String wxMpOpenId;

    @ApiField(description = "wx H5 OPENID")
    private String wxH5OpenId;

    @ApiField(description = "wx开放平台OPENID")
    private String wxAppOpenId;

    @ApiField(description = "昵称")
    private String nickname;

    @ApiField(description = "头像URL")
    private String avatarUrl;

    @ApiField(description = "省份")
    private String province;

    @ApiField(description = "市")
    private String city;

    @ApiField(description = "区")
    private String county;

    @ApiField(description = "等级", enums = UserLevelType.class)
    private Integer level;

    @ApiField(description = "VIP过期时间")
    private Date gmtVipExpire;

    @ApiField(description = "出生日期")
    private Date birthday;

    @ApiField(description = "性别", enums = UserGenderType.class)
    private Integer gender;

    @ApiField(description = "最后登录时间")
    private Date gmtLastLogin;

    @ApiField(description = "最后登录ID")
    private String lastLoginIp;

    @ApiField(description = "状态", enums = StatusType.class)
    private Integer status;

}
