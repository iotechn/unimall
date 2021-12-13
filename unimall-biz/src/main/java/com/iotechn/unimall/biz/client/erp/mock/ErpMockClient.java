package com.iotechn.unimall.biz.client.erp.mock;

import com.dobbinsoft.fw.core.exception.ServiceException;
import com.iotechn.unimall.biz.client.erp.ErpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;


public class ErpMockClient implements ErpClient {

    private static final Logger logger = LoggerFactory.getLogger(ErpMockClient.class);

    @Override
    public boolean syncCategories() throws ServiceException {
        return false;
    }

    @Override
    public List<String> syncProducts() throws ServiceException {
        return null;
    }

    @Override
    public void takeSalesHeader(String orderNo) throws ServiceException {
        logger.info("[模拟ERP] 销售制单: " + orderNo);
    }

    @Override
    public void takeStockReturnOrder(String orderNo) throws ServiceException {
        logger.info("[模拟ERP] 退货制单: " + orderNo);
    }

    @Override
    public void invokeStockChange(String barcode, BigDecimal stock) {

    }
}
