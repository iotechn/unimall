package com.iotechn.unimall.core.util;

import java.util.UUID;

/**
 * Created by rize on 2019/7/1.
 */
public class GeneratorUtil {

    public static String genSixVerifyCode() {
        String time = System.nanoTime() + "";
        return time.substring(time.length() - 6);
    }

    public static String genSessionId() {
        return UUID.randomUUID().toString().replace("-","").toUpperCase();
    }
}
