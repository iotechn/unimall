package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
public class AdminLogDO {

    private Long id;

    @TableField("admin_id")
    private Long adminId;

    @TableField("request_id")
    private Long requestId;

    @TableField("`group`")
    private String group;

    @TableField("`method`")
    private String method;

    @TableField("gmt_create")
    private Date gmtCreate;

}
