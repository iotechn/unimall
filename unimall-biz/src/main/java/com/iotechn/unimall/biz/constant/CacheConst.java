package com.iotechn.unimall.biz.constant;

/**
 * Description:
 * User: rize
 * Date: 2020/8/1
 * Time: 12:11
 */
public class CacheConst {

    /**
     *  用户验证码
     */
    public static final String USER_VERIFY_CODE_PREFIX = "USER_VERIFY_CODE:";

    /**
     * 管理员登陆验证码
     * ADMIN_MSG_CODE: + 电话
     */
    public final static String ADMIN_MSG_CODE = "ADMIN_MSG_CODE:";

    /**
     * 广告缓存 (ADVERT_TYPE: + 广告类型(adType))
     */
    public static final String ADVERT_TYPE = "ADVERT_TYPE:";

    /**
     * 评论缓存 APPRAISE_KEY: + spuId + ":" + pageNo + ":" + pageSize
     */
    public static final String APPRAISE_KEY = "APPRAISE_KEY:";

    /**
     * 三级类目树
     */
    public static final String CATEGORY_THREE_LEVEL_TREE = "CATEGORY_THREE_LEVEL_TREE";

    /**
     * 包含所有类目的一个链表
     */
    public static final String CATEGORY_ALL_LIST = "CATEGORY_ALL_LIST";

    /**
     * 二级类目树
     */
    public static final String CATEGORY_SECOND_LEVEL_TREE = "CATEGORY_SECOND_LEVEL_TREE";

    /**
     * 一个类目ID的HASH键值对
     * "S" + 三级类目ID : 二级类目ID + "_" + 一级类目ID
     */
    public static final String CATEGORY_ID_HASH = "CATEGORY_ID_HASH";

    /**
     * 获取足迹列表+_用户Id (追加)
     */
    public static final String FOOTPRINT_LRU = "FOOTPRINT_LRU:";

    /**
     * 用户收藏商品缓存 COLLECT_USER:+ userID
     */
    public static final String COLLECT_USER = "COLLECT_USER:";

    public static final String USER_OFFICIAL_WECHAT_ACCESS = "USER_OFFICIAL_WECHAT_ACCESS";

    public static final String USER_OFFICIAL_WECHAT_TICKET = "USER_OFFICIAL_WECHAT_TICKET";

    public static final String USER_MINI_WECHAT_ACCESS = "USER_MINI_WECHAT_ACCESS";

    /**
     * 商品类目内价格排序缓存 (追加+=类目)
     */
    public static final String PRT_CATEGORY_ORDER_PRICE_ZSET = "PRT_CATEGORY_ORDER_PRICE:";

    public static final String PRT_CATEGORY_ORDER_ID_ZSET = "PRT_CATEGORY_ORDER_ID:";

    public static final String PRT_CATEGORY_ORDER_SALES_ZSET = "PRT_CATEGORY_ORDER_SALES:";

    /**
     * 商品spuId - SpuDO 映射表不包括detail字段 (单独)
     */
    public static final String PRT_SPU_HASH_BUCKET = "PRT_SPU_HASH_BUCKET";

    /**
     * 商品spuId - SpuDTO 详情、SkuList、图片、评论、属性等字段 (单独)
     */
    public static final String PRT_SPU_DETAIL_HASH_BUCKET = "PRT_SPU_DETAIL_HASH_BUCKET";

    /**
     * 商品skuId - 库存缓存 (单独)
     */
    public static final String PRT_SKU_STOCK_BUCKET = "CA_PRODUCT_SKU_DETAIL_HASH_BUCKET";

    /**
     * 团购商品缓存
     * GROUP_SHOP_LIST: + pageNo + ":" + pageSize
     */
    public static final String GROUP_SHOP_LIST = "GROUP_SHOP_LIST:";

}
