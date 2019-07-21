<template>
	<view class="content">
		<ali :datas="data.traces" ></ali>
	</view>
</template>

<script>
import ali from '@/components/WuLiu-step.vue';
export default {
	components: {
		ali
	},
	data() {
		return {
			data: {
				traces: []
			}
		};
	},
	onLoad(option) {
		const that = this
		uni.showLoading({
			title: '正在查询'
		})
		that.$api.request('order', 'queryShip', {
			orderNo : option.orderno
		}, failres => {
			uni.hideLoading()
			uni.showModal({
				title: '提示',
				content: failres.errmsg,
				confirmText: '确定',
				success: () => {
					uni.navigateBack()
				}
			})
			
		}).then(res => {
			uni.hideLoading()
			that.data = res.data
			that.data.traces = that.data.traces.reverse()
		})
	},
	methods: {
		
	}
};
</script>

<style>
.content {
}
</style>
