<template>
  <div>
    <el-card class="card">
      <div class="custom-tree-container">
        <div class="block">
          <el-tree
            :data="list"
            :props="{ label: 'title', children: 'childrenList' }"
            :expand-on-click-node="false"
            default-expand-all>
            <span slot-scope="{ node, data }" class="custom-tree-node">
              <div class="title-size">
                <el-image v-show="data.level === 2" :src="data.picUrl" class="c-img"/>
                {{ data.title }}
                <el-tag v-show="data.id !== 0" :type="data.level === 0 ? 'danger' : (data.level === 1 ? 'warning' : 'success')">
                  {{ data.level === 0 ? '一级' : (data.level === 1 ? '二级' : '三级') }}
                </el-tag>
              </div>

              <span>
                <el-button
                  v-show="data.level !== 2 "
                  type="primary"
                  size="mini"
                  round
                  @click="handleCreate(data)">
                  添加
                </el-button>
                <el-button
                  v-show="data.id !== 0"
                  type="warning"
                  size="mini"
                  round
                  @click="handleUpdate(data)">
                  编辑
                </el-button>
                <el-button
                  v-show="!data.childrenList || data.childrenList.length <= 0"
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
    </el-card>

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
          <el-input v-model="dataForm.title" />
        </el-form-item>
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
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button v-if="dialogStatus=='create'" :loading="submiting" type="primary" @click="createData">确定</el-button>
        <el-button v-else :loading="submiting" type="primary" @click="updateData">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { createCategory, updateCategory, deleteCategory, categoryTree } from '@/api/category'
import { uploadPath } from '@/api/storage'
import { getToken } from '@/utils/auth'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination

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
      categoryLevelMap,
      uploadPath,
      list: [],
      dialogOptions: undefined,
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
      currentParent: undefined,
      current: undefined,
      rules: {
        title: [{ required: true, message: '类目名不能为空', trigger: 'blur' }]
      },
      submiting: false
    }
  },
  computed: {
    headers() {
      return {
        ADMINTOKEN: getToken()
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    handleUpdate(row) {
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.current = row
      // 对象的拷贝
      this.dataForm = Object.assign({}, row)
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    // 删除类目
    remove(node, data) {
      this.$confirm('此操作将永久删除该类目--' + data.title + '--, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 清除节点
        deleteCategory(data.id)
          .then(response => {
            this.$notify.success({
              title: '成功',
              message: '删除成功'
            })
            const parent = node.parent
            const children = parent.data.childrenList || parent.data
            const index = children.findIndex(d => d.id === data.id)
            children.splice(index, 1)
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
    getList() {
      categoryTree().then(response => {
        const l = response.data.data
        // 为一二级添加空子树
        for (let i = 0; i < l.length; i++) {
          if (!l[i].childrenList) {
            l[i].childrenList = []
          } else {
            const c = l[i].childrenList
            for (let j = 0; j < c.length; j++) {
              if (!c[j].childrenList) {
                c[j].childrenList = []
              }
            }
          }
        }
        this.list = [{
          title: '全部类目',
          id: 0,
          childrenList: response.data.data
        }]
      })
        .catch(() => {
          this.list = []
          this.total = 0
        })
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
    handleCreate(row) {
      this.resetForm()
      this.dataForm.parentId = row.id
      this.currentParent = row
      this.dialogStatus = 'create'
      this.dialogOptions = undefined
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['dataForm'].validate(valid => {
        this.submiting = true
        createCategory(this.dataForm)
          .then(response => {
            if (response.data.data.level < 2) {
              response.data.data.childrenList = []
            }
            this.currentParent.childrenList.push(response.data.data)
            this.dialogFormVisible = false
            this.$notify.success({
              title: '成功',
              message: '创建成功'
            })
            this.submiting = false
          })
          .catch(response => {
            this.submiting = false
            this.$notify.error({
              title: '失败',
              message: response.data.errmsg
            })
          })
      })
    },
    updateData() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          updateCategory(this.dataForm)
            .then((res) => {
              this.current.title = res.data.data.title
              this.current.iconUrl = res.data.data.iconUrl
              this.current.picUrl = res.data.data.picUrl
              debugger
              this.dialogFormVisible = false
              this.$notify.success({
                title: '成功',
                message: '更新成功'
              })
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
    }
  }
}
</script>

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
  font-size: 18px;
  padding: 8px;
}
.title-size {
  padding: 5px 0;
}
.card {
  margin: 10px;
}
.el-tree-node__content {
  height: 40px;
}
.c-img {
  width: 30px;
  height: 30px;
}
</style>
