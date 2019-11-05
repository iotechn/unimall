<template>
	<view style="width: 100%;">
		<view class="search-box">
			<input @click="naviageToPage('/pages/product/search')" class="ser-input" type="text" value="输入关键字搜索" disabled />
		</view>
		<view class="content">

			<scroll-view scroll-y class="left-aside">
				<view v-for="item in categoryTree" :key="item.id" class="f-item b-b" :class="{active: item.id === currentId}" v-if="item.childrenList.length > 0"
				 @click="tabtap(item)">
					{{item.title}}
				</view>
			</scroll-view>
			<view class="right-aside">
				<view class="sec-cate-bg">
					<view v-for="item in firstCurrent.childrenList" :key="item.id" class="s-item" :class="{active: item.id === scurrentId}"
					 @click="stap(item)">
						{{item.title}}
					</view>
				</view>
				<scroll-view scroll-y="true" :scroll-top="tabScrollTop" @scrolltolower="scrollToBottom" style="padding-top: 25upx; height: 100%; padding-bottom: 200upx;">
					<view v-for="(glistitem, glistindex) in currentLeafList.leaflist" :key="glistindex" v-if="glistitem.values.length > 0"
					 class="s-list">
						<text class="t-item">{{glistitem.title}}</text>
						<view class="g-list" v-for="(gitem, gindex) in glistitem.values" :key="gindex">
							<view class="g-item b-b">
								<view class="image-wrapper">
									<image :src="gitem.img + '?x-oss-process=style/200px'" :class="loadedItemIds.has(gitem.id) ? 'loaded': ''"
									 mode="aspectFill" lazy-load @load="onImageLoad(gitem)" @error="onImageError(glistindex, gindex)"></image>
								</view>
								<view class="item-right">
									<text class="clamp title">{{gitem.title}}</text>
									<view class="price-bar">
										<text class="price">
											<text v-if="gitem.originalPrice > gitem.price" style="text-decoration:line-through">¥{{gitem.originalPrice / 100.0}}</text>
											¥{{gitem.price / 100.0}}
										</text>
										<view class="pick">
											<text class="sub" @click="sub(glistindex, gindex)">-</text>
											<text class="num">{{gitem.num?gitem.num:0}}</text>
											<text class="plus" @click="plus(glistindex, gindex)">+</text>
										</view>
									</view>
								</view>
							</view>
						</view>
					</view>
				</scroll-view>
			</view>

			<!-- 底部菜单栏 -->
			<view class="action-section">
				<view class="total-box">
					<text class="price">¥{{total / 100.0}}</text>
					<text class="coupon">
						总共
						<text>{{totalItems}}</text>
						件
					</text>
				</view>
				<button type="primary" class="no-border confirm-btn" @click="toCart">去结算</button>
			</view>
		</view>
	</view>
</template>

