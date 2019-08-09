package com.iotechn.unimall.data.dto;

import com.iotechn.unimall.data.dto.SuperDTO;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-08
 * Time: 下午3:30
 */
@Data
public class RecommendDTO extends SuperDTO {

    private Integer recommendType;

    private Long spuId;

    private Integer spuOriginalPrice;

    private Integer spuPrice;

    private Integer spuVipPrice;

    private Integer spuSales;

    private String spuImg;

    private String spuTitle;

    private Long spuCategoryId;
}
