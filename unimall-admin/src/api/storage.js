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

export function beforeFileUplod(file) {
  const isIMAGE = file.type === 'image/jpeg' || 'image/gif' || 'image/png' || 'image/jpg'
  const isLt1M = file.size / 1024 / 1024 < 1
  if (!isIMAGE) {
    this.$message.error('上传文件只能是图片格式!')
  }
  if (!isLt1M) {
    this.$message.error('上传文件大小不能超过 1MB!')
  }
  return isIMAGE && isLt1M
}
