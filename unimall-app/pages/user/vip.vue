<template>
  <view class="container">
    <!-- /////////////// -->
    <view class="rules-desc">
      <view class="top">
        <text>{{ selectedPayCard.description }}</text>
      </view>
      <view class="bottom">
        有效期: 开通后{{ selectedPayCard.dayNum }}天
      </view>
    </view>
    <!-- /////////////// -->
    <scroll-view scroll-x enable-flex class="vip-select">
      <view v-for="(item, index) in payCardList" :key="item.id" class="select-item" :class="{'selected': currentIndex === index}" @click="handleActive(index)">
        <view class="desc">
          {{ item.title }}
        </view>
        <view class="price">
          <text class="current-price">
            <text>￥</text>{{ item.price / 100.0 }}
          </text>
          <text class="old-price">
            <text>￥</text>{{ item.originalPrice / 100.0 }}
          </text>
        </view>
      </view>
    </scroll-view>
    <!-- /////////////// -->
    <view v-if="couponList.length > 0" class="coupon">
      <view class="coupon-title">
        开卡专享券
      </view>
      <scroll-view class="coupon-scroll" scroll-x enable-flex>
        <view v-for="(item, index) in couponList" :key="index" class="scroll-item" @click="obtainCoupon(item)">
          <view class="content">
            <text class="acircal" />
            <view class="left">
              <view class="limit">
                <text>￥</text>{{ item.discount / 100.0 }}
              </view>
              <view class="desc">
                满￥{{ item.min / 100.0 }}可用
              </view>
            </view>
            <view class="right">
              <view class="title">
                {{ item.title }}
              </view>
              <view class="desc">
                领取后{{ item.days }}天有效
              </view>
            </view>
            <text class="bcircal" />
          </view>
        </view>
      </scroll-view>
    </view>
    <view style="position: fixed; width: 100%; bottom: 0px">
      <view class="btn" @click="doBuy">
        购买
      </view>
    </view>
  </view>
</template>

<script>
import { mapState } from 'vuex'
export default {
  data() {
    return {
      payCardList: [],
      currentIndex: 0,
      selectedPayCard: {
		description: '',
		dayNum: 0
	  },
      couponList: [],
      buyLock: false
    }
  },
  onLoad(options) {
    if (options.shareId) {
      uni.setStorageSync('shareId', options.shareId)
      console.log(options.shareId)
    }
    this.$api.request('vip.template', 'list').then(res => {
      this.payCardList = res.data
	  if (this.payCardList.length === 0) {
		uni.showModal({
		  title: '提示',
		  content: '商户未添加会员任何套餐',
		  confirmText: '确定',
		  success: () => {
		    uni.navigateBack()
		  }
		})
	  }
      this.selectedPayCard = this.payCardList[0]
      uni.setStorageSync('payCard', this.selectedPayCard)
      this.getVipCoupon()
    })
  },
  computed: {
    ...mapState(['comfirmUser'])
  },
  onShareAppMessage() {
    return {
      title: 'Unimall',
      path: '/pages/index/index'
    }
  },
  onShareTimeline() {
    return {
      title: 'Unimall',
      path: '/pages/index/index'
    }
  },
  methods: {
    goDetail(id) {
      uni.navigateTo({
        url: '/pages/product/detail?id=' + id
      })
    },
    // 获取优惠券
    getVipCoupon() {
      this.$api.request('coupon', 'getVipCoupons').then(res => {
        this.couponList = res.data
      })
    },
    handleActive(index) {
      this.currentIndex = index
      this.selectedPayCard = this.payCardList[index]
      uni.setStorageSync('payCard', this.selectedPayCard)
    },
    doBuy() {
      const that = this
      uni.navigateTo({
      	url: '/pages/pay/pay?templateid=' + that.payCardList[that.currentIndex].id + '&type=vip&price=' + that.payCardList[that.currentIndex].price
      })
    },
	obtainCoupon(item) {
      const that = this
	  that.$api.request('coupon', 'obtainCoupon', {
		couponId: item.id
	  }).then(res => {
		that.$api.msg('领取成功！')
	  })
	}
  }
}
</script>

<style lang="scss" scoped>

