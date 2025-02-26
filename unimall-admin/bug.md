vue3注意：
1. *路由不能使用动态的 例如parseJson方法
2. path 插件不能用
3. element ui组件需要更改
4. solt需要更改
5. path插件不能使用
6. *Vue3：v-if的优先级比v-for高。这意味着在Vue 3中，如果v-if和v-for同时出现在同一个元素上，v-if会先执行。这可能导致问题，因为v-if执行时可能还没有访问到v-for循环中的变量。 例如菜单中使用出现下级显示不出来问题
7. *i18n取值结构发生变化，例如 i18n.t = i18n.global.t;
8. *vue2通配符路由* 需要改成 path":"/:pathMatch(.*)*  否则permission.js文件 路由权限会抛异常 进而执行FedLogOut方法导致刷新后 删除token
9. **路由跳转后到每个页面后，这个页面不能有报错信息（包括Vue warn），否则路由跳转会失效
10. 在根template下不能写注释，否则页面加载不出来 可能与vite.config.js配置有关
11. Export2Excel.js需要引入file-saver插件
12. 通过<router-link to="/">跳转首页，需要配置路由redirect: '/dashboard'


element plus注意
1. el-date-picker  default-time需要去掉
2. el-button 使用:icon="Search" Search需要单独引入
3. $notify替换ElNotification单独引入
4. el-form-item 下能直接跟template标签，否则渲染不出来





升级需要处理问题
页面样式调整
