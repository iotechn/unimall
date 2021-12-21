package com.iotechn.unimall.biz.client.erp.handler;

import java.math.BigDecimal;

public interface ErpStockChangeHandler {

    /**
     *
     * @param barcode 商品条码
     * @param stock 现在剩余库存
     * @return
     */
    public boolean onStockChange(String barcode, BigDecimal stock);

}
