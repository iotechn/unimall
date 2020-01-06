<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <!--<el-input v-model="listQuery.userId" clearable class="filter-item" style="width: 200px;" placeholder="请输入用户ID"/>-->
      <el-input
        v-model="listQuery.id"
        clearable
        class="filter-item"
        style="width: 200px;"
        placeholder="评论ID"
      />
      <el-input
        v-model="listQuery.userName"
        clearable
        style="width: 200px"
        class="filter-item"
        placeholder="用户姓名"
      />
      <el-input
        v-model="listQuery.spuName"
        clearable
        style="width: 200px"
        class="filter-item"
        placeholder="商品名称"
      />
      <el-input
        v-model="listQuery.content"
        clearable
        style="width: 200px"
        class="filter-item"
        placeholder="评论内容"
      />
      <el-button
        v-permission="['operation:appraise:query']"
        clearable
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="handleFilter"
      >查找</el-button>
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
      <el-table-column align="center" label="评论编号" prop="id" />

      <el-table-column align="center" label="评论内容" width="300" prop="content" />
      <el-table-column align="center" label="评论分数" prop="score" />

      <el-table-column align="center" label="用户ID" prop="userId" />
      <el-table-column align="center" label="用户昵称" prop="userNickName" />
      <el-table-column align="center" label="订单ID" prop="orderId" />
      <el-table-column align="center" label="商品ID" prop="spuId" />
      <el-table-column align="center" label="商品名称" prop="spuTitle" />
      <el-table-column align="center" label="评论时间" prop="gmtCreate">
        <template slot-scope="scope">{{ scope.row.gmtCreate | formatTime }}</template>
      </el-table-column>

      <el-table-column align="center" label="操作" width="200" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-permission="['operation:appraise:delete']"
            type="danger"
            size="mini"
            @click="handleDelete(scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="listQuery.pageNo"
      :limit.sync="listQuery.limit"
      @pagination="getList"
    />

  </div>
</template>

<style>
</style>

<script>
import { listAppraise, deleteAppraise } from '@/api/appraise'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination

export default {
  name: 'Order',
  components: { Pagination },
  filters: {
  },
  data() {
    return {
      list: undefined,
      total: 0,
      listLoading: true,
      listQuery: {
        pageNo: 1,
        limit: 20,
        id: undefined,
        spuName: undefined,
        content: undefined,
        userName: undefined
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      listAppraise(this.listQuery)
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
    handleDelete(row) {
      this.$confirm('此操作将永久删除该评论' + row.id + ', 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteAppraise(row)
          .then(response => {
            this.$notify.success({
              title: '成功',
              message: '删除评论成功'
            })
            const index = this.list.indexOf(row)
            this.list.splice(index, 1)
          })
          .catch(response => {
            this.$notify.error({
              title: '失败',
              message: response.data.errmsg
            })
          })
      }).catch(() => {
        return false
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    }
  }
}
</script>
