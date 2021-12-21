<template>
  <view class="container">
    <!-- 空白页 -->
    <empty v-if="loadingType === 'nomore' && groupShopList.length === 0" />
    <view class="groupshop-list">
      <block v-for="(item, index) in groupShopList" :key="index">
        <navigator class="groupshop-item" :class="{'b-b': index!==groupShopList.length-1}" :url="'/pages/product/detail?id=' + item.spuId + '&gid=' + item.id">
          <view class="image-wrapper">
            <image
              :src="item.img + style(200)"
              :class="[item.loaded]"
              mode="aspectFill"
              lazy-load
              @load="onImageLoad('groupShopList', index)"
              @error="onImageError('groupShopList', index)"
            />
          </view>
          <view class="item-right">
            <text class="clamp-title">
              {{ item.title }}
            </text>
            <view class="pro-box">
              <view class="progress-box">
                <progress :percent="100 * item.buyerNum / item.minNum" activeColor="#fa436a" active stroke-width="6" />
              </view>
              <text>{{ item.minNum }}人成团</text>
            </view>
            <text class="bottom">
              <!-- <text style="text-decoration:line-through;color: #6B6B6B;">¥{{item.originalPrice / 100.0}}</text> -->
              <text class="price">
                ¥{{ item.minPrice / 100.0 }}
              </text>
              <text class="attr info">
                已拼团{{ item.buyerNum }}件
              </text>
            </text>
          </view>
        </navigator>
      </block>
    </view>
  </view>
</template>

<script>
import empty from '@/components/empty'
export default {
  components: {
    empty
  },
  data() {
    return {
      style: this.$api.style,
	  groupShopList: [],
      pageNo: 1,
      loadingType: 'more'
    }
  },
  onLoad(options) {
    this.loadData()
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
    // 获取收藏列表
    loadData(type) {
      const that = this
      if (type === 'refresh') {
        that.pageNo = 1
        that.groupShopList = []
        that.loadingType = 'more'
      }
      if (that.loadingType === 'more') {
        that.loadingType = 'loading'
        that.$api.request('groupshop', 'getGroupShopPage', {
          pageNo: that.pageNo
        }).then(res => {
          that.pageNo = res.data.pageNo + 1
          that.loadingType = res.data.pageNo < res.data.totalPageNo ? 'more' : 'nomore'
          res.data.items.forEach(item => {
            that.groupShopList.push(item)
          })
          if (type === 'refresh') {
            uni.stopPullDownRefresh()
          }
        })
      }
    },
    // 监听image加载完成
    onImageLoad(key, index) {
      this.$set(this[key][index], 'loaded', 'loaded')
    },
    // 监听image加载失败
    onImageError(key, index) {
      this[key][index].image = '/static/errorImage.jpg'
    }
  }
}
</script>

<style lang="scss">
	.container {
		padding-bottom: 134upx;

		/* 空白页 */
		.empty {
			position: fixed;
			left: 0;
			top: 0;
			width: 100%;
			height: 100vh;
			padding-bottom: 100upx;
			display: flex;
			justify-content: center;
			flex-direction: column;
			align-items: center;
			background: #fff;

			image {
				width: 240upx;
				height: 160upx;
				margin-bottom: 30upx;
			}

			.empty-tips {
				display: flex;
				font-size: $font-sm+2upx;
				color: $font-color-disabled;

				.navigator {
					color: $uni-color-primary;
					margin-left: 16upx;
				}
			}
		}
	}

	/* 团购列表项 */
	.groupshop-item {
		display: flex;
		padding: 30upx 40upx;

		.image-wrapper {
			width: 230upx;
			height: 230upx;
			image {
				border-radius: 8upx;
			}
		}

		.item-right {
			flex: 1;
			position: relative;
			padding-left: 30upx;
			.clamp-title{
				width: 410upx;
				display: -webkit-box;
				-webkit-line-clamp: 3;
				-webkit-box-orient: vertical;
			}
			.bottom {
				font-size: $font-base + 2upx;
				color: $font-color-dark;
				height: 40upx;
				line-height: 40upx;
				position: absolute;
				bottom: 10upx;
				left: 30upx;

			}

			.price {
				color: $uni-color-primary;
				font-size: 18px;
			}

			.attr {
				font-size: $font-sm + 2upx;
				color: $font-color-light;
				height: 50upx;
				line-height: 50upx;
			}

			.info {
				margin-left: 10upx;
			}

			.pro-box{
				display:flex;
				align-items:center;
				margin-top: 10upx;
				font-size: $font-sm;
				color: $font-base;
				padding-right: 10upx;

				.progress-box{
					flex: 1;
					border-radius: 10px;
					overflow: hidden;
					margin-right: 8upx;
				}

			}

			.price {
				height: 50upx;
				line-height: 50upx;
			}
		}
	}
</style>
