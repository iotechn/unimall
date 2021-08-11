package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.support.domain.SuperDO;
import lombok.Data;

/**
 * Created by rize on 2019/4/8.
 */
@Data
@ApiEntity(description = "角色权限表")
@TableName("unimall_role_permission")
public class RolePermissionDO extends SuperDO {

    @ApiField(description = "角色ID")
    private Long roleId;

    @ApiField(description = "权限点")
    private String permission;

}
