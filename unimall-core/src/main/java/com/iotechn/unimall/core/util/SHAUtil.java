package com.iotechn.unimall.core.util;

import java.security.MessageDigest;

public class SHAUtil {

    /**
     * @return
     * @Comment SHA256实现
     * @Author Ron
     * @Date 2017年9月13日 下午3:30:36
     */
    public static String shaEncode(String inStr) throws Exception {
        MessageDigest sha = null;
        sha = MessageDigest.getInstance("SHA-256");

        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = sha.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

}
