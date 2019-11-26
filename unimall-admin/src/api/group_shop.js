import request from '@/utils/request'
import Qs from 'qs'

export function queryGroupShop(query) {
  return request({
    method: 'get',
    params: {
      _gp: 'admin.groupShop',
      _mt: 'queryGroupShop',
      ...query
    }
  })
}

export function editGroupShop(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.groupShop',
      _mt: 'editGroupShopSpu',
      ...data
    })
  })
}

export function deleteGroupShop(id) {
  return request({
    method: 'post',
    params: {
      _gp: 'admin.groupShop',
      _mt: 'deleteGroupShopSpu',
      id: id
    }
  })
}

export function createGroupShop(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.groupShop',
      _mt: 'addGroupShopSpu',
      ...data
    })
  })
}
