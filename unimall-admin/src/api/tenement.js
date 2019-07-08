import request from '@/utils/request'
import Qs from 'qs'

export function listTenement() {
  return request({
    method: 'get',
    params: {
      _gp: 'tenement',
      _mt: 'getTenementList',
    },
  })
}


