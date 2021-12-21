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
      @shareCoupon="shareCoupon"
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
      @shareCoupon="shareCoupon"
      @obtainCoupon="obtainCoupon"
    />
    <view class="hideCanvasView">
      <canvas class="hideCanvas" canvas-id="default_PosterCanvasId" :style="{width: (poster.width||0) + 'px', height: (poster.height||0) + 'px'}" />
    </view>

    <!-- 以下是浮层 -->
    <view class="flex_row_c_c modalView" :class="qrShow?'show':''" @tap="hideQr()">
      <view class="flex_column">
        <view class="backgroundColor-white padding1vh border_radius_10px">
          <image :src="poster.finalPath" mode="widthFix" class="posterImage" />
        </view>
        <view class="flex_row marginTop2vh">
          <button class="posterBtn" type="primary" size="mini" @tap.prevent.stop="saveImage()">
            保存图片
          </button>
          <button class="posterBtn" type="primary" open-type="share" size="mini">
            分享好友
          </button>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
import coupon from '@/components/coolc-coupon/coolc-coupon'
import { getSharePoster } from '@/components/QS-SharePoster/QS-SharePoster.js'
import _app from '@/components/QS-SharePoster/app.js'
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
      poster: {},
      qrShow: false,
      canvasId: 'default_PosterCanvasId',
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
    },
    shareCoupon(item, index) {
      const that = this
      uni.showLoading({
        title: '正在获取.'
      })
      if (!item.couponId) {
        that.$api.msg('这个优惠券不能分享')
        return
      }
      that.$api.request('coupon', 'getCouponQRCodeUrl', {
        couponId: item.couponId
      }, failres => {
        uni.hideLoading()
        that.$api.msg(failres.errmsg)
      }).then(res => {
        that.shareFc(res.data)
      })
    },
    async shareFc(qrcodeText) {
      try {
        console.log('准备生成:' + new Date())
        const d = await getSharePoster({
          _this: this, // 若在组件中使用 必传
          type: 'testShareType',
          formData: {
            // 访问接口获取背景图携带自定义数据

          },
          posterCanvasId: this.canvasId, // canvasId
          delayTimeScale: 20, // 延时系数
          /* background: {
							width: 1080,
							height: 1920,
							backgroundColor: '#666'
						}, */
          drawArray: ({
            bgObj,
            type,
            bgScale
          }) => {
            const dx = bgObj.width * 0.3
            const fontSize = bgObj.width * 0.045
            const lineHeight = bgObj.height * 0.04
            // 可直接return数组，也可以return一个promise对象, 但最终resolve一个数组, 这样就可以方便实现后台可控绘制海报
            return new Promise((rs, rj) => {
              rs([{
                type: 'custom',
                setDraw(Context) {
                  Context.setFillStyle('black')
                  Context.setGlobalAlpha(0.3)
                  Context.fillRect(0, bgObj.height - bgObj.height * 0.2, bgObj.width, bgObj.height * 0.2)
                  Context.setGlobalAlpha(1)
                }
              },
              {
                type: 'image',
                url: '/static/logo.jpg',
                alpha: 0.3,
                dx,
                dy: bgObj.height - bgObj.width * 0.25,
                infoCallBack(imageInfo) {
                  const scale = bgObj.width * 0.2 / imageInfo.height
                  return {
                    circleSet: {
                      x: imageInfo.width * scale / 2,
                      y: bgObj.width * 0.2 / 2,
                      r: bgObj.width * 0.2 / 2
                    }, // 圆形图片 , 若circleSet与roundRectSet一同设置 优先circleSet设置
                    dWidth: imageInfo.width * scale, // 因为设置了圆形图片 所以要乘以2
                    dHeight: bgObj.width * 0.2
                    /* roundRectSet: { // 圆角矩形
													r: imageInfo.width * .1
												} */
                  }
                }
              },
              {
                type: 'text',
                fontStyle: 'italic',
                text: '优惠券',
                size: fontSize,
                color: 'white',
                alpha: 0.5,
                textAlign: 'left',
                textBaseline: 'middle',
                infoCallBack(textLength) {
                  _app.log('index页面的text的infocallback ，textlength:' + textLength)
                  return {
                    dx: bgObj.width - textLength - fontSize,
                    dy: bgObj.height - lineHeight * 3
                  }
                },
                serialNum: 0,
                id: 'tag1' // 自定义标识
              },
              {
                type: 'text',
                text: '优惠券',
                fontWeight: 'bold',
                size: fontSize,
                color: 'white',
                alpha: 0.75,
                textAlign: 'left',
                textBaseline: 'middle',
                serialNum: 1,
                allInfoCallback({ // v3.0.1 更新 可以获取drawArray中全部数据
                  drawArray
                } = {}) {
                  const obj = drawArray.find(item => item.id === 'tag1')
                  /* return {
												dx: obj.dx,
												dy: obj.dy + lineHeight
											} */
                  // 也可以return promise对象
                  return new Promise((rs, rj) => {
                    setTimeout(() => {
                      rs({
                        dx: obj.dx,
                        dy: obj.dy + lineHeight
                      })
                    }, 1)
                  })
                }
              },
              {
                type: 'text',
                text: '优惠券',
                size: fontSize,
                color: 'white',
                alpha: 1,
                textAlign: 'left',
                textBaseline: 'middle',
                infoCallBack(textLength) {
                  return {
                    dx: bgObj.width - textLength - fontSize,
                    dy: bgObj.height - lineHeight
                  }
                }
              },
              {
                type: 'qrcode',
                text: qrcodeText,
                size: bgObj.width * 0.2,
                dx: bgObj.width * 0.05,
                dy: bgObj.height - bgObj.width * 0.25
              }
              ])
            })
          },
          setCanvasWH: ({
            bgObj,
            type,
            bgScale
          }) => { // 为动态设置画布宽高的方法，
            this.poster = bgObj
          }
        })
        console.log('海报生成成功, 时间:' + new Date() + '， 临时路径: ' + d.poster.tempFilePath)
        this.poster.finalPath = d.poster.tempFilePath
        this.qrShow = true
      } catch (e) {
        _app.hideLoading()
        _app.showToast(JSON.stringify(e))
        console.log(JSON.stringify(e))
      }
    },
    saveImage() {
      // #ifndef H5
      uni.saveImageToPhotosAlbum({
        filePath: this.poster.finalPath,
        success(res) {
          _app.showToast('保存成功')
        }
      })
      // #endif
      // #ifdef H5
      _app.showToast('保存了')
      // #endif
    },
    share() {
      // #ifdef APP-PLUS
      _app.getShare(false, false, 2, '', '', '', this.poster.finalPath, false, false)
      // #endif

      // #ifndef APP-PLUS
      _app.showToast('分享了')
      // #endif
    },
    hideQr() {
      this.qrShow = false
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
