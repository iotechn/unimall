import request from '@/utils/request'
import Qs from 'qs'

export function listTable() {
  return request({
    method: 'get',
    params: {
      _gp: 'admin.generator',
      _mt: 'listTable'
    }
  })
}

export function listColumns(tableName) {
  return request({
    method: 'get',
    params: {
      _gp: 'admin.generator',
      _mt: 'loadColumns',
      tableName
    }
  })
}

export function generate(dto) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.generator',
      _mt: 'generate',
      generateDTO: JSON.stringify(dto)
    })
  })
}
