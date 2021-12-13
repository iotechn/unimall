package com.iotechn.unimall.biz.client.erp.dobbin.model;

import com.dobbinsoft.fw.support.domain.SuperDO;
import lombok.Data;

import java.io.Serializable;

/**
 * Description: 属性关联表
 * User: rize
 * Date: 2021/3/28
 * Time: 13:10
 */
@Data
public class ErpSkuAttribute extends SuperDO implements Serializable {

    private Long skuId;

    private String name;

    private String attribute;

}
