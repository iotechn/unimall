<template>
  <view class="container">
    <view class="user-section">
      <image class="bg" src="/static/user-bg.jpg" />
      <view class="user-info-box">
        <view class="portrait-box">
          <image class="portrait" :src="userInfo.avatarUrl || '/static/missing-face.png'" />
        </view>
        <view class="info-box">
          <text class="username" @click="toLogin">
            {{ hasLogin ? (userInfo.nickname || '未设置昵称') : '立即登录' }}
          </text>
        </view>
      </view>
      <view class="vip-card-box">
        <image class="card-bg" src="/static/vip-card-bg.png" mode="" />
        <view class="tit">
          <text v-if="isVip" class="yticon icon-iLinkapp-" @click="navTo('/pages/user/vip')">
            <text style="padding-left: 10rpx;">
              VIP会员
            </text>
          </text>
          <text v-else class="yticon icon-iLinkapp-" @click="navTo('/pages/user/vip')">
            <text style="padding-left: 10rpx;">
              开通会员
            </text>
          </text>
        </view>
        <text class="e-m">
          VIP权益
        </text>
        <text class="e-b">
          {{ isVip ? '会员专享VIP价' : '点击立即“立即开通”开通会员' }}
        </text>
        <text v-if="userInfo.gmtVipExpire" class="e-b">
          将于{{ userInfo.gmtVipExpire }}过期
        </text>
      </view>
    </view>

    <view
      class="cover-container"
      :style="[{
        transform: coverTransform,
        transition: coverTransition
      }]"
      @touchstart="coverTouchstart"
      @touchmove="coverTouchmove"
      @touchend="coverTouchend"
    >
      <image class="arc" src="/static/arc.png" />

      <!-- 订单 -->
      <view class="order-section">
        <view class="order-item" hover-class="common-hover" :hover-stay-time="50" @click="navTo('/pages/order/list?state=0')">
          <text class="yticon icon-shouye" />
          <text>全部订单</text>
        </view>
        <view class="order-item" hover-class="common-hover" :hover-stay-time="50" @click="navTo('/pages/order/list?state=10')">
          <text class="yticon icon-daifukuan" />
          <text>待付款</text>
        </view>
        <view class="order-item" hover-class="common-hover" :hover-stay-time="50" @click="navTo('/pages/order/list?state=30')">
          <text class="yticon icon-yishouhuo" />
          <text>待收货</text>
        </view>
        <view class="order-item" hover-class="common-hover" :hover-stay-time="50" @click="navTo('/pages/order/list?state=60')">
          <text class="yticon icon-shouhoutuikuan" />
          <text>退款/售后</text>
        </view>
      </view>

      <view class="history-section icon">
        <view v-if="footprintList.length > 0" class="sec-header">
          <text class="yticon icon-lishijilu" />
          <text>浏览历史</text>
        </view>
        <scroll-view v-if="footprintList.length > 0" scroll-x class="h-list">
          <image v-for="(item, index ) in footprintList" :key="index" :src="item.img + style(200)" mode="aspectFill" @longpress="deleteFootprint(item)" @click="navTo('/pages/product/detail?id=' + item.id)" />
        </scroll-view>
        <list-cell icon="icon-dizhi" icon-color="#5fcda2" title="地址管理" @eventClick="navTo('/pages/address/list')" />
        <list-cell icon="icon-shoucang_xuanzhongzhuangtai" icon-color="#54b4ef" title="我的收藏" @eventClick="navTo('/pages/product/favorite')" />
        <!-- #ifdef MP-WEIXIN -->
        <list-cell icon="icon-huifu" icon-color="#e07472" title="在线客服" :open-type="'contact'" />
        <!-- #endif -->
        <list-cell icon="icon-tuandui" icon-color="#EE82EE" title="个人资料" @eventClick="navTo('/pages/user/profile')" />
        <list-cell icon="icon-iconfontweixin" icon-color="#EEEE00" title="我的优惠券" @eventClick="navTo('/pages/coupon/list?type=user')" />
        <list-cell icon="icon-pinglun-copy" icon-color="#ee883b" title="关于" @eventClick="navTo('/pages/user/about')" />
        <list-cell icon="icon-zuoshang" icon-color="#e07472" title="退出登录" @eventClick="logout()" />
      </view>
    </view>
  </view>
