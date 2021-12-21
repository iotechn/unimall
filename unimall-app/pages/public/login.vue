<template>
  <view class="container">
    <view class="left-bottom-sign" />
    <!-- #ifndef MP-ALIPAY -->
    <view class="back-btn yticon icon-zuojiantou-up" @click="navBack" />
    <!-- #endif -->
    <view class="right-top-sign" />
    <!-- 设置白色背景防止软键盘把下部绝对定位元素顶上来盖住输入框等 -->
    <view class="wrapper">
      <view class="left-top-sign">
        LOGIN
      </view>
      <view class="welcome">
        欢迎回来！
      </view>
      <view v-if="loginType === 'phone'" class="input-content">
        <view class="input-item">
          <text class="tit">
            手机号码
          </text>
          <input type="number" :value="phone" placeholder="请输入手机号码" maxlength="11" data-key="phone" @input="inputChange">
        </view>
        <view class="input-item">
          <text class="tit">
            密码
          </text>
          <input
            type="mobile"
            value=""
            placeholder="8-18位不含特殊字符的数字、字母组合"
            placeholder-class="input-empty"
            maxlength="20"
            password
            data-key="password"
            @input="inputChange"
            @confirm="toLogin"
          >
        </view>
      </view>
      <!-- #ifdef MP-WEIXIN || APP-PLUS || H5 -->
      <button v-if="!loginType" class="confirm-btn" :disabled="logining" @click="chooseLoginType('wechat')">
        微信快速登录（推荐）
      </button>
      <!-- #endif -->
      <!-- #ifdef MP-ALIPAY -->
      <button v-if="!loginType" class="confirm-btn" :disabled="logining" @click="chooseLoginType('alipay')">
        支付宝快速登录（推荐）
      </button>
      <!-- #endif -->
      <!-- #ifdef APP-PLUS -->
      <button v-if="!loginType" class="confirm-btn" :disabled="logining" @click="chooseLoginType('phone')">
        手机注册登录
      </button>
      <!-- #endif -->
      <!-- #ifdef MP-WEIXIN -->
      <button
        v-if="loginType === 'wechat'"
        class="confirm-btn"
        :disabled="logining"
        @click="miniWechatLogin"
      >
        微信授权登录
      </button>
      <!-- #endif -->
      <!-- #ifdef APP-PLUS -->
      <button v-if="loginType === 'wechat'" class="confirm-btn" :disabled="logining" @click="wechatLogin">
        微信授权登录
      </button>
      <!-- #endif -->
      <!-- #ifdef H5 -->
      <button v-if="loginType === 'wechat'" class="confirm-btn" :disabled="logining" @click="wechatH5Login">
        微信授权登录
      </button>
      <!-- #endif -->
      <!-- #ifdef MP-ALIPAY -->
      <button v-if="loginType === 'alipay'" class="confirm-btn" open-type="getAuthorize" :disabled="logining" scope="userInfo" @click="alipayLogin()">
        支付宝授权登录
      </button>
      <!-- #endif -->
      <!-- #ifdef APP-PLUS -->
      <button v-if="loginType === 'alipay'" class="confirm-btn" :disabled="logining" @click="alipayLogin">
        支付宝授权登录
      </button>
      <!-- #endif -->
      <button v-if="loginType === 'phone'" class="confirm-btn" :disabled="logining" @click="toLogin">
        登录
      </button>
      <view v-if="loginType === 'phone'" class="forget-section" @click="toReset">
        忘记密码?
      </view>
    </view>
    <view v-if="loginType === 'phone'" class="register-section">
      还没有账号?
      <text @click="toRegist">
        马上注册
      </text>
    </view>
  </view>
</template>

