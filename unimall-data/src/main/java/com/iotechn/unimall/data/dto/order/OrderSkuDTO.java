package com.iotechn.unimall.data.dto.order;

import lombok.Data;

/**
 * Created by rize on 2019/7/6.
 */
@Data
public class OrderSkuDTO {

    private Long skuId;

    /**
     * 冗余SPUID方便评论
     */
    private Long spuId;

    private Long orderId;

    /**
     * 冗余，方便技术查库
     */
    private String orderNo;

    /**
     * SPU 标题
     */
    private String title;

    /**
     * SKU 标题， 即小规格名称
     */
    private String skuTitle;

    private String barCode;

    private Integer num;

    /**
     * 单价
     */
    private Integer price;

    /**
     * SKU 或 SPU主图 （优先使用SKU图）
     */
    private String img;

}
