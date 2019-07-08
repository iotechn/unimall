<template>
  <div class="app-container">

    <!-- 查询结果 -->
    <el-table v-loading="listLoading" :data="list" size="small" element-loading-text="正在查询中。。。" border fit highlight-current-row>

      <el-table-column align="center" label="反馈ID" prop="id"/>

      <el-table-column align="center" label="用户ID" prop="userId"/>

      <el-table-column align="center" label="反馈类型" prop="type">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.type | feedbackTypeFilter }}</el-tag>
        </template>
      </el-table-column>


      <el-table-column align="center" label="反馈描述" prop="description"/>

      <el-table-column align="center" label="联系电话" prop="contactPhone"/>

      <el-table-column align="center" label="时间" prop="gmtCreate"/>

    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

  </div>
</template>

<script>
import { listFeedback } from '@/api/user'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination

const typeMap = {
    0 : '功能建议',
    1 : 'BUG 反馈',
    2 : '其他'
}

export default {
  name: 'Feedback',
  components: { Pagination },
  filters: {
    feedbackTypeFilter(code) {
      return typeMap[code]
    }
  },
  data() {
    return {
      list: undefined,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        sort: 'id',
        order: 'desc'
      },
      downloadLoading: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      listFeedback(this.listQuery).then(response => {
        this.list = response.data.data.items
        this.total = response.data.data.total
        this.listLoading = false
      }).catch(() => {
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
