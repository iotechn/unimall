package com.iotechn.unimall.data.model;

import com.iotechn.unimall.data.dto.SuperDTO;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2020/3/26
 * Time: 15:57
 */
@Data
public class OrderCalcSkuModel extends SuperDTO {

    // 前端请求的数据，欲购买的SKU与欲购买的数量

    private Long skuId;

    private Integer num;


    // 以下 在循环中加入的数据，是可信任的，但仅SKU循环后可用

    private Long freightTemplateId;

    /**
     * 单品重量
     */
    private Integer weight;

    /**
     * 此处价格为用户真实购买的价格，若商品参加促销活动，则是促销活动的价格
     */
    private Integer price;

    private Integer vipPrice;

}
