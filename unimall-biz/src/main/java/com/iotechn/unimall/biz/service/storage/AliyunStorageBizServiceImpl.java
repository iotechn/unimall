package com.iotechn.unimall.biz.service.storage;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.iotechn.unimall.data.properties.UnimallAliOSSProperties;
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

    @Autowired
    private UnimallAliOSSProperties unimallAliOSSProperties;

    @Autowired
    private OSSClient ossClient;

    @Override
    public String upload(String fileName, InputStream is, long contentLength, String contentType) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(contentLength);
        objectMetadata.setContentType(contentType);
        PutObjectRequest putObjectRequest = new PutObjectRequest(unimallAliOSSProperties.getBucket(), fileName, is, objectMetadata);
        ossClient.putObject(putObjectRequest);
        return unimallAliOSSProperties.getBaseUrl() + fileName;
    }
}
