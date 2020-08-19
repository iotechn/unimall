import Vue from 'vue'
import store from './store'
import App from './App'

import * as filters from './filters'

Object.keys(filters).forEach(key => {
	Vue.filter(key, filters[key])
})

//#ifdef H5
let jweixin = require('./components/jweixin-module')
let jwx = require('./components/jweixin-module/jwx')
Vue.mixin({
	onShow() {
		jwx.configWeiXin(jwx => {
		})
	}
})
//#endif

import config from './config'

const msg = (title, duration = 1500, mask = false, icon = 'none') => {
	//统一提示方便全局修改
	if (Boolean(title) === false) {
		return;
	}
	uni.showToast({
		title,
		duration,
		mask,
		icon
	});
}

let userInfo = undefined

const logout = () => {
	userInfo = undefined
	uni.removeStorage({
		key: 'userInfo'
	})
}

const setUserInfo = (i) => {
	userInfo = i
}

const isVip = () => {
	return userInfo && userInfo.level
}

let loginLock = false

const request = (_gp, _mt, data = {}, failCallback) => {
	//异步请求数据
	return new Promise(resolve => {
		if (!userInfo || !userInfo.accessToken) {
			userInfo = uni.getStorageSync('userInfo')
		}
		let accessToken = userInfo ? userInfo.accessToken : ''
		let baseUrl = config.baseUrl
		uni.request({
			url: baseUrl + '/m.api',
			data: {
				...data,
				_gp,
				_mt
			},
			method: 'POST',
			header: {
				'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
				'ACCESSTOKEN': accessToken
			},
			success: (res) => {
				if (res.statusCode === 200) {
					if (res.data.errno === 200) {
						resolve(res.data);
					} else if (res.data.errno === 10001) {
						if (failCallback) {
							failCallback(res.data)
						}
						if (!loginLock) {
							loginLock = true
							uni.showModal({
								title: '登录提示',
								content: '您尚未登录，是否立即登录？',
								showCancel: true,
								confirmText: '登录',
								success: (e) => {
									if (e.confirm) {
										uni.navigateTo({
											url: '/pages/public/login'
										})
									}
								},
								fail: () => {},
								complete: () => {
									loginLock = false
								}
							})
						}

					} else {
						if (failCallback) {
							failCallback(res.data)
						} else {
							uni.showToast({
								title: res.data.errmsg,
								icon: 'none'
							})
						}
					}
				}
			}
		})
	})
}

const uploadImg = (successCallback) => {
	let baseUrl = config.baseUrl
	uni.chooseImage({
		sizeType: ['compressed'],
		success: function(res) {
			for (let i = 0; i < res.tempFilePaths.length; i++) {
				uni.request({
					url: baseUrl + '/upload',
					method: 'get',
					success: function(signRes) {
						uni.showLoading({
							title: '图片上传中',
						})
						let fileName = ('imgs/' + random_string(15) + get_suffix(res.tempFilePaths[i]))
						uni.uploadFile({
							url: signRes.data.baseUrl,
							filePath: res.tempFilePaths[i],
							name: 'file',
							formData: {
								name: res.tempFilePaths[i],
								key: fileName,
								policy: signRes.data.policy,
								OSSAccessKeyId: signRes.data.accessid,
								success_action_status: '200',
								signature: signRes.data.signature
							},
							success: function(uploadRes) {
								uni.hideLoading()
								if (uploadRes.statusCode === 200) {
									if (successCallback) {
										successCallback(signRes.data.baseUrl + fileName)
									} else {
										uni.showToast({
											title: '上传成功',
											icon: 'none'
										})
									}
								} else {
									uni.hideLoading()
									uni.showToast({
										title: '网络错误 code=' + uploadRes.statusCode,
										icon: 'none'
									})
								}
							}
						})
					}
				})
			}
		}
	})
}

function get_suffix(filename) {
	var pos = filename.lastIndexOf('.')
	var suffix = ''
	if (pos != -1) {
		suffix = filename.substring(pos)
	}
	return suffix;
}

function random_string(len) {
	len = len || 32;
	var chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';
	var maxPos = chars.length;
	var pwd = '';
	for (var i = 0; i < len; i++) {
		pwd += chars.charAt(Math.floor(Math.random() * maxPos));
	}
	return pwd;
}

const prePage = () => {
	let pages = getCurrentPages();
	let prePage = pages[pages.length - 2];
	// #ifdef H5
	return prePage;
	// #endif
	return prePage.$vm;
}

const globalData = {}

Vue.config.productionTip = false
Vue.prototype.$fire = new Vue();
Vue.prototype.$store = store;
Vue.prototype.$api = {
	msg,
	prePage,
	request,
	uploadImg,
	logout,
	isVip,
	setUserInfo,
	config,
	globalData
};
//#ifdef H5
Vue.prototype.$jweixin = jweixin;
//#endif

App.mpType = 'app'

const app = new Vue({
	...App
})
app.$mount()
