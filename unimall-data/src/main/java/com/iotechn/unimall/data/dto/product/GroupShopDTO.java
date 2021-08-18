package com.iotechn.unimall.data.dto.product;

import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.core.entiy.SuperDTO;
import com.iotechn.unimall.data.enums.GroupShopAutomaticRefundType;
import com.iotechn.unimall.data.enums.StatusType;
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
@ApiEntity(description = "团购商品传输实体")
public class GroupShopDTO extends SuperDTO {

    @ApiField(description = "商品ID")
    private Long spuId;

    @ApiField(description = "最低价格")
    private Integer minPrice;

    @ApiField(description = "最好价格")
    private Integer maxPrice;

    @ApiField(description = "活动开始时间")
    private Date gmtStart;

    @ApiField(description = "活动结束时间")
    private Date gmtEnd;

    @ApiField(description = "团购最小人数")
    private Integer minNum;

    @ApiField(description = "当前已付款人数")
    private Integer buyerNum;

    @ApiField(description = "团购人数未满执行策略", enums = GroupShopAutomaticRefundType.class)
    private Integer automaticRefund;

    /**
     * TODO 删除 DTO GroupShopSkuDTO列表
     */
    @ApiField(description = "团购商品列表")
    private List<GroupShopSkuDTO> groupShopSkuDTOList;

    /**
     * spu属性
     */
    @ApiField(description = "商品原价（仅显示作用）")
    private Integer originalPrice;

    @ApiField(description = "价格（仅显示作用）")
    private Integer price;

    @ApiField(description = "VIP价格（仅显示作用）")
    private Integer vipPrice;

    @ApiField(description = "商品标题")
    private String title;

    @ApiField(description = "当前销量")
    private Integer sales;

    @ApiField(description = "商品主图")
    private String img;

    @ApiField(description = "富文本详情")
    private String detail;

    @ApiField(description = "商品描述")
    private String description;

    @ApiField(description = "所属类目ID")
    private Long categoryId;

    @ApiField(description = "运费模板ID")
    private Long freightTemplateId;

    @ApiField(description = "计量单位")
    private String unit;

    @ApiField(description = "状态", enums = StatusType.class)
    private Integer status;

}