<script>
import {
  mapMutations
} from 'vuex'
const PLATFORM_APP = 1
const PLATFORM_WEB = 2
const PLATFORM_MP = 3
const PLATFORM_MICRO = 4
const PLATFORM_WAP = 5
export default {
  data() {
    return {
      loginType: '',
      phone: '',
      password: '',
      logining: false
    }
  },
  onShow() {
    this.$api.logout()
  },
  onLoad(options) {
    this.wechatH5LoginCallback(options)
  },
  methods: {
    ...mapMutations(['login']),
    inputChange(e) {
      const key = e.currentTarget.dataset.key
      this[key] = e.detail.value
    },
    chooseLoginType(type) {
      this.loginType = type
    },
    navBack() {
      uni.navigateBack()
    },
    toRegist() {
      uni.redirectTo({
        url: '/pages/public/register'
      })
    },
    toReset() {
      uni.redirectTo({
        url: '/pages/public/resetpwd'
      })
    },
    async toLogin() {
      const that = this
      if (that.phone.length !== 11) {
        that.$api.msg('请输入11位中国手机号')
      } else if (that.password.length < 8) {
        that.$api.msg('密码至少8位')
      } else {
        that.logining = true
        // #ifdef MP-WEIXIN
        // 若是小程序平台，则获取到openId。整个过程是静默完成的
        uni.login({
          provider: 'weixin',
          success: wxres => {
            that.$api.request('user', 'login', {
              phone: that.phone,
              password: that.password,
              loginType: 1,
              platform: PLATFORM_MP,
              raw: JSON.stringify(wxres)
            }, failres => {
              that.logining = false
              uni.showToast({
                title: failres.errmsg,
                icon: 'none'
              })
            }).then(res => {
              that.logining = false
              that.$store.commit('login', res.data)
              uni.setStorageSync('userInfo', res.data)
              if (that.$api.prePage().loadData) {
                that.$api.prePage().loadData()
              }
              uni.navigateBack()
            })
          }
        })
        // #endif
        // #ifdef APP-PLUS || H5
        // 若是App登录，则不需要保存OpenId。可直接登录
        that.$api.request('user', 'login', {
          phone: that.phone,
          password: that.password,
          platform: PLATFORM_APP
        }, failres => {
          that.logining = false
          uni.showToast({
            title: failres.errmsg,
            icon: 'none'
          })
        }).then(res => {
          that.logining = false
          const loginData = res.data
          that.syncUserInfo(loginData, loginData)
          if (loginData.status === 2) {
            // 未完善手机号，小程序，要求同步信息
            uni.redirectTo({
              url: '/pages/public/bind'
            })
          } else {
            uni.navigateBack()
          }
        })
        // #endif
      }
    },
    // #ifdef MP-WEIXIN
    miniWechatLogin() {
      const that = this
      that.logining = true
      const loginType = 1
      uni.getUserProfile({
        desc: '登录',
        success: (loginRes) => {
          const userInfo = loginRes.userInfo
          userInfo['nickname'] = userInfo.nickName
          uni.showLoading({
            title: '登录中'
          })
          uni.login({
            provider: 'weixin',
            success: wxres => {
              that.logining = false
              const raw = JSON.stringify(wxres)
              that.$api.request('user', 'thirdPartLogin', {
                loginType: loginType,
                raw: raw,
                platform: PLATFORM_MP
              }, failres => {
                that.$api.msg(failres.errmsg)
                uni.hideLoading()
              }).then(res => {
                uni.hideLoading()
                const loginData = res.data
                that.syncUserInfo(loginData, userInfo)
                if (loginData.status === 2) {
                  // 未完善手机号，小程序，要求同步信息
                  uni.redirectTo({
                    url: '/pages/public/bind'
                  })
                } else {
                  uni.navigateBack()
                }
              })
            }
          })
        },
        fail() {
          that.logining = false
        }
      })
    },
    // #endif
    // #ifdef MP-ALIPAY
    alipayMpLogin(e) {
      debugger
      console.log(e)
    },
    // #endif
    alipayLogin() {
      const that = this
      that.logining = true
      const loginType = 4
      uni.showLoading({
        title: '正在同步消息'
      })
      uni.login({
        provider: 'alipay',
        success: alires => {
          that.$api.request('user', 'thirdPartLogin', {
            loginType: loginType,
            raw: JSON.stringify(alires),
            platform: PLATFORM_MP
          }, failres => {
            that.$api.msg(failres.errmsg)
            that.logining = false
            uni.hideLoading()
          }).then(res => {
            that.logining = false
            uni.getUserInfo({
              lang: 'zh_CN',
              success: (e) => {
                const loginData = res.data
                uni.setStorageSync('userInfo', res.data)
                that.$store.commit('login', res.data)
                e.userInfo.nickname = e.userInfo.nickName
                that.syncUserInfo(loginData, e.userInfo)
                if (loginData.status === 2) {
                  // 未完善手机号，小程序，要求同步信息
                  uni.redirectTo({
                    url: '/pages/public/bind'
                  })
                } else {
                  uni.navigateBack()
                }
              },
              fail(e) {
                console.log(e)
              },
              complete: (e) => {
                if (that.$api.prePage().loadData) {
                  that.$api.prePage().loadData()
                }
                uni.hideLoading()
                uni.navigateBack()
              }
            })
          })
        }
      })
    },
    wechatLogin() {
      const that = this
      that.logining = true
      const loginType = 2
      uni.showLoading({
        title: '登录中'
      })
      uni.login({
        provider: 'weixin',
        success: wxres => {
          that.$api.request('user', 'thirdPartLogin', {
            loginType: loginType,
            raw: JSON.stringify(wxres),
            platform: PLATFORM_APP
          }, failres => {
            that.$api.msg(failres.errmsg)
            uni.hideLoading()
          }).then(res => {
            that.logining = false
            uni.hideLoading()
            uni.showLoading({
              title: '正在同步消息'
            })
            uni.getUserInfo({
              lang: 'zh_CN',
              success: (e) => {
                const loginData = res.data
                uni.setStorageSync('userInfo', res.data)
                that.$store.commit('login', res.data)
                e.userInfo.nickname = e.userInfo.nickName
                that.syncUserInfo(loginData, e.userInfo)
                console.log(loginData)
                if (loginData.status === 2) {
                  // 未完善手机号，小程序，要求同步信息
                  uni.redirectTo({
                    url: '/pages/public/bind'
                  })
                } else {
                  uni.navigateBack()
                }
              },
              fail: (e) => {
                uni.hideLoading()
                that.$api.msg(e.errMsg)
                uni.navigateBack()
              },
              complete: (e) => {
                if (that.$api.prePage().loadData) {
                  that.$api.prePage().loadData()
                }
                uni.hideLoading()
              }
            })
          })
        },
        fail: (e) => {
          uni.hideLoading()
          that.$api.msg(e.errMsg)
          console.log(e)
        }
      })
    },
    wechatH5Login() {
      const that = this
      const href = window.location.href
      const page = that.$api.prePage()
      let prePath = '/pages/index/index'
      if (page) {
        const options = page.__page__.options
        let str = ''
        for (const key in options) {
          str += key
          str += '='
          str += options[key]
          str += '&'
        }
        prePath = page.__page__.path + '?' + str
      }
      window.location = 'https://open.weixin.qq.com/connect/oauth2/authorize?' +
				'appid=' + that.$api.config.h5Appid + '&redirect_uri=' + escape(href) + '&response_type=code&scope=snsapi_userinfo&state=' + escape(prePath) + '#wechat_redirect'
    },
    wechatH5LoginCallback(options) {
      // #ifdef H5
      // H5进入，有可能是回调进来的
      if (options.code && options.state) {
        const that = this
        that.logining = true
        that.$api.request('user', 'thirdPartLogin', {
          loginType: 3,
          raw: options.code,
          platform: PLATFORM_WAP
        }, failres => {
          that.logining = false
          that.$api.msg(failres.errmsg)
        }).then(res => {
          // 登录成功，重定向到指定目标
          that.logining = false
          const loginData = res.data
          that.$store.commit('login', loginData)
          uni.setStorageSync('userInfo', loginData)
          if (loginData.status === 2) {
            // 未完善手机号，小程序，要求同步信息
            uni.redirectTo({
              url: '/pages/public/bind'
            })
          } else {
            // 不能重定向到tabbar页面
            if (options.state === '/pages/cart/cart' || options.state === '/pages/user/user' ||
              options.state === '/pages/index/index' || options.state === '/pages/category/category') {
              uni.switchTab({
                url: options.state
              })
            } else {
              uni.redirectTo({
                url: options.state
              })
            }
          }
        })
      }
      // #endif
    },
    syncUserInfo(loginData, userInfo) {
      const that = this
      that.$api.setUserInfo(loginData)
      if (!loginData.nickname) {
        uni.showLoading({
          title: '同步用户信息'
        })
        that.$api.request('user', 'syncUserInfo', userInfo, failres => {
          uni.hideLoading()
          that.$api.msg(failres.errmsg)
        }).then(syncRes => {
          uni.hideLoading()
          // 同步过后
          loginData.nickname = userInfo.nickname
          loginData.avatarUrl = userInfo.avatarUrl
          loginData.gender = userInfo.gender
          uni.setStorageSync('userInfo', loginData)
          that.$store.commit('login', loginData)
          that.$api.setUserInfo(loginData)
          if (that.$api.prePage().loadData) {
            that.$api.prePage().loadData()
          }
        })
      } else {
        that.$store.commit('login', loginData)
        that.$api.setUserInfo(loginData)
        if (that.$api.prePage().loadData) {
          that.$api.prePage().loadData()
        }
      }
    }
  }

}
</script>

