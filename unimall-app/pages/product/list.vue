<template>
  <view class="content">
    <view class="navbar" :style="{position:headerPosition,top:headerTop}">
      <view class="nav-item" :class="{current: filterIndex === 0}" @click="tabClick(0)">
        销量优先
      </view>
      <view class="nav-item" :class="{current: filterIndex === 1}" @click="tabClick(1)">
        <text>价格</text>
        <view class="p-box">
          <text :class="{active: priceOrder === 1 && filterIndex === 1}" class="yticon icon-shang" />
          <text :class="{active: priceOrder === 2 && filterIndex === 1}" class="yticon icon-shang xia" />
        </view>
      </view>
    </view>
    <view class="goods-list">
      <view v-for="(item, index) in productList" :key="index" class="goods-item" @click="navToDetailPage(item)">
        <view class="image-wrapper">
          <image :src="item.img + style(200)" mode="aspectFill" />
        </view>
        <text class="title clamp">
          {{ item.title }}
        </text>
        <view class="price-box">
          <text class="price">
            {{ isVip? (item.vipPrice / 100.0 + ' [VIP]') : (item.price / 100.0) }}
          </text>
          <text>已售 {{ item.sales?item.sales:0 }}</text>
        </view>
      </view>
    </view>
    <!-- #ifndef MP-ALIPAY -->
    <uni-load-more :status="loadingType" />
    <!-- #endif -->
  </view>
</template>

<script>
import uniLoadMore from '@/components/uni-load-more/uni-load-more.vue'
export default {
  components: {
    uniLoadMore
  },
  data() {
    return {
      style: this.$api.style,
	  cateMaskState: 0, // 分类面板展开状态
      headerPosition: 'fixed',
      headerTop: '0px',
      loadingType: 'more', // 加载更多状态
      filterIndex: 0,
      priceOrder: 0, // 1 价格从低到高 2价格从高到低
      productList: [],
      cateId: 0,
      keyword: '',
      pageNo: 1,
      isVip: false
    }
  },
  onShow() {
    this.isVip = this.$api.isVip()
  },
  onLoad(options) {
    // #ifdef H5
    this.headerTop = document.getElementsByTagName('uni-page-head')[0].offsetHeight + 'px'
    // #endif
    this.cateId = options.tid ? options.tid : 0
    this.keyword = options.keyword ? options.keyword : ''
    this.loadData()
  },
  onPageScroll(e) {
    // 兼容iOS端下拉时顶部漂移
    if (e.scrollTop >= 0) {
      this.headerPosition = 'fixed'
    } else {
      this.headerPosition = 'absolute'
    }
  },
  // 下拉刷新
  onPullDownRefresh() {
    this.loadData('refresh')
  },
  // 加载更多
  onReachBottom() {
    this.loadData()
  },
  methods: {
    // 加载商品 ，带下拉刷新和上滑加载
    async loadData(type = 'add', loading) {
      // 没有更多直接返回
      if (type === 'add') {
        if (this.loadingType === 'nomore') {
          return
        }
        this.loadingType = 'loading'
      } else {
        this.loadingType = 'more'
      }

      let orderByInfo = {}
      if (this.filterIndex === 0) {
        // 销量排序
        orderByInfo = {
          orderBy: 'sales',
          isAsc: false
        }
      }
      if (this.filterIndex === 1) {
        // 价格排序 需要从新获取Page
        orderByInfo = {
          orderBy: 'price',
          isAsc: this.priceOrder === 1
        }
      }
      if (type === 'refresh') {
        this.pageNo = 1
      }
      this.$api.request('product', 'getProductPage', {
        categoryId: this.cateId,
        title: this.keyword,
        pageNo: this.pageNo,
        ...orderByInfo
      }).then(res => {
        const tempList = res.data.items
        if (type === 'refresh') {
          this.productList = []
        }
        this.productList = this.productList.concat(tempList)
        this.pageNo = res.data.pageNo + 1
        this.loadingType = res.data.totalPageNo > res.data.pageNo ? 'more' : 'nomore'
        if (type === 'refresh') {
          if (loading === 1) {
            uni.hideLoading()
          } else {
            uni.stopPullDownRefresh()
          }
        }
      })
    },
    // 筛选点击
    tabClick(index) {
      if (this.filterIndex === index && index !== 1) {
        return
      }
      this.filterIndex = index
      if (index === 1) {
        this.priceOrder = this.priceOrder === 1 ? 2 : 1
      } else {
        this.priceOrder = 0
      }
      uni.pageScrollTo({
        duration: 300,
        scrollTop: 0
      })
      this.loadData('refresh', 1)
      uni.showLoading({
        title: '正在加载'
      })
    },
    // 详情
    navToDetailPage(item) {
      // 测试数据没有写id，用title代替
      const id = item.id
      uni.navigateTo({
        url: `/pages/product/detail?id=${id}`
      })
    },
    stopPrevent() {}
  }
}
</script>

