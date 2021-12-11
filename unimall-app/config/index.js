let ENV_CONFIG
if (process.env.NODE_ENV === 'development') {
  // 开发环境
  ENV_CONFIG = require('./.env.dev.js')
} else {
  // 生产环境
  ENV_CONFIG = require('./.env.prod.js')
}
module.exports = ENV_CONFIG
