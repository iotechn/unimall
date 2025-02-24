<template>
	<div class="login-container">
		<div style="position: absolute;width: 100%;height: 100vh">
			<div v-for="item in snow" :key="item.id" class="snow"></div>
		</div>
		<el-form ref="loginFromRef" class="login-form" :model="loginForm" :rules="loginRules">
			<div class="title-container">
				<h3 class="title">Vue3后台系统</h3>
			</div>

			<el-form-item prop="username" style="display: flex;flex-direction: row">
				<span class="icon-container">
					<UserFilled style="width: 1em;height: 1em" />
				</span>
				<el-input
					v-model="loginForm.username" placeholder="请输入用户名" name="username" type="text"
					@keyup.enter.native="handleLogin"
				/>
			</el-form-item>

			<el-form-item prop="password">
				<span class="icon-container">
					<Lock style="width: 1em;height: 1em" />
				</span>
				<el-input
					v-model="loginForm.password" placeholder="请输入密码" name="password" :type="passwordType"
					@keyup.enter.native="handleLogin"
				/>
				<span class="show-pwd">
					<svg-icon :icon="passwordType === 'password' ? 'eye' : 'eye-open'" @click="onChangePwdType" />

				</span>
			</el-form-item>

			<el-form-item class="code-box" prop="captcha_code">
				<span class="icon-container">
					<Tickets style="width: 1em;height: 1em" />
				</span>
				<el-input
					v-model="loginForm.captcha_code" placeholder="图形验证码" name="captcha_code" class="code-input"
					maxlength="4" @keyup.enter.native="handleLogin"
				>
				</el-input>
				<div class="code-img" @click="getCodeImg">{{codeNet}}</div>
			</el-form-item>

			<el-button
				type="primary" style=" margin-bottom: 30px;width: 100%;" :loading="loading"
				size="large" @click.native.prevent="handleLogin"
			>
				<span>{{!loading ? '登 录' : '登 录 中...'}}</span>
			</el-button>
		</el-form>
		<!--  底部  -->
		<div class="el-login-footer">
			<span>项目搭建博客地址：https://juejin.cn/user/1310273591836957</span>
		</div>
	</div>
</template>

<style lang="scss" scoped>
$bg: #5268bc;
$dark_gray: #889aa4;
$light_gray: #eee;
$cursor: #000;
$txt_color: #333;
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  //background-image: url("../../assets/login-background.jpeg");
  //background-size: cover;
  background: radial-gradient(ellipse at bottom, #1b2735 0%, #090a0f 100%);
  .login-form {
    overflow: hidden;
    position: relative;
    margin: 0 auto;
    margin-top: 3%;
    padding: 35px;
    width: 420px;
    max-width: 100%;
    background: white;
    ::v-deep .el-form-item {
      border: 1px solid rgba(255, 255, 255, 0.1);
      border-radius: 5px;
      background: rgba(0, 0, 0, 0.1);
      color: #454545;
    }
    ::v-deep .el-input {
      display: inline-block;
      width: 85%;
      height: 47px;
      input {
        padding: 12px 5px 12px 15px;
        height: 47px;
        color: $txt_color;
        caret-color: $cursor;
      }
      .el-input__wrapper {
        width: 90%;
        height: 47px;
        background: none;
        box-shadow: none;
      }

    }
  }
  .icon-container {
    display: inline-block;
    padding: 6px 5px 6px 15px;
    vertical-align: middle;
    color: $dark_gray;
  }
  .title-container {
    position: relative;
    .title {
      margin: 0 auto 40px;
      text-align: center;
      font-weight: bold;
      font-size: 26px;
      color: $txt_color;
    }
  }
  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    cursor: pointer;
    user-select: none;
    font-size: 16px;
    color: $dark_gray;
  }
  .code-box {
    position: relative;
    .code-img {
      position: absolute;
      right: 0;
      top: 6px;
      width: 60px;
      background: transparent;
      font-size: 22px;

    }
  }
  .el-login-footer {
    position: fixed;
    bottom: 0;
    width: 100%;
    height: 40px;
    line-height: 40px;
    text-align: center;
    font-family: Arial;
    font-size: 12px;
    color: #fff;
    letter-spacing: 1px;
  }
}
@function random_range($min, $max) {
  $rand: random();
  $random_range: $min + floor($rand * (($max - $min) + 1));
  @return $random_range;
}
.snow {
  $total: 200;
  position: absolute;
  border-radius: 50%;
  width: 10px;
  height: 10px;
  background: white;
  @for $i from 1 through $total {
    $random-x: random(1000000) * 0.0001vw;
    $random-offset: random_range(-100000, 100000) * 0.0001vw;
    $random-x-end: $random-x + $random-offset;
    $random-x-end-yoyo: $random-x + ($random-offset / 2);
    $random-yoyo-time: random_range(30000, 80000) / 100000;
    $random-yoyo-y: $random-yoyo-time * 100vh;
    $random-scale: random(10000) * 0.0001;
    $fall-duration: random_range(10, 30) * 1s;
    $fall-delay: random(30) * -1s;

    &:nth-child(#{$i}) {
      opacity: random(10000) * 0.0001;
      transform: translate($random-x, -10px) scale($random-scale);
      animation: fall-#{$i} $fall-duration $fall-delay linear infinite;
    }
    @keyframes fall-#{$i} {
      #{percentage($random-yoyo-time)} {
        transform: translate($random-x-end, $random-yoyo-y) scale($random-scale);
      }
      100% {
        transform: translate($random-x-end-yoyo, 100vh) scale($random-scale);
      }
    }
  }
}
</style>

<script setup>
import { UserFilled, Lock, Tickets } from "@element-plus/icons";

import { ref, onMounted } from "vue";
import { validatePassword, validateCode } from "./rules";
import { getCode } from "@/api/api";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";

// 雪花效果
const snow = ref([]);
for (let i = 0; i < 1000; i++) {
	snow.value.push(i);
}

const codeNet = ref("");

onMounted(() => {
	getCodeImg();
});

// 数据源
const loginForm = ref({
	username: "admin",
	password: "123456",
	captcha_code: "",
	code_key: ""
});
// 验证规则
const loginRules = ref({
	username: [
		{
			required: true,
			trigger: "blur",
			message: "请输入用户名"
		}

	],
	password: [
		{
			required: true,
			trigger: "blur",
			validator: validatePassword()
		}

	],
	captcha_code: [
		{
			required: true,
			trigger: "blur",
			validator: validateCode()
		}

	]
});

// 处理密码框文本显示状态
const passwordType = ref("password");
const onChangePwdType = () => {
	if (passwordType.value === "password") {
		passwordType.value = "text";
	} else {
		passwordType.value = "password";
	}
};

// 登录动作处理
const loading = ref(false);
const loginFromRef = ref(null);
const store = useStore();
const router = useRouter();

/**
 * 登录
 */
const handleLogin = () => {
	loginFromRef.value.validate(valid => {
		if (!valid) return;
		console.log(loginForm.value);
		if (loginForm.value.captcha_code !== codeNet.value) {
			ElMessage.error("验证码错误！");
			return;
		}
		loading.value = true;
		store.dispatch("user/login", loginForm.value)
			.then(() => {
				setTimeout(() => {
					loading.value = false;
					// TODO: 登录后操作
					router.push("/");
				}, 500);
			})
			.catch(() => {
				getCodeImg();
				loading.value = false;
			});
	});
};
/**
 * 获取图形验证码
 */
const getCodeImg = () => {
	getCode({})
		.then(data => {
			const obj = data.obj;

			loginForm.value.code_key = obj.code_key;
			codeNet.value = obj.code;
		})
		.catch(() => {
		});
};

</script>