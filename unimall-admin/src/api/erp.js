import request from '@/utils/request'
import Qs from 'qs'

export function syncCategory() {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.erp',
      _mt: 'syncCategory'
    })
  })
}

export function syncProduct() {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.erp',
      _mt: 'syncProduct'
    })
  })
}
