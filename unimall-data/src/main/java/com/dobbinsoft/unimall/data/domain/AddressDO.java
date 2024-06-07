package com.dobbinsoft.unimall.data.domain;

/*
@author kbq
@date  2019/7/4 - 21:29
*/

import com.baomidou.mybatisplus.annotation.TableName;
import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.core.annotation.param.NotNull;
import com.dobbinsoft.fw.support.domain.SuperDO;
import lombok.Data;

@Data
@ApiEntity(description = "地址领域模型")
@TableName("unimall_address")
public class AddressDO extends SuperDO {

    @NotNull
    @ApiField(description = "省份")
    private String province;

    @NotNull
    @ApiField(description = "城市")
    private String city;

    @NotNull
    @ApiField(description = "行政区/镇")
    private String county;

    @NotNull
    @ApiField(description = "详细地址")
    private String address;

    @NotNull
    @ApiField(description = "是否是默认地址")
    private Integer defaultAddress;

    @NotNull
    @ApiField(description = "所属用户ID")
    private Long userId;

    @NotNull
    @ApiField(description = "签收手机号")
    private String phone;

    @NotNull
    @ApiField(description = "签收人姓名")
    private String consignee;



}
