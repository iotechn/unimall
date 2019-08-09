package com.iotechn.unimall.data.dto;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-20
 * Time: 上午11:46
 */

@Data
public class ConfigDTO extends SuperDTO {
    private String title;
    private String logoUrl;
    private String description;
    private String address;
    private Integer showType;
    private Integer status;
}