<style lang="scss">
	page,
	.content {
		background: $page-color-base;
	}

	.content {
		padding-top: 96upx;
	}

	.navbar {
		position: fixed;
		left: 0;
		top: var(--window-top);
		display: flex;
		width: 100%;
		height: 80upx;
		background: #fff;
		box-shadow: 0 2upx 10upx rgba(0, 0, 0, .06);
		z-index: 10;

		.nav-item {
			flex: 1;
			display: flex;
			justify-content: center;
			align-items: center;
			height: 100%;
			font-size: 30upx;
			color: $font-color-dark;
			position: relative;

			&.current {
				color: $base-color;

				&:after {
					content: '';
					position: absolute;
					left: 50%;
					bottom: 0;
					transform: translateX(-50%);
					width: 120upx;
					height: 0;
					border-bottom: 4upx solid $base-color;
				}
			}
		}

		.p-box {
			display: flex;
			flex-direction: column;

			.yticon {
				display: flex;
				align-items: center;
				justify-content: center;
				width: 30upx;
				height: 14upx;
				line-height: 1;
				margin-left: 4upx;
				font-size: 26upx;
				color: #888;

				&.active {
					color: $base-color;
				}
			}

			.xia {
				transform: scaleY(-1);
			}
		}

		.cate-item {
			display: flex;
			justify-content: center;
			align-items: center;
			height: 100%;
			width: 80upx;
			position: relative;
			font-size: 44upx;

			&:after {
				content: '';
				position: absolute;
				left: 0;
				top: 50%;
				transform: translateY(-50%);
				border-left: 1px solid #ddd;
				width: 0;
				height: 36upx;
			}
		}
	}

	/* 分类 */
	.cate-mask {
		position: fixed;
		left: 0;
		top: var(--window-top);
		bottom: 0;
		width: 100%;
		background: rgba(0, 0, 0, 0);
		z-index: 95;
		transition: .3s;

		.cate-content {
			width: 630upx;
			height: 100%;
			background: #fff;
			float: right;
			transform: translateX(100%);
			transition: .3s;
		}

		&.none {
			display: none;
		}

		&.show {
			background: rgba(0, 0, 0, .4);

			.cate-content {
				transform: translateX(0);
			}
		}
	}

	.cate-list {
		display: flex;
		flex-direction: column;
		height: 100%;

		.cate-item {
			display: flex;
			align-items: center;
			height: 90upx;
			padding-left: 30upx;
			font-size: 28upx;
			color: #555;
			position: relative;
		}

		.two {
			height: 64upx;
			color: #303133;
			font-size: 30upx;
			background: #f8f8f8;
		}

		.active {
			color: $base-color;
		}
	}

	/* 商品列表 */
	.goods-list {
		display: flex;
		flex-wrap: wrap;
		padding: 0 30upx;
		background: #fff;

		.goods-item {
			display: flex;
			flex-direction: column;
			width: 48%;
			padding-bottom: 40upx;

			&:nth-child(2n+1) {
				margin-right: 4%;
			}
		}

		.image-wrapper {
			width: 100%;
			height: 330upx;
			border-radius: 3px;
			overflow: hidden;

			image {
				width: 100%;
				height: 100%;
				opacity: 1;
			}
		}

		.title {
			font-size: $font-lg;
			color: $font-color-dark;
			line-height: 80upx;
		}

		.price-box {
			display: flex;
			align-items: center;
			justify-content: space-between;
			padding-right: 10upx;
			font-size: 24upx;
			color: $font-color-light;
		}

		.price {
			font-size: $font-lg;
			color: $uni-color-primary;
			line-height: 1;

			&:before {
				content: '￥';
				font-size: 26upx;
			}
		}
	}
</style>
