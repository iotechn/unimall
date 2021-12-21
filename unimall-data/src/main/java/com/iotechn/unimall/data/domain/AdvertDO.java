package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.support.domain.SuperDO;
import com.iotechn.unimall.data.enums.AdvertType;
import com.iotechn.unimall.data.enums.AdvertUnionType;
import com.iotechn.unimall.data.enums.StatusType;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-08
 * Time: 下午6:50
 */
@Data
@ApiEntity(description = "广告领域模型")
@TableName("unimall_advert")
public class AdvertDO extends SuperDO {

    @ApiField(description = "类型", enums = AdvertType.class)
    private Integer type;

    /**
     * 广告关联类型
     */
    @ApiField(description = "关联类型", enums = AdvertUnionType.class)
    private Integer unionType;

    @ApiField(description = "广告标题")
    private String title;

    /**
     * 广告关联值，可以是商品、类目、页面等
     */
    @ApiField(description = "关联值与关联类型联合起来才有意义 可以是商品、类目、页面等 ")
    private String unionValue;

    @ApiField(description = "广告图片")
    private String imgUrl;

    @ApiField(description = "状态", enums = StatusType.class)
    private Integer status;

    @ApiField(description = "广告的主色调,默认通过取颜色平均值获取")
    private String color;
}
