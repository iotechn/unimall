<template>
  <div class="tags-view-container">
    <scroll-pane ref="scrollPaneRef" class="tags-view-wrapper">
      <router-link
        v-for="tag in visitedViews"
        ref="tagRefs"
        :class="isActive(tag) ? 'active' : ''"
        :to="{ path: tag.path, query: tag.query, fullPath: tag.fullPath }"
        :key="tag.path"
        tag="span"
        class="tags-view-item"
        @click.middle.prevent="closeSelectedTag(tag)"
        @contextmenu.prevent="openMenu(tag, $event)"
      >
        {{ generateTitle(tag.title) }}
        <span
          class="el-icon-close"
          @click.prevent.stop="closeSelectedTag(tag)"
        />
      </router-link>
    </scroll-pane>
    <ul
      v-show="visible"
      :style="{ left: left + 'px', top: top + 'px' }"
      class="contextmenu"
    >
      <li @click="refreshSelectedTag(selectedTag)">
        {{ $t('tagsView.refresh') }}
      </li>
      <li @click="closeSelectedTag(selectedTag)">{{ $t('tagsView.close') }}</li>
      <li @click="closeOthersTags">{{ $t('tagsView.closeOthers') }}</li>
      <li @click="closeAllTags">{{ $t('tagsView.closeAll') }}</li>
    </ul>
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted, nextTick } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useStore } from 'vuex'
import ScrollPane from '@/components/ScrollPane'
import { generateTitle } from '@/utils/i18n'

// 引入 i18n
import { useI18n } from 'vue-i18n'
const { t: $t } = useI18n()

// 组件引用
const scrollPaneRef = ref(null)
const tagRefs = ref([])

// 响应式数据
const visible = ref(false)
const top = ref(0)
const left = ref(0)
const selectedTag = ref({})

// 获取路由和路由实例
const route = useRoute()
const router = useRouter()

// 获取 store 实例
const store = useStore()

// 计算属性
const visitedViews = computed(() => store.state.tagsView.visitedViews)

// 方法
const isActive = (routeObj) => routeObj.path === route.path

const addViewTags = () => {
  const { name } = route
  if (name) {
    store.dispatch('addView', route)
  }
  return false
}

const moveToCurrentTag = () => {
  nextTick(() => {
    for (const tag of tagRefs.value) {
      if (tag.to.path === route.path) {
        nextTick(() => {
          scrollPaneRef.value.moveToTarget(tag)
        })

        // when query is different then update
        if (tag.to.fullPath !== route.fullPath) {
          store.dispatch('updateVisitedView', route)
        }

        break
      }
    }
  })
}

const refreshSelectedTag = (view) => {
  store.dispatch('delCachedView', view).then(() => {
    const { fullPath } = view
    nextTick(() => {
      router.replace({
        path: '/redirect' + fullPath,
      })
    })
  })
}

const closeSelectedTag = (view) => {
  store.dispatch('delView', view).then(({ visitedViews }) => {
    if (isActive(view)) {
      const latestView = visitedViews.slice(-1)[0]
      if (latestView) {
        router.push(latestView)
      } else {
        router.push('/')
      }
    }
  })
}

const closeOthersTags = () => {
  router.push(selectedTag.value)
  store.dispatch('delOthersViews', selectedTag.value).then(() => {
    moveToCurrentTag()
  })
}

const closeAllTags = () => {
  store.dispatch('delAllViews')
  router.push('/')
}

const openMenu = (tag, e) => {
  const menuMinWidth = 105
  const offsetLeft = e.target.getBoundingClientRect().left // container margin left
  const offsetWidth = e.target.offsetWidth // container width
  const maxLeft = offsetWidth - menuMinWidth // left boundary
  const leftValue = e.clientX - offsetLeft + 15 // 15: margin right

  if (leftValue > maxLeft) {
    left.value = maxLeft
  } else {
    left.value = leftValue
  }
  top.value = e.clientY

  visible.value = true
  selectedTag.value = tag
}

const closeMenu = () => {
  visible.value = false
}

// 监听路由变化
watch(route, () => {
  addViewTags()
  moveToCurrentTag()
})

// 监听 visible 变化
watch(visible, (value) => {
  if (value) {
    document.body.addEventListener('click', closeMenu)
  } else {
    document.body.removeEventListener('click', closeMenu)
  }
})

// 挂载时执行
onMounted(() => {
  addViewTags()
})
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.tags-view-container {
  height: 34px;
  width: 100%;
  background: #fff;
  border-bottom: 1px solid #d8dce5;
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.12), 0 0 3px 0 rgba(0, 0, 0, 0.04);
  .tags-view-wrapper {
    .tags-view-item {
      display: inline-block;
      position: relative;
      cursor: pointer;
      height: 26px;
      line-height: 26px;
      border: 1px solid #d8dce5;
      color: #495060;
      background: #fff;
      padding: 0 8px;
      font-size: 12px;
      margin-left: 5px;
      margin-top: 4px;
      &:first-of-type {
        margin-left: 15px;
      }
      &:last-of-type {
        margin-right: 15px;
      }
      &.active {
        background-color: #42b983;
        color: #fff;
        border-color: #42b983;
        &::before {
          content: '';
          background: #fff;
          display: inline-block;
          width: 8px;
          height: 8px;
          border-radius: 50%;
          position: relative;
          margin-right: 2px;
        }
      }
    }
  }
  .contextmenu {
    margin: 0;
    background: #fff;
    z-index: 100;
    position: absolute;
    list-style-type: none;
    padding: 5px 0;
    border-radius: 4px;
    font-size: 12px;
    font-weight: 400;
    color: #333;
    box-shadow: 2px 2px 3px 0 rgba(0, 0, 0, 0.3);
    li {
      margin: 0;
      padding: 7px 16px;
      cursor: pointer;
      &:hover {
        background: #eee;
      }
    }
  }
}
</style>

<style rel="stylesheet/scss" lang="scss">
//reset element css of el-icon-close
.tags-view-wrapper {
  .tags-view-item {
    .el-icon-close {
      width: 16px;
      height: 16px;
      vertical-align: 2px;
      border-radius: 50%;
      text-align: center;
      transition: all 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);
      transform-origin: 100% 50%;
      &:before {
        transform: scale(0.6);
        display: inline-block;
        vertical-align: -3px;
      }
      &:hover {
        background-color: #b4bccc;
        color: #fff;
      }
    }
  }
}
</style>
