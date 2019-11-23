package com.iotechn.unimall.data.dto.appraise;


import com.iotechn.unimall.data.dto.SuperDTO;
import lombok.Data;

/*
@author kbq
@date  2019/7/6 - 14:38
*/
/*
* 订单评价时，用来存储每种商品时的数据结构
* */
@Data
public class AppraiseRequestItemDTO extends SuperDTO {

    private Long spuId;

    private Long skuId;
    /**
     * 冗余信息
     */
    private Long orderId;

    private Long userId;

    /**
     * 以,分隔的图片路径。
     */
    private String imgUrl;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论星数
     */
    private Integer score;
}
