import store from "@/store";
import { lowerCase } from "@/utils/index";

function checkPermission(el, binding) {
	// 获取绑定的值，此处为权限
	const value = lowerCase(binding.value);
	const auths = store.getters.buttons || [];
	if (!auths.includes(value)) {
		el.parentNode.removeChild(el);
	}
}

export default {
	// 在绑定元素的父组件被挂载后调用
	mounted(el, binding) {
		checkPermission(el, binding);
	},
	// 在包含组件的 VNode 及其子组件的 VNode 更新后调用
	update(el, binding) {
		checkPermission(el, binding);
	}
};