package com.iotechn.unimall.biz.service.user;

import com.alibaba.fastjson.JSONObject;
import com.iotechn.unimall.data.constant.CacheConst;
import com.iotechn.unimall.data.domain.UserDO;
import com.iotechn.unimall.data.mapper.UserMapper;
import com.dobbinsoft.fw.support.component.CacheComponent;
import com.dobbinsoft.fw.support.properties.FwWxAppProperties;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by rize on 2019/9/12.
 */
@Service
public class UserBizService {

    private OkHttpClient okHttpClient = new OkHttpClient();

    @Autowired
    private FwWxAppProperties fwWxAppProperties;

    @Autowired
    private CacheComponent cacheComponent;

    @Autowired
    private UserMapper userMapper;

    private static final Logger logger = LoggerFactory.getLogger(UserBizService.class);

    public String getWxH5AccessToken() throws Exception {
        String wxAccessToken = cacheComponent.getRaw(CacheConst.USER_OFFICIAL_WECHAT_ACCESS);
        if (StringUtils.isEmpty(wxAccessToken)) {
            //尝试获取微信公众号Token
            String accessJson = okHttpClient.newCall(
                    new Request.Builder()
                            .url("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                                    + fwWxAppProperties.getH5AppId() + "&secret=" + fwWxAppProperties.getH5AppSecret())
                            .get()
                            .build()).execute().body().string();
            JSONObject jsonObject = JSONObject.parseObject(accessJson);
            wxAccessToken = jsonObject.getString("access_token");
            if (!StringUtils.isEmpty(wxAccessToken)) {
                Integer expires_in = jsonObject.getInteger("expires_in");
                //在过期前重置
                Integer cacheExpireSec = expires_in * 4 / 5;
                cacheComponent.putRaw(CacheConst.USER_OFFICIAL_WECHAT_ACCESS, wxAccessToken, cacheExpireSec);
            } else {
                throw new RuntimeException("回复错误:" + accessJson);
            }
        }
        return wxAccessToken;
    }

    public String getWxH5Ticket(String accessToken) throws Exception {
        String wxTicket = cacheComponent.getRaw(CacheConst.USER_OFFICIAL_WECHAT_TICKET);
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
                cacheComponent.putRaw(CacheConst.USER_OFFICIAL_WECHAT_TICKET, wxTicket, cacheExpireSec);
            } else {
                throw new RuntimeException("回复错误:" + ticketJson);
            }
        }
        return wxTicket;
    }

    public String getWxMiniAccessToken() throws Exception {
        String access_token = cacheComponent.getRaw(CacheConst.USER_MINI_WECHAT_ACCESS);
        if (StringUtils.isEmpty(access_token)) {
            String accessJson = okHttpClient.newCall(
                    new Request.Builder()
                            .url("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                                    + fwWxAppProperties.getMiniAppId() + "&secret=" + fwWxAppProperties.getMiniAppSecret())
                            .get()
                            .build()).execute().body().string();
            JSONObject jsonObject = JSONObject.parseObject(accessJson);
            access_token = jsonObject.getString("access_token");
            if (!StringUtils.isEmpty(access_token)) {
                Integer expires_in = jsonObject.getInteger("expires_in");
                Integer cacheExpireSec = expires_in * 4 / 5;
                cacheComponent.putRaw(CacheConst.USER_MINI_WECHAT_ACCESS, access_token, cacheExpireSec);
            } else {
                throw new RuntimeException("回复错误:" + accessJson);
            }
        }
        return access_token;
    }


    public UserDO getUserById(Long userId) {
        return userMapper.selectById(userId);
    }

}
