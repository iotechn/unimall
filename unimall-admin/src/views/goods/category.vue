<template>
  <div>
    <div class="custom-tree-container">
      <div class="block">
        <el-tree
          :data="list"
          :props="defaultProps"
          :expand-on-click-node="false">
          <span slot-scope="{ node, data }" class="custom-tree-node">
            <span class="titleSize">{{ data.title }}</span>
            <span>
              <el-button
                type="primary"
                size="mini"
                round
                @click="handleAdd">
                添加
              </el-button>
              <el-button
                type="warning"
                size="mini"
                round
                @click="handleclick(data)">
                编辑
              </el-button>
              <el-button
                type="danger"
                size="mini"
                round
                @click="remove(node, data)">
                删除
              </el-button>
            </span>
          </span>
        </el-tree>
      </div>
    </div>

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
        <el-form-item label="类目ID" prop="id" hidden>
          <el-input v-model="dataForm.id" />
        </el-form-item>
        <el-form-item label="类目名称" prop="title">
          <el-input v-model="dataForm.title" @input="tlog" />
        </el-form-item>
        <!-- <el-form-item label="类目标签图片" prop="iconUrl">
          <el-upload
            :headers="headers"
            :action="uploadPath"
            :show-file-list="false"
            :on-success="iconUploadSuccessHandle"
            :before-upload="onBeforeUpload"
            class="avatar-uploader"
            accept=".jpg, .jpeg, .png, .gif"
          >
            <img v-if="dataForm.iconUrl" ref="adImg" :src="dataForm.iconUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon" />
          </el-upload>
        </el-form-item> -->
        <el-form-item label="类目图片" prop="picUrl">
          <el-upload
            :headers="headers"
            :action="uploadPath"
            :show-file-list="false"
            :on-success="pirUploadSuccessHandle"
            :before-upload="onBeforeUpload"
            class="avatar-uploader"
            accept=".jpg, .jpeg, .png, .gif"
          >
            <img v-if="dataForm.picUrl" ref="adImg" :src="dataForm.picUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon" />
          </el-upload>
        </el-form-item>

        <el-form-item label="父类目">
          <el-cascader
            :options="options"
            :props="{ checkStrictly: true ,label:'title',value:'id',children:'childrenList'}"
            v-model="dialogOptions"
            placeholder="选择父类目，默认一级类目"
            filterable
            clearable
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
    <!-- 确定删除 -->
    <!-- <el-dialog
      :visible.sync="dialogVisible"
      :before-close="handleClose"
      title="提示"
      width="30%">
      <span>确定删除吗？</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="remove(node,data)">确 定</el-button>
      </span>
    </el-dialog> -->
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
  .custom-tree-node {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 14px;
    padding-right: 8px;
  }
  .titleSize{
    font-size: 20px;
    padding: 5px 0;
  }

</style>

<script>
import { createCategory, updateCategory, deleteCategory, categoryTree } from '@/api/category'
import { uploadPath } from '@/api/storage'
import { getToken } from '@/utils/auth'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination
import { categorySecondLevelTree } from '@/api/category'

