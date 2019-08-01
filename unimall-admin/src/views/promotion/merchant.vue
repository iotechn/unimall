<template>
  <div class="app-container">
    <el-card class="box-card">
      <h3>商铺信息</h3>
      <el-form ref="dataForm" :rules="rules" :model="dataForm" label-width="150px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="dataForm.title" />
        </el-form-item>
        <el-form-item label="商铺logo" prop="logoUrl">
          <el-upload
            :action="uploadPath"
            :show-file-list="false"
            :on-success="uploadSuccessHandle"
            :before-upload="onBeforeUpload"
            class="avatar-uploader"
            accept=".jpg, .jpeg, .png, .gif"
          >
            <img v-if="list.logoUrl" :src="dataForm.logoUrl" class="avatar" >
            <i v-else class="el-icon-plus avatar-uploader-icon" />
          </el-upload>
        </el-form-item>

        <el-form-item label="描述" prop="description">
          <el-input v-model="dataForm.description" />
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="dataForm.address" />
        </el-form-item>
        <!-- <el-form-item label="展示方式" prop="showType">
          <el-radio-group v-model="dataForm.showType">
            <el-radio :label="1">商品列表</el-radio>
            <el-radio :label="2">点餐列表</el-radio>
          </el-radio-group>
        </el-form-item> -->
      </el-form>
    </el-card>

    <div class="op-container">
      <el-button v-permission="['promote:merchant:create']" type="primary" @click="handleEdit">保存更改</el-button>
    </div>
  </div>
</template>

<style>
.el-tag + .el-tag {
  margin-left: 10px;
}

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
import { uploadPath } from '@/api/storage'
import Editor from '@tinymce/tinymce-vue'
import { getToken } from '@/utils/auth'
import { listMerchant, updateMerchant } from '@/api/merchant'

export default {
  name: 'GoodsEdit',
  components: { Editor },
  data() {
    return {
      uploadPath,
      list: [],
      dataForm: {
        title: undefined,
        logoUrl: undefined,
        description: undefined,
        address: undefined,
        showType: 1
      },
      rules: {
        //     showType: [{ required: true, message: '商铺展示类型不能为空', trigger: 'blur' }],
        address: [{ required: true, message: '商铺地址不能为空', trigger: 'blur' }],
        title: [{ required: true, message: '商铺标题不能为空', trigger: 'blur' }],
        description: [{ required: true, message: '商铺描述不能为空', trigger: 'blur' }]
      },
      listLoading: false
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

    getList: function() {
      this.listLoading = true
      listMerchant()
        .then(response => {
          this.list = response.data.data
          this.dataForm = Object.assign({}, this.list)
          this.listLoading = false
        })
        .catch(() => {
          this.list = []
          this.listLoading = false
        })
    },
    handleEdit: function() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          updateMerchant(this.dataForm)
            .then(response => {
              this.$notify.success({
                title: '成功',
                message: '更改成功'
              })
            })
            .catch(response => {
              this.$notify.error({
                title: '失败',
                message: '更改失败'
              })
            })
        } else {
          this.$notify.error({
            title: '失败',
            message: '请完善表单'
          })
        }
      })
    },
    // 上传图片了处理图片
    uploadSuccessHandle(e) {
      this.list.logoUrl = e.url
      this.dataForm.logoUrl = e.url
      this.dialogFormVisible = false
      this.dialogFormVisible = true
    },
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
    }
  }
}
</script>
