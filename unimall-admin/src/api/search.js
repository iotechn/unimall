import request from '@/utils/request'
import Qs from 'qs'

export function initSearchEngine() {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.search',
      _mt: 'init'
    })
  })
}

export function rebuildSearchEngine() {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.search',
      _mt: 'rebuild'
    })
  })
}

export function reloadPropertiesSearchEngine() {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.search',
      _mt: 'reloadProperties'
    })
  })
}
