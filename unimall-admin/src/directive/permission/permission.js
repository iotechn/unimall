import store from "@/store";

function checkPermission(el, binding) {
	const { value } = binding
    const perms = store.getters && store.getters.perms

    if (value && value instanceof Array && value.length > 0) {
      const permissions = value

      var hasPermission = false

      if (perms.indexOf('*') >= 0) {
        hasPermission = true
      } else {
        hasPermission = perms.some(perm => {
          return permissions.includes(perm)
        })
      }
      console.log(hasPermission,"122222222222222222");

      if (!hasPermission) {
        el.parentNode && el.parentNode.removeChild(el)
      }
    } else {
      throw new Error(`need perms! Like v-permission="['GET /aaa','POST /bbb']"`)
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