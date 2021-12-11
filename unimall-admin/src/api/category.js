import request from '@/utils/request'
import Qs from 'qs'

export function categorySecondLevelTree() {
  return request({
    method: 'get',
    params: {
      _gp: 'admin.category',
      _mt: 'categorySecondLevelTree'
    }
  })
}

export function categoryTree() {
  return request({
    method: 'get',
    params: {
      _gp: 'admin.category',
      _mt: 'categoryTree'
    }
  })
}

export function listCategory(query) {
  return request({
    method: 'get',
    params: {
      _gp: 'admin.category',
      _mt: 'list',
      ...query
    }
  })
}

export function createCategory(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.category',
      _mt: 'create',
      ...data
    })
  })
}

export function updateCategory(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.category',
      _mt: 'update',
      ...data
    })
  })
}

export function deleteCategory(id) {
  return request({
    method: 'post',
    params: {
      _gp: 'admin.category',
      _mt: 'delete',
      id: id
    }
  })
}
