package com.iotechn.unimall.biz.service.storage;

import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2019/11/26
 * Time: 21:33
 */
public interface StorageBizService {

    public String upload(String fileName, InputStream is, long contentLength, String contentType);

}
