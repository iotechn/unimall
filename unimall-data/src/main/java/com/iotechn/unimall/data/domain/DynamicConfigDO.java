package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dobbinsoft.fw.support.domain.SuperDO;
import lombok.Data;

import java.io.Serializable;

/**
 * Description: 为方便使用系统，将部分静态配置动态化，例如微信相关的配置，OSS，短信等配置
 * User: rize
 * Date: 2020/8/5
 * Time: 11:28
 */
@Data
@TableName("unimall_dynamic_config")
public class DynamicConfigDO extends SuperDO implements Serializable {

    private String configKey;

    private String configValue;

}
