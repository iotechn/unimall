<template>
	<view class="container">
		<view class="left-bottom-sign"></view>
		<view class="back-btn yticon icon-zuojiantou-up" @click="navBack"></view>
		<view class="right-top-sign"></view>
		<!-- 设置白色背景防止软键盘把下部绝对定位元素顶上来盖住输入框等 -->
		<view class="wrapper">
			<view class="left-top-sign">REGISTER</view>
			<view class="welcome">
				欢迎加入！
			</view>
			<view class="input-content">
				<view class="input-item">
					<text class="tit">手机号码</text>
					<input type="number" :value="phone" placeholder="请输入手机号码" maxlength="11" data-key="phone" @input="inputChange" />
				</view>
				<view class="input-item">
					<text class="tit">验证码</text>
					<view class="verify-code">
						<input type="mobile" value="" placeholder="6位验证码" maxlength="6"
						 data-key="verifyCode" @input="inputChange" style="width: 60%;" />
						<button style="width: 40%;" :disabled="sendDisabled" @click="doGetVerify">{{sendText}}</button>
					</view>
				</view>
				<view class="input-item">
					<text class="tit">密码</text>
						<input type="mobile" value="" placeholder="8-18位数字、字母组合" placeholder-class="input-empty" maxlength="20"
						  data-key="password" @input="inputChange" @confirm="doRegister" />
				</view>
			</view>
			<button class="confirm-btn" @click="doRegister" :disabled="registering">注册</button>
			
		</view>
		<view class="register-section">
			已经有账号？
			<text @click="toLogin">马上登录</text>
		</view>
	</view>
</template>

<script>
	import {
		mapMutations
	} from 'vuex';

	export default {
		data() {
			return {
				phone: '',
				password: '',
				registering: false,
				sendText: '获取验证码',
				sendDisabled: false
			}
		},
		onLoad() {

		},
		methods: {
			...mapMutations(['login']),
			inputChange(e) {
				const key = e.currentTarget.dataset.key;
				this[key] = e.detail.value;
			},
			navBack() {
				uni.navigateBack();
			},
			toLogin() {
				uni.redirectTo({
					url: "/pages/public/login"
				})
			},
			doGetVerify() {
				const that = this
				if (!that.phone || that.phone.length != 11) {
					uni.showToast({
						title:'请输入正确手机号！',
						icon:'none'
					})
					return
				}
				that.$api.request('user', 'sendVerifyCode', {
					phone: that.phone,
				}).then(res => {
					debugger
					that.sendDisabled = true
					let sec = 60
					let interval = setInterval(() => {
						sec--;
						that.sendText = sec + 's后重发'
						if (sec <= 0) {
							that.sendDisabled = false
							that.sendText = "获取验证码"
							clearInterval(interval)
						}
					}, 1000)
				})
				
			},
			async doRegister() {
				const that = this
				that.registering = true;
				that.$api.request('user','register', {
					phone: that.phone,
					password: that.password,
					verifyCode: that.verifyCode
				}, (failres => {
					uni.showToast({
						title:failres.errmsg
					})
					that.registering = false
				})).then(res => {
					//注册成功
					that.registering = false
					uni.redirectTo({
						url: './login'
					})
				})
			}
		},

	}
</script>

<style lang='scss'>
	page {
		background: #fff;
	}

	.container {
		padding-top: 115px;
		position: relative;
		width: 100vw;
		height: 100vh;
		overflow: hidden;
		background: #fff;
	}

	.wrapper {
		position: relative;
		z-index: 90;
		background: #fff;
		padding-bottom: 40upx;
	}

	.back-btn {
		position: absolute;
		left: 40upx;
		z-index: 9999;
		padding-top: var(--status-bar-height);
		top: 40upx;
		font-size: 40upx;
		color: $font-color-dark;
	}

	.left-top-sign {
		font-size: 120upx;
		color: $page-color-base;
		position: relative;
		left: -16upx;
	}

	.right-top-sign {
		position: absolute;
		top: 80upx;
		right: -30upx;
		z-index: 95;

		&:before,
		&:after {
			display: block;
			content: "";
			width: 400upx;
			height: 80upx;
			background: #b4f3e2;
		}

		&:before {
			transform: rotate(50deg);
			border-radius: 0 50px 0 0;
		}

		&:after {
			position: absolute;
			right: -198upx;
			top: 0;
			transform: rotate(-50deg);
			border-radius: 50px 0 0 0;
			/* background: pink; */
		}
	}

	.left-bottom-sign {
		position: absolute;
		left: -270upx;
		bottom: -320upx;
		border: 100upx solid #d0d1fd;
		border-radius: 50%;
		padding: 180upx;
	}

	.welcome {
		position: relative;
		left: 50upx;
		top: -90upx;
		font-size: 46upx;
		color: #555;
		text-shadow: 1px 0px 1px rgba(0, 0, 0, .3);
	}

	.input-content {
		padding: 0 60upx;
	}

	.input-item {
		display: flex;
		flex-direction: column;
		align-items: flex-start;
		justify-content: center;
		padding: 0 30upx;
		background: $page-color-light;
		height: 140upx;
		border-radius: 4px;
		margin-bottom: 50upx;

		&:last-child {
			margin-bottom: 0;
		}

		.tit {
			height: 50upx;
			line-height: 56upx;
			font-size: $font-sm+2upx;
			color: $font-color-base;
		}

		input {
			height: 60upx;
			font-size: $font-base + 2upx;
			color: $font-color-dark;
			width: 100%;
		}
		
		.verify-code {
			flex-direction: row;
			display: flex;
			justify-content:end; 
			flex-wrap: wrap
		}
	}

	.confirm-btn {
		width: 630upx;
		height: 76upx;
		line-height: 76upx;
		border-radius: 50px;
		margin-top: 70upx;
		background: $uni-color-primary;
		color: #fff;
		font-size: $font-lg;

		&:after {
			border-radius: 100px;
		}
	}

	.forget-section {
		font-size: $font-sm+2upx;
		color: $font-color-spec;
		text-align: center;
		margin-top: 40upx;
	}

	.register-section {
		position: absolute;
		left: 0;
		bottom: 50upx;
		width: 100%;
		font-size: $font-sm+2upx;
		color: $font-color-base;
		text-align: center;

		text {
			color: $font-color-spec;
			margin-left: 10upx;
		}
	}
</style>
