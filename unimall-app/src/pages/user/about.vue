<template>
  <view class="content">
    <view class="row b-b">
      <text class="tit">
        版本号
      </text>
      <text class="tiv">
        v4.0.0
      </text>
    </view>
    <view class="row b-b">
      <text class="tit">
        官网
      </text>
      <text class="tiv">
        www.dobbinsoft.com
      </text>
    </view>
    <view class="row b-b">
      <text class="tit">
        邮箱
      </text>
      <text class="tiv">
        weichaozheng@dobbinsoft.com
      </text>
    </view>
    <text class="tcp">
      道宾云（重庆）科技有限公司
    </text>
  </view>
</template>

<script>
export default {
  data() {
    return {

    }
  },
  methods: {

  }
}
</script>

<style lang="scss">
	page {
		background: $page-color-base;
		padding-top: 16upx;
	}

	.row{
		display: flex;
		align-items: center;
		position: relative;
		padding:0 30upx;
		height: 110upx;
		background: #fff;

		.tit{
			flex-shrink: 0;
			width: 120upx;
			font-size: 30upx;
			color: $font-color-dark;
		}
		.tiv{
			flex: 1;
			font-size: 30upx;
			color: #aaaaaa;
			text-align: right;
		}

	}

	.tcp{
		width: 100%;
		font-size: 28upx;
		color: #aaaaaa;
		text-align: center;
		position: absolute;
		bottom: 200upx;
	}

</style>
