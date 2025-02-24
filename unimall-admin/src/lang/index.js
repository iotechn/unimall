// main.js 或者 i18n.js 文件，根据你的项目结构选择合适的位置

import { createI18n } from 'vue-i18n';
import Cookies from 'js-cookie';
import en from 'element-plus/dist/locale/en.mjs'; // element-ui lang
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'; // element-ui lang
import enLocale from './en';
import zhLocale from './zh';

// 定义语言消息对象
const messages = {
  en: {
    ...enLocale,
    ...en
  },
  zh: {
    ...zhLocale,
    ...zhCn
  }
};

// 创建 i18n 实例
const i18n = createI18n({
  // 使用 Composition API 模式
  legacy: false, 
  // 设置当前语言
  locale: Cookies.get('language') || 'zh', 
  // 设置语言消息对象
  messages
});
console.log(i18n,"i18n")
i18n.t = i18n.global.t;
i18n.te = i18n.global.te;
export default i18n;