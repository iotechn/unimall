import { createApp } from "vue";
import App from "./App.vue";
import store from "./store";
import router from "./router";

// 导入权限控制模块
import "./permission";
import "@/styles/index.scss";

import Cookies from 'js-cookie'
import i18n from './lang' // Internationalization
import ElementPlus from 'element-plus' // 完整引入Element Plus
// element
import installElementPlus from "./plugins/element";
// directives
import installDirective from "@/directives";
// filter
import installFilter from "@/filters";

// 自定义表格工具组件
import RightToolbar from "@/components/RightToolbar";
// 分页组件
import Pagination from "@/components/Pagination";
// svg组件
import svgIcon from "@/components/SvgIcon/index.vue";
const app = createApp(App);
installElementPlus(app);
installDirective(app);
installFilter(app);
// 全局组件挂载
app.component("RightToolbar", RightToolbar);
app.component("Pagination", Pagination);
app.component("svg-icon", svgIcon);


app.use(ElementPlus, {
  size: Cookies.get('size') || 'medium', // set element-ui default size
  i18n: (key, value) => i18n.t(key, value)
})
app.use(i18n)
app
	.use(store)
	.use(router)
	.mount("#app");