package com.iotechn.unimall.biz.pay;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.pay.exception.PayServiceException;
import com.dobbinsoft.fw.pay.handler.MatrixPayCallbackHandler;
import com.dobbinsoft.fw.pay.model.notify.MatrixPayNotifyResponse;
import com.dobbinsoft.fw.pay.model.notify.MatrixPayOrderNotifyResult;
import com.dobbinsoft.fw.support.mq.DelayedMessageQueue;
import com.iotechn.unimall.biz.service.user.UserBizService;
import com.iotechn.unimall.biz.service.vip.VipOrderBizService;
import com.iotechn.unimall.data.domain.VipOrderDO;
import com.iotechn.unimall.data.enums.DMQHandlerType;
import com.iotechn.unimall.data.enums.VipOrderStatusType;
import com.iotechn.unimall.data.mapper.VipOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

public class VipOrderPayCallbackHandler implements MatrixPayCallbackHandler {

    @Autowired
    private VipOrderMapper vipOrderMapper;

    @Autowired
    private DelayedMessageQueue delayedMessageQueue;

    @Autowired
    private VipOrderBizService vipOrderBizService;

    @Autowired
    private UserBizService userBizService;

    private static final Logger logger = LoggerFactory.getLogger(VipOrderPayCallbackHandler.class);

    @Override
    public void beforeCheckSign(HttpServletRequest httpServletRequest) {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object handle(MatrixPayOrderNotifyResult result, HttpServletRequest request) {
        try {
            /* 之前传过去的我们系统的订单ID */
            // 现在是不知道是父订单还是普通订单
            String orderNo = result.getOutTradeNo();
            QueryWrapper<VipOrderDO> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no", orderNo);
            List<VipOrderDO> vipOrderDOS = vipOrderMapper.selectList(wrapper);
            if (CollectionUtils.isEmpty(vipOrderDOS)) {
                return MatrixPayNotifyResponse.fail("没有该订单");
            }
            VipOrderDO vipOrderDO = vipOrderDOS.get(0);
            if (vipOrderDO.getStatus() != VipOrderStatusType.WAIT_BUY.getCode()) {
                return MatrixPayNotifyResponse.success("订单已处理");
            }

            if (result.getTotalFee().intValue() != vipOrderDO.getPrice().intValue()) {
                return MatrixPayNotifyResponse.fail("价格不一致");
            }
            logger.info("[VIP 充值回调] result=" + JSONObject.toJSONString(result));
            vipOrderBizService.changeOrderParentStatus(vipOrderDO.getId(), VipOrderStatusType.WAIT_REFUND.getCode(), VipOrderStatusType.WAIT_BUY.getCode());
            userBizService.upUserLevel(vipOrderDO);
            delayedMessageQueue.publishTask(DMQHandlerType.VIP_ORDER_BUY_OVER.getCode(), String.valueOf(vipOrderDO.getId()), 60 * 60 * 24 * 7);
            VipOrderDO update = new VipOrderDO();
            update.setId(vipOrderDO.getId());
            update.setGmtPay(new Date());
            update.setPayChannel(result.getPayChannel().getCode());
            update.setAppId(result.getAppid());
            vipOrderMapper.updateById(update);
            return MatrixPayNotifyResponse.success("支付成功");
        } catch (ServiceException e) {
            throw new PayServiceException(e.getMessage());
        }
    }
}
