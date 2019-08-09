<template>
	<view class="content">
		<view class="navbar">
			<view v-for="(item, index) in navList" :key="index" class="nav-item" :class="{current: tabCurrentIndex === index}"
			 @click="tabClick(index)">
				{{item.text}}
			</view>
		</view>

		<swiper :current="tabCurrentIndex" class="swiper-box" duration="300" @change="changeTab">
			<swiper-item class="tab-content" v-for="(tabItem,tabIndex) in navList" :key="tabIndex">
				<scroll-view class="list-scroll-content" scroll-y @scrolltolower="loadData">
					<!-- 空白页 -->
					<empty v-if="tabItem.loaded === true && tabItem.orderList.length === 0"></empty>

					<!-- 订单列表 -->
					<view v-for="(item,index) in tabItem.orderList" :key="index" class="order-item">
						<navigator :url="'/pages/order/detail?orderid=' + item.id">
							<view class="i-top b-b">
								<text class="time">{{item.gmtCreate | dateFormat}}</text>
								<text class="state">{{statusMap[item.status]}}</text>
							</view>

							<scroll-view v-if="item.skuList.length > 1" class="goods-box" scroll-x>
								<view v-for="(skuItem, skuIndex) in item.skuList" :key="skuIndex" class="goods-item">
									<image class="goods-img" :src="skuItem.img + '?x-oss-process=style/200px'" mode="aspectFill"></image>
								</view>
							</scroll-view>
							<view v-if="item.skuList.length === 1" class="goods-box-single" v-for="(skuItem, skuIndex) in item.skuList" :key="skuIndex">
								<image class="goods-img" :src="skuItem.img + '?x-oss-process=style/200px'" mode="aspectFill"></image>
								<view class="right">
									<text class="title clamp">{{skuItem.spuTitle}}</text>
									<text class="attr-box">{{skuItem.title}} x {{skuItem.num}}</text>
									<text class="price">{{skuItem.price / 100.0}}</text>
								</view>
							</view>

							<view class="price-box">
								共
								<text class="num">{{item.skuCount}}</text>
								件商品 实付款
								<text class="price">{{item.actualPrice / 100.0}}</text>
							</view>
						</navigator>
						<view class="action-box b-t" v-if="item.status == 10">
							<button :disabled="submiting" class="action-btn" @click="cancelOrder(item)">取消订单</button>
							<button class="action-btn recom" @click="payOrder(item)">立即支付</button>
						</view>
						<view class="action-box b-t" v-if="item.status == 20">
							<button :disabled="submiting" class="action-btn" @click="refundOrder(item)">申请退款</button>
						</view>
						<view class="action-box b-t" v-if="item.status == 30">
							<button :disabled="submiting" class="action-btn" @click="refundOrder(item)">申请退款</button>
							<button :disabled="submiting" class="action-btn" @click="showShipTrace(item)">查看物流</button>
							<button :disabled="submiting" class="action-btn recom" @click="confirmOrder(item)">确认收货</button>
						</view>
						<view class="action-box b-t" v-if="item.status == 40">
							<navigator url="./orderAppraise">
								<button :disabled="submiting" class="action-btn recom" @click="appraiseOrder(item)">立即评价</button>
							</navigator>
						</view>
					</view>

					<uni-load-more :status="tabItem.loadingType"></uni-load-more>

				</scroll-view>
			</swiper-item>
		</swiper>
	</view>
</template>

