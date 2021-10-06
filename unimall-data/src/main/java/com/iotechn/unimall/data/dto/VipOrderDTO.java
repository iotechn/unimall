package com.iotechn.unimall.data.dto;

import com.dobbinsoft.fw.core.entiy.SuperDTO;
import lombok.Data;

import java.util.Date;

@Data
public class VipOrderDTO extends SuperDTO {

    private String orderNo;

    private Integer dayNum;

    private Integer source;

    private Integer price;

    private Integer commission;

    private Long templateId;

    private Integer status;

    private String phone;

    private Date gmtPay;

    private String payChannel;

    private String payId;

    private Long userId;

    private Long idNumId;

    private Long shareId;

    private String title;

    private String description;

    private String idNum;

    private String name;

    private Integer gender;

}
