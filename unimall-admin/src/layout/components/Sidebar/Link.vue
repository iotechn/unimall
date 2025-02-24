<template>
  <!-- eslint-disable vue/require-component-is -->
  <component :is="linkProps(to).is" v-bind="linkProps(to)">
    <slot />
  </component>
</template>

<script setup>
import { defineProps } from 'vue'
import { isExternal } from '@/utils'

// 定义组件接收的 props
const props = defineProps({
  to: {
    type: String,
    required: true,
  },
})

// 判断是否为外部链接的方法
const isExternalLink = (routePath) => {
  return isExternal(routePath)
}

// 根据链接类型返回不同的组件属性
const linkProps = (url) => {
  if (isExternalLink(url)) {
    return {
      is: 'a',
      href: url,
      target: '_blank',
      rel: 'noopener',
    }
  }
  return {
    is: 'router-link',
    to: url,
  }
}
</script>
