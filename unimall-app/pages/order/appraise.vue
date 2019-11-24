<template>
	<view class="page">
		<view v-for="(item, index) in orderDetail.skuList" :key="index">
			<view class="goods-box-single">
				<image class="goods-img" :src="item.img + '?x-oss-process=style/200px'" mode="aspectFill"></image>
				<view class="right">
					<text class="title clamp">{{item.spuTitle}}</text>
					<text class="attr-box">{{item.title}} x {{item.num}}</text>
					<text class="price">{{item.price / 100.0}}</text>
				</view>
			</view>
			<view class='appraise-title appraise-star-view'>
				<text>宝贝评分</text>
				<view class="appraise-star-view">
					<text class="appraise-star" v-for="(value,key) in stars" :key="key" :class="key < orderDetail.skuList[index].score ? 'active' : ''"
					 @tap="chooseStar(value,index)"></text>
				</view>
			</view>
			<view class="appraise-body">
				<textarea placeholder="请输入评价(可空)..." v-model="item.content" class="appraise-textare" />
				</view>
			<view class="appraise-body appraise-uploader">
				<view class="uni-uploader">
					<view class="uni-uploader-head">
						<view class="uni-uploader-title" style="color: #6f6f74;">晒一晒</view>
						<view class="uni-uploader-info">{{item.imgs.length}}/8</view>
					</view>
					<view class="uni-uploader-body">
						<view class="uni-uploader__files">
							<block v-for="(image,imgIndex) in item.imgs" :key="imgIndex">
								<view class="uni-uploader__file" style="position: relative;">
									<image class="uni-uploader__img" :src="image + '?x-oss-process=style/200px'" @tap="previewImage"></image>
									<view class="close-view" @click="close(item, index, imgIndex)">x</view>
								</view>
							</block>
							<view class="uni-uploader__input-box" v-show="imageList.length < 8">
								<view class="uni-uploader__input" @tap="chooseImg(item,index)"></view>
							</view>
						</view>
					</view>
				</view>
			</view>
		</view>
		
        
        <button type="primary" class="appraise-submit" @tap="send">提交</button>
    </view>
</template>

<script>
    export default {
        data() {
            return {
                stars: [1, 2, 3, 4, 5],
                imageList: [],
                sendDate: {
                    score: 0,
                    content: "",
                    contact: ""
                },
				orderDetail: {
					skuList: []
				},
				appraiseRequest: {
					appraiseDTOList: []
				}
            }
        },
        onLoad(option) {
            const that = this
			uni.showLoading({
            	title: '正在加载'
            })
			
			that.$api.request('order', 'getOrderDetail', {
				orderId : option.orderid
			}, failres => {
				uni.hideLoading()
				that.$api.msg(failres.errmsg)
			}).then(res => {
				uni.hideLoading()
				that.orderDetail = res.data
				that.orderDetail.skuList.forEach(item => {
					item.score = 0
					item.content = ''
					item.imgs = []
				})
			})
			
        },
        methods: {
            close(item, index, imgIndex){
                item.imgs.splice(imgIndex,1);
				this.orderDetail.skuList.splice(index, item)
            },

            chooseImg(item,index) { //选择图片
				const that = this
                that.$api.uploadImg((res => {
					item.imgs.push(res)
					that.orderDetail.skuList.splice(index, that.orderDetail.skuList[index])
				}))
            },
            chooseStar(e,index) { //点击评星
				const that = this
                that.orderDetail.skuList[index].score = e
				that.orderDetail.skuList.splice(index, that.orderDetail.skuList[index])
            },
            previewImage() { //预览图片
                uni.previewImage({
                    urls: this.imageList
                });
            },
            send() { //发送反馈
                const that = this
				let requestItems = []
				that.orderDetail.skuList.forEach(item => {
					if (item.score <= 0) {
						that.$api.msg('请为所有宝贝点上星星')
						return
					}

					requestItems.push({
						skuId: item.skuId,
						spuId: item.spuId,
						score: item.score,
						content: item.content,
						imgUrl: item.imgs.length > 0 ? item.imgs.join(',') : ''
					})
				
				})
				
				that.appraiseRequest.orderId = that.orderDetail.id
				that.appraiseRequest.appraiseDTOList = requestItems
				
				that.$api.request('appraise', 'addAppraise', {
					appraiseRequestDTO : JSON.stringify(that.appraiseRequest)
				}).then(res => {
					that.$api.msg('评价成功！')
					that.$api.prePage().loadData('refresh')
					uni.navigateBack()
				})
            }
        }
    }
