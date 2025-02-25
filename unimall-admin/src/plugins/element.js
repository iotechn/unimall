import ElementPlus from "element-plus";
import "element-plus/dist/index.css";
import locale from 'element-plus/dist/locale/zh-cn.mjs'

// 注册全部的svg图标
import elementIcons from "@/plugins/svgicon";
import "virtual:svg-icons-register";

export default app => {
	app
		.use(ElementPlus, { locale })
		.use(elementIcons); // 全局注册element svg图标
};
