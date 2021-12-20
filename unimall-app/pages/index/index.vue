<template>
  <view class="container">
    <!-- 小程序头部兼容 -->
    <!-- #ifdef MP -->
    <view class="mp-search-box">
      <navigator url="/pages/product/search" hover-class="none">
        <input class="ser-input" type="text" value="输入关键字搜索" disabled>
      </navigator>
    </view>
    <!-- #endif -->

    <!-- 头部轮播 -->
    <view class="carousel-section">
      <!-- 标题栏和状态栏占位符 -->
      <view class="titleNview-placing" />
      <!-- 背景色区域 -->
      <view class="titleNview-background" :style="{backgroundColor:titleNViewBackground}" />
      <swiper autoplay="true" interval="3000" duration="500" class="carousel" circular @change="swiperChange">
        <swiper-item v-for="(item, index) in carouselList" :key="index" class="carousel-item" @click="navToAdvertTargetPage(item)">
          <image :src="item.imgUrl" />
        </swiper-item>
      </swiper>
      <!-- 自定义swiper指示器 -->
      <view class="swiper-dots">
        <text class="num">
          {{ swiperCurrent+1 }}
        </text>
        <text class="sign">
          /
        </text>
        <text class="num">
          {{ swiperLength }}
        </text>
      </view>
    </view>
    <!-- 轮播下五按钮 -->
    <view class="cate-section">
      <view v-for="(item, index) in categoryButtomList" :key="index" class="cate-item" @click="navToAdvertTargetPage(item)">
        <image :src="item.imgUrl" />
        <text>{{ item.title }}</text>
      </view>
    </view>

    <!-- 店铺公告 -->
    <view v-if="postMsgs && postMsgs.length > 0" class="post-section">
      <swiper style="height: 100%;" :circular="true" :autoplay="true" :vertical="true">
        <swiper-item v-for="(item, index) in postMsgs" :key="index">
          <view class="yticon icon-xiaoxi content">
            : {{ item }}
          </view>
        </swiper-item>
      </swiper>
    </view>

    <view v-if="banner" class="ad-1" @click="navToAdvertTargetPage(banner)">
      <image :src="banner.imgUrl" mode="scaleToFill" />
    </view>

    <!-- 橱窗推荐 -->
    <view v-if="windowSpuList && windowSpuList.length > 0" class="f-header m-t">
      <image src="/static/temp/h1.png" />
      <view class="tit-box">
        <text class="tit">
          橱窗推荐
        </text>
        <text class="tit2">
          Shop Window
        </text>
      </view>
    </view>
    <view v-if="windowSpuList && windowSpuList.length > 0" class="group-section">
      <swiper class="g-swiper" :duration="500">
        <swiper-item
          v-for="(item, index) in windowSpuList"
          v-show="index % 2 === 0"
          :key="index"
          class="g-swiper-item"
        >
          <view class="g-item left" @click="navToWindowSuggestSpu(index)">
            <image :src="item.img + style(400)" mode="aspectFill" />
            <view class="t-box">
              <text class="title clamp">
                {{ item.title }}
              </text>
              <view class="price-box">
                <text class="price">
                  ￥{{ isVip ? (item.vipPrice / 100.0 + ' [VIP]') : (item.price / 100.0) }}
                </text>
                <text v-if="item.originalPrice > (isVip ? item.vipPrice : item.price)" class="m-price">
                  ￥{{ item.originalPrice / 100 }}
                </text>
              </view>

              <view class="pro-box">
                <text>累计销售:{{ item.sales }}件</text>
              </view>
            </view>
          </view>
          <view v-if="index + 1 < windowSpuList.length" class="g-item right" @click="navToWindowSuggestSpu(index + 1)">
            <image :src="windowSpuList[index+1].img" mode="aspectFill" />
            <view class="t-box">
              <text class="title clamp">
                {{ windowSpuList[index+1].title }}
              </text>
              <view class="price-box">
                <text class="price">
                  ￥{{ isVip ? (windowSpuList[index+1].vipPrice / 100.0 + ' [VIP]') : (windowSpuList[index+1].price / 100.0) }}
                </text>
                <text v-if="windowSpuList[index+1].originalPrice > (isVip ? (windowSpuList[index+1].vipPrice) : (windowSpuList[index+1].price))" class="m-price">
                  ￥{{ windowSpuList[index+1].originalPrice / 100.0 }}
                </text>
              </view>
              <view class="pro-box">
                <text>累计销售:{{ windowSpuList[index+1].sales }}件</text>
              </view>
            </view>
          </view>
          <view v-if="index + 1 === windowSpuList.length" class="g-item right" />
        </swiper-item>
      </swiper>
    </view>

    <!-- 分类推荐楼层 -->
    <view class="f-header m-t">
      <image src="/static/temp/h1.png" />
      <view class="tit-box">
        <text class="tit">
          分类精选
        </text>
        <text class="tit2">
          Competitive Products For You
        </text>
      </view>
    </view>
    <view v-for="(item, index) in categoryPickList" :key="index" class="hot-floor">
      <view class="floor-img-box">
        <image class="floor-img" :src="item.imgUrl" mode="scaleToFill" />
      </view>
      <scroll-view class="floor-list" scroll-x>
        <view class="scoll-wrapper">
          <view
            v-for="(spuItem, skuIndex) in item.data"
            :key="skuIndex"
            class="floor-item"
            @click="navToDetailPage(spuItem.id)"
          >
            <image :src="spuItem.img + style(200)" mode="aspectFill" />
            <text class="title clamp">
              {{ spuItem.title }}
            </text>
            <text class="price">
              ￥{{ (isVip ? spuItem.vipPrice : spuItem.price) / 100 }}
            </text>
          </view>
          <view class="more" @click="navToAdvertTargetPage(item)">
            <text>查看全部</text>
            <text>More+</text>
          </view>
        </view>
      </scroll-view>
    </view>

    <!-- 销量top -->
    <view class="f-header m-t">
      <image src="/static/temp/h1.png" />
      <view class="tit-box">
        <text class="tit">
          热销宝贝
        </text>
        <text class="tit2">
          Hot Sale
        </text>
      </view>
    </view>

    <view class="guess-section">
      <view
        v-for="(item, index) in salesTop"
        :key="index"
        class="guess-item"
        @click="navToDetailPage(item.id)"
      >
        <view class="image-wrapper">
          <image :src="item.img + style(400)" mode="aspectFill" />
        </view>
        <text class="title clamp">
          {{ item.title }}
        </text>
        <text class="price">
          ￥{{ isVip ? (item.vipPrice / 100 + ' [VIP]') : item.price / 100 }}
        </text>
      </view>
    </view>
  </view>
