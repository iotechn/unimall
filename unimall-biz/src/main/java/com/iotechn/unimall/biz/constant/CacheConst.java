package com.iotechn.unimall.biz.constant;

/**
 * Description:
 * User: rize
 * Date: 2020/8/1
 * Time: 12:11
 */
public class CacheConst {

    // 用户验证码
    public static final String USER_VERIFY_CODE_PREFIX = "USER_VERIFY_CODE:";

    // 广告缓存 ADVERTISEMENT_TYPE: + 广告类型(adType)
    public static final String ADVERTISEMENT_TYPE = "ADVERTISEMENT_TYPE:";

    // 评论缓存 CA_APPRAISE_KEY: + spuId + ":" + pageNo + ":" + pageSize
    public static final String APPRAISE_KEY = "APPRAISE_KEY:";

    // 三级类目树
    public static final String CATEGORY_THREE_LEVEL_TREE = "CATEGORY_THREE_LEVEL_TREE";

    //TODO???这是啥玩意？
    public static final String CATEGORY_ID_HASH = "CATEGORY_ID_HASH";

    //用户收藏商品缓存 COLLECT_USER:+ userID
    public static final String COLLECT_USER = "COLLECT_USER:";
}
