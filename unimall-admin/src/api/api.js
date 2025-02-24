import axios from "@/utils/axios";
import moment from "moment";

/**
 * 登录
 */
export function login(params) {
	return axios({
		url: "/Index/login",
		method: "post",
		data: params
	});
}
/**
 * 获取图形验证码
 */
export function getCode(params) {
	return axios({
		url: "/Index/getCaptchaCode",
		method: "post",
		data: params
	});
}

/**
 * 权限列表（侧边栏权限和按钮权限）
 * @param params
 */
export function getPermission(params) {
	return axios({
		url: "/Index/getPermission",
		method: "post",
		data: params
	});
}

/**
 * 账号列表
 * @param params
 */
export function getAdmintorList(params) {
	return axios({
		url: "/adminAuth/adminList",
		method: "post",
		data: params
	});
}

/**
 * 角色列表
 * @param params
 */
export function getRoleList(params) {
	return axios({
		url: "/adminAuth/getRoleList",
		method: "post",
		data: params
	});
}

// 上传图片
export function publicUploadFile(params) {
	return axios({
		url: "/public/uploadFile",
		method: "post",
		data: params
	});
}