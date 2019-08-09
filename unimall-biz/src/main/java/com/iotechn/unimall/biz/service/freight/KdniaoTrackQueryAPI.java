package com.iotechn.unimall.biz.service.freight;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.core.exception.ThirdPartServiceException;
import com.iotechn.unimall.data.dto.freight.ShipTraceDTO;
import com.iotechn.unimall.data.dto.freight.ShipTraceItemDTO;
import com.iotechn.unimall.data.enums.ShipCodeType;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.LinkedList;
import java.util.List;


@Component
public class KdniaoTrackQueryAPI implements ShipTraceQuery {

    private static final Logger logger = LoggerFactory.getLogger(KdniaoTrackQueryAPI.class);

    @Value("${com.iotechn.ship.query.kdn.app-key}")
    private String businessID;

    @Value("${com.iotechn.ship.query.kdn.business-id}")
    private String appKey;

    private static final String REQ_URL = "http://api.kdniao.com/Ebusiness/EbusinessOrderHandle.aspx";

    private OkHttpClient okHttpClient = new OkHttpClient();

    @Override
    public ShipTraceDTO query(String shipNo, String shipCode) throws ServiceException {
        String msg = "";
        try {
            String orderTracesByJson = getOrderTracesByJson(shipCode, shipNo);
            JSONObject jsonObject = JSONObject.parseObject(orderTracesByJson);
            ShipTraceDTO shipTraceDTO = new ShipTraceDTO();
            shipTraceDTO.setErrcode(jsonObject.getInteger("State"));
            shipTraceDTO.setShipNo(shipNo);
            shipTraceDTO.setShipCode(shipCode);
            shipTraceDTO.setShipName(ShipCodeType.getByCode(shipCode).getMsg());
            shipTraceDTO.setErrmsg(jsonObject.getString("Reason"));
            msg = shipTraceDTO.getErrmsg();
            List<ShipTraceItemDTO> traces = new LinkedList<>();
            JSONArray tracesFromJson = jsonObject.getJSONArray("Traces");
            if (!CollectionUtils.isEmpty(tracesFromJson)) {
                for (int i = 0; i < tracesFromJson.size(); i++) {
                    ShipTraceItemDTO shipTraceItemDTO = new ShipTraceItemDTO();
                    shipTraceItemDTO.setStation(tracesFromJson.getJSONObject(i).getString("AcceptStation"));
                    shipTraceItemDTO.setTime(tracesFromJson.getJSONObject(i).getString("AcceptTime"));
                    traces.add(shipTraceItemDTO);
                }
            }
            shipTraceDTO.setTraces(traces);
            return shipTraceDTO;
        } catch (Exception e) {
            logger.error("[快递鸟 查询] 异常", e);
            throw new ThirdPartServiceException(msg, ExceptionDefinition.THIRD_PART_SERVICE_EXCEPTION.getCode());
        }
    }

    /**
     * Json方式 查询订单物流轨迹
     *
     * @throws Exception
     */
    private String getOrderTracesByJson(String shipCode, String shipNo) throws Exception {
        String requestData = "{\"OrderCode\":\"\",\"ShipperCode\":\"" + shipCode + "\",\"LogisticCode\":\"" + shipNo + "\"}";
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        StringBuilder sb = new StringBuilder();
        sb.append("RequestData=");
        sb.append(URLEncoder.encode(requestData, "UTF-8"));
        sb.append("&EBusinessID=");
        sb.append(businessID);
        sb.append("&RequestType=1002");
        String dataSign = encrypt(requestData, appKey, "UTF-8");
        sb.append("&DataSign=");
        sb.append(dataSign);
        sb.append("&DataType=2");
        RequestBody body = RequestBody.create(mediaType, sb.toString());
        String result = okHttpClient.newCall(new Request.Builder().url(REQ_URL).post(body).build()).execute().body().string();
        return result;
    }

    /**
     * MD5加密
     *
     * @param str     内容
     * @param charset 编码方式
     * @throws Exception
     */
    private String MD5(String str, String charset) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes(charset));
        byte[] result = md.digest();
        StringBuffer sb = new StringBuffer(32);
        for (int i = 0; i < result.length; i++) {
            int val = result[i] & 0xff;
            if (val <= 0xf) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString().toLowerCase();
    }

    /**
     * base64编码
     *
     * @param str     内容
     * @param charset 编码方式
     * @throws UnsupportedEncodingException
     */
    private String base64(String str, String charset) throws UnsupportedEncodingException {
        return Base64Utils.encodeToString(str.getBytes(charset));
    }

    /**
     * 电商Sign签名生成
     *
     * @param content  内容
     * @param keyValue Appkey
     * @param charset  编码方式
     * @return DataSign签名
     * @throws UnsupportedEncodingException ,Exception
     */
    private String encrypt(String content, String keyValue, String charset) throws UnsupportedEncodingException, Exception {
        if (keyValue != null) {
            return base64(MD5(content + keyValue, charset), charset);
        }
        return base64(MD5(content, charset), charset);
    }



}
