package com.iotechn.unimall.data.dto;

import lombok.Data;

/**
 * Created by rize on 2019/7/3.
 */
@Data
public class CartDTO extends SuperDTO {

    private Long skuId;

    private Integer num;

    private String title;

    private Integer price;

    private String skuTitle;

    private String spuImg;

    private String skuImg;

    private Integer stock;

    private Long spuId;

}
