package com.iotechn.unimall.biz.handler;


import com.dobbinsoft.fw.support.mq.DelayedMessageHandler;
import com.iotechn.unimall.biz.service.vip.VipOrderBizService;
import com.iotechn.unimall.data.domain.VipOrderDO;
import com.iotechn.unimall.data.enums.DMQHandlerType;
import com.iotechn.unimall.data.enums.VipOrderStatusType;
import com.iotechn.unimall.data.mapper.VipOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

public class VipOrderBuyOverHandler implements DelayedMessageHandler {

    @Autowired
    private VipOrderMapper vipOrderMapper;

    @Autowired
    private VipOrderBizService vipOrderBizService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Override
    public int handle(String value) {
        Integer res = transactionTemplate.execute(status -> {
            try {
                Long id = Long.valueOf(value);
                VipOrderDO vipOrderDO = vipOrderMapper.selectById(id);
                vipOrderBizService.changeOrderParentStatus(vipOrderDO.getId(), VipOrderStatusType.BUY_OVER.getCode(), VipOrderStatusType.WAIT_REFUND.getCode());
            } catch (Exception e) {
                status.setRollbackOnly();
                return 0;
            }
            return 1;
        });
        return res;
    }

    @Override
    public int getCode() {
        return DMQHandlerType.VIP_ORDER_BUY_OVER.getCode();
    }
}
