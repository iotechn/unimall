import request from '@/utils/request'
import Qs from 'qs'

export function listAd(query) {
  return request({
    method: 'get',
    params: {
      _gp: 'nearby',
      _mt: 'adlist',
      ...query
    }
  })
}

export function createAd(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'nearby',
      _mt: 'adcreate',
      nearbyAd: JSON.stringify(data)
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
      _gp: 'nearby',
      _mt: 'adupdate',
      nearbyAd: JSON.stringify(data)
    })
  })
}

export function deleteAd(id) {
  return request({
    method: 'post',
    params: {
      _gp: 'nearby',
      _mt: 'addelete',
      adId: id
    }
  })
}
