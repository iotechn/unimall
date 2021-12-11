import request from '@/utils/request'
import Qs from 'qs'

export function changePassword(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin',
      _mt: 'newPassword',
      ...data
    })
  })
}
