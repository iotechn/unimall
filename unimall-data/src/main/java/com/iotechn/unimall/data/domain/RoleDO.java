package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.support.domain.SuperDO;
import com.iotechn.unimall.data.enums.StatusType;
import lombok.Data;

/**
 * Created by rize on 2019/4/8.
 */
@Data
@ApiEntity(description = "管理员角色领域模型")
@TableName("unimall_role")
public class RoleDO extends SuperDO {

    @ApiField(description = "角色姓名")
    private String name;

    @ApiField(description = "角色描述")
    @TableField("`desc`")
    private String desc;

    @ApiField(description = "状态", enums = StatusType.class)
    private Integer status;

}
