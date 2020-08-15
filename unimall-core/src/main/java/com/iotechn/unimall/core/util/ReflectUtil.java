package com.iotechn.unimall.core.util;

/**
 * Description:
 * User: rize
 * Date: 2020/8/15
 * Time: 11:01
 */
public class ReflectUtil {

    /**
     * 通过Getter方法名称获取属性
     * @param getterName
     * @return
     */
    public static String getField(String getterName) {
        char[] dst = new char[getterName.length() - 3];
        getterName.getChars(3, getterName.length(), dst, 0);
        if ('A' <= dst[0] && 'Z' >= dst[0]) {
            dst[0] = (char) (dst[0] + 32);
        }
        return new String(dst);
    }

    /**
     * 通过属性名获取 Getter 或 Setter
     * @param fieldName
     * @param prefix "get" | "set"
     * @return
     */
    public static String getMethodName(String fieldName, String prefix) {
        char[] dst = new char[fieldName.length() + 3];
        // 1. 将prefix搞进去
        prefix.getChars(0, 3, dst, 0);
        // 2. 跟随考进去
        fieldName.getChars(0, fieldName.length(), dst, 3);
        if ('a' <= dst[3] && 'z' >= dst[3]) {
            dst[3] = (char) (dst[3] - 32);
        }
        return new String(dst);
    }

}
