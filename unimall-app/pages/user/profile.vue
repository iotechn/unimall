<template>
	<view>
		<view class="container">
		<view class="list-cell b-b m-t" @click="inputShowModal('nickname')" hover-class="cell-hover" :hover-stay-time="50">
			<text class="cell-tit">修改昵称</text>
			<text class="cell-more yticon icon-you"></text>
		</view>
		<view class="list-cell b-b" @click="genderShowModal" hover-class="cell-hover" :hover-stay-time="50">
			<text class="cell-tit">修改性别</text>
			<text class="cell-more yticon icon-you"></text>
		</view>
		
		<neil-modal 
			:show="inputShow" 
			@close="cancel" 
			title="编辑" 
			@cancel="cancel" 
			@confirm="confirm">
			<input v-model="inputContent" style="margin:20upx" placeholder="请输入..." />
		</neil-modal>

		<neil-modal 
			:show="genderShow" 
			@close="cancel" 
			title="选择性别" 
			@cancel="cancel" 
			@confirm="confirmGender">
			<view>
                <radio-group style="text-align:center" @change="genderRadioChange">
                <label v-for="(item, index) in genders" :key="item.value">
                    <radio :value="item.value + ''" :checked="index === gender" style="margin:10upx"/>{{item.name}}
                </label>
            </radio-group>
            </view>
		</neil-modal>
	</view>
	</view>
</template>

<script>
	import neilModal from '@/components/neil-modal/neil-modal.vue';
	import {  
	    mapState,  
	    mapMutations  
	} from 'vuex';  
	export default {
		components: {
			neilModal
		},
		data() {
			return {
				inputShow: false,
				feild: undefined,
				inputContent: '',
				genderShow: false,
				gender: undefined,
				genders: [{name: '保密', value: 0 },{name: '男', value: 1}, {name: '女', value: 2}]
			};
		},
		computed:{
			...mapState(['userInfo']),
		},
		methods: {
			cancel() {
				this.inputShow = false
				this.genderShow = false
			},
			inputShowModal(feild) {
				this.feild = feild
				this.inputShow = true
				this.inputContent = ''
			},
			genderShowModal() {
				this.genderShow = true
				this.gender = this.userInfo.gender
			},
			confirm() {
				const that = this
				if (!that.inputContent) {
					that.$api.msg('输入不能为空')
					return
				}
				let obj = {}
				obj[that.feild] = that.inputContent
				that.$api.request('user', 'syncUserInfo', obj).then(res => {
					that.userInfo[that.feild] = that.inputContent
					that.inputContent = ''
					that.$store.commit('login', that.userInfo)
				})
			},
			genderRadioChange(e) {
				this.gender = parseInt(e.detail.value)
			},
			confirmGender() {
				const that = this
				if (that.gender === undefined) {
					that.$api.msg('请选择性别')
					return
				}
				let obj = {
					gender: that.gender
				}
				that.$api.request('user', 'syncUserInfo', obj).then(res => {
					that.userInfo.gender = that.gender
				})
			}
			
		}
	}
</script>

<style lang="scss">
	page{
		background: $page-color-base;
	}
	
	page{
		background: $page-color-base;
	}
	.list-cell{
		display:flex;
		align-items:baseline;
		padding: 20upx $page-row-spacing;
		line-height:60upx;
		position:relative;
		background: #fff;
		justify-content: center;
		&.log-out-btn{
			margin-top: 40upx;
			.cell-tit{
				color: $uni-color-primary;
				text-align: center;
				margin-right: 0;
			}
		}
		&.cell-hover{
			background:#fafafa;
		}
		&.b-b:after{
			left: 30upx;
		}
		&.m-t{
			margin-top: 16upx; 
		}
		.cell-more{
			align-self: baseline;
			font-size:$font-lg;
			color:$font-color-light;
			margin-left:10upx;
		}
		.cell-tit{
			flex: 1;
			font-size: $font-base + 2upx;
			color: $font-color-dark;
			margin-right:10upx;
		}
		.cell-tip{
			font-size: $font-base;
			color: $font-color-light;
		}
		switch{
			transform: translateX(16upx) scale(.84);
		}
	}


</style>
