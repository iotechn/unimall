import request from '@/utils/request'
import Qs from 'qs'

export function spuTree() {
  return request({
    method: 'get',
    params: {
      _gp: 'admin.goods',
      _mt: 'getSpuBigTree'
    }
  })
}

export function listGoods(query) {
  return request({
    method: 'get',
    params: {
      _gp: 'admin.goods',
      _mt: 'list',
      ...query
    }
  })
}

export function editGoods(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.goods',
      _mt: 'edit',
      spuDTO: JSON.stringify(data)
    })
  })
}

export function deleteGoods(id) {
  return request({
    method: 'post',
    params: {
      _gp: 'admin.goods',
      _mt: 'delete',
      spuId: id
    }
  })
}

export function batchDeleteGoods(ids) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.goods',
      _mt: 'batchDelete',
      ids: JSON.stringify(ids)
    })
  })
}

export function createGoods(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.goods',
      _mt: 'create',
      spuDTO: JSON.stringify(data)
    })
  })
}

export function detailGoods(id) {
  return request({
    method: 'get',
    params: {
      _gp: 'admin.goods',
      _mt: 'detail',
      spuId: id
    }
  })
}

export function freezeOrActivtion(id, status) {
  return request({
    method: 'post',
    params: {
      _gp: 'admin.goods',
      _mt: 'freezeOrActivation',
      spuId: id,
      status: status
    }
  })
}
