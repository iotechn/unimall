import request from '@/utils/request'
import Qs from 'qs'

export function loginByUsername(username, password, verifyCode) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin',
      _mt: 'login',
      username,
      password,
      verifyCode
    })
  })
}

export function sendMsg(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin',
      _mt: 'sendLoginMsg',
      ...data
    })
  })
}

export function logout() {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin',
      _mt: 'logout'
    })
  })
}

export function getUserInfo() {
  return request({
    method: 'get',
    params: {
      _gp: 'admin',
      _mt: 'info'
    }
  })
}

