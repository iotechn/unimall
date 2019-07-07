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
    public static final ServiceExceptionDefinition ADMIN_TEMPLATE_INSERT_FAILED =
            new ServiceExceptionDefinition(50001, "运费模板主表增加失败");
    public static final ServiceExceptionDefinition ADMIN_CARRIAGE_INSERT_FAILED =
            new ServiceExceptionDefinition(50002, "运费模板副表增加失败");
    public static final ServiceExceptionDefinition ADMIN_TEMPLATE_DELETE_FAILED =
            new ServiceExceptionDefinition(50003, "运费模板主表删除失败");
    public static final ServiceExceptionDefinition ADMIN_CARRIAGE_DELETE_FAILED =
            new ServiceExceptionDefinition(50004, "运费模板副表删除失败");
    public static final ServiceExceptionDefinition ADMIN_TEMPLATE_UPDATE_FAILED =
            new ServiceExceptionDefinition(50005, "运费模板主表修改失败");
    public static final ServiceExceptionDefinition ADMIN_CARRIAGE_UPDATE_FAILED =
            new ServiceExceptionDefinition(50006, "运费模板副表修改失败");
    public static final ServiceExceptionDefinition ADMIN_TEMPLATE_QUERY_FAILED =
            new ServiceExceptionDefinition(50007, "运费模板主表查询失败");


}
