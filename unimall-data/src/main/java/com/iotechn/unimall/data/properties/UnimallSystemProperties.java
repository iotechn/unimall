package com.iotechn.unimall.data.properties;

import com.iotechn.unimall.data.annotation.DynamicConfigProperties;
import com.iotechn.unimall.data.constant.DynamicConst;
import lombok.Data;

/**
 * Description:
 * User: rize
 * Date: 2020/8/23
 * Time: 21:47
 */
@Data
@DynamicConfigProperties(prefix = DynamicConst.SYSTEM_CONFIG_PREFIX)
public class UnimallSystemProperties {

    /**
     * 用户会话周期（M）
     */
    private Integer userSessionPeriod;

    /**
     * 管理员会话周期（M）
     */
    private Integer adminSessionPeriod;

    /**
     * SSL 配置
     */
    private String sslCrtPath;

    private String sslKeyPath;

    private String guest;



}
