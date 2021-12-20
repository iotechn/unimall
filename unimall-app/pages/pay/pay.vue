<template>
  <view class="app">
    <view class="price-box">
      <text>支付金额</text>
      <text class="price">
        {{ price / 100.0 }}
      </text>
    </view>

    <view class="pay-type-list">
      <!-- #ifdef MP-WEIXIN | APP-PLUS || H5 -->
      <view class="type-item b-b" @click="changePayType('WX')">
        <text class="icon yticon icon-weixinzhifu" />
        <view class="con">
          <text class="tit">
            微信支付
          </text>
          <text>推荐使用微信支付</text>
        </view>
        <label class="radio">
          <radio value="WX" color="#fa436a" :checked="payType == 'WX'" />
        </label>
      </view>
      <!-- #endif -->
      <!-- #ifdef MP-ALIPAY | APP-PLUS -->
      <view class="type-item b-b" @click="changePayType('ALI')">
        <text class="icon yticon icon-alipay" />
        <view class="con">
          <text class="tit">
            支付宝
          </text>
        </view>
        <label class="radio">
          <radio value="ALI" color="#fa436a" :checked="payType == 'ALI'" />
        </label>
      </view>
      <!-- #endif -->
      <view class="type-item" @click="changePayType('OFFLINE')">
        <text class="icon yticon icon-erjiye-yucunkuan" />
        <view class="con">
          <text class="tit">
            线下支付（到付）
          </text>
        </view>
        <label class="radio">
          <radio value="OFFLINE" color="#fa436a" :checked="payType == 'OFFLINE'" />
        </label>
      </view>
    </view>

    <text :disabled="submiting" class="mix-btn" @click="confirm">
      确认支付
    </text>
  </view>
</template>

