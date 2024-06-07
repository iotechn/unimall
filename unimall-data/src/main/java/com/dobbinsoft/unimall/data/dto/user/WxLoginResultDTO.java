package com.dobbinsoft.unimall.data.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class WxLoginResultDTO {

    @JsonProperty("openid")
    private String openid;
    @JsonProperty("session_key")
    private String sessionKey;
    @JsonProperty("unionid")
    private String unionid;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("errcode")
    private Integer errcode;
    @JsonProperty("errmsg")
    private String errmsg;
}
