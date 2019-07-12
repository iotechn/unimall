<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-button
        v-permission="['admin:nearby:adcreate']"
        class="filter-item"
        type="primary"
        icon="el-icon-edit"
        @click="handleCreate"
      >添加</el-button>

      <el-select
        v-model="listQuery.status"
        style="width: 200px"
        class="filter-item"
        placeholder="请选择广告状态"
      >
        <el-option v-for="(key,index) in adStatusMap" :key="index" :label="key.name" :value="key.value" />
      </el-select>
      <el-select
        v-model="listQuery.adType"
        style="width: 200px"
        class="filter-item"
        placeholder="请选择广告类型"
      >
        <el-option v-for="(key,index) in adTypeMap" :key="index" :label="key.name" :value="key.value" />
      </el-select>
      <el-button
        v-permission="['promote:advertisement:query']"
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
      <el-table-column align="center" label="广告ID" prop="id" sortable />

      <el-table-column align="center" label="广告标题" prop="title" />

      <el-table-column align="center" label="广告类型" prop="adType">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.adType | adTypeFilter }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="广告状态" prop="status">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.status | adStatusFilter }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="广告图片" prop="imgUrl">
        <template slot-scope="scope">
          <img v-if="scope.row.imgUrl" :src="scope.row.imgUrl" width="80" >
        </template>
      </el-table-column>

      <el-table-column align="center" label="活动链接" prop="url" />

      <el-table-column align="center" label="操作" width="200" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-permission="['promote:advertisement:update']"
            type="primary"
            size="mini"
            @click="handleUpdate(scope.row)"
          >编辑</el-button>
          <el-button
            v-permission="['promote:advertisement:delete']"
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
        <el-form-item label="隐藏的广告id" prop="id" hidden>
          <el-input v-model="dataForm.id" />
        </el-form-item>
        <el-form-item label="广告标题" prop="title">
          <el-input v-model="dataForm.title" />
        </el-form-item>
        <el-form-item label="广告图片" prop="imgUrl">
          <el-upload
            :headers="headers"
            :action="uploadPath"
            :show-file-list="false"
            :on-success="uploadUrl"
            class="avatar-uploader"
            accept=".jpg, .jpeg, .png, .gif"
          >
            <img v-if="dataForm.imgUrl" :src="dataForm.imgUrl" class="avatar" >
            <i v-else class="el-icon-plus avatar-uploader-icon" />
          </el-upload>
        </el-form-item>
        <el-form-item label="广告类型" prop="adType">
          <el-select v-model="dataForm.adType" placeholder="请选择">
            <el-option v-for="(key, index) in adTypeMap" :key="index" :label="key.name" :value="key.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="广告状态" prop="status">
          <el-select v-model="dataForm.status" placeholder="请选择">
            <el-option v-for="(key, index) in adStatusMap" :key="index" :label="key.name" :value="key.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="活动链接">
          <el-cascader
            :options="options"
            :props="{ checkStrictly: true }"
            placeholder="关联类目、商品"
            filterable
            @change="handleLink"
          />
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
import { listAd, createAd, updateAd, deleteAd } from '@/api/merchantad'
import { spuTree } from '@/api/goods'
import { uploadPath } from '@/api/storage'
import { getToken } from '@/utils/auth'
import Pagination from '@/components/Pagination'
import ElOption from '../../../node_modules/element-ui/packages/select/src/option' // Secondary package based on el-pagination

// const adTypeMap = {
//   1: '轮播',
//   2: '商户列表三图',
//   '': '全部'
// }

const adTypeMap = [{ value: 1, name: '轮播' }, { value: 2, name: '商户三图' }, { value: '', name: '全部' }]
const adStatusMap = [{ value: 0, name: '冻结' }, { value: 1, name: '激活' }, { value: '', name: '全部' }]
// const adStatusMap = {
//   0: '冻结',
//   1: '激活',
//   '': '全部'
// }

export default {
  name: 'Ad',
  components: {
    ElOption,
    Pagination
  },
  filters: {
    adTypeFilter(code) {
      return adTypeMap[code - 1]['name']
    },
    adStatusFilter(code) {
      return adStatusMap[code]['name']
    }
  },
  data() {
    return {
      uploadPath,
      options: [],
      value: [],
      list: [],
      total: 0,
      listLoading: true,
      adTypeMap,
      adStatusMap,
      listQuery: {
        pageNo: 1,
        limit: 20,
        status: undefined,
        adType: undefined
      },
      dataForm: {
        id: undefined,
        adType: undefined,
        title: undefined,
        url: '',
        imgUrl: undefined,
        status: undefined
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '创建'
      },
      rules: {
        title: [{ required: true, message: '广告标题不能为空', trigger: 'blur' }],
        imgUrl: [{ required: true, message: '广告图片不能为空', trigger: 'blur' }],
        adType: [{ required: true, message: '请选择广告类型', trigger: 'blur' }],
        status: [{ required: true, message: '请选择广告状态', trigger: 'blur' }]
      },
      downloadLoading: false
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
      listAd(this.listQuery)
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
    handleLink(e) {
      const tag = e[e.length - 1]
      let url = ''
      if (tag.startsWith('C')) {
        if (e.length < 3) {
          this.$notify.error({
            title: '失败',
            message: '请关联第三级类目'
          })
          return
        }
        url = '/pages/product/list?fid=' + e[0].substring(2) + '&tid=' + e[2].substring(2)
      } else {
        url = '/pages/product/product?id=' + (e[3].substring(2))
      }
      this.dataForm.url = url
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    resetForm() {
      this.dataForm = {
        id: undefined,
        title: undefined,
        type: undefined,
        picUrl: undefined,
        url: undefined
      }
    },
    refreshOptions() {
      if (this.options.length === 0) {
        spuTree().then(response => {
          this.options = response.data.data
        })
      }
    },
    handleCreate() {
      this.resetForm()
      this.refreshOptions()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    uploadUrl: function(response) {
      this.dataForm.imgUrl = response.url
    },
    createData() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          createAd(this.dataForm)
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
        }
      })
    },
    // 点击编辑按钮时的处理
    handleUpdate(row) {
      this.dataForm = Object.assign({}, row)
      this.refreshOptions()
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          updateAd(this.dataForm)
            .then(() => {
              for (const v of this.list) {
                if (v.id === this.dataForm.id) {
                  const index = this.list.indexOf(v)
                  this.list.splice(index, 1, this.dataForm)
                  break
                }
              }
              this.dialogFormVisible = false
              this.$notify.success({
                title: '成功',
                message: '更新广告成功'
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
      deleteAd(row.id, row.adType)
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
    }
  }
}
</script>