<script>
const PLATFORM_APP = 1
const PLATFORM_WEB = 2
const PLATFORM_MP = 3
const PLATFORM_MICRO = 4
const PLATFORM_WAP = 5
export default {
  data() {
    return {
      payType: '',
      price: 0,
      orderNo: '',
      parentOrderNo: '',
      submiting: false,
	  type: 'order',
	  templateId: ''
    }
  },
  onLoad(options) {
    // #ifdef MP-WEIXIN
    this.payType = 'WX'
    // #endif
    // #ifdef MP-ALIPAY
    this.payType = 'ALI'
    // #endif
    // #ifdef APP-PLUS
    this.payType = 'WX'
    // #endif
    // #ifdef H5
    this.payType = 'WX'
    // #endif
	if (options.type) {
	  this.type = options.type
	}
	if (this.type === 'order') {
	  this.price = options.price
	  if (options.orderno) {
	    this.orderNo = options.orderno
	  } else {
	    this.parentOrderNo = options.parentorderno
	  }
	} else if (this.type === 'vip') {
	  this.templateId = options.templateid
	  this.price = options.price
	}
    
  },

  methods: {
    // 选择支付方式
    changePayType(type) {
      this.payType = type
    },
    // 确认支付
    confirm: async function() {
      const that = this
      let platform = ''
      // #ifdef MP-WEIXIN
      platform = PLATFORM_MP
      // #endif
      // #ifdef MP-ALIPAY
      platform = PLATFORM_MP
      // #endif
      // #ifdef APP-PLUS
      platform = PLATFORM_APP
      // #endif
      // #ifdef H5
      platform = PLATFORM_WAP
      // #endif
	  // 判断是vip支付，还是order支付
	  if (this.type === 'order') {
		if (that.payType === 'WX') {
		  that.submiting = true
		  that.$api.request('order', 'prepay', {
		    orderNo: that.orderNo,
		    parentOrderNo: that.parentOrderNo,
		    platform: platform,
		    payChannel: 'WX'
		  }, failres => {
		    that.submiting = false
		    that.$api.msg(failres.errmsg)
		  }).then(prepayRes => {
		    that.submiting = false
		    let payParam
		    // #ifdef MP-WEIXIN
		    payParam = {
		      appId: prepayRes.data.appId,
		      nonceStr: prepayRes.data.nonceStr,
		      package: prepayRes.data.packageValue,
		      timeStamp: prepayRes.data.timeStamp,
		      signType: prepayRes.data.signType,
		      paySign: prepayRes.data.paySign
		    }
		    // #endif
		    // #ifdef APP-PLUS
		    payParam = {
		      appid: prepayRes.data.appId,
		      noncestr: prepayRes.data.nonceStr,
		      package: prepayRes.data.packageValue,
		      partnerid: prepayRes.data.partnerId,
		      prepayid: prepayRes.data.prepayId,
		      timestamp: parseInt(prepayRes.data.timeStamp),
		      sign: prepayRes.data.sign,
		      signType: 'MD5'
		    }
		    // #endif
		    // #ifdef MP-WEIXIN || APP-PLUS
		    uni.requestPayment({
		      provider: 'wxpay',
		      // #ifdef MP-WEIXIN
		      ...payParam,
		      // #endif
		      // #ifdef APP-PLUS
		      orderInfo: payParam,
		      // #endif
		      success: function(res) {
		        uni.redirectTo({
		          url: '/pages/pay/success'
		        })
		      },
		      fail: function(res) {
		        console.log('支付过程失败:' + JSON.stringify(res))
		        that.$api.msg('支付取消')
		      },
		      complete: function(res) {
		        console.log('支付过程结束')
		      }
		    })
		    // #endif
		    // #ifdef H5
		    that.$jweixin.chooseWXPay({
		      nonceStr: prepayRes.data.nonceStr,
		      timestamp: prepayRes.data.timeStamp,
		      package: prepayRes.data.packageValue,
		      signType: prepayRes.data.signType,
		      paySign: prepayRes.data.paySign,
		      success: (e) => {
		        // 支付成功
		        uni.redirectTo({
		          url: '/pages/pay/success'
		        })
		      },
		      fail: function(res) {
		        console.log('支付过程失败:' + JSON.stringify(res))
		        that.$api.msg('支付取消')
		      },
		      complete: function(res) {
		        console.log('支付过程结束')
		      }
		    })
		    // #endif
		  })
		} else if (that.payType === 'ALI') {
		  that.submiting = true
		  that.$api.request('order', 'prepay', {
		    orderNo: that.orderNo,
		    parentOrderNo: that.parentOrderNo,
		    platform: platform,
		    payChannel: 'ALI'
		  }, failres => {
		    that.submiting = false
		    that.$api.msg(failres.errmsg)
		  }).then(prepayRes => {
		    that.submiting = false
		    // #ifdef MP-ALIPAY
		    if (prepayRes.data.code === '10000') {
		      const payParam = JSON.parse(prepayRes.data.httpBody).alipay_trade_create_response.trade_no
		      uni.requestPayment({
		        provider: 'alipay',
		        orderInfo: payParam,
		        success: function(res) {
		          uni.redirectTo({
		            url: '/pages/pay/success'
		          })
		        },
		        fail: function(res) {
		          that.$api.msg('支付取消')
		        },
		        complete: function(res) {
		          console.log('支付过程结束')
		        }
		      })
		    } else {
		      that.$api.msg(prepayRes.data.subMsg)
		    }
		    // #endif
		    // #ifdef APP-PLUS
		    uni.requestPayment({
		      provider: 'alipay',
		      orderInfo: prepayRes.data,
		      success: function(res) {
		        uni.redirectTo({
		          url: '/pages/pay/success'
		        })
		      },
		      fail: function(res) {
		        that.$api.msg('支付取消')
				console.log(res)
		      },
		      complete: function(res) {
		        console.log('支付过程结束')
		      }
		    })
		    // #endif
		  })
		} else if (that.payType === 'OFFLINE') {
		  that.submiting = true
		  that.$api.request('order', 'offlinePrepay', {
		    parentOrderNo: that.parentOrderNo,
		    orderNo: that.orderNo,
		    platform: platform
		  }, failres => {
		    that.submiting = false
		    that.$api.msg(failres.errmsg)
		  }).then(res => {
		    uni.redirectTo({
		      url: '/pages/pay/success'
		    })
		  })
		}
	  } else if (this.type === 'vip') {
		if (that.payType === 'WX') {
		  that.submiting = true
		  that.$api.request('vip.order', 'prepay', {
		    templateId: that.templateId,
		    payPlatform: platform,
		    payChannel: 'WX'
		  }, failres => {
		    that.submiting = false
		    that.$api.msg(failres.errmsg)
		  }).then(prepayRes => {
		    that.submiting = false
		    let payParam
		    // #ifdef MP-WEIXIN
		    payParam = {
		      appId: prepayRes.data.appId,
		      nonceStr: prepayRes.data.nonceStr,
		      package: prepayRes.data.packageValue,
		      timeStamp: prepayRes.data.timeStamp,
		      signType: prepayRes.data.signType,
		      paySign: prepayRes.data.paySign
		    }
		    // #endif
		    // #ifdef APP-PLUS
		    payParam = {
		      appid: prepayRes.data.appId,
		      noncestr: prepayRes.data.nonceStr,
		      package: prepayRes.data.packageValue,
		      partnerid: prepayRes.data.partnerId,
		      prepayid: prepayRes.data.prepayId,
		      timestamp: parseInt(prepayRes.data.timeStamp),
		      sign: prepayRes.data.sign,
		      signType: 'MD5'
		    }
		    // #endif
		    // #ifdef MP-WEIXIN || APP-PLUS
		    uni.requestPayment({
		      provider: 'wxpay',
		      // #ifdef MP-WEIXIN
		      ...payParam,
		      // #endif
		      // #ifdef APP-PLUS
		      orderInfo: payParam,
		      // #endif
		      success: function(res) {
		        uni.redirectTo({
		          url: '/pages/pay/success?scene=vip'
		        })
		      },
		      fail: function(res) {
		        console.log('支付过程失败:' + JSON.stringify(res))
		        that.$api.msg('支付取消')
		      },
		      complete: function(res) {
		        console.log('支付过程结束')
		      }
		    })
		    // #endif
		    // #ifdef H5
		    that.$jweixin.chooseWXPay({
		      nonceStr: prepayRes.data.nonceStr,
		      timestamp: prepayRes.data.timeStamp,
		      package: prepayRes.data.packageValue,
		      signType: prepayRes.data.signType,
		      paySign: prepayRes.data.paySign,
		      success: (e) => {
		        // 支付成功
				that.$api.msg('支付成功')
		        uni.redirectTo({
		          url: '/pages/pay/success?scene=vip'
		        })
		      },
		      fail: function(res) {
		        console.log('支付过程失败:' + JSON.stringify(res))
		        that.$api.msg('支付取消')
		      },
		      complete: function(res) {
		        console.log('支付过程结束')
		      }
		    })
		    // #endif
		  })
		} else if (that.payType === 'ALI') {
		  that.submiting = true
		  that.$api.request('order', 'prepay', {
		    orderNo: that.orderNo,
		    parentOrderNo: that.parentOrderNo,
		    platform: platform,
		    payChannel: 'ALI'
		  }, failres => {
		    that.submiting = false
		    that.$api.msg(failres.errmsg)
		  }).then(prepayRes => {
		    that.submiting = false
		    // #ifdef MP-ALIPAY
		    if (prepayRes.data.code === '10000') {
		      const payParam = JSON.parse(prepayRes.data.httpBody).alipay_trade_create_response.trade_no
		      uni.requestPayment({
		        provider: 'alipay',
		        orderInfo: payParam,
		        success: function(res) {
		          that.$api.msg('支付成功')
		          uni.redirectTo({
		            url: '/pages/pay/success?scene=vip'
		          })
		        },
		        fail: function(res) {
		          that.$api.msg('支付取消')
		        },
		        complete: function(res) {
		          console.log('支付过程结束')
		        }
		      })
		    } else {
		      that.$api.msg(prepayRes.data.subMsg)
		    }
		    // #endif
		    // #ifdef APP-PLUS
		    uni.requestPayment({
		      provider: 'alipay',
		      orderInfo: prepayRes.data,
		      success: function(res) {
		        that.$api.msg('支付成功')
		        uni.navigateBack()
		      },
		      fail: function(res) {
		        that.$api.msg('支付取消')
		      },
		      complete: function(res) {
		        console.log('支付过程结束')
		      }
		    })
		    // #endif
		  })
		}
	  }
      
    }
  }
}
</script>

