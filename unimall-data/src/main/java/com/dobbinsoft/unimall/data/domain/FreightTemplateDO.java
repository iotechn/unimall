package com.dobbinsoft.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.core.annotation.param.NotNull;
import com.dobbinsoft.fw.support.domain.SuperDO;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 若难以理解计费方式    请联想坐出租车的计费方式
 * 起步价 -> defaultFirstPrice
 * 起步里程 -> defaultFirstWeight
 *
 * User: rize
 * Date: 2019-07-07
 * Time: 上午11:35
 */
@Data
@ApiEntity(description = "运费模板领域模型")
@TableName("unimall_freight_template")
public class FreightTemplateDO extends SuperDO {

    @NotNull
    @ApiField(description = "运费模板标题")
    private String title;

    @NotNull
    @ApiField(description = "商品发货地址")
    private String spuLocation;

    @NotNull
    @ApiField(description = "发货期限 eg 7天内发货 则输入7")
    private Integer deliveryDeadline;

    @NotNull
    @ApiField(description = "免邮费价格 0包邮 -1永不包邮，正数表示满好多包邮 单位 分")
    private Integer defaultFreePrice;

    /**
     * 第一次计价后，可以包含的商品重量
     * default:为了区分特殊地区字段，加的
     */
    @NotNull
    @ApiField(description = "以起步价计算的 重量 eg 1000 代表1KG内 以defaultFirstPrice计算")
    private Integer defaultFirstWeight;

    /**
     * 第一次计价的价格
     */
    @NotNull
    @ApiField(description = "起步价")
    private Integer defaultFirstPrice;

    /**
     * 商品数量超过了第一次计价后的商品重量，会续加一次价格，
     * 每续加一次价格，包含的商品重量
     */
    @NotNull
    @ApiField(description = "续重 每增加N的重量")
    private Integer defaultContinueWeight;

    /**
     * 续加计价的价格
     */
    @NotNull
    @ApiField(description = "续重价格 每增加N的重量 增加M的运费")
    private Integer defaultContinuePrice;
}
