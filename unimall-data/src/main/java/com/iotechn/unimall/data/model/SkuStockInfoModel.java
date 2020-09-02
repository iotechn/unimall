package com.iotechn.unimall.data.model;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2020/3/26
 * Time: 18:12
 */
@Data
public class SkuStockInfoModel {

    private Long skuId;

    /**
     * 当前系统剩余量
     */
    private int surplus;

    /**
     * 用户期望购买量
     */
    private int expect;

}
