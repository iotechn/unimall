package com.iotechn.unimall.biz.service.config;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iotechn.unimall.biz.service.storage.StorageBizService;
import com.iotechn.unimall.biz.service.user.UserBizService;
import com.iotechn.unimall.core.Const;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.core.exception.ThirdPartServiceException;
import com.iotechn.unimall.core.util.GeneratorUtil;
import com.iotechn.unimall.data.annotaion.AspectCommonCache;
import com.iotechn.unimall.data.component.CacheComponent;
import com.iotechn.unimall.data.constant.CacheConst;
import com.iotechn.unimall.data.domain.MerchantInfoDO;
import com.iotechn.unimall.data.dto.MerchantInfoDTO;
import com.iotechn.unimall.data.mapper.MerchantInfoMapper;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by rize on 2019/7/21.
 */
@Service
public class MerchantInfoBizService {

    @Autowired
    private MerchantInfoMapper merchantInfoMapper;

    @Autowired
    private CacheComponent cacheComponent;

    @Autowired
    private UserBizService userBizService;

    @Autowired
    private StorageBizService storageBizService;

    private OkHttpClient okHttpClient = new OkHttpClient();

    private static final String GEN_QR_CODE_URL = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=";

    private static final Logger logger = LoggerFactory.getLogger(MerchantInfoBizService.class);

    @AspectCommonCache(value = CacheConst.MERCHANT_INFO,arrayClass = MerchantInfoDTO.class,second = -1)
    public MerchantInfoDTO getMerchantInfo() {
        List<MerchantInfoDO> list = merchantInfoMapper.selectList(null);
        MerchantInfoDTO configDTO = new MerchantInfoDTO();
        for (MerchantInfoDO storeInfoDO : list) {
            switch (storeInfoDO.getKeyWord()) {
                case "title":
                    configDTO.setTitle(storeInfoDO.getValueWorth());
                    break;
                case "logoUrl":
                    configDTO.setLogoUrl(storeInfoDO.getValueWorth());
                    break;
                case "description":
                    configDTO.setDescription(storeInfoDO.getValueWorth());
                    break;
                case "address":
                    configDTO.setAddress(storeInfoDO.getValueWorth());
                    break;
                case "showType":
                    configDTO.setShowType(Integer.parseInt(storeInfoDO.getValueWorth()));
                    break;
                case "status":
                    configDTO.setStatus(Integer.parseInt(storeInfoDO.getValueWorth()));
                    break;
            }
        }
        return configDTO;
    }

    public boolean createOrUpdate(String key, String value){
        QueryWrapper<MerchantInfoDO> wrapper = new QueryWrapper<>();
        wrapper.eq("key_word",key);
        List<MerchantInfoDO> merchantInfoDOS = merchantInfoMapper.selectList(wrapper);
        if(CollectionUtils.isEmpty(merchantInfoDOS)){
            return merchantInfoMapper.insert(new MerchantInfoDO(key,value)) > 0;
        }else {
            return merchantInfoMapper.update(new MerchantInfoDO(key,value),new QueryWrapper<MerchantInfoDO>().eq("key_word",key)) > 0;
        }
    }

    public void clearMerchantInfoCache() {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                cacheComponent.del(CacheConst.MERCHANT_INFO);
            }
        });
    }

    /**
     * 生成微信小程序二维码
     * @param path 小程序访问页面路径
     * @return
     */
    public String generateWxMiniCode(String path) throws ServiceException {
        try {
            MediaType mediaType = MediaType.parse("application/json");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("path", path);
            jsonObject.put("width", 1280);
            RequestBody body = RequestBody.create(mediaType, jsonObject.toJSONString());
            Request request = new Request.Builder()
                    .url(GEN_QR_CODE_URL + userBizService.getWxMiniAccessToken())
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            if (response.code() == 200) {
                InputStream is = response.body().byteStream();
                String url = storageBizService.upload("qr/" + GeneratorUtil.genFileName() + ".png", is, response.body().contentLength(), "image/png");
                return url;
            } else {
                throw new ThirdPartServiceException("生成二维码失败", ExceptionDefinition.THIRD_PART_IO_EXCEPTION.getCode());
            }
        } catch (IOException e) {
            logger.error("[生成小程序二维码] IO异常", e);
            throw new ThirdPartServiceException(ExceptionDefinition.THIRD_PART_IO_EXCEPTION);
        } catch (Exception e) {
            logger.error("[生成小程序二维码] 异常", e);
            throw new ThirdPartServiceException(ExceptionDefinition.APP_UNKNOWN_EXCEPTION);
        }
    }
}
