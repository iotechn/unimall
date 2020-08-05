<template>
  <div class="app-container">
    <el-card class="box-card">
      <h3>微信APP配置</h3>
      <el-form ref="wxAppDataForm" :rules="rules" :model="wxAppDataForm" label-width="150px">

        <el-form-item label="小程序 AppId" prop="miniAppId">
          <el-input v-model="wxAppDataForm.miniAppId" placeholder="wx7393c9e390fd38ab" />
        </el-form-item>

        <el-form-item label="小程序 AppSecret" prop="miniAppSecret">
          <el-input v-model="wxAppDataForm.miniAppSecret" placeholder="6c91b5af08e39b06b428d8fac333d9c9" />
        </el-form-item>

        <el-form-item label="App AppId" prop="appId">
          <el-input v-model="wxAppDataForm.appId" placeholder="wx6e1355f89b03fd2e" />
        </el-form-item>

        <el-form-item label="App AppSecret" prop="appScret">
          <el-input v-model="wxAppDataForm.appScret" placeholder="013972cce59f480f6ac0890422f1aefd" />
        </el-form-item>

        <el-form-item label="公众号 AppId" prop="h5AppId">
          <el-input v-model="wxAppDataForm.h5AppId" placeholder="wxc9d6f423651c0f24" />
        </el-form-item>

        <el-form-item label="公众号 AppSecret" prop="h5AppSecret">
          <el-input v-model="wxAppDataForm.h5AppSecret" placeholder="013972cce59f480f6ac0890422f1aefd" />
        </el-form-item>

      </el-form>

      <div class="op-container">
        <el-button v-permission="['promote:merchant:create']" type="primary" @click="handleEdit">保存更改</el-button>
      </div>

    </el-card>

    <el-card class="box-card">
      <h3>微信支付配置</h3>
      <el-form ref="wxPayDataForm" :rules="rules" :model="wxPayDataForm" label-width="150px">

        <el-form-item label="商户ID" prop="mchId">
          <el-input v-model="wxPayDataForm.mchId" placeholder="微信商户平台 商户ID" />
        </el-form-item>

        <el-form-item label="商户KEY" prop="mchKey">
          <el-input v-model="wxPayDataForm.mchKey" placeholder="微信商户平台 自己设定的32位 Api调用 Key" />
        </el-form-item>

        <el-form-item label="支付回调URL" prop="notifyUrl">
          <el-input v-model="wxPayDataForm.notifyUrl" placeholder="https://api.example.com/cb/wxpay" />
        </el-form-item>

        <el-form-item label="FS证书地址" prop="keyPath">
          <el-input v-model="wxPayDataForm.appScret" placeholder="文件系统中退款证书的位置 eg: /cert/apiclient_cert.p12" />
        </el-form-item>

      </el-form>

      <div class="op-container">
        <el-button v-permission="['promote:merchant:create']" type="primary" @click="handleEdit">保存更改</el-button>
      </div>

    </el-card>

    <el-card class="box-card">
      <h3>短信通知配置</h3>
      <el-form ref="smsDataForm" :rules="rules" :model="smsDataForm" label-width="150px">

        <el-form-item label="启用" prop="enable">
          <el-select v-model="smsDataForm.enable" placeholder="请选择短信通知平台">
            <el-option
              :value="'mock'"
              :label="'控制台模拟'"
            />
            <el-option
              :value="'aliyun'"
              :label="'阿里云'"
            />
            <el-option
              :value="'qcloud'"
              :label="'腾讯云'"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="商户KEY" prop="mchKey">
          <el-input v-model="smsDataForm.mchKey" />
        </el-form-item>

      </el-form>

      <div class="op-container">
        <el-button type="primary" @click="handleUpdate('', '')">保存更改</el-button>
      </div>

    </el-card>

  </div>
</template>
<script>

// import { getData, save } from '@/api/config'
export default {
  name: 'SysConfig',
  data() {
    return {
      wxAppDataForm: {},
      wxPayDataForm: {},
      smsDataForm: {}
    }
  },
  created() {
    this.loadData()
  },
  methods: {
    loadData() {
      // 1. 加载数据

    },
    save(form, prefix) {
      const dataForm = this[form]
      dataForm.map(item => {
        // const newObj = {}
      })
    },
    handleUpdate() {

    }
  }
}
</script>

<style>
.box-card {
  margin-bottom: 15px;
}
</style>
