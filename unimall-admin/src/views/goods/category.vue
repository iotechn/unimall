<template>
  <div class="app-container">

    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-input v-model="listQuery.id" clearable class="filter-item" style="width: 200px;" placeholder="请输入类目ID"/>
      <el-input v-model="listQuery.title" clearable class="filter-item" style="width: 200px;" placeholder="请输入类目名称"/>
      <el-button v-permission="['admin:category:list']" class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">查找</el-button>
      <el-button v-permission="['admin:category:create']" class="filter-item" type="primary" icon="el-icon-edit" @click="handleCreate">添加</el-button>
    </div>

    <!-- 查询结果 -->
    <el-table v-loading="listLoading" :data="list" size="small" element-loading-text="正在查询中。。。" border fit highlight-current-row>

      <el-table-column align="center" label="类目ID" prop="id"/>

      <el-table-column align="center" label="类目名" prop="title"/>

      <el-table-column align="center" label="全类目名" prop="fullTitle"/>

      <el-table-column
        :filters="[{ text: '一级类目', value: 'L1' }, { text: '二级类目', value: 'L2' }]"
        :filter-method="filterLevel"
        align="center"
        label="级别"
        prop="level">
        <template slot-scope="scope">
          <el-tag :type="scope.row.parentId === 0 ? 'primary' : 'info' ">{{ scope.row.parentId === 0 ? '一级类目' : '二级类目' }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="父类目ID" prop="parentId"/>

      <el-table-column align="center" label="操作" width="200" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-permission="['admin:category:update']" type="primary" size="mini" @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button v-permission="['admin:category:delete']" type="danger" size="mini" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <!-- 添加或修改对话框 -->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="dataForm" status-icon label-position="left" label-width="100px" style="width: 400px; margin-left:50px;">
        <el-form-item label="类目名称" prop="title">
          <el-input v-model="dataForm.title"/>
        </el-form-item>
        <el-form-item label="级别" prop="level">
          <el-select v-model="dataForm.level" @change="onLevelChange">
            <el-option label="一级类目" value="L1"/>
            <el-option label="二级类目" value="L2"/>
          </el-select>
        </el-form-item>
        <el-form-item v-if="dataForm.level === 'L2'" label="父类目" prop="parentId">
          <el-select v-model="dataForm.parentId">
            <el-option v-for="item in catL1" :key="item.value" :label="item.label" :value="item.value"/>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button v-if="dialogStatus=='create'" type="primary" @click="createData">确定</el-button>
        <el-button v-else type="primary" @click="updateData">确定</el-button>
      </div>
    </el-dialog>

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
import { listCategory, listCatL1, createCategory, updateCategory, deleteCategory } from '@/api/category'
import { uploadPath } from '@/api/storage'
import { getToken } from '@/utils/auth'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination

export default {
  name: 'Category',
  components: { Pagination },
  data() {
    return {
      uploadPath,
      list: undefined,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        id: undefined,
        title: undefined
      },
      catL1: {},
      dataForm: {
        id: undefined,
        title: '',
        level: 'L2',
        parentId: undefined,
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '创建'
      },
      rules: {
        title: [{ required: true, message: '类目名不能为空', trigger: 'blur' }]
      },
      downloadLoading: false
    }
  },
  computed: {
    headers() {
      return {
        'accessToken': getToken()
      }
    }
  },
  created() {
    this.getList()
    this.getCatL1()
  },
  methods: {
    getList() {
      this.listLoading = true
      listCategory(this.listQuery)
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
    getCatL1() {
      listCatL1().then(response => {
        this.catL1 = response.data.data
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    resetForm() {
      this.dataForm = {
        id: undefined,
        title: '',
        level: 'L2',
        parentId: undefined,
      }
    },
    filterLevel: function(value, row) {
      return row.level === value
    },
    onLevelChange: function(value) {
      if (value === 'L1') {
        this.parentId = undefined
      }
    },
    handleCreate() {
      this.resetForm()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          if ('L2' === this.dataForm.level && (!this.dataForm.parentId || this.dataForm.parentId === 0)) {
            this.$notify.error({
              title: '失败',
              message: '请选择父分类'
            })
          } else {
            createCategory(this.dataForm)
              .then(response => {
                this.list.unshift(response.data.data)
                // 更新L1目录
                this.getCatL1()
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
          }

        }
      })
    },
    handleUpdate(row) {
      if (row.parentId === 0) {
          row.level = 'L1'
      } else {
          row.level = 'L2'
      }
      this.dataForm = Object.assign({}, row)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          updateCategory(this.dataForm)
            .then(() => {
              for (const v of this.list) {
                if (v.id === this.dataForm.id) {
                  const index = this.list.indexOf(v)
                  this.list.splice(index, 1, this.dataForm)
                  break
                }
              }
              // 更新L1目录
              this.getCatL1()
              this.dialogFormVisible = false
              this.$notify.success({
                title: '成功',
                message: '更新成功'
              })
            })
            .catch(response => {
              this.$notify.error({
                title: '失败',
                message: response.data.errmsg
              })
            })
        }
      })
    },
    handleDelete(row) {
      deleteCategory(row.id)
        .then(response => {
          // 更新L1目录
          this.getCatL1()
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
  }
}
</script>
