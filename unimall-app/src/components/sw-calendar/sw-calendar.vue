<template>
  <view class="sign-wrap">
    <view class="date-wrap">
      <view class="cur-date">{{ curDate }}</view>
      <view class="title-item-box item-box" >
        <view
          v-for="(item, index) in ['日','一','二','三','四','五','六']"
          :key="index"
          class="item">{{ item }}
        </view>
      </view>
      <view class="date-item-box item-box">
        <view
          v-for="(item, index) in dateArray"
          :class="{disabled:item.disabled,active:item.isToday,ok:item.ok}"
          :style="itemStyle"
          :key="index"
          class="item date-item"
          @tap="tapThis(item)">
          {{ item.date }}
        </view>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  props: {
    history: {
      type: Array,
      default: () => {
        return []
      }
    }
  },
  data() {
    return {
      itemStyle: '',
      curDate: '', // 当前日期
      dateArray: []
    }
  },
  watch: {
    history(newVal, oldVal) {
      for (let i = 0; i < this.dateArray.length; i++) {
        const obj = this.dateArray[i]
        const strDate = obj.year + '-' + ((obj.month < 10) ? ('0' + obj.month) : ('' + obj.month)) + '-' + obj.date
        for (let i = 0; i < newVal.length; i++) {
          if (newVal[i] === strDate) {
            obj.ok = true
          }
        }
      }
    }

  },
  mounted() {
    // 设置天数高度
    const nodes = uni.createSelectorQuery().in(this).select('.item')
    nodes.fields({
      size: true
    }, data => {
      this.itemStyle = `height:${data.width}px;line-height:${data.width}px;margin: 10upx calc((100% - ${data.width * 7}px) / 14);`
    }).exec()

    // 设置日期
    this.getCurDate()
  },
  methods: {
    getCurDate() {
      const d = new Date()
      const y = d.getFullYear()
      let m = d.getMonth()
      const week = new Date(y, m, 1).getDay() // 一号是周几
      m = m === 11 ? 12 : m + 1 // 月份转换
      const max = new Date(y, m, 0).getDate() // 本月最大天
      const day = d.getDate()

      this.curDate = `${y}年${m}月${day}日`
      // 1号之前留白,对应正确的周几
      for (let i = 0; i < week; i++) {
        const obj = {
          year: '',
          month: '',
          date: '',
          disabled: false,
          isToday: false
        }
        this.dateArray.push(obj)
      }
      for (let i = 0; i < max; i++) {
        const today = i + 1
        const obj = {
          year: y,
          month: m,
          date: today,
          disabled: today < day,
          isToday: today === day,
          ok: false
        }
        this.dateArray.push(obj)
      }
    },
    // 点击日期
    tapThis(e) {
      if (!e.date) return
      const data = {
        year: e.year,
        month: e.month,
        date: e.date,
        type: 'today' // 默认点击今天
      };
      // 点击今天之前
      (e.disabled && !e.isToday) && (data.type = 'pre');
      // 点击今天之后
      (!e.disabled && !e.isToday) && (data.type = 'next')
      this.$emit('tapDate', data)
    }
  }
}
</script>

<style scoped lang="scss" >
	.date-wrap{
		width: 96%;margin-left: 2%;padding: 2% 0;border-radius: 16upx;background: #fff;box-sizing: border-box;text-align: center;
		.cur-date{font-size: 30upx; padding: 10rpx;}
		.item-box{
			display: grid;
			color: #777;font-size: 28upx;
			grid-template-columns: repeat(7, 14.285%);
			.item{
				width: 70rpx; margin: 10upx auto;border-radius: 100upx; height: 70rpx; line-height: 70rpx;
			}
			.disabled{
				background: #efefef;color: #999;
			}
			.active{
				background-color: $base-color;
				color: white;
			}
			.ok {
				background-size: 100% 100%;
				background-image: url(https://unimall-asset.oss-cn-beijing.aliyuncs.com/ok.png);
			}
		}
	}
</style>
