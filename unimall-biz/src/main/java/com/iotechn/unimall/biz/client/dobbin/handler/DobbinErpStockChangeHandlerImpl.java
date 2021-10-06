package com.iotechn.unimall.biz.client.dobbin.handler;

import com.iotechn.unimall.biz.client.handler.ErpStockChangeHandler;

import java.math.BigDecimal;


public class DobbinErpStockChangeHandlerImpl implements ErpStockChangeHandler {

    @Override
    public boolean onStockChange(String barcode, BigDecimal stock) {
        return false;
    }

}
