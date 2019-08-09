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
 * Date: 2019-07-08
 * Time: 下午6:50
 */
@Data
@TableName("unimall_advertisement")
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementDO extends SuperDO {

    @TableField("ad_type")
    private Integer adType;

    private String title;

    private String url;

    @TableField("img_url")
    private String imgUrl;

    private Integer status;

    private String color;
}
