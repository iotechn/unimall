<template>
  <view class="content">
    <scroll-view scroll-y class="left-aside">
      <view v-for="item in flist" :key="item.id" class="f-item b-b" :class="{active: item.id === currentId}" @click="tabtap(item)">
        {{ item.title }}
      </view>
    </scroll-view>
    <scroll-view scroll-with-animation scroll-y class="right-aside" :scroll-top="tabScrollTop">
      <view class="s-list">
        <view v-for="sitem in slist" :key="sitem.id" class="s-item" @click="navToList(sitem.id)">
          <image :src="sitem.picUrl + style(200)" />
          <text>{{ sitem.title }}</text>
        </view>
      </view>
    </scroll-view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      style: this.$api.style,
      sizeCalcState: false,
      tabScrollTop: 0,
      currentId: 1,
      flist: [],
      slist: [],
      rawData: []
    }
  },
  onLoad() {
    this.loadData()
  },
  methods: {
    loadData() {
      const that = this
      this.$api.request('category', 'categoryList').then(res => {
        console.log(res)
        that.rawData = res.data
        that.flist = res.data
        that.currentId = res.data[0].id
        that.slist = res.data[0].childrenList
        console.log(that.slist)
      })
    },
    // 一级分类点击
    tabtap(item) {
      this.currentId = item.id
      this.slist = item.childrenList
      this.tabScrollTop = this.tabScrollTop === 0 ? 1 : 0
    },
    navToList(sid) {
      uni.navigateTo({
        url: `/pages/product/list?tid=${sid}`
      })
    }
  }
}
</script>

<style lang='scss'>
	page,
	.content {
		height: 100%;
		background-color: #f8f8f8;
	}

	.content {
		display: flex;
	}
	.left-aside {
		flex-shrink: 0;
		width: 200upx;
		height: 100%;
		background-color: #fff;
	}
	.f-item {
		display: flex;
		align-items: center;
		justify-content: center;
		width: 100%;
		height: 100upx;
		font-size: 28upx;
		color: $font-color-base;
		position: relative;
		&.active{
			color: $base-color;
			background: #f8f8f8;
			&:before{
				content: '';
				position: absolute;
				left: 0;
				top: 50%;
				transform: translateY(-50%);
				height: 36upx;
				width: 8upx;
				background-color: $base-color;
				border-radius: 0 4px 4px 0;
				opacity: .8;
			}
		}
	}

	.right-aside{
		flex: 1;
		overflow: hidden;
		padding-left: 20upx;
	}
	.s-list{
		display: flex;
		flex-wrap: wrap;
		width: 100%;
		background: #fff;
		padding-top: 12upx;
		margin-top: 10upx;
		&:after{
			content: '';
			flex: 99;
			height: 0;
		}
	}
	.s-item{
		flex-shrink: 0;
		display: flex;
		justify-content: center;
		align-items: center;
		flex-direction: column;
		width: 176upx;
		font-size: 26upx;
		color: #666;
		padding-bottom: 20upx;

		image{
			width: 140upx;
			height: 140upx;
		}
	}
</style>
