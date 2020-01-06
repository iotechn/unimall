package com.iotechn.unimall.launcher.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.*;
import com.iotechn.unimall.core.util.GeneratorUtil;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rize on 2019/2/13.
 */
@Controller
@RequestMapping("/upload")
public class FileUploadController implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @Value("${oss.aliyun.oss.accessId}")
    private String accessId;
    @Value("${oss.aliyun.oss.accessKey}")
    private String accessKey;
    @Value("${oss.aliyun.oss.endpoint}")
    private String endpoint;
    @Value("${oss.aliyun.oss.bucket}")
    private String bucket;
    @Value("${oss.aliyun.oss.dir}")
    private String dir;
    @Value("${oss.aliyun.oss.callbackUrl}")
    private String callbackUrl;
    @Value("${oss.aliyun.oss.basekUrl}")
    private String baseUrl;
    @Autowired
    private OSSClient ossClient;

    private String host;

//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void afterPropertiesSet() throws Exception {
        host = "http://" + bucket + "." + endpoint;
    }

    /**
     * 前台签名直传， 由服务器签名，用户可直接上传图片
     * 这种只支持 Aliyun(因为我编码查看文档时，只有阿里云做了这个设计) 优点是 上传不需要占用应用服务器带宽。 目前前端是使用的这个。
     * 若需要更改，请自行修改前端上传逻辑
     * @param request
     * @param response
     */
    @RequestMapping(method = RequestMethod.GET)
    public void upload(HttpServletRequest request, HttpServletResponse response) {
        try {
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);
            JSONObject respJsonObj = new JSONObject();
            respJsonObj.put("accessid", accessId);
            respJsonObj.put("policy", encodedPolicy);
            respJsonObj.put("signature", postSignature);
            respJsonObj.put("dir", dir);
            respJsonObj.put("host", host);
            respJsonObj.put("expire", String.valueOf(expireEndTime / 1000));
            // respMap.put("expire", formatISO8601Date(expiration));
            JSONObject jasonCallback = new JSONObject();
            jasonCallback.put("callbackUrl", callbackUrl);
            jasonCallback.put("callbackBody",
                    "filename=${object}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}");
            jasonCallback.put("callbackBodyType", "application/x-www-form-urlencoded");
            String base64CallbackBody = BinaryUtil.toBase64String(jasonCallback.toString().getBytes());
            respJsonObj.put("callback", base64CallbackBody);

            // System.out.println(ja1.toString());
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST");
            respJsonObj.put("code", 200);
            respJsonObj.put("baseUrl", baseUrl);
            response(request, response, respJsonObj.toJSONString());
        } catch (Exception e) {
            logger.error("[上传签名] 异常", e);
        }
    }

    /**
     * 后台通过服务器间接传文件
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/admin")
    @ResponseBody
    public Object create(@RequestParam("file") MultipartFile file) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        String uuid = GeneratorUtil.genFileName();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, "bg/" + uuid+"."+ext, file.getInputStream(), objectMetadata);
        ossClient.putObject(putObjectRequest);
        Map<String, Object> data = new HashMap<>();
        data.put("url", baseUrl + "bg/" + uuid +"."+ext);
        data.put("errno", 200);
        data.put("errmsg", "成功");
        return data;
    }


    /**
     * Post请求
     */
    @RequestMapping(method = RequestMethod.POST)
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        JSONObject responseJson = new JSONObject();
        responseJson.put("code", 200);
        for (String key : parameterMap.keySet()) {
            responseJson.put(key, parameterMap.get(key)[0]);
        }
        response(request, response, responseJson.toJSONString(), HttpServletResponse.SC_OK);
    }

    /**
     * 服务器响应结果
     *
     * @param request
     * @param response
     * @param results
     * @param status
     * @throws IOException
     */
    private void response(HttpServletRequest request, HttpServletResponse response, String results, int status)
            throws IOException {
        String callbackFunName = request.getParameter("callback");
        response.addHeader("Content-Length", String.valueOf(results.length()));
        if (callbackFunName == null || callbackFunName.equalsIgnoreCase(""))
            response.getWriter().println(results);
        else
            response.getWriter().println(callbackFunName + "( " + results + " )");
        response.setStatus(status);
        response.flushBuffer();
    }

    /**
     * 服务器响应结果
     */
    private void response(HttpServletRequest request, HttpServletResponse response, String results) throws IOException {
        String callbackFunName = request.getParameter("callback");
        if (callbackFunName == null || callbackFunName.equalsIgnoreCase(""))
            response.getWriter().println(results);
        else
            response.getWriter().println(callbackFunName + "( " + results + " )");
        response.setStatus(HttpServletResponse.SC_OK);
        response.flushBuffer();
    }

}
