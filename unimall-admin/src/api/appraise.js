import request from '@/utils/request'
import Qs from 'qs'

export function listAppraise(query) {
  return request({
    method: 'get',
    params: {
      _gp: 'admin.appraise',
      _mt: 'list',
      ...query
    }
  })
}

export function deleteAppraise(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.appraise',
      _mt: 'delete',
      ...data
    })
  })
}
