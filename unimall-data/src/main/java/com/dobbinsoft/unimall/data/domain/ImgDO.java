package com.dobbinsoft.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.core.annotation.param.NotNull;
import com.dobbinsoft.fw.support.domain.SuperDO;
import com.dobbinsoft.unimall.data.enums.BizType;
import lombok.Data;

/**
 * Created by rize on 2019/2/13.
 */
@Data
@ApiEntity(description = "图片表")
@TableName("unimall_img")
public class ImgDO extends SuperDO {

    @NotNull
    @ApiField(description = "图片关联类型", enums = BizType.class)
    private Integer bizType;

    @NotNull
    @ApiField(description = "图片关联ID")
    private Long bizId;

    @NotNull
    private String url;

}
