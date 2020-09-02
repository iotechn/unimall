package com.iotechn.unimall.core.exception;

/**
 * Created by rize on 2019/7/1.
 */
public class ExceptionDefinition {

    public static final ServiceExceptionDefinition THIRD_PART_SERVICE_EXCEPTION =
            new ServiceExceptionDefinition(0, "第三方服务异常");

    public static final ServiceExceptionDefinition PLUGIN_EXCEPTION =
            new ServiceExceptionDefinition(1, "插件异常: ${0}");

    public static final ServiceExceptionDefinition THIRD_PART_IO_EXCEPTION =
            new ServiceExceptionDefinition(2, "第三方网络异常");

    public static final ServiceExceptionDefinition APP_UNKNOWN_EXCEPTION =
            new ServiceExceptionDefinition(10000, "系统未知异常");

    public static final ServiceExceptionDefinition PARAM_CHECK_FAILED =
            new ServiceExceptionDefinition(10002, "参数校验失败");

    public static final ServiceExceptionDefinition SYSTEM_BUSY =
            new ServiceExceptionDefinition(10007, "系统繁忙～");

    public static final ServiceExceptionDefinition USER_UNKNOWN_EXCEPTION =
            new ServiceExceptionDefinition(11000, "用户系统未知异常");

    public static final ServiceExceptionDefinition USER_SEND_VERIFY_FAILED =
            new ServiceExceptionDefinition(11001, "发送验证码失败");

    public static final ServiceExceptionDefinition USER_VERIFY_CODE_NOT_EXIST =
            new ServiceExceptionDefinition(11002, "验证码未发送或已过期");

    public static final ServiceExceptionDefinition USER_VERIFY_CODE_NOT_CORRECT =
            new ServiceExceptionDefinition(11003, "验证码不正确");

    public static final ServiceExceptionDefinition USER_PHONE_HAS_EXISTED =
            new ServiceExceptionDefinition(11004, "手机已经被注册");

    public static final ServiceExceptionDefinition USER_PHONE_NOT_EXIST =
            new ServiceExceptionDefinition(11005, "手机尚未绑定账号");

    public static final ServiceExceptionDefinition USER_PHONE_OR_PASSWORD_NOT_CORRECT =
            new ServiceExceptionDefinition(11006, "手机号或密码错误!");

    public static final ServiceExceptionDefinition USER_THIRD_PART_LOGIN_FAILED =
            new ServiceExceptionDefinition(11007, "用户第三方登录失败");

    public static final ServiceExceptionDefinition USER_THIRD_UNEXPECT_RESPONSE =
            new ServiceExceptionDefinition(11008, "第三方登录期望之外的错误");

    public static final ServiceExceptionDefinition USER_THIRD_PART_NOT_SUPPORT =
            new ServiceExceptionDefinition(11009, "未知的第三方登录平台");

    public static final ServiceExceptionDefinition USER_INFORMATION_MISSING =
            new ServiceExceptionDefinition(11010, "用户信息缺失，不能添加");

    public static final ServiceExceptionDefinition USER_PHONE_ALREADY_EXIST =
            new ServiceExceptionDefinition(11011, "用户电话已经存在，不能添加");

    public static final ServiceExceptionDefinition USER_CAN_NOT_ACTICE =
            new ServiceExceptionDefinition(11012, "用户处于冻结状态，请联系管理员");


    public static final ServiceExceptionDefinition CART_UPDATE_FAILED =
            new ServiceExceptionDefinition(12001, "购物车更新失败");

    public static final ServiceExceptionDefinition ORDER_UNKNOWN_EXCEPTION =
            new ServiceExceptionDefinition(13000, "订单系统未知异常");

    public static final ServiceExceptionDefinition ORDER_PARAM_CHECK_FAILED =
            new ServiceExceptionDefinition(13001, "订单参数校验失败");

    public static final ServiceExceptionDefinition ORDER_SYSTEM_BUSY =
            new ServiceExceptionDefinition(13002, "订单系统繁忙~");

    public static final ServiceExceptionDefinition ORDER_SKU_STOCK_NOT_ENOUGH =
            new ServiceExceptionDefinition(13003, "商品库存不足～");

    public static final ServiceExceptionDefinition ORDER_SKU_NOT_EXIST =
            new ServiceExceptionDefinition(13004, "商品并不存在~");

    public static final ServiceExceptionDefinition ORDER_PRICE_MUST_GT_ZERO =
            new ServiceExceptionDefinition(13005, "订单金额必须大于0");

