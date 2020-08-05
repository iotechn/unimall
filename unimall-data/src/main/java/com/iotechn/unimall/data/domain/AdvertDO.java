package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-08
 * Time: 下午6:50
 */
@Data
@TableName("unimall_advert")
public class AdvertDO extends SuperDO {

    @TableField("type")
    private Integer type;

    /**
     * 广告关联类型
     */
    @TableField("union_type")
    private Integer unionType;

    private String title;

    /**
     * 广告关联值，可以是商品、类目、页面等
     */
    @TableField("union_value")
    private String unionValue;

    @TableField("img_url")
    private String imgUrl;

    private Integer status;

    private String color;
}
