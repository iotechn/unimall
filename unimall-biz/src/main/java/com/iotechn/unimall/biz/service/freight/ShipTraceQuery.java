package com.iotechn.unimall.biz.service.freight;

import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.dto.freight.ShipTraceDTO;

/**
 * Created by rize on 2019/7/10.
 */
public interface ShipTraceQuery {

    public ShipTraceDTO query(String shipNo, String shipCode) throws ServiceException;

}
