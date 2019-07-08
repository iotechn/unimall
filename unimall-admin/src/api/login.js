import request from '@/utils/request'

export function loginByUsername(username, password) {
  const data = {
    username,
    password
  }
  return request({
    method: 'post',
    params: {
      _gp: 'admin',
      _mt: 'login',
      ...data
    }
  })
}

export function logout() {
  return request({
    method: 'post',
    params: {
      _gp: 'admin',
      _mt: 'logout'
    }
  })
}

export function getUserInfo() {
  return request({
    method: 'post',
    params: {
      _gp: 'admin',
      _mt: 'info'
    }
  })
}