</script>

<style lang="scss">
    page {
        background-color: #FFFFFF;
    }
	@font-face {
    	font-family: uniicons;
    	font-weight: normal;
    	font-style: normal;
    	src: url('https://img-cdn-qiniu.dcloud.net.cn/fonts/uni.ttf') format('truetype');
    }
    view{
        font-size: 28upx;
    }
    .input-view {
        font-size: 28upx;
    }
    .close-view{
        text-align: center;line-height:14px;height: 16px;width: 16px;border-radius: 50%;background: #FF5053;color: #FFFFFF;position: absolute;top: -6px;right: -4px;font-size: 12px;
    }
    /* 上传 */
    .uni-uploader {
    	flex: 1;
    	flex-direction: column;
    }
    .uni-uploader-head {
    	display: flex;
    	flex-direction: row;
    	justify-content: space-between;
    }
    .uni-uploader-info {
    	color: #B2B2B2;
    }
    .uni-uploader-body {
    	margin-top: 16upx;
    }
    .uni-uploader__files {
    	display: flex;
    	flex-direction: row;
    	flex-wrap: wrap;
    }
    .uni-uploader__file {
    	margin: 10upx;
    	width: 210upx;
    	height: 210upx;
    }
    .uni-uploader__img {
    	display: block;
    	width: 210upx;
    	height: 210upx;
    }
    .uni-uploader__input-box {
    	position: relative;
    	margin:10upx;
    	width: 208upx;
    	height: 208upx;
    	border: 2upx solid #D9D9D9;
    }
    .uni-uploader__input-box:before,
    .uni-uploader__input-box:after {
    	content: " ";
    	position: absolute;
    	top: 50%;
    	left: 50%;
    	-webkit-transform: translate(-50%, -50%);
    	transform: translate(-50%, -50%);
    	background-color: #D9D9D9;
    }
    .uni-uploader__input-box:before {
    	width: 4upx;
    	height: 79upx;
    }
    .uni-uploader__input-box:after {
    	width: 79upx;
    	height: 4upx;
    }
    .uni-uploader__input-box:active {
    	border-color: #999999;
    }
    .uni-uploader__input-box:active:before,
    .uni-uploader__input-box:active:after {
    	background-color: #999999;
    }
    .uni-uploader__input {
    	position: absolute;
    	z-index: 1;
    	top: 0;
    	left: 0;
    	width: 100%;
    	height: 100%;
    	opacity: 0;
    }
    
    /*问题反馈*/
    .appraise-title {
    	display: flex;
    	flex-direction: row;
    	justify-content: space-between;
    	align-items: center;
    	padding: 20upx;
    	color: #6f6f74;
		background-color: #FFFFFF;
    	font-size: 28upx;
    }
    .appraise-star-view.appraise-title {
    	justify-content: flex-start;
    	margin: 0;
    }
    .appraise-quick {
    	position: relative;
    	padding-right: 40upx;
    }
    .appraise-quick:after {
    	font-family: uniicons;
    	font-size: 40upx;
    	content: '\e581';
    	position: absolute;
    	right: 0;
    	top: 50%;
    	color: #bbb;
    	-webkit-transform: translateY(-50%);
    	transform: translateY(-50%);
    }
    .appraise-body {
    	background: #fff;
    }
    .appraise-textare {
    	height: 200upx;
    	font-size: 28upx;
    	line-height: 34upx;
    	width: 100%;
    	box-sizing: border-box;
    	padding: 20upx 30upx 0;
    }
    .appraise-input {
    	font-size: 34upx;
    	height: 50upx;
    	min-height: 50upx;
    	padding: 15upx 20upx;
    	line-height: 50upx;
    }
    .appraise-uploader {
    	padding: 22upx 20upx;
    }
    .appraise-star {
    	font-family: uniicons;
    	font-size: 40upx;
    	margin-left: 6upx;
    }
    .appraise-star-view {
    	margin-left: 20upx;
    }
    .appraise-star:after {
    	content: '\e408';
    }
    .appraise-star.active {
    	color: #FFB400;
    }
    .appraise-star.active:after {
    	content: '\e438';
    }
    .appraise-submit {
    	background: #007AFF;
    	color: #FFFFFF;
    	margin: 20upx;
    }
	/* 单条商品 */
	.goods-box-single {
		display: flex;
		padding: 20upx;

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
</style>
