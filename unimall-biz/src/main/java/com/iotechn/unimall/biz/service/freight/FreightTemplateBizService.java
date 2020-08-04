package com.iotechn.unimall.biz.service.freight;

import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.dto.freight.ShipTraceDTO;
import com.iotechn.unimall.data.mapper.FreightTemplateCarriageMapper;
import com.iotechn.unimall.data.mapper.FreightTemplateMapper;
import com.iotechn.unimall.data.model.FreightCalcModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-07
 * Time: 下午7:48
 */
@Service
public class FreightTemplateBizService {

    @Autowired
    private FreightTemplateMapper freightTemplateMapper;

    @Autowired
    private FreightTemplateCarriageMapper freightTemplateCarriageMapper;

    @Autowired
    private ShipTraceQuery shipTraceQuery;

    public int computePostage(FreightCalcModel freightCalcModel) throws ServiceException {
        // TODO 实现运费计算
        return 0;
    }

    public ShipTraceDTO getShipTraceList(String shipNo, String shipCode) throws ServiceException {
        return shipTraceQuery.query(shipNo, shipCode);
    }
}
