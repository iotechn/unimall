package com.iotechn.unimall.data.dto.goods;

import com.iotechn.unimall.data.dto.SuperDTO;
import lombok.Data;

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

    private String img;

    private String spuImg;

    private Integer originalPrice;

    private Integer price;

    private Integer vipPrice;

    private Integer stock;

    private Integer status;

    private Integer freezeStock;

    private String unit;

}
