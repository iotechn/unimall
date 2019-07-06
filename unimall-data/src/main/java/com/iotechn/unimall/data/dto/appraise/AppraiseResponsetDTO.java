package com.iotechn.unimall.data.dto.appraise;

import com.baomidou.mybatisplus.annotations.TableField;
import com.iotechn.unimall.data.dto.SuperDTO;
import lombok.Data;

/*
@author kbq
@date  2019/7/6 - 10:39
*/

/*
* 向前端传出评价信息的数据结构
* */

@Data
public class AppraiseResponsetDTO extends SuperDTO {

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
    private String skuTitle;



}
