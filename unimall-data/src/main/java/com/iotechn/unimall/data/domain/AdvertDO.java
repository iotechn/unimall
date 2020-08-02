package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-08
 * Time: 下午6:50
 */
@Data
@TableName("unimall_advert")
@NoArgsConstructor
@AllArgsConstructor
public class AdvertDO extends SuperDO {

    @TableField("ad_type")
    private Integer adType;

    private String title;

    private String url;

    @TableField("img_url")
    private String imgUrl;

    private Integer status;

    private String color;
}
