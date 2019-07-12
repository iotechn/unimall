import request from '@/utils/request'

export function fetchList(query) {
  return request({
    method: 'get',
    params: {
      _gp: 'admin.user',
      _mt: 'getUser',
      ...query
    }
  })
}

export function activeUser(data) {
  return request({
    method: 'post',
    params: {
      _gp: 'admin.user',
      _mt: 'updateUser',
      user: JSON.stringify(data)
    }
  })
}

export function createUser(data) {
  return request({
    method: 'post',
    params: {
      _gp: 'admin.user',
      _mt: 'addUser',
      user: JSON.stringify(data)
    }
  })
}

export function updateUser(data) {
  return request({
    method: 'post',
    params: {
      _gp: 'admin.user',
      _mt: 'updateUser',
      user: JSON.stringify(data)
    }
  })
}

export function deleteUser(id, nickname) {
  return request({
    method: 'post',
    params: {
      _gp: 'admin.user',
      _mt: 'deleteUser',
      id: id,
      nickname: nickname
    }
  })
}

// ==========================下面的是冗余的==========================
export function fetchUserOrgApplyList(queryCondition, status) {
  return request({
    method: 'get',
    params: {
      _gp: 'user',
      _mt: 'getUserOrgApplyList',
      ...queryCondition
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
