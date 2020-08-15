package com.iotechn.unimall.data.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SearchUtil {

    /**
     * 将一个实例以属性名为键，属性值为值的键值对查询方式加入map
     */
    public static Map<String, Object> setConditionMap(Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (obj == null) {
            return null;
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        Field[] superFields = obj.getClass().getSuperclass().getDeclaredFields();
        addField(map, fields, obj);
        addField(map, superFields, obj);
        return map;
    }

    private static void addField(Map map, Field[] fields, Object obj) {
        if (fields == null || fields.length == 0) {
            return;
        }

        for (Field field : fields) {
            String fieldName = field.getName();
            fieldName = hump(fieldName);
            boolean accessFlag = field.isAccessible();
            try {
                field.setAccessible(true);
                Object o = field.get(obj);

                // 因为开放搜索使用utf-8编码，所以谨慎一点
                if (o instanceof String) {
                    byte[] bytes;
                    String temp = String.valueOf(o);
                    try {
                        bytes = temp.getBytes("utf-8");
                        o = new String(bytes, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

                // 将日期转换成数字型传过去
                if (o instanceof Date) {
                    o = ((Date) o).getTime();
                }
                map.put(fieldName, o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } finally {
                field.setAccessible(accessFlag);
            }
        }
    }

    private static String hump(String fieldName) {
        String[] standard = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        for (int i = 0; i < standard.length; i++) {
            if (fieldName.contains(standard[i])) {
                fieldName = fieldName.replace(standard[i], "_" + standard[i].toLowerCase());
            }
        }
        return fieldName;
    }
}
