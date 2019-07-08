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
 * Time: 下午3:28
 */
@TableName("unimall_recommend")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendDO extends SuperDO {

    @TableField("spu_id")
    private Long spuId;

    @TableField("recommend_type")
    private Integer recommendType;
}
