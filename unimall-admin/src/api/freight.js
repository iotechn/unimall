import request from '@/utils/request'
import Qs from 'qs'

export function listFreight(query) {
  return request({
    method: 'get',
    params: {
      _gp: 'admin.freight',
      _mt: 'list'
    }
  })
}

export function createFreight(query) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.freight',
      _mt: 'create',
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
      _mt: 'delete',
      templateId: query
    })
  })
}

export function updateFreight(query) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.freight',
      _mt: 'edit',
      ...query,
      freightTemplateCarriageDOList: JSON.stringify(query.freightTemplateCarriageDOList)
    })
  })
}
