package com.iotechn.unimall.data.enums;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-08
 * Time: 下午6:46
 */

public enum  AdvertisementType {

    CAROUSEL(1, 3, "轮播"),
    CATEGORY_PICK(2, 1, "分类精选"),
    BANNER(3,3, "横幅"),
    HOME_BUTTON(4, 1, "首页5按钮")
    ;

    AdvertisementType(int code, int unionType, String msg) {
        this.code = code;
        this.unionType = unionType;
        this.msg = msg;
    }

    private int code;

    /**
     * unionType 1.类目 2.商品  1+2=3
     */
    private int unionType;

    private String msg;

    public int getCode() {
        return code;
    }

    public int getUnionType() {
        return unionType;
    }

    public String getMsg() {
        return msg;
    }

}

