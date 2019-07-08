import request from '@/utils/request'

export function info() {
  return request({
    method: 'get',
    params: {
      _gp: 'dashboard',
      _mt: 'all'
    }
  })
}
