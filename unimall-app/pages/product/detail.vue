<template>
  <view class="container">
    <view class="carousel">
      <swiper indicator-dots circular="true" duration="400">
        <swiper-item v-for="(item,index) in product.imgList" :key="index" class="swiper-item">
          <view class="image-wrapper">
            <image :src="item + style(600)" class="loaded" mode="aspectFill" />
          </view>
        </swiper-item>
      </swiper>
    </view>

    <view class="introduce-section">
      <text class="title">
        {{ product.title }}
      </text>
      <view class="price-box">
        <text class="price-tip">
          ¥
        </text>
        <text class="price">
          {{ isVip ? (selectedSku.vipPrice ? selectedSku.vipPrice : product.vipPrice) / 100.0 : (selectedSku.price ? selectedSku.price : product.price) / 100.0 }}
        </text>
        <text
          v-if="(isVip ? (selectedSku.vipPrice ? selectedSku.vipPrice : product.vipPrice) : (selectedSku.price ? selectedSku.price : product.price)) < (selectedSku.price ? selectedSku.originalPrice : product.originalPrice)"
          class="m-price"
        >
          ¥{{ (selectedSku.price ? selectedSku.originalPrice : product.originalPrice) / 100 }}
        </text>
        <text v-if="product.priceTag" class="coupon-tip">
          {{ product.priceTag }}
        </text>
        <text
          v-else-if="(isVip ? (selectedSku.vipPrice ? selectedSku.vipPrice : product.vipPrice) : (selectedSku.price ? selectedSku.price : product.price)) < (selectedSku.price ? selectedSku.originalPrice : product.originalPrice)"
          class="coupon-tip"
        >
          {{ parseInt((isVip? (selectedSku.vipPrice ? selectedSku.vipPrice : product.vipPrice): (selectedSku.price ? selectedSku.price : product.price)) / (selectedSku.originalPrice ? selectedSku.originalPrice : product.originalPrice) * 100) / 10 }}折
        </text>
      </view>
      <view class="bot-row">
        <text>销量: {{ product.sales }}</text>
        <text>库存: {{ product.stock }}</text>
      </view>
    </view>

    <!-- 团购分享 -->
    <button v-if="activityType === 1" class="share-section" open-type="share">
      <view class="share-icon">
        <text class="yticon icon-xingxing" />
        团
      </view>
      <text class="tit">
        {{ product.activity.minNum }}人成团，已有{{ product.activity.buyerNum }}人参团
      </text>
      <text class="yticon icon-bangzhu1" />
      <view class="share-btn">
        告诉TA
        <text class="yticon icon-you" />
      </view>
    </button>

    <view class="c-list">
      <view class="c-row b-b" @click="toggleSpec">
        <text class="tit">
          购买类型
        </text>
        <view class="con">
          <text class="selected-text">
            {{ selectedSku.title?selectedSku.title:'请选择' }}
          </text>
        </view>
        <text class="yticon icon-you" />
      </view>
      <view v-if="couponList.length > 0" class="c-row b-b" @click="toggleMask('show')">
        <text class="tit">
          优惠券
        </text>
        <text class="con t-r red">
          领取优惠券
        </text>
        <text class="yticon icon-you" />
      </view>
      <view class="c-row b-b">
        <text class="tit">
          配送费用
        </text>
        <view v-if="product.freightTemplate" class="con-list">
          <text>单笔购买满¥{{ product.freightTemplate.defaultFreePrice / 100.0 }}元免邮费</text>
          <text v-if="product.freightTemplate.defaultContinueMoney > 0">
            每增加{{ product.freightTemplate.defaultFirstNum }}件，增加运费¥{{ product.freightTemplate.freightTemplateDO.defaultContinueMoney / 100.0 }}元
          </text>
          <!-- <text v-if="product.freightTemplate.carriageDOList.length > 0">
            特殊情况说明页面
          </text> -->
        </view>
      </view>
      <view v-for="(item, index) in product.attributeList" :key="index" class="c-row b-b">
        <text class="tit">
          {{ item.attribute }}
        </text>
        <view class="bz-list con">
          <text>{{ item.value }}</text>
        </view>
      </view>
    </view>

    <!-- 评价 -->
    <view v-if="product.appraisePage && product.appraisePage.items.length > 0" class="eva-section">
      <view class="e-header">
        <text class="tit">
          评价
        </text>
        <text>({{ product.appraisePage.count }})</text>
        <text class="tip" @click="navAppraisePage">
          全部评论
        </text>
        <text class="yticon icon-you" />
      </view>
      <view class="eva-box">
        <image
          class="portrait"
          :src="product.appraisePage.items[0].userAvatarUrl ? product.appraisePage.items[0].userAvatarUrl : '/static/missing-face.png'"
          mode="aspectFill"
        />
        <view class="right">
          <text class="name">
            {{ product.appraisePage.items[0].userNickName?product.appraisePage.items[0].userNickName:('用户' + product.appraisePage.items[0].userId) }}
          </text>
          <text class="con">
            {{ product.appraisePage.items[0].content }}
          </text>
          <view class="bot">
            <text class="attr">
              购买类型：{{ product.appraisePage.items[0].skuTitle }}
            </text>
            <text class="time">
              {{ product.appraisePage.items[0].gmtCreate }}
            </text>
          </view>
        </view>
      </view>
    </view>

    <view class="detail-desc">
      <view class="d-header">
        <text>图文详情</text>
      </view>
      <u-parse class-name="rich-img" :content="product.detail" />
    </view>

    <!-- 底部操作菜单 -->
    <view class="page-bottom">
      <navigator url="/pages/index/index" open-type="switchTab" class="p-b-btn">
        <text class="yticon icon-xiatubiao--copy" />
        <text>首页</text>
      </navigator>
      <navigator url="/pages/cart/cart" open-type="switchTab" class="p-b-btn">
        <text class="yticon icon-gouwuche" />
        <text>购物车</text>
      </navigator>

      <view class="p-b-btn" :class="{active: product.favorite}" @click="likeIt">
        <text class="yticon icon-shoucang" />
        <text>收藏</text>
      </view>
      <view class="action-btn-group">
        <button type="primary" class=" action-btn no-border buy-now-btn" @click="buy">
          {{ product.groupShop ? '立即参团' : '立即购买' }}
        </button>
        <button type="primary" class=" action-btn no-border add-cart-btn" @click="addCart">
          加入购物车
        </button>
      </view>
    </view>

    <view class="mask" :class="maskState===0 ? 'none' : maskState===1 ? 'show' : ''" @click="toggleMask">
      <view class="mask-content" @click.stop.prevent="stopPrevent">
        <view v-for="(item,index) in couponList" :key="index" class="coupon-item" @click="obtainCoupon(index)">
          <view class="con">
            <view class="left">
              <text class="title">
                {{ item.title }}
              </text>
              <text v-if="item.gmtEnd" class="time">
                在{{ item.gmtEnd }}前有效。 可领{{ item.limit }}张，已领{{ item.nowCount }}张
              </text>
              <text v-if="!item.gmtEnd" class="time">
                在领取后{{ item.days }}天内有效。可领{{ item.limit }}张，已领{{ item.nowCount }}张
              </text>
            </view>
            <view class="right">
              <text class="price">
                {{ item.discount / 100.0 }}
              </text>
              <text>满{{ item.min / 100.0 }}可用</text>
            </view>
            <view class="circle l" />
            <view class="circle r" />
          </view>
          <text class="tips">
            {{ item.categoryTitle?'限' + item.categoryTitle + '可用': '全品类可用' }}
          </text>
        </view>
      </view>
    </view>

    <!-- 规格-模态层弹窗 -->
    <view class="popup spec" :class="specClass" @touchmove.stop.prevent="stopPrevent" @click="closeSpec">
      <!-- 遮罩层 -->
      <view class="mask" />
      <view class="layer attr-content">
        <view class="a-t">
          <image v-if="product.img" :src="(selectedSku.img?selectedSku.img:product.img) + style(200)" />
          <view class="right">
            <text class="price">
              ¥{{ isVip ? (selectedSku.vipPrice / 100.0) : selectedSku.price / 100.0 }} <text v-if="selectedSku && selectedSku.priceTag" class="coupon-tip">
                {{ selectedSku.priceTag }}
              </text>
            </text>
            <text class="stock">
              库存：{{ selectedSku.stock }}件
            </text>
            <view class="selected">
              已选：
              <text>
                {{ selectedSku.title }}
              </text>
            </view>
          </view>
        </view>
        <scroll-view scroll-y="true" style="max-height: 70vh;" @click.stop="stopPrevent">
          <view v-for="(specItem, specIndex) in product.specificationList" :key="specIndex" class="attr-list">
            <text>{{ specItem.title }}</text>
            <view class="item-list">
              <text
                v-for="(valueItem, valueIndex) in specItem.values"
                :key="valueIndex"
                class="tit"
                :class="{selected: valueIndex === specItem.selectedIndex}"
                @click="selectSpec(specIndex, valueIndex)"
              >
                {{ valueItem }}
              </text>
            </view>
          </view>
          <text>数量</text>
          <view style="height: 70upx; margin-bottom: 100upx; margin-top: 15upx; position: relative;">
            <uni-number-box class="step" :min="1" :value="buyNum" :is-min="buyNum===1" @eventChange="numberChange" />
          </view>
          <button class="btn" @click="toggleSpec">
            完成
          </button>
        </scroll-view>
      </view>
    </view>
  </view>
