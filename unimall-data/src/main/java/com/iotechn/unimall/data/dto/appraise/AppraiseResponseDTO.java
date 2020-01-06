package com.iotechn.unimall.data.dto.appraise;

import com.iotechn.unimall.data.dto.SuperDTO;
import lombok.Data;

import java.util.List;

/*
向前端传出评价信息的数据结构
@author kbq
@date  2019/7/6 - 10:39
*/
@Data
public class AppraiseResponseDTO extends SuperDTO {

    private String content;

    private Integer score;

    private List<String> imgList;

    private Long userId;

    private String userNickName;

    private String userAvatarUrl;

    private Long orderId;

    private Long spuId;

    private String spuTitle;

    private Long skuId;

    private String skuTitle;

}
