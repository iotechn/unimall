package com.iotechn.unimall.launcher.model;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-08-12
 * Time: 上午10:35
 */
@Data
public class GatewayResponse {
    private int errno;
    private String errmsg;
    private Object data;
    private long timestamp;
}
