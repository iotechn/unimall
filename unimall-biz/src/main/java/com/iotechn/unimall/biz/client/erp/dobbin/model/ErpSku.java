package com.iotechn.unimall.biz.client.erp.dobbin.model;

import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.core.entiy.SuperDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName: ErpSkuDTO
 * Description: 商品传输模型
 *
 * @author: e-weichaozheng
 * @date: 2021-03-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ErpSku extends SuperDTO implements Serializable {

    /**
     * 内部编号
     */
    @ApiField(description = "商品编号")
    private String code;

    /**
     * 条形码
     */
    @ApiField(description = "商品条码")
    private String barCode;

    @ApiField(description = "商品名称")
    private String title;

    /**
     * 冗余主图
     */
    @ApiField(description = "商品主图")
    private String img;

    @ApiField(description = "类目ID")
    private Long categoryId;

    @ApiField(description = "类目标题")
    private String categoryTitle;

    /**
     * 商品类型 1. 普通商品 2. 服务商品
     */
    @ApiField(description = "商品类型")
    private Integer type;

    /**
     * 可空字段，型号
     */
    @ApiField(description = "型号")
    private String specification;

    @ApiField(description = "品牌标题")
    private String bandTitle;

    @ApiField(description = "产地")
    private String place;

    /************** 单位信息 START ****************/
    /**
     * 基本单位
     */
    @ApiField(description = "基本单位")
    private String unit;

    /**
     * 默认采购单位
     */
    @ApiField(description = "采购单位")
    private String purchaseUnit;

    /**
     * 默认销售单位
     */
    @ApiField(description = "销售单位")
    private String salesUnit;


    /************** 保质期 START ****************/
    /**
     * 保质期 时间（非时刻）
     */
    @ApiField(description = "保质期")
    private Integer expiration;

    /**
     * 
     */
    @ApiField(description = "过期日期类型")
    private Integer expirationType;

    /**
     * 过期预警天数
     */
    @ApiField(description = "预警天数")
    private Integer warningDays;

    /************** 库存信息 START ****************/

    /**
     * 默认仓库ID
     */
    @ApiField(description = "默认仓库ID")
    private Long locationId;

    /**
     * 默认仓库名称
     */
    @ApiField(description = "默认仓库标题")
    private String locationTitle;

    /**
     * 默认供货商
     */
    @ApiField(description = "供货商ID")
    private Long vendorId;

    /**
     * 默认供货商名称
     */
    @ApiField(description = "供货商标题")
    private String vendorTitle;

    /**
     * 默认采购员
     */
    @ApiField(description = "采购员")
    private Long buyerId;

    /**
     * 采购员名称
     */
    @ApiField(description = "采购员名")
    private String buyerTitle;

    @ApiField(description = "最低库存")
    private Integer stockMin;

    @ApiField(description = "最高库存")
    private Integer stockMax;

    /**
     * 库存预警
     */
    @ApiField(description = "库存预警")
    private Integer stockWarning;

    /**
     * 位置库存列表
     */
    @ApiField(description = "仓库库存信息")
    private List<ErpSkuLocationStock> locationStockList;

    /************** 子表信息 START ****************/

    @ApiField(description = "商品属性")
    private List<ErpSkuAttribute> attributeList;



    @ApiField(description = "价格列表")
    private List<ErpSkuPrice> priceList;

    @ApiField(description = "图片列表")
    private List<ErpSkuImg> imgList;

}
