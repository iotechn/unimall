package com.iotechn.unimall.data.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WxH5UserInfoResultDTO {

    private String nickname;

    private String headimgurl;

    // 枚举参照H5
    private Integer sex;

}
