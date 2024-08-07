package com.dobbinsoft.unimall.app.api.address;

/*
@author kbq
@date  2019/7/4 - 21:36
*/

import com.dobbinsoft.fw.core.annotation.HttpOpenApi;
import com.dobbinsoft.fw.core.annotation.HttpMethod;
import com.dobbinsoft.fw.core.annotation.HttpParam;
import com.dobbinsoft.fw.core.annotation.HttpParamType;
import com.dobbinsoft.fw.core.annotation.param.NotNull;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.unimall.data.domain.AddressDO;

import java.util.List;

@HttpOpenApi(group = "address", description = "收货地址")
public interface AddressService {

    @HttpMethod(description = "增加收货地址")
    public String create(
            @NotNull @HttpParam(name = "province", type = HttpParamType.COMMON, description = "省份") String province,
            @NotNull @HttpParam(name = "city", type = HttpParamType.COMMON, description = "城市") String city,
            @NotNull @HttpParam(name = "county", type = HttpParamType.COMMON, description = "区县") String county,
            @NotNull @HttpParam(name = "address", type = HttpParamType.COMMON, description = "详细地址") String address,
            @NotNull @HttpParam(name = "defaultAddress", type = HttpParamType.COMMON, description = "默认地址") Integer defaultAddress,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户ID") Long userId,
            @NotNull @HttpParam(name = "phone", type = HttpParamType.COMMON, description = "电话") String phone,
            @NotNull @HttpParam(name = "consignee", type = HttpParamType.COMMON, description = "收件人") String consignee) throws ServiceException;

    @HttpMethod(description = "删除收货地址")
    public String delete(
            @NotNull @HttpParam(name = "addressId", type = HttpParamType.COMMON, description = "收货地址ID") Long addressId,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户ID") Long userId) throws ServiceException;

    @HttpMethod(description = "修改收货地址")
    public String edit(
            @NotNull @HttpParam(name = "addressId", type = HttpParamType.COMMON, description = "地址Id") Long addressId,
            @NotNull @HttpParam(name = "province", type = HttpParamType.COMMON, description = "省份") String province,
            @NotNull @HttpParam(name = "city", type = HttpParamType.COMMON, description = "城市") String city,
            @NotNull @HttpParam(name = "county", type = HttpParamType.COMMON, description = "区县") String county,
            @NotNull @HttpParam(name = "address", type = HttpParamType.COMMON, description = "详细地址") String address,
            @NotNull @HttpParam(name = "defaultAddress", type = HttpParamType.COMMON, description = "默认地址") Integer defaultAddress,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户ID") Long userId,
            @NotNull @HttpParam(name = "phone", type = HttpParamType.COMMON, description = "电话") String phone,
            @NotNull @HttpParam(name = "consignee", type = HttpParamType.COMMON, description = "收件人") String consignee) throws ServiceException;

    @HttpMethod(description = "查询用户所有收货地址")
    public List<AddressDO> list(
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "根据地址ID，查询收货地址")
    public AddressDO getAddressById(
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId,
            @NotNull @HttpParam(name = "addressId", type = HttpParamType.COMMON, description = "地址Id") Long addressId) throws ServiceException;

    @HttpMethod(description = "获取用户默认地址")
    public AddressDO getDefAddress(
            @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户id") Long userId) throws ServiceException;

}
