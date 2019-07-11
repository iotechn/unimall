import request from '@/utils/request'
import Qs from 'qs'

export function listAd(query) {
  return request({
    method: 'get',
    params: {
      _gp: 'admin.advertisement',
      _mt: 'queryAdvertisement',
      ...query
    }
  })
}

export function createAd(data) {
  return request({
    method: 'post',
    data: Qs.stringify({
      _gp: 'admin.advertisement',
      _mt: 'addAdvertisement',
      ...data
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
      _gp: 'admin.advertisement',
      _mt: 'updateAdvertisement',
      ...data,
      adId: data.id
    })
  })
}

export function deleteAd(id, adType) {
  return request({
    method: 'post',
    params: {
      _gp: 'admin.advertisement',
      _mt: 'deleteAdvertisement',
      adId: id,
      adType: adType
    }
  })
}
