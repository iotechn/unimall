<template>
  <div class="app-container">
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
      <el-table-column align="center" label="表名" prop="name" />

      <el-table-column align="center" label="是否已经生成" prop="exist">
        <template slot-scope="scope">{{ scope.row.exist ? '已生成' : '未生成' }}</template>
      </el-table-column>

      <el-table-column align="center" label="操作" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-if="!scope.row.exist" type="primary" size="mini" @click="generateShow(scope.row)">生成</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改对话框 -->
    <el-dialog :visible.sync="dialogFormVisible" :fullscreen="true" title="生成Admin服务代码">
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="dataForm"
        status-icon
        label-position="left"
        label-width="150px"
        style="width: 700px; margin-left:50px;"
      >
        <el-form-item label="表名" prop="tableName">
          <el-input :disabled="true" v-model="dataForm.tableName"/>
        </el-form-item>
        <el-form-item label="服务名称" prop="title">
          <el-input v-model="dataForm.title"/>
        </el-form-item>
        <el-form-item >
          <el-alert
            title="服务名称（全英文，比如RoleService -> sys.Role 即可）"
            type="info"/>
        </el-form-item>
        <el-form-item label="服务文件夹" prop="folder">
          <el-input v-model="dataForm.folder"/>
        </el-form-item>
        <el-form-item label="页面名" prop="pageName">
          <el-input v-model="dataForm.pageName"/>
        </el-form-item>
      </el-form>
      <el-table
        v-loading="columnsLoading"
        :data="columns"
        size="small"
        element-loading-text="正在查询中。。。"
        border
        fit
        highlight-current-row
      >
        <el-table-column align="center" label="列名" prop="name" />

        <el-table-column align="center" label="别名" prop="alias" />

        <el-table-column align="center" label="中文名" prop="chinese" >
          <template slot-scope="scope">
            <el-input v-model="scope.row.chinese" size="small" />
          </template>
        </el-table-column>

        <el-table-column align="center" label="映射Java" prop="clazz" >
          <template slot-scope="scope">
            <el-input v-model="scope.row.clazz" size="small" />
          </template>
        </el-table-column>

        <el-table-column align="center" label="钱？" prop="chinese" >
          <template slot-scope="scope">
            <el-checkbox v-model="scope.row.money"/>
          </template>
        </el-table-column>

        <el-table-column align="center" label="列表展示" prop="showInList" >
          <template slot-scope="scope">
            <el-checkbox v-model="scope.row.showInList"/>
          </template>
        </el-table-column>

        <el-table-column align="center" label="搜索" prop="query" >
          <template slot-scope="scope">
            <el-checkbox v-model="scope.row.query"/>
          </template>
        </el-table-column>

        <el-table-column align="center" label="like搜索" prop="likeQuery" >
          <template slot-scope="scope">
            <el-checkbox v-model="scope.row.likeQuery"/>
          </template>
        </el-table-column>

        <el-table-column align="center" label="新增字段" prop="insertColumn" >
          <template slot-scope="scope">
            <el-checkbox v-model="scope.row.insertColumn"/>
          </template>
        </el-table-column>

        <el-table-column align="center" label="非空" prop="notnull" >
          <template slot-scope="scope">
            <el-checkbox v-model="scope.row.notnull"/>
          </template>
        </el-table-column>

        <el-table-column align="center" label="非空提示" prop="notice" >
          <template slot-scope="scope">
            <el-input v-model="scope.row.notice" size="small" />
          </template>
        </el-table-column>

      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button :loading="submiting" type="primary" @click="handleGenerate" >立即生成</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listTable,
  listColumns,
  generate
} from '@/api/generator'
export default {
  name: 'Generator',
  data() {
    return {
      list: null,
      total: 0,
      listLoading: true,
      columns: [],
      columnsLoading: true,
      listQuery: {
        page: 1,
        limit: 20
      },
      dataForm: {
        tableName: '',
        serviceTitle: ''
      },
      dialogFormVisible: false,
      rules: {
        tableName: [{ required: true, message: '表名不能为空', trigger: 'blur' }],
        title: [{ required: true, message: '服务名不能为空', trigger: 'blur' }],
        folder: [{ required: true, message: '页面文件夹不能为空', trigger: 'blur' }],
        pageName: [{ required: true, message: '页面名称不能为空', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      listTable(this.listQuery)
        .then(response => {
          this.list = response.data.data
          this.listLoading = false
        })
        .catch(() => {
          this.list = []
          this.listLoading = false
        })
    },
    resetForm() {
      this.dataForm = {
        id: undefined
      }
    },
    generateShow(row) {
      // 创建
      this.dialogFormVisible = true
      this.columnsLoading = true
      this.dataForm.tableName = row.name
      listColumns(row.name)
        .then(res => {
          this.columnsLoading = false
          this.columns = res.data.data
        })
        .catch(() => {
          this.columns = []
          this.columnsLoading = false
        })
    },
    handleGenerate() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          this.submiting = true
          const dto = {
            ...this.dataForm,
            columnDefinitionList: this.columns
          }
          generate(dto)
            .then(res => {
              this.$notify.success({
                title: '成功',
                message: '生成代码成功！'
              })
              this.submiting = false
              this.dialogFormVisible = false
            })
            .catch(() => {
              this.submiting = false
            })
        }
      })
    }
  }
}
</script>

<style>
</style>
