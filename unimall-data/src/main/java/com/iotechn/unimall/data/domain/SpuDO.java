package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.support.domain.SuperDO;
import com.iotechn.unimall.data.enums.SpuActivityType;
import com.iotechn.unimall.data.enums.StatusType;
import lombok.Data;

import java.util.Date;

/**
 * Created by rize on 2019/7/1.
 */
@Data
@TableName("unimall_spu")
public class SpuDO extends SuperDO {

    /**
     * 商品原价
     */
    @ApiField(description = "商品原价（仅显示作用）")
    private Integer originalPrice;

    /**
     * 商品价格 单位 分
     */
    @ApiField(description = "价格（仅显示作用）")
    private Integer price;

    /**
     * 会员价格
     */
    @ApiField(description = "VIP价格（仅显示作用）")
    private Integer vipPrice;

    /**
     * 商品标题
     */
    @ApiField(description = "商品标题")
    private String title;

    /**
     * 商品销量
     */
    @ApiField(description = "当前销量")
    private Integer sales;

    /**
     * 商品主图（冗余信息）
     */
    @ApiField(description = "商品主图")
    private String img;

    /**
     * 商品详情
     */
    @ApiField(description = "富文本详情")
    private String detail;

    /**
     * 商品一句话描述
     */
    @ApiField(description = "商品描述")
    private String description;

    /**
     * 商品类目id
     */
    @ApiField(description = "所属类目ID")
    private Long categoryId;

    /**
     * 运费模板Id
     */
    @ApiField(description = "运费模板ID")
    private Long freightTemplateId;

    /**
     * 单位
     */
    @ApiField(description = "计量单位")
    private String unit;

    /**
     * 商品状态
     */
    @ApiField(description = "状态", enums = StatusType.class)
    private Integer status;

    /**
     * 当前商品所参加的促销活动类型
     */
    @ApiField(description = "促销活动类型", enums = SpuActivityType.class)
    private Integer activityType;

    /**
     * 当前商品所参加促销活动ID
     */
    @ApiField(description = "促销活动ID")
    private Long activityId;

    /**
     * 当前商品参加促销活动开始时间
     */
    @ApiField(description = "活动开始时间")
    private Date gmtActivityStart;

    /**
     * 当前商品参加促销活动结束时间
     */
    @ApiField(description = "活动结束时间")
    private Date gmtActivityEnd;

}
