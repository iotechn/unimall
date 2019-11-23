<template>
  <div
    class="login-container"
    @keyup.enter.native="handleLogin">
    <div class="login-weaper animated bounceInDown">
      <div class="login-left">
        <div class="login-time">
          欢迎使用
        </div>
        <img
          class="img"
          src="@/assets/avatar.png"
          alt="">
        <p class="title">Unimall 开源商城后台登录</p>
      </div>
      <div class="login-border">
        <div class="login-main">
          <h4 class="login-title">
            登录 Unimall
          </h4>
          <el-form
            ref="loginForm"
            :rules="loginRules"
            :model="loginForm"
            class="login-form"
            status-icon
            label-width="0">
            <el-form-item prop="username">
              <el-input
                v-model="loginForm.username"
                placeholder="请输入用户名"
                size="small"
                auto-complete="off"
                @keyup.enter.native="handleLogin">
                <i
                  slot="prefix"
                  class="icon-yonghu"/>
              </el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input
                :type="passwordType"
                v-model="loginForm.password"
                placeholder="请输入密码"
                size="small"
                auto-complete="off"
                @keyup.enter.native="handleLogin">
                <i
                  slot="suffix"
                  class="el-icon-view el-input__icon"
                  @click="showPwd"/>
                <i
                  slot="prefix"
                  class="icon-mima"/>
              </el-input>
            </el-form-item>
            <el-form-item prop="code">
              <el-input
                v-model="loginForm.verifyCode"
                placeholder="输入验证码"
                size="small"
                auto-complete="off"
                @keyup.enter.native="handleLogin">
                <i
                  slot="prefix"
                  class="icon-yanzhengma"
                  style="margin-top:6px;"/>
                <template slot="append">
                  <span
                    :class="[{display:!show}]"
                    class="msg-text"
                    @click="sendShortMsg">{{ show ? '验证码' : count }}</span>
                </template>
              </el-input>
            </el-form-item>
            <el-form-item>
              <el-button
                type="primary"
                size="small"
                class="login-submit"
                @click.native.prevent="handleLogin">登录</el-button>
            </el-form-item>
          </el-form>

          <div class="login-menu">
            <a
              href="#"
              @click.stop="activeName='user'">驽驹官网</a>
            <a
              href="#"
              @click.stop="activeName='code'">客服</a>
            <a
              href="#"
              @click.stop="activeName='third'">Test</a>
          </div>
        </div>

      </div>
    </div>
  </div>
</template>

<script>
import { sendMsg } from '@/api/login'

export default {
  name: 'Login',
  data() {
    const validateUsername = (rule, value, callback) => {
      if (validateUsername == null) {
        callback(new Error('请输入正确的管理员用户名'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (value.length < 6) {
        callback(new Error('管理员密码长度应大于6'))
      } else {
        callback()
      }
    }
    return {
      loginForm: {
        username: 'guest',
        password: '123456',
        verifyCode: '666666'
      },
      loginRules: {
        username: [
          { required: true, trigger: 'blur', validator: validateUsername }
        ],
        password: [
          { required: true, trigger: 'blur', validator: validatePassword }
        ],
        verifyCode: [
          { required: true, trigger: 'blur', message: '验证码不能为空' }
        ]
      },
      passwordType: 'password',
      loading: false,
      verifyLoading: false,
      show: true,
      count: '',
      timer: null
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  created() {
    // window.addEventListener('hashchange', this.afterQRScan)
  },
  destroyed() {
    // window.removeEventListener('hashchange', this.afterQRScan)
  },
  methods: {
    sendShortMsg() {
      if (!this.show) {
        this.$notify.error({
          title: '失败',
          message: '请等待60s后重试'
        })
        return
      }
      if (this.loginForm.username == null || this.loginForm.username === '' || this.loginForm.password == null || this.loginForm.password === '') {
        this.$notify.error({
          title: '失败',
          message: '请先填写用户名和密码'
        })
        return false
      }
      this.verifyLoading = true
      sendMsg(this.loginForm)
        .then(response => {
          this.verifyLoading = false
          this.$notify.success({
            title: '成功',
            message: '信息发送成功'
          })
          const TIME_COUNT = 60
          if (!this.timer) {
            this.count = TIME_COUNT
            this.show = false
            this.timer = setInterval(() => {
              if (this.count > 0 && this.count <= TIME_COUNT) {
                this.count--
              } else {
                this.show = true
                clearInterval(this.timer)
                this.timer = null
              }
            }, 1000)
          }
        })
        .catch(response => {
          this.verifyLoading = false
          this.$notify.error({
            title: '失败',
            message: response.data.errmsg
          })
          this.verifyLoading = false
        })
    },
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid && !this.loading) {
          this.loading = true
          this.$store
            .dispatch('LoginByUsername', this.loginForm)
            .then(() => {
              this.loading = false
              this.$router.push({ path: this.redirect || '/' })
            })
            .catch(response => {
              this.$notify.error({
                title: '失败',
                message: response.data.errmsg
              })
              this.loading = false
            })
        } else {
          return false
        }
      })
    }
  }
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
@import "@/styles/login.scss";
</style>
