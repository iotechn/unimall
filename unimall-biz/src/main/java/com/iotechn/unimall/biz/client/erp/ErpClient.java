package com.iotechn.unimall.biz.client.erp;

import com.dobbinsoft.fw.core.exception.ServiceException;
import com.iotechn.unimall.data.dto.order.OrderDTO;

import java.math.BigDecimal;
import java.util.List;

/**
 * Erp对接客户端
 * 职能：
 * 1. 通过ERP同步类目、商品
 * 2. 订单支付成功后，向ERP销售制单 & 销售出库 & 销售收款单
 * 3. 订单退货，向ERP下退货单 & 销售退款单
 *
 * 4. 接受ERP通知：
 * 4.1. 接受ERP库存更改通知
 */
public interface ErpClient {

    /**
     * 从ERP同步类目
     * @return
     */
    public boolean syncCategories() throws ServiceException;

    /**
     * 从ERP同步商品
     * 返回同步结果信息
     * @return
     */
    public List<String> syncProducts() throws ServiceException;

    /**
     * 下销售制单 & 销售出库 & 销售收款
     * @return
     */
    public void takeSalesHeader(String orderNo) throws ServiceException;

    /**
     * 下退货入库单 & 销售退款单
     * 当在商户后台点确认的时候，需要STOCK RETURN退货单，来补充库存
     * @return
     */
    public void takeStockReturnOrder(String orderNo) throws ServiceException;

    /**
     * 调用库存更改（同步）
     */
    public void invokeStockChange(String barcode, BigDecimal stock);


}
