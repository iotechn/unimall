package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dobbinsoft.fw.support.domain.SuperDO;
import lombok.Data;

import java.util.Date;

/**
 * ClassName: OpenPlatformNotifyDO
 * Description: 开放平台通知实例
 *
 * @author: e-weichaozheng
 * @date: 2021-04-25
 */
@Data
@TableName("unimall_open_platform_notify")
public class OpenPlatformNotifyDO extends SuperDO {

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
    private String params;

}
