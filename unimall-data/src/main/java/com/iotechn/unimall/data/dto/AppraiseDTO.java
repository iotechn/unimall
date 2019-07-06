package com.iotechn.unimall.data.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

/*
@author kbq
@date  2019/7/6 - 10:39
*/
@Data
public class AppraiseDTO extends SuperDTO {

    private String content;
    private Integer score;
    private String appraiseImgUrl;
    private Long userId;
    private String userNickName;
    private String userAvatarUrl;
    private Long orderId;
    private Long spuId;
    private String spuTitle;
    private Long skuId;
    private String skuBarCode;
    private String skuTitle;


}
