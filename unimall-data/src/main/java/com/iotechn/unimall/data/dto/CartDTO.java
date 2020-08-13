package com.iotechn.unimall.data.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by rize on 2019/7/3.
 */
@Data
public class CartDTO extends SuperDTO {

    private Long skuId;

    private Integer num;

    private String title;

    private Integer originalPrice;

    private Integer price;

    private Integer vipPrice;

    private String skuTitle;

    private String spuImg;

    private String skuImg;

    private Integer stock;

    private Long spuId;

    private Long categoryId;

    private List<Long> categoryIdList;

    private Integer weight;

    private Long freightTemplateId;

}
