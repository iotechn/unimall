<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-input
        v-model="listQuery.targetAdminId"
        clearable
        class="filter-item"
        style="width: 200px;"
        placeholder="搜索管理员ID"
      />
      <el-button v-permission="['admin:log:list']" class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">查找</el-button>

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
      <el-table-column align="center" label="日志ID" width="80" prop="id" />

      <el-table-column align="center" label="管理员ID" width="80" prop="adminId" />

      <el-table-column align="center" label="请求ID" width="130" prop="requestId" />

      <el-table-column align="center" label="API分组" prop="group" />

      <el-table-column align="center" label="API方法" prop="method" />

      <el-table-column align="center" width="140" label="操作时间" prop="gmtCreate">
        <template slot-scope="scope">{{ scope.row.gmtCreate | formatTime }}</template>
      </el-table-column>

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
  listAdminLog
} from '@/api/admin'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination

export default {
  name: 'AdminLog',
  components: { Pagination },
  data() {
    return {
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        targetAdminId: undefined
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      listAdminLog(this.listQuery)
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
