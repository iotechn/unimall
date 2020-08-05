import axios from 'axios'
import { Message, MessageBox } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'

// create an axios instance
const service = axios.create({
  baseURL: process.env.HOST + '/m.api', // api 的 base_url
  timeout: 15000 // request timeout
})

// request interceptor
service.interceptors.request.use(
  config => {
    // Do something before request is sent
    if (store.getters.token) {
      // 让每个请求携带token-- ['ADMINTOKEN']为自定义key 请根据实际情况自行修改
      config.headers['ADMINTOKEN'] = getToken()
    }
    return config
  },
  error => {
    // Do something with request error
    console.log(error) // for debug
    Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  response => {
    const res = response.data
    if (res.errno === 10001) {
      MessageBox.alert('系统未登录，请重新登录', '错误', {
        confirmButtonText: '确定',
        type: 'error'
      }).then(() => {
        store.dispatch('FedLogOut').then(() => {
          location.reload()
        })
      })
      return Promise.reject('error')
    } if (res.errno !== 200) {
      Message({
        message: res.errmsg + ' 异常代码：' + res.errno,
        type: 'error',
        duration: 5 * 1000
      })
      return Promise.reject(response)
    } else {
      return response
    }
  }, error => {
    Message({
      message: error + '（请联系系统管理员）',
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  })

export default service
