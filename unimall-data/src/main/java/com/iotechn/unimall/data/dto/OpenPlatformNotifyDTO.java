package com.iotechn.unimall.data.dto;

import com.dobbinsoft.fw.core.entiy.SuperDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * ClassName: OpenPlatformNotifyDTO
 * Description: 开放平台通知实例
 *
 * @author: e-weichaozheng
 * @date: 2021-04-25
 */
@Data
public class OpenPlatformNotifyDTO extends SuperDTO {

    private String clientCode;

    /**
     * 已经推送次数
     */
    private Integer times;

    /**
     * 是否已经通知
     */
    private Integer status;

    /**
     * 下一次通知时间
     */
    private Date nextNotify;

    /**
     * 参数string json List<String>
     */
    private List<String> params;

}
