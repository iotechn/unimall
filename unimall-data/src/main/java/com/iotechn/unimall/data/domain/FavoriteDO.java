package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/*
@author kbq
@date  2019/7/5 - 10:03
用户收藏商品
*/
@Data
@TableName("unimall_favorite")
public class FavoriteDO extends SuperDO{

    @TableField("user_id")
    private Long userId;

    @TableField("spu_id")
    private Long spuId;
}
