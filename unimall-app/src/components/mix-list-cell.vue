<template>
  <view class="content">
    <button v-if="openType" :open-type="openType" class="btn"/>
    <view :class="border" :hover-stay-time="50" class="mix-list-cell" hover-class="cell-hover" @click="eventClick">
      <text
        v-if="icon"
        :style="[{
          color: iconColor,
        }]"
        :class="icon"
        class="cell-icon yticon"/>
      <text class="cell-tit clamp">{{ title }}</text>
      <text v-if="tips" class="cell-tip">{{ tips }}</text>
      <text :class="typeList[navigateType]" class="cell-more yticon"/>

    </view>
  </view>
</template>

<script>
/**
   *  简单封装了下， 应用范围比较狭窄，可以在此基础上进行扩展使用
   *  比如加入image， iconSize可控等
   */
export default {
  props: {
    openType: {
      type: String,
      default: ''
    },
    icon: {
      type: String,
      default: ''
    },
    title: {
      type: String,
      default: '标题'
    },
    tips: {
      type: String,
      default: ''
    },
    navigateType: {
      type: String,
      default: 'right'
    },
    border: {
      type: String,
      default: 'b-b'
    },
    hoverClass: {
      type: String,
      default: 'cell-hover'
    },
    iconColor: {
      type: String,
      default: '#333'
    }
  },
  data() {
    return {
      typeList: {
        left: 'icon-zuo',
        right: 'icon-you',
        up: 'icon-shang',
        down: 'icon-xia'
      }
    }
  },
  methods: {
    eventClick() {
      this.$emit('eventClick')
    }
  }
}
</script>

<style lang='scss'>
  .icon .mix-list-cell.b-b:after {
    left: 90upx;
  }

  .mix-list-cell {
    display: flex;
    align-items: baseline;
    background: #FFFFFF;
    padding-left: 20upx;
    padding: 20upx $page-row-spacing;
    line-height: 60upx;
    position: relative;

    &.cell-hover {
      background: #fafafa;
    }

    &.b-b:after {
      left: 30upx;
    }

    .cell-icon {
      align-self: center;
      width: 56upx;
      max-height: 60upx;
      font-size: 38upx;
    }

    .cell-more {
      align-self: center;
      font-size: 30upx;
      color: $font-color-base;
      margin-left: $uni-spacing-row-sm;
    }

    .cell-tit {
      flex: 1;
      font-size: $font-base;
      color: $font-color-dark;
      margin-right: 10upx;
    }

    .cell-tip {
      border-top: #909399;
      font-size: $font-sm+2upx;
      color: $font-color-light;
    }

  }

  .btn::after {
    display: none;
  }

  .btn {
    width: 95%;
    position: absolute;
    opacity: 0;
    z-index: 99;
  }
</style>
