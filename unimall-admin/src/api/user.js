import request from '@/utils/request'

export function fetchList(query) {
  return request({
    method: 'get',
    params: {
      _gp: 'user',
      _mt: 'list',
      ...query
    }
  })
}


export function activeUser(id, status) {
  return request({
    method: 'post',
    params: {
      _gp: 'user',
      _mt: 'status',
      userId: id,
      status: status
    }
  })
}


export function fetchUserOrgApplyList(queryCondition,status) {
  return request({
    method: 'get',
    params: {
      _gp: 'user',
      _mt: 'getUserOrgApplyList',
      ...queryCondition,
    }
  })
}


export function auditUserOrgApply(id) {
  return request({
    method: 'post',
    params: {
      _gp: 'user',
      _mt: 'audit',
      applyId: id
    }
  })
}

export function oneKeyUserOrgApply(id) {
  return request({
    method: 'post',
    params: {
      _gp: 'user',
      _mt: 'oneKey',
      applyId: id
    }
  })
}
