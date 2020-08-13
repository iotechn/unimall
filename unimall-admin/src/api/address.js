import request from '@/utils/request'

export function listAddress(query) {
  return request({
    method: 'get',
    params: {
      _gp: 'admin.address',
      _mt: 'list',
      ...query
    }
  })
}
