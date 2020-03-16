import request from '@/utils/request'
import Qs from 'qs'

export function list${doName}(query) {
  return request({
    method: 'get',
    params: {
      _gp: 'admin.${serviceLowCaseName}',
      _mt: 'list',
      ...query
    }
  })
}

export function create${doName}(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.${serviceLowCaseName}',
      _mt: 'create',
      ...data
    })
  })
}

export function update${doName}(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.${serviceLowCaseName}',
      _mt: 'edit',
      ...data
    })
  })
}

export function delete${doName}(id) {
  return request({
    method: 'post',
    params: {
      _gp: 'admin.${serviceLowCaseName}',
      _mt: 'delete',
      id: id
    }
  })
}
