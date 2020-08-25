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
        <el-button v-permission="['promote:merchant:create']" :loading="submiting" type="primary" @click="handleSave('wxAppDataForm', prefixs.wxAppDataPrefix)">保存更改</el-button>
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

        <el-form-item label="文件系统证书地址" prop="keyPath">
          <el-input v-model="wxPayDataForm.keyPath" placeholder="文件系统中退款证书的位置 eg: /cert/apiclient_cert.p12" />
        </el-form-item>

      </el-form>

      <div class="op-container">
        <el-button v-permission="['promote:merchant:create']" :loading="submiting" type="primary" @click="handleSave('wxPayDataForm', prefixs.wxPayDataPrefix)">保存更改</el-button>
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
      <h3>阿里云OSS配置(需要重启)</h3>
      <el-form ref="ossDataForm" :model="ossDataForm" label-width="150px">

        <el-form-item label="AccessKeyId" prop="accessKeyId">
          <el-input v-model="ossDataForm.accessKeyId" />
        </el-form-item>

        <el-form-item label="AccessKeySecret" prop="accessKeySecret">
          <el-input v-model="ossDataForm.accessKeySecret" />
        </el-form-item>

        <el-form-item label="EndPoint" prop="endpoint">
          <el-input v-model="ossDataForm.endpoint" />
        </el-form-item>

        <el-form-item label="bucket" prop="bucket">
          <el-input v-model="ossDataForm.bucket" />
        </el-form-item>

        <el-form-item label="图片路径" prop="dir">
          <el-input v-model="ossDataForm.dir" />
        </el-form-item>

        <el-form-item label="Base Url" prop="baseUrl">
          <el-input v-model="ossDataForm.baseUrl" />
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

        <el-form-item label="游客访问" prop="guest">
          <el-select v-model="systemDataForm.guest" placeholder="默认不可游客访问">
            <el-option
              :value="'true'"
              :label="'允许'"
            />
            <el-option
              :value="'false'"
              :label="'不允许'"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="SSL Crt 路径" prop="sslCrtPath">
          <el-input v-model="systemDataForm.sslCrtPath" />
        </el-form-item>

        <el-form-item label="SSL Crt 上传">
          <el-upload
            :action="uploadLocalPath"
            :headers="headers"
            :data="{
              fsf: systemDataForm.sslCrtPath
            }"
            accept=".crt,.pem"
            class="upload-demo"
            drag>
            <i class="el-icon-upload"/>
            <div class="el-upload__text">将Crt/Pem文件拖到此处，或<em>点击上传</em></div>
            <div slot="tip" class="el-upload__tip">Docker用户请勿修改上传路径，只能上传crt/pem纯文本文件</div>
          </el-upload>
        </el-form-item>

        <el-form-item label="SSL Key 路径" prop="sslKeyPath">
          <el-input v-model="systemDataForm.sslKeyPath" />
        </el-form-item>

        <el-form-item label="SSL Key 上传">
          <el-upload
            :action="uploadLocalPath"
            :headers="headers"
            :data="{
              fsf: systemDataForm.sslKeyPath
            }"
            class="upload-demo"
            accept=".key"
            drag>
            <i class="el-icon-upload"/>
            <div class="el-upload__text">将Key文件拖到此处，或<em>点击上传</em></div>
            <div slot="tip" class="el-upload__tip">Docker用户请勿修改上传路径，只能上传key文件纯文本文件</div>
          </el-upload>
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
      <h3>搜索引擎</h3>
      <el-form ref="searchDataForm" :model="searchDataForm" label-width="150px">

        <el-form-item label="启用(需要重启)" prop="enable">
          <el-select v-model="searchDataForm.enable" placeholder="请选择搜索引擎">
            <el-option
              :value="'db'"
              :label="'不使用'"
            />
            <el-option
              :value="'opensearch'"
              :label="'阿里云OpenSearch'"
            />
          </el-select>
        </el-form-item>

        <el-form-item v-if="searchDataForm.enable == 'opensearch'" label="AccessKeyId" prop="openSearchAccessKeyId">
          <el-input v-model="searchDataForm.openSearchAccessKeyId" />
        </el-form-item>

        <el-form-item v-if="searchDataForm.enable == 'opensearch'" label="AccessKeySecret" prop="openSearchAccessKeySecret">
          <el-input v-model="searchDataForm.openSearchAccessKeySecret" />
        </el-form-item>

        <el-form-item v-if="searchDataForm.enable == 'opensearch'" label="API 网关地址" prop="openSearchHost">
          <el-input v-model="searchDataForm.openSearchHost" />
        </el-form-item>

      </el-form>

      <div class="op-container">
        <el-button :loading="submiting" type="primary" @click="handleSave('searchDataForm', prefixs.searchDataPrefix)">保存更改</el-button>
        <el-button :loading="submiting" type="primary" @click="handleSearchEngineInit()">初始化</el-button>
        <el-button :loading="submiting" type="primary" @click="handleSearchEngineRebuild()">重建引擎数据</el-button>
      </div>

    </el-card>

  </div>
