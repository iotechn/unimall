import request from '@/utils/request'
import Qs from 'qs'

export function list(query) {
  return request({
    method: 'get',
    params: {
      _gp: 'qq',
      _mt: 'list',
      ...query
    },
  })
}

export function create(group) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'qq',
      _mt: 'create',
      group: JSON.stringify(group)
    })
  })
}

export function update(group) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'qq',
      _mt: 'update',
      group: JSON.stringify(group)
    })
  })
}

export function del(id) {
  return request({
    method: 'post',
    params: {
      _gp: 'qq',
      _mt: 'delete',
      id
    }
  })
}



