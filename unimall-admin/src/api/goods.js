import request from '@/utils/request'
import Qs from 'qs'

export function listGoods(query) {
  return request({
    method: 'get',
    params: {
      _gp: 'goods',
      _mt: 'list',
      ...query
    }
  })
}

export function deleteGoods(id) {
  return request({
    method: 'post',
    params: {
      _gp: 'goods',
      _mt: 'delete',
      goodsId : id
    }
  })
}

export function publishGoods(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp : 'goods',
      _mt : 'create',
      allinone : JSON.stringify(data)
    })
  })
}

export function detailGoods(id) {
  return request({
    method: 'get',
    params: {
      _gp : 'goods',
      _mt : 'detail',
      goodsId: id
    }
  })
}

export function editGoods(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'goods',
      _mt: 'update',
      allinone: JSON.stringify(data)
    })
  })
}

export function listCatAndBrand() {
  return request({
    method: 'get',
    params: {
      _gp: 'goods',
      _mt: 'categories'
    }
  })
}
