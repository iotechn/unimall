package com.iotechn.unimall.biz.client.erp.dobbin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dobbinsoft.fw.core.exception.BizServiceException;
import com.dobbinsoft.fw.core.exception.CoreExceptionDefinition;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.core.exception.ThirdPartServiceException;
import com.dobbinsoft.fw.support.component.open.OpenPlatformUtil;
import com.dobbinsoft.fw.support.model.Page;
import com.iotechn.unimall.biz.client.erp.ErpClient;
import com.iotechn.unimall.biz.client.erp.dobbin.model.*;
import com.iotechn.unimall.biz.client.erp.handler.ErpStockChangeHandler;
import com.iotechn.unimall.biz.service.product.ProductBizService;
import com.iotechn.unimall.data.domain.*;
import com.iotechn.unimall.data.dto.product.AdminSpuDTO;
import com.iotechn.unimall.data.enums.CategoryLevelType;
import com.iotechn.unimall.data.enums.StatusType;
import com.iotechn.unimall.data.mapper.*;
import com.iotechn.unimall.data.properties.UnimallErpOpenPlatformProperties;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class DobbinErpClient implements ErpClient {

    private OkHttpClient okHttpClient = new OkHttpClient();

    private static final String ERP_GW = "http://test.dobbinsoft.com/erp/m.api";

//    private static final String ERP_GW = "https://console.dobbinsoft.com/erp/m.api";

    private static final String TENEMENT_ID_HEADER = "TENEMENTID";

    @Autowired
    private ErpStockChangeHandler erpStockChangeHandler;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderSkuMapper orderSkuMapper;

    @Autowired
    private FreightTemplateMapper freightTemplateMapper;

    @Autowired
    private ProductBizService productBizService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private UnimallErpOpenPlatformProperties unimallErpOpenPlatformProperties;

    private static final Logger logger = LoggerFactory.getLogger(DobbinErpClient.class);

    @Override
    public boolean syncCategories() throws ServiceException {
        RequestBody requestBody = OpenPlatformUtil.buildBody("_gp=admin.erpcategory&_mt=categoryTree",
                unimallErpOpenPlatformProperties.getDobbinClientCode(),
                unimallErpOpenPlatformProperties.getDobbinClientPrivateKey());
        try {
            String json = okHttpClient.newCall(
                    new Request.Builder()
                            .url(ERP_GW)
                            .addHeader(TENEMENT_ID_HEADER, unimallErpOpenPlatformProperties.getDobbinTenementId())
                            .post(requestBody).build())
                    .execute().body().string();
            List<ErpCategory> erpCategories = this.getListFromJson(json, ErpCategory.class);
            List<CategoryDO> sysCategories = categoryMapper.selectList(new QueryWrapper<CategoryDO>().isNotNull("third_id"));
            Map<Long, CategoryDO> sysCategoryMap = sysCategories.stream().collect(Collectors.toMap(k -> Long.parseLong(k.getThirdId()), v -> v));
            for (ErpCategory firstCategory : erpCategories) {
                CategoryDO sysFirstCategory = sysCategoryMap.get(firstCategory.getId());
                if (sysFirstCategory != null) {
                    // 1. 更新一级类目
                    CategoryDO updateFirstCategory = new CategoryDO();
                    updateFirstCategory.setLevel(CategoryLevelType.ONE.getCode());
                    updateFirstCategory.setParentId(0L);
                    updateFirstCategory.setFirstLevelId(0L);
                    updateFirstCategory.setThirdId(firstCategory.getId().toString());
                    updateFirstCategory.setTitle(firstCategory.getTitle());
                    updateFirstCategory.setPicUrl(firstCategory.getImg());
                    categoryMapper.update(updateFirstCategory, new QueryWrapper<CategoryDO>().eq("third_id", firstCategory.getId()));
                    // 已同步此类目，遍历是否有未同步的二级类目
                    this.commonsSecondCategory(sysCategoryMap, firstCategory, sysFirstCategory);
                } else {
                    // 未同步类目，先同步类目
                    CategoryDO newSysFirstCategory = new CategoryDO();
                    newSysFirstCategory.setLevel(CategoryLevelType.ONE.getCode());
                    newSysFirstCategory.setParentId(0L);
                    newSysFirstCategory.setFirstLevelId(0L);
                    newSysFirstCategory.setThirdId(firstCategory.getId().toString());
                    newSysFirstCategory.setTitle(firstCategory.getTitle());
                    newSysFirstCategory.setPicUrl(firstCategory.getImg());
                    categoryMapper.insert(newSysFirstCategory);
                    // 已同步此类目，遍历是否有未同步的二级类目
                    this.commonsSecondCategory(sysCategoryMap, firstCategory, newSysFirstCategory);
                }
            }
            logger.info("[开放ERP 同步类目] 成功！");
            return true;
        } catch (IOException e) {
            throw new ThirdPartServiceException(CoreExceptionDefinition.THIRD_PART_IO_EXCEPTION);
        }
    }

    /**
     * 公共二级类目插入
     * @param sysCategoryMap
     * @param firstCategory
     * @param sysFirstCategory
     */
    private void commonsSecondCategory(Map<Long, CategoryDO> sysCategoryMap, ErpCategory firstCategory, CategoryDO sysFirstCategory) {
        List<ErpCategory> children = firstCategory.getChildren();
        for (ErpCategory secondCategory : children) {
            CategoryDO sysSecondCategory = sysCategoryMap.get(secondCategory.getId());
            if (sysSecondCategory == null) {
                CategoryDO newSysSecondCategory = new CategoryDO();
                newSysSecondCategory.setLevel(CategoryLevelType.TWO.getCode());
                newSysSecondCategory.setParentId(sysFirstCategory.getId());
                newSysSecondCategory.setFirstLevelId(sysFirstCategory.getId());
                newSysSecondCategory.setThirdId(secondCategory.getId().toString());
                newSysSecondCategory.setTitle(secondCategory.getTitle());
                newSysSecondCategory.setPicUrl(secondCategory.getImg());
                categoryMapper.insert(newSysSecondCategory);
            } else {
                CategoryDO updateSysSecondCategory = new CategoryDO();
                updateSysSecondCategory.setLevel(CategoryLevelType.TWO.getCode());
                updateSysSecondCategory.setParentId(sysFirstCategory.getId());
                updateSysSecondCategory.setFirstLevelId(sysFirstCategory.getId());
                updateSysSecondCategory.setThirdId(secondCategory.getId().toString());
                updateSysSecondCategory.setTitle(secondCategory.getTitle());
                updateSysSecondCategory.setPicUrl(secondCategory.getImg());
                categoryMapper.update(updateSysSecondCategory, new QueryWrapper<CategoryDO>().eq("third_id", secondCategory.getId()));
            }
        }
    }

    @Override
    public List<String> syncProducts() throws ServiceException {
        Map<String, String> listParams = new HashMap<>();
        listParams.put("_gp", "admin.erpsku");
        listParams.put("_mt", "list");
        List<String> resInfo = new ArrayList<>();
        try {
            int pageNo = 1;
            boolean nextPage = true;
            do {
                listParams.put("page", pageNo + "");
                RequestBody listRequestBody = OpenPlatformUtil.buildBody(this.qs(listParams),
                        unimallErpOpenPlatformProperties.getDobbinClientCode(),
                        unimallErpOpenPlatformProperties.getDobbinClientPrivateKey());
                String listJson = okHttpClient.newCall(
                        new Request.Builder()
                                .url(ERP_GW)
                                .addHeader(TENEMENT_ID_HEADER, unimallErpOpenPlatformProperties.getDobbinTenementId())
                                .post(listRequestBody)
                                .build())
                        .execute().body().string();
                Page<ErpSku> page = this.getObjectFromJson(listJson, new TypeReference<Page<ErpSku>>() {
                });
                List<ErpSku> items = page.getItems();
                inner: for (ErpSku erpSku : items) {
                    if (!ObjectUtils.isEmpty(erpSku.getBarCode())) {
                        // 请求详情，通过详情来进行同步
                        Map<String, String> detailParams = new HashMap<>();
                        detailParams.put("_gp", "admin.erpsku");
                        detailParams.put("_mt", "detail");
                        detailParams.put("id", erpSku.getId() + "");
                        RequestBody detailRequestBody = OpenPlatformUtil.buildBody(this.qs(detailParams),
                                unimallErpOpenPlatformProperties.getDobbinClientCode(),
                                unimallErpOpenPlatformProperties.getDobbinClientPrivateKey());
                        String detailJson = okHttpClient.newCall(
                                new Request.Builder()
                                        .url(ERP_GW)
                                        .addHeader(TENEMENT_ID_HEADER, unimallErpOpenPlatformProperties.getDobbinTenementId())
                                        .post(detailRequestBody)
                                        .build())
                                .execute().body().string();
                        // 查看是否存在该商品，若存在，则走更新流程
                        ErpSku detailSku = this.getObjectFromJson(detailJson, ErpSku.class);
                        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                            @Override
                            protected void doInTransactionWithoutResult(TransactionStatus status) {
                                try {
                                    SkuDO skuFromDB = skuMapper.selectOne(new QueryWrapper<SkuDO>().eq("bar_code", detailSku.getBarCode()));
                                    SpuDO spuFromDB = null;
                                    if (skuFromDB != null) {
                                        Long spuId = skuFromDB.getSpuId();
                                        Integer count = skuMapper.selectCount(new QueryWrapper<SkuDO>().eq("spu_id", spuId));
                                        spuFromDB = spuMapper.selectById(spuId);
                                        if (count > 1) {
                                            resInfo.add("放弃添加“" + detailSku.getTitle() + "”,该商品存在多个规格");
                                            return;
                                        }
                                    }
                                    // 同步基本信息
                                    AdminSpuDTO adminSpuDTO = new AdminSpuDTO();
                                    if (spuFromDB != null) {
                                        adminSpuDTO.setId(spuFromDB.getId());
                                    }
                                    adminSpuDTO.setTitle(detailSku.getTitle());
                                    FreightTemplateDO freightTemplateDO = freightTemplateMapper.selectOne(new QueryWrapper<FreightTemplateDO>().last(" limit 1"));
                                    adminSpuDTO.setFreightTemplateId(freightTemplateDO.getId());
                                    adminSpuDTO.setStatus(StatusType.ACTIVE.getCode());
                                    adminSpuDTO.setImg(detailSku.getImg());
                                    adminSpuDTO.setImgList(Arrays.asList(detailSku.getImg()));
                                    String unit = detailSku.getSalesUnit() == null ? detailSku.getUnit() : detailSku.getSalesUnit();
                                    adminSpuDTO.setUnit(unit);
                                    // 新增可空直
                                    if (skuFromDB == null) {
                                        adminSpuDTO.setCategoryId(0L);
                                        Long erpCategoryId = detailSku.getCategoryId();
                                        if (erpCategoryId != null) {
                                            CategoryDO categoryDO = categoryMapper.selectOne(new QueryWrapper<CategoryDO>().eq("third_id", erpCategoryId));
                                            if (categoryDO != null) {
                                                adminSpuDTO.setCategoryId(categoryDO.getId());
                                            }
                                        }
                                        adminSpuDTO.setDescription("暂无介绍");
                                        adminSpuDTO.setDetail("暂无详情");
                                        List<SpuAttributeDO> list = detailSku.getAttributeList().stream().map(item -> {
                                            SpuAttributeDO spuAttributeDO = new SpuAttributeDO();
                                            spuAttributeDO.setAttribute(item.getName());
                                            spuAttributeDO.setValue(item.getAttribute());
                                            return spuAttributeDO;
                                        }).collect(Collectors.toList());
                                        adminSpuDTO.setAttributeList(list);
                                        SpuSpecificationDO spuSpecificationDO = new SpuSpecificationDO();
                                        spuSpecificationDO.setTitle("规格");
                                        adminSpuDTO.setSpecificationList(Arrays.asList(spuSpecificationDO));
                                        if (skuFromDB != null) {
                                            adminSpuDTO.setId(skuFromDB.getSpuId());
                                        }
                                    } else {
                                        adminSpuDTO.setCategoryId(spuFromDB.getCategoryId());
                                        adminSpuDTO.setDescription(spuFromDB.getDescription());
                                        adminSpuDTO.setDetail(spuFromDB.getDetail());
                                        adminSpuDTO.setStatus(spuFromDB.getStatus());
                                    }
                                    List<ErpSkuPrice> priceList = detailSku.getPriceList();
                                    for (ErpSkuPrice erpSkuPrice : priceList) {
                                        if (erpSkuPrice.getUnit().equals(unit)) {
                                            if (erpSkuPrice.getSalesPrice() == null) {
                                                resInfo.add("放弃添加“" + detailSku.getTitle() + "”,ERP系统不存在销售价格");
                                                return;
                                            } else {
                                                SkuDO newSkuDO = new SkuDO();
                                                newSkuDO.setBarCode(detailSku.getBarCode());
                                                newSkuDO.setPrice(erpSkuPrice.getSalesPrice());
                                                if (skuFromDB == null) {
                                                    newSkuDO.setOriginalPrice(erpSkuPrice.getSalesPrice());
                                                    newSkuDO.setVipPrice(erpSkuPrice.getSalesPrice());
                                                    newSkuDO.setSpecification("规格_标准");
                                                    newSkuDO.setWeight(newSkuDO.getWeight() == null ? 1 : newSkuDO.getWeight());
                                                    newSkuDO.setTitle("标准");
                                                    newSkuDO.setStock(0);
                                                } else {
                                                    newSkuDO.setOriginalPrice(skuFromDB.getOriginalPrice());
                                                    newSkuDO.setVipPrice(skuFromDB.getVipPrice());
                                                    newSkuDO.setSpecification(skuFromDB.getSpecification());
                                                    newSkuDO.setWeight(skuFromDB.getWeight());
                                                    newSkuDO.setTitle(skuFromDB.getTitle());
                                                    newSkuDO.setStock(skuFromDB.getStock());
                                                }
                                                adminSpuDTO.setSkuList(Arrays.asList(newSkuDO));
                                            }
                                        }
                                    }
                                    if (skuFromDB == null) {
                                        productBizService.create(adminSpuDTO);
                                    } else {
                                        productBizService.edit(adminSpuDTO);
                                    }
                                } catch (Exception e) {
                                    logger.error("[同步SKU] 异常", e);
                                    status.setRollbackOnly();
                                }
                            }
                        });
                    }
                }
                if (!page.hasNext()) {
                    nextPage = false;
                }
                pageNo++;
            } while (nextPage);
            logger.info("[开放ERP 同步商品] 成功！");
            return resInfo;
        } catch (IOException e) {
            throw new ThirdPartServiceException(CoreExceptionDefinition.THIRD_PART_IO_EXCEPTION);
        }
    }

    @Override
    public void takeSalesHeader(String orderNo) throws ServiceException {
        OrderDO orderDO = orderMapper.selectOne(
                    new QueryWrapper<OrderDO>()
                            .eq("order_no", orderNo));
        ErpSalesHeader erpSalesHeader = new ErpSalesHeader();
        erpSalesHeader.setPlaceCode(orderDO.getOrderNo());
        erpSalesHeader.setRecvConsignee(orderDO.getConsignee());
        erpSalesHeader.setRecvAddress(orderDO.getProvince() + " " + orderDO.getCity() + " " + orderDO.getCounty());
        erpSalesHeader.setRecvPhone(orderDO.getPhone());
        erpSalesHeader.setTotal(orderDO.getActualPrice());
        erpSalesHeader.setDeposit(0);
        erpSalesHeader.setGmtSales(orderDO.getGmtCreate());
        erpSalesHeader.setMono(orderDO.getMono());
        erpSalesHeader.setTaxRate(0);
        List<OrderSkuDO> orderSkuList = orderSkuMapper.selectList(new QueryWrapper<OrderSkuDO>().eq("order_id", orderDO.getId()));
        List<ErpSalesHeaderSku> skuList = orderSkuList.stream().map(item -> {
            ErpSalesHeaderSku erpSalesHeaderSku = new ErpSalesHeaderSku();
            erpSalesHeaderSku.setBarCode(item.getBarCode());
            erpSalesHeaderSku.setUnit(item.getUnit());
            erpSalesHeaderSku.setQuantity(new BigDecimal(item.getNum()));
            erpSalesHeaderSku.setTitle(item.getSpuTitle() + " " + item.getTitle());
            erpSalesHeaderSku.setPrice(item.getPrice());
            erpSalesHeaderSku.setMono("");
            return erpSalesHeaderSku;
        }).collect(Collectors.toList());
        erpSalesHeader.setSkuList(skuList);
        Map<String, String> listParams = new HashMap<>();
        listParams.put("_gp", "admin.openplace");
        listParams.put("_mt", "create");
        listParams.put("header", JSONObject.toJSONString(erpSalesHeader));
        RequestBody listRequestBody = OpenPlatformUtil.buildBody(this.qs(listParams),
                unimallErpOpenPlatformProperties.getDobbinClientCode(),
                unimallErpOpenPlatformProperties.getDobbinClientPrivateKey());
        try {
            String json = this.okHttpClient.newCall(
                    new Request.Builder()
                            .url(ERP_GW)
                            .addHeader(TENEMENT_ID_HEADER, unimallErpOpenPlatformProperties.getDobbinTenementId())
                            .post(listRequestBody)
                            .build()).execute().body().string();
            getObjectFromJson(json);
            logger.info("[开放ERP 下发销售单] 成功！");
        } catch (IOException e) {
            throw new ThirdPartServiceException(CoreExceptionDefinition.THIRD_PART_IO_EXCEPTION);
        }
    }

    @Override
    public void takeStockReturnOrder(String orderNo) throws ServiceException {
        OrderDO orderDO = orderMapper.selectOne(
                new QueryWrapper<OrderDO>()
                        .eq("order_no", orderNo));
        ErpPlaceRefund erpPlaceRefund = new ErpPlaceRefund();
        erpPlaceRefund.setPlaceCode(orderDO.getOrderNo());
        List<OrderSkuDO> orderSkuList = orderSkuMapper.selectList(new QueryWrapper<OrderSkuDO>().eq("order_id", orderDO.getId()));
        List<ErpPlaceRefundSku> skuList = orderSkuList.stream().map(item -> {
            ErpPlaceRefundSku erpPlaceRefundSku = new ErpPlaceRefundSku();
            erpPlaceRefundSku.setBarCode(item.getBarCode());
            erpPlaceRefundSku.setQuantity(new BigDecimal(item.getNum()));
            erpPlaceRefundSku.setPrice(item.getPrice());
            erpPlaceRefundSku.setUnit(item.getUnit());
            erpPlaceRefundSku.setTitle(item.getTitle());
            return erpPlaceRefundSku;
        }).collect(Collectors.toList());
        erpPlaceRefund.setSkuList(skuList);
        Map<String, String> listParams = new HashMap<>();
        listParams.put("_gp", "admin.openplace");
        listParams.put("_mt", "refund");
        listParams.put("refund", JSONObject.toJSONString(erpPlaceRefund));
        RequestBody listRequestBody = OpenPlatformUtil.buildBody(this.qs(listParams),
                unimallErpOpenPlatformProperties.getDobbinClientCode(),
                unimallErpOpenPlatformProperties.getDobbinClientPrivateKey());
        try {
            String json = this.okHttpClient.newCall(
                    new Request.Builder()
                            .url(ERP_GW)
                            .addHeader(TENEMENT_ID_HEADER, unimallErpOpenPlatformProperties.getDobbinTenementId())
                            .post(listRequestBody)
                            .build()).execute().body().string();
            getObjectFromJson(json);
            logger.info("[开放ERP 下发退款] 成功！");
        } catch (IOException e) {
            throw new ThirdPartServiceException(CoreExceptionDefinition.THIRD_PART_IO_EXCEPTION);
        }
    }

    @Override
    public void invokeStockChange(String barcode, BigDecimal stock) {
        this.erpStockChangeHandler.onStockChange(barcode, stock);
    }


    private JSONObject getObjectFromJson(String json) throws ServiceException {
        JSONObject jsonObject = JSONObject.parseObject(json);
        Integer errno = jsonObject.getInteger("errno");
        if (errno.intValue() != 200) {
            // 抛出异常
            throw new BizServiceException(jsonObject.getString("errmsg"), jsonObject.getInteger("errno"));
        }
        return jsonObject.getJSONObject("data");
    }

    private <T> T getObjectFromJson(String json, Class<T> clazz) throws ServiceException {
        JSONObject jsonObject = JSONObject.parseObject(json);
        Integer errno = jsonObject.getInteger("errno");
        if (errno.intValue() != 200) {
            // 抛出异常
            throw new BizServiceException(jsonObject.getString("errmsg"), jsonObject.getInteger("errno"));
        }
        JSONObject data = jsonObject.getJSONObject("data");
        return data.toJavaObject(clazz);
    }

    private <T> T getObjectFromJson(String json, TypeReference<T> typeReference) throws ServiceException {
        JSONObject jsonObject = JSONObject.parseObject(json);
        Integer errno = jsonObject.getInteger("errno");
        if (errno.intValue() != 200) {
            // 抛出异常
            throw new BizServiceException(jsonObject.getString("errmsg"), jsonObject.getInteger("errno"));
        }
        JSONObject data = jsonObject.getJSONObject("data");
        return data.toJavaObject(typeReference);
    }

    private <T> List<T> getListFromJson(String json, Class<T> clazz) throws ServiceException {
        JSONObject jsonObject = JSONObject.parseObject(json);
        Integer errno = jsonObject.getInteger("errno");
        if (errno.intValue() != 200) {
            // 抛出异常
            throw new BizServiceException(jsonObject.getString("errmsg"), jsonObject.getInteger("errno"));
        }
        JSONArray data = jsonObject.getJSONArray("data");
        return data.toJavaList(clazz);
    }

    private String qs(Map<String, String> params) {
        Set<String> strings = params.keySet();
        return strings.stream().map(item -> item + "=" + params.get(item)).collect(Collectors.joining("&"));
    }

}
