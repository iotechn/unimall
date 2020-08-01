package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description: 字段映射模型
 * User: rize
 * Date: 2020/3/11
 * Time: 11:11
 */
@Data
public class GeneratorTableColumnDO {

    @TableField("Field")
    private String field;

    @TableField("Type")
    private String type;

    private String Null;

}
