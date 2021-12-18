package com.iotechn.unimall.app.api.user;


import com.iotechn.unimall.data.dto.UserDTO;
import com.dobbinsoft.fw.core.Const;
import com.dobbinsoft.fw.core.annotation.*;
import com.dobbinsoft.fw.core.annotation.param.NotNull;
import com.dobbinsoft.fw.core.annotation.param.TextFormat;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.iotechn.unimall.data.enums.UserLoginType;

/**
 * Created by rize on 2019/6/30.
 */
@HttpOpenApi(group = "user", description = "用户服务")
public interface UserService {

    @HttpMethod(description = "发送验证码到用户手机", rate = 10, rateLimit = RateLimitType.IP)
    public String sendVerifyCode(
            @NotNull(message = "手机号不能为空") @TextFormat(length = 11, message = "手机号长度为11位") @HttpParam(name = "phone", type = HttpParamType.COMMON, description = "用户手机号") String phone) throws ServiceException;

    @HttpMethod(description = "用户注册")
    public String register(
            @NotNull(message = "手机号不能为空") @TextFormat(length = 11, message = "手机号长度为11位") @HttpParam(name = "phone", type = HttpParamType.COMMON, description = "用户手机号") String phone,
            @NotNull(message = "密码不能为空") @TextFormat(lengthMin = 8, lengthMax = 18, notChinese = true, message = "密码长度为8到18位") @HttpParam(name = "password", type = HttpParamType.COMMON, description = "用户密码") String password,
            @NotNull(message = "请输入验证码") @HttpParam(name = "verifyCode", type = HttpParamType.COMMON, description = "注册验证码") String verifyCode,
            @HttpParam(name = "ip", type = HttpParamType.IP, description = "用户Ip") String ip) throws ServiceException;

    @HttpMethod(description = "用户绑定手机号")
    public String bindPhone(
            @NotNull @TextFormat(length = 11) @HttpParam(name = "phone", type = HttpParamType.COMMON, description = "用户手机号") String phone,
            @NotNull(message = "请输入注册验证码") @HttpParam(name = "verifyCode", type = HttpParamType.COMMON, description = "注册验证码") String verifyCode,
            @NotNull @HttpParam(name = Const.USER_ACCESS_TOKEN, type = HttpParamType.HEADER, description = "用户访问") String accessToken,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "用户重置密码")
    public String resetPassword(
            @NotNull @TextFormat(length = 11) @HttpParam(name = "phone", type = HttpParamType.COMMON, description = "用户手机号") String phone,
            @NotNull @TextFormat(lengthMin = 8, lengthMax = 18, notChinese = true) @HttpParam(name = "password", type = HttpParamType.COMMON, description = "用户密码") String password,
            @NotNull @HttpParam(name = "verifyCode", type = HttpParamType.COMMON, description = "注册验证码") String verifyCode) throws ServiceException;

    @HttpMethod(description = "密码登录")
    public UserDTO login(
            @NotNull(message = "请输入手机号") @HttpParam(name = "phone", type = HttpParamType.COMMON, description = "用户手机号") String phone,
            @NotNull(message = "请输入密码") @TextFormat(lengthMin = 8, lengthMax = 18, notChinese = true, message = "密码长度8到18位") @HttpParam(name = "password", type = HttpParamType.COMMON, description = "用户密码") String password,
            @NotNull @HttpParam(name = "platform", type = HttpParamType.COMMON, description = "登录平台") Integer platform,
            @NotNull @HttpParam(name = "ip", type = HttpParamType.IP, description = "登录IP") String ip) throws ServiceException;

    @HttpMethod(description = "用户注销")
    public String logout(
            @NotNull @HttpParam(name = Const.USER_ACCESS_TOKEN, type = HttpParamType.HEADER, description = "用户访问") String accessToken,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    /**
     * 首次第三方登录时，系统会记录OPEN_ID。但此时，用户无法产生用户数据
     * 当用户完善手机号，或通过微信自动获取、或通过自助绑定手机号获取。
     * 绑定完成后，用户可正常使用此系统。
     *
     * 若用户手机已经存在（例如，先从支付宝登录、后用微信登录，使用同一个手机号、系统会将OPEN_ID合并到之前的账号上）
     *
     * @param loginType
     * @param platform 登录平台 与 PayPlatform一致
     * @param ip
     * @param raw
     * @return
     * @throws ServiceException
     */
    @HttpMethod(description = "第三方登录")
    public UserDTO thirdPartLogin(
            @NotNull @HttpParam(name = "loginType", type = HttpParamType.COMMON, description = "第三方代号", enums = UserLoginType.class) Integer loginType,
            @NotNull @HttpParam(name = "platform", type = HttpParamType.COMMON, description = "登录平台") Integer platform,
            @NotNull @HttpParam(name = "ip", type = HttpParamType.IP, description = "用户Ip") String ip,
            @NotNull @HttpParam(name = "raw", type = HttpParamType.COMMON, description = "第三方平台返回的数据") String raw) throws ServiceException;

    @HttpMethod(description = "获取手机号")
    public String getWxPhone(
            @NotNull @HttpParam(name = "raw", type = HttpParamType.COMMON, description = "小程序登录，wx.login()返回的数据串") String raw,
            @NotNull @HttpParam(name = "encryptedData", type = HttpParamType.COMMON, description = "包括敏感数据在内的完整用户信息的加密数据") String encryptedData,
            @NotNull @HttpParam(name = "iv", type = HttpParamType.COMMON, description = "加密算法的初始向量") String iv,
            @NotNull @HttpParam(name = Const.USER_ACCESS_TOKEN, type = HttpParamType.HEADER, description = "用户访问") String accessToken,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "同步用户信息")
    public String syncUserInfo(
            @HttpParam(name = "nickname", type = HttpParamType.COMMON, description = "用户昵称") String nickname,
            @HttpParam(name = "avatarUrl", type = HttpParamType.COMMON, description = "用户头像url") String avatarUrl,
            @HttpParam(name = "province", type = HttpParamType.COMMON, description = "省份") String province,
            @HttpParam(name = "city", type = HttpParamType.COMMON, description = "市") String city,
            @HttpParam(name = "county", type = HttpParamType.COMMON, description = "区") String county,
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

    @HttpMethod(description = "获取用户信息")
    public UserDTO info(
            @NotNull @HttpParam(name = Const.USER_ACCESS_TOKEN, type = HttpParamType.HEADER, description = "token") String accessToken,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户ID") Long userId) throws ServiceException;

}