    public static final ServiceExceptionDefinition ORDER_PRODUCT_PRICE_HAS_BEEN_CHANGED =
            new ServiceExceptionDefinition(13006, "商品价格已经发生改变，请重新下单！");

    public static final ServiceExceptionDefinition ORDER_COUPON_NOT_EXIST =
            new ServiceExceptionDefinition(13007, "优惠券不存在或已使用！");

    public static final ServiceExceptionDefinition ORDER_COUPON_PRICE_NOT_ENOUGH =
            new ServiceExceptionDefinition(13008, "优惠券金额未达到指定值");

    public static final ServiceExceptionDefinition ORDER_COUPON_DISCOUNT_CHECK_FAILED =
            new ServiceExceptionDefinition(13009, "订单优惠券金额校验失败");

    public static final ServiceExceptionDefinition ORDER_ADDRESS_NOT_BELONGS_TO_YOU =
            new ServiceExceptionDefinition(13010, "收货地址不属于您！");

    public static final ServiceExceptionDefinition ORDER_NOT_EXIST =
            new ServiceExceptionDefinition(13011, "订单并不存在");

    public static final ServiceExceptionDefinition ORDER_STATUS_NOT_SUPPORT_PAY =
            new ServiceExceptionDefinition(13012, "订单状态不支持支付");

    public static final ServiceExceptionDefinition ORDER_STATUS_CHANGE_FAILED =
            new ServiceExceptionDefinition(13013, "订单状态流转失败！");

    public static final ServiceExceptionDefinition ORDER_STATUS_NOT_SUPPORT_REFUND =
            new ServiceExceptionDefinition(13014, "订单状态不支持退款");

    public static final ServiceExceptionDefinition ORDER_REFUND_FAILED =
            new ServiceExceptionDefinition(13015, "微信退款失败 请检查微信账户余额");

    public static final ServiceExceptionDefinition ORDER_STATUS_NOT_SUPPORT_CANCEL =
            new ServiceExceptionDefinition(13016, "订单状态不支持取消");

    public static final ServiceExceptionDefinition ORDER_STATUS_NOT_SUPPORT_CONFIRM =
            new ServiceExceptionDefinition(13016, "订单状态不支持确认收货");

    public static final ServiceExceptionDefinition ORDER_HAS_NOT_SHIP =
            new ServiceExceptionDefinition(13017, "订单尚未发货");

    public static final ServiceExceptionDefinition ORDER_DID_NOT_SET_SHIP =
            new ServiceExceptionDefinition(13018, "订单不需要物流公司");

    public static final ServiceExceptionDefinition ORDER_LOGIN_TYPE_NOT_SUPPORT_WXPAY =
            new ServiceExceptionDefinition(13019, "当前平台不支持微信支付");

    public static final ServiceExceptionDefinition ORDER_DO_NOT_EXIST_SHIP_TRACE =
            new ServiceExceptionDefinition(13020, "暂时没有物流信息");

    public static final ServiceExceptionDefinition ORDER_PAY_CHANNEL_NOT_SUPPORT_REFUND =
            new ServiceExceptionDefinition(13021, "订单支付方式不支持退款");

    public static final ServiceExceptionDefinition ORDER_GROUP_SPU_CAN_SINGLE_TAKE =
            new ServiceExceptionDefinition(13022, "团购订单只允许单品结算");

    public static final ServiceExceptionDefinition ORDER_GROUP_SHOP_NOT_EXIST_OR_EXPIRED =
            new ServiceExceptionDefinition(13023, "团购活动已经过期或不存在");

    public static final ServiceExceptionDefinition ORDER_REFUND_SUM_MOST_LOWER_THAN_PAY_PRICE =
            new ServiceExceptionDefinition(13024, "订单退款金额需要小于等于支付金额");

    public static final ServiceExceptionDefinition ORDER_SPU_NOT_SELLING =
            new ServiceExceptionDefinition(13025, "有商品没有上架");

    public static final ServiceExceptionDefinition ORDER_ADDRESS_CANNOT_BE_NULL =
            new ServiceExceptionDefinition(13026, "请完善收货地址~");

    public static final ServiceExceptionDefinition ORDER_GROUP_SHOP_ACTIVITY_HAS_OVER =
            new ServiceExceptionDefinition(13027, "团购活动已经结束");

    public static final ServiceExceptionDefinition ORDER_REFUND_KEY_PATH_ERROR =
            new ServiceExceptionDefinition(13028, "退款证书路径不正确，请参照系统配置放置");


    public static final ServiceExceptionDefinition COUPON_ISSUE_OVER =
            new ServiceExceptionDefinition(14001, "优惠券已经领完～");

