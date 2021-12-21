<template>
  <view class="container">
    <!-- 空白页 -->
    <view v-if="!hasLogin || empty===true" class="empty">
      <image src="/static/emptyCart.jpg" mode="aspectFit" />
      <view v-if="hasLogin" class="empty-tips">
        空空如也
        <navigator v-if="hasLogin" class="navigator" url="/pages/index/index" open-type="switchTab">
          随便逛逛>
        </navigator>
      </view>
      <view v-else class="empty-tips">
        空空如也
        <view class="navigator" @click="navToLogin">
          去登陆>
        </view>
      </view>
    </view>
    <view v-else>
      <!-- 列表 -->
      <view class="cart-list">
        <block v-for="(item, index) in cartList" :key="item.id">
          <view
            class="cart-item"
            :class="{'b-b': index!==cartList.length-1}"
          >
            <view class="image-wrapper">
              <image
                :src="(item.skuImg?item.skuImg:item.spuImg) + style(200)"
                :class="loadedItemIds.has(item.id) ? 'loaded': ''"
                mode="aspectFill"
                lazy-load
                @load="onImageLoad(item)"
                @error="onImageError('cartList', index)"
              />
              <view
                class="yticon icon-xuanzhong2 checkbox"
                :class="{checked: item.checked}"
                @click="check('item', index)"
              />
            </view>
            <view class="item-right">
              <text class="clamp title">
                {{ item.title }}
              </text>
              <text class="attr">
                {{ item.skuTitle }} <text v-show="item.num > item.stock" style="color: #FF570A;">
                  {{ ' (库存不足 剩余:' + item.stock + ')' }}
                </text>
              </text>
              <text class="price">
                <text v-if="item.originalPrice > item.price" style="text-decoration:line-through">
                  ¥{{ isVip ? (item.vipPrice / 100 + '[VIP]') : item.originalPrice / 100.0 }}
                </text> ¥{{ item.price / 100.0 }}
              </text>
              <uni-number-box
                class="step"
                :min="1"
                :value="item.num"
                :is-min="item.num===1"
                :index="index"
                @eventChange="numberChange"
              />
            </view>
            <text class="del-btn yticon icon-fork" @click="deleteCartItem(index)" />
          </view>
        </block>
      </view>
      <!-- 底部菜单栏 -->
      <view class="action-section">
        <view class="checkbox">
          <image
            :src="allChecked?'/static/selected.png':'/static/select.png'"
            mode="aspectFit"
            @click="check('all')"
          />
          <view class="clear-btn" :class="{show: allChecked}" @click="clearCart">
            清空
          </view>
        </view>
        <view class="total-box">
          <text class="price">
            ¥{{ total / 100.0 }}
          </text>
          <text class="coupon">
            总共
            <text>{{ totalItems }}</text>
            件
          </text>
        </view>
        <button type="primary" class="no-border confirm-btn" @click="createOrder">
          去结算
        </button>
      </view>
    </view>
  </view>
</template>

<script>
import {
  mapState
} from 'vuex'
import uniNumberBox from '@/components/uni-number-box.vue'
export default {
  components: {
    uniNumberBox
  },
  data() {
    return {
	  style: this.$api.style,
      totalItems: 0, // 总数量
      total: 0, // 总价格
      allChecked: false, // 全选状态  true|false
      empty: false, // 空白页现实  true|false
      cartList: [],
      loadedItemIds: new Set()
    }
  },
  onLoad() {

  },
  onShow() {
    this.loadData()
  },
  computed: {
    ...mapState(['hasLogin'])
  },
  watch: {
    // 显示空白页
    cartList(e) {
      const empty = e.length === 0
      if (this.empty !== empty) {
        this.empty = empty
      }
    }
  },
  methods: {
    // 请求数据
    async loadData() {
      const that = this
      that.$api.request('cart', 'getCartList').then(res => {
        res.data.forEach(item => {
          item.checked = true
        })
        that.cartList = res.data
        that.calcTotal() // 计算总价
      })
    },
    // 监听image加载完成
    onImageLoad(item) {
      this.loadedItemIds.add(item.id)
      this.$forceUpdate()
    },
    // 监听image加载失败
    onImageError(key, index) {
      this[key][index].skuImg = '/static/errorImage.jpg'
    },
    navToLogin() {
      uni.navigateTo({
        url: '/pages/public/login'
      })
    },
    // 选中状态处理
    check(type, index) {
      if (type === 'item') {
        this.cartList[index].checked = !this.cartList[index].checked
      } else {
        const checked = !this.allChecked
        const list = this.cartList
        list.forEach(item => {
          item.checked = checked
        })
        this.allChecked = checked
      }
      this.calcTotal(type)
    },
    // 数量
    numberChange(data) {
      const that = this
      that.$api.request('cart', 'updateCartItemNum', {
        cartId: that.cartList[data.index].id,
        num: data.number
      }, failres => {
        that.$api.msg(failres.errmsg)
        // that.cartList[data.index].num = that.cartList[data.index].num
      }).then(res => {
        that.cartList[data.index].num = data.number
        that.calcTotal()
      })
    },
    // 删除
    deleteCartItem(index) {
      const that = this
      that.$api.request('cart', 'removeCartItem', {
        cartId: that.cartList[index].id
      }).then(res => {
        that.cartList.splice(index, 1)
        that.calcTotal()
        // uni.hideLoading();
      })
    },
    // 清空
    clearCart() {
      const that = this
      uni.showModal({
        content: '清空购物车？',
        success: (e) => {
          if (e.confirm) {
            that.$api.request('cart', 'removeCartAll').then(res => {
              that.cartList = []
            })
          }
        }
      })
    },
    // 计算总价
    calcTotal() {
      const that = this
      const list = that.cartList
      if (list.length === 0) {
        that.empty = true
        return
      }
      let total = 0
      let totalItems = 0
      let checked = true
      list.forEach(item => {
        if (item.checked === true) {
          totalItems += item.num
          total += (that.isVip ? item.vipPrice : item.price) * item.num
        } else if (checked === true) {
          checked = false
        }
      })
      this.allChecked = checked
      this.total = Number(total.toFixed(2))
      this.totalItems = totalItems
    },
    // 创建订单
    createOrder() {
      // 滤除未被选择的item
      const selectedItems = []
      this.cartList.forEach(item => {
        if (item.checked) {
          selectedItems.push(item)
        }
      })
      if (selectedItems.length === 0) {
        this.$api.msg('您没有选中任何商品')
        return
      }
      console.log(selectedItems)
      this.$api.globalData.skuList = selectedItems
      uni.navigateTo({
        url: `/pages/order/create?takeway=cart`
      })
    }
  }
}
</script>

