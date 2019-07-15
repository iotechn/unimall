package com.iotechn.unimall.data.dto;

import lombok.Data;

/**
 * Created by rize on 2019/7/14.
 */
@Data
public class AdvertisementDTO extends SuperDTO {

    private Integer adType;

    private String title;

    private String url;

    private String imgUrl;

    private Integer status;

    private String color;

    /**
     * 附加广告数据
     */
    private Object data;
}
