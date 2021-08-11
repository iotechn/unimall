package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.support.domain.SuperDO;
import lombok.Data;

/*
@author kbq
@date  2019/7/5 - 10:03
用户收藏商品
*/
@Data
@ApiEntity(description = "用户收藏")
@TableName("unimall_favorite")
public class FavoriteDO extends SuperDO {

    @ApiField(description = "用户ID")
    private Long userId;

    @ApiField(description = "商品ID")
    private Long spuId;
}
