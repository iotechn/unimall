package com.iotechn.unimall.data.dto;

import lombok.Data;

/*
@author kbq
@date  2019/7/5 - 10:33
*/
@Data
public class CollectDTO extends SuperDTO {

    private Long userId;
    private Long spuId;
    private Integer originalPrice;
    private Integer price;
    private Integer vipPirce;
    private String title;
    private Integer sales;
    private String img;
    private String description;
    private String unit;
    private Integer status;

}
