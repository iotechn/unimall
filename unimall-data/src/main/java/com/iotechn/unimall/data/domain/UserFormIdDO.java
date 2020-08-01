package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description: 小程序提交上来的formId，用来创建模板消息
 * User: rize
 * Date: 2019/10/29
 * Time: 23:18
 */
@Data
@TableName("unimall_user_form_id")
public class UserFormIdDO extends SuperDO {

    @TableField("user_id")
    private Long userId;

    private String openid;

    @TableField("form_id")
    private String formId;


}
