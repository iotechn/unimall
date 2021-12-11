<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-input
        v-model="listQuery.userId"
        clearable
        class="filter-item"
        style="width: 200px;"
        placeholder="请输入用户ID"
      />
      <el-button v-permission="['admin:address:list']" class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">查找</el-button>

    </div>

    <!-- 查询结果 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      size="small"
      element-loading-text="正在查询中。。。"
      border
      fit
      highlight-current-row
    >
      <el-table-column align="center" label="地址ID" width="80" prop="id" />

      <el-table-column align="center" label="用户ID" width="80" prop="userId" />

      <el-table-column align="center" label="签收人" prop="consignee" />

      <el-table-column align="center" label="手机号" prop="phone" />

      <el-table-column align="center" label="省份" prop="province" />

      <el-table-column align="center" label="城市" prop="city" />

      <el-table-column align="center" label="区/县" prop="county" />

      <el-table-column align="center" label="详情地址" prop="address" />

    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="listQuery.page"
      :limit.sync="listQuery.limit"
      @pagination="getList"
    />

  </div>
</template>

<script>
import {
  listAddress
} from '@/api/address'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination

export default {
  name: 'Address',
  components: { Pagination },
  data() {
    return {
      list: [],
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        userId: undefined
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      listAddress(this.listQuery)
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
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    }
  }
}
</script>

<style>
</style>
