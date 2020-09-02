package com.iotechn.unimall.data.enums;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2020-03-26
 * Time: 下午6:46
 */
public enum AdvertType {

    CAROUSEL(1, "轮播"),
    CATEGORY_PICK(2, "分类精选"),
    BANNER(3, "横幅"),
    HOME_BUTTON(4, "首页5按钮"),
    POST_MSG(6, "公告"),
    PRODUCT_RECOMMEND(9, "商品推荐");

    AdvertType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}

