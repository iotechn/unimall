package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Created by rize on 2019/7/3.
 */
@Data
@TableName("unimall_cart")
public class CartDO extends SuperDO {

    @TableField("sku_id")
    private Long skuId;

    @TableField("user_id")
    private Long userId;

    private Integer num;

}
