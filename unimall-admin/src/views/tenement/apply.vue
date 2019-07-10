<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-select
        v-model="listQuery.status"
        style="width: 200px"
        class="filter-item"
        placeholder="请选择申请表状态"
      >
        <el-option v-for="(key, value) in statusMap" :key="key" :label="key" :value="value" />
      </el-select>
      <el-button
        v-permission="['admin:apply:list']"
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="handleFilter"
      >查找</el-button>
      <el-button
        v-permission="['admin:apply:apply']"
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="showApply"
      >申请</el-button>
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
      <el-table-column align="center" label="用户姓名" prop="realname" />

      <el-table-column align="center" label="手机号" prop="phone" />

      <el-table-column align="center" label="租户Code" prop="tenementCode" />

      <el-table-column align="center" label="商户Code" prop="merchantCode" />

      <el-table-column align="center" label="申请状态" prop="status">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.status | applyStatusFilter }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="申请时间" prop="gmtCreate">
        <template slot-scope="scope">{{ scope.row.gmtCreate | formatTime }}</template>
      </el-table-column>

      <el-table-column align="center" label="操作" width="200" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-permission="['admin:apply:handle']"
            v-if="scope.row.status===0"
            type="primary"
            size="mini"
            @click="handleDeal(scope.row, 1)"
          >通过</el-button>
          <el-button
            v-permission="['admin:apply:handle']"
            v-if="scope.row.status===0"
            type="primary"
            size="mini"
            @click="handleDeal(scope.row, 2)"
          >回绝</el-button>
          <el-button v-if="scope.row.status===1" type="primary" size="mini">已通过</el-button>
          <el-button v-if="scope.row.status===2" type="primary" size="mini">已回绝</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :visible.sync="applyDialogVisible" title="申请">
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="applyDetail"
        status-icon
        label-position="left"
        label-width="100px"
        style="width: 400px; margin-left:50px;"
      >
        <el-form-item label="目标校区" prop="tenementCode">
          <el-select v-model="applyDetail.tenementCode" placeholder="请选择">
            <el-option
              v-for="item in tenementList"
              :key="item.tenementCode"
              :label="item.title"
              :value="item.tenementCode"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="applyDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleApply">确定</el-button>
      </div>
    </el-dialog>

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
</style>

<script>
import { listApply, applydeal, apply } from '@/api/apply'
import { listTenement } from '@/api/tenement'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination
import checkPermission from '@/utils/permission' // 权限判断函数

const statusMap = {
  0: '未处理',
  1: '已通过',
  2: '已回绝'
}

export default {
  name: 'NearbyApply',
  components: { Pagination },
  filters: {
    applyStatusFilter(status) {
      return statusMap[status]
    },
    formatTime(time) {
      const d = new Date(time)

      return (
        d.getFullYear() +
        '-' +
        (d.getMonth() + 1) +
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
      tenementList: [],
      list: undefined,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        status: undefined
      },
      rules: {
        tenementCode: [
          { required: true, message: '校区Code不能为空', trigger: 'blur' }
        ]
      },
      statusMap,
      applyDialogVisible: false,
      applyDetail: {
        tenementCode: ''
      }
    }
  },
  created() {
    this.getList()
    this.getTenementList()
  },
  methods: {
    checkPermission,
    showApply() {
      this.applyDialogVisible = true
      this.applyDetail = {
        tenementCode: ''
      }
    },
    handleApply() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          apply(this.applyDetail.tenementCode)
            .then(response => {
              this.list.unshift(response.data.data)
              this.dialogFormVisible = false
              this.$notify.success({
                title: '成功',
                message: '创建成功'
              })
            })
            .catch(response => {
              this.$notify.error({
                title: '失败',
                message: response.data.errmsg
              })
            })
          this.applyDialogVisible = false
        }
      })
    },
    getTenementList() {
      listTenement().then(response => {
        this.tenementList = response.data.data
      })
    },
    getList() {
      this.listLoading = true
      listApply(this.listQuery)
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
    handleDeal(row, status) {
      this.$confirm(
        '您确定要标记 ' +
          row.realname +
          ' 的请求为' +
          (status === 1 ? '已通过' : '已回绝') +
          '吗？',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      )
        .then(() => {
          applydeal(row.id, status)
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
    }
  }
}
</script>
