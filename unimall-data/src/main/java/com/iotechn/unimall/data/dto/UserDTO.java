package com.iotechn.unimall.data.dto;

import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.core.entiy.SuperDTO;
import com.dobbinsoft.fw.core.entiy.inter.IdentityOwner;
import com.iotechn.unimall.data.enums.StatusType;
import com.iotechn.unimall.data.enums.UserGenderType;
import com.iotechn.unimall.data.enums.UserLevelType;
import lombok.Data;

import java.util.Date;

/**
 * Created by rize on 2019/7/1.
 */
@Data
@ApiEntity(description = "用户传输实体")
public class UserDTO extends SuperDTO implements IdentityOwner {

    @ApiField(description = "用户手机号")
    private String phone;

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

    /**
     * com.dobbinsoft.fw.pay.enums.PayPlatformType
     */
    @ApiField(description = "登录平台PayPlatformType")
    private Integer platform;
    /**
     * 登录成功，包装此参数
     */
    private String accessToken;
}
