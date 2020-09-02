package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 * Created by rize on 2019/6/30.
 */
@Data
public class SuperDO {

    private Long id;

    @TableField("gmt_update")
    private Date gmtUpdate;

    @TableField("gmt_create")
    private Date gmtCreate;

}
