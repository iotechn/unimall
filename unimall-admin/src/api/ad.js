import request from '@/utils/request'
import Qs from 'qs'

export function listAd(query) {
  return request({
    method: 'get',
    params: {
      _gp: 'ad',
      _mt: 'list',
      ...query
    }
  })
}

export function createAd(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'ad',
      _mt: 'create',
      ad: JSON.stringify(data)
    })
  })
}

// export function readAd(data) {
//   return request({
//     url: '/ad/read',
//     method: 'get',
//     data
//   })
// }

export function updateAd(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'ad',
      _mt: 'update',
      ad: JSON.stringify(data)
    })
  })
}

export function deleteAd(id) {
  return request({
    method: 'post',
    params: {
      _gp: 'ad',
      _mt: 'delete',
      adId: id
    }
  })
}
