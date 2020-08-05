package com.iotechn.unimall.data.domain;

/*
@author kbq
@date  2019/7/4 - 21:29
*/

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("unimall_address")
public class AddressDO extends SuperDO{

    /**
     * 省份
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 行政区/镇
     */
    private String county;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 是否是默认地址
     */
    @TableField("default_address")
    private Integer defaultAddress;

    @TableField("user_id")
    private Long userId;

    private String phone;
    /**
     * 收件人
     */
    private String consignee;



}
