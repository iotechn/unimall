package com.iotechn.unimall.biz.service.storage;

import java.io.InputStream;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2019/11/26
 * Time: 21:33
 */
public interface StorageBizService {

    // public String upload(String fileName, InputStream is, long contentLength, String contentType);

    public void delete(String img);

    public void delete(List<String> imgList);

}
