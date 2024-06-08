<template>
  <!--增加video标签支持，并循环添加-->
  <view :class="node.classStr" :style="node.styleStr">
    <video :class="node.classStr" :src="node.attr.src" class="video-video"/>
  </view>
</template>

<script>
export default {
  name: 'WxParseVideo',
  props: {
    node: {
      type: Object,
      default: () => {
        return {}
      }
    }
  }
}
</script>
