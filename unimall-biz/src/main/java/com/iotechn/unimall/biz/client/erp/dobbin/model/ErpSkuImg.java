package com.iotechn.unimall.biz.client.erp.dobbin.model;

import com.dobbinsoft.fw.support.domain.SuperDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * ClassName: ErpSkuImgDO
 * Description: SKU 图片
 *
 * @author: e-weichaozheng
 * @date: 2021-03-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ErpSkuImg extends SuperDO implements Serializable {

    private Long skuId;

    private String url;

}
