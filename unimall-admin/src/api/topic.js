import request from '@/utils/request'
import Qs from 'qs'

export function listTopic(query) {
  return request({
    method: 'get',
    params: {
      _gp: 'article',
      _mt: 'list',
      ...query
    }
  })
}

export function createTopic(data) {
  return request({
    method: 'post',
    data : Qs.stringify({
      _gp: 'article',
      _mt: 'create',
      article: JSON.stringify(data)
    })
  })
}

export function updateTopic(data) {
  return request({
    method: 'post',
    data : Qs.stringify({
      _gp: 'article',
      _mt: 'update',
      article: JSON.stringify(data)
    })
  })
}

export function deleteTopic(id) {
  return request({
    method: 'post',
    params: {
      _gp: 'article',
      _mt: 'delete',
      articleId: id
    }
  })
}


export function activeTopic(params) {
  return request({
    method: 'post',
    params: {
      _gp: 'article',
      _mt: 'status',
      ...params
    }
  })
}
