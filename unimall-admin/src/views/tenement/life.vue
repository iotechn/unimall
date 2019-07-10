<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-input
        v-model="listQuery.content"
        clearable
        class="filter-item"
        style="width: 200px;"
        placeholder="请输入动态内容"
      />
      <el-button
        v-permission="['admin:life:list']"
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
      <el-table-column align="center" label="ID" prop="id" sortable />

      <el-table-column align="center" label="用户ID" prop="userId" />

      <el-table-column align="center" label="内容" prop="content" />

      <el-table-column align="center" label="创建时间" prop="gmtCreate">
        <template slot-scope="scope">{{ scope.row.gmtCreate | formatTime }}</template>
      </el-table-column>

      <el-table-column align="center" label="操作" width="300" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-permission="['admin:life:push']"
            v-if="scope.row.pushStatus === 1"
            type="primary"
            size="mini"
            @click="handlePush(scope.row,2)"
          >推送</el-button>
          <el-button
            v-permission="['admin:life:push']"
            v-if="scope.row.pushStatus === 1"
            type="danger"
            size="mini"
            @click="handlePush(scope.row,3)"
          >回绝</el-button>
          <el-button
            v-permission="['admin:life:delete']"
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
      :page.sync="listQuery.page"
      :limit.sync="listQuery.limit"
      @pagination="getList"
    />
  </div>
</template>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #20a0ff;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
}

.avatar {
  width: 145px;
  height: 145px;
  display: block;
}
</style>

<script>
import { listLife, deleteLife, pushLife } from '@/api/life'
import { getToken } from '@/utils/auth'
import Pagination from '@/components/Pagination'
import ElOption from '../../../node_modules/element-ui/packages/select/src/option' // Secondary package based on el-pagination

export default {
  name: 'Life',
  components: {
    ElOption,
    Pagination
  },
  filters: {
    formatTime(time) {
      const d = new Date(time)
      return (
        d.getFullYear() +
        '-' +
        d.getMonth() +
        1 +
        '-' +
        d.getDate() +
        ' ' +
        d.getHours() +
        ':' +
        d.getMinutes()
      )
    }
  },
  data() {
    return {
      list: [],
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20
      }
    }
  },
  computed: {
    headers() {
      return {
        accessToken: getToken()
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      listLife(this.listQuery)
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
    handleDelete(row) {
      deleteLife(row.id)
        .then(response => {
          this.$notify.success({
            title: '成功',
            message: '删除成功'
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
    },
    handlePush(row, status) {
      pushLife(row.id, status)
        .then(res => {
          this.$notify.success({
            title: '成功',
            message: (status === 2 ? '推送' : '回绝') + '成功'
          })
          this.getList()
        })
        .catch(response => {
          this.$notify.error({
            title: '失败',
            message: response.data.errmsg
          })
        })
    }
  }
}
</script>
