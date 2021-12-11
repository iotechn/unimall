package com.iotechn.unimall.runner.controller;

import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rize on 2019/7/10.
 */
@RestController
@RequestMapping("/cb")
public class CallbackController implements InitializingBean {

    @Value("${com.path}")
    private String path;


    private static final Logger logger = LoggerFactory.getLogger(CallbackController.class);

    @RequestMapping("/erp/stock")
    @Transactional(rollbackFor = Exception.class)
    public Object stock(@RequestBody String body) throws Exception {
        // 验签
        // 编辑库存
        return WxPayNotifyResponse.success("支付成功");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.printf(this.path);
    }
}
