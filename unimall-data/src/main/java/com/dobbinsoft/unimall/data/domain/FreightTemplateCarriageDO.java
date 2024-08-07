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
 * User: kbq
 * Date: 2019-07-07
 * Time: 上午11:41
 */
@Data
@ApiEntity(description = "运费模板特殊地区")
@TableName("unimall_freight_template_carriage")
public class FreightTemplateCarriageDO extends SuperDO {

    @NotNull
    @ApiField(description = "主表ID")
    private Long templateId;

    @NotNull
    @ApiField(description = "特殊地区名称")
    private String designatedArea;

    @NotNull
    @ApiField(description = "免邮费价格")
    private Integer freePrice;

    @NotNull
    @ApiField(description = "以起步价计算的 重量 eg 1000 代表1KG内 以firstPrice计算")
    private Integer firstWeight;

    @NotNull
    @ApiField(description = "起步价")
    private Integer firstPrice;

    @NotNull
    @ApiField(description = "续重 每增加N的重量")
    private Integer continueWeight;

    @NotNull
    @ApiField(description = "续重价格 每增加N的重量 增加M的运费")
    private Integer continuePrice;

}
