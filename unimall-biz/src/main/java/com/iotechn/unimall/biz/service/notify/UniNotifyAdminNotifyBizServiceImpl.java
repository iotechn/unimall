package com.iotechn.unimall.biz.service.notify;

import com.iotechn.unimall.data.dto.order.OrderDTO;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * Description: 借用Uni-Notify 公共服务器推送通知的实现
 * User: rize
 * Date: 2019/12/27
 * Time: 16:19
 */
public class UniNotifyAdminNotifyBizServiceImpl implements AdminNotifyBizService {

    @Value("${com.iotechn.admin.notify.uninotify.app-id}")
    private String appId;

    @Value("${com.iotechn.admin.notify.uninotify.app-secret}")
    private String appSecret;

    private OkHttpClient okHttpClient = new OkHttpClient();

    private static final String SEVER_URL = "http://public.dobbinsoft.com/m.api";

    private static final Logger logger = LoggerFactory.getLogger(UniNotifyAdminNotifyBizServiceImpl.class);

    @Override
    public void newOrder(OrderDTO orderDTO) {
        try {
            FormBody formBody = new FormBody.Builder()
                    .add("_gp", "template")
                    .add("_mt", "newOrderNotify")
                    //要通知的管理员Id
                    .add("userId", "admin")
                    .add("orderNo", orderDTO.getOrderNo())
                    .add("actualPrice", "￥" + (orderDTO.getActualPrice() / 100.0))
                    .add("payChannel", orderDTO.getPayChannel())
                    .add("consignee", orderDTO.getConsignee())
                    .add("phone", orderDTO.getPhone())
                    .add("address", orderDTO.getAddress())
                    .add("skuInfo", orderDTO.getSkuList().stream().map(item -> (item.getTitle() + " * " + item.getNum())).collect(Collectors.joining("\r\n")))
                    .build();
            okHttpClient.newCall(new Request.Builder().url(SEVER_URL).post(formBody).build()).execute().body().string();
        } catch (Exception e) {
            logger.error("[通知管理员] 异常", e);
        }
    }

    @Override
    public void refundOrder(OrderDTO orderDTO) {
        try {
            FormBody formBody = new FormBody.Builder()
                    .add("_gp", "template")
                    .add("_mt", "refundOrderNotify")
                    //要通知的管理员Id
                    .add("userId", "admin")
                    .add("orderNo", orderDTO.getOrderNo())
                    .add("refundPrice", "￥" + ((orderDTO.getActualPrice() - orderDTO.getFreightPrice()) / 100.0))
                    .add("skuInfo", orderDTO.getSkuList().stream().map(item -> (item.getTitle() + " * " + item.getNum())).collect(Collectors.joining("\r\n")))
                    .build();
            okHttpClient.newCall(new Request.Builder().url(SEVER_URL).post(formBody).build()).execute().body().string();
        } catch (Exception e) {
            logger.error("[通知管理员] 异常", e);
        }
    }
}
