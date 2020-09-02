package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/*
@author kbq
@date  2019/7/6 - 10:14
*/
@TableName("unimall_appraise")
@Data
public class AppraiseDO extends SuperDO{

    @TableField("spu_id")
    private Long spuId;
    @TableField("sku_id")
    private Long skuId;
    @TableField("order_id")
    private Long orderId;
    @TableField("user_id")
    private Long userId;

    //评论内容
    private String content;
    //评论星数
    private Integer score;


}
