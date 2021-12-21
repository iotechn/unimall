import request from '@/utils/request'
import Qs from 'qs'

export function list(query) {
  return request({
    method: 'post',
    params: {
      _gp: 'admin.vip.template',
      _mt: 'list',
      ...query
    }
  })
}

export function create(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.vip.template',
      _mt: 'create',
      ...data
    })
  })
}

export function edit(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.vip.template',
      _mt: 'edit',
      ...data
    })
  })
}

export function deleteTemplate(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.vip.template',
      _mt: 'delete',
      id: data
    })
  })
}

export function generate(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.vip.template',
      _mt: 'generate',
      ...data
    })
  })
}
