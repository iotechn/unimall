import request from '@/utils/request'
import Qs from 'qs'

export function getSpuBigTree() {
  return request({
    method: 'get',
    params: {
      _gp: 'admin.product',
      _mt: 'getSpuBigTree'
    }
  })
}

/**
 * 通过CategoryId， 列举出商品Id和商品标题
 * @param {int} categoryId
 */
export function listProductIdAndTitleByCategoryId(categoryId) {
  return request({
    method: 'get',
    params: {
      _gp: 'admin.product',
      _mt: 'listByCategory',
      categoryId
    }
  })
}

export function listProduct(query) {
  return request({
    method: 'get',
    params: {
      _gp: 'admin.product',
      _mt: 'list',
      ...query
    }
  })
}

export function editProduct(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.product',
      _mt: 'edit',
      spuDTO: JSON.stringify(data)
    })
  })
}

export function deleteProduct(id) {
  return request({
    method: 'post',
    params: {
      _gp: 'admin.product',
      _mt: 'delete',
      spuId: id
    }
  })
}

export function batchDeleteProduct(ids) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.product',
      _mt: 'batchDelete',
      ids: JSON.stringify(ids)
    })
  })
}

export function createProduct(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.product',
      _mt: 'create',
      spuDTO: JSON.stringify(data)
    })
  })
}

export function detailProduct(id) {
  return request({
    method: 'get',
    params: {
      _gp: 'admin.product',
      _mt: 'detail',
      spuId: id
    }
  })
}

export function freezeOrActivtion(id, status) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.product',
      _mt: 'freezeOrActivation',
      spuId: id,
      status: status
    })
  })
}
