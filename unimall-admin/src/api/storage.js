import request from '@/utils/request'
const uploadPath = process.env.HOST + '/upload/admin'
const uploadLocalPath = process.env.HOST + '/upload/local'
export { uploadPath, uploadLocalPath }

export function createStorage(data) {
  return request({
    url: uploadPath,
    method: 'post',
    data
  })
}
