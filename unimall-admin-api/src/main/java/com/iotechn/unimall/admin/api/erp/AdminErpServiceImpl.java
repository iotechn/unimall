package com.iotechn.unimall.admin.api.erp;

import com.dobbinsoft.fw.core.exception.ServiceException;
import com.iotechn.unimall.biz.client.erp.ErpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminErpServiceImpl implements AdminErpService {

    @Autowired
    private ErpClient erpClient;

    @Override
    public String syncCategory(Long adminId) throws ServiceException {
        erpClient.syncCategories();
        return "ok";
    }

    @Override
    public String syncProduct(Long adminId) throws ServiceException {
        erpClient.syncProducts();
        return "ok";
    }

}
