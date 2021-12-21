import request from '@/utils/request'
import Qs from 'qs'

export function getMerchant(query) {
  return request({
    method: 'get',
    params: {
      _gp: 'admin.merchant',
      _mt: 'detail',
      ...query
    }
  })
}

export function updateMerchant(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.merchant',
      _mt: 'edit',
      config: JSON.stringify(data)
    })
  })
}
