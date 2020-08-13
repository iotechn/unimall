<template>
  <div class="dashboard-editor-container">
    <el-row>
      <el-col :span="5">
        <el-row class="panel-group">
          <el-col class="card-panel-col">
            <div class="card-panel" @click="handleSetLineChartData('newVisitis')">
              <div class="card-panel-icon-wrapper icon-people">
                <svg-icon icon-class="peoples" class-name="card-panel-icon" />
              </div>
              <div class="card-panel-description">
                <div class="card-panel-text">商品数量</div>
                <count-to :start-val="0" :end-val="productCount" :duration="2600" class="card-panel-num" />
              </div>
            </div>
          </el-col>
        </el-row>
        <el-row class="panel-group">
          <el-col class="card-panel-col">
            <div class="card-panel" @click="handleSetLineChartData('messages')">
              <div class="card-panel-icon-wrapper icon-message">
                <svg-icon icon-class="message" class-name="card-panel-icon" />
              </div>
              <div class="card-panel-description">
                <div class="card-panel-text">未发货单</div>
                <count-to
                  :start-val="0"
                  :end-val="waitStockCount"
                  :duration="3000"
                  class="card-panel-num"
                />
              </div>
            </div>
          </el-col>
        </el-row>
      </el-col>
      <el-col :span="16">
        <div id="orderChart" class="chart" style="height: 350px; padding: 20px; padding-left: 80px"/>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="10">
        <div id="sumChart" class="chart" style="height: 400px; padding: 30px"/>
      </el-col>
      <el-col :span="11">
        <el-row>
          <el-col :span="12">
            <div id="areaChart" class="chart" style="height: 400px; padding: 30px"/>
          </el-col>
          <el-col :span="12">
            <div id="channelChart" class="chart" style="height: 400px; padding: 30px"/>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
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
      productCount: 0,
      waitStockCount: 0
    }
  },
  created() {
    info().then(response => {
      const orderChart = echarts.init(document.getElementById('orderChart'))
      const sumChart = echarts.init(document.getElementById('sumChart'))
      const areaChart = echarts.init(document.getElementById('areaChart'))
      const channelChart = echarts.init(document.getElementById('channelChart'))
      this.waitStockCount = response.data.data.waitStockCount
      this.productCount = response.data.data.productCount
      // 每日订单走势
      orderChart.setOption({
        title: { text: '7日订单' },
        legend: {
          data: ['订单数'],
          right: 1
        },
        yAxis: [
          {
            name: '订单数',
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
          data: response.data.data.daysOrder[0]
        },
        series: [
          {
            name: '订单数',
            yAxisIndex: 0,
            data: response.data.data.daysOrder[1],
            type: 'bar',
            color: '#00B5FF',
            barWidth: 30
          }
        ]
      })
      // 订单金额走势
      sumChart.setOption({
        title: { text: '7日成交金额' },
        legend: {
          data: ['订单数'],
          right: 1
        },
        yAxis: [
          {
            name: '订单数',
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
          data: response.data.data.daysSum[0]
        },
        series: [
          {
            name: '订单数',
            yAxisIndex: 0,
            data: response.data.data.daysSum[1],
            type: 'line',
            color: '#20B2AA'
          }
        ]
      })
      // 地区饼图
      areaChart.setOption({
        title: { text: '订单地区分布' },
        legend: {
          data: ['地区分布'],
          right: 1,
          color: '#20B2AA'
        },
        series: [
          {
            type: 'pie',
            name: '地区分布',
            data: response.data.data.area
          }
        ]
      })
      // 渠道饼图
      channelChart.setOption({
        title: { text: '订单渠道分布' },
        legend: {
          data: ['渠道分布'],
          right: 1,
          color: '#20B2AA'
        },
        series: [
          {
            type: 'pie',
            name: '地区分布',

            data: response.data.data.channel
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
  padding-left: 30px;

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
