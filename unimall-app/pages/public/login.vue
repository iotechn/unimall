<template>
	<view class="container">
		<view class="left-bottom-sign"></view>
		<view class="back-btn yticon icon-zuojiantou-up" @click="navBack"></view>
		<view class="right-top-sign"></view>
		<!-- 设置白色背景防止软键盘把下部绝对定位元素顶上来盖住输入框等 -->
		<view class="wrapper">
			<view class="left-top-sign">LOGIN</view>
			<view class="welcome">
				欢迎回来！
			</view>
			<view v-if="loginType === 'phone'" class="input-content">
				<view class="input-item">
					<text class="tit">手机号码</text>
					<input type="number" :value="phone" placeholder="请输入手机号码" maxlength="11" data-key="phone" @input="inputChange" />
				</view>
				<view class="input-item">
					<text class="tit">密码</text>
					<input type="mobile" value="" placeholder="8-18位不含特殊字符的数字、字母组合" placeholder-class="input-empty" maxlength="20"
					 password data-key="password" @input="inputChange" @confirm="toLogin" />
				</view>
			</view>
			<button v-if="!loginType" class="confirm-btn" @click="chooseLoginType('wechat')" :disabled="logining">微信快速登录（推荐）</button>
			<button v-if="!loginType" class="confirm-btn" @click="chooseLoginType('phone')" :disabled="logining">手机注册登录</button>
			<!-- #ifdef MP-WEIXIN -->
			<button v-if="loginType === 'wechat'" class="confirm-btn" open-type="getUserInfo" @getuserinfo="miniWechatLogin"
			 :disabled="logining">微信授权登录</button>
			<!-- #endif -->
			<!-- #ifdef APP-PLUS -->
			<button v-if="loginType === 'wechat'" class="confirm-btn" @click="wechatLogin" :disabled="logining">微信授权登录</button>
			<!-- #endif -->
			<!-- #ifdef H5 -->
			<button v-if="loginType === 'wechat'" class="confirm-btn" @click="wechatH5Login" :disabled="logining">微信授权登录</button>
			<!-- #endif -->
			<button v-if="loginType === 'phone'" class="confirm-btn" @click="toLogin" :disabled="logining">登录</button>
			<view v-if="loginType === 'phone'" class="forget-section">
				忘记密码?
			</view>
		</view>
		<view v-if="loginType === 'phone'" class="register-section">
			还没有账号?
			<text @click="toRegist">马上注册</text>
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
				loginType: '',
				phone: '',
				password: '',
				logining: false
			}
		},
		onShow() {
			this.$api.logout()
		},
		onLoad(options) {
			
		},
		methods: {
			...mapMutations(['login']),
			inputChange(e) {
				const key = e.currentTarget.dataset.key;
				this[key] = e.detail.value;
			},
			chooseLoginType(type) {
				this.loginType = type
			},
			navBack() {
				uni.navigateBack();
			},
			toRegist() {
				uni.redirectTo({
					url: '/pages/public/register'
				})
			},
			async toLogin() {
				const that = this
				if (that.phone.length !== 11) {
					that.$api.msg('请输入11位中国手机号')
				} else if (that.password.length < 8) {
					that.$api.msg('密码至少8位')
				} else {
					that.logining = true;
					//#ifdef MP-WEIXIN
					//若是小程序平台，则获取到openId。整个过程是静默完成的
					uni.login({
						provider: 'weixin',
						success: (wxres => {
							that.$api.request('user', 'login', {
								phone: that.phone,
								password: that.password,
								loginType: 1,
								raw: JSON.stringify(wxres)
							}, failres => {
								that.logining = false
								uni.showToast({
									title: failres.errmsg,
									icon: "none"
								});
							}).then(res => {
								that.logining = false
								that.$store.commit('login', res.data)
								uni.setStorageSync('userInfo', res.data)
								if (that.$api.prePage().lodaData) {
									that.$api.prePage().loadData()
								}
								uni.navigateBack()
							})
						})
					})
					//#endif
					//#ifdef APP-PLUS || H5
					//若是App登录，则不需要保存OpenId。可直接登录
					that.$api.request('user', 'login', {
						phone: that.phone,
						password: that.password,
					}, failres => {
						that.logining = false
						uni.showToast({
							title: failres.errmsg,
							icon: "none"
						});
					}).then(res => {
						that.logining = false
						that.$store.commit('login', res.data)
						uni.setStorageSync('userInfo', res.data)
						if (that.$api.prePage().lodaData) {
							that.$api.prePage().loadData()
						}
						uni.navigateBack()
					})
					//#endif
				}
			},
			miniWechatLogin(e) {
				const that = this
				that.logining = true
				let loginType = 1
				let userInfo = e.detail.userInfo
				uni.login({
					provider: 'weixin',
					success: (wxres => {
						that.logining = false
						that.$api.request('user', 'thirdPartLogin', {
							loginType: loginType,
							raw: JSON.stringify(wxres)
						}, failres => {
							that.$api.msg(failres.errmsg)
							uni.hideLoading()
						}).then(res => {
							that.$api.setUserInfo(res.data)
							if (!res.data.nickname) {
								that.$api.request('user', 'syncUserInfo', userInfo).then(syncRes => {
									//同步过后
									res.data.nickname = userInfo.nickName
									res.data.avatarUrl = userInfo.avatarUrl
									res.data.gender = userInfo.gender
									uni.setStorageSync('userInfo', res.data)
									that.$store.commit('login', res.data)
									that.$api.setUserInfo(res.data)
								
									if (that.$api.prePage().lodaData) {
										that.$api.prePage().loadData()
									}
									uni.hideLoading()
									uni.navigateBack()
								})
							} else {
								that.$store.commit('login', res.data)
								that.$api.setUserInfo(res.data)
																
								if (that.$api.prePage().lodaData) {
									that.$api.prePage().loadData()
								}
								uni.hideLoading()
								uni.navigateBack()
							}
						})
					})
				})



			},
			wechatLogin() {
				const that = this
				that.logining = true
				let loginType = 2
				uni.showLoading({
					title: '正在同步消息'
				})
				uni.login({
					provider: 'weixin',
					success: (wxres => {
						that.$api.request('user', 'thirdPartLogin', {
							loginType: loginType,
							raw: JSON.stringify(wxres)
						}, failres => {
							that.$api.msg(failres.errmsg)
							uni.hideLoading()
						}).then(res => {
							that.logining = false
							uni.getUserInfo({
								lang: 'zh_CN',
								success: (e) => {
									uni.setStorageSync('userInfo', res.data)
									that.$store.commit('login', res.data)
									e.userInfo.nickname = e.userInfo.nickName
									that.$api.request('user', 'syncUserInfo', e.userInfo).then(syncRes => {
										//同步过后
										res.data.nickname = e.userInfo.nickName
										res.data.avatarUrl = e.userInfo.avatarUrl
										res.data.gender = e.userInfo.gender
										uni.setStorageSync('userInfo', res.data)
										that.$store.commit('login', res.data)
									})
								},
								complete: (e) => {
									if (that.$api.prePage().lodaData) {
										that.$api.prePage().loadData()
									}
									uni.hideLoading()
									uni.navigateBack()
								}
							})
						})
					})
				})
			},
			wechatH5Login() {
				const that = this
				let href = window.location.origin
				let page = that.$api.prePage()
				let prePath = '/pages/index/index'
				if (page) {
					prePath = page.__page__.path
				}
				window.location = 'https://open.weixin.qq.com/connect/oauth2/authorize?' 
				+ 'appid=' + that.$api.defConfig().h5Appid + '&redirect_uri=' + escape(href) + '&response_type=code&scope=snsapi_userinfo&state=' + escape(prePath) + '#wechat_redirect'
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
		height: 120upx;
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
