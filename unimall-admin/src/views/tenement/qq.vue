<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-input
        v-model="listQuery.number"
        clearable
        class="filter-item"
        style="width: 200px;"
        placeholder="请输入群号搜索"
      />
      <el-select
        v-model="listQuery.type"
        style="width: 200px"
        class="filter-item"
        placeholder="请选择查找类型"
      >
        <el-option v-for="(key, value) in typeMap" :key="key" :label="key" :value="value" />
      </el-select>
      <el-button
        v-permission="['admin:qq:list']"
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="handleFilter"
      >查找</el-button>
      <el-button
        v-permission="['admin:qq:create']"
        class="filter-item"
        type="primary"
        icon="el-icon-create"
        @click="showCreate"
      >添加</el-button>
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
      <el-table-column align="center" label="id" prop="id" />

      <el-table-column align="center" label="租户Code" prop="tenementCode" />

      <el-table-column align="center" label="群标题" prop="title" />

      <el-table-column align="center" label="推送类型" prop="pushType">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.type | pushTypeFilter }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="群类型" prop="type">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.type | groupTypeFilter }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="群号码" prop="number" />

      <el-table-column align="center" label="创建时间" prop="gmtCreate">
        <template slot-scope="scope">{{ scope.row.gmtCreate | formatTime }}</template>
      </el-table-column>

      <el-table-column align="center" label="操作" width="200" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-permission="['admin:qq:delete']"
            type="primary"
            size="mini"
            @click="handleDel(scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :visible.sync="createDialogVisible" title="添加">
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="temp"
        status-icon
        label-position="left"
        label-width="100px"
        style="width: 400px; margin-left:50px;"
      >
        <el-form-item label="Q群标题" prop="title">
          <el-input v-model="temp.title" />
        </el-form-item>
        <el-form-item label="Q群类型" prop="type">
          <el-select v-model="temp.type" placeholder="请选择">
            <el-option v-for="(key, value) in typeMap" :key="key" :label="key" :value="value" />
          </el-select>
        </el-form-item>
        <el-form-item label="Q群类型" prop="pushType">
          <el-select v-model="temp.pushType" placeholder="请选择">
            <el-option v-for="(key, value) in pushTypeMap" :key="key" :label="key" :value="value" />
          </el-select>
        </el-form-item>
        <el-form-item label="Q群号" prop="number">
          <el-input v-model="temp.number" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="createDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleCreate">确定</el-button>
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
import { list, create, del } from '@/api/qq'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination
import checkPermission from '@/utils/permission' // 权限判断函数

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

const pushTypeMap = {
  1: '链接推送(奔放)',
  2: '图文推送(保守)'
}

export default {
  name: 'NearbyApply',
  components: { Pagination },
  filters: {
    groupTypeFilter(status) {
      return typeMap[status]
    },
    pushTypeFilter(pushType) {
      return pushTypeMap[pushType]
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
        type: undefined,
        number: undefined
      },
      typeMap,
      pushTypeMap,
      createDialogVisible: false,
      temp: {
        type: undefined,
        title: undefined,
        number: undefined
      },
      rules: {
        title: [{ required: true, message: '群标题不能为空', trigger: 'blur' }],
        type: [{ required: true, message: '群类型不能为空', trigger: 'blur' }],
        number: [{ required: true, message: '群号码不能为空', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    checkPermission,
    showCreate() {
      this.createDialogVisible = true
      this.temp = {
        type: undefined,
        title: undefined,
        number: undefined
      }
    },
    handleCreate() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          create(this.temp)
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
          this.createDialogVisible = false
        }
      })
    },
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
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    handleDel(row) {
      this.$confirm('您确定要删除"' + row.title + '"吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          del(row.id)
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
