import request from '@/utils/request'
import Qs from 'qs'

export function listMerchant(query) {
  return request({
    method: 'get',
    params: {
      _gp: 'admin.merchant',
      _mt: 'getMerchant',
      ...query
    }
  })
}

export function updateMerchant(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.merchant',
      _mt: 'updateMerchant',
      ...data
    })
  })
}
