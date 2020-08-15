package com.iotechn.unimall.core.util;


import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;

public class SHAUtil {

    /**
     * @return
     * @Comment SHA256实现
     * @Author Ron
     * @Date 2017年9月13日 下午3:30:36
     */
    public static String sha256Encode(String inStr) throws Exception {
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

    /**
     * hmac SHA1
     * @param encryptText
     * @param encryptKey
     * @return
     * @throws Exception
     */
    public static byte[] hmacSHA1Encrypt(String encryptText, String encryptKey) throws Exception {
        byte[] data = encryptKey.getBytes("utf-8");
        // 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        SecretKey secretKey = new SecretKeySpec(data, "HmacSHA1");
        // 生成一个指定 Mac 算法 的 Mac 对象
        Mac mac = Mac.getInstance("HmacSHA1");
        // 用给定密钥初始化 Mac 对象
        mac.init(secretKey);
        byte[] text = encryptText.getBytes("utf-8");
        // 完成 Mac 操作
        return mac.doFinal(text);
    }

}
