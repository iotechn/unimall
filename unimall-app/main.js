import Vue from 'vue'
import store from './store'
import App from './App'

import Json from './Json' //测试用数据

//TODO 放到配置文件
// const baseUrl = 'http://127.0.0.1:8080'
const baseUrl = 'https://fresh.easycampus.cn'
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
						}
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

Vue.config.productionTip = false
Vue.prototype.$fire = new Vue();
Vue.prototype.$store = store;
Vue.prototype.$api = {
	msg,
	json,
	prePage,
	request,
	uploadImg
};

App.mpType = 'app'

const app = new Vue({
	...App
})
app.$mount()