</template>

<script>
const PLATFORM_WAP = 5
export default {
  data() {
    return {
      uni,
	  style: this.$api.style,
      titleNViewBackground: '',
      swiperCurrent: 0,
      swiperLength: 0,
      carouselList: [],
      windowSpuList: [],
      categoryPickList: [],
      categoryButtomList: [],
      salesTop: [],
      banner: undefined,
      isVip: false,
      postMsgs: []
    }
  },
  onShow() {
    this.isVip = this.$api.isVip()
  },
  onLoad(options) {
    this.loadData()
  },
  methods: {
    async loadData() {
      const that = this
      uni.showLoading({
        title: '正在加载'
      })
      that.$api.request('integral', 'getIndexData', failres => {
        that.$api.msg(failres.errmsg)
        uni.hideLoading()
      }).then(res => {
        const data = res.data
        // 轮播
        if (data.advertisement.t1) {
          data.advertisement.t1.forEach(item => {
            if (!item.color) {
              item.color = 'rgb(205, 215, 218)'
            }
          })
          that.carouselList = data.advertisement.t1
          that.swiperLength = data.advertisement.t1.length
          that.titleNViewBackground = data.advertisement.t1[0].color
        }
        // 分类精选
        if (data.advertisement.t2) {
          that.categoryPickList = data.advertisement.t2
        }
        // 横幅
        if (data.advertisement.t3 && data.advertisement.t3.length > 0) {
          that.banner = data.advertisement.t3[0]
        }
        // 热销
        if (data.salesTop) {
          that.salesTop = data.salesTop
        }
        // 分类5Buttom
        if (data.advertisement.t4) {
          that.categoryButtomList = data.advertisement.t4
        }
        // 橱窗
        if (data.advertisement.t9) {
          this.windowSpuList = data.advertisement.t9.map(item => item.data)
          console.log(this.windowSpuList)
        }
        // 公告
        if (data.advertisement.t6) {
          this.postMsgs = data.advertisement.t6.map(item => item.title)
        }
        uni.hideLoading()
      })
    },
    // 轮播图切换修改背景色
    swiperChange(e) {
      const index = e.detail.current
      this.swiperCurrent = index
      this.titleNViewBackground = this.carouselList[index].color
    },
    // 详情页
    navToDetailPage(id) {
      uni.navigateTo({
        url: `/pages/product/detail?id=${id}`
      })
    },
    // 橱窗推荐跳转
    navToWindowSuggestSpu(index) {
      const that = this
      uni.navigateTo({
        url: '/pages/product/detail?id=' + that.windowSpuList[index].id
      })
    },
    // 跳转到广告目标页
    navToAdvertTargetPage(advert) {
      // 针对Advert Type 不同做不同跳转
      const { unionType, unionValue } = advert
      let url = '/pages/index/index'
      if (unionType === 1) {
        url = '/pages/product/detail?id=' + unionValue
      } else if (unionType === 2) {
        url = '/pages/product/list?tid=' + unionValue
      } else if (unionType === 3) {
        url = '/pages/product/list?keyword=' + unionValue
      } else if (unionType === 4) {
        url = unionValue
      }
      uni.navigateTo({
        url: url
      })
    }
  },
  // #ifndef MP
  // 标题栏input搜索框点击
  onNavigationBarSearchInputClicked: async function(e) {
    uni.navigateTo({
      url: '/pages/product/search'
    })
  }
  // #endif
}
</script>

