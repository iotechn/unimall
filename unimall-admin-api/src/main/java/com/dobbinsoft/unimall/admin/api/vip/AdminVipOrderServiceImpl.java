package com.dobbinsoft.unimall.admin.api.vip;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.pay.model.request.MatrixPayRefundRequest;
import com.dobbinsoft.fw.pay.model.result.MatrixPayRefundResult;
import com.dobbinsoft.fw.pay.service.pay.MatrixPayService;
import com.dobbinsoft.fw.support.component.LockComponent;
import com.dobbinsoft.fw.support.model.Page;
import com.dobbinsoft.unimall.biz.service.vip.VipOrderBizService;
import com.dobbinsoft.unimall.data.constant.LockConst;
import com.dobbinsoft.unimall.data.domain.VipOrderDO;
import com.dobbinsoft.unimall.data.enums.VipOrderStatusType;
import com.dobbinsoft.unimall.data.exception.ExceptionDefinition;
import com.dobbinsoft.unimall.data.mapper.VipOrderMapper;
import com.dobbinsoft.unimall.data.properties.UnimallWxAppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AdminVipOrderServiceImpl implements AdminVipOrderService {

    private static final Logger logger = LoggerFactory.getLogger(AdminVipOrderServiceImpl.class);

    @Autowired
    private VipOrderMapper vipOrderMapper;
    @Autowired
    private VipOrderBizService vipOrderBizService;
    @Autowired
    private LockComponent lockComponent;
    @Autowired
    private UnimallWxAppProperties unimallWxAppProperties;
    @Autowired
    private MatrixPayService matrixPayService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String refund(Long id, Long adminId) throws ServiceException {
        VipOrderDO vipOrderDO = vipOrderMapper.selectById(id);
        if (vipOrderDO.getStatus().intValue() != VipOrderStatusType.WAIT_REFUND.getCode()) {
            throw new ServiceException(ExceptionDefinition.VIP_ORDER_STATUS_ERROR);
        }
        if (vipOrderDO.getGmtPay().plusDays(7).isBefore(LocalDateTime.now())) {
            throw new ServiceException(ExceptionDefinition.VIP_ORDER_REFUND_TIME_EXPIRED);
        }
        vipOrderBizService.changeOrderParentStatus(id, VipOrderStatusType.WAIT_REFUND.getCode(), VipOrderStatusType.REFUND_OVER.getCode());
        String lockKey = LockConst.VIP_ORDER_REFUND_LOCK + id;
        if (lockComponent.tryLock(lockKey, 30)) {
            try {
                //2.2.2 向支付平台发送退款请求
                MatrixPayRefundRequest matrixPayRefundRequest = new MatrixPayRefundRequest();
                matrixPayRefundRequest.setAppid(unimallWxAppProperties.getMiniAppId());
                matrixPayRefundRequest.setOutTradeNo(vipOrderDO.getOrderNo());
                matrixPayRefundRequest.setOutRefundNo("refund_VIP_" + vipOrderDO.getOrderNo());
                matrixPayRefundRequest.setTotalFee(vipOrderDO.getPrice());
                matrixPayRefundRequest.setRefundFee(vipOrderDO.getPrice());
                MatrixPayRefundResult matrixPayRefundResult = matrixPayService.refund(matrixPayRefundRequest);
                if (!matrixPayRefundResult.getReturnCode().equals("SUCCESS")) {
                    logger.warn("[退款] 失败 : " + matrixPayRefundResult.getReturnMsg());
                    throw new ServiceException(matrixPayRefundResult.getReturnMsg(),
                            ExceptionDefinition.THIRD_PART_SERVICE_EXCEPTION.getCode());
                }
                if (!matrixPayRefundResult.getResultCode().equals("SUCCESS")) {
                    logger.warn("[退款] 失败 : " + matrixPayRefundResult.getReturnMsg());
                    throw new ServiceException(matrixPayRefundResult.getReturnMsg(),
                            ExceptionDefinition.THIRD_PART_SERVICE_EXCEPTION.getCode());
                }
                return "ok";

            } catch (ServiceException e) {
                throw e;
            } catch (Exception e) {
                logger.error("[退款] 异常", e);
                throw new ServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
            } finally {
                lockComponent.release(lockKey);
            }
        } else {
            throw new ServiceException(ExceptionDefinition.SYSTEM_BUSY);
        }
    }

    @Override
    public Page<VipOrderDO> list(String orderNo, Integer status, String phone, Long templateId, Integer pageNo, Integer limit, Long adminId) throws ServiceException {
        QueryWrapper<VipOrderDO> wrapper = new QueryWrapper<>();
        if (orderNo != null) {
            wrapper.eq("order_no", orderNo);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        if (phone != null) {
            wrapper.eq("phone", phone);
        }
        if (templateId != null) {
            wrapper.eq("template_id", templateId);
        }
        wrapper.orderByDesc("id");
        return vipOrderMapper.selectPage(Page.div(pageNo, limit, VipOrderDO.class), wrapper);
    }
}