</template>

<script>
import uniNumberBox from '@/components/uni-number-box.vue'
import uParse from '@/components/u-parse/u-parse.vue'
export default {
  components: {
    uParse,
    uniNumberBox
  },
  data() {
    return {
      style: this.$api.style,
	  spuId: undefined,
      product: {
        freightTemplate: undefined,
        skuList: [],
        categoryList: [],
        appraisePage: undefined
      },
      isVip: false,
      specClass: 'none',
      specSelected: [],
      buyNum: 1,
      selectedSku: {},
      selectedSkuIndex: -1,
      toggleCallback: undefined,
      maskState: 0, // 优惠券面板显示状态
      couponList: [],
      submiting: false,
      // 有效activity, 若activity未开始或已经结束，此三个值将为 undefined
      activityType: undefined,
      activityId: undefined,
      activity: undefined

    }
  },
  onShow() {
    this.isVip = this.$api.isVip()
  },
  onLoad(options) {
    const that = this
    that.spuId = options.id
    that.loadData()
  },
  onShareAppMessage() {
    return {
      title: (this.activityType === 1 ? '立即拼团-' : '好货分享-') + this.product.title,
      imageUrl: this.product.img,
      path: '/pages/product/detail?id=' + this.product.id + (this.product.groupShop ? '&gid=' + this.product.groupShop.id : '')
    }
  },
  methods: {
    /**
     * 从后端请求数据并加载
     */
    async loadData() {
      const that = this
      uni.showLoading({
        title: '正在加载'
      })
      that.$api.request('product', 'getProduct', {
        spuId: that.spuId
      }, failres => {
        uni.hideLoading()
        that.$api.msg(failres.errmsg)
      }).then(res => {
        let stock = 0
        // 分组specification & 计算总库存
        const skuList = res.data.skuList
        for (let i = 0; i < skuList.length; i++) {
          // 1. 计算总库存
          stock += skuList[i].stock
          // 2. 分组
          const tempArray = skuList[i].specification.split(',')
          for (let j = 0; j < tempArray.length; j++) {
            const singleArray = tempArray[j].split('_')
            const key = singleArray[0]
            for (let z = 0; z < res.data.specificationList.length; z++) {
              if (res.data.specificationList[z].title === key) {
                if (res.data.specificationList[z].values) {
                  if (res.data.specificationList[z].values.indexOf(singleArray[1]) < 0) {
                    res.data.specificationList[z].values.push(singleArray[1])
                  }
                } else {
                  res.data.specificationList[z].values = [singleArray[1]]
                }
              }
            }
          }
        }
        res.data.stock = stock
        // 解析活动
        const activityType = res.data.activityType
        const activity = res.data.activity
        const timestamp = res.timestamp
        if (activityType && activity && activity.gmtStart < timestamp && activity.gmtEnd > timestamp) {
          if (activityType === 1) {
            that.activityType = activityType
            that.activityId = res.data.activityId
            that.activity = activity
            // 团购活动
            res.data.priceTag = '团购价'
            let lowestPrice = skuList[0].price
            for (let i = 0; i < skuList.length; i++) {
              skuList[i].priceTag = '团购价'
              for (let j = 0; j < activity.groupShopSkuDTOList.length; j++) {
                const groupshop = activity.groupShopSkuDTOList[j]
                if (groupshop.skuId === skuList[i].id) {
                  skuList[i].price = groupshop.skuGroupShopPrice
                  skuList[i].vipPrice = groupshop.skuGroupShopPrice
                  if (skuList[i].price < lowestPrice) {
                    lowestPrice = skuList[i].price
                  }
                }
              }
            }
            res.data.price = lowestPrice
            res.data.vipPrice = lowestPrice
          }
        }
        that.product = JSON.parse(JSON.stringify(res.data))
        uni.hideLoading()
      })
      that.$api.request('coupon', 'getObtainableCoupon').then(res => {
        that.couponList = res.data
      })
    },
    /**
     * 领取优惠券
     * @param {Number} index
     */
    obtainCoupon(index) {
      const that = this
      that.$api.request('coupon', 'obtainCoupon', {
        couponId: that.couponList[index].id
      }).then(res => {
        that.$api.msg('领取成功')
        that.couponList[index].nowCount++
        that.toggleMask()
      })
    },
    /**
     * 规格弹窗开关
     */
    toggleSpec() {
      const that = this
      if (that.specClass === 'show') {
        that.specClass = 'hide'
        setTimeout(() => {
          that.specClass = 'none'
          if (that.toggleCallback) {
            that.toggleCallback()
            that.toggleCallback = undefined
          }
        }, 150)
      } else if (that.specClass === 'none') {
        that.specClass = 'show'
        for (let i = 0; i < that.product.specificationList.length; i++) {
          if (i !== that.product.specificationList.length - 1) {
            that.product.specificationList[i].selectedIndex = 0
          } else {
            that.selectSpec(i, 0)
          }
        }
      }
    },
    /**
     * 选择规格
     * @param {Object} specIndex 选择规格维度索引，例如：颜色，内存容量
     * @param {Object} valueIndex 规格的第N个选择
     */
    selectSpec(specIndex, valueIndex) {
      const that = this
      // 1.设置索引
      that.product.specificationList[specIndex].selectedIndex = valueIndex
      // 2.选中Sku
      // 2.1 构建SkuAttr
      let attr = ''
      for (let i = 0; i < that.product.specificationList.length; i++) {
        const spec = that.product.specificationList[i]
        attr += spec.title + '_' + spec.values[spec.selectedIndex]
        if (i !== that.product.specificationList.length - 1) {
          attr += ','
        }
      }
      // 3.遍历SkuList
      for (let i = 0; i < that.product.skuList.length; i++) {
        if (that.product.skuList[i].specification === attr) {
          that.selectedSku = that.product.skuList[i]
          break
        }
      }
      that.$forceUpdate()
    },
    /**
     * 加入购物车
     */
    addCart() {
      const that = this
      if (!that.selectedSku.id) {
        that.specClass = 'none'
        that.toggleSpec()
        that.toggleCallback = that.addCart
      } else {
        // 添加到购车车
        that.$api.request('cart', 'addCartItem', {
          skuId: that.selectedSku.id,
          num: that.buyNum ? that.buyNum : 1
        }).then(res => {
          if (that.product.groupShop) {
            that.$api.msg('从购物车结算不会参加团购')
          } else {
            that.$api.msg('添加购物车成功')
          }
        })
      }
    },
    /**
     * 收藏 OR 取消收藏
     */
    likeIt() {
      const that = this
      if (that.product.favorite) {
        that.product.favorite = false
        this.$api.request('favorite', 'delete', {
          spuId: that.product.id
        }).then(res => {

        })
      } else {
        that.product.favorite = true
        this.$api.request('favorite', 'create', {
          spuId: that.product.id
        })
      }
    },
    /**
     * 购买，单品提交订单
     */
    buy() {
      const that = this
      if (!that.selectedSku.id) {
        that.specClass = 'none'
        that.toggleSpec()
        that.toggleCallback = that.buy
      } else {
        const skuItem = {
          skuId: that.selectedSku.id,
          num: that.buyNum,
          title: that.product.title,
          freightTemplateId: that.product.freightTemplateId,
          originalPrice: that.selectedSku.originalPrice,
          price: that.selectedSku.price,
          vipPrice: that.selectedSku.vipPrice,
          skuTitle: that.selectedSku.title,
          spuImg: that.product.img,
          skuImg: that.selectedSku.img,
          stock: that.selectedSku.stock,
          spuId: that.product.id,
          categoryId: that.product.categoryId,
          categoryIdList: that.product.categoryIds,
          weight: that.selectedSku.weight
        }
        if (that.product.groupShop) {
          skuItem['groupShopId'] = that.product.groupShop.id
        }
        const skuList = [1]
        skuList[0] = skuItem
        that.$api.globalData.skuList = skuList
        uni.navigateTo({
          url: `/pages/order/create?takeway=buy`
        })
      }
    },
    /**
     * 前往评论页面
     */
    navAppraisePage() {
      uni.navigateTo({
        url: `/pages/product/appraise?spuid=${this.product.id}`
      })
    },
    /** ***** 以下非业务方法 *******/
    stopPrevent() {},
    numberChange(e) {
      this.buyNum = e.number
    },
    toggleMask(type) {
      const timer = type === 'show' ? 10 : 300
      const state = type === 'show' ? 1 : 0
      this.maskState = 2
      setTimeout(() => {
        this.maskState = state
      }, timer)
    },
    closeSpec() {
      this.toggleCallback = undefined
      this.toggleSpec()
    }
  }

}
</script>

