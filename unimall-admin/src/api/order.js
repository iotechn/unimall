import request from '@/utils/request'
import Qs from 'qs'

export function listOrder(query) {
  return request({
    method: 'get',
    params: {
      _gp: 'admin.order',
      _mt: 'list',
      ...query
    }
  })
}

export function detailOrder(orderId) {
  return request({
    method: 'get',
    params: {
      _gp: 'admin.order',
      _mt: 'detail',
      orderId
    }
  })
}

export function shipOrder(shipForm) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.order',
      _mt: 'ship',
      ...shipForm
    })
  })
}

export function refundOrder(refundForm) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.order',
      _mt: 'refund',
      ...refundForm
    })
  })
}

export function getExcelInfo(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.order',
      _mt: 'queryToExcel',
      ...data
    })
  })
}

export function getExcelStatistics(data) {
  return request({
    method: 'get',
    params: {
      _gp: 'admin.order',
      _mt: 'statistics',
      ...data
    }
  })
}

export function editAdminMono(orderId, level, mono) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.order',
      _mt: 'editAdminMono',
      orderId,
      level,
      mono
    })
  })
}
