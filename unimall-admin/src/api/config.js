import request from '@/utils/request'
import Qs from 'qs'

export function save(configs, prefix) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.sysconfig',
      _mt: 'create',
      configs,
      prefix
    })
  })
}

export function getData() {
  return request({
    method: 'get',
    data: Qs.stringify({
      _gp: 'admin.sysconfig',
      _mt: 'getData'
    })
  })
}
