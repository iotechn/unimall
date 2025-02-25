<template>
  <el-scrollbar
    ref="scrollContainerRef"
    :vertical="false"
    class="scroll-container"
    @wheel.prevent="handleScroll"
  >
    <slot />
  </el-scrollbar>
</template>

<script setup>
import { ref, getCurrentInstance } from 'vue'

const tagAndTagSpacing = 4 // tagAndTagSpacing

// 创建 ref 变量来引用 el-scrollbar
const scrollContainerRef = ref(null)

// 定义数据
const left = ref(0)

// 处理滚动事件
const handleScroll = (e) => {
  const eventDelta = e.wheelDelta || -e.deltaY * 40
  const scrollWrapper = scrollContainerRef.value.$refs.wrap
  scrollWrapper.scrollLeft = scrollWrapper.scrollLeft + eventDelta / 4
}

// 移动到目标标签
const moveToTarget = (currentTag) => {
  const container = scrollContainerRef.value.$el
  const containerWidth = container.offsetWidth
  const scrollWrapper = scrollContainerRef.value.$refs.wrap
  const parent = getCurrentInstance().parent
  const tagList = parent.refs.tag

  let firstTag = null
  let lastTag = null
  let prevTag = null
  let nextTag = null

  // find first tag and last tag
  if (tagList.length > 0) {
    firstTag = tagList[0]
    lastTag = tagList[tagList.length - 1]
  }

  // find preTag and nextTag
  for (let i = 0; i < tagList.length; i++) {
    if (tagList[i] === currentTag) {
      if (i === 0) {
        nextTag = tagList.length > 1 && tagList[i + 1]
      } else if (i === tagList.length - 1) {
        prevTag = tagList.length > 1 && tagList[i - 1]
      } else {
        prevTag = tagList[i - 1]
        nextTag = tagList[i + 1]
      }
      break
    }
  }

  if (firstTag === currentTag) {
    scrollWrapper.scrollLeft = 0
  } else if (lastTag === currentTag) {
    scrollWrapper.scrollLeft = scrollWrapper.scrollWidth - containerWidth
  } else {
    // the tag's offsetLeft after of nextTag
    const afterNextTagOffsetLeft =
      nextTag.$el.offsetLeft + nextTag.$el.offsetWidth + tagAndTagSpacing

    // the tag's offsetLeft before of prevTag
    const beforePrevTagOffsetLeft = prevTag.$el.offsetLeft - tagAndTagSpacing

    if (afterNextTagOffsetLeft > scrollWrapper.scrollLeft + containerWidth) {
      scrollWrapper.scrollLeft = afterNextTagOffsetLeft - containerWidth
    } else if (beforePrevTagOffsetLeft < scrollWrapper.scrollLeft) {
      scrollWrapper.scrollLeft = beforePrevTagOffsetLeft
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.scroll-container {
  white-space: nowrap;
  position: relative;
  overflow: hidden;
  width: 100%;
  :deep(.el-scrollbar__bar) {
    bottom: 0px;
  }
  :deep(.el-scrollbar__wrap) {
    height: 49px;
  }
}
</style>
