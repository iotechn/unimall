<template>
	<view class="content">
		
		<view class="row b-b">
			<text class="tit">联系人</text>
			<input class="input" type="text" v-model="addressData.consignee" placeholder="收货人姓名" placeholder-class="placeholder" />
		</view>
		<view class="row b-b">
			<text class="tit">手机号</text>
			<input class="input" type="number" v-model="addressData.phone" placeholder="收货人手机号码" placeholder-class="placeholder" />
		</view>
		
		<view class="row b-b">
			<text class="tit">城市</text>
			<input placeholder="请选择城市" disabled="true" :value="addressData.province + ' ' + addressData.city + ' ' + addressData.county" @click="lotusAddressData.visible = true" class="input">
				<lotus-address v-on:choseVal="choseValue" :lotusAddressData="lotusAddressData"></lotus-address>
			</input>
			<text class="yticon icon-shouhuodizhi"></text>
		</view>
		<view class="row b-b"> 
			<text class="tit">详细</text>
			<input class="input" type="text" v-model="addressData.address" placeholder="街道、楼号、门牌" placeholder-class="placeholder" />
		</view>
		
		<view class="row default-row">
			<text class="tit">设为默认</text>
			<switch :checked="addressData.defaultAddress" color="#fa436a" @change="switchChange" />
		</view>
		<button class="add-btn" @click="confirm">提交</button>
	</view>
</template>

<script>
	import lotusAddress from "@/components/Winglau14-lotusAddress/Winglau14-lotusAddress.vue"
	export default {
		data() {
			return {
				addressData: {
					consignee: '',
					phone: '',
					province: '',
					city: '',
					county: '',
					address: '',
					defaultAddress: 0,
					def: false
				},
				lotusAddressData:{
					visible:false,
					provinceName:'',
					cityName:'',
					townName:'',
				},
			}
		},
		onLoad(option){
			let title = '新增收货地址';
			if(option.type==='edit'){
				title = '编辑收货地址'
				this.addressData = JSON.parse(option.data)
			}
			this.manageType = option.type;
			uni.setNavigationBarTitle({
				title
			})
		},
		methods: {
			switchChange(e){
				this.addressData.defaultAddress = e.detail.value?1:0
				this.addressData.def = e.detail.value
			},
			
			choseValue(res){
            //res数据源包括已选省市区与省市区code
				this.lotusAddressData.visible = false;//visible为显示与关闭组件标识true显示false隐藏
				if(res.isChose){
					console.log(res)
					this.lotusAddressData.provinceName = res.provice;//省
					this.lotusAddressData.cityName = res.city;//市
					this.lotusAddressData.townName = res.town;//区
					
					//赋值到addressData
					this.addressData.province = res.provice
					this.addressData.city = res.city
					this.addressData.county = res.town

				}
			},
			
			//提交
			confirm(){
				const that = this
				let data = this.addressData;
				if(!data.consignee){
					this.$api.msg('请填写收货人姓名');
					return;
				}
				if(!/(^1[3|4|5|7|8][0-9]{9}$)/.test(data.phone)){
					that.$api.msg('请输入正确的手机号码');
					return
				}
				if (!data.province) {
					that.$api.msg('请选择省市区');
					return
				}
				if (!data.city) {
					that.$api.msg('请选择二级城市')
					return
				}
				if (!data.county) {
					that.$api.msg('请选择三级区或县')
					return
				}
				if(!data.address){
					that.$api.msg('请输入详细地址');
					return
				}
				
				//this.$api.prePage()获取上一页实例，可直接调用上页所有数据和方法，在App.vue定义
				
				//this.$api.msg(`地址${this.manageType=='edit' ? '修改': '添加'}成功`);
				if (that.manageType === 'edit') {
					that.$api.request('address', 'updateAddress', {
						...that.addressData,
						addressId : that.addressData.id
					}).then(res => {
						that.$api.prePage().refreshList(data, that.manageType);
						uni.navigateBack()
					})
				} else {
					that.$api.request('address', 'addAddress', that.addressData).then(res => {
						that.$api.prePage().refreshList(data, that.manageType);
						uni.navigateBack()
					})
				}
				
			},
		},
		components:{
			"lotus-address":lotusAddress
		},
	}
</script>

<style lang="scss">
	page{
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
		.input{
			flex: 1;
			font-size: 30upx;
			color: $font-color-dark;
		}
		.icon-shouhuodizhi{
			font-size: 36upx;
			color: $font-color-light;
		}
	}
	.default-row{
		margin-top: 16upx;
		.tit{
			flex: 1;
		}
		switch{
			transform: translateX(16upx) scale(.9);
		}
	}
	.add-btn{
		display: flex;
		align-items: center;
		justify-content: center;
		width: 690upx;
		height: 80upx;
		margin: 60upx auto;
		font-size: $font-lg;
		color: #fff;
		background-color: $base-color;
		border-radius: 10upx;
		box-shadow: 1px 2px 5px rgba(219, 63, 96, 0.4);
	}
</style>
