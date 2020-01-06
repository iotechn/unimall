package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
@PackageName:com.iotechn.unimall.data.domain
@ClassName: GroupShopSkuDO
@Description:
@author kbq
@date 19-11-13下午1:21
*/
@Data
@TableName("unimall_group_shop_sku")
public class GroupShopSkuDO extends SuperDO{

    @TableField("sku_id")
    private Long skuId;

    @TableField("group_shop_id")
    private Long groupShopId;

    @TableField("sku_group_shop_price")
    private Integer skuGroupShopPrice;

}
