package com.iotechn.unimall.data.dto.goods;

import com.iotechn.unimall.data.dto.SuperDTO;
import lombok.Data;

import java.util.Date;

/**
 * Created by rize on 2019/7/6.
 */
@Data
public class SkuDTO extends SuperDTO {

    private Long spuId;

    private String barCode;

    private Long categoryId;

    /**
     * SKU显示文字
     */
    private String title;

    private String spuTitle;

    private String specification;

    private String img;

    private String spuImg;

    private Integer originalPrice;

    private Integer price;

    private Integer vipPrice;

    private Integer stock;

    private Long freightTemplateId;

    private Integer status;

    private Integer weight;

    private String unit;

    private Integer activityType;

    private Long activityId;

    private Date gmtActivityStart;

    private Date gmtActivityEnd;

}
