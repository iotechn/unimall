package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.support.domain.SuperDO;
import lombok.Data;

/**
 * Created by rize on 2019/7/3.
 */
@Data
@ApiEntity(description = "购物车领域模型")
@TableName("unimall_cart")
public class CartDO extends SuperDO {

    @ApiField(description = "商品ID")
    private Long skuId;

    @ApiField(description = "用户ID")
    private Long userId;

    @ApiField(description = "数量")
    private Integer num;

}
