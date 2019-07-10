import request from '@/utils/request'
import Qs from 'qs'

export function listCategory(query) {
  return request({
    method: 'get',
    params: {
      _gp: 'category',
      _mt: 'list',
      ...query
    }
  })
}

export function listCatL1() {
  return request({
    method: 'get',
    params: {
      _gp: 'category',
      _mt: 'parents'
    }
  })
}

export function createCategory(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'category',
      _mt: 'create',
      category: JSON.stringify(data)
    })
  })
}

export function updateCategory(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'category',
      _mt: 'update',
      category: JSON.stringify(data)
    })
  })
}

export function deleteCategory(id) {
  return request({
    method: 'post',
    params: {
      _gp: 'category',
      _mt: 'delete',
      categoryId: id
    }
  })
}
