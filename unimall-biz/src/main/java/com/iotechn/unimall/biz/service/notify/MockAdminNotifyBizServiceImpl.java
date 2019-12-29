package com.iotechn.unimall.biz.service.notify;

import com.alibaba.fastjson.JSONObject;
import com.iotechn.unimall.data.dto.order.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * Description: 为了能够正常启动demo的mock实现
 * User: rize
 * Date: 2019/12/27
 * Time: 16:22
 */
public class MockAdminNotifyBizServiceImpl implements AdminNotifyBizService {

    private static final Logger logger = LoggerFactory.getLogger(MockAdminNotifyBizServiceImpl.class);

    @Override
    public void newOrder(OrderDTO orderDTO) {
        logger.info("[mock通知 有新订单] 请及时发货：" + JSONObject.toJSONString(orderDTO));
    }

    @Override
    public void refundOrder(OrderDTO orderDTO) {
        logger.info("[mock通知 有新退款] 请及时处理：" + JSONObject.toJSONString(orderDTO));
    }
}
