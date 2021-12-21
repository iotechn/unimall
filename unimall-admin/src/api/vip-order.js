import request from '@/utils/request'
import Qs from 'qs'

export function list(query) {
  return request({
    method: 'post',
    params: {
      _gp: 'admin.vip.order',
      _mt: 'list',
      ...query
    }
  })
}

export function bind(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.vip.order',
      _mt: 'bind',
      ...data
    })
  })
}

export function refund(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.vip.order',
      _mt: 'refund',
      id: data
    })
  })
}
