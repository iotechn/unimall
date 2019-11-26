package com.iotechn.unimall.biz.service.storage;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2019/11/26
 * Time: 21:34
 */
@Service
public class AliyunStorageBizServiceImpl implements StorageBizService {

    @Value("${oss.aliyun.oss.endpoint}")
    private String endpoint;
    @Value("${oss.aliyun.oss.bucket}")
    private String bucket;
    @Value("${oss.aliyun.oss.basekUrl}")
    private String baseUrl;

    @Autowired
    private OSSClient ossClient;

    @Override
    public String upload(String fileName, InputStream is, long contentLength, String contentType) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(contentLength);
        objectMetadata.setContentType(contentType);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, is, objectMetadata);
        ossClient.putObject(putObjectRequest);
        return baseUrl + fileName;
    }
}
