package com.iotechn.unimall.data.dto;

import com.dobbinsoft.fw.core.entiy.SuperDTO;
import com.dobbinsoft.fw.core.entiy.inter.IdentityOwner;
import lombok.Data;

import java.util.Date;

/**
 * Created by rize on 2019/7/1.
 */
@Data
public class UserDTO extends SuperDTO implements IdentityOwner {

    private String phone;

    private String wxMpOpenId;

    private String nickname;

    private String avatarUrl;

    private Integer level;

    private Date birthday;

    private Integer gender;

    private Date gmtLastLogin;

    private String lastLoginIp;

    private Integer status;

    /**
     * 登录成功，包装此参数
     */
    private String accessToken;
}
