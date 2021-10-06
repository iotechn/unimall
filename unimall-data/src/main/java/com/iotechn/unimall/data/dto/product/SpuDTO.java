package com.iotechn.unimall.data.dto.product;

import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.core.entiy.SuperDTO;
import com.dobbinsoft.fw.support.annotation.LeafTable;
import com.dobbinsoft.fw.support.model.Page;
import com.iotechn.unimall.data.domain.SkuDO;
import com.iotechn.unimall.data.domain.SpuAttributeDO;
import com.iotechn.unimall.data.domain.SpuSpecificationDO;
import com.iotechn.unimall.data.dto.CategoryDTO;
import com.iotechn.unimall.data.dto.appraise.AppraiseResponseDTO;
import com.iotechn.unimall.data.dto.freight.FreightTemplateDTO;
import com.iotechn.unimall.data.enums.SpuActivityType;
import com.iotechn.unimall.data.enums.StatusType;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by rize on 2019/7/2.
 */
@Data
@ApiEntity(description = "商品传输实体")
public class SpuDTO extends SuperDTO {

    /**
     * 商品原价
     */
    @ApiField(description = "商品原价（仅显示作用）")
    private Integer originalPrice;

    /**
     * 商品价格 单位 分
     */
    @ApiField(description = "价格（仅显示作用）")
    private Integer price;

    /**
     * 会员价格
     */
    @ApiField(description = "VIP价格（仅显示作用）")
    private Integer vipPrice;

    /**
     * 商品标题
     */
    @ApiField(description = "商品标题")
    private String title;

    /**
     * 商品销量
     */
    @ApiField(description = "当前销量")
    private Integer sales;

    /**
     * 商品主图（冗余信息）
     */
    @ApiField(description = "商品主图")
    private String img;


    /**
     * 后面的图，仅在详情接口才出现
     */
    @ApiField(description = "画廊")
    private List<String> imgList;

    /**
     * 商品详情
     */
    @ApiField(description = "富文本详情")
    private String detail;

    /**
     * 商品一句话描述
     */
    @ApiField(description = "商品描述")
    private String description;

    /**
     * 商品类目id
     */
    @ApiField(description = "所属类目ID")
    private Long categoryId;

    @ApiField(description = "类目全ID")
    private List<Long> categoryIds;

    @ApiField(description = "类目对象列表")
    private List<CategoryDTO> categoryList;

    @ApiField(description = "类目全名称")
    private String categoryFullTitle;

    /**
     * TODO 修改热评
     * 商品的第一页(前10条)评价
     */
    @ApiField(description = "第一页评论")
    private Page<AppraiseResponseDTO> appraisePage;

    @ApiField(description = "商品单位")
    private String unit;

    @ApiField(description = "运费模板ID")
    private Long freightTemplateId;

    @ApiField(description = "运费模板标题")
    private String freightTemplateTitle;

    @ApiField(description = "运费模板实体")
    private FreightTemplateDTO freightTemplate;

    /**
     * 用户是否LIKE此商品
     */
    @ApiField(description = "当前用户是否LIKE此商品")
    private Boolean favorite;

    @ApiField(description = "状态", enums = StatusType.class)
    private Integer status;

    /**
     * 商品正在参加的活动类型
     */
    @ApiField(description = "促销活动类型", enums = SpuActivityType.class)
    private Integer activityType;

    /**
     * 商品正在参加的活动Id
     */
    @ApiField(description = "促销活动ID")
    private Long activityId;

    /**
     * 商品活动开始时间
     * 若此当前时间已经超过时间，即时费用系统没有通知到商品系统，商品系统也默认此活动已经结束，可覆盖创建新的活动。
     * 若活动没有结束时间，应该将其设为巨大的值eg.Long.MAX_VALUE，否则商品系统会误判活动已经结束
     */
    @ApiField(description = "活动开始时间")
    private Date gmtActivityStart;

    /**
     * 商品活动结束时间
     * 若此当前时间已经超过时间，即时费用系统没有通知到商品系统，商品系统也默认此活动已经结束，可覆盖创建新的活动。
     * 若活动没有结束时间，应该将其设为巨大的值eg.Long.MAX_VALUE，否则商品系统会误判活动已经结束
     */
    @ApiField(description = "活动结束时间")
    private Date gmtActivityEnd;

    /**
     * Spu活动
     */
    @ApiField(description = "活动附加对象")
    private Object activity;


    /*************************** 直接子表 *********************************/

    @ApiField(description = "属性列表")
    private List<SpuAttributeDO> attributeList;

    /**
     * 商品规格枚举列表
     */
    @ApiField(description = "商品规格枚举列表")
    private List<SpuSpecificationDO> specificationList;

    @ApiField(description = "商品列表")
    private List<SkuDO> skuList;

}
