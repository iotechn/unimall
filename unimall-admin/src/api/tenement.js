import request from '@/utils/request'

export function listTenement() {
  return request({
    method: 'get',
    params: {
      _gp: 'tenement',
      _mt: 'getTenementList'
    }
  })
}

