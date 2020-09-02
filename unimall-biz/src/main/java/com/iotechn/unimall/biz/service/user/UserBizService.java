package com.iotechn.unimall.biz.service.user;

import com.alibaba.fastjson.JSONObject;
import com.iotechn.unimall.data.constant.CacheConst;
import com.iotechn.unimall.data.component.CacheComponent;
import com.iotechn.unimall.data.domain.UserDO;
import com.iotechn.unimall.data.mapper.UserMapper;
import com.iotechn.unimall.data.properties.UnimallWxAppProperties;
import com.iotechn.unimall.data.wx.WeChatCommonTemplateMessageModel;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
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
    private UnimallWxAppProperties unimallWxProperties;

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
                                    + unimallWxProperties.getH5AppId() + "&secret=" + unimallWxProperties.getH5AppSecret())
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
                                    + unimallWxProperties.getMiniAppId() + "&secret=" + unimallWxProperties.getMiniAppSecret())
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

    /**
     * 抽取 公众号模板消息
     *
     * @param model
     * @param url
     * @throws Exception
     */
    private int wechatCommonTemplateMessage(WeChatCommonTemplateMessageModel model, String url) throws Exception {
        String modelJson = JSONObject.toJSONString(model);
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, modelJson);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        String res = okHttpClient.newCall(request).execute().body().string();
        JSONObject jsonObject = JSONObject.parseObject(res);
        Integer errcode = jsonObject.getInteger("errcode");
        if (errcode != 0) {
            logger.error("[模板消息回复] 错误，请求报文=" + modelJson);
            logger.error("[模板消息回复] 错误，回复报文=" + res);
        }
        return errcode;
    }

    public UserDO getUserById(Long userId) {
        return userMapper.selectById(userId);
    }

}
