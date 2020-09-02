package com.iotechn.unimall.data.search;

import com.iotechn.unimall.data.model.Page;
import com.iotechn.unimall.data.model.SearchEngineInitModel;
import com.iotechn.unimall.data.model.SearchWrapperModel;

import java.util.List;

/**
 * Description:
 * User: rize
 * Date: 2020/8/14
 * Time: 10:23
 */
public interface SearchEngine {

    /**
     * 若不成功，需要以异常的形式返回
     * 初始化引擎
     */
    public void init(SearchEngineInitModel initModel);

    /**
     * 判断是否初始化引擎
     * eg. 建立表结构等
     * @return
     */
    public boolean isInit();

    /**
     * 标识该搜索引擎是否可以完成自动初始化
     * @return
     */
    public boolean initAble();

    /**
     * 当用户进行新的动态配置时，需要重新加载属性
     */
    public void reloadProperties();

    /**
     * 数据同步
     */
    public void dataTransmission(Object object);

    /**
     * 传输一个被 SearchTable 注解的列表
     * @param list
     */
    public void dataTransmissionList(List list);

    /**
     * 删除一个文档 SearchTable 的对象 此对象必须包含主键
     * @param obj
     */
    public void deleteData(Object obj);

    /**
     * 去搜索引擎搜索信息
     * @param wrapperModel 搜索条件wrapper
     * @param clazz 搜索结果 映射的被SearchTable注解的 Java Pojo'class
     * @param <T>
     * @return
     */
    public <T> Page<T> search(SearchWrapperModel wrapperModel, Class<T> clazz);

}
