<template>
	<view class="content b-t">
		<view class="list b-b" v-for="(item, index) in addressList" :key="index" @click="checkAddress(item)" @longpress="deleteShow(item)">
			<view class="wrapper">
				<view class="u-box">
					<text class="name">{{item.consignee}}</text>
					<text class="mobile">{{item.phone}}</text>
				</view>
				<view class="address-box">
					<text v-if="item.defaultAddress" class="tag">默认</text>
					<text class="address">{{item.province}} {{item.city}} {{item.county}} {{item.address}}</text>
				</view>
			</view>
			<text class="yticon icon-bianji" @click.stop="addAddress('edit', item)"></text>
		</view>
		<button class="add-btn" @click="addAddress('add')">新增地址</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				source: 0,
				addressList: [

				]
			}
		},
		onLoad(option) {
			this.refreshList()
			this.source = option.source;
		},
		methods: {
			//选择地址
			checkAddress(item) {
				if (this.source == 1) {
					//this.$api.prePage()获取上一页实例，在App.vue定义
					this.$api.prePage().addressData = item
					this.$api.prePage().calcFreightPrice()
					uni.navigateBack()
				}
			},
			addAddress(type, item) {
				uni.navigateTo({
					url: `/pages/address/create?type=${type}&data=${JSON.stringify(item)}`
				})
			},
			//添加或修改成功之后回调
			refreshList(data, type) {
				const that = this
				that.$api.request('address', 'getAllAddress').then(res => {
					that.addressList = res.data
				})
			},
			deleteShow(item) {
				const that = this
				uni.showModal({
					title: '删除提示',
					content: '您确定要删除该地址吗？',
					showCancel: true,
					confirmText: '删除',
					success: (e) => {
						if (e.confirm) {
							that.$api.request('address', 'deleteAddress', {
								addressId: item.id
							}).then(res => {
								that.refreshList()
							})
						}
					},
					fail: () => {}
				})
			}
		}
	}
</script>

<style lang='scss'>
	page {
		padding-bottom: 120upx;
	}

	.content {
		position: relative;
	}

	.list {
		display: flex;
		align-items: center;
		padding: 20upx 30upx;
		;
		background: #fff;
		position: relative;
	}

	.wrapper {
		display: flex;
		flex-direction: column;
		flex: 1;
	}

	.address-box {
		display: flex;
		align-items: center;

		.tag {
			font-size: 24upx;
			color: $base-color;
			margin-right: 10upx;
			background: #fffafb;
			border: 1px solid #ffb4c7;
			border-radius: 4upx;
			padding: 4upx 10upx;
			line-height: 1;
		}

		.address {
			font-size: 28upx;
			color: $font-color-light;
		}
	}

	.u-box {
		font-size: 30upx;
		color: $font-color-dark;
		margin-top: 16upx;

		.name {
			margin-right: 30upx;
		}
	}

	.icon-bianji {
		display: flex;
		align-items: center;
		height: 80upx;
		font-size: 40upx;
		color: $font-color-light;
		padding-left: 30upx;
	}

	.add-btn {
		position: fixed;
		left: 30upx;
		right: 30upx;
		bottom: 16upx;
		z-index: 95;
		display: flex;
		align-items: center;
		justify-content: center;
		width: 690upx;
		height: 80upx;
		font-size: 32upx;
		color: #fff;
		background-color: $base-color;
		border-radius: 10upx;
		box-shadow: 1px 2px 5px rgba(219, 63, 96, 0.4);
	}
</style>
