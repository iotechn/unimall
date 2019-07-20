import request from '@/utils/request'
import Qs from 'qs'

export function listFreight(query) {
  return request({
    method: 'get',
    params: {
      _gp: 'admin.freight',
      _mt: 'getAllFreightTemplate'
    }
  })
}

export function createFreight(query) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.freight',
      _mt: 'addFreightTemplate',
      ...query,
      freightTemplateCarriageDOList: JSON.stringify(query.freightTemplateCarriageDOList)
    })
  })
}

export function deleteFreight(query) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.freight',
      _mt: 'deleteFreightTemplate',
      templateId: query
    })
  })
}

export function updateFreight(query) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.freight',
      _mt: 'updateFreightTemplate',
      ...query,
      freightTemplateCarriageDOList: JSON.stringify(query.freightTemplateCarriageDOList)
    })
  })
}
