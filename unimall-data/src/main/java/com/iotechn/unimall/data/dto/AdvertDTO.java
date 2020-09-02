package com.iotechn.unimall.data.dto;

import lombok.Data;

/**
 * Created by rize on 2019/7/14.
 */
@Data
public class AdvertDTO extends SuperDTO {

    private Integer type;

    private Integer unionType;

    private String title;

    private String unionValue;

    private String imgUrl;

    private Integer status;

    private String color;

    /**
     * 附加广告数据
     */
    private Object data;
}
