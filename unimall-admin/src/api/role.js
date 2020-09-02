import request from '@/utils/request'
import Qs from 'qs'

export function listRole(query) {
  return request({
    method: 'get',
    params: {
      _gp: 'admin.role',
      _mt: 'list',
      ...query
    }
  })
}

export function createRole(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.role',
      _mt: 'create',
      role: JSON.stringify(data)
    })
  })
}

export function updateRole(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.role',
      _mt: 'update',
      role: JSON.stringify(data)
    })
  })
}

export function deleteRole(id) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.role',
      _mt: 'delete',
      roleId: id
    })
  })
}

export function getPermission(query) {
  return request({
    method: 'get',
    params: {
      _gp: 'admin.role',
      _mt: 'permissionList',
      ...query
    }
  })
}

export function updatePermission(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.role',
      _mt: 'permissionSet',
      roleSetPermissionDTO: JSON.stringify(data)
    })
  })
}

export function roleOptions(query) {
  return request({
    method: 'get',
    params: {
      _gp: 'admin.role',
      _mt: 'options'
    }
  })
}
