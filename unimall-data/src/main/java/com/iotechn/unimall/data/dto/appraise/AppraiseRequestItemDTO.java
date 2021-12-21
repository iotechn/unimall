package com.iotechn.unimall.data.dto.appraise;


import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.core.entiy.SuperDTO;
import lombok.Data;

/*
@author kbq
@date  2019/7/6 - 14:38
*/
/*
* 订单评价时，用来存储每种商品时的数据结构
* */
@Data
@ApiEntity(description = "Item请求实体")
public class AppraiseRequestItemDTO extends SuperDTO {

    @ApiField(description = "商品ID")
    private Long spuId;

    @ApiField(description = "商品规格ID")
    private Long skuId;

    /**
     * 冗余信息
     */
    @ApiField(description = "订单ID")
    private Long orderId;

    @ApiField(description = "用户ID")
    private Long userId;

    /**
     * 以,分隔的图片路径。
     */
    @ApiField(description = "图片URL")
    private String imgUrl;

    /**
     * 评论内容
     */
    @ApiField(description = "内容")
    private String content;

    /**
     * 评论星数
     */
    @ApiField(description = "评论分数")
    private Integer score;

}
