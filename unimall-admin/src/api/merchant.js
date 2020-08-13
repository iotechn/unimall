import request from '@/utils/request'
import Qs from 'qs'

export function listMerchant(query) {
  return request({
    method: 'get',
    params: {
      _gp: 'admin.merchant',
      _mt: 'list',
      ...query
    }
  })
}

export function updateMerchant(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.merchant',
      _mt: 'createOrUpdate',
      ...data
    })
  })
}
