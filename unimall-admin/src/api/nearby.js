import request from '@/utils/request'
import Qs from 'qs'

export function detail() {
  return request({
    method: 'get',
    params: {
      _gp: 'nearby',
      _mt: 'detail'
    }
  })
}

export function upsert(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'nearby',
      _mt: 'upsert',
      nearby: JSON.stringify(data)
    })
  })
}

export function formlist(query) {
  return request({
    method: 'get',
    params: {
      _gp: 'nearby',
      _mt: 'formlist',
      ...query
    }
  })
}

export function formcomplete(makaId) {
  return request({
    method: 'get',
    params: {
      _gp: 'nearby',
      _mt: 'formComplete',
      makaId
    }
  })
}

