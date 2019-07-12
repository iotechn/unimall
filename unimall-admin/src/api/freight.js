import request from '@/utils/request'

export function listFreight(query) {
  return request({
    method: 'get',
    params: {
      _gp: 'admin.freight',
      _mt: 'getAllFreightTemplate'
    }
  })
}
