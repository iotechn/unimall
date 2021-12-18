<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-input v-model="listQuery.orderNo" clearable class="filter-item" style="width: 200px;" placeholder="填写会员卡号" />
      <el-input v-model="listQuery.phone" clearable class="filter-item" style="width: 200px;" placeholder="填写手机号" />
      <el-select v-model="listQuery.status" style="width: 200px" class="filter-item" placeholder="选择状态" clearable>
        <el-option v-for="(key,index) in statusDic" :key="index" :label="key.name" :value="key.value" />
      </el-select>
      <el-select v-model="listQuery.source" style="width: 200px" class="filter-item" placeholder="选择来源" clearable>
        <el-option v-for="(key,index) in sourceDic" :key="index" :label="key.name" :value="key.value" />
      </el-select>
      <el-select v-model="listQuery.templateId" style="width: 200px" class="filter-item" placeholder="选择会员卡模板" clearable>
        <el-option v-for="(key,index) in templateDic" :key="index" :label="key.name" :value="key.value" />
      </el-select>
      <el-button v-permission="['vip:order:list']" class="filter-item" type="primary" icon="el-icon-search" @click="getList" >查找</el-button>
    </div>

    <!-- 查询结果 -->
    <el-table v-loading="listLoading" :data="list" size="small" element-loading-text="正在查询中。。。" border fit highlight-current-row >
      <el-table-column align="center" label="ID" prop="id" width="80"/>
      <el-table-column align="center" label="模板ID" prop="templateId" width="80"/>
      <el-table-column align="center" label="卡标题" prop="title" />
      <el-table-column align="center" label="描述" min-width="200" prop="description" />
      <el-table-column align="center" label="会员卡号" prop="orderNo" />
      <el-table-column align="center" label="套餐天数" prop="dayNum" width="80" />
      <el-table-column align="center" label="会员卡价格" prop="price" width="100">
        <template slot-scope="scope">￥{{ scope.row.price / 100 }}</template>
      </el-table-column>
      <el-table-column align="center" label="状态" prop="source">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.status | statusFilter }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作" width="120" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="scope">
          <el-button v-permission="['vip:order:refund']" v-if="scope.row.status === 10" type="primary" size="medium" @click="refund(scope.row)" >退款</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.pageNo" :limit.sync="listQuery.limit" @pagination="getList" />

  </div>
</template>

<script>
import { list, refund } from '@/api/vip-order'
import { list as templateList } from '@/api/vip-template'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination

export default {
  name: 'VipTemplate',
  components: { Pagination },
  filters: {
    statusFilter(status) {
      switch (status) {
        case 10: return '待兑换'
        case 20: return '已兑换'
        case 30: return '待支付'
        case 40: return '已支付'
        case 50: return '已退款'
        default:
          return '未知状态'
      }
    }
  },
  data() {
    return {
      list: [],
      total: 0,
      listLoading: true,
      listQuery: {
        pageNo: 1,
        limit: 20,
        title: undefined,
        display: undefined
      },
      displayDic: [{ value: 0, name: '不显示' }, { value: 1, name: '显示' }],
      statusDic: [{ value: 10, name: '待兑换' }, { value: 20, name: '已兑换' }, { value: 30, name: '待支付' }, { value: 40, name: '已支付' }, { value: 50, name: '已退款' }],
      sourceDic: [{ value: 0, name: '线上购买' }, { value: 1, name: '兑换码兑换' }],
      templateDic: [],
      bindForm: {
        id: undefined,
        orderNo: undefined,
        phone: undefined
      }
    }
  },
  created() {
    this.getList()
    templateList({ limit: 1000 }).then(response => {
      const array = response.data.data.items
      for (let index = 0; index < array.length; index++) {
        const element = array[index]
        this.templateDic.push({ value: element.id, name: element.title })
      }
    })
  },
  methods: {
    getList() {
      this.listLoading = true
      list(this.listQuery)
        .then(response => {
          this.list = response.data.data.items
          this.total = response.data.data.total
          this.listLoading = false
        })
        .catch(() => {
          this.list = []
          this.total = 0
          this.listLoading = false
        })
    },
    refund(row) {
      const that = this
      this.$confirm('此操作将退款---' + row.id + '---, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        refund(row.id).then(() => {
          that.getList()
          that.$notify.success({
            title: '成功',
            message: '退款成功'
          })
        })
          .catch(response => {
            that.$notify.error({
              title: '失败',
              message: response.data.errmsg
            })
          })
      }).catch(() => {
        return false
      })
    }
  }
}
</script>