.container{
	.rules-desc{
		margin: 100rpx 20rpx 30rpx 20rpx;
		background-color: #e9cbcb;
		border-radius: 15rpx 15rpx 0 0;
		padding: 30rpx;
		.top{
			display: flex;
			justify-content: space-between;
			margin-bottom: 10rpx;
			text{
				font-size: 35rpx;
				font-weight: 600;
				letter-spacing: 3rpx;
			}
			navigator{
				view{
					border: 1rpx solid #000;
					border-radius: 30rpx;
					padding: 5rpx 10rpx;
					font-size: 20rpx;
				}
			}
		}
		.bottom{
			font-size: 24rpx;
			margin-bottom: 20rpx;
		}
	}
	.description{
		padding: 20rpx;
		margin: 20rpx;
		display: flex;
		justify-content: space-around;
		.warp{
			.big{
				font-size: 40rpx;
				font-weight: 800;
				font-style: oblique;
				margin-bottom: 10rpx;
			}
			.small{
				font-size: 25rpx;
				text-align: center;
			}
		}
	}
	.vip-select{
		display: flex;
		height: 130rpx;
		width: 96%;
		margin: auto;
		.select-item{
			flex: 0 0 32%;
			border-radius: 10rpx;
			border: 1rpx solid #000;
			margin-right: 15rpx;
			padding: 20rpx 10rpx;
			.desc{
				margin-bottom: 15rpx;
			}
			.price{
				.current-price{
					font-size: 38rpx;
					font-weight: 600;
					margin-right: 5rpx;
					text{
						font-size: 20rpx;
					}
				}
				.old-price{
					text-decoration: line-through;
					opacity: 0.3;
					font-size: 25rpx;
				}
			}
		}
		.selected{
			background-color: #000000;
			.desc{
				color: #FFFFFF;
				margin-bottom: 15rpx;
			}
			.price{
				.current-price{
					color: #fcf309;
					font-size: 38rpx;
					font-weight: 600;
					margin-right: 5rpx;
					text{
						font-size: 20rpx;
					}
				}
				.old-price{
					text-decoration: line-through;
					opacity: 0.3;
					font-size: 25rpx;
					color: #FFFFFF;
				}
			}
		}
	}
	.coupon{
		margin-left: 20rpx;
		margin-top: 30rpx;
		.coupon-title{
			font-size: 35rpx;
			font-weight: 600;
			letter-spacing: 3rpx;
			margin-bottom: 20rpx;
		}
		.coupon-scroll{
			display: flex;
			width: calc( 100vw - 50rpx );
			height: 140rpx;
			margin-left: 20rpx;
			.scroll-item{
				display: flex;
				flex-shrink: 0;
				position: relative;
				margin-right: 50rpx;
				.acircal{
					width: 25rpx;
					height: 25rpx;
					border-radius: 50%;
					position: absolute;
					top: 50%;
					left: -12.5rpx;
					background-color: #fff;
					transform: translateY(-12.5rpx);
				}
				.content{
					background-color: #ffeeea;
					padding: 20rpx;
					display: flex;
					overflow: hidden;
					.left{
						border-right: 5rpx dashed #f66a42;
						margin-right: 30rpx;
						height: 100rpx;
						padding-right: 20rpx;
						.limit{
							margin-top: 10rpx;
							text-align: center;
							font-size: 50rpx;
							font-weight: 600;
							text{
								font-size: 25rpx;
							}
						}
						.desc{
							font-size: 20rpx;
							opacity: 0.3;
							}
					}
					.right{
						margin-right: 60rpx;
						.title{
							font-size: 28rpx;
							font-weight: 600;
							margin-top: 20rpx;
							margin-bottom: 15rpx;
						}
						.desc{
							font-size: 20rpx;
							opacity: 0.3;
						}
					}
				}
				.bcircal{
					width: 25rpx;
					height: 25rpx;
					border-radius: 50%;
					position: absolute;
					top: 50%;
					right: -12.5rpx;
					background-color: #fff;
					transform: translateY(-12.5rpx);
				}
			}
		}
	}
    .btn {
		width: 90%;
		padding: 0 30rpx;
		border-radius: 35rpx;
		color: white;
		background-color: #FA7D96;
		line-height: 70rpx;
		margin: 15rpx auto;
		text-align: center;
	}
	.check:after{
		border: none;
	}
}
</style>

