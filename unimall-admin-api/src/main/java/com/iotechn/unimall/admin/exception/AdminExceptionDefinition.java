package com.iotechn.unimall.admin.exception;

import com.iotechn.unimall.core.exception.ServiceExceptionDefinition;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-07
 * Time: 下午4:22
 */
public class AdminExceptionDefinition {
    public static final ServiceExceptionDefinition FREIGHT_TEMPLATE_INSERT_FAILED =
            new ServiceExceptionDefinition(51001, "运费模板主表增加失败");
    public static final ServiceExceptionDefinition FREIGHT_CARRIAGE_INSERT_FAILED =
            new ServiceExceptionDefinition(51002, "运费模板副表增加失败");
    public static final ServiceExceptionDefinition FREIGHT_TEMPLATE_DELETE_FAILED =
            new ServiceExceptionDefinition(51003, "运费模板主表删除失败");
    public static final ServiceExceptionDefinition FREIGHT_CARRIAGE_DELETE_FAILED =
            new ServiceExceptionDefinition(51004, "运费模板副表删除失败");
    public static final ServiceExceptionDefinition FREIGHT_TEMPLATE_UPDATE_FAILED =
            new ServiceExceptionDefinition(51005, "运费模板主表修改失败");
    public static final ServiceExceptionDefinition FREIGHT_CARRIAGE_UPDATE_FAILED =
            new ServiceExceptionDefinition(51006, "运费模板副表修改失败");
    public static final ServiceExceptionDefinition FREIGHT_TEMPLATE_QUERY_FAILED =
            new ServiceExceptionDefinition(51007, "运费模板主表查询失败");
    public static final ServiceExceptionDefinition FREIGHT_SPU_QUERY_HAS =
            new ServiceExceptionDefinition(51007, "当前仍有商品使用该模板");


}