<style lang='scss'>
	page {
		background: $page-color-base;
		padding-bottom: 160upx;
	}

	.icon-you {
		font-size: $font-base + 2upx;
		color: #888;
	}

	.carousel {
		height: 722upx;
		position: relative;

		swiper {
    height: 100%;
		}

		.image-wrapper {
    width: 100%;
    height: 100%;
		}

		.swiper-item {
    display: flex;
    justify-content: center;
    align-content: center;
    height: 750upx;
    overflow: hidden;

    image {
    	width: 100%;
    	height: 100%;
    }
		}

	}

	/* 标题简介 */
	.introduce-section {
		background: #fff;
		padding: 20upx 30upx;

		.title {
    font-size: 32upx;
    color: $font-color-dark;
    height: 50upx;
    line-height: 50upx;
		}

		.price-box {
    display: flex;
    align-items: baseline;
    height: 64upx;
    padding: 10upx 0;
    font-size: 26upx;
    color: $uni-color-primary;
		}

		.price {
    font-size: $font-lg + 2upx;
		}

		.m-price {
    margin: 0 12upx;
    color: $font-color-light;
    text-decoration: line-through;
		}

		.coupon-tip {
    align-items: center;
    padding: 4upx 10upx;
    background: $uni-color-primary;
    font-size: $font-sm;
    color: #fff;
    border-radius: 6upx;
    line-height: 1;
    transform: translateY(-4upx);
		}

		.bot-row {
    display: flex;
    align-items: center;
    height: 50upx;
    font-size: $font-sm;
    color: $font-color-light;

    text {
    	flex: 1;
    }
		}
	}

	/* 分享 */
	.share-section {
		display: flex;
		align-items: center;
		color: $font-color-base;
		background: linear-gradient(left, #fdf5f6, #fbebf6);
		padding: 12upx 30upx;

		.share-icon {
    display: flex;
    align-items: center;
    width: 70upx;
    height: 30upx;
    line-height: 1;
    border: 1px solid $uni-color-primary;
    border-radius: 4upx;
    position: relative;
    overflow: hidden;
    font-size: 22upx;
    color: $uni-color-primary;

    &:after {
    	content: '';
    	width: 50upx;
    	height: 50upx;
    	border-radius: 50%;
    	left: -20upx;
    	top: -12upx;
    	position: absolute;
    	background: $uni-color-primary;
    }
		}

		.icon-xingxing {
    position: relative;
    z-index: 1;
    font-size: 24upx;
    margin-left: 2upx;
    margin-right: 10upx;
    color: #fff;
    line-height: 1;
		}

		.tit {
    font-size: $font-base;
    margin-left: 10upx;
    width: 450rpx;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
		}

		.icon-bangzhu1 {
    padding: 10upx;
    font-size: 30upx;
    line-height: 1;
		}

		.share-btn {
    flex: 1;
    text-align: right;
    font-size: $font-sm;
    color: $uni-color-primary;
		}

		.icon-you {
    font-size: $font-sm;
    margin-left: 4upx;
    color: $uni-color-primary;
		}
	}

	.c-list {
		font-size: $font-sm + 2upx;
		color: $font-color-base;
		background: #fff;

		.c-row {
    display: flex;
    align-items: center;
    padding: 20upx 30upx;
    position: relative;
		}

		.tit {
    width: 140upx;
		}

		.con {
    flex: 1;
    color: $font-color-dark;

    .selected-text {
    	margin-right: 10upx;
    }
		}

		.bz-list {
    height: 40upx;
    font-size: $font-sm+2upx;
    color: $font-color-dark;

    text {
    	display: inline-block;
    	margin-right: 30upx;
    }
		}

		.con-list {
    flex: 1;
    display: flex;
    flex-direction: column;
    color: $font-color-dark;
    line-height: 40upx;
		}

		.red {
    color: $uni-color-primary;
		}
	}

	/* 评价 */
	.eva-section {
		display: flex;
		flex-direction: column;
		padding: 20upx 30upx;
		background: #fff;
		margin-top: 16upx;

		.e-header {
    display: flex;
    align-items: center;
    height: 70upx;
    font-size: $font-sm + 2upx;
    color: $font-color-light;

    .tit {
    	font-size: $font-base + 2upx;
    	color: $font-color-dark;
    	margin-right: 4upx;
    }

    .tip {
    	flex: 1;
    	text-align: right;
    }

    .icon-you {
    	margin-left: 10upx;
    }
		}
	}

	.eva-box {
		display: flex;
		padding: 20upx 0;

		.portrait {
    flex-shrink: 0;
    width: 80upx;
    height: 80upx;
    border-radius: 100px;
		}

		.right {
    flex: 1;
    display: flex;
    flex-direction: column;
    font-size: $font-base;
    color: $font-color-base;
    padding-left: 26upx;

    .con {
    	font-size: $font-base;
    	color: $font-color-dark;
    	padding: 20upx 0;
    }

    .bot {
    	display: flex;
    	justify-content: space-between;
    	font-size: $font-sm;
    	color: $font-color-light;
    }
		}
	}

	/*  详情 */
	.detail-desc {
		background: #fff;
		margin-top: 16upx;
		width: 750upx;

		.d-header {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 80upx;
    font-size: $font-base + 2upx;
    color: $font-color-dark;
    position: relative;

    text {
    	padding: 0 20upx;
    	background: #fff;
    	position: relative;
    	z-index: 1;
    }

    &:after {
    	position: absolute;
    	left: 50%;
    	top: 50%;
    	transform: translateX(-50%);
    	width: 300upx;
    	height: 0;
    	content: '';
    	border-bottom: 1px solid #ccc;
    }
		}
	}

	/* 规格选择弹窗 */
	.attr-content {
		padding: 10upx 30upx;

		.a-t {
    display: flex;

    image {
    	width: 170upx;
    	height: 170upx;
    	flex-shrink: 0;
    	margin-top: -40upx;
    	border-radius: 8upx;
    	;
    }

    .right {
    	display: flex;
    	flex-direction: column;
    	padding-left: 24upx;
    	font-size: $font-sm + 2upx;
    	color: $font-color-base;
    	line-height: 42upx;

    	.price {
    		font-size: $font-lg;
    		color: $uni-color-primary;
    		margin-bottom: 10upx;

    		.coupon-tip {
        align-items: center;
        margin-left: 8upx;
        padding: 4upx 10upx;
        background: $uni-color-primary;
        font-size: $font-sm;
        color: #fff;
        border-radius: 6upx;
        line-height: 1;
        transform: translateY(-4upx);
    		}
    	}

    	.selected-text {
    		margin-right: 10upx;
    	}

    }
		}

		.attr-list {
    display: flex;
    flex-direction: column;
    font-size: $font-base + 2upx;
    color: $font-color-base;
    padding-top: 30upx;
    padding-left: 10upx;
		}

		.item-list {
    padding: 30upx 0 0;
    display: flex;
    flex-wrap: wrap;

    text {
    	display: flex;
    	align-items: center;
    	justify-content: center;
    	background: #eee;
    	margin-right: 20upx;
    	margin-bottom: 20upx;
    	border-radius: 100upx;
    	min-width: 60upx;
    	height: 60upx;
    	padding: 0 20upx;
    	font-size: $font-base;
    	color: $font-color-dark;
    }

    .selected {
    	background: #fbebee;
    	color: $uni-color-primary;
    }
		}
	}

	/*  弹出层 */
	.popup {
		position: fixed;
		left: 0;
		top: 0;
		right: 0;
		bottom: 0;
		z-index: 99;

		&.show {
    display: block;

    .mask {
    	animation: showPopup 0.2s linear both;
    }

    .layer {
    	animation: showLayer 0.2s linear both;
    }
		}

		&.hide {
    .mask {
    	animation: hidePopup 0.2s linear both;
    }

    .layer {
    	animation: hideLayer 0.2s linear both;
    }
		}

		&.none {
    display: none;
		}

		.mask {
    position: fixed;
    top: 0;
    width: 100%;
    height: 100%;
    z-index: 1;
    background-color: rgba(0, 0, 0, 0.4);
		}

		.layer {
    position: fixed;
    z-index: 99;
    bottom: 0;
    width: 100%;
    min-height: 40vh;
    border-radius: 10upx 10upx 0 0;
    background-color: #fff;

    .btn {
    	height: 66upx;
    	line-height: 66upx;
    	border-radius: 100upx;
    	background: $uni-color-primary;
    	font-size: $font-base + 2upx;
    	color: #fff;
    	margin: 30upx auto 20upx;
    }
		}

		@keyframes showPopup {
    0% {
    	opacity: 0;
    }

    100% {
    	opacity: 1;
    }
		}

		@keyframes hidePopup {
    0% {
    	opacity: 1;
    }

    100% {
    	opacity: 0;
    }
		}

		@keyframes showLayer {
    0% {
    	transform: translateY(120%);
    }

    100% {
    	transform: translateY(0%);
    }
		}

		@keyframes hideLayer {
    0% {
    	transform: translateY(0);
    }

    100% {
    	transform: translateY(120%);
    }
		}
	}

	/* 底部操作菜单 */
	.page-bottom {
		position: fixed;
		left: 30upx;
		bottom: 30upx;
		z-index: 95;
		display: flex;
		justify-content: center;
		align-items: center;
		width: 690upx;
		height: 100upx;
		background: rgba(255, 255, 255, .9);
		box-shadow: 0 0 20upx 0 rgba(0, 0, 0, .5);
		border-radius: 16upx;

		.p-b-btn {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    font-size: $font-sm;
    color: $font-color-base;
    width: 96upx;
    height: 80upx;

    .yticon {
    	font-size: 40upx;
    	line-height: 48upx;
    	color: $font-color-light;
    }

    &.active,
    &.active .yticon {
    	color: $uni-color-primary;
    }

    .icon-fenxiang2 {
    	font-size: 42upx;
    	transform: translateY(-2upx);
    }

    .icon-shoucang {
    	font-size: 46upx;
    }
		}

		.action-btn-group {
    display: flex;
    height: 76upx;
    border-radius: 100px;
    overflow: hidden;
    box-shadow: 0 20upx 40upx -16upx #fa436a;
    box-shadow: 1px 2px 5px rgba(219, 63, 96, 0.4);
    background: linear-gradient(to right, #ffac30, #fa436a, #F56C6C);
    margin-left: 20upx;
    position: relative;

    &:after {
    	content: '';
    	position: absolute;
    	top: 50%;
    	right: 50%;
    	transform: translateY(-50%);
    	height: 28upx;
    	width: 0;
    	border-right: 1px solid rgba(255, 255, 255, .5);
    }

    .action-btn {
    	display: flex;
    	align-items: center;
    	justify-content: center;
    	width: 180upx;
    	height: 100%;
    	font-size: $font-base;
    	padding: 0;
    	border-radius: 0;
    	background: transparent;
    }
		}
	}

	/* 优惠券面板 */
	.mask {
		display: flex;
		align-items: flex-end;
		position: fixed;
		left: 0;
		top: var(--window-top);
		bottom: 0;
		width: 100%;
		background: rgba(0, 0, 0, 0);
		z-index: 9995;
		transition: .3s;

		.mask-content {
    width: 100%;
    min-height: 30vh;
    max-height: 70vh;
    background: #f3f3f3;
    transform: translateY(100%);
    transition: .3s;
    overflow-y: scroll;
		}

		&.none {
    display: none;
		}

		&.show {
    background: rgba(0, 0, 0, .4);

    .mask-content {
    	transform: translateY(0);
    }
		}
	}

	/* 优惠券列表 */
	.coupon-item {
		display: flex;
		flex-direction: column;
		margin: 20upx 24upx;
		background: #fff;

		.con {
    display: flex;
    align-items: center;
    position: relative;
    height: 120upx;
    padding: 0 30upx;

    &:after {
    	position: absolute;
    	left: 0;
    	bottom: 0;
    	content: '';
    	width: 100%;
    	height: 0;
    	border-bottom: 1px dashed #f3f3f3;
    	transform: scaleY(50%);
    }
		}

		.left {
    display: flex;
    flex-direction: column;
    justify-content: center;
    flex: 1;
    overflow: hidden;
    height: 100upx;
		}

		.title {
    font-size: 32upx;
    color: $font-color-dark;
    margin-bottom: 10upx;
		}

		.time {
    font-size: 24upx;
    color: $font-color-light;
		}

		.right {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    font-size: 26upx;
    color: $font-color-base;
    height: 100upx;
		}

		.price {
    font-size: 44upx;
    color: $base-color;

    &:before {
    	content: '￥';
    	font-size: 34upx;
    }
		}

		.tips {
    font-size: 24upx;
    color: $font-color-light;
    line-height: 60upx;
    padding-left: 30upx;
		}

		.circle {
    position: absolute;
    left: -6upx;
    bottom: -10upx;
    z-index: 10;
    width: 20upx;
    height: 20upx;
    background: #f3f3f3;
    border-radius: 100px;

    &.r {
    	left: auto;
    	right: -6upx;
    }
		}
	}

	.rich-img {
		width: 100%;
		height: auto;
		margin: 0;
		padding: 0;
		line-height: 0px;
	}

	button::after {
		border: none;
	}
</style>
