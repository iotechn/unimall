package com.iotechn.unimall.biz.service.user;

import com.alibaba.fastjson.JSONObject;
import com.iotechn.unimall.data.component.CacheComponent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by rize on 2019/9/12.
 */
@Service
public class UserBizService {

    private OkHttpClient okHttpClient = new OkHttpClient();

    private static final String CA_OFFICIAL_WECHAT_ACCESS = "CA_OFFICIAL_WECHAT_ACCESS";

    private static final String CA_OFFICIAL_WECHAT_TICKET = "CA_OFFICIAL_WECHAT_TICKET";

    @Value("${com.iotechn.unimall.wx.h5.app-id}")
    private String wxH5Appid;

    @Value("${com.iotechn.unimall.wx.h5.app-secret}")
    private String wxH5Secret;

    @Autowired
    private CacheComponent cacheComponent;

    public String getWxH5AccessToken() throws Exception {
        String wxAccessToken = cacheComponent.getRaw(CA_OFFICIAL_WECHAT_ACCESS);
        if (StringUtils.isEmpty(wxAccessToken)) {
            //尝试获取微信公众号Token
            String accessJson = okHttpClient.newCall(
                    new Request.Builder()
                            .url("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + wxH5Appid + "&secret=" + wxH5Secret)
                            .get()
                            .build()).execute().body().string();
            JSONObject jsonObject = JSONObject.parseObject(accessJson);
            wxAccessToken = jsonObject.getString("access_token");
            if (!StringUtils.isEmpty(wxAccessToken)) {
                Integer expires_in = jsonObject.getInteger("expires_in");
                //在过期前重置
                Integer cacheExpireSec = expires_in * 4 / 5;
                cacheComponent.putRaw(CA_OFFICIAL_WECHAT_ACCESS, wxAccessToken, cacheExpireSec);
            } else {
                throw new RuntimeException("回复错误:" + accessJson);
            }
        }
        return wxAccessToken;
    }

    public String getWxH5Ticket(String accessToken) throws Exception {
        String wxTicket = cacheComponent.getRaw(CA_OFFICIAL_WECHAT_TICKET);
        if (StringUtils.isEmpty(wxTicket)) {
            //尝试获取微信公众号Ticket
            String ticketJson = okHttpClient.newCall(
                    new Request.Builder()
                            .url("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken + "&type=jsapi")
                            .get()
                            .build()).execute().body().string();
            JSONObject jsonObject = JSONObject.parseObject(ticketJson);
            wxTicket = jsonObject.getString("ticket");
            if (!StringUtils.isEmpty(wxTicket)) {
                Integer expires_in = jsonObject.getInteger("expires_in");
                //在过期前重置
                Integer cacheExpireSec = expires_in * 4 / 5;
                cacheComponent.putRaw(CA_OFFICIAL_WECHAT_TICKET, wxTicket, cacheExpireSec);
            } else {
                throw new RuntimeException("回复错误:" + ticketJson);
            }
        }
        return wxTicket;
    }
}
