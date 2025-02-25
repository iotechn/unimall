<template>
  <el-breadcrumb class="app-breadcrumb" separator="/">
    <transition-group name="breadcrumb">
      <el-breadcrumb-item v-for="(item, index) in levelList" :key="item.path">
        <span
          v-if="
            item.redirect === 'noredirect' || index === levelList.length - 1
          "
          class="no-redirect"
          >{{ generateTitle(item.meta.title) }}</span
        >
        <router-link v-else :to="item.redirect || item.path">{{
          generateTitle(item.meta.title)
        }}</router-link>
      </el-breadcrumb-item>
    </transition-group>
  </el-breadcrumb>
</template>

<script>
import { ref, watch } from 'vue'
import { generateTitle } from '@/utils/i18n'
import { compile } from 'path-to-regexp'
import { useRoute } from 'vue-router'

export default {
  setup() {
    // 使用 ref 来创建响应式数据
    const levelList = ref(null)

    // 引入路由
    const route = useRoute()

    // 监听路由变化
    watch(
      () => route,
      () => {
        getBreadcrumb()
      }
    )

    // 获取面包屑数据的方法
    const getBreadcrumb = () => {
      const { params } = route
      let matched = route.matched.filter((item) => {
        if (item.name) {
          // To solve this problem https://github.com/PanJiaChen/vue-element-admin/issues/561
          var toPath = compile(item.path)
          item.path = toPath(params)
          return true
        }
      })
      const first = matched[0]
      if (
        first &&
        first.name.trim().toLocaleLowerCase() !==
          'Dashboard'.toLocaleLowerCase()
      ) {
        matched = [{ path: '/dashboard', meta: { title: 'dashboard' } }].concat(
          matched
        )
      }
      levelList.value = matched
    }

    // 组件创建时调用获取面包屑数据的方法
    getBreadcrumb()

    return {
      levelList,
      generateTitle,
      getBreadcrumb,
    }
  },
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.app-breadcrumb.el-breadcrumb {
  display: inline-block;
  font-size: 14px;
  line-height: 50px;
  margin-left: 10px;
  .no-redirect {
    color: #97a8be;
    cursor: text;
  }
}
</style>
