package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Description:
 * User: rize
 * Date: 2020/8/1
 * Time: 17:24
 */
@Data
@TableName("unimall_spu_specification")
public class SpuSpecificationDO extends SuperDO {

    @TableField("spu_id")
    private Long spuId;

    private String title;

}
