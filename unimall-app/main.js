import Vue from 'vue'
import store from './store'
import App from './App'

import Json from './Json' //测试用数据

//TODO 放到配置文件
const baseUrl = 'http://127.0.0.1:8080'

/**
 *  因工具函数属于公司资产, 所以直接在Vue实例挂载几个常用的函数
 *  所有测试用数据均存放于根目录json.js
 *  
 *  css部分使用了App.vue下的全局样式和iconfont图标，有需要图标库的可以留言。
 *  示例使用了uni.scss下的变量, 除变量外已尽量移除特有语法,可直接替换为其他预处理器使用
 */
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
const json = type => {
	//模拟异步请求数据
	return new Promise(resolve => {
		setTimeout(() => {
			resolve(Json[type]);
		}, 500)
	})
}

let userInfo = undefined

const request = (_gp, _mt, data = {}, failCallback) => {
	//异步请求数据
	return new Promise(resolve => {
		if (!userInfo) {
			userInfo = uni.getStorageSync('userInfo')
		}
		let accessToken = userInfo ? userInfo.accessToken : ''
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
						} else {
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
								fail: () => {

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

const prePage = () => {
	let pages = getCurrentPages();
	let prePage = pages[pages.length - 2];
	// #ifdef H5
	return prePage;
	// #endif
	return prePage.$vm;
}

Vue.config.productionTip = false
Vue.prototype.$fire = new Vue();
Vue.prototype.$store = store;
Vue.prototype.$api = {
	msg,
	json,
	prePage,
	request
};

App.mpType = 'app'

const app = new Vue({
	...App
})
app.$mount()
