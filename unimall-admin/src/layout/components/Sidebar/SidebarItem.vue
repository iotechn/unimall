<template>
  <div v-if="!item.hidden && item.children" class="menu-wrapper">
    <template
      v-if="
        hasOneShowingChild(item.children, item) &&
        (!onlyOneChild.children || onlyOneChild.noShowingChildren) &&
        !item.alwaysShow
      "
    >
      <app-link :to="resolvePath(onlyOneChild.path)">
        <el-menu-item
          :index="resolvePath(onlyOneChild.path)"
          :class="{ 'submenu-title-noDropdown': !isNest }"
        >
          <item
            v-if="onlyOneChild.meta"
            :icon="onlyOneChild.meta.icon || item.meta.icon"
            :title="generateTitle(onlyOneChild.meta.title)"
          />
        </el-menu-item>
      </app-link>
    </template>
    <el-sub-menu
      ref="submenuRef"
      v-if="item.path"
      :index="resolvePath(item.path)"
    >
      <template #title>
        <item
          v-if="item.meta"
          :icon="item.meta.icon"
          :title="generateTitle(item.meta.title)"
        />
      </template>
      <template v-for="child in item.children">
        <div v-if="!child.hidden">
          <SidebarItem
            v-if="child.children && child.children.length > 0"
            :is-nest="true"
            :item="child"
            :key="child.path"
            :base-path="resolvePath(child.path)"
            class="nest-menu"
          />
          <app-link v-else :to="resolvePath(child.path)" :key="child.name">
            <el-menu-item :index="resolvePath(child.path)">
              <item
                v-if="child.meta"
                :icon="child.meta.icon"
                :title="generateTitle(child.meta.title)"
              />
            </el-menu-item>
          </app-link>
        </div>
      </template>
    </el-sub-menu>
  </div>
</template>

<script setup>
import path from 'path-browserify'
import { ref } from 'vue'
// import path from 'path'
import { generateTitle } from '@/utils/i18n'
import { isExternal } from '@/utils'
import Item from './Item'
import AppLink from './Link'
import FixiOSBug from './FixiOSBug'

// 定义 props
const props = defineProps({
  // route object
  item: {
    type: Object,
    required: true,
  },
  isNest: {
    type: Boolean,
    default: false,
  },
  basePath: {
    type: String,
    default: '',
  },
})

// 定义响应式数据
const onlyOneChild = ref(null)
const submenuRef = ref(null)

// 定义方法
const hasOneShowingChild = (children, parent) => {
  const showingChildren = children.filter((item) => {
    if (item.hidden) {
      return false
    } else {
      // Temp set(will be used if only has one showing child)
      onlyOneChild.value = item
      return true
    }
  })

  // When there is only one child router, the child router is displayed by default
  if (showingChildren.length === 1) {
    return true
  }

  // Show parent if there are no child router to display
  if (showingChildren.length === 0) {
    onlyOneChild.value = { ...parent, path: '', noShowingChildren: true }
    return true
  }

  return false
}

// const resolvePath = (routePath) => {
//   if (isExternalLink(routePath)) {
//     return routePath
//   }
//   return path.resolve(props.basePath, routePath)
// }
const resolvePath = (routePath) => {
  if (isExternalLink(routePath)) {
    return routePath
  }
  // 使用字符串拼接替代 path.resolve
  let resolvedPath = props.basePath
  if (!resolvedPath.endsWith('/')) {
    resolvedPath += '/'
  }
  if (routePath.startsWith('/')) {
    routePath = routePath.slice(1)
  }
  console.log(resolvedPath + routePath, 'aaaa')
  return resolvedPath + routePath
}

const isExternalLink = (routePath) => {
  return isExternal(routePath)
}
</script>
