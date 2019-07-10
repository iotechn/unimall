<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-select
        v-model="listQuery.status"
        style="width: 200px"
        class="filter-item"
        placeholder="请选择查找状态"
      >
        <el-option v-for="(key, value) in statusMap" :key="key" :label="key" :value="value" />
      </el-select>
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">查找</el-button>
      <!--<el-button class="filter-item" type="primary" icon="el-icon-edit" @click="handleCreate">添加</el-button>-->
      <!--<el-button :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload">导出</el-button>-->
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
      <el-table-column align="center" width="60px" label="用户ID" prop="userId" sortable />

      <el-table-column align="center" width="100px" label="类型" prop="type">
        <template slot-scope="scope">
          <el-tag>{{ typeMap[scope.row.type]?typeMap[scope.row.type]:'未指定' }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="内容" prop="content" />

      <el-table-column align="center" label="图片" prop="imgDOList">
        <template slot-scope="scope">
          <img v-for="item in scope.row.imgDOList" :key="item.id" :src="item.url" width="100px" >
        </template>
      </el-table-column>

      <el-table-column align="center" width="100px" label="状态" prop="status">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.status === 1? '已处理': '未处理' }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column
        align="center"
        label="操作"
        width="200px"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            v-permission="['admin:orgapply:audit']"
            v-if="scope.row.status === 0"
            type="primary"
            size="mini"
            @click="handleStatus(scope.row)"
          >{{ '处理' }}</el-button>
          <el-button
            v-permission="['admin:orgapply:onekey']"
            v-if="scope.row.status === 0 && scope.row.type"
            type="primary"
            size="mini"
            @click="handleOneKey(scope.row)"
          >{{ '一键转发' }}</el-button>
        </template>
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
  fetchUserOrgApplyList,
  oneKeyUserOrgApply,
  auditUserOrgApply
} from '@/api/user'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination

const statusMap = {
  0: '未处理',
  1: '已处理'
}

const typeMap = {
  0: '系统通知',
  1: '生活动态',
  2: '跳蚤市场',
  3: '失物招领',
  4: '互相帮助',
  5: '租房拼车',
  6: '表白墙',
  7: '兼职招聘'
}

export default {
  name: 'User',
  components: { Pagination },
  data() {
    return {
      typeMap,
      statusMap,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        status: undefined
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
      fetchUserOrgApplyList(this.listQuery)
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
    },
    handleStatus(row) {
      this.$confirm('您确定要标记 已处理 吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          auditUserOrgApply(row.id)
            .then(response => {
              this.$notify.success({
                title: '成功',
                message: '确认处理成功'
              })
              this.getList()
            })
            .catch(response => {
              this.$notify.error({
                title: '失败',
                message: response.data.errmsg
              })
            })
        })
        .catch(() => {})
    },
    handleOneKey(row) {
      this.$confirm('您确定要标记 一键转发 吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          oneKeyUserOrgApply(row.id)
            .then(response => {
              this.$notify.success({
                title: '成功',
                message: '确认转发成功'
              })
              this.getList()
            })
            .catch(response => {
              this.$notify.error({
                title: '失败',
                message: response.data.errmsg
              })
            })
        })
        .catch(() => {})
    }
  }
}
</script>
