package com.iotechn.unimall.data.enums;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-08
 * Time: 下午3:18
 */
public enum RecommendType {
    WINDOW(1,"橱窗推荐")
    ;

    RecommendType(int code, String msg) {
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
