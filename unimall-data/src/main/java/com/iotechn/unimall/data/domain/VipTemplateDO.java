package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.core.annotation.param.NotNull;
import com.dobbinsoft.fw.support.domain.SuperDO;
import lombok.Data;

@Data
@TableName("unimall_vip_template")
public class VipTemplateDO extends SuperDO {

    @NotNull
    @ApiField(description = "模板标题")
    private String title;

    @NotNull
    @ApiField(description = "续费天数 90/60/30 天")
    private Integer dayNum;

    @NotNull
    @ApiField(description = "原价")
    private Integer originalPrice;

    @NotNull
    @ApiField(description = "现价")
    private Integer price;

    @ApiField(description = "文字描述")
    private String description;

    @NotNull
    @ApiField(description = "1. 在APP端显示 0. 不显示")
    private Integer display;
}
