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
      <view :style="{backgroundColor:titleNViewBackground}" class="titleNview-background" />
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
      <swiper :circular="true" :autoplay="true" :vertical="true" style="height: 100%;">
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
      <swiper :duration="500" class="g-swiper">
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
        <image :src="item.imgUrl" class="floor-img" mode="scaleToFill" />
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
export default {
  data() {
    return {
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
@import './index.scss'
</style>
