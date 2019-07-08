package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-08
 * Time: 上午8:30
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("unimall_footprint")
public class FootprintDO extends SuperDO {

    @TableField("user_id")
    private Long userId;

    @TableField("spu_id")
    private Long spuId;
}
