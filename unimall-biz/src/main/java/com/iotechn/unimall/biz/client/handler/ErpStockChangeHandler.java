package com.iotechn.unimall.biz.client.handler;

import java.math.BigDecimal;

public interface ErpStockChangeHandler {

    /**
     *
     * @param barcode 商品条码
     * @param locationCode 位置编码（用于区分仓库、门店）
     * @param stock 现在剩余库存
     * @return
     */
    public boolean onStockChange(String barcode, String locationCode, BigDecimal stock);

}