<style lang="scss">
	.container {
		position: relative;
	}
	/* #ifdef MP */
	.mp-search-box{
		position:absolute;
		left: 0;
		top: 30upx;
		z-index: 9999;
		width: 100%;
		padding: 0 80upx;
		.ser-input{
			flex:1;
			height: 56upx;
			line-height: 56upx;
			text-align: center;
			font-size: 28upx;
			color:$font-color-base;
			border-radius: 20px;
			background: rgba(255,255,255,.6);
			width: 100%;
		}
	}
	page{
		.cate-section{
			position:relative;
			z-index:5;
			border-radius:16upx 16upx 0 0;
			margin-top:-20upx;
		}
		.carousel-section{
			padding: 0;
			.titleNview-placing {
				padding-top: 0;
				height: 0;
			}
			.carousel{
				.carousel-item{
					padding: 0;
				}
			}
			.swiper-dots{
				left:45upx;
				bottom:40upx;
			}
		}

		.post-section {
			margin-top: 16upx;
			height: 60upx;
			line-height: 60upx;
			background-color: white;
			padding-left: 20upx;

			.content {
				height: 60upx;
				line-height: 60upx;
				font-size: 28upx;
				text-overflow: ellipsis;
				overflow: hidden;
				color: $uni-color-primary;
				white-space: nowrap;
			}
		}
	}
	/* #endif */

	page {
		background: #f5f5f5;
	}
	.m-t{
		margin-top: 16upx;
	}
	/* 头部 轮播图 */
	.carousel-section {
		position: relative;
		padding-top: 10px;

		.titleNview-placing {
			height: var(--status-bar-height);
			padding-top: 44px;
			box-sizing: content-box;
		}

		.titleNview-background {
			position: absolute;
			top: 0;
			left: 0;
			width: 100%;
			height: 426upx;
			transition: .4s;
		}
	}
	.carousel {
		width: 100%;
		height: 350upx;

		.carousel-item {
			width: 100%;
			height: 100%;
			padding: 0 28upx;
			overflow: hidden;
		}

		image {
			width: 100%;
			height: 100%;
			border-radius: 10upx;
		}
	}
	.swiper-dots {
		display: flex;
		position: absolute;
		left: 60upx;
		bottom: 15upx;
		width: 72upx;
		height: 36upx;
		background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAMgAAABkCAYAAADDhn8LAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyZpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNi1jMTMyIDc5LjE1OTI4NCwgMjAxNi8wNC8xOS0xMzoxMzo0MCAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6OTk4MzlBNjE0NjU1MTFFOUExNjRFQ0I3RTQ0NEExQjMiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6OTk4MzlBNjA0NjU1MTFFOUExNjRFQ0I3RTQ0NEExQjMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTcgKFdpbmRvd3MpIj4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6Q0E3RUNERkE0NjExMTFFOTg5NzI4MTM2Rjg0OUQwOEUiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6Q0E3RUNERkI0NjExMTFFOTg5NzI4MTM2Rjg0OUQwOEUiLz4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz4Gh5BPAAACTUlEQVR42uzcQW7jQAwFUdN306l1uWwNww5kqdsmm6/2MwtVCp8CosQtP9vg/2+/gY+DRAMBgqnjIp2PaCxCLLldpPARRIiFj1yBbMV+cHZh9PURRLQNhY8kgWyL/WDtwujjI8hoE8rKLqb5CDJaRMJHokC6yKgSCR9JAukmokIknCQJpLOIrJFwMsBJELFcKHwM9BFkLBMKFxNcBCHlQ+FhoocgpVwwnv0Xn30QBJGMC0QcaBVJiAMiec/dcwKuL4j1QMsVCXFAJE4s4NQA3K/8Y6DzO4g40P7UcmIBJxbEesCKWBDg8wWxHrAiFgT4fEGsB/CwIhYE+AeBAAdPLOcV8HRmWRDAiQVcO7GcV8CLM8uCAE4sQCDAlHcQ7x+ABQEEAggEEAggEEAggEAAgQACASAQQCCAQACBAAIBBAIIBBAIIBBAIABe4e9iAe/xd7EAJxYgEGDeO4j3EODp/cOCAE4sYMyJ5cwCHs4rCwI4sYBxJ5YzC84rCwKcXxArAuthQYDzC2JF0H49LAhwYUGsCFqvx5EF2T07dMaJBetx4cRyaqFtHJ8EIhK0i8OJBQxcECuCVutxJhCRoE0cZwMRyRcFefa/ffZBVPogePihhyCnbBhcfMFFEFM+DD4m+ghSlgmDkwlOgpAl4+BkkJMgZdk4+EgaSCcpVX7bmY9kgXQQU+1TgE0c+QJZUUz1b2T4SBbIKmJW+3iMj2SBVBWz+leVfCQLpIqYbp8b85EskIxyfIOfK5Sf+wiCRJEsllQ+oqEkQfBxmD8BBgA5hVjXyrBNUQAAAABJRU5ErkJggg==);
		background-size: 100% 100%;

		.num {
			width: 36upx;
			height: 36upx;
			border-radius: 50px;
			font-size: 24upx;
			color: #fff;
			text-align: center;
			line-height: 36upx;
		}

		.sign {
			position: absolute;
			top: 0;
			left: 50%;
			line-height: 36upx;
			font-size: 12upx;
			color: #fff;
			transform: translateX(-50%);
		}
	}
	/* 分类 */
	.cate-section {
		display: flex;
		justify-content: space-around;
		align-items: center;
		flex-wrap:wrap;
		padding: 30upx 22upx;
		background: #fff;
		.cate-item {
			display: flex;
			flex-direction: column;
			align-items: center;
			font-size: $font-sm + 2upx;
			color: $font-color-dark;
		}
		/* 原图标颜色太深,不想改图了,所以加了透明度 */
		image {
			width: 88upx;
			height: 88upx;
			margin-bottom: 14upx;
			border-radius: 50%;
			opacity: .7;
			box-shadow: 4upx 4upx 20upx rgba(250, 67, 106, 0.3);
		}
	}
	.ad-1{
		width: 100%;
		height: 210upx;
		padding: 10upx 0;
		background: #fff;
		image{
			width:100%;
			height: 100%;
		}
	}
	/* 秒杀专区 */
	.seckill-section{
		padding: 4upx 30upx 24upx;
		background: #fff;
		.s-header{
			display:flex;
			align-items:center;
			height: 92upx;
			line-height: 1;
			.s-img{
				width: 140upx;
				height: 30upx;
			}
			.tip{
				font-size: $font-base;
				color: $font-color-light;
				margin: 0 20upx 0 40upx;
			}
			.timer{
				display:inline-block;
				width: 40upx;
				height: 36upx;
				text-align:center;
				line-height: 36upx;
				margin-right: 14upx;
				font-size: $font-sm+2upx;
				color: #fff;
				border-radius: 2px;
				background: rgba(0,0,0,.8);
			}
			.icon-you{
				font-size: $font-lg;
				color: $font-color-light;
				flex: 1;
				text-align: right;
			}
		}
		.floor-list{
			white-space: nowrap;
		}
		.scoll-wrapper{
			display:flex;
			align-items: flex-start;
		}
		.floor-item{
			width: 150upx;
			margin-right: 20upx;
			font-size: $font-sm+2upx;
			color: $font-color-dark;
			line-height: 1.8;
			image{
				width: 150upx;
				height: 150upx;
				border-radius: 6upx;
			}
			.price{
				color: $uni-color-primary;
			}
		}
	}

	.f-header{
		display:flex;
		align-items:center;
		height: 140upx;
		padding: 6upx 30upx 8upx;
		background: #fff;
		image{
			flex-shrink: 0;
			width: 80upx;
			height: 80upx;
			margin-right: 20upx;
		}
		.tit-box{
			flex: 1;
			display: flex;
			flex-direction: column;
		}
		.tit{
			font-size: $font-lg +2upx;
			color: #font-color-dark;
			line-height: 1.3;
		}
		.tit2{
			font-size: $font-sm;
			color: $font-color-light;
		}
		.icon-you{
			font-size: $font-lg +2upx;
			color: $font-color-light;
		}
	}
	/* 团购楼层 */
	.group-section{
		background: #fff;
		.g-swiper{
			height: 650upx;
			padding-bottom: 30upx;
		}
		.g-swiper-item{
			width: 100%;
			padding: 0 30upx;
			display:flex;
		}
		image{
			width: 100%;
			height: 460upx;
			border-radius: 4px;
		}
		.g-item{
			display:flex;
			flex-direction: column;
			overflow:hidden;
		}
		.left{
			flex: 1.2;
			margin-right: 24upx;
			.t-box{
				padding-top: 20upx;
			}
		}
		.right{
			flex: 0.8;
			flex-direction: column-reverse;
			.t-box{
				padding-bottom: 20upx;
			}
		}
		.t-box{
			height: 160upx;
			font-size: $font-base+2upx;
			color: $font-color-dark;
			line-height: 1.6;
		}
		.price{
			color:$uni-color-primary;
		}
		.m-price{
			font-size: $font-sm+2upx;
			text-decoration: line-through;
			color: $font-color-light;
			margin-left: 8upx;
		}
		.pro-box{
			display:flex;
			align-items:center;
			margin-top: 10upx;
			font-size: $font-sm;
			color: $font-base;
			padding-right: 10upx;
		}
		.progress-box{
			flex: 1;
			border-radius: 10px;
			overflow: hidden;
			margin-right: 8upx;
		}
	}
	/* 分类推荐楼层 */
	.hot-floor{
		width: 100%;
		overflow: hidden;
		margin-bottom: 20upx;
		.floor-img-box{
			width: 100%;
			height:320upx;
			position:relative;
			&:after{
				content: '';
				position:absolute;
				left: 0;
				top: 0;
				width: 100%;
				height: 100%;
				background: linear-gradient(rgba(255,255,255,.06) 30%, #f8f8f8);
			}
		}
		.floor-img{
			width: 100%;
			height: 100%;
		}
		.floor-list{
			white-space: nowrap;
			padding: 20upx;
			padding-right: 50upx;
			border-radius: 6upx;
			margin-top:-140upx;
			margin-left: 30upx;
			background: #fff;
			box-shadow: 1px 1px 5px rgba(0,0,0,.2);
			position: relative;
			z-index: 1;
		}
		.scoll-wrapper{
			display:flex;
			align-items: flex-start;
		}
		.floor-item{
			width: 180upx;
			margin-right: 20upx;
			font-size: $font-sm+2upx;
			color: $font-color-dark;
			line-height: 1.8;
			image{
				width: 180upx;
				height: 180upx;
				border-radius: 6upx;
			}
			.price{
				color: $uni-color-primary;
			}
		}
		.more{
			display:flex;
			align-items: center;
			justify-content: center;
			flex-direction: column;
			flex-shrink: 0;
			width: 180upx;
			height: 180upx;
			border-radius: 6upx;
			background: #f3f3f3;
			font-size: $font-base;
			color: $font-color-light;
			text:first-child{
				margin-bottom: 4upx;
			}
		}
	}
	/* 猜你喜欢 */
	.guess-section{
		display:flex;
		flex-wrap:wrap;
		padding: 0 30upx;
		background: #fff;
		.guess-item{
			display:flex;
			flex-direction: column;
			width: 48%;
			padding-bottom: 40upx;
			&:nth-child(2n+1){
				margin-right: 4%;
			}
		}
		.image-wrapper{
			width: 100%;
			height: 330upx;
			border-radius: 3px;
			overflow: hidden;
			image{
				width: 100%;
				height: 100%;
				opacity: 1;
			}
		}
		.title{
			font-size: $font-lg;
			color: $font-color-dark;
			line-height: 80upx;
		}
		.price{
			font-size: $font-lg;
			color: $uni-color-primary;
			line-height: 1;
		}
	}

</style>
