import request from '@/utils/request'
import Qs from 'qs'

export function listFreight() {
  return request({
    method: 'get',
    params: {
      _gp: 'admin.freight',
      _mt: 'list',
      adminId: 'guest'
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
      carriageDOList: JSON.stringify(query.carriageDOList)
    })
  })
}

export function deleteFreight(query) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.freight',
      _mt: 'delete',
      id: query
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
      carriageDOList: JSON.stringify(query.carriageDOList)
    })
  })
}