<script>
	import uniLoadMore from '@/components/uni-load-more/uni-load-more.vue';
	import empty from "@/components/empty";
	export default {

		components: {
			uniLoadMore,
			empty
		},
		data() {
			return {
				statusMap: {
					10: '未付款',
					20: '待出库',
					30: '待收货',
					40: '待评价',
					50: '已完成',
					60: '退款中',
					70: '已退款',
					80: '已取消',
					90: '已取消(系统)'
				},
				submiting: false,
				tabCurrentIndex: 0,
				navList: [{
						state: 0,
						text: '全部',
						loadingType: 'more',
						pageNo: 1,
						orderList: []
					},
					{
						state: 10,
						text: '待付款',
						loadingType: 'more',
						pageNo: 1,
						orderList: []
					},
					{
						state: 20,
						text: '待出库',
						loadingType: 'more',
						pageNo: 1,
						orderList: []
					},
					{
						state: 30,
						text: '待收货',
						loadingType: 'more',
						pageNo: 1,
						orderList: []
					},
					{
						state: 40,
						text: '待评价',
						loadingType: 'more',
						pageNo: 1,
						orderList: []
					},
					{
						state: 60,
						text: '退款中',
						loadingType: 'more',
						pageNo: 1,
						orderList: []
					}
				],
			};
		},
		onLoad(options) {
			/**
			 * 修复app端点击除全部订单外的按钮进入时不加载数据的问题
			 * 替换onLoad下代码即可
			 */
			this.tabCurrentIndex = 0
			for (let i = 0; i < this.navList.length; i++) {
				if (this.navList[i].state === parseInt(options.state)) {
					this.tabCurrentIndex = i
				}
			}
			// #ifndef MP
			this.loadData()
			// #endif
			// #ifdef MP
			if (options.state == 0) {
				this.loadData()
			}
			// #endif

		},

		methods: {
			//获取订单列表
			loadData(source) {
				const that = this
				//这里是将订单挂载到tab列表下
				let index = that.tabCurrentIndex;
				let navItem = that.navList[index];
				let state = navItem.state;

				if (source === 'tabChange' && navItem.loaded === true) {
					//tab切换只有第一次需要加载数据
					return;
				}
				if (navItem.loadingType === 'loading') {
					//防止重复加载
					return;
				}

				navItem.loadingType = 'loading';

				let orderList = that.$api.request('order', 'getOrderPage', {
					pageNo: navItem.pageNo,
					status: navItem.state
				}).then(res => {
					navItem.pageNo = res.data.pageNo + 1
					navItem.loadingType = res.data.pageNo < res.data.totalPageNo ? 'more' : 'noMore'
					res.data.items.forEach(item => {
						navItem.orderList.push(item);
						item.skuCount = 0
						item.skuList.forEach(skuItem => {
							item.skuCount += skuItem.num
						})
					})
					//loaded新字段用于表示数据加载完毕，如果为空可以显示空白页
					that.$set(navItem, 'loaded', true);
				})
			},

			//swiper 切换
			changeTab(e) {
				this.tabCurrentIndex = e.target.current;
				this.loadData('tabChange');
			},
			//顶部tab点击
			tabClick(index) {
				this.tabCurrentIndex = index;
			},
			payOrder(item) {
				uni.redirectTo({
					url: '/pages/pay/pay?orderno='+ item.orderNo + '&price=' + item.actualPrice
				})
			},
			//取消订单
			cancelOrder(item) {
				const that = this
				uni.showModal({
					title: '取消？',
					content: '您确定要取消此订单吗？',
					success : (e) => {
						if (e.confirm) {
							that.submiting = true
							that.$api.request('order', 'cancel', {
								orderNo: item.orderNo
							}, failres => {
								that.submiting = false
								that.$api.msg(failres.errmsg)
							}).then(res => {
								that.submiting = false
								item.status = 80
							})
						}
					}
				})
				
			},
			//订单退款
			refundOrder(item) {
				const that = this
				uni.showModal({
					title: '退款？',
					content: '您确定要退款吗？',
					success : (e) => {
						if (e.confirm) {
							that.submiting = true
							that.$api.request('order', 'refund', {
								orderNo: item.orderNo
							}, failres => {
								that.submiting = false
								that.$api.msg(failres.errmsg)
							}).then(res => {
								that.submiting = false
								item.status = 60
							})
						}
					}
				})
			},
			//确认订单
			confirmOrder(item) {
				const that = this
				uni.showModal({
					title: '退款？',
					content: '您确定要退款吗？',
					success : (e) => {
						if (e.confirm) {
							that.submiting = true
							that.$api.request('order', 'confirm', {
								orderNo: item.orderNo
							}, failres => {
								that.submiting = false
								that.$api.msg(failres.errmsg)
							}).then(res => {
								that.submiting = false
								item.status = 40
							})
						}
					}
				})
			},
			showShipTrace(item) {
				uni.navigateTo({
					url: "/pages/order/trace?orderno=" + item.orderNo
				})
			},
			//评价订单
			appraiseOrder(item) {
				uni.navigateTo({
					url: '/pages/order/appraise?orderid=' + item.id
				})
			}
		}
	}
</script>