    public static final ServiceExceptionDefinition COUPON_YOU_HAVE_OBTAINED =
            new ServiceExceptionDefinition(14002, "您已经领取过了～");

    public static final ServiceExceptionDefinition COUPON_ACTIVITY_NOT_START =
            new ServiceExceptionDefinition(14003, "优惠券活动还没开始");

    public static final ServiceExceptionDefinition COUPON_ACTIVITY_HAS_END =
            new ServiceExceptionDefinition(14004, "优惠券活动已经结束");

    public static final ServiceExceptionDefinition COUPON_HAS_LOCKED =
            new ServiceExceptionDefinition(14005, "优惠券活动已经冻结！");

    public static final ServiceExceptionDefinition COUPON_STRATEGY_INCORRECT =
            new ServiceExceptionDefinition(14006, "优惠券策略不正确");

    public static final ServiceExceptionDefinition COUPON_NOT_EXIST =
            new ServiceExceptionDefinition(14006, "优惠券不存在");

    public static final ServiceExceptionDefinition COUPON_CHECK_DATA_FAILED =
            new ServiceExceptionDefinition(14006, "优惠券审核数据失败");



    public static final ServiceExceptionDefinition ADDRESS_QUERY_FAILED  =
            new ServiceExceptionDefinition(16001, "这是个有地址却没有默认地址的用户");

    public static final ServiceExceptionDefinition ADDRESS_DATABASE_QUERY_FAILED  =
            new ServiceExceptionDefinition(16002, "执行语句失败");

    public static final ServiceExceptionDefinition APPRAISE_PARAM_CHECK_FAILED  =
            new ServiceExceptionDefinition(17001, "参数校验失败");

    public static final ServiceExceptionDefinition APPRAISE_ORDER_CHECK_FAILED  =
            new ServiceExceptionDefinition(17002, "当前状态不允许评价");


    public static final ServiceExceptionDefinition FREIGHT_PARAM_CHECK_FAILED  =
            new ServiceExceptionDefinition(18001, "邮费传入参数校验失败");

    public static final ServiceExceptionDefinition FREIGHT_TEMPLATE_NOT_EXIST =
            new ServiceExceptionDefinition(18002, "运费模版不存在");

    public static final ServiceExceptionDefinition FREIGHT_TEMPLATE_INSERT_FAILED =
            new ServiceExceptionDefinition(18003, "运费模板主表增加失败");

    public static final ServiceExceptionDefinition FREIGHT_CARRIAGE_INSERT_FAILED =
            new ServiceExceptionDefinition(18004, "运费模板副表增加失败");

    public static final ServiceExceptionDefinition FREIGHT_TEMPLATE_DELETE_FAILED =
            new ServiceExceptionDefinition(18005, "运费模板主表删除失败");

    public static final ServiceExceptionDefinition FREIGHT_CARRIAGE_DELETE_FAILED =
            new ServiceExceptionDefinition(18006, "运费模板副表删除失败");

    public static final ServiceExceptionDefinition FREIGHT_TEMPLATE_UPDATE_FAILED =
            new ServiceExceptionDefinition(18007, "运费模板主表修改失败");

    public static final ServiceExceptionDefinition FREIGHT_CARRIAGE_UPDATE_FAILED =
            new ServiceExceptionDefinition(18008, "运费模板副表修改失败");

    public static final ServiceExceptionDefinition FREIGHT_TEMPLATE_QUERY_FAILED =
            new ServiceExceptionDefinition(18009, "运费模板主表查询失败");

    public static final ServiceExceptionDefinition FREIGHT_SPU_QUERY_HAS =
            new ServiceExceptionDefinition(18010, "当前仍有商品使用该模板");


    public static final ServiceExceptionDefinition CATEGORY_OUGHT_TO_EMPTY =
            new ServiceExceptionDefinition(19001, "该类目还有子类目或着商品");

    public static final ServiceExceptionDefinition CATEGORY_OR_PARENT_NODE_IS_EMPTY =
            new ServiceExceptionDefinition(19002, "传入ID，父节点ID不能为空");

    public static final ServiceExceptionDefinition CATEGORY_EXIST_ADVERT =
            new ServiceExceptionDefinition(19003, "类目被广告关联，请先移除广告");

    public static final ServiceExceptionDefinition CATEGORY_NOT_FIND_PARENT_NODE_OR_NODE =
            new ServiceExceptionDefinition(19004, "未在数据库中查找到父节点或本节点");

