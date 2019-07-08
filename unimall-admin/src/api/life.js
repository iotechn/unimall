import request from '@/utils/request'

export function listLife(query) {
  return request({
    method: 'get',
    params: {
      _gp: 'life',
      _mt: 'list',
      ...query
    }
  })
}


export function deleteLife(id) {
  return request({
    method: 'post',
    params: {
      _gp: 'life',
      _mt: 'delete',
      lifeId : id
    }
  })
}

export function pushLife(id,status) {
  return request({
    method: 'post',
    params: {
      _gp: 'life',
      _mt: 'push',
      lifeId : id,
      status
    }
  })
}
