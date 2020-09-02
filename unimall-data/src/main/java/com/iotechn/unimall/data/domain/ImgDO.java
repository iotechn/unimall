package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.security.KeyStore;
import java.util.HashMap;

/**
 * Created by rize on 2019/2/13.
 */
@Data
@TableName("unimall_img")
public class ImgDO extends SuperDO {

    @TableField("biz_type")
    private Integer bizType;

    @TableField("biz_id")
    private Long bizId;

    private String url;


}