    public static final ServiceExceptionDefinition CATEGORY_UPDATE_FAILURE =
            new ServiceExceptionDefinition(19005, "类目数据库修改失败");

    public static final ServiceExceptionDefinition CATEGORY_PARENT_NODE_INFORMATION_ERROR =
            new ServiceExceptionDefinition(19006, "父节点信息不准确");


    public static final ServiceExceptionDefinition GOODS_NOT_EXIST =
            new ServiceExceptionDefinition(20001, "商品并不存在");

    public static final ServiceExceptionDefinition GOODS_SKU_LIST_EMPTY =
            new ServiceExceptionDefinition(20002, "商品的类型（Sku）列表不能为空");

    public static final ServiceExceptionDefinition GOODS_CREATE_HAS_ID =
            new ServiceExceptionDefinition(20003, "创建商品时请不要传入Id");

    public static final ServiceExceptionDefinition GOODS_CREATE_BARCODE_REPEAT =
            new ServiceExceptionDefinition(20004, "商品条码已经存在了 商品Id:${0} 重复Sku:${1}");

    public static final ServiceExceptionDefinition GOODS_PRICE_CHECKED_FAILED =
            new ServiceExceptionDefinition(20005, "必须 vip价格 <= 现价 <= 原价");

    public static final ServiceExceptionDefinition GOODS_NEED_STATUS_ERROR =
            new ServiceExceptionDefinition(20006, "商品已经是该状态,无法改变");

    public static final ServiceExceptionDefinition GOODS_UPDATE_SQL_FAILED =
            new ServiceExceptionDefinition(20007, "商品执行修改SQL失败");

    public static final ServiceExceptionDefinition GOODS_UPLOAD_SKU_BARCODE_REPEAT =
            new ServiceExceptionDefinition(20008, "您上传的SKU列表中条码重复");

    public static final ServiceExceptionDefinition GOODS_ORDER_BY_WAY_ILLEGAL =
            new ServiceExceptionDefinition(20009, "商品排序方式不合法");

    public static final ServiceExceptionDefinition GOODS_UNION_ACTIVITY_CAN_NOT_BE_OFF_SHELF =
            new ServiceExceptionDefinition(20010, "商品关联的活动正在进行，不允许下架");

    public static final ServiceExceptionDefinition GOODS_EXIST_ADVERT =
            new ServiceExceptionDefinition(20011, "商品存在广告，请先移除广告");

    public static final ServiceExceptionDefinition GOODS_SPU_EXIST_ACTIVITY =
            new ServiceExceptionDefinition(20012, "商品已经存在活动，不能继续添加");


    public static final ServiceExceptionDefinition GROUP_SHOP_SPU_NO_EXITS_OR_ONLY_SPU =
            new ServiceExceptionDefinition(21001, "团购商品中对应的spu不存在或只有spu存在,没有对应sku存在");

    public static final ServiceExceptionDefinition GROUP_SHOP_SKU_NUMBER_ERROR =
            new ServiceExceptionDefinition(21002, "团购商品sku数量不对应");

    public static final ServiceExceptionDefinition GROUP_SHOP_SKU_ID_ERROR =
            new ServiceExceptionDefinition(21003, "团购商品sku所对应的sku_id错误.");

    public static final ServiceExceptionDefinition GROUP_SHOP_SKU_PRICE_ERROR =
            new ServiceExceptionDefinition(21004, "团购商品sku价格为空,或者为0");

    public static final ServiceExceptionDefinition GROUP_SHOP_SKU_GROUP_SHOP_ID_ERROR =
            new ServiceExceptionDefinition(21005, "团购商品sku的团购商品spuID和传入的不一致");

    public static final ServiceExceptionDefinition GROUP_SHOP_SPU_ADD_SQL_QUERY_ERROR =
            new ServiceExceptionDefinition(21006, "团购商品spu添加出误");

    public static final ServiceExceptionDefinition GROUP_SHOP_SKU_ADD_SQL_QUERY_ERROR =
            new ServiceExceptionDefinition(21007, "团购商品sku添加出错");

    public static final ServiceExceptionDefinition GROUP_SHOP_SPU_DELETE_SQL_QUERY_ERROR =
            new ServiceExceptionDefinition(21008, "团购商品spu删除出错");

    public static final ServiceExceptionDefinition GROUP_SHOP_SKU_DELETE_SQL_QUERY_ERROR =
            new ServiceExceptionDefinition(21009, "团购商品sku删除出错");

    public static final ServiceExceptionDefinition GROUP_SHOP_SPU_NO_EXITS =
            new ServiceExceptionDefinition(21010, "团购商品spu不存在");

