package com.iotechn.unimall.data.dto;

import com.dobbinsoft.fw.core.entiy.SuperDTO;
import com.dobbinsoft.fw.support.component.open.model.OPClientPermission;
import lombok.Data;

import java.util.List;

/**
 * ClassName: OpenPlatformClientDO
 * Description: 开放平台接入方实例
 *
 * @author: e-weichaozheng
 * @date: 2021-04-25
 */
@Data
public class OpenPlatformClientDTO extends SuperDTO {

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
    private List<OPClientPermission> permissionList;

}
