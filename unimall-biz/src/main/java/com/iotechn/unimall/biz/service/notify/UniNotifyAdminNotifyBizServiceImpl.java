package com.iotechn.unimall.biz.service.notify;

import com.iotechn.unimall.core.util.SHAUtil;
import com.iotechn.unimall.data.dto.order.OrderDTO;
import com.iotechn.unimall.data.properties.UnimallAdminNotifyProperties;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.net.URLEncoder;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * Description: 借用Uni-Notify 公共服务器推送通知的实现
 * User: rize
 * Date: 2019/12/27
 * Time: 16:19
 */
public class UniNotifyAdminNotifyBizServiceImpl implements AdminNotifyBizService {

    @Autowired
    private UnimallAdminNotifyProperties unimallAdminNotifyProperties;

    private OkHttpClient okHttpClient = new OkHttpClient();

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
                    .add("consignee", StringUtils.isEmpty(orderDTO.getConsignee()) ? "" : orderDTO.getConsignee())
                    .add("phone", StringUtils.isEmpty(orderDTO.getPhone()) ? "" : orderDTO.getPhone())
                    .add("address", StringUtils.isEmpty(orderDTO.getAddress()) ? "" : orderDTO.getAddress())
                    .add("skuInfo", orderDTO.getSkuList().stream().map(item -> (item.getSpuTitle() + "-" + item.getTitle() + " * " + item.getNum())).collect(Collectors.joining("\r\n")))
                    .add("appId", unimallAdminNotifyProperties.getUniNotifyAppId())
                    .add("timestamp", System.currentTimeMillis() + "")
                    .build();
            String sign = getSign(formBody);
            String string = okHttpClient.newCall(new Request.Builder().url(unimallAdminNotifyProperties.getUniNotifyUrl() + "?sign=" + sign).post(formBody).build()).execute().body().string();
            logger.info(string);
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
                    .add("skuInfo", orderDTO.getSkuList().stream().map(item -> (item.getSpuTitle() + "-" + item.getTitle() + " * " + item.getNum())).collect(Collectors.joining("\r\n")))
                    .add("appId", unimallAdminNotifyProperties.getUniNotifyAppId())
                    .add("timestamp", System.currentTimeMillis() + "")
                    .build();
            String sign = getSign(formBody);
            okHttpClient.newCall(new Request.Builder().url(unimallAdminNotifyProperties.getUniNotifyUrl() + "?sign=" + sign).post(formBody).build()).execute().body().string();
        } catch (Exception e) {
            logger.error("[通知管理员] 异常", e);
        }
    }

    private String getSign(FormBody formBody) throws Exception {
        Set<String> sortSet = new TreeSet<>();
        for (int i = 0; i < formBody.size(); i++) {
            sortSet.add(formBody.value(i));
        }
        sortSet.add(this.unimallAdminNotifyProperties.getUniNotifyAppSecret());
        return SHAUtil.sha256Encode(URLEncoder.encode(sortSet.stream().collect(Collectors.joining()), "utf-8"));
    }
}
