<template>
  <div class="app-container">
    <el-card class="box-card">
      <h3>商铺信息</h3>
      <el-form ref="dataForm" :rules="rules" :model="nearby" label-width="150px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="nearby.title" />
        </el-form-item>
        <el-form-item label="商铺logo">
          <el-upload
            :action="uploadPath"
            :show-file-list="false"
            :on-success="handleimgsUrl"
            :on-remove="handleRemove"
            class="avatar-uploader"
          >
            <img v-if="nearby.logoUrl" :src="nearby.logoUrl" class="avatar" >
            <i v-else class="el-icon-plus avatar-uploader-icon" />
          </el-upload>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="nearby.description" />
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="nearby.address" />
        </el-form-item>
        <el-form-item label="H5链接" prop="h5url">
          <el-input v-model="nearby.h5url" />
        </el-form-item>
        <el-form-item label="展示方式" prop="type">
          <el-radio-group v-model="nearby.type">
            <el-radio :label="1">商品列表</el-radio>
            <el-radio :label="2">点餐列表</el-radio>
            <el-radio :label="3">广告商户</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="nearby.status">
            <el-radio :label="1">正在营业</el-radio>
            <el-radio :label="2">暂停营业</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
    </el-card>

    <div class="op-container">
      <!--<el-button @click="handleCancel">取消</el-button>-->
      <el-button type="primary" @click="handleEdit">保存更改</el-button>
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
import { detail, upsert } from '@/api/nearby'
import { createStorage, uploadPath } from '@/api/storage'
import Editor from '@tinymce/tinymce-vue'
import { MessageBox } from 'element-ui'
import { getToken } from '@/utils/auth'

export default {
  name: 'GoodsEdit',
  components: { Editor },
  data() {
    return {
      uploadPath,
      nearby: { title: undefined, status: undefined, logoUrl: undefined },
      rules: {
        type: [
          { required: true, message: '商铺展示类型不能为空', trigger: 'blur' }
        ],
        address: [
          { required: true, message: '商铺地址不能为空', trigger: 'blur' }
        ],
        title: [
          { required: true, message: '商铺标题不能为空', trigger: 'blur' }
        ],
        description: [
          { required: true, message: '商铺描述不能为空', trigger: 'blur' }
        ],
        status: [
          { required: true, message: '商铺描述不能为空', trigger: 'blur' }
        ]
      },
      editorInit: {
        language: 'zh_CN',
        convert_urls: false,
        plugins: [
          'advlist anchor autolink autosave code codesample colorpicker colorpicker contextmenu directionality emoticons fullscreen hr image imagetools importcss insertdatetime link lists media nonbreaking noneditable pagebreak paste preview print save searchreplace spellchecker tabfocus table template textcolor textpattern visualblocks visualchars wordcount'
        ],
        toolbar: [
          'searchreplace bold italic underline strikethrough alignleft aligncenter alignright outdent indent  blockquote undo redo removeformat subscript superscript code codesample',
          'hr bullist numlist link image charmap preview anchor pagebreak insertdatetime media table emoticons forecolor backcolor fullscreen'
        ],
        images_upload_handler: function(blobInfo, success, failure) {
          const formData = new FormData()
          formData.append('file', blobInfo.blob())
          createStorage(formData)
            .then(res => {
              success(res.data.data.url)
            })
            .catch(() => {
              failure('上传失败，请重新上传')
            })
        }
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
    this.init()
  },
  methods: {
    init: function() {
      detail().then(response => {
        debugger
        this.nearby = response.data.data
      })
    },
    //    handleCancel: function() {
    //      this.$router.push({ path: '/nearby/list' })
    //    },
    handleEdit: function() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          upsert(this.nearby)
            .then(response => {
              this.$notify.success({
                title: '成功',
                message: '保存成功'
              })
              this.$router.push({ path: '/promotion/merchant' })
            })
            .catch(response => {
              MessageBox.alert('业务错误：' + response.data.errmsg, '警告', {
                confirmButtonText: '确定',
                type: 'error'
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
    uploadOverrun: function() {
      this.$message({
        type: 'error',
        message: '上传文件个数超出限制!最多上传5张图片!'
      })
    },
    handleimgsUrl(response, file, fileList) {
      if (response.errno === 200) {
        this.nearby.logoUrl = response.url
      }
    },
    handleRemove: function(file, fileList) {
      this.nearby.logoUrl = undefined
    }
  }
}
</script>
