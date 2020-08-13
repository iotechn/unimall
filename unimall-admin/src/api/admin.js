import request from '@/utils/request'
import Qs from 'qs'

export function listAdmin(query) {
  return request({
    method: 'get',
    params: {
      _gp: 'admin',
      _mt: 'list',
      ...query
    }
  })
}

export function listAdminLog(query) {
  return request({
    method: 'get',
    params: {
      _gp: 'admin.log',
      _mt: 'list',
      ...query
    }
  })
}

export function createAdmin(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin',
      _mt: 'create',
      adminDTO: JSON.stringify(data)
    })
  })
}

export function readminAdmin(data) {
  return request({
    url: '/admin/readmin',
    method: 'get',
    data
  })
}

export function updateAdmin(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin',
      _mt: 'edit',
      adminDTO: JSON.stringify(data)
    })
  })
}

export function deleteAdmin(id) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin',
      _mt: 'delete',
      id: id
    })
  })
}

export function bindUniNotify() {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin',
      _mt: 'bindUniNotify'
    })
  })
}
