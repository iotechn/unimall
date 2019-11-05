import request from '@/utils/request'
import Qs from 'qs'

export function createAutoShip(param) {
  return request({
    method: 'post',
    params: {
      _gp: 'autoship',
      _mt: 'create',
      ...param
    }
  })
}

export function listAutoShip(query) {
  return request({
    method: 'post',
    params: {
      _gp: 'autoship',
      _mt: 'list',
      ...query
    }
  })
}

export function batchCreateAutoShip(list) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'autoship',
      _mt: 'batchCreate',
      list: JSON.stringify(list)
    })
  })
}

export function deleteAutoShip(id) {
  return request({
    method: 'post',
    params: {
      _gp: 'autoship',
      _mt: 'delete',
      cdkId: id
    }
  })
}

export function updateAutoShip(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'autoship',
      _mt: 'edit',
      ...data
    })
  })
}
