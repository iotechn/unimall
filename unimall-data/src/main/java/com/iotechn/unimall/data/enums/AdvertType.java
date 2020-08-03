package com.iotechn.unimall.data.enums;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2020-03-26
 * Time: 下午6:46
 */
public enum AdvertType {

    CAROUSEL(1, AdvertUnionType.values(), "轮播"),
    CLASSIFY_PICK(2, new AdvertUnionType[]{AdvertUnionType.CLASSIFY}, "分类精选"),
    BANNER(3, AdvertUnionType.values(), "横幅"),
    HOME_BUTTON(4, AdvertUnionType.values(), "首页5按钮"),
    POST_MSG(6, AdvertUnionType.values(), "公告"),
    STORE(7, AdvertUnionType.values(), "店铺推荐"),
    CATEGORY_PICK(8, new AdvertUnionType[]{AdvertUnionType.CATEGORY}, "品类精选"),
    PRODUCT_RECOMMEND(9, new AdvertUnionType[]{AdvertUnionType.PRODUCT}, "商品推荐");

    AdvertType(int code, AdvertUnionType unionType[], String msg) {
        this.code = code;
        this.unionType = unionType;
        this.msg = msg;
    }

    private int code;

    private AdvertUnionType[] unionType;

    private String msg;

    public int getCode() {
        return code;
    }

    public AdvertUnionType[] getUnionType() {
        return unionType;
    }

    public String getMsg() {
        return msg;
    }

}

