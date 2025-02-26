<template>
  <section class="app-main">
    <transition name="fade-transform" mode="out-in">
      <keep-alive :include="cachedViews">
        <router-view :key="key" />
      </keep-alive>
    </transition>
  </section>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import { useStore } from 'vuex'

// 获取 Vuex 存储实例
const store = useStore()
// 获取当前路由信息
const route = useRoute()
console.log('22222222222222222')
// 计算 cachedViews，从 Vuex 中获取状态
const cachedViews = computed(() => store.state.tagsView.cachedViews)
// 计算 key，使用当前路由的 fullPath
const key = computed(() => route.fullPath)
console.log(cachedViews, key, 'qqqqqqq')
</script>

<style scoped>
.app-main {
  /*84 = navbar + tags-view = 50 +34 */
  min-height: calc(100vh - 94px);
  width: 100%;
  position: relative;
  overflow: hidden;
}
</style>
