package com.dobbinsoft.unimall.biz.service.freight;

import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.unimall.data.dto.freight.ShipTraceDTO;

/**
 * Created by rize on 2019/7/10.
 */
public interface ShipTraceQuery {

    public ShipTraceDTO query(String shipNo, String shipCode) throws ServiceException;

}
