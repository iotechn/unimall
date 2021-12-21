<template>
	<view class="coupon-item">
		<view class="coupon-money">
			<view class="nick" v-if="!types">{{item.title}}</view>
			<view class="layof" :style="{color:theme}">￥{{item.discount / 100.0}}</view>
			<view v-if="item.couponId" class="end_time">有效期至{{item.gmtEnd | dateFormat}}</view>
			<view v-if="!item.couponId && item.gmtEnd" class="end_time">在{{item.gmtEnd}}前有效。 可领{{item.limit}}张，已领{{item.nowCount}}张</view>
			<view v-if="!item.couponId && !item.gmtEnd" class="end_time">在领取后{{item.days}}天内有效。可领{{item.limit}}张，已领{{item.nowCount}}张</view>
			<view v-if="!types">
				<!-- <view class="tit">券号：{{item.ticket}}</view> -->
				<view class="demand">满{{item.min / 100.0}}可用。{{item.categoryTitle?'限' + item.categoryTitle + '可用': '全品类可用'}}</view>
			</view>
		</view>
		<!-- <view @click="share" v-if="item.couponId" class="get-btn" :style="{color:color, borderColor:color, background:solid}">分享</view> -->
		<view @click="obtain" v-if="!item.couponId" class="get-btn" :style="{color:color, borderColor:color, background:solid}">{{ item.nowCount < item.limit ? '立即领取' : '已领取' }}</view>
	</view>
</template>

<script>
	export default {
		components: {

		},
		data() {
			return {

			}
		},
		props: {
			item: {
				type: Object
			},
			index: {
				type: Number
			},
			types: {
				type: String,
				default: ''
			},
			theme: {
				type: String,
				default: '#ff9000'
			},
			solid: {
				type: String,
				default: '#ffffff'
			},
			color: {
				type: String,
				default: '#ff9000'
			},
		},
		methods: {
			obtain() {
				this.$emit('obtainCoupon', this.item, this.index)
			},
			share() {
				this.$emit('shareCoupon', this.item, this.index)
			}
		}
	}
</script>

<style lang='scss'>
	.coupon-item {
		width: 100%;
		height: auto;
		display: table;
		border-radius: 10upx;
		padding: 0 20upx;
		margin-top: 22upx;
		border: 1px solid #eeeeee;
		position: relative;

		.coupon-money {
			width: 465upx;
			height: auto;
			display: table;
			float: left;
			padding: 26upx 0;
			border-style: none dotted none none;
			border-color: #eeeeee;

			.nick {
				width: 100%;
				height: 50upx;
				line-height: 30upx;
				font-size: $font-sm;
				color: #999999;
			}

			.tit {
				width: 100%;
				height: 50upx;
				line-height: 50upx;
				font-size: $font-sm;
				color: #999999;
			}

			.demand {
				width: 100%;
				height: 30upx;
				line-height: 30upx;
				font-size: $font-sm;
				color: #999999;
			}

			.layof {
				width: 100%;
				height: 48upx;
				line-height: 30upx;
				font-size: 44upx;
				color: #ff9000;
				font-weight: bold;
			}

			.end_time {
				width: 100%;
				height: 30upx;
				line-height: 30upx;
				font-size: $font-sm;
				color: #999999;
			}
		}

		.get-btn {
			width: 146upx;
			height: 52upx;
			line-height: 50upx;
			position: absolute;
			top: 50%;
			right: 26upx;
			margin-top: -26upx;
			text-align: center;
			border-radius: 60upx;
			color: #ff9000;
			border: 1px solid #ff9000;
			font-size: $font-sm;
			float: right;
		}
	}

	.coupon-item:after {
		width: 40upx;
		height: 20upx;
		position: absolute;
		left: 460upx;
		top: -1px;
		border-radius: 0 0 40upx 40upx;
		content: "";
		display: block;
		background: #FFF;
		border: 1px solid #eeeeee;
		border-top: 0px;
	}

	.coupon-item:before {
		width: 40upx;
		height: 20upx;
		position: absolute;
		left: 460upx;
		bottom: -1px;
		border-radius: 40upx 40upx 0 0;
		content: "";
		display: block;
		background: #FFF;
		border: 1px solid #eeeeee;
		border-bottom: 0px;
	}
</style>
