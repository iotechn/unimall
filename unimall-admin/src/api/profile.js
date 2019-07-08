import request from '@/utils/request'

export function changePassword(data) {
  return request({
    method: 'post',
    params: {
      _gp: 'admin',
      _mt: 'newPassword',
      ...data
    }
  })
}
