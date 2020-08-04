import request from '@/utils/request'
import Qs from 'qs'

export function listGroupShop(query) {
  return request({
    method: 'get',
    params: {
      _gp: 'admin.groupshop',
      _mt: 'list',
      ...query
    }
  })
}

export function editGroupShop(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.groupshop',
      _mt: 'edit',
      ...data
    })
  })
}

export function deleteGroupShop(id) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.groupshop',
      _mt: 'delete',
      id: id
    })
  })
}

export function createGroupShop(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.groupshop',
      _mt: 'create',
      ...data
    })
  })
}
