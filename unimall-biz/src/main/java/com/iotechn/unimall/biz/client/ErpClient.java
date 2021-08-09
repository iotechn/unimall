package com.iotechn.unimall.biz.client;

import com.iotechn.unimall.biz.client.handler.ErpStockChangeHandler;
import com.iotechn.unimall.data.dto.order.OrderDTO;

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
    public boolean syncCategories();

    /**
     * 从ERP同步商品
     * @return
     */
    public boolean syncProducts();

    /**
     * 下销售制单 & 销售出库 & 销售收款
     * @return
     */
    public boolean takeSalesHeader(OrderDTO dto);

    /**
     * 下退货入库单 & 销售退款单
     * @return
     */
    public boolean takeStockReturnOrder(OrderDTO dto);

    /**
     * 注册库存改变事件通知其
     */
    public void registerStockChangeHandler(ErpStockChangeHandler handler);

}
