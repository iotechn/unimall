package com.iotechn.unimall.data.dto.goods;

import com.iotechn.unimall.data.dto.SuperDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
@PackageName:com.iotechn.unimall.data.dto.goods
@ClassName: GroupShopDTO
@Description:
@author kbq
@date 19-11-13下午1:36
*/

@Data
public class GroupShopDTO extends SuperDTO {

    private Long spuId;

    private Integer minPrice;

    private Integer maxPrice;

    private Date gmtStart;

    private Date gmtEnd;

    private Integer minNum;

    private Integer buyerNum;

    private Integer automaticRefund;

    /**
     * GroupShopSkuDTO列表
     */
    private List<GroupShopSkuDTO> groupShopSkuDTOList;

    /**
     * spu属性
     */
    private Integer originalPrice;

    private Integer price;

    private Integer vipPrice;

    private String title;

    private Integer sales;

    private String img;

    private String detail;

    private String description;

    private Long categoryId;

    private Long freightTemplateId;

    private String unit;

    private Integer status;
}
