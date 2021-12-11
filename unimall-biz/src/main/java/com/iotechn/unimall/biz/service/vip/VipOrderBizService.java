package com.iotechn.unimall.biz.service.vip;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dobbinsoft.fw.core.exception.BizServiceException;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.support.component.LockComponent;
import com.iotechn.unimall.data.constant.LockConst;
import com.iotechn.unimall.data.domain.VipOrderDO;
import com.iotechn.unimall.data.exception.ExceptionDefinition;
import com.iotechn.unimall.data.mapper.VipOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class VipOrderBizService {

    @Autowired
    private VipOrderMapper vipOrderMapper;

    @Autowired
    private LockComponent lockComponent;

    public boolean changeOrderParentStatus(Long id, int nowStatus, int oldStatus) throws ServiceException {
        String lockKey = LockConst.VIP_ORDER_STATUS_LOCK + id;
        try {
            if (lockComponent.tryLock(lockKey, 30)) {
                VipOrderDO update = new VipOrderDO();
                update.setId(id);
                update.setStatus(nowStatus);
                update.setGmtUpdate(new Date());
                int updateRes = vipOrderMapper.update(update,
                        new QueryWrapper<VipOrderDO>()
                                .eq("id", id)
                                .eq("status", oldStatus));
                if (updateRes > 0) {
                    return true;
                }
                throw new BizServiceException(ExceptionDefinition.VIP_ORDER_STATUS_CHANGE_FAILED);
            } else {
                throw new BizServiceException(ExceptionDefinition.VIP_ORDER_SYSTEM_BUSY);
            }
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new BizServiceException(ExceptionDefinition.VIP_ORDER_UNKNOWN_EXCEPTION);
        } finally {
            lockComponent.release(lockKey);
        }
    }
}
