package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dobbinsoft.fw.support.domain.SuperDO;
import lombok.Data;

/**
 * Created by rize on 2019/4/8.
 */
@TableName("unimall_role")
@Data
public class RoleDO extends SuperDO {

    private String name;

    // TODO 修改字段名
    @TableField("`desc`")
    private String desc;

    private Integer status;

}