    public static final ServiceExceptionDefinition GROUP_SHOP_SPU_UPDATE_SQL_QUERY_ERROR =
            new ServiceExceptionDefinition(21011, "团购商品spu更新失败");

    public static final ServiceExceptionDefinition GROUP_SHOP_START_MUST_LESS_THAN_END =
            new ServiceExceptionDefinition(21012, "团购开始时间必须小于结束时间");

    public static final ServiceExceptionDefinition ORDER_IS_NOT_GROUP_SHOP_STATUS =
            new ServiceExceptionDefinition(21013, "订单状态不是团购状态");

    public static final ServiceExceptionDefinition GROUP_SHOP_ALREADY_EXIT =
            new ServiceExceptionDefinition(21014, "该商品已经是团购商品");

    public static final ServiceExceptionDefinition GROUP_SHOP_ALREADY_ACTIVE =
            new ServiceExceptionDefinition(21015, "团购商品已经在团购中.无法进行编辑或修改操作");


    public static final ServiceExceptionDefinition ADVERTISEMENT_SQL_ADD_FAILED =
            new ServiceExceptionDefinition(22001, "添加广告数据库失败");

    public static final ServiceExceptionDefinition ADVERTISEMENT_SQL_DELETE_FAILED =
            new ServiceExceptionDefinition(22002, "删除广告数据库失败");

    public static final ServiceExceptionDefinition ADVERTISEMENT_SQL_UPDATE_FAILED =
            new ServiceExceptionDefinition(22003, "修改广告数据库失败");


    public static final ServiceExceptionDefinition SEARCH_ENGINE_INNER_EXCEPTION =
            new ServiceExceptionDefinition(23000, "搜索引擎:${0}");

    public static final ServiceExceptionDefinition SEARCH_ENGINE_NOT_SET =
            new ServiceExceptionDefinition(23001, "没有任何搜索引擎被加载！若已配置请尝试重启后端服务！");

    public static final ServiceExceptionDefinition SEARCH_ENGINE_NOT_SUPPORT_AUTO_INIT =
            new ServiceExceptionDefinition(23002, "搜索引擎不支持自动初始化");

    public static final ServiceExceptionDefinition SEARCH_ENGINE_HAS_INITED =
            new ServiceExceptionDefinition(23003, "搜索引擎已经被初始化,无需重复执行");


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

    public static final ServiceExceptionDefinition ADMIN_VERIFYCODE_ERROR =
            new ServiceExceptionDefinition(50008, "登陆验证码错误");

    public static final ServiceExceptionDefinition ADMIN_USER_NOT_EXITS =
            new ServiceExceptionDefinition(50009, "请输入正确账号密码");

    public static final ServiceExceptionDefinition ADMIN_GUEST_NOT_NEED_VERIFY_CODE =
            new ServiceExceptionDefinition(50010, "游客用户无须验证码，请直接输入666666");

    public static final ServiceExceptionDefinition ADMIN_VERIFY_CODE_SEND_FAIL=
            new ServiceExceptionDefinition(50011, "登陆验证码发送失败");

    public static final ServiceExceptionDefinition ADMIN_GENERATOR_WORK_DIR_NOT_EXIST =
            new ServiceExceptionDefinition(50012, "工作路径不正确");

    public static final ServiceExceptionDefinition ADMIN_GENERATOR_FILE_ALREADY_EXIST =
            new ServiceExceptionDefinition(50013, "欲生成的文件已经存在");

    public static final ServiceExceptionDefinition ADMIN_GENERATOR_IO_EXCEPTION =
            new ServiceExceptionDefinition(50013, "代码生成网络异常");

    public static final ServiceExceptionDefinition ADMIN_GENERATOR_TEMPLATE_EXCEPTION =
            new ServiceExceptionDefinition(50013, "代码生成模板异常");

    public static final ServiceExceptionDefinition ADMIN_ROLE_UNION_ADMIN =
            new ServiceExceptionDefinition(50014, "角色关联仍有管理员关联");


    public static final ServiceExceptionDefinition ORDER_EXCEL_PARAM_ERROR =
            new ServiceExceptionDefinition(52001, "生成excel查询参数错误");




    public static ServiceExceptionDefinition buildVariableException(ServiceExceptionDefinition definition, String ...args) {
        String msg = definition.getMsg();
        for (int i = 0; i < args.length; i++) {
            msg = msg.replace("${" + i + "}", args[i]);
        }
        return new ServiceExceptionDefinition(definition.getCode(), msg);
    }




}
