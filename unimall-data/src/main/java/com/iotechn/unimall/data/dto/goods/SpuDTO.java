package com.iotechn.unimall.data.dto.goods;

import com.iotechn.unimall.data.domain.SkuDO;
import com.iotechn.unimall.data.domain.SpuAttributeDO;
import com.iotechn.unimall.data.domain.SpuSpecificationDO;
import com.iotechn.unimall.data.dto.CategoryDTO;
import com.iotechn.unimall.data.dto.SuperDTO;
import com.iotechn.unimall.data.dto.appraise.AppraiseResponseDTO;
import com.iotechn.unimall.data.dto.freight.FreightTemplateDTO;
import com.iotechn.unimall.data.model.Page;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by rize on 2019/7/2.
 */
@Data
public class SpuDTO extends SuperDTO {

    private List<SkuDO> skuList;

    private Integer originalPrice;

    private Integer price;

    private Integer vipPrice;

    private Integer stock;

    private Integer sales;

    private String title;

    /**
     * 主图
     */
    private String img;

    /**
     * 后面的图，仅在详情接口才出现
     */
    private List<String> imgList;

    private String detail;

    private String description;

    private Long categoryId;

    private List<Long> categoryIds;

    private List<CategoryDTO> categoryList;

    private List<SpuAttributeDO> attributeList;

    /**
     * 商品规格枚举列表
     */
    private List<SpuSpecificationDO> specificationList;

    /**
     * 商品的第一页(前10条)评价
     */
    private Page<AppraiseResponseDTO> appraisePage;

    private String unit;

    private Long freightTemplateId;

    private FreightTemplateDTO freightTemplate;

    /**
     * 用户是否是否LIKE此商品
     */
    private Boolean favorite;

    private Integer status;

    /**
     * 商品正在参加的活动类型
     */
    private Integer activityType;

    /**
     * 商品正在参加的活动Id
     */
    private Long activityId;

    /**
     * 商品活动开始时间
     * 若此当前时间已经超过时间，即时费用系统没有通知到商品系统，商品系统也默认此活动已经结束，可覆盖创建新的活动。
     * 若活动没有结束时间，应该将其设为巨大的值eg.Long.MAX_VALUE，否则商品系统会误判活动已经结束
     */
    private Date gmtActivityStart;

    /**
     * 商品活动结束时间
     * 若此当前时间已经超过时间，即时费用系统没有通知到商品系统，商品系统也默认此活动已经结束，可覆盖创建新的活动。
     * 若活动没有结束时间，应该将其设为巨大的值eg.Long.MAX_VALUE，否则商品系统会误判活动已经结束
     */
    private Date gmtActivityEnd;

    /**
     * Spu活动
     */
    private Object activity;

}
