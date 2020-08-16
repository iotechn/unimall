package com.iotechn.unimall.data.search;

import com.alibaba.fastjson.JSONObject;
import com.iotechn.unimall.core.util.ReflectUtil;
import com.iotechn.unimall.data.annotation.SearchField;
import com.iotechn.unimall.data.annotation.SearchTable;
import com.iotechn.unimall.data.model.SearchEngineInitModel;
import com.iotechn.unimall.data.search.exception.SearchEngineException;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Description: 提供一些通用的方法 例如：解析类上面的注解等
 * User: rize
 * Date: 2020/8/14
 * Time: 15:24
 */
public abstract class AbstractSearchEngine implements SearchEngine {

    private Map<Class, List<String>> documentFieldsMap = new HashMap<>();

    private Map<Class, Map<String, SearchField>> documentFieldsAnnotationMap = new HashMap<>();

    /**
     * 将SearchTable类转换为STable的行为（Action）
     */
    private Function<Class, STable> mapToSTable = clazz -> {
        SearchTable searchTable = (SearchTable) clazz.getAnnotation(SearchTable.class);
        if (searchTable == null) {
            throw new SearchEngineException("传入搜索引擎模型,并非SearchTable");
        }
        // 1. 获取欲设置为的表名
        STable table = new STable();
        table.setTitle(searchTable.value());

        // 2. 获取欲写入的字段
        Field[] fields = clazz.getFields();
        List<SField> fieldList = Arrays.stream(fields).map(item -> {
            SearchField searchField = item.getAnnotation(SearchField.class);
            SField field = new SField();
            field.setTitle(searchField.value());
            field.setType(item.getType());
            return field;
        }).filter(item -> !StringUtils.isEmpty(item.getTitle())).collect(Collectors.toList());

        table.setFields(fieldList);

        return table;
    };

    /**
     * 将需要初始化的表列表 转换为具体的 pojo
     * @param model
     * @return
     */
    protected List<STable> parseSearchTable(SearchEngineInitModel model) {
        List<Class> tables = model.getTables();
        List<STable> stables = tables.stream().map(mapToSTable).collect(Collectors.toList());
        return stables;
    }

    protected STable parseSearchTableClass(Class clazz) {
        return mapToSTable.apply(clazz);
    }

    /**
     * 以SearchField为Key构建一个JSON对象，并以字符串的形式返回
     * @param obj
     * @return
     */
    protected String getSearchTableJsonStr(Object obj) {
        return JSONObject.toJSONString(getSearchTableJsonObject(obj));
    }

    /**
     * 以SearchField为Key构建一个JSON对象，并以JSON对象的形式返回
     * @param obj
     * @return
     */
    protected Object getSearchTableJsonObject(Object obj) {
        Class clazz = obj.getClass();
        SearchTable searchTable = (SearchTable) clazz.getDeclaredAnnotation(SearchTable.class);
        if (searchTable == null) {
            throw new SearchEngineException("传入搜索引擎模型,并非SearchTable");
        }
        Field[] fields = clazz.getDeclaredFields();
        Map<String, Object> o = new HashMap<>();
        for (Field field : fields) {
            SearchField searchField = field.getDeclaredAnnotation(SearchField.class);
            if (searchField != null) {
                // 询问Field中的值存不存在
                try {
                    Object f = clazz.getMethod(ReflectUtil.getMethodName(field.getName(), "get")).invoke(obj);
                    if (f != null) {
                        o.put(searchField.value(), f);
                    }
                } catch (NoSuchMethodException e) {
                    throw new SearchEngineException("请为Pojo提供Getter与Setter");
                } catch (IllegalAccessException e) {
                    throw new SearchEngineException("请为Pojo提供Getter与Setter");
                } catch (InvocationTargetException e) {
                    throw new SearchEngineException("请为Pojo提供Getter与Setter");
                }
            }
        }
        return o;
    }

    /**
     * 获取文档SearchFiled
     * @param clazz
     * @return
     */
    protected List<String> getDocumentFields(Class clazz) {
        List<String> documentFields = documentFieldsMap.get(clazz);
        if (documentFields == null) {
            synchronized (this) {
                // 保证写入的时候只有一个线程在运行 借鉴 单例的 DCL好像是这个名字 Double Check Lock
                documentFields = documentFieldsMap.get(clazz);
                if (documentFields == null) {
                    // 尝试从缓存中获取
                    SearchTable searchTable = (SearchTable) clazz.getDeclaredAnnotation(SearchTable.class);
                    if (searchTable == null) {
                        throw new SearchEngineException("传入搜索引擎模型,并非SearchTable");
                    }
                    Field[] fields = clazz.getDeclaredFields();
                    List<SearchField> annotationList = Arrays.stream(fields).map(item -> item.getDeclaredAnnotation(SearchField.class)).filter(item -> item != null).collect(Collectors.toList());
                    documentFields = annotationList.stream().map(item -> item.value()).collect(Collectors.toList());
                    documentFieldsMap.put(clazz, documentFields);
                    // 同时缓存映射
                    Map<String, SearchField> annotationMap = annotationList.stream().collect(Collectors.toMap(SearchField::value, item -> item));
                    documentFieldsAnnotationMap.put(clazz, annotationMap);
                }
            }
        }
        return documentFields;
    }

    /**
     * 获取注解
     * @param clazz
     * @param field
     * @return
     */
    protected SearchField getAnnotation(Class clazz, String field) {
        // 确保初始化
        getDocumentFields(clazz);
        return documentFieldsAnnotationMap.get(clazz).get(field);
    }

    /**
     * 搜索引擎表名和字段的pojo
     */
    @Data
    protected static class STable {

        private String title;

        private List<SField> fields;

    }

    /**
     * 对字段展开描述的Pojo
     */
    @Data
    protected static class SField {

        private String title;

        private Class type;

    }

}
