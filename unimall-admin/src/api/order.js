import request from '@/utils/request'
import Qs from 'qs'

export function listOrder(query) {
  return request({
    method: 'get',
    params: {
      _gp: 'order',
      _mt: 'list',
      ...query
    }
  })
}

export function detailOrder(id) {
  return request({
    method: 'get',
    params: {
      _gp: 'order',
      _mt: 'detail',
      id
    }
  })
}

export function shipOrder(id) {
  return request({
    method: 'post',
    params: {
      _gp: 'order',
      _mt: 'ship',
      id
    }
  })
}
