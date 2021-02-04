package com.iotechn.unimall.biz.service.storage;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.ServiceException;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.iotechn.unimall.core.exception.AppServiceException;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.data.properties.UnimallAliOSSProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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

//    @Override
//    public String upload(String fileName, InputStream is, long contentLength, String contentType) {
//        ObjectMetadata objectMetadata = new ObjectMetadata();
//        objectMetadata.setContentLength(contentLength);
//        objectMetadata.setContentType(contentType);
//        PutObjectRequest putObjectRequest = new PutObjectRequest(unimallAliOSSProperties.getBucket(), fileName, is, objectMetadata);
//        ossClient.putObject(putObjectRequest);
//        return unimallAliOSSProperties.getBaseUrl() + fileName;
//    }

    @Override
    public void delete(String img) {
        if(img == null || "".equals(img)){
            return;
        }
        int chop = img.lastIndexOf("/");
        int point = img.lastIndexOf(".");
        if (chop <= 0 || point <= 0 || chop >= point) {
            return;
        }
        ossClient.deleteObject(unimallAliOSSProperties.getBucket(),img.substring(chop+1,point));
    }

    @Override
    public void delete(List<String> imgList){
        if (CollectionUtils.isEmpty(imgList)) {
            return;
        }
        ArrayList<String> keys = new ArrayList<>();
        for (int i = 0; i < imgList.size(); i++) {
            String s = imgList.get(i);
            int chop = s.lastIndexOf("/");
            int point = s.lastIndexOf(".");
            if (chop <= 0 || point <= 0 || chop >= point) {
                continue;
            }
            keys.add(s.substring(chop + 1, point));
        }
        if (CollectionUtils.isEmpty(keys)) {
            return;
        }
        if(keys.size() > 1000){
            int index = 0;
            while (index < keys.size()){
                ossClient.deleteObjects(new DeleteObjectsRequest(unimallAliOSSProperties.getBucket()).withKeys(keys.subList(index,keys.size() - index > 1000 ? (index + 1000) : keys.size())));
                index += 1000;
            }
        }else {
            ossClient.deleteObjects(new DeleteObjectsRequest(unimallAliOSSProperties.getBucket()).withKeys(keys));
        }
    }
}
