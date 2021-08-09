package com.iotechn.unimall.biz.client.handler;

import java.math.BigDecimal;

public class DobbinErpStockChangeHandlerImpl implements ErpStockChangeHandler {

    @Override
    public boolean onStockChange(String barcode, String locationCode, BigDecimal stock) {
        return false;
    }

}
