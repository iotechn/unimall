package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dobbinsoft.fw.support.domain.SuperDO;
import lombok.Data;

/**
 * ClassName: OpenPlatformClientDO
 * Description: 开放平台接入方实例
 *
 * @author: e-weichaozheng
 * @date: 2021-04-25
 */
@Data
@TableName("unimall_open_platform_client")
public class OpenPlatformClientDO extends SuperDO {

    /**
     * 客户编号
     */
    private String code;

    /**
     * 平台私钥
     */
    private String privateKey1;

    /**
     * 平台公钥
     */
    private String publicKey1;

    /**
     * 对接者私钥
     */
    private String publicKey2;

    /**
     * 客户回调地址
     */
    private String notifyUrl;

    /**
     * 客户可以访问的接口列表 json List<OPClientPermission>
     */
    private String permissionList;

}
