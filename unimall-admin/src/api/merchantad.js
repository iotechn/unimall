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

export function getImageColor(canvas, img) {
  canvas.width = img.width
  canvas.height = img.height

  var context = canvas.getContext('2d')

  context.drawImage(img, 0, 0)

  // 获取像素数据
  var data = context.getImageData(0, 0, img.width, img.height).data

  var r, g, b
  r = g = b = 0
  // 取所有像素的平均值
  for (var row = 0; row < img.height; row++) {
    for (var col = 0; col < img.width; col++) {
      r += data[((img.width * row) + col) * 4]
      g += data[((img.width * row) + col) * 4 + 1]
      b += data[((img.width * row) + col) * 4 + 2]
    }
  }
  // 求取平均值
  r /= (img.width * img.height)
  g /= (img.width * img.height)
  b /= (img.width * img.height)

  // 将最终的值取整
  r = Math.round(r)
  g = Math.round(g)
  b = Math.round(b)

  return 'rgb(' + r + ',' + g + ',' + b + ')'
}

