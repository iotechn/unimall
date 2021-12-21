<template>
  <view class="container">
    <!-- 此页面不可返回 -->
    <view class="left-bottom-sign" />
    <view class="right-top-sign" />
    <view class="wrapper">
      <view class="left-top-sign">
        BIND
      </view>
      <view class="welcome">
        绑定手机！
      </view>
      <!-- #ifdef MP-WEIXIN -->
      <!-- 微信小程序，自动获取 -->
      <button class="confirm-btn" :disabled="wxBinding" open-type="getPhoneNumber" @getphonenumber="doWxBind">
        授权绑定手机号
      </button>
      <!-- #endif -->
      <!-- #ifndef MP-WEIXIN -->
      <!-- 验证码绑定 -->
      <view class="input-content">
        <view class="input-item">
          <text class="tit">
            手机号码
          </text>
          <input type="number" :value="phone" placeholder="请输入手机号码" maxlength="11" data-key="phone" @input="inputChange">
        </view>
        <view class="input-item">
          <text class="tit">
            验证码
          </text>
          <view class="verify-code">
            <input
              type="mobile"
              value=""
              placeholder="6位验证码"
              maxlength="6"
              data-key="verifyCode"
              style="width: 60%;"
              @input="inputChange"
            >
            <button style="width: 40%;" :disabled="sendDisabled" @click="doGetVerify">
              {{ sendText }}
            </button>
          </view>
        </view>
      </view>
      <button class="confirm-btn" :disabled="binding" @click="doBind">
        绑定
      </button>
      <!-- #endif -->
    </view>
  </view>
</template>

<script>
import {
  mapMutations
} from 'vuex'

export default {
  data() {
    return {
      wxBinding: false,
      phone: '',
      binding: false,
      sendText: '获取验证码',
      sendDisabled: false
    }
  },
  onLoad() {

  },
  methods: {
    ...mapMutations(['login']),
    // #ifdef MP-WEIXIN
    doWxBind(e) {
      const that = this
      that.wxBinding = true
      uni.showLoading({
        title: '正在绑定'
      })
      uni.login({
        provider: 'weixin',
        success: wxres => {
          that.$api.request('user', 'getWxPhone', {
            raw: JSON.stringify(wxres),
            encryptedData: e.detail.encryptedData,
            iv: e.detail.iv
          }, failres => {
            that.wxBinding = false
            that.$api.msg(failres.errmsg)
          }).then(res => {
            that.wxBinding = false
            // 更新storage中userInfo状态
            uni.getStorage({
              key: 'userInfo',
              success: function(res) {
                const userInfo = res.data
                userInfo.status = 1
                uni.setStorage({
                  key: 'userInfo',
                  data: userInfo,
                  success: function() {
                  }
                })
              }
            })
            uni.navigateBack()
          })
        }, fail() {
          uni.navigateBack()
        }
      })
    },
    // #endif
    // #ifndef MP-WEIXIN
    inputChange(e) {
      const key = e.currentTarget.dataset.key
      this[key] = e.detail.value
    },
    doGetVerify() {
      const that = this
      if (!that.phone || that.phone.length !== 11) {
        uni.showToast({
          title: '请输入正确手机号！',
          icon: 'none'
        })
        return
      }
      that.$api.request('user', 'sendVerifyCode', {
        phone: that.phone
      }).then(res => {
        that.sendDisabled = true
        let sec = 60
        const interval = setInterval(() => {
          sec--
          that.sendText = sec + 's后重发'
          if (sec <= 0) {
            that.sendDisabled = false
            that.sendText = '获取验证码'
            clearInterval(interval)
          }
        }, 1000)
      })
    },
    doBind() {
      const that = this
      that.binding = true
      that.$api.request('user', 'bindPhone', {
        phone: that.phone,
        password: that.password,
        verifyCode: that.verifyCode
      }, failres => {
        uni.showToast({
          title: failres.errmsg
        })
        that.binding = false
      }).then(res => {
        that.binding = false
        that.$api.request('user', 'info', failres => {
          uni.getStorage({
            key: 'userInfo',
            success: function(res) {
              const userInfo = res.data
              userInfo.status = 1
              uni.setStorage({
                key: 'userInfo',
                data: userInfo,
                success: function() {
                }
              })
            }
          })
          // #ifndef MP-ALIPAY
          uni.navigateBack()
          // #endif
          // #ifdef MP-ALIPAY
          uni.switchTab({
            url: '/pages/user/user'
          })
          // #endif
        }).then(userRes => {
          uni.setStorageSync('userInfo', userRes.data)
          that.$store.commit('login', userRes.data)
          that.$api.setUserInfo(userRes.data)
          // #ifndef MP-ALIPAY || H5
          uni.navigateBack()
          // #endif
          // #ifdef MP-ALIPAY || H5
          uni.switchTab({
            url: '/pages/user/user'
          })
          // #endif
        })
      })
    }
  // #endif
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
		padding: 0 0 0 30upx;
		background: $page-color-light;
		height: 140upx;
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

		.verify-code {
			flex-direction: row;
			display: flex;
			justify-content:end;
			flex-wrap: wrap;
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
