package com.iotechn.unimall.data.dto.appraise;

import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.core.entiy.SuperDTO;
import lombok.Data;

import java.util.List;

/*
向前端传出评价信息的数据结构
@author kbq
@date  2019/7/6 - 10:39
*/
@Data
@ApiEntity(description = "评论查询响应传输模型")
public class AppraiseResponseDTO extends SuperDTO {

    @ApiField(description = "内容")
    private String content;

    @ApiField(description = "分数")
    private Integer score;

    @ApiField(description = "图片列表")
    private List<String> imgList;

    @ApiField(description = "用户ID")
    private Long userId;

    @ApiField(description = "用户昵称")
    private String userNickName;

    @ApiField(description = "用户头像")
    private String userAvatarUrl;

    @ApiField(description = "订单ID")
    private Long orderId;

    @ApiField(description = "商品ID")
    private Long spuId;

    @ApiField(description = "商品标题")
    private String spuTitle;

    @ApiField(description = "SkuId")
    private Long skuId;

    @ApiField(description = "Sku 标题")
    private String skuTitle;

}