<style lang='scss'>
	.container{
		padding-bottom: 134upx;
		/* 空白页 */
		.empty{
			position:fixed;
			left: 0;
			top:0;
			width: 100%;
			height: 100vh;
			padding-bottom:100upx;
			display:flex;
			justify-content: center;
			flex-direction: column;
			align-items:center;
			background: #fff;
			image{
				width: 240upx;
				height: 160upx;
				margin-bottom:30upx;
			}
			.empty-tips{
				display:flex;
				font-size: $font-sm+2upx;
				color: $font-color-disabled;
				.navigator{
					color: $uni-color-primary;
					margin-left: 16upx;
				}
			}
		}
	}
	/* 购物车列表项 */
	.cart-item{
		display:flex;
		position:relative;
		padding:30upx 40upx;
		.image-wrapper{
			width: 230upx;
			height: 230upx;
			flex-shrink: 0;
			position:relative;
			image{
				border-radius:8upx;
			}
		}
		.checkbox{
			position:absolute;
			left:-16upx;
			top: -16upx;
			z-index: 8;
			font-size: 44upx;
			line-height: 1;
			padding: 4upx;
			color: $font-color-disabled;
			background:#fff;
			border-radius: 50px;
		}
		.item-right{
			display:flex;
			flex-direction: column;
			flex: 1;
			overflow: hidden;
			position:relative;
			padding-left: 30upx;
			.title,.price{
				font-size:$font-base + 2upx;
				color: $font-color-dark;
				height: 40upx;
				line-height: 40upx;
			}
			.attr{
				font-size: $font-sm + 2upx;
				color: $font-color-light;
				height: 50upx;
				line-height: 50upx;
			}
			.price{
				height: 50upx;
				line-height:50upx;
			}
		}
		.del-btn{
			padding:4upx 10upx;
			font-size:34upx;
			height: 50upx;
			color: $font-color-light;
		}
	}
	/* 底部栏 */
	.action-section{
		/* #ifdef H5 */
		margin-bottom:100upx;
		/* #endif */
		position:fixed;
		left: 30upx;
		bottom:30upx;
		z-index: 95;
		display: flex;
		align-items: center;
		width: 690upx;
		height: 100upx;
		padding: 0 30upx;
		background: rgba(255,255,255,.9);
		box-shadow: 0 0 20upx 0 rgba(0,0,0,.5);
		border-radius: 16upx;
		.checkbox{
			height:52upx;
			position:relative;
			image{
				width: 52upx;
				height: 100%;
				position:relative;
				z-index: 5;
			}
		}
		.clear-btn{
			position:absolute;
			left: 26upx;
			top: 0;
			z-index: 4;
			width: 0;
			height: 52upx;
			line-height: 52upx;
			padding-left: 38upx;
			font-size: $font-base;
			color: #fff;
			background: $font-color-disabled;
			border-radius:0 50px 50px 0;
			opacity: 0;
			transition: .2s;
			&.show{
				opacity: 1;
				width: 120upx;
			}
		}
		.total-box{
			flex: 1;
			display:flex;
			flex-direction: column;
			text-align:right;
			padding-right: 40upx;
			.price{
				font-size: $font-lg;
				color: $font-color-dark;
			}
			.coupon{
				font-size: $font-sm;
				color: $font-color-light;
				text{
					color: $font-color-dark;
				}
			}
		}
		.confirm-btn{
			padding: 0 38upx;
			margin: 0;
			border-radius: 100px;
			height: 76upx;
			line-height: 76upx;
			font-size: $font-base + 2upx;
			background: $uni-color-primary;
			box-shadow: 1px 2px 5px rgba(217, 60, 93, 0.72)
		}
	}
	/* 复选框选中状态 */
	.action-section .checkbox.checked,
	.cart-item .checkbox.checked{
		color: $uni-color-primary;
	}
</style>
