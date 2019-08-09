package com.iotechn.unimall.data.enums;

/**
 * Created by rize on 2019/2/11.
 */
public enum ShipCodeType {
    SF("SF","顺丰速运"),
    HTKY("HTKY", "百世快递"),
    ZTO("ZTO", "中通快递"),
    STO("STO", "申通快递"),
    YTO("YTO", "圆通速递"),
    YD("YD", "韵达速递"),
    YZPY("YZPY", "邮政快递包裹"),
    EMS("EMS", "EMS"),
    HHTT("HHTT", "天天快递"),
    JD("JD", "京东快递"),
    UC("UC", "优速快递"),
    DBL("DBL", "德邦快递"),
    ZJS("ZJS", "宅急送"),
    TNT("TNT", "TNT快递"),


    OTHERS("OTHERS", "其他快递");


    private String code;

    private String msg;

    ShipCodeType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }

    public static ShipCodeType getByCode(String code) {
        for (ShipCodeType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return OTHERS;
    }

}