</template>
<script>

import { initSearchEngine, rebuildSearchEngine, reloadPropertiesSearchEngine } from '@/api/search'
import { uploadLocalPath } from '@/api/storage'
import { getToken } from '@/utils/auth'
import { getData, save } from '@/api/config'
export default {
  name: 'SysConfig',
  data() {
    return {
      // 为上传SSL证书等文件
      uploadLocalPath,
      // 请按照此命名规范命名
      prefixs: {
        wxAppDataPrefix: 'WX_APP_CONFIG:',
        wxPayDataPrefix: 'WX_PAY_CONFIG:',
        smsDataPrefix: 'SMS_CONFIG:',
        ossDataPrefix: 'OSS_CONFIG:',
        orderDataPrefix: 'ORDER_CONFIG:',
        advertDataPrefix: 'ADVERT_CONFIG:',
        searchDataPrefix: 'OPEN_SEARCH_CONFIG:',
        systemDataPrefix: 'SYSTEM_CONFIG:',
        adminNotifyDataPrefix: 'ADMIN_NOTIFY_CONFIG:'
      },
      wxAppDataForm: {},
      wxPayDataForm: {},
      smsDataForm: {},
      ossDataForm: {},
      orderDataForm: {},
      advertDataForm: {},
      searchDataForm: {},
      systemDataForm: {},
      adminNotifyDataForm: {},
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
            if (cfg.key.startsWith(this.prefixs[prefix])) {
              this[prefix.replace('Prefix', 'Form')][cfg.key.replace(this.prefixs[prefix], '')] = cfg.value
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
          key: attr,
          value: dataForm[attr]
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
          if (prefix === this.prefixs.searchDataPrefix) {
            // 重新加载属性
            this.$notify.warning({
              type: 'warning',
              title: '注意！Attention！',
              message: '若更换搜索引擎实现，请重启后端服务以使IoC加载实例',
              position: 'bottom-left',
              duration: 0
            })
            reloadPropertiesSearchEngine().then(reloadRes => {

            })
          }
        })
        .catch(failres => {
          this.submiting = false
        })
    },
    handleSearchEngineInit() {
      this.submiting = true
      initSearchEngine().then(res => {
        this.$notify.success({
          type: 'success',
          title: '成功',
          message: '初始化引擎成功'
        })
        this.submiting = false
      })
        .catch(failres => {
          this.submiting = false
        })
    },
    handleSearchEngineRebuild() {
      this.submiting = true
      rebuildSearchEngine().then(res => {
        this.$notify.success({
          type: 'success',
          title: '成功',
          message: '重建搜索引擎数据成功!'
        })
        this.submiting = false
      })
        .catch(failres => {
          this.submiting = false
        })
    }
  }
}
</script>

<style>
.box-card {
  margin-bottom: 15px;
}
</style>
