package com.iotechn.unimall.app.api.user;

import com.iotechn.unimall.core.Const;
import com.iotechn.unimall.core.annotation.HttpMethod;
import com.iotechn.unimall.core.annotation.HttpOpenApi;
import com.iotechn.unimall.core.annotation.HttpParam;
import com.iotechn.unimall.core.annotation.HttpParamType;
import com.iotechn.unimall.core.annotation.param.NotNull;
import com.iotechn.unimall.core.annotation.param.TextFormat;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.dto.UserDTO;

/**
 * Created by rize on 2019/6/30.
 */
@HttpOpenApi(group = "user", description = "用户服务")
public interface UserService {

    @HttpMethod(description = "发送验证码到用户手机")
    public String sendVerifyCode(
            @NotNull @TextFormat(length = 11) @HttpParam(name = "phone", type = HttpParamType.COMMON, description = "用户手机号") String phone) throws ServiceException;

    @HttpMethod(description = "用户注册")
    public String register(
            @NotNull @TextFormat(length = 11) @HttpParam(name = "phone", type = HttpParamType.COMMON, description = "用户手机号") String phone,
            @NotNull @TextFormat(lengthMin = 8, lengthMax = 18, notChinese = true) @HttpParam(name = "password", type = HttpParamType.COMMON, description = "用户密码") String password,
            @NotNull @HttpParam(name = "verifyCode", type = HttpParamType.COMMON, description = "注册验证码") String verifyCode,
            @HttpParam(name = "ip", type = HttpParamType.IP, description = "用户Ip") String ip) throws ServiceException;

    @HttpMethod(description = "用户绑定手机号")
    public String bindPhone(
            @NotNull @TextFormat(length = 11) @HttpParam(name = "phone", type = HttpParamType.COMMON, description = "用户手机号") String phone,
            @NotNull @TextFormat(lengthMin = 8, lengthMax = 18, notChinese = true) @HttpParam(name = "password", type = HttpParamType.COMMON, description = "设置用户密码") String password,
            @NotNull @HttpParam(name = "verifyCode", type = HttpParamType.COMMON, description = "注册验证码") String verifyCode,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "用户重置密码")
    public String resetPassword(
            @NotNull @TextFormat(length = 11) @HttpParam(name = "phone", type = HttpParamType.COMMON, description = "用户手机号") String phone,
            @NotNull @TextFormat(lengthMin = 8, lengthMax = 18, notChinese = true) @HttpParam(name = "password", type = HttpParamType.COMMON, description = "用户密码") String password,
            @NotNull @HttpParam(name = "verifyCode", type = HttpParamType.COMMON, description = "注册验证码") String verifyCode) throws ServiceException;

    @HttpMethod(description = "用户登录")
    public UserDTO login(
            @NotNull @HttpParam(name = "phone", type = HttpParamType.COMMON, description = "用户手机号") String phone,
            @NotNull @TextFormat(lengthMin = 8, lengthMax = 18, notChinese = true) @HttpParam(name = "password", type = HttpParamType.COMMON, description = "用户密码") String password,
            @HttpParam(name = "loginType", type = HttpParamType.COMMON, description = "登录方式") Integer loginType,
            @HttpParam(name = "raw", type = HttpParamType.COMMON, description = "第三方平台返回的数据") String raw,
            @NotNull @HttpParam(name = "ip", type = HttpParamType.IP, description = "登录IP") String ip) throws ServiceException;

    @HttpMethod(description = "用户注销")
    public String logout(
            @NotNull @HttpParam(name = Const.USER_ACCESS_TOKEN, type = HttpParamType.HEADER, description = "用户访问") String accessToken,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "第三方登录")
    public UserDTO thirdPartLogin(
            @NotNull @HttpParam(name = "loginType", type = HttpParamType.COMMON, description = "第三方代号") Integer loginType,
            @NotNull @HttpParam(name = "ip", type = HttpParamType.IP, description = "用户Ip") String ip,
            @NotNull @HttpParam(name = "raw", type = HttpParamType.COMMON, description = "第三方平台返回的数据") String raw) throws ServiceException;

    @HttpMethod(description = "同步用户信息")
    public String syncUserInfo(
            @HttpParam(name = "nickname", type = HttpParamType.COMMON, description = "用户昵称") String nickname,
            @HttpParam(name = "avatarUrl", type = HttpParamType.COMMON, description = "用户头像url") String avatarUrl,
            @HttpParam(name = "gender", type = HttpParamType.COMMON, description = "性别0未知1男2女") Integer gender,
            @HttpParam(name = "birthday", type = HttpParamType.COMMON, description = "用户生日") Long birthday,
            @HttpParam(name = Const.USER_ACCESS_TOKEN, type = HttpParamType.HEADER, description = "访问令牌") String accessToken,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户ID") Long userId) throws ServiceException;

    /**
     * H5 页面签名
     * 当H5页面需要调用微信的API时，微信为防止DNS劫持等情况，他是需要对每个页面进行签名的
     *
     * @param url
     * @return
     * @throws ServiceException
     */
    @HttpMethod(description = "获取H5签名")
    public Object getH5Sign(
            @NotNull @HttpParam(name = "url", type = HttpParamType.COMMON, description = "url") String url) throws ServiceException;

    @HttpMethod(description = "检测登录")
    public Boolean checkLogin(
            @NotNull @HttpParam(name = Const.USER_ACCESS_TOKEN, type = HttpParamType.HEADER, description = "token") String accessToken) throws ServiceException;

}
