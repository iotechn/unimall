<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-cascader
        :options="options"
        style="width: 200px"
        class="filter-item"
        placeholder="请选择搜索商品SPU"
        clearable
        @change="handleQuery"
      />
      <el-select v-model="listQuery.skuId" clearable placeholder="请选择SKU">
        <el-option
          v-for="item in selectOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value"/>
      </el-select>
      <el-button
        v-permission="['plugin:autoship:list']"
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="handleFilter"
      >查找</el-button>
      <el-button
        v-permission="['plugin:autoship:create']"
        class="filter-item"
        type="primary"
        icon="el-icon-edit"
        @click="handleCreate"
      >添加</el-button>
      <el-button
        v-permission="['plugin:autoship:batchCreate']"
        class="filter-item"
        type="primary"
        icon="el-icon-create"
        @click="batchCreateVisible = true"
      >xls批量添加</el-button>
      <el-button
        v-permission="['plugin:autoship:batchCreate']"
        class="filter-item"
        type="primary"
        icon="el-icon-download"
        @click="downloadXls"
      >下载xls表头</el-button>
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
      <el-table-column align="center" label="Id" prop="id" width="100" />

      <el-table-column align="center" label="skuId" prop="skuId" width="100"/>

      <el-table-column align="center" label="商品名称" prop="title">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.spuTitle + '-' + scope.row.skuTitle }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="CDK" prop="cdk" />

      <el-table-column align="center" label="可用次数" prop="times" width="120" />

      <el-table-column align="center" label="操作" width="200" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-permission="['plugin:autoship:edit']"
            type="primary"
            size="mini"
            @click="handleUpdate(scope.row)"
          >编辑</el-button>
          <el-button
            v-permission="['plugin:autoship:delete']"
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

    <!-- 添加或修改对话框 -->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="dataForm"
        status-icon
        label-position="left"
        label-width="100px"
        style="width: 400px; margin-left:50px;"
      >
        <el-form-item label="关联SPU">
          <el-cascader
            :options="options"
            placeholder="请选择关联商品"
            clearable
            @change="handleQuery"
          />
        </el-form-item>
        <el-form-item label="关联SKU">
          <el-select v-model="dataForm.skuId" clearable placeholder="请选择SKU">
            <el-option
              v-for="item in selectOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"/>
          </el-select>
        </el-form-item>

        <el-form-item label="CDK" prop="cdk">
          <el-input v-model="dataForm.cdk"/>
        </el-form-item>
        <el-form-item label="使用次数" prop="times">
          <el-input v-model="dataForm.times"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button v-if="dialogStatus=='create'" type="primary" @click="createData">确定</el-button>
        <el-button v-else type="primary" @click="updateData">确定</el-button>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="batchCreateVisible" title="添加">
      <el-form
        ref="dataForm"
        :model="temp"
        status-icon
        label-position="left"
        label-width="100px"
        style="width: 400px; margin-left:50px;"
      >
        <el-upload
          :on-change="importExcel"
          action="/"
          drag
          accept=".xls, .xlsx">
          <i class="el-icon-upload"/>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <div slot="tip" class="el-upload__tip">{{ xlsArray.length > 0 ? '已经获取到数据，您可以点确定或重新上传' : '只能上传xls/xlxs文件，且不超过500kb' }}</div>
        </el-upload>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="batchCreateVisible = false">取消</el-button>
        <el-button type="primary" @click="handleBatchCreate">确定</el-button>
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
import { listAutoShip, createAutoShip, updateAutoShip, deleteAutoShip, batchCreateAutoShip } from '@/api/plugins/autoship'
import { getToken } from '@/utils/auth'
import { clearTreeEmptyChildren } from '@/utils/index'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination
import { spuTree, detailGoods } from '@/api/goods'
import XLSX from 'xlsx'

