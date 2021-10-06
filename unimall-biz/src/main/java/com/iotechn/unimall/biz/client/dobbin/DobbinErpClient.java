package com.iotechn.unimall.biz.client.dobbin;

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
import com.iotechn.unimall.biz.client.ErpClient;
import com.iotechn.unimall.biz.client.dobbin.model.*;
import com.iotechn.unimall.biz.client.handler.ErpStockChangeHandler;
import com.iotechn.unimall.biz.service.product.ProductBizService;
import com.iotechn.unimall.data.domain.CategoryDO;
import com.iotechn.unimall.data.domain.SkuDO;
import com.iotechn.unimall.data.dto.order.OrderDTO;
import com.iotechn.unimall.data.dto.product.AdminSpuDTO;
import com.iotechn.unimall.data.enums.CategoryLevelType;
import com.iotechn.unimall.data.mapper.CategoryMapper;
import com.iotechn.unimall.data.mapper.SkuMapper;
import com.iotechn.unimall.data.properties.UnimallErpOpenPlatformProperties;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DobbinErpClient implements ErpClient {

    private OkHttpClient okHttpClient = new OkHttpClient();

    private static final String ERP_GW = "https://console.dobbinsoft.com/erp/m.api";

    private volatile ErpStockChangeHandler erpStockChangeHandler;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private ProductBizService productBizService;

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
                            .post(requestBody).build())
                    .execute().body().string();
            List<ErpCategory> erpCategories = this.getListFromJson(json, ErpCategory.class);
            List<CategoryDO> sysCategories = categoryMapper.selectList(new QueryWrapper<CategoryDO>().isNotNull("third_id"));
            Map<Long, CategoryDO> sysCategoryMap = sysCategories.stream().collect(Collectors.toMap(k -> {
                return Long.parseLong(k.getThirdId());
            }, v -> v));
            for (ErpCategory firstCategory : erpCategories) {
                CategoryDO sysFirstCategory = sysCategoryMap.get(firstCategory.getId());
                if (sysFirstCategory != null) {
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
                categoryMapper.insert(newSysSecondCategory);
            }
        }
    }

    @Override
    public boolean syncProducts() throws ServiceException {
        Map<String, String> listParams = new HashMap<>();
        listParams.put("_gp", "admin.erpsku");
        listParams.put("_mt", "list");
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
                                .post(listRequestBody)
                                .build())
                        .execute().body().string();
                Page<ErpSku> page = this.getObjectFromJson(listJson, new TypeReference<Page<ErpSku>>() {
                });
                List<ErpSku> items = page.getItems();
                for (ErpSku erpSku : items) {
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
                                        .post(detailRequestBody)
                                        .build())
                                .execute().body().string();
                        // 查看是否存在该商品，若存在，则走更新流程
                        ErpSku detailSku = this.getObjectFromJson(detailJson, ErpSku.class);
                        SkuDO skuDO = skuMapper.selectOne(new QueryWrapper<SkuDO>().eq("bar_code", detailSku.getBarCode()));
                        // TODO 同步信息
                        AdminSpuDTO adminSpuDTO = new AdminSpuDTO();
                        if (skuDO != null) {
                            adminSpuDTO.setId(skuDO.getSpuId());
                        }
                        productBizService.create(adminSpuDTO);
                    }
                }
                if (!page.hasNext()) {
                    nextPage = false;
                }
                pageNo++;
            } while (nextPage);
            logger.info("[开放ERP 同步商品] 成功！");
            return true;
        } catch (IOException e) {
            throw new ThirdPartServiceException(CoreExceptionDefinition.THIRD_PART_IO_EXCEPTION);
        }
    }

    @Override
    public boolean takeSalesHeader(OrderDTO dto) throws ServiceException {
        ErpSalesHeader erpSalesHeader = new ErpSalesHeader();
        erpSalesHeader.setPlaceCode(dto.getOrderNo());
        erpSalesHeader.setRecvConsignee(dto.getConsignee());
        erpSalesHeader.setRecvAddress(dto.getProvince() + " " + dto.getCity() + " " + dto.getCounty());
        erpSalesHeader.setRecvPhone(dto.getPhone());
        erpSalesHeader.setTotal(dto.getActualPrice());
        erpSalesHeader.setDeposit(0);
        erpSalesHeader.setGmtSales(dto.getGmtCreate());
        erpSalesHeader.setMono(dto.getMono());
        erpSalesHeader.setTaxRate(0);
        List<ErpSalesHeaderSku> skuList = dto.getSkuList().stream().map(item -> {
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
                            .post(listRequestBody)
                            .build()).execute().body().string();
            getObjectFromJson(json);
            logger.info("[开放ERP 下发销售单] 成功！");
            return true;
        } catch (IOException e) {
            throw new ThirdPartServiceException(CoreExceptionDefinition.THIRD_PART_IO_EXCEPTION);
        }
    }

    @Override
    public boolean takeStockReturnOrder(OrderDTO dto) throws ServiceException {
        ErpPlaceRefund erpPlaceRefund = new ErpPlaceRefund();
        erpPlaceRefund.setPlaceCode(dto.getOrderNo());
        List<ErpPlaceRefundSku> skuList = dto.getSkuList().stream().map(item -> {
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
                            .post(listRequestBody)
                            .build()).execute().body().string();
            getObjectFromJson(json);
            logger.info("[开放ERP 下发退款] 成功！");
            return true;
        } catch (IOException e) {
            throw new ThirdPartServiceException(CoreExceptionDefinition.THIRD_PART_IO_EXCEPTION);
        }
    }

    @Override
    public void invokeStockChange(String barcode, BigDecimal stock) {
        this.erpStockChangeHandler.onStockChange(barcode, stock);
    }

    @Override
    public void registerStockChangeHandler(ErpStockChangeHandler handler) {
        this.erpStockChangeHandler = handler;
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
