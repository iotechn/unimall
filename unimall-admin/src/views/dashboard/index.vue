<template>
  <div class="dashboard-editor-container">
    <el-row :gutter="40" class="panel-group">
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel" @click="handleSetLineChartData('newVisitis')">
          <div class="card-panel-icon-wrapper icon-people">
            <svg-icon icon-class="peoples" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">用户数量</div>
            <count-to :start-val="0" :end-val="userCount" :duration="2600" class="card-panel-num" />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel" @click="handleSetLineChartData('messages')">
          <div class="card-panel-icon-wrapper icon-message">
            <svg-icon icon-class="message" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">日活数量</div>
            <count-to
              :start-val="0"
              :end-val="activeUserCount"
              :duration="3000"
              class="card-panel-num"
            />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel" @click="handleSetLineChartData('purchases')">
          <div class="card-panel-icon-wrapper icon-money">
            <svg-icon icon-class="message" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">动态数量</div>
            <count-to :start-val="0" :end-val="lifeCount" :duration="3200" class="card-panel-num" />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel" @click="handleSetLineChartData('shoppings')">
          <div class="card-panel-icon-wrapper icon-shoppingCard">
            <svg-icon icon-class="money" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">未发货数</div>
            <count-to :start-val="0" :end-val="orderCount" :duration="3600" class="card-panel-num" />
          </div>
        </div>
      </el-col>
    </el-row>

    <div id="userChart" class="chart" style="height: 400px"/>
  </div>
</template>

<script>
import { info } from '@/api/dashboard'
import CountTo from 'vue-count-to'
var echarts = require('echarts')
export default {
  components: {
    CountTo
  },
  data() {
    return {
      userCount: 0,
      activeUserCount: 0,
      lifeCount: 0,
      orderCount: 0,
      dayMap: {}
    }
  },
  created() {
    info().then(response => {
      this.userCount = response.data.data.userCount
      this.activeUserCount = response.data.data.activeUserCount
      this.lifeCount = response.data.data.lifeCount
      this.orderCount = response.data.data.orderCount

      this.userChart = echarts.init(document.getElementById('userChart'))
      // 转换
      const dayMap = response.data.data.dayMap

      const nameArray = []
      const valueArray = []
      for (var key in dayMap) {
        nameArray.push(key)
        valueArray.push(dayMap[key])
      }

      this.userChart.setOption({
        title: { text: '15日新用户' },
        legend: {
          data: ['新用户'],
          right: 1
        },
        yAxis: [
          {
            name: '新用户',
            type: 'value',
            axisLine: {
              show: false
            },
            axisTick: {
              show: false
            },
            axisLabel: {
              color: '#666',
              fontSize: 12
            }
          }
        ],
        xAxis: {
          axisTick: {
            show: false
          },
          axisLabel: {
            color: '#666',
            fontSize: 12,
            margin: 12
          },
          data: nameArray
        },
        series: [
          {
            name: '新用户',
            yAxisIndex: 0,
            data: valueArray,
            type: 'bar',
            color: '#00B5FF'
          }
        ]
      })
    })
  },
  methods: {
    handleSetLineChartData(type) {
      this.$emit('handleSetLineChartData', type)
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

.panel-group {
  margin-top: 18px;

  .card-panel-col {
    margin-bottom: 32px;
  }
  .card-panel {
    height: 108px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, 0.05);
    border-color: rgba(0, 0, 0, 0.05);
    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;
      }
      .icon-people {
        background: #40c9c6;
      }
      .icon-message {
        background: #36a3f7;
      }
      .icon-money {
        background: #f4516c;
      }
      .icon-shoppingCard {
        background: #34bfa3;
      }
    }
    .icon-people {
      color: #40c9c6;
    }
    .icon-message {
      color: #36a3f7;
    }
    .icon-money {
      color: #f4516c;
    }
    .icon-shoppingCard {
      color: #34bfa3;
    }
    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }
    .card-panel-icon {
      float: left;
      font-size: 48px;
    }
    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;
      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }
      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}
</style>
