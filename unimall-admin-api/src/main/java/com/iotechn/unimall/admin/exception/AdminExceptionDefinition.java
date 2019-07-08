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

    public static final ServiceExceptionDefinition ADMIN_UNKNOWN_EXCEPTION =
            new ServiceExceptionDefinition(50000, "管理员系统未知异常");

    public static final ServiceExceptionDefinition ADMIN_NOT_EXIST =
            new ServiceExceptionDefinition(50001, "管理员不存在");

    public static final ServiceExceptionDefinition ADMIN_PASSWORD_ERROR =
            new ServiceExceptionDefinition(50002, "密码错误");

    public static final ServiceExceptionDefinition ADMIN_NOT_BIND_WECHAT =
            new ServiceExceptionDefinition(50003, "管理员尚未绑定微信");

    public static final ServiceExceptionDefinition ADMIN_APPLY_NOT_BELONGS_TO_YOU =
            new ServiceExceptionDefinition(50004, "用户申请表并不属于您");

    public static final ServiceExceptionDefinition ADMIN_APPLY_NOT_SUPPORT_ONE_KEY =
            new ServiceExceptionDefinition(50005, "未定义类型不支持一键发布");

    public static final ServiceExceptionDefinition ADMIN_ROLE_IS_EMPTY =
            new ServiceExceptionDefinition(50006, "管理员角色为空！");

    public static final ServiceExceptionDefinition ADMIN_USER_NAME_REPEAT =
            new ServiceExceptionDefinition(50007, "管理员用户名重复");



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

    public static final ServiceExceptionDefinition RECOMMEND_SPU_NO_HAS =
            new ServiceExceptionDefinition(52001, "你要加入推荐的商品不存在");
    public static final ServiceExceptionDefinition RECOMMEND_ALREADY_HAS =
            new ServiceExceptionDefinition(52002, "你要加入推荐的商品已推荐");
    public static final ServiceExceptionDefinition RECOMMEND_SQL_ADD_FAILED =
            new ServiceExceptionDefinition(52003, "加入推荐数据库失败");
    public static final ServiceExceptionDefinition RECOMMEND_SQL_DELETE_FAILED =
            new ServiceExceptionDefinition(52004, "删除推荐数据库失败");



}
