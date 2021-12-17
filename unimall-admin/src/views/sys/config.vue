<template>
  <div class="app-container">
    <el-card class="box-card">
      <h3>微信APP配置</h3>
      <el-form ref="wxAppDataForm" :model="wxAppDataForm" label-width="150px">

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
        <el-button :loading="submiting" type="primary" @click="handleSave('wxAppDataForm', prefixs.wxAppDataPrefix)">保存更改</el-button>
      </div>

    </el-card>

    <el-card class="box-card">
      <h3>微信支付配置</h3>
      <el-form ref="wxPayDataForm" :model="wxPayDataForm" label-width="150px">

        <el-form-item label="商户ID" prop="mchId">
          <el-input v-model="wxPayDataForm.mchId" placeholder="微信商户平台 商户ID" />
        </el-form-item>

        <el-form-item label="商户KEY" prop="mchKey">
          <el-input v-model="wxPayDataForm.mchKey" placeholder="微信商户平台 自己设定的32位 Api调用 Key" />
        </el-form-item>

        <el-form-item label="支付回调URL" prop="notifyUrl">
          <el-input v-model="wxPayDataForm.notifyUrl" placeholder="https://api.example.com/cb/wxpay" />
        </el-form-item>

        <el-form-item>
          <el-upload
            :auto-upload="false"
            :on-change="onCertChange"
            :multiple="false"
            :show-file-list="false"
            class="upload-demo"
            accept=".p12"
            drag
            action="https://jsonplaceholder.typicode.com/posts/">
            <i class="el-icon-upload"/>
            <div v-if="file" class="el-upload__text"><em>{{ file.name }}</em></div>
            <div v-else class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            <div slot="tip" class="el-upload__tip">只能上传.p12文件</div>
          </el-upload>
        </el-form-item>

      </el-form>

      <div class="op-container">
        <el-button :loading="submiting" type="primary" @click="handleSave('wxPayDataForm', prefixs.wxPayDataPrefix)">保存更改</el-button>
      </div>

    </el-card>

    <el-card class="box-card">
      <h3>支付宝APP配置</h3>
      <el-form ref="aliAppDataForm" :model="aliAppDataForm" label-width="150px">

        <el-form-item label="网关地址" prop="aliGateway">
          <el-input v-model="aliAppDataForm.aliGateway" placeholder="https://openapi.alipay.com/gateway.do" />
        </el-form-item>

        <el-form-item label="小程序 AppId" prop="miniAppId">
          <el-input v-model="aliAppDataForm.miniAppId" placeholder="2021002140688403" />
        </el-form-item>

        <el-form-item label="小程序 支付宝公钥" prop="miniAppPublicKey1">
          <el-input v-model="aliAppDataForm.miniAppPublicKey1" placeholder="" />
        </el-form-item>

        <el-form-item label="小程序 应用公钥" prop="miniAppPublicKey2">
          <el-input v-model="aliAppDataForm.miniAppPublicKey2" placeholder="" />
        </el-form-item>

        <el-form-item label="小程序 应用私钥" prop="miniAppPrivateKey2">
          <el-input v-model="aliAppDataForm.miniAppPrivateKey2" placeholder="" />
        </el-form-item>

        <el-form-item label="小程序 支付回调URL" prop="miniNotifyUrl">
          <el-input v-model="aliAppDataForm.miniNotifyUrl" placeholder="" />
        </el-form-item>

        <el-form-item label="APP AppId" prop="appId">
          <el-input v-model="aliAppDataForm.appId" placeholder="2021002140688403" />
        </el-form-item>

        <el-form-item label="APP 支付宝公钥" prop="appPublicKey1">
          <el-input v-model="aliAppDataForm.appPublicKey1" placeholder="" />
        </el-form-item>

        <el-form-item label="APP 应用公钥" prop="appPublicKey2">
          <el-input v-model="aliAppDataForm.appPublicKey2" placeholder="" />
        </el-form-item>

        <el-form-item label="APP 应用私钥" prop="appPrivateKey2">
          <el-input v-model="aliAppDataForm.appPrivateKey2" placeholder="" />
        </el-form-item>

        <el-form-item label="APP 支付回调URL" prop="appNotifyUrl">
          <el-input v-model="aliAppDataForm.appNotifyUrl" placeholder="" />
        </el-form-item>

      </el-form>

      <div class="op-container">
        <el-button :loading="submiting" type="primary" @click="handleSave('aliAppDataForm', prefixs.aliAppDataPrefix)">保存更改</el-button>
      </div>

    </el-card>

    <el-card class="box-card">
      <h3>短信通知配置</h3>
      <el-form ref="smsDataForm" :model="smsDataForm" label-width="150px">

        <el-form-item label="启用(需要重启)" prop="enable">
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

        <el-form-item v-if="smsDataForm.enable === 'aliyun'" label="AccessKeyId" prop="aliyunAccessKeyId">
          <el-input v-model="smsDataForm.aliyunAccessKeyId" />
        </el-form-item>

        <el-form-item v-if="smsDataForm.enable === 'aliyun'" label="AccessKeySecret" prop="aliyunAccessKeySecret">
          <el-input v-model="smsDataForm.aliyunAccessKeySecret" />
        </el-form-item>

        <el-form-item v-if="smsDataForm.enable === 'aliyun'" label="Signature" prop="aliyunSignature">
          <el-input v-model="smsDataForm.aliyunSignature" />
        </el-form-item>

        <el-form-item v-if="smsDataForm.enable === 'aliyun'" label="注册 模板ID" prop="aliyunRegisterTemplateId">
          <el-input v-model="smsDataForm.aliyunRegisterTemplateId" />
        </el-form-item>

        <el-form-item v-if="smsDataForm.enable === 'aliyun'" label="绑定手机 模板ID" prop="aliyunBindPhoneTemplateId">
          <el-input v-model="smsDataForm.aliyunBindPhoneTemplateId" />
        </el-form-item>

        <el-form-item v-if="smsDataForm.enable === 'aliyun'" label="重置密码 模板ID" prop="aliyunResetPasswordTemplateId">
          <el-input v-model="smsDataForm.aliyunResetPasswordTemplateId" />
        </el-form-item>

        <el-form-item v-if="smsDataForm.enable === 'aliyun'" label="管理员登录 模板ID" prop="aliyunAdminLoginTemplateId">
          <el-input v-model="smsDataForm.aliyunAdminLoginTemplateId" />
        </el-form-item>

        <el-form-item v-if="smsDataForm.enable === 'qcloud'" label="App Id" prop="qcloudAppId">
          <el-input v-model="smsDataForm.qcloudAppId" />
        </el-form-item>

        <el-form-item v-if="smsDataForm.enable === 'qcloud'" label="App Key" prop="qcloudAppKey">
          <el-input v-model="smsDataForm.qcloudAppKey" />
        </el-form-item>

        <el-form-item v-if="smsDataForm.enable === 'qcloud'" label="Signature" prop="qcloudSignature">
          <el-input v-model="smsDataForm.qcloudSignature" />
        </el-form-item>

        <el-form-item v-if="smsDataForm.enable === 'qcloud'" label="注册 模板ID" prop="qcloudRegisterTemplateId">
          <el-input v-model="smsDataForm.qcloudRegisterTemplateId" />
        </el-form-item>

        <el-form-item v-if="smsDataForm.enable === 'qcloud'" label="绑定手机 模板ID" prop="qcloudBindPhoneTemplateId">
          <el-input v-model="smsDataForm.qcloudBindPhoneTemplateId" />
        </el-form-item>

        <el-form-item v-if="smsDataForm.enable === 'qcloud'" label="重置密码 模板ID" prop="qcloudResetPasswordTemplateId">
          <el-input v-model="smsDataForm.qcloudResetPasswordTemplateId" />
        </el-form-item>

        <el-form-item v-if="smsDataForm.enable === 'qcloud'" label="管理员登录 模板ID" prop="qcloudAdminLoginTemplateId">
          <el-input v-model="smsDataForm.qcloudAdminLoginTemplateId" />
        </el-form-item>

      </el-form>

      <div class="op-container">
        <el-button :loading="submiting" type="primary" @click="handleSave('smsDataForm', prefixs.smsDataPrefix)">保存更改</el-button>
      </div>

    </el-card>

    <el-card class="box-card">
      <h3>OSS配置(需要重启)</h3>
      <el-form ref="ossDataForm" :model="ossDataForm" label-width="150px">

        <el-form-item label="启用" prop="enable">
          <el-select v-model="ossDataForm.enable" placeholder="请选择存储平台">
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

        <el-form-item v-if="ossDataForm.enable === 'aliyun'" label="AccessKeyId" prop="aliAccessKeyId">
          <el-input v-model="ossDataForm.aliAccessKeyId" />
        </el-form-item>

        <el-form-item v-if="ossDataForm.enable === 'aliyun'" label="AccessKeySecret" prop="aliAccessKeySecret">
          <el-input v-model="ossDataForm.aliAccessKeySecret" />
        </el-form-item>

        <el-form-item v-if="ossDataForm.enable === 'aliyun'" label="EndPoint" prop="aliEndpoint">
          <el-input v-model="ossDataForm.aliEndpoint" />
        </el-form-item>

        <el-form-item v-if="ossDataForm.enable === 'aliyun'" label="Bucket" prop="aliBucket">
          <el-input v-model="ossDataForm.aliBucket" />
        </el-form-item>

        <el-form-item v-if="ossDataForm.enable === 'aliyun'" label="Base Url" prop="aliBaseUrl">
          <el-input v-model="ossDataForm.aliBaseUrl" />
        </el-form-item>

        <el-form-item v-if="ossDataForm.enable === 'qcloud'" label="SecretId" prop="qcloudSecretId">
          <el-input v-model="ossDataForm.qcloudSecretId" />
        </el-form-item>

        <el-form-item v-if="ossDataForm.enable === 'qcloud'" label="SecretKey" prop="qcloudSecretKey">
          <el-input v-model="ossDataForm.qcloudSecretKey" />
        </el-form-item>

        <el-form-item v-if="ossDataForm.enable === 'qcloud'" label="Region" prop="qcloudRegion">
          <el-input v-model="ossDataForm.qcloudRegion" />
        </el-form-item>

        <el-form-item v-if="ossDataForm.enable === 'qcloud'" label="Bucket" prop="qcloudBucket">
          <el-input v-model="ossDataForm.qcloudBucket" />
        </el-form-item>

        <el-form-item v-if="ossDataForm.enable === 'qcloud'" label="Base Url" prop="qcloudBaseUrl">
          <el-input v-model="ossDataForm.qcloudBaseUrl" />
        </el-form-item>

      </el-form>

      <div class="op-container">
        <el-button :loading="submiting" type="primary" @click="handleSave('ossDataForm', prefixs.ossDataPrefix)">保存更改</el-button>
      </div>

    </el-card>

    <el-card class="box-card">
      <h3>订单信息配置</h3>
      <el-form ref="orderDataForm" :model="orderDataForm" label-width="150px">

        <el-form-item label="自动取消延时时间(S)" prop="autoCancelTime">
          <el-input-number :precision="0" v-model="orderDataForm.autoCancelTime" />
        </el-form-item>

        <el-form-item label="自动收货延时时间(S)" prop="autoConfirmTime">
          <el-input-number :precision="0" v-model="orderDataForm.autoConfirmTime" />
        </el-form-item>
      </el-form>

      <div class="op-container">
        <el-button :loading="submiting" type="primary" @click="handleSave('orderDataForm', prefixs.orderDataPrefix)">保存更改</el-button>
      </div>

    </el-card>

    <el-card class="box-card">
      <h3>广告配置</h3>
      <el-form ref="advertDataForm" :model="advertDataForm" label-width="150px">

        <el-form-item label="首页热销推荐商品数" prop="topSalesNum">
          <el-input-number :precision="0" v-model="advertDataForm.topSalesNum" />
        </el-form-item>
      </el-form>

      <div class="op-container">
        <el-button :loading="submiting" type="primary" @click="handleSave('advertDataForm', prefixs.advertDataPrefix)">保存更改</el-button>
      </div>

    </el-card>

    <el-card class="box-card">
      <h3>系统配置</h3>
      <el-form ref="advertDataForm" :model="systemDataForm" label-width="150px">

        <el-form-item label="用户登录有效期(分)" prop="userSessionPeriod">
          <el-input-number :precision="0" v-model="systemDataForm.userSessionPeriod" />
        </el-form-item>

        <el-form-item label="管理登录有效期(分)" prop="adminSessionPeriod">
          <el-input-number :precision="0" v-model="systemDataForm.adminSessionPeriod" />
        </el-form-item>

      </el-form>

      <div class="op-container">
        <el-button :loading="submiting" type="primary" @click="handleSave('systemDataForm', prefixs.systemDataPrefix)">保存更改</el-button>
      </div>
    </el-card>

    <el-card class="box-card">
      <h3>管理员通知</h3>
      <el-form ref="advertDataForm" :model="adminNotifyDataForm" label-width="150px">

        <el-form-item label="启用(需要重启)" prop="enable">
          <el-select v-model="adminNotifyDataForm.enable" placeholder="默认不可游客访问">
            <el-option
              :value="'mock'"
              :label="'控制台模拟'"
            />
            <el-option
              :value="'uninotify'"
              :label="'驽驹通知中心'"
            />
          </el-select>
        </el-form-item>

        <el-form-item v-if="adminNotifyDataForm.enable === 'uninotify'" label="服务器URL" prop="uniNotifyUrl">
          <el-input v-model="adminNotifyDataForm.uniNotifyUrl" placeholder="http://public.dobbinsoft.com/m.api" />
        </el-form-item>

        <el-form-item v-if="adminNotifyDataForm.enable === 'uninotify'" label="Uni AppId" prop="uniNotifyAppId">
          <el-input v-model="adminNotifyDataForm.uniNotifyAppId" placeholder="申请的通知AppId" />
        </el-form-item>

        <el-form-item v-if="adminNotifyDataForm.enable === 'uninotify'" label="Uni AppSecret" prop="uniNotifyAppSecret">
          <el-input v-model="adminNotifyDataForm.uniNotifyAppSecret" placeholder="申请的通知AppSecret" />
        </el-form-item>

      </el-form>

      <div class="op-container">
        <el-button :loading="submiting" type="primary" @click="handleSave('adminNotifyDataForm', prefixs.adminNotifyDataPrefix)">保存更改</el-button>
      </div>
    </el-card>

    <el-card class="box-card">
      <h3>进销存开放平台</h3>
      <el-form ref="erpDataForm" :model="erpDataForm" label-width="150px">

        <el-form-item label="启用" prop="enable">
          <el-select v-model="erpDataForm.enable" placeholder="请选择服务提供者">
            <el-option
              :value="'disable'"
              :label="'不使用'"
            />
            <el-option
              :value="'dobbin'"
              :label="'道宾云'"
            />
          </el-select>
        </el-form-item>

        <el-form-item v-if="erpDataForm.enable == 'dobbin'" label="租户ID" prop="dobbinTenementId">
          <el-input v-model="erpDataForm.dobbinTenementId" />
        </el-form-item>

        <el-form-item v-if="erpDataForm.enable == 'dobbin'" label="ClientCode" prop="dobbinClientCode">
          <el-input v-model="erpDataForm.dobbinClientCode" />
        </el-form-item>

        <el-form-item v-if="erpDataForm.enable == 'dobbin'" label="ServerPublicKey" prop="dobbinServerPublicKey">
          <el-input v-model="erpDataForm.dobbinServerPublicKey" />
        </el-form-item>

        <el-form-item v-if="erpDataForm.enable == 'dobbin'" label="ClientPublicKey" prop="dobbinClientPublicKey">
          <el-input v-model="erpDataForm.dobbinClientPublicKey" />
        </el-form-item>

        <el-form-item v-if="erpDataForm.enable == 'dobbin'" label="ClientPrivateKey" prop="dobbinClientPrivateKey">
          <el-input v-model="erpDataForm.dobbinClientPrivateKey" />
        </el-form-item>

      </el-form>

      <div class="op-container">
        <el-button :loading="submiting" type="primary" @click="handleSave('erpDataForm', prefixs.erpDataPrefix)">保存更改</el-button>
        <el-button v-if="erpDataForm.enable == 'dobbin'" :loading="submiting" type="primary" @click="syncCategory">同步库存</el-button>
        <el-button v-if="erpDataForm.enable == 'dobbin'" :loading="submiting" type="primary" @click="syncProduct">同步商品</el-button>
      </div>

    </el-card>

  </div>
