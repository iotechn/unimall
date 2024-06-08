<template>
  <view class="coupon_box">
    <view v-if="type === 'user'" class="other_type">
      <view class="text">
        <span>已领取优惠券</span>
      </view>
    </view>
    <coupon
      v-for="(item, index) in couponList"
      :key="index"
      :item="item"
      :index="index"
      theme="#ff0000"
      @obtainCoupon="obtainCoupon"
    />
    <view v-if="type === 'list' || obatinableCouponList.length > 0" class="other_type">
      <view class="text">
        <span>可领取</span>
      </view>
    </view>
    <coupon
      v-for="(item, index) in obatinableCouponList"
      v-show="item.surplus > 0"
      :key="index"
      :item="item"
      :index="index"
      theme="#ff0000"
      @obtainCoupon="obtainCoupon"
    />
  </view>
</template>

<script>
import coupon from '@/components/coolc-coupon/coolc-coupon'
export default {
  components: {
    coupon
  },
  data() {
    return {
      title: '可领取优惠券',
      couponList: [],
      obatinableCouponList: [],
      type: 'list',
      couponId: undefined
    }
  },
  /**
   * @param {Object} options .type 取值为 list(默认) | user
   * list: 表示从Advert等地方进去的，只显示可领取优惠券
   * user: 表示从我的进去的，显示已领取优惠券
   */
  onLoad(options) {
    if (options.type) {
      this.type = options.type
    } else {
      this.type = 'list'
    }
    this.loadData()
  },
  onShareAppMessage() {
    const that = this
    if (that.couponId) {
      return {
        title: 'Unimall-在线商城',
        imageUrl: 'http://shopmalling-asset.oss-cn-hangzhou.aliyuncs.com/coupon_share_bg.jpg',
        path: '/pages/index/index?couponid=' + that.couponId
      }
    } else {
      return {
        title: 'Unimall-在线商城',
        imageUrl: 'http://shopmalling-asset.oss-cn-hangzhou.aliyuncs.com/coupon_share_bg.jpg',
        path: '/pages/coupon/list'
      }
    }
  },
  methods: {
    async loadData() {
      const that = this
      if (that.type === 'user') {
        that.$api.request('coupon', 'getUserCoupons').then(res => {
          that.couponList = res.data
        })
      }
      that.$api.request('coupon', 'getObtainableCoupon').then(res => {
        that.obatinableCouponList = res.data
      })
    },
    obtainCoupon(item, index) {
      const that = this
      that.$api.request('coupon', 'obtainCoupon', {
        couponId: item.id
      }).then(res => {
        that.$api.msg('领取成功')
        if (that.type === 'list') {
          that.obatinableCouponList[index].nowCount++
        } else {
          that.loadData()
        }
      })
    }
  }
}
</script>

<style lang='scss'>
  page {
    background: #FFFFFF;
  }

  .coupon_box {
    width: 100%;
    height: auto;
    display: table;
    padding: 6upx 26upx 26upx 26upx;
  }

  .other_type {
    width: 100%;
    height: 90upx;
    padding-top: 50upx;

    .text {
      width: 100%;
      border-top: 1px solid #eeeeee;
      display: block;
      text-align: center;
      position: relative;
    }

    .text span {
      width: 180upx;
      height: 40upx;
      line-height: 40upx;
      color: #999999;
      display: block;
      background: #ffffff;
      position: absolute;
      left: 50%;
      top: 50%;
      margin-left: -90upx;
      margin-top: -20upx;
      font-size: $font-base;
    }
  }

  .hideCanvasView {
    position: relative;
  }

  .hideCanvas {
    position: fixed;
    top: -99999upx;
    left: -99999upx;
    z-index: -99999;
  }

  .flex_row_c_c {
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
  }

  .modalView {
    width: 100%;
    height: 100%;
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    opacity: 0;
    outline: 0;
    transform: scale(1.2);
    perspective: 2500upx;
    background: rgba(0, 0, 0, 0.6);
    transition: all .3s ease-in-out;
    pointer-events: none;
    backface-visibility: hidden;
    z-index: 999;
  }

  .modalView.show {
    opacity: 1;
    transform: scale(1);
    pointer-events: auto;
  }

  .flex_column {
    display: flex;
    flex-direction: column;
  }

  .backgroundColor-white {
    background-color: white;
  }

  .border_radius_10px {
    border-radius: 10px;
  }

  .padding1vh {
    padding: 1vh;
  }

  .posterImage {
    width: 60vw;
  }

  .flex_row {
    display: flex;
    flex-direction: row;
  }

  .marginTop2vh {
    margin-top: 2vh;
  }

  .posterBtn {
    background: $uni-color-primary;
    color: #fff;
    font-size: $font-lg;
  }
</style>
