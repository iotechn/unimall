package com.dobbinsoft.unimall.biz.client.erp.dobbin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.support.utils.JacksonUtil;
import com.dobbinsoft.unimall.biz.client.erp.ErpClient;
import com.dobbinsoft.unimall.biz.client.erp.dobbin.model.ErpPlaceRefund;
import com.dobbinsoft.unimall.biz.client.erp.dobbin.model.ErpPlaceRefundSku;
import com.dobbinsoft.unimall.biz.client.erp.dobbin.model.ErpSalesHeader;
import com.dobbinsoft.unimall.biz.client.erp.dobbin.model.ErpSalesHeaderSku;
import com.dobbinsoft.unimall.biz.client.erp.handler.ErpStockChangeHandler;
import com.dobbinsoft.unimall.data.domain.OrderDO;
import com.dobbinsoft.unimall.data.domain.OrderSkuDO;
import com.dobbinsoft.unimall.data.mapper.OrderMapper;
import com.dobbinsoft.unimall.data.mapper.OrderSkuMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class MockErpClient implements ErpClient {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderSkuMapper orderSkuMapper;

    @Autowired
    private ErpStockChangeHandler erpStockChangeHandler;

    @Override
    public boolean syncCategories() throws ServiceException {
        log.info("[ERP同步类目] 成功！");
        return true;
    }

    @Override
    public List<String> syncProducts() throws ServiceException {
        log.info("[ERP同步产品] 成功！");
        return Collections.emptyList();
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
        log.info("[ERP销售制单] mock request={}", JacksonUtil.toJSONString(erpSalesHeader));
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
        log.info("[ERP销售退单] mock request={}", JacksonUtil.toJSONString(erpPlaceRefund));
    }

    @Override
    public void invokeStockChange(String barcode, BigDecimal stock) {
        this.erpStockChangeHandler.onStockChange(barcode, stock);
    }

}
