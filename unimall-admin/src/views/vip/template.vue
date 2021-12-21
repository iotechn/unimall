<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-input v-model="listQuery.title" clearable class="filter-item" style="width: 200px;" placeholder="请输入VIP套餐名称" />
      <el-select v-model="listQuery.display" style="width: 200px" class="filter-item" placeholder="请选择是否给前端使用" clearable>
        <el-option v-for="(key,index) in displayDic" :key="index" :label="key.name" :value="key.value" />
      </el-select>
      <el-button v-permission="['vip:template:list']" class="filter-item" type="primary" icon="el-icon-search" @click="getList" >查找</el-button>
      <el-button v-permission="['vip:template:create']" class="filter-item" type="primary" icon="el-icon-edit" @click="openCreateDialogBtn" >添加</el-button>
    </div>

    <!-- 查询结果 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      size="small"
      element-loading-text="正在查询中。。。"
      border
      fit
      highlight-current-row >
      <el-table-column align="center" label="ID" prop="id" width="80"/>
      <el-table-column align="center" label="套餐名" prop="title" min-width="200" />
      <el-table-column align="center" label="套餐天数" prop="dayNum" />
      <el-table-column align="center" label="描述" prop="description" min-width="200" />
      <el-table-column align="center" label="套餐原价" prop="originalPrice">
        <template slot-scope="scope">￥{{ scope.row.originalPrice / 100 }}</template>
      </el-table-column>
      <el-table-column align="center" label="套餐现价" prop="price">
        <template slot-scope="scope">￥{{ scope.row.price / 100 }}</template>
      </el-table-column>
      <el-table-column align="center" label="前端能否展示" prop="display">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.display === 0? '隐藏': '展示' }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="操作" width="200" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="scope">
          <el-button v-permission="['vip:template:edit']" type="primary" size="mini" @click="openUpdateDialogBtn(scope.row)" >编辑</el-button>
          <el-button v-permission="['vip:template:delete']" type="primary" size="mini" @click="deleteDataBtn(scope.row)" >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.pageNo" :limit.sync="listQuery.limit" @pagination="getList" />

    <!-- 添加或修改对话框 -->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" center>
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="dataForm"
        status-icon
        label-position="left"
        label-width="130px"
        style="width: 400px; margin-left:50px;">
        <el-form-item label="套餐名称" prop="title">
          <el-input v-model="dataForm.title" maxlength="250" show-word-limit/>
        </el-form-item>
        <el-form-item label="套餐天数(天)" prop="dayNum">
          <el-input-number v-model="dataForm.dayNum" :precision="0" :min="1" />
        </el-form-item>
        <el-form-item label="套餐原价（元）" prop="originalPrice">
          <el-input-number v-model="dataForm.originalPrice" :precision="2" :min="0.01" />
        </el-form-item>
        <el-form-item label="套餐现价（元）" prop="price">
          <el-input-number v-model="dataForm.price" :precision="2" :min="0.01" />
        </el-form-item>
        <el-form-item label="是否前端展示" prop="display">
          <el-select v-model="dataForm.display" placeholder="请选择">
            <el-option v-for="(key, index) in displayDic" :key="index" :label="key.name" :value="key.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="简介" prop="description">
          <el-input v-model="dataForm.description" type="textarea" maxlength="250" show-word-limit/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button v-if="dialogStatus=='create'" type="primary" @click="createDataBtn">确定</el-button>
        <el-button v-else type="primary" @click="updateDataBtn">更新</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { create, list, deleteTemplate, edit } from '@/api/vip-template'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination

export default {
  name: 'VipTemplate',
  components: { Pagination },
  data() {
    return {
      list: [],
      total: 0,
      listLoading: true,
      listQuery: {
        pageNo: 1,
        limit: 20,
        title: undefined,
        display: undefined
      },
      displayDic: [{ value: 0, name: '不显示' }, { value: 1, name: '显示' }],
      dataForm: {},
      vipTypeDetail: '',
      detailDialogVisible: false,
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: { update: '编辑', create: '创建' },
      rules: {
        title: [{ required: true, message: '套餐名不能为空', trigger: 'blur' }],
        dayNum: [{ required: true, message: '套餐天数不能为空', trigger: 'blur' }],
        originalPrice: [{ required: true, message: '套餐原价不能为空', trigger: 'blur' }],
        price: [{ required: true, message: '套餐现价不能为空', trigger: 'blur' }],
        display: [{ required: true, message: '是否在前端显示不能为空', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
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
    resetForm() {
      this.dataForm = {
        id: undefined,
        title: undefined,
        description: undefined,
        dayNum: 0,
        originalPrice: 0,
        price: 0,
        display: undefined
      }
    },
    openCreateDialogBtn() {
      this.resetForm()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createDataBtn() {
      const queryData = Object.assign({}, this.dataForm)
      const that = this
      queryData.originalPrice = queryData.originalPrice * 100
      queryData.price = queryData.price * 100
      that.$refs['dataForm'].validate(valid => {
        if (!valid) {
          return false
        }
        create(queryData)
          .then(response => {
            that.$notify.success({
              title: '成功',
              message: '创建成功'
            })
            that.list.push(response.data.data)
            that.dialogFormVisible = false
          })
          .catch(response => {
            that.$notify.error({
              title: '失败',
              message: response.data.errmsg
            })
          })
      })
    },
    openUpdateDialogBtn(row) {
      this.dataForm = Object.assign({}, {
        id: row.id,
        title: row.title,
        description: row.description,
        dayNum: row.dayNum,
        originalPrice: row.originalPrice / 100,
        price: row.price / 100,
        display: row.display
      })
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateDataBtn() {
      const queryData = Object.assign({}, this.dataForm)
      const that = this
      queryData.originalPrice = queryData.originalPrice * 100
      queryData.price = queryData.price * 100
      that.$refs['dataForm'].validate(valid => {
        if (!valid) {
          return false
        }
        if (valid) {
          edit(queryData)
            .then(() => {
              that.getList()
              that.dialogFormVisible = false
              that.$notify.success({
                title: '成功',
                message: '更新成功'
              })
            })
            .catch(response => {
              that.$notify.error({
                title: '失败',
                message: response.data.errmsg
              })
            })
        }
      })
    },
    deleteDataBtn(row) {
      this.$confirm('此操作将永久删除该套餐--' + row.title + '--, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteTemplate(row.id)
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
      }).catch(() => {
        return false
      })
    }
  }
}
</script>
