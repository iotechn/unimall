import request from '@/utils/request'
import Qs from 'qs'

export function vipRichtext(query) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.rule',
      _mt: 'edit',
      ...query
    })
  })
}

export function getVipRichtext(query) {
  return request({
    method: 'get',
    params: {
      _gp: 'admin.rule',
      _mt: 'list',
      ...query
    }
  })
}
