package com.iotechn.unimall.data.dto;

import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.core.entiy.SuperDTO;
import com.iotechn.unimall.data.enums.StatusType;
import lombok.Data;

/*
@author kbq
@date  2019/7/5 - 10:33
*/
@Data
@ApiEntity(description = "用户收藏")
public class FavoriteDTO extends SuperDTO {

    @ApiField(description = "用户ID")
    private Long userId;

    @ApiField(description = "商品ID")
    private Long spuId;

    @ApiField(description = "商品原价(仅显示)")
    private Integer originalPrice;

    @ApiField(description = "价格")
    private Integer price;

    @ApiField(description = "Vip价格")
    private Integer vipPrice;

    @ApiField(description = "商品标题")
    private String title;

    @ApiField(description = "商品销量")
    private Integer sales;

    @ApiField(description = "商品图片")
    private String img;

    @ApiField(description = "商品简介")
    private String description;

    @ApiField(description = "计量单位")
    private String unit;

    @ApiField(description = "状态", enums = StatusType.class)
    private Integer status;

}
