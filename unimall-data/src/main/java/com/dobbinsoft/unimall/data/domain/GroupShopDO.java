package com.dobbinsoft.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.core.annotation.param.NotNull;
import com.dobbinsoft.fw.support.domain.SuperDO;
import com.dobbinsoft.unimall.data.enums.GroupShopAutomaticRefundType;
import com.dobbinsoft.unimall.data.enums.StatusType;
import lombok.Data;

import java.time.LocalDateTime;

/**
@PackageName:com.dobbinsoft.unimall.data.domain
@ClassName: GroupShopSpuDO
@Description:
@author kbq
@date 19-11-13下午1:23
*/

@Data
@ApiEntity(description = "团购领域模型")
@TableName("unimall_group_shop")
public class GroupShopDO extends SuperDO {

    @NotNull
    @ApiField(description = "商品ID")
    private Long spuId;

    @NotNull
    @ApiField(description = "冗余的团购Sku最低价")
    private Integer minPrice;

    @NotNull
    @ApiField(description = "冗余的团购SKu最高价")
    private Integer maxPrice;

    @NotNull
    @ApiField(description = "活动开始时间")
    private LocalDateTime gmtStart;

    @NotNull
    @ApiField(description = "活动结束时间")
    private LocalDateTime gmtEnd;

    /**
     * 团购最小人数
     */
    @NotNull
    @ApiField(description = "团购最小人数")
    private Integer minNum;

    @NotNull
    @ApiField(description = "当前已付款人数")
    private Integer buyerNum;

    @NotNull
    @ApiField(description = "团购人数未满执行策略", enums = GroupShopAutomaticRefundType.class)
    private Integer automaticRefund;

    @NotNull
    @ApiField(description = "团购状态", enums = StatusType.class)
    private Integer status;

}
