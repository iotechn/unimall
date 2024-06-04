package com.iotechn.unimall.data.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WxH5TicketResultDTO {

    private String ticket;

    @JsonProperty("expires_in")
    private Integer expiresIn;

}
