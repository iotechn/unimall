package com.iotechn.unimall.biz.client.erp.dobbin.handler;

import com.iotechn.unimall.biz.client.erp.handler.ErpStockChangeHandler;
import com.iotechn.unimall.biz.service.product.ProductBizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class DobbinErpStockChangeHandlerImpl implements ErpStockChangeHandler {

    @Autowired
    private ProductBizService productBizService;

    @Override
    public boolean onStockChange(String barcode, BigDecimal stock) {
        return productBizService.adjustSkuStock(barcode, stock.intValue());
    }

}
