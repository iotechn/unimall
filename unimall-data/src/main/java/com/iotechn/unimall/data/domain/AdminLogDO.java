package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.support.domain.SuperDO;
import lombok.Data;

import java.util.Date;

/**
 * Description: 管理员操作日志
 * User: rize
 * Date: 2020/8/11
 * Time: 15:44
 */
@Data
@TableName("unimall_admin_log")
@ApiEntity(description = "管理员日志对象")
public class AdminLogDO extends SuperDO {

    @ApiField(description = "管理员ID")
    private Long adminId;

    @ApiField(description = "请求ID")
    private Long requestId;

    @ApiField(description = "API 分组")
    private String apiGroup;

    @ApiField(description = "API 方法")
    private String apiMethod;

}
