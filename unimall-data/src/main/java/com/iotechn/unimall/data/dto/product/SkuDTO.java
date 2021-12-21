package com.iotechn.unimall.data.dto.product;

import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.core.entiy.SuperDTO;
import com.iotechn.unimall.data.enums.SpuActivityType;
import com.iotechn.unimall.data.enums.StatusType;
import lombok.Data;

import java.util.Date;

/**
 * Created by rize on 2019/7/6.
 */
@Data
public class SkuDTO extends SuperDTO {

    @ApiField(description = "商品ID")
    private Long spuId;

    @ApiField(description = "条形码")
    private String barCode;

    @ApiField(description = "规格名称")
    private String title;

    @ApiField(description = "所属类目ID")
    private Long categoryId;

    @ApiField(description = "商品标题")
    private String spuTitle;

    /**
     * TODO 注释格式
     */
    @ApiField(description = "每个规格的具体值 格式: ")
    private String specification;

    @ApiField(description = "规格图片")
    private String img;

    @ApiField(description = "SPU商品图片")
    private String spuImg;
    @ApiField(description = "原始价格(仅显示作用)")
    private Integer originalPrice;

    @ApiField(description = "价格")
    private Integer price;

    @ApiField(description = "VIP价格")
    private Integer vipPrice;

    @ApiField(description = "库存")
    private Integer stock;

    @ApiField(description = "重量（G）")
    private Integer weight;

    @ApiField(description = "运费模板ID")
    private Long freightTemplateId;

    @ApiField(description = "状态", enums = StatusType.class)
    private Integer status;

    @ApiField(description = "计量单位")
    private String unit;

    @ApiField(description = "活动类型", enums = SpuActivityType.class)
    private Integer activityType;

    @ApiField(description = "活动ID")
    private Long activityId;

    @ApiField(description = "活动开始时间")
    private Date gmtActivityStart;

    @ApiField(description = "活动结束时间")
    private Date gmtActivityEnd;

}
