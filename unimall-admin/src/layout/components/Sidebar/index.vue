<template>
  <el-scrollbar wrap-class="scrollbar-wrapper">
    <el-menu
      :show-timeout="200"
      :default-active="$route.path"
      :collapse="isCollapse"
      mode="vertical"
      background-color="#304156"
      text-color="#bfcbd9"
      active-text-color="#409EFF"
    >
      <SidebarItem
        v-for="route in permissionRouters"
        :key="route.path"
        :item="route"
        :base-path="route.path"
      />
    </el-menu>
  </el-scrollbar>
</template>

<script setup>
import { computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { useStore } from 'vuex'
import SidebarItem from './SidebarItem'

// 引入 Vuex 存储实例
const store = useStore()
// 获取当前路由
const route = useRoute()

// 使用计算属性获取 Vuex 中的状态
const permissionRouters = computed(() => store.getters['permission_routers'])
const sidebar = computed(() => store.getters['sidebar'])

// 计算 isCollapse
const isCollapse = computed(() => !sidebar.value.opened)

// 模拟 mounted 钩子
onMounted(() => {})
</script>
