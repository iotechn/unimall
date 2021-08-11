package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.support.domain.SuperDO;
import lombok.Data;

/*
@author kbq
@date  2019/7/6 - 10:14
*/
@TableName("unimall_appraise")
@Data
@ApiEntity(description = "评论领域模型")
public class AppraiseDO extends SuperDO {

    @ApiField(description = "评论商品ID")
    private Long spuId;

    @ApiField(description = "评论规格ID")
    private Long skuId;

    @ApiField(description = "购买订单")
    private Long orderId;

    @ApiField(description = "评论的用户ID")
    private Long userId;

    @ApiField(description = "评论的文字内容")
    private String content;

    @ApiField(description = "打分")
    private Integer score;


}