<style lang="scss">
	page,
	.content {
		background: $page-color-base;
		height: 100%;
	}

	.swiper-box {
		height: calc(100% - 40px);
	}

	.list-scroll-content {
		height: 100%;
	}

	.navbar {
		display: flex;
		height: 40px;
		padding: 0 5px;
		background: #fff;
		box-shadow: 0 1px 5px rgba(0, 0, 0, .06);
		position: relative;
		z-index: 10;

		.nav-item {
			flex: 1;
			display: flex;
			justify-content: center;
			align-items: center;
			height: 100%;
			font-size: 15px;
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
					width: 44px;
					height: 0;
					border-bottom: 2px solid $base-color;
				}
			}
		}
	}

	.uni-swiper-item {
		height: auto;
	}

	.order-item {
		display: flex;
		flex-direction: column;
		padding-left: 30upx;
		background: #fff;
		margin-top: 16upx;

		.i-top {
			display: flex;
			align-items: center;
			height: 80upx;
			padding-right: 30upx;
			font-size: $font-base;
			color: $font-color-dark;
			position: relative;

			.time {
				flex: 1;
			}

			.state {
				color: $base-color;
			}

			.del-btn {
				padding: 10upx 0 10upx 36upx;
				font-size: $font-lg;
				color: $font-color-light;
				position: relative;

				&:after {
					content: '';
					width: 0;
					height: 30upx;
					border-left: 1px solid $border-color-dark;
					position: absolute;
					left: 20upx;
					top: 50%;
					transform: translateY(-50%);
				}
			}
		}

		/* 多条商品 */
		.goods-box {
			height: 160upx;
			padding: 20upx 0;
			white-space: nowrap;

			.goods-item {
				width: 120upx;
				height: 120upx;
				display: inline-block;
				margin-right: 24upx;
			}

			.goods-img {
				display: block;
				width: 100%;
				height: 100%;
			}
		}

		/* 单条商品 */
		.goods-box-single {
			display: flex;
			padding: 20upx 0;

			.goods-img {
				display: block;
				width: 120upx;
				height: 120upx;
			}

			.right {
				flex: 1;
				display: flex;
				flex-direction: column;
				padding: 0 30upx 0 24upx;
				overflow: hidden;

				.title {
					font-size: $font-base + 2upx;
					color: $font-color-dark;
					line-height: 1;
				}

				.attr-box {
					font-size: $font-sm + 2upx;
					color: $font-color-light;
					padding: 10upx 12upx;
				}

				.price {
					font-size: $font-base + 2upx;
					color: $font-color-dark;

					&:before {
						content: '￥';
						font-size: $font-sm;
						margin: 0 2upx 0 8upx;
					}
				}
			}
		}

		.price-box {
			display: flex;
			justify-content: flex-end;
			align-items: baseline;
			padding: 20upx 30upx;
			font-size: $font-sm + 2upx;
			color: $font-color-light;

			.num {
				margin: 0 8upx;
				color: $font-color-dark;
			}

			.price {
				font-size: $font-lg;
				color: $font-color-dark;

				&:before {
					content: '￥';
					font-size: $font-sm;
					margin: 0 2upx 0 8upx;
				}
			}
		}

		.action-box {
			display: flex;
			justify-content: flex-end;
			align-items: center;
			height: 100upx;
			position: relative;
			padding-right: 30upx;
		}

		.action-btn {
			width: 160upx;
			height: 60upx;
			margin: 0;
			margin-left: 24upx;
			padding: 0;
			text-align: center;
			line-height: 60upx;
			font-size: $font-sm + 2upx;
			color: $font-color-dark;
			background: #fff;
			border-radius: 100px;

			&:after {
				border-radius: 100px;
			}

			&.recom {
				background: #fff9f9;
				color: $base-color;

				&:after {
					border-color: #f7bcc8;
				}
			}
		}
	}


	/* load-more */
	.uni-load-more {
		display: flex;
		flex-direction: row;
		height: 80upx;
		align-items: center;
		justify-content: center
	}

	.uni-load-more__text {
		font-size: 28upx;
		color: #999
	}

	.uni-load-more__img {
		height: 24px;
		width: 24px;
		margin-right: 10px
	}

	.uni-load-more__img>view {
		position: absolute
	}

	.uni-load-more__img>view view {
		width: 6px;
		height: 2px;
		border-top-left-radius: 1px;
		border-bottom-left-radius: 1px;
		background: #999;
		position: absolute;
		opacity: .2;
		transform-origin: 50%;
		animation: load 1.56s ease infinite
	}

	.uni-load-more__img>view view:nth-child(1) {
		transform: rotate(90deg);
		top: 2px;
		left: 9px
	}

	.uni-load-more__img>view view:nth-child(2) {
		transform: rotate(180deg);
		top: 11px;
		right: 0
	}

	.uni-load-more__img>view view:nth-child(3) {
		transform: rotate(270deg);
		bottom: 2px;
		left: 9px
	}

	.uni-load-more__img>view view:nth-child(4) {
		top: 11px;
		left: 0
	}

	.load1,
	.load2,
	.load3 {
		height: 24px;
		width: 24px
	}

	.load2 {
		transform: rotate(30deg)
	}

	.load3 {
		transform: rotate(60deg)
	}

	.load1 view:nth-child(1) {
		animation-delay: 0s
	}

	.load2 view:nth-child(1) {
		animation-delay: .13s
	}

	.load3 view:nth-child(1) {
		animation-delay: .26s
	}

	.load1 view:nth-child(2) {
		animation-delay: .39s
	}

	.load2 view:nth-child(2) {
		animation-delay: .52s
	}

	.load3 view:nth-child(2) {
		animation-delay: .65s
	}

	.load1 view:nth-child(3) {
		animation-delay: .78s
	}

	.load2 view:nth-child(3) {
		animation-delay: .91s
	}

	.load3 view:nth-child(3) {
		animation-delay: 1.04s
	}

	.load1 view:nth-child(4) {
		animation-delay: 1.17s
	}

	.load2 view:nth-child(4) {
		animation-delay: 1.3s
	}

	.load3 view:nth-child(4) {
		animation-delay: 1.43s
	}

	@-webkit-keyframes load {
		0% {
			opacity: 1
		}

		100% {
			opacity: .2
		}
	}
</style>