const categoryLevelMap = [{ text: '一级类目', value: 0 }, { text: '二级类目', value: 1 }, { text: '三级类目', value: 2 }]
export default {
  name: 'Category',
  components: { Pagination },
  filters: {
    categoryLevelFilter(code) {
      if (code >= 0 && code < 3) {
        return categoryLevelMap[code].text
      }
      return '错误级别'
    }
  },
  data() {
    return {
      options: [],
      uploadPath,
      list: undefined,
      total: 0,
      listLoading: true,
      dialogOptions: undefined,
      queryOptions: undefined,
      listQuery: {
        page: 1,
        limit: 20,
        id: undefined,
        title: undefined,
        level: undefined,
        parentId: undefined
      },
      catL1: {},
      dataForm: {
        id: undefined,
        title: undefined,
        level: undefined,
        parentId: undefined,
        iconUrl: undefined,
        picUrl: undefined
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
      downloadLoading: false,
      categoryLevelMap,

      defaultProps: {
        label: 'title',
        children: 'childrenList'
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
    this.refreshOptions()
  },
  methods: {
    handleclick(data) {
      this.dialogStatus = 'updata'
      this.dialogFormVisible = true
      // 对象的拷贝
      this.dataForm = Object.assign({}, data)
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    handleAdd() {
      this.handleCreate()
    },
    // 删除类目
    remove(node, data) {
      this.$confirm('此操作将永久删除该类目--' + data.title + '--, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 清除节点
        const parent = node.parent
        const children = parent.data.childrenList || parent.data
        const index = children.findIndex(d => d.id === data.id)
        children.splice(index, 1)
        deleteCategory(data.id)
          .then(response => {
            this.$notify.success({
              title: '成功',
              message: '删除成功'
            })

            this.$forceUpdate()
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
    tlog(e) {
      // console.log(e)
    },
    getList() {
      categoryTree().then(response => {
        this.list = response.data.data

        // this.total = response.data.data.total
      })
        .catch(() => {
          this.list = []
          this.total = 0
        })
    },
    handleFilter() {
      this.listQuery.page = 1
      if (this.listQuery.id != null && isNaN(Number(this.listQuery.id))) {
        this.$notify.error({
          title: '失败',
          message: '请输入整数'
        })
        return false
      }
      this.getList()
    },
    resetForm() {
      this.dataForm = {
        id: undefined,
        title: undefined,
        level: undefined,
        parentId: 0,
        iconUrl: undefined,
        picUrl: undefined
      }
    },
    resetQuery() {
      this.listQuery = {
        page: 1,
        limit: 20,
        id: undefined,
        title: undefined,
        level: undefined,
        parentId: undefined
      }
    },
    handleCreate() {
      this.resetForm()
      this.dialogStatus = 'create'
      this.dialogOptions = undefined
      this.queryOptions = undefined
      this.resetQuery()
      this.refreshOptions()
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['dataForm'].validate(valid => {
        createCategory(this.dataForm)
          .then(response => {
            this.resetQuery()
            this.listQuery.title = this.dataForm.title
            this.getList()
            this.dialogFormVisible = false
            this.$notify.success({
              title: '成功',
              message: '创建成功'
            })
            this.refreshOptions()
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
      this.dataForm = Object.assign({}, {
        id: row.value,
        title: row.label,
        parentId: row.parent,
        picUrl: row.picUrl,
        iconUrl: row.iconUrl
      })
      this.dialogOptions = row.parent
      // this.queryOptions = undefined
      // this.resetQuery()
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
          updateCategory(this.dataForm)
            .then(() => {
            // this.resetQuery()
            // this.listQuery.id = this.dataForm.id
              this.getList()
              this.dialogFormVisible = false
              this.$notify.success({
                title: '成功',
                message: '更新成功'
              })
              this.refreshOptions()
            })
            .catch(response => {
              this.$notify.error({
                title: '失败',
                message: '更新失败'
              })
            })
        }
      })
    },
    // 上传图片前调用
    onBeforeUpload(file) {
      const isIMAGE = file.type === 'image/jpeg' || 'image/gif' || 'image/png' || 'image/jpg'
      const isLt1M = file.size / 1024 / 1024 < 1

      if (!isIMAGE) {
        this.$message.error('上传文件只能是图片格式!')
      }
      if (!isLt1M) {
        this.$message.error('上传文件大小不能超过 1MB!')
      }
      return isIMAGE && isLt1M
    },
    // icon上传图片了处理图片
    iconUploadSuccessHandle(e) {
      this.dataForm.iconUrl = e.url
      this.dialogFormVisible = false
      this.dialogFormVisible = true
    },
    // pir上传图片了处理图片
    pirUploadSuccessHandle(e) {
      this.dataForm.picUrl = e.url
      this.dialogFormVisible = false
      this.dialogFormVisible = true
    },
    // 填写弹框选择父类目时，获得父类目的id
    handleLink(e) {
      console.log('===============================')
      console.log(e)
      if (e == null || e === undefined) {
        return false
      }
      if (e.length === 0) {
        this.dataForm.parentId = 0
        return true
      }
      const tag = e[e.length - 1]

      if (this.dialogStatus === 'update') {
        if (this.dataForm.id === tag) {
          this.$notify.error({
            title: '失败',
            message: '请不要选择本节点作为父节点'
          })
        }
      }
      this.dataForm.parentId = tag
    },
    // 查询框选择父类目时，获得父类目的id
    handleQuery(e) {
      if (e == null || e === undefined) {
        return false
      }
      this.refreshOptions()
      const tag = e[e.length - 1]
      this.listQuery.parentId = tag
    },
    // 刷新类目选择节点
    refreshOptions() {
      categorySecondLevelTree().then(response => {
        this.options = response.data.data
      })
    }

  }
}
</script>
