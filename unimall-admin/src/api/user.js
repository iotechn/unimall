import request from '@/utils/request'
import Qs from 'qs'

export function listUser(query) {
  return request({
    method: 'get',
    params: {
      _gp: 'admin.user',
      _mt: 'list',
      ...query
    }
  })
}

export function activeUser(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.user',
      _mt: 'editStatus',
      userId: data.id,
      status: data.status
    })
  })
}

export function createUser(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.user',
      _mt: 'create',
      user: JSON.stringify(data)
    })
  })
}

export function updateUser(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.user',
      _mt: 'edit',
      user: JSON.stringify(data)
    })
  })
}

export function deleteUser(id, nickname) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.user',
      _mt: 'delete',
      id: id
    })
  })
}