<style lang='scss'>
	.app {
		width: 100%;
	}

	.price-box {
		background-color: #fff;
		height: 265upx;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		font-size: 28upx;
		color: #909399;

		.price {
			font-size: 50upx;
			color: #303133;
			margin-top: 12upx;

			&:before {
				content: '￥';
				font-size: 40upx;
			}
		}
	}

	.pay-type-list {
		margin-top: 20upx;
		background-color: #fff;
		padding-left: 60upx;

		.type-item {
			height: 120upx;
			padding: 20upx 0;
			display: flex;
			justify-content: space-between;
			align-items: center;
			padding-right: 60upx;
			font-size: 30upx;
			position: relative;
		}

		.icon {
			width: 100upx;
			font-size: 52upx;
		}

		.icon-erjiye-yucunkuan {
			color: #fe8e2e;
		}

		.icon-weixinzhifu {
			color: #36cb59;
		}

		.icon-alipay {
			color: #01aaef;
		}

		.tit {
			font-size: $font-lg;
			color: $font-color-dark;
			margin-bottom: 4upx;
		}

		.con {
			flex: 1;
			display: flex;
			flex-direction: column;
			font-size: $font-sm;
			color: $font-color-light;
		}
	}

	.mix-btn {
		display: flex;
		align-items: center;
		justify-content: center;
		width: 630upx;
		height: 80upx;
		margin: 80upx auto 30upx;
		font-size: $font-lg;
		color: #fff;
		background-color: $base-color;
		border-radius: 10upx;
		box-shadow: 1px 2px 5px rgba(219, 63, 96, 0.4);
	}
</style>