export default {
  name: 'Category',
  components: { Pagination },
  data() {
    return {
      options: [],
      list: undefined,
      total: 0,
      listLoading: true,
      xlsArray: [],
      selectOptions: [],
      listQuery: {
        pageNo: 1,
        limit: 20,
        skuId: undefined
      },
      dataForm: {
        skuId: undefined,
        cdk: undefined,
        times: 1
      },
      batchCreateVisible: false,
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '创建'
      },
      rules: {
        cdk: [{ required: true, message: 'CDK不能为空', trigger: 'blur' }],
        times: [{ required: true, message: '可用次数', trigger: 'blur' }]
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
      listAutoShip(this.listQuery)
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
      spuTree()
        .then(response => {
          this.options = clearTreeEmptyChildren(response.data.data)
        })
    },
    handleFilter() {
      this.listQuery.pageNo = 1
      this.getList()
    },
    handleQuery(option) {
      // 当SPU选择改变，获取SKU列表
      if (option.length === 4) {
        const spuId = option[3].substring(2)
        detailGoods(spuId)
          .then(res => {
            const selectOptions = []
            res.data.data.skuList.forEach(item => {
              selectOptions.push({
                value: item.id,
                label: item.title
              })
            })
            this.selectOptions = selectOptions
          })
      } else {
        this.$notify().error({
          title: '失败',
          message: '请选择Spu'
        })
      }
    },
    resetForm() {
      this.dataForm = {
        id: undefined,
        skuId: undefined,
        cdk: undefined,
        times: 1
      }
    },
    resetQuery() {
      this.listQuery = {
        pageNo: 1,
        limit: 20,
        skuId: undefined
      }
    },
    handleCreate() {
      this.resetForm()
      this.dialogStatus = 'create'
      this.resetQuery()
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['dataForm'].validate(valid => {
        createAutoShip(this.dataForm)
          .then(response => {
            this.resetQuery()
            this.getList()
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
      })
    },
    handleUpdate(row) {
      debugger
      this.dataForm = Object.assign({}, row)
      this.dialogOptions = row.parent
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          updateAutoShip(this.dataForm)
            .then(() => {
            // this.resetQuery()
            // this.listQuery.id = this.dataForm.id
              this.getList()
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
    handleBatchCreate() {
      if (this.xlsArray.length === 0) {
        this.$message.error({
          title: '失败',
          message: '表格为空'
        })
      } else {
        batchCreateAutoShip(this.xlsArray)
          .then(res => {
            this.$message.success({
              title: '成功',
              message: '提交成功，请在数秒后刷新'
            })
            this.batchCreateVisible = false
            this.getList()
          })
          .catch(failres => {
            this.$message.error({
              title: '失败',
              message: failres.data.errmsg
            })
          })
      }
    },
    // 删除时使用
    handleDelete(row) {
      this.$confirm('此操作将永久删除该CDK--' + row.label + '--, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteAutoShip(row.id)
          .then(response => {
            this.$notify.success({
              title: '成功',
              message: '删除成功'
            })
            this.getList()
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
    file2Xce(file) {
      return new Promise(function(resolve, reject) {
        const reader = new FileReader()
        reader.onload = function(e) {
          const data = e.target.result
          this.wb = XLSX.read(data, {
            type: 'binary'
          })
          const result = []
          this.wb.SheetNames.forEach((sheetName) => {
            result.push({
              sheetName: sheetName,
              sheet: XLSX.utils.sheet_to_json(this.wb.Sheets[sheetName])
            })
          })
          resolve(result)
        }
        reader.readAsBinaryString(file.raw)
        // reader.readAsBinaryString(file) // 传统input方法
      })
    },
    importExcel(file) {
      this.file2Xce(file).then(tabJson => {
        if (tabJson && tabJson.length !== 1) {
          this.$message('每个文件有且仅有一个工作薄')
          return
        }
        this.xlsArray = tabJson[0].sheet
      })
    },
    downloadXls() {
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = [
          'cdk',
          'skuId',
          'times'
        ]
        const filterVal = [
          'cdk',
          'skuId',
          'times'
        ]
        excel.export_json_to_excel2(tHeader, [{
          'number': '',
          'title': '',
          'times': ''
        }], filterVal, 'CDK导入模板')
      })
    }

  }
}
</script>