</template>
<script>
import listCell from '@/components/mix-list-cell'
import {
  mapState
} from 'vuex'
let startY = 0; let moveY = 0; const pageAtTop = true
export default {
  components: {
    listCell
  },
  data() {
    return {
      style: this.$api.style,
	  coverTransform: 'translateY(0px)',
      coverTransition: '0s',
      moving: false,
      footprintList: [],
      isVip: false
    }
  },
  onShow() {
    this.isVip = this.$api.isVip()
    this.loadFootprint()
  },
  onLoad() {
  },
  computed: {
    ...mapState(['hasLogin', 'userInfo'])
  },
  methods: {
    async loadFootprint() {
      const that = this
      that.$api.request('footprint', 'list').then(res => {
        that.footprintList = res.data
      })
    },

    deleteFootprint(item) {
      const that = this
      uni.showModal({
        title: '删除？',
        content: '您确定要删除此足迹吗？',
        success: (e) => {
          if (e.confirm) {
            that.$api.request('footprint', 'delete', {
              footprintId: item.id
            }).then(res => {
              that.loadFootprint()
            })
          }
        }
      })
    },

    toLogin() {
      if (!this.hasLogin) {
        uni.navigateTo({
          url: '/pages/public/login'
        })
      }
    },

    logout() {
      const that = this
      uni.showModal({
        title: '询问',
        content: '您确定要退出吗？',
        cancelText: '取消',
        confirmText: '确定',
        success: (e) => {
          if (e.confirm) {
            that.$store.commit('logout')
            that.$api.logout()
          }
        }
      })
    },

    /**
     * 统一跳转接口,拦截未登录路由
     * navigator标签现在默认没有转场动画，所以用view
     */
    navTo(url) {
      if (!this.hasLogin) {
        url = '/pages/public/login'
      }
      uni.navigateTo({
        url
      })
    },

    /**
     *  会员卡下拉和回弹
     *  1.关闭bounce避免ios端下拉冲突
     *  2.由于touchmove事件的缺陷（以前做小程序就遇到，比如20跳到40，h5反而好很多），下拉的时候会有掉帧的感觉
     *    transition设置0.1秒延迟，让css来过渡这段空窗期
     *  3.回弹效果可修改曲线值来调整效果，推荐一个好用的bezier生成工具 http://cubic-bezier.com/
     */
    coverTouchstart(e) {
      if (pageAtTop === false) {
        return
      }
      this.coverTransition = 'transform .1s linear'
      startY = e.touches[0].clientY
    },
    coverTouchmove(e) {
      moveY = e.touches[0].clientY
      let moveDistance = moveY - startY
      if (moveDistance < 0) {
        this.moving = false
        return
      }
      this.moving = true
      if (moveDistance >= 80 && moveDistance < 100) {
        moveDistance = 80
      }

      if (moveDistance > 0 && moveDistance <= 80) {
        this.coverTransform = `translateY(${moveDistance}px)`
      }
    },
    coverTouchend() {
      if (this.moving === false) {
        return
      }
      this.moving = false
      this.coverTransition = 'transform 0.3s cubic-bezier(.21,1.93,.53,.64)'
      this.coverTransform = 'translateY(0px)'
    }
  }
}
</script>
<style lang='scss'>
	%flex-center {
		display:flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
	}
	%section {
		display:flex;
		justify-content: space-around;
		align-content: center;
		background: #fff;
		border-radius: 10upx;
	}

	.user-section{
		height: 520upx;
		padding: 100upx 30upx 0;
		position:relative;
		.bg{
    position:absolute;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    filter: blur(1px);
    opacity: .7;
		}
	}
	.user-info-box{
		height: 180upx;
		display:flex;
		align-items:center;
		position:relative;
		z-index: 1;
		.portrait{
    width: 130upx;
    height: 130upx;
    border:5upx solid #fff;
    border-radius: 50%;
		}
		.username{
    font-size: $font-lg + 6upx;
    color: $font-color-dark;
    margin-left: 20upx;
		}
	}

	.vip-card-box{
		display:flex;
		flex-direction: column;
		color: #f7d680;
		height: 240upx;
		background: rgba(0,0,0,.7);
		border-radius: 16upx 16upx 0 0;
		overflow: hidden;
		position: relative;
		padding: 20upx 24upx;
		.card-bg{
    position:absolute;
    top: 20upx;
    right: 0;
    width: 380upx;
    height: 260upx;
		}
		.b-btn{
    position: absolute;
    right: 20upx;
    top: 16upx;
    width: 132upx;
    height: 40upx;
    text-align: center;
    line-height: 40upx;
    font-size: 22upx;
    color: #36343c;
    border-radius: 20px;
    background: linear-gradient(left, #f9e6af, #ffd465);
    z-index: 1;
		}
		.tit{
    font-size: $font-base+2upx;
    color: #f7d680;
    margin-bottom: 28upx;
    .yticon{
    	color: #f6e5a3;
    	margin-right: 16upx;
    }
		}
		.e-b{
    font-size: $font-sm;
    color: #d8cba9;
    margin-top: 10upx;
		}
	}
	.cover-container{
		background: $page-color-base;
		margin-top: -150upx;
		padding: 0 30upx;
		position:relative;
		background: #f5f5f5;
		padding-bottom: 20upx;
		.arc{
    position:absolute;
    left: 0;
    top: -34upx;
    width: 100%;
    height: 36upx;
		}
	}
	.tj-sction{
		@extend %section;
		.tj-item{
    @extend %flex-center;
    flex-direction: column;
    height: 140upx;
    font-size: $font-sm;
    color: #75787d;
		}
		.num{
    font-size: $font-lg;
    color: $font-color-dark;
    margin-bottom: 8upx;
		}
	}
	.order-section{
		@extend %section;
		padding: 28upx 0;
		margin-top: 20upx;
		.order-item{
    @extend %flex-center;
    width: 120upx;
    height: 120upx;
    border-radius: 10upx;
    font-size: $font-sm;
    color: $font-color-dark;
		}
		.yticon{
    font-size: 48upx;
    margin-bottom: 18upx;
    color: #fa436a;
		}
		.icon-shouhoutuikuan{
    font-size:44upx;
		}
	}
	.history-section{
		padding: 30upx 0 0;
		margin-top: 20upx;
		background: #fff;
		border-radius:10upx;
		.sec-header{
    display:flex;
    align-items: center;
    font-size: $font-base;
    color: $font-color-dark;
    line-height: 40upx;
    margin-left: 30upx;
    .yticon{
    	font-size: 44upx;
    	color: #5eba8f;
    	margin-right: 16upx;
    	line-height: 40upx;
    }
		}
		.h-list{
    white-space: nowrap;
    padding: 30upx 30upx 0;
    image{
    	display:inline-block;
    	width: 160upx;
    	height: 160upx;
    	margin-right: 20upx;
    	border-radius: 10upx;
    }
		}
	}

</style>
