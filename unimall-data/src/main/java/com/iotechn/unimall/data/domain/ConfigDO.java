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
 * Date: 2019-07-20
 * Time: 上午10:15
 */

@Data
@TableName("unimall_config")
@AllArgsConstructor
@NoArgsConstructor
public class ConfigDO extends SuperDO {

    @TableField("key_word")
    private String keyWord;

    @TableField("value_worth")
    private String valueWorth;
}
