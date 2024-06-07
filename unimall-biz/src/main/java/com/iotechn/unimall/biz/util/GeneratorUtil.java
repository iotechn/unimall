package com.iotechn.unimall.biz.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class GeneratorUtil {

    private static final AtomicInteger orderIdCount = new AtomicInteger();

    private static final SimpleDateFormat ORDER_ID_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");


    public static String genOrderId(String machineNo) {
        int i = orderIdCount.incrementAndGet() % 1000;
        i += 1000;
        return machineNo + ORDER_ID_FORMAT.format(new Date()) + i;
    }
}
