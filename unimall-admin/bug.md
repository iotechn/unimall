vue3注意：
1.路由不能使用动态的 例如parseJson方法
2.path 插件不能用
3.element ui组件需要更改
4.solt需要更改
5.path插件不能使用
6.Vue3：v-if的优先级比v-for高。这意味着在Vue 3中，如果v-if和v-for同时出现在同一个元素上，v-if会先执行。这可能导致问题，因为v-if执行时可能还没有访问到v-for循环中的变量。 例如菜单中使用出现下级显示不出来问题
