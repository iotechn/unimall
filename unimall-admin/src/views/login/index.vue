<template>
  <div class="login-container" @keyup.enter.native="handleLogin">
    <div class="login-weaper animated bounceInDown">
      <div class="login-left">
        <div class="login-time">欢迎使用</div>
        <img class="img" src="@/assets/avatar.png" alt="" />
        <p class="title">Unimall 开源商城后台登录</p>
      </div>
      <div class="login-border">
        <div class="login-main">
          <h4 class="login-title">登录 Unimall</h4>
          <el-form
            ref="loginFormRef"
            :rules="loginRules"
            :model="loginForm"
            class="login-form"
            status-icon
            label-width="0"
          >
            <el-form-item prop="username">
              <el-input
                v-model="loginForm.username"
                placeholder="请输入用户名"
                size="small"
                auto-complete="off"
                @keyup.enter.native="handleLogin"
              >
                <template v-slot:prefix>
                  <i class="icon-yonghu" />
                </template>
              </el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input
                :type="passwordType"
                v-model="loginForm.password"
                placeholder="请输入密码"
                size="small"
                auto-complete="off"
                @keyup.enter.native="handleLogin"
              >
                <template v-slot:suffix>
                  <i class="el-icon-view el-input__icon" @click="showPwd" />
                </template>
                <template v-slot:prefix>
                  <i class="icon-mima" />
                </template>
              </el-input>
            </el-form-item>
            <el-form-item prop="code">
              <el-input
                v-model="loginForm.verifyCode"
                placeholder="输入验证码"
                size="small"
                auto-complete="off"
                @keyup.enter.native="handleLogin"
              >
                <template v-slot:prefix>
                  <i class="icon-yanzhengma" style="margin-top: 6px" />
                </template>
                <template v-slot:append>
                  <span
                    :class="[{ display: !show }]"
                    class="msg-text"
                    @click="sendShortMsg"
                    >{{ show ? '验证码' : count }}</span
                  >
                </template>
              </el-input>
            </el-form-item>
            <el-form-item>
              <el-button
                type="primary"
                size="small"
                class="login-submit"
                @click.native.prevent="handleLogin"
                >登录</el-button
              >
            </el-form-item>
          </el-form>

          <div class="login-menu">
            <a
              href="https://www.dobbinsoft.com"
              @click.stop="activeName = 'user'"
              >道宾云官网</a
            >
            <a
              href="https://console.dobbinsoft.com"
              @click.stop="activeName = 'code'"
              >SaaS服务</a
            >
            <a
              href="https://www.dobbinsoft.com"
              @click.stop="activeName = 'third'"
              >多商户平台</a
            >
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, onMounted, onUnmounted } from 'vue'
import { sendMsg } from '@/api/login'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { ElNotification } from 'element-plus'

// 验证用户名
const validateUsername = (rule, value, callback) => {
  if (value == null) {
    callback(new Error('请输入正确的管理员用户名'))
  } else {
    callback()
  }
}

// 验证密码
const validatePassword = (rule, value, callback) => {
  if (value.length < 6) {
    callback(new Error('管理员密码长度应大于6'))
  } else {
    callback()
  }
}

// 登录表单数据
const loginForm = ref({
  username: 'guest',
  password: '123456',
  verifyCode: '666666',
})

// 登录表单验证规则
const loginRules = ref({
  username: [{ required: true, trigger: 'blur', validator: validateUsername }],
  password: [{ required: true, trigger: 'blur', validator: validatePassword }],
  verifyCode: [{ required: true, trigger: 'blur', message: '验证码不能为空' }],
})

// 密码输入类型
const passwordType = ref('password')
// 加载状态
const loading = ref(false)
// 验证码加载状态
const verifyLoading = ref(false)
// 显示验证码文本
const show = ref(true)
// 倒计时
const count = ref('')
// 定时器
const timer = ref(null)
// 重定向路径
const redirect = ref('')
// 激活名称
const activeName = ref('')

// 获取 Vuex store 实例
const store = useStore()
// 获取 Vue Router 实例
const router = useRouter()
// 获取表单引用
const loginFormRef = ref(null)

// 监听路由变化
watch(
  () => router.currentRoute.value,
  (route) => {
    redirect.value = route.query && route.query.redirect
  },
  { immediate: true }
)

// 组件挂载后执行
onMounted(() => {
  // window.addEventListener('hashchange', afterQRScan)
})

// 组件销毁前执行
onUnmounted(() => {
  // window.removeEventListener('hashchange', afterQRScan)
})

// 发送短信验证码
const sendShortMsg = () => {
  if (!show.value) {
    // 提示等待 60 秒后重试
    ElNotification.error({
      title: '失败',
      message: '请等待60s后重试',
    })
    return
  }
  if (
    loginForm.value.username == null ||
    loginForm.value.username === '' ||
    loginForm.value.password == null ||
    loginForm.value.password === ''
  ) {
    // 提示先填写用户名和密码
    ElNotification.error({
      title: '失败',
      message: '请先填写用户名和密码',
    })
    return false
  }
  verifyLoading.value = true
  sendMsg(loginForm.value)
    .then((response) => {
      verifyLoading.value = false
      // 提示信息发送成功
      ElNotification.success({
        title: '成功',
        message: '信息发送成功',
      })
      const TIME_COUNT = 60
      if (!timer.value) {
        count.value = TIME_COUNT
        show.value = false
        timer.value = setInterval(() => {
          if (count.value > 0 && count.value <= TIME_COUNT) {
            count.value--
          } else {
            show.value = true
            clearInterval(timer.value)
            timer.value = null
          }
        }, 1000)
      }
    })
    .catch((response) => {
      verifyLoading.value = false
      // 提示发送失败
      ElNotification.error({
        title: '失败',
        message: response.data.errmsg,
      })
      verifyLoading.value = false
    })
}

// 显示/隐藏密码
const showPwd = () => {
  if (passwordType.value === 'password') {
    passwordType.value = ''
  } else {
    passwordType.value = 'password'
  }
}

// 处理登录
const handleLogin = () => {
  loginFormRef.value.validate((valid) => {
    if (valid && !loading.value) {
      loading.value = true
      store
        .dispatch('LoginByUsername', loginForm.value)
        .then(() => {
          loading.value = false
          router.push({ path: redirect.value || '/' })
        })
        .catch((response) => {
          // 提示登录失败
          ElNotification.error({
            title: '失败',
            message: response.data.errmsg,
          })
          loading.value = false
        })
    } else {
      return false
    }
  })
}
</script>

<style>
.msg-text {
  display: block;
  width: 60px;
  font-size: 12px;
  text-align: center;
  cursor: pointer;
}
.msg-text.display {
  color: #ccc;
}
</style>

<style lang="scss">
@use '@/styles/login.scss';
</style>
