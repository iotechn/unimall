import request from '@/utils/request'
import Qs from 'qs'

export function listApply(query) {
  return request({
    method: 'get',
    params: {
      _gp: 'nearby',
      _mt: 'applylist',
      ...query
    },
  })
}

export function applydeal(applyId, status) {
  return request({
    method: 'post',
    params: {
      _gp: 'nearby',
      _mt: 'handle',
      applyId,
      status : status
    }
  })
}

export function apply(tenementCode) {
  return request({
    method: 'post',
    params: {
      _gp: 'nearby',
      _mt: 'apply',
      tenementCode
    }
  })
}

