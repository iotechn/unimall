package com.dobbinsoft.unimall.biz.service.user;

import com.dobbinsoft.fw.support.component.CacheComponent;
import com.dobbinsoft.fw.support.utils.JacksonUtil;
import com.dobbinsoft.unimall.data.constant.CacheConst;
import com.dobbinsoft.unimall.data.domain.UserDO;
import com.dobbinsoft.unimall.data.domain.VipOrderDO;
import com.dobbinsoft.unimall.data.dto.user.WxAccessTokenResultDTO;
import com.dobbinsoft.unimall.data.dto.user.WxH5TicketResultDTO;
import com.dobbinsoft.unimall.data.enums.UserLevelType;
import com.dobbinsoft.unimall.data.mapper.UserMapper;
import com.dobbinsoft.unimall.data.properties.UnimallWxAppProperties;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

/**
 * Created by rize on 2019/9/12.
 */
@Service
public class UserBizService {

    private final OkHttpClient okHttpClient = new OkHttpClient();

    @Autowired
    private UnimallWxAppProperties fwWxAppProperties;

    @Autowired
    private CacheComponent cacheComponent;

    @Autowired
    private UserMapper userMapper;

    private static final Logger logger = LoggerFactory.getLogger(UserBizService.class);

    public String getWxH5AccessToken() throws Exception {
        String wxAccessToken = cacheComponent.getRaw(CacheConst.USER_OFFICIAL_WECHAT_ACCESS);
        if (ObjectUtils.isEmpty(wxAccessToken)) {
            //尝试获取微信公众号Token
            String responseJson = okHttpClient.newCall(
                    new Request.Builder()
                            .url("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                                    + fwWxAppProperties.getH5AppId() + "&secret=" + fwWxAppProperties.getH5AppSecret())
                            .get()
                            .build()).execute().body().string();

            WxAccessTokenResultDTO wxAccessTokenResultDTO = JacksonUtil.parseObject(responseJson, WxAccessTokenResultDTO.class);

            wxAccessToken = wxAccessTokenResultDTO.getAccessToken();
            if (!ObjectUtils.isEmpty(wxAccessToken)) {
                //在过期前重置
                Integer cacheExpireSec = wxAccessTokenResultDTO.getExpiresIn() * 4 / 5;
                cacheComponent.putRaw(CacheConst.USER_OFFICIAL_WECHAT_ACCESS, wxAccessToken, cacheExpireSec);
            } else {
                throw new RuntimeException("回复错误:" + responseJson);
            }
        }
        return wxAccessToken;
    }

    public String getWxH5Ticket(String accessToken) throws Exception {
        String wxTicket = cacheComponent.getRaw(CacheConst.USER_OFFICIAL_WECHAT_TICKET);
        if (ObjectUtils.isEmpty(wxTicket)) {
            //尝试获取微信公众号Ticket
            String ticketJson = okHttpClient.newCall(
                    new Request.Builder()
                            .url("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken + "&type=jsapi")
                            .get()
                            .build()).execute().body().string();
            WxH5TicketResultDTO wxH5TicketResultDTO = JacksonUtil.parseObject(ticketJson, WxH5TicketResultDTO.class);
            wxTicket = wxH5TicketResultDTO.getTicket();
            if (!ObjectUtils.isEmpty(wxTicket)) {
                //在过期前重置
                Integer cacheExpireSec = wxH5TicketResultDTO.getExpiresIn() * 4 / 5;
                cacheComponent.putRaw(CacheConst.USER_OFFICIAL_WECHAT_TICKET, wxTicket, cacheExpireSec);
            } else {
                throw new RuntimeException("回复错误:" + ticketJson);
            }
        }
        return wxTicket;
    }

    public String getWxMiniAccessToken() throws Exception {
        String accessToken = cacheComponent.getRaw(CacheConst.USER_MINI_WECHAT_ACCESS);
        if (ObjectUtils.isEmpty(accessToken)) {
            String accessJson = okHttpClient.newCall(
                    new Request.Builder()
                            .url("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                                    + fwWxAppProperties.getMiniAppId() + "&secret=" + fwWxAppProperties.getMiniAppSecret())
                            .get()
                            .build()).execute().body().string();


            WxAccessTokenResultDTO wxAccessTokenResultDTO = JacksonUtil.parseObject(accessJson, WxAccessTokenResultDTO.class);
            accessToken = wxAccessTokenResultDTO.getAccessToken();
            if (!ObjectUtils.isEmpty(accessToken)) {
                Integer cacheExpireSec = wxAccessTokenResultDTO.getExpiresIn() * 4 / 5;
                cacheComponent.putRaw(CacheConst.USER_MINI_WECHAT_ACCESS, accessToken, cacheExpireSec);
            } else {
                throw new RuntimeException("回复错误:" + accessJson);
            }
        }
        return accessToken;
    }


    public UserDO getUserById(Long userId) {
        return userMapper.selectById(userId);
    }

    /**
     * 用户升级为VIP时，设置过期时间
     * @param vipOrder
     */
    public UserDO upUserLevel(VipOrderDO vipOrder){
        UserDO userDO = userMapper.selectById(vipOrder.getUserId());
        LocalDateTime now = LocalDateTime.now();
        // 若已过期或首次充值，则以现在的时间为续费基点。否则在过期的时间基础上加时长。
        LocalDateTime baseTime = (UserLevelType.COMMON.getCode() == userDO.getLevel().intValue() || now.isAfter(userDO.getGmtVipExpire())) ? now : userDO.getGmtVipExpire();
        userDO.setGmtVipExpire(baseTime.plusDays(vipOrder.getDayNum()));
        userDO.setLevel(UserLevelType.VIP.getCode());
        userDO.setId(userDO.getId());
        userMapper.updateById(userDO);
        return userDO;
    }

}
