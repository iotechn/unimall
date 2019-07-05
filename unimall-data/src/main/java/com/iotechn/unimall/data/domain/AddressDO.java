package com.iotechn.unimall.data.domain;

/*
@author kbq
@date  2019/7/4 - 21:29
*/

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("unimall_address")
public class AddressDO extends SuperDO{

    private String province;

    private String city;

    private String county;

    private String street;

    private String address;

    @TableField("user_id")
    private Long userId;

    private String phone;

    private String consignee;


}