</template>
<script>
import { syncCategory, syncProduct } from '@/api/erp'
import { getToken } from '@/utils/auth'
import { getData, save } from '@/api/config'
export default {
  name: 'SysConfig',
  data() {
    return {
      file: '',
      // 请按照此命名规范命名
      prefixs: {
        wxAppDataPrefix: 'WX_APP_CONFIG:',
        wxPayDataPrefix: 'WX_PAY_CONFIG:',
        aliAppDataPrefix: 'ALI_APP_CONFIG:',
        smsDataPrefix: 'SMS_CONFIG:',
        ossDataPrefix: 'OSS_CONFIG:',
        orderDataPrefix: 'ORDER_CONFIG:',
        advertDataPrefix: 'ADVERT_CONFIG:',
        searchDataPrefix: 'OPEN_SEARCH_CONFIG:',
        systemDataPrefix: 'SYSTEM_CONFIG:',
        adminNotifyDataPrefix: 'ADMIN_NOTIFY_CONFIG:',
        erpDataPrefix: 'ADMIN_ERP_OPEN_PLATFORM_PREFIX:'
      },
      wxAppDataForm: {},
      wxPayDataForm: {},
      aliAppDataForm: {},
      smsDataForm: {},
      ossDataForm: {},
      orderDataForm: {},
      advertDataForm: {},
      searchDataForm: {},
      systemDataForm: {},
      adminNotifyDataForm: {},
      erpDataForm: {},
      submiting: false
    }
  },
  computed: {
    headers() {
      return {
        ADMINTOKEN: getToken()
      }
    }
  },
  created() {
    this.loadData()
  },
  methods: {
    loadData() {
      // 1. 加载数据
      const loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      getData().then(res => {
        // 将各个配置进行分组，分到各个配置项中
        const arr = res.data.data
        for (let i = 0; i < arr.length; i++) {
          const cfg = arr[i]
          for (const prefix in this.prefixs) {
            if (cfg.configKey.startsWith(this.prefixs[prefix])) {
              this[prefix.replace('Prefix', 'Form')][cfg.configKey.replace(this.prefixs[prefix], '')] = cfg.configValue
            }
          }
        }
        for (const prefix in this.prefixs) {
          // 将对象进行深拷贝
          this[prefix.replace('Prefix', 'Form')] = JSON.parse(JSON.stringify(this[prefix.replace('Prefix', 'Form')]))
        }
        loading.close()
        this.$forceUpdate()
      })
        .catch(failres => {
          loading.close()
        })
    },
    handleSave(form, prefix) {
      this.submiting = true
      const dataForm = this[form]
      const configs = []
      for (const attr in dataForm) {
        const obj = {
          configKey: attr,
          configValue: dataForm[attr]
        }
        configs.push(obj)
      }
      save(JSON.stringify(configs), prefix)
        .then(res => {
        // 保存成功
          this.submiting = false
          this.$notify.success({
            type: 'success',
            title: '成功',
            message: '保存成功'
          })
        })
        .catch(failres => {
          this.submiting = false
        })
    },
    syncCategory() {
      this.submiting = true
      this.$notify.success({
        type: 'success',
        title: '成功',
        message: '已经开始类目同步任务'
      })
      syncCategory().then(res => {
        this.submiting = false
        this.$notify.success({
          type: 'success',
          title: '成功',
          message: '类目同步成功！'
        })
      }).catch(failres => {
        this.submiting = false
      })
    },
    syncProduct() {
      this.submiting = true
      this.$notify.success({
        type: 'success',
        title: '成功',
        message: '已经开始商品同步任务'
      })
      syncProduct().then(res => {
        this.submiting = false
        this.$notify.success({
          type: 'success',
          title: '成功',
          message: '商品同步成功！'
        })
      }).catch(failres => {
        this.submiting = false
      })
    },
    onCertChange(file) {
      this.file = file
      const reader = new FileReader()
      /* 当读取操作成功完成时调用*/
      reader.onload = async(e) => {
        const base64Str = reader.result // 取得数据 这里的this指向FileReader（）对象的实例reader
        this.wxPayDataForm.keyContent = base64Str.replace('data:application/x-pkcs12;base64,', '')
      }
      reader.readAsDataURL(file.raw) // 异步读取文件内容，结果用data:url的字符串形式表示
    }
  }
}
</script>

<style>
.box-card {
  margin-bottom: 15px;
}
</style>