<style lang='scss'>
	page {
		background: #fff;
	}

	.container {
		padding-top: 115px;
		position: relative;
		width: 100vw;
		height: 100vh;
		overflow: hidden;
		background: #fff;
	}

	.wrapper {
		position: relative;
		z-index: 90;
		background: #fff;
		padding-bottom: 40upx;
	}

	.back-btn {
		position: absolute;
		left: 40upx;
		z-index: 9999;
		padding-top: var(--status-bar-height);
		top: 40upx;
		font-size: 40upx;
		color: $font-color-dark;
	}

	.left-top-sign {
		font-size: 120upx;
		color: $page-color-base;
		position: relative;
		left: -16upx;
	}

	.right-top-sign {
		position: absolute;
		top: 80upx;
		right: -30upx;
		z-index: 95;

		&:before,
		&:after {
			display: block;
			content: "";
			width: 400upx;
			height: 80upx;
			background: #b4f3e2;
		}

		&:before {
			transform: rotate(50deg);
			border-radius: 0 50px 0 0;
		}

		&:after {
			position: absolute;
			right: -198upx;
			top: 0;
			transform: rotate(-50deg);
			border-radius: 50px 0 0 0;
			/* background: pink; */
		}
	}

	.left-bottom-sign {
		position: absolute;
		left: -270upx;
		bottom: -320upx;
		border: 100upx solid #d0d1fd;
		border-radius: 50%;
		padding: 180upx;
	}

	.welcome {
		position: relative;
		left: 50upx;
		top: -90upx;
		font-size: 46upx;
		color: #555;
		text-shadow: 1px 0px 1px rgba(0, 0, 0, .3);
	}

	.input-content {
		padding: 0 60upx;
	}

	.input-item {
		display: flex;
		flex-direction: column;
		align-items: flex-start;
		justify-content: center;
		padding: 0 30upx;
		background: $page-color-light;
		height: 120upx;
		border-radius: 4px;
		margin-bottom: 50upx;

		&:last-child {
			margin-bottom: 0;
		}

		.tit {
			height: 50upx;
			line-height: 56upx;
			font-size: $font-sm+2upx;
			color: $font-color-base;
		}

		input {
			height: 60upx;
			font-size: $font-base + 2upx;
			color: $font-color-dark;
			width: 100%;
		}
	}

	.confirm-btn {
		width: 630upx;
		height: 76upx;
		line-height: 76upx;
		border-radius: 50px;
		margin: 70upx auto 0;
		background: $uni-color-primary;
		color: #fff;
		font-size: $font-lg;

		&:after {
			border-radius: 100px;
		}
	}

	.forget-section {
		font-size: $font-sm+2upx;
		color: $font-color-spec;
		text-align: center;
		margin-top: 40upx;
	}

	.register-section {
		position: absolute;
		left: 0;
		bottom: 50upx;
		width: 100%;
		font-size: $font-sm+2upx;
		color: $font-color-base;
		text-align: center;

		text {
			color: $font-color-spec;
			margin-left: 10upx;
		}
	}
</style>
