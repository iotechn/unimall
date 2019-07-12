package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * Created by rize on 2019/7/2.
 */
@TableName("unimall_spu_attribute")
@Data
public class SpuAttributeDO extends SuperDO {

    @TableField("spu_id")
    private Long spuId;

    private String attribute;

    private String value;

}
