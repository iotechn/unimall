import Vue from 'vue'
import store from './store'
import App from './App'

import * as filters from './filters'

import config from './config'

Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key])
})

// #ifdef H5
const jweixin = require('./components/jweixin-module')
const jwx = require('./components/jweixin-module/jwx')
Vue.mixin({
  onShow() {
    jwx.configWeiXin(jwx => {
    })
  }
})
// #endif

const msg = (title, duration = 1500, mask = false, icon = 'none') => {
  // 统一提示方便全局修改
  if (Boolean(title) === false) {
    return
  }
  uni.showToast({
    title,
    duration,
    mask,
    icon
  })
}

let userInfo

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
  // 异步请求数据
  return new Promise(resolve => {
    if (!userInfo || !userInfo.accessToken) {
      userInfo = uni.getStorageSync('userInfo')
    }
    const accessToken = userInfo ? userInfo.accessToken : ''
    const baseUrl = config.baseUrl
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
            resolve(res.data)
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
  if (!userInfo || !userInfo.accessToken) {
    userInfo = uni.getStorageSync('userInfo')
  }
  const accessToken = userInfo ? userInfo.accessToken : ''
  const baseUrl = config.baseUrl
  uni.chooseImage({
    sizeType: ['compressed'],
    success: function(res) {
      for (let i = 0; i < res.tempFilePaths.length; i++) {
        uni.showLoading({
          title: '图片上传中'
        })
        const fileName = ('imgs/' + random_string(15) + get_suffix(res.tempFilePaths[i]))
        uni.uploadFile({
          url: baseUrl + '/upload/user',
          filePath: res.tempFilePaths[i],
          name: 'file',
          formData: {
            name: res.tempFilePaths[i],
            key: fileName
          },
          header: {
            // #ifdef MP-WEIXIN
            APPID: uni.getAccountInfoSync().miniProgram.appId,
            // #endif
            ACCESSTOKEN: accessToken
          },
          success: function(uploadRes) {
            uni.hideLoading()
            console.log(uploadRes)
            if (uploadRes.statusCode === 200) {
              if (successCallback) {
                successCallback(JSON.parse(uploadRes.data).url)
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
    }
  })
}


function get_suffix(filename) {
  const pos = filename.lastIndexOf('.')
  let suffix = ''
  if (pos !== -1) {
    suffix = filename.substring(pos)
  }
  return suffix
}

function random_string(len) {
  len = len || 32
  const chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678'
  const maxPos = chars.length
  let pwd = ''
  for (let i = 0; i < len; i++) {
    pwd += chars.charAt(Math.floor(Math.random() * maxPos))
  }
  return pwd
}

const prePage = () => {
  const pages = getCurrentPages()
  const prePage = pages[pages.length - 2]
  if (!prePage) {
    return null
  }
  let res = prePage.$vm
  // #ifdef H5
  res = prePage
  // #endif
  return res
}

const globalData = {}

export function style(width) {
  if (config.OSS_PROVIDER === 'aliyun') {
    return '?x-oss-process=style/' + width + 'px'
  } else if (config.OSS_PROVIDER === 'qcloud') {
    return '?imageMogr2/thumbnail/' + width + 'x/interlace/0'
  }
}

Vue.config.productionTip = false
Vue.prototype.$fire = new Vue()
Vue.prototype.$store = store
Vue.prototype.$api = {
  msg,
  prePage,
  request,
  uploadImg,
  logout,
  isVip,
  setUserInfo,
  config,
  globalData,
  style
}
// #ifdef H5
Vue.prototype.$jweixin = jweixin
// #endif

App.mpType = 'app'

const app = new Vue({
  ...App
})
app.$mount()