<script>
	import uniNumberBox from '@/components/uni-number-box.vue'
	export default {
		components: {
			uniNumberBox
		},
		data() {
			return {
				sizeCalcState: false,
				tabScrollTop: 0,
				currentId: 1,
				scurrentId: 1,
				firstCurrent: {},
				secondCurrent: {},
				categoryTree: [],
				cglist: [],
				gmap: {},
				currentLeafList: {
					leaflist: []
				},
				total: 0,
				totalItems: 0,
				cartList: [],
				loadedItemIds: new Set(),
				over: false,
				gpoint: 0,
				grawlist: []
			}
		},
		onShow() {
			const that = this
			that.over = false
			that.isVip = that.$api.isVip()
			that.$api.request('cart', 'getCartList').then(res => {
				res.data.forEach(item => {
					item.checked = true
				})
				that.cartList = res.data
				that.calcPrice() //计算总价
				that.reloadSpu()
			})
		},
		onLoad() {
			this.loadData();
		},
		methods: {
			loadData() {
				const that = this
				that.$api.request('category', 'categoryList').then(res => {
					that.categoryTree = res.data
					that.currentId = res.data[0].id
					that.firstCurrent = res.data[0]
					if (res.data[0].childrenList && res.data[0].childrenList.length > 0) {
						that.secondCurrent = res.data[0].childrenList[0]
						that.scurrentId = that.secondCurrent.id
					}

					that.$api.request('cart', 'getCartList', {}, failres => {
						that.reloadSpu()
					}).then(res => {
						that.cartList = res.data
						that.reloadSpu()
					})
				})

			},
			//一级分类点击
			tabtap(item) {
				const that = this
				that.currentId = item.id
				that.firstCurrent = item
				if (item.childrenList && item.childrenList.length > 0) {
					that.scurrentId = item.childrenList[0].id
				}
				that.reloadSpu()
			},

			//二级类目点击
			stap(item) {
				const that = this
				that.scurrentId = item.id
				that.secondCurrent = item
				that.reloadSpu()
			},

			onImageLoad(item) {
				if (item.id) {
					this.loadedItemIds.add(item.id)
					this.$forceUpdate()
				}

			},
			//监听image加载失败
			onImageError(glistindex, gindex) {
				this.currentLeafList.leaflist[glistindex].values[gindex].img = '/static/errorImage.jpg';
			},

			/**
			 * 当到达底部时
			 */
			scrollToBottom() {
				const that = this
				if (that.over) {
					return
				}
				//gpoint 即将加第 gpoint个 grawlist 商品列表
				//跳过gpoint个
				let point = that.gpoint
				let counter = 0
				outter: for (let i = 0; i < that.grawlist.length; i++) {
					if (counter === 0) {
						//未找到之前，才改变pointer的值
						point -= that.grawlist[i].data.length
					}
					if (point <= 0) {
						if (counter === 0) {
							point += that.grawlist[i].data.length
						}
						//获取到即将加载的地方
						let datalist = []
						let isNode = false
						for (let j = point; j < that.grawlist[i].data.length; j++) {
							counter++
							let item = that.grawlist[i].data[j]
							item.num = 0
							//是否存在购物车
							if (that.cartList && that.cartList.length > 0) {
								that.cartList.forEach(cartItem => {
									if (cartItem.spuId === item.id) {
										item.num = cartItem.num
										that.totalPrice = cartItem
									}
								})
							}
							datalist.push(item)
							if (counter >= 10) {
								if (point === 0) {
									//说明是刚好从头部开始的，重新建立一个clist对象
									that.currentLeafList.leaflist.push({
										id: that.scurrentId,
										title: that.grawlist[i].name,
										values: datalist
									})
								} else {
									//说明不是从头部开始的。最后一片叶子后面追加
									let last = that.currentLeafList.leaflist.length - 1
									that.currentLeafList.leaflist[last].values = that.currentLeafList.leaflist[last].values.concat(datalist)
								}
								that.gpoint += counter
								break outter;
							} else {
								//或者即将退出，
								if (j == that.grawlist[i].data.length - 1) {
									if (point === 0) {
										//最后一片叶子是独立的
										isNode = true
										that.currentLeafList.leaflist.push({
											id: that.scurrentId,
											title: that.grawlist[i].name,
											values: datalist
										})
									} else {
										point = 0
										isNode = true
										let last = that.currentLeafList.leaflist.length - 1
										that.currentLeafList.leaflist[last].values = that.currentLeafList.leaflist[last].values.concat(datalist)
									}
									if (i == that.grawlist.length - 1) {
										//最后一片叶子
										that.over = true
									}
								}
							}
						}
						if (!isNode) {
							if (point === 0) {
								//说明是刚好从头部开始的，重新建立一个clist对象
								that.currentLeafList.leaflist.push({
									id: that.scurrentId,
									title: that.grawlist[i].name,
									values: datalist
								})
								//这种就说明下一次一定是从头开始的
								point = 0
							} else {
								//说明不是从头部开始的。则最佳到clist最后
								let last = that.currentLeafList.leaflist.length - 1
								that.currentLeafList.leaflist[last].values = that.currentLeafList.leaflist[last].values.concat(datalist)
							}
						}


					}

				}
			},

			reloadSpu() {
				uni.showLoading({
					title: '加载中'
				})

				const that = this
				that.tabScrollTop = that.tabScrollTop === 1 ? 0 : 1
				let cacheCGlist = undefined
				for (let exist in that.cglist) {
					if (exist.categoryId === that.scurrentId) {
						cacheCGlist = exit
					}
				}
				if (!cacheCGlist) {
					that.$api.request('goods', 'getGoodsBySecondCategory', {
						categoryId: that.scurrentId
					}, failres => {
						uni.hideLoading()
						that.$api.msg(failres.errmsg)
					}).then(res => {
						let clist = []
						let rawlist = []
						for (let f in res.data) {
							if (res.data[f] && res.data[f].length > 0) {
								rawlist.push({
									data: res.data[f],
									name: f
								})
							}
						}
						let counter = 0
						//外层
						outter: for (let i = 0; i < rawlist.length; i++) {
							let datalist = []
							inner: for (let j = 0; j < rawlist[i].data.length; j++) {
								//商品层
								counter++
								let item = rawlist[i].data[j]
								item.num = 0
								//是否存在购物车
								if (that.cartList && that.cartList.length > 0) {
									that.cartList.forEach(cartItem => {
										if (cartItem.spuId === item.id) {
											item.num = cartItem.num
											that.totalPrice = cartItem
										}
									})
								}
								datalist.push(item)
								if (counter >= 10) {
									//将剩下这个类封装到clist里面，然后break
									clist.push({
										id: that.scurrentId,
										title: rawlist[i].name,
										values: datalist
									})
									break outter;
								}
							}
							//若循环完一次，则直接将整个放进去
							clist.push({
								id: that.scurrentId,
								title: rawlist[i].name,
								values: datalist
							})
						}
						that.gpoint = counter
						that.grawlist = rawlist
						that.currentLeafList = {
							categoryId: that.scurrentId,
							leaflist: clist
						}
						that.cglist.push(that.currentLeafList)
						uni.hideLoading()
					})
				} else {
					that.currentLeafList = cacheCGlist
					uni.hideLoading()
				}

			},

			plus(glistindex, gindex) {
				const that = this
				let item = that.currentLeafList.leaflist[glistindex].values[gindex]
				if (!item.num) {
					item.num = 0
				}
				let originalNum = item.num
				if (item.num == 0) {
					item.num = item.saleMinNum
				} else {
					item.num += item.saleMinNum
				}
				that.$api.request('cart', 'addCartItemBySpu', {
					num: item.saleMinNum,
					spuId: item.id
				}, failres => {
					item.num = item.originalNum
				}).then(res => {
					let isNew = true
					for (let i = 0; i < that.cartList.length; i++) {
						let cartItem = that.cartList[i]
						if (cartItem.spuId === item.id) {
							cartItem.num += item.saleMinNum
							isNew = false
						}
					}
					if (isNew) {
						that.cartList.push({
							spuId: item.id,
							num: item.num,
							price: item.price,
							vipPrice: item.vipPrice,
							originalPrice: item.originalPrice
						})
					}
					that.calcPrice()
				})
			},

			sub(glistindex, gindex) {
				const that = this
				let item = that.currentLeafList.leaflist[glistindex].values[gindex]
				if (!item.num) {
					item.num = 0
				}
				let originalNum = item.num
				if (item.num == 0) {
					item.num = 0
				} else {
					item.num -= item.saleMinNum
				}
				that.$api.request('cart', 'subCartItemBySpu', {
					num: item.saleMinNum,
					spuId: item.id
				}, failres => {
					item.num = item.originalNum
				}).then(res => {
					for (let i = 0; i < that.cartList.length; i++) {
						let cartItem = that.cartList[i]
						if (cartItem.spuId === item.id) {
							cartItem.num -= item.saleMinNum
							if (cartItem.num < 0) {
								cartItem.num = 0
							}
						}
					}
					that.calcPrice()
				})
			},

			calcPrice() {
				const that = this
				let t = 0
				let tn = 0
				that.cartList.forEach(item => {
					t += ((that.isVip ? item.vipPrice : item.price) * item.num)
					tn += item.num
				})
				that.total = t
				that.totalItems = tn
			},

			toCart() {
				uni.switchTab({
					url: '/pages/cart/cart'
				})
			},
			
			naviageToPage(url) {
				uni.navigateTo({
					url: url
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
		display: flex;
	}
	
	.search-box {
		left: 0;
		top: 10upx;
		z-index: 9999;
		width: 100%;
		padding-bottom: 5upx;
		border-bottom: 1upx;

		.ser-input {
			flex: 1;
			height: 56upx;
			line-height: 56upx;
			text-align: center;
			font-size: 28upx;
			color: $font-color-base;
			border-radius: 10upx;
			background-color: #ededed
		}
	}

	.left-aside {
		flex-shrink: 0;
		width: 150upx;
		height: 100%;
		background-color: #fff;
		padding-bottom: 150upx;
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

		&.active {
			color: $base-color;
			background: #f8f8f8;

			&:before {
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

	.s-item {
		display: inline;
		align-items: center;
		justify-content: center;
		width: 30%;
		padding: 4upx;
		margin: 8upx;
		text-align: center;
		background: #FFFFFF;
		font-size: 32upx;
		border-radius: 8upx;
		color: $font-color-base;
		position: relative;

		&.active {
			color: $base-color;
		}
	}

	.right-aside {
		flex: 1;
		overflow: hidden;
		height: 100%;
		margin-left: 5upx;
		margin-right: 5upx;
		white-space: nowrap;
	}
	
	.sec-cate-bg {
		background: #F8F8F8;
		display: flex;
		flex-wrap: wrap;
	}

	.g-list {
		display: flex;
		flex-wrap: wrap;
		width: 100%;
		background: #fff;
		padding-top: 5upx;
		padding-bottom: 5upx;

		&:after {
			content: '';
			flex: 99;
			height: 0;
		}
	}

	.t-item {
		flex-shrink: 0;
		display: flex;
		justify-content: center;
		align-items: center;
		text-align: center;
		flex-direction: column;
		width: 176upx;
		font-size: 26upx;
		color: #666;
		padding-bottom: 20upx;

		image {
			width: 140upx;
			height: 140upx;
		}
	}


	.g-item {
		display: flex;
		position: relative;
		width: 100%;

		.image-wrapper {
			width: 130upx;
			height: 160upx;
			flex-shrink: 0;
			position: relative;

			image {
				border-radius: 8upx;
			}
		}

		.item-right {
			display: flex;
			flex-direction: column;
			flex: 1;
			overflow: hidden;
			position: relative;
			padding-left: 10upx;

			.title {
				font-size: 28upx;
				height: 82upx;
				color: #666;
				width: 100%;
				overflow: hidden;
				display: -webkit-box;
				white-space: normal;
				-webkit-line-clamp: 2;
				text-overflow: ellipsis;
				display: -webkit-box;
				-webkit-box-orient: vertical;
			}

			.price-bar {
				display: flex;
				flex-flow: row nowrap;
				align-items: center;
				height: 56upx;

				.pick {
					flex: 2;
					display: flex;
					flex-flow: row nowrap;
					height: 56upx;
					padding-right: 20upx;

					.plus {
						align-items: center;
						text-align: center;
						background-color: $uni-color-primary;
						border-radius: 50%;
						color: #f8f8f8;
						flex: 1;
						height: 56upx;
						width: 56upx;
						line-height: 56upx;
						vertical-align: middle;
					}

					.sub {
						align-items: center;
						text-align: center;
						background-color: $uni-color-primary;
						border-radius: 50%;
						color: #f8f8f8;
						flex: 1;
						height: 56upx;
						width: 56upx;
						line-height: 56upx;
						vertical-align: middle;
					}

					.num {
						align-items: center;
						text-align: center;
						flex: 1;
						color: #666;
						height: 56upx;
						width: 56upx;
						line-height: 56upx;
						vertical-align: middle;
					}

				}

				.price {
					flex: 3;
					font-size: $font-base + 2upx;
					color: $uni-color-primary;
					height: 56upx;
					line-height: 56upx;
				}
			}



			.attr {
				font-size: $font-sm + 2upx;
				color: $font-color-light;
				height: 50upx;
				line-height: 50upx;
			}

			.price {
				height: 50upx;
				line-height: 50upx;
			}
		}
	}

	/* 底部栏 */
	.action-section {
		/* #ifdef H5 */
		margin-bottom: 100upx;
		/* #endif */
		position: fixed;
		left: 30upx;
		bottom: 30upx;
		z-index: 95;
		display: flex;
		align-items: center;
		width: 690upx;
		height: 100upx;
		padding: 0 30upx;
		background: rgba(255, 255, 255, .9);
		box-shadow: 0 0 20upx 0 rgba(0, 0, 0, .5);
		border-radius: 16upx;

		.clear-btn {
			position: absolute;
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
			border-radius: 0 50px 50px 0;
			opacity: 0;
			transition: .2s;

			&.show {
				opacity: 1;
				width: 120upx;
			}
		}

		.total-box {
			flex: 1;
			display: flex;
			flex-direction: column;
			text-align: right;
			padding-right: 40upx;

			.price {
				font-size: $font-lg;
				color: $font-color-dark;
			}

			.coupon {
				font-size: $font-sm;
				color: $font-color-light;

				text {
					color: $font-color-dark;
				}
			}
		}

		.confirm-btn {
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
</style>
