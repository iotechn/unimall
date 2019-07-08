<template>
  <div class="app-container">

    <el-card class="box-card">
      <h3>商品介绍</h3>
      <el-form ref="dataForm" :rules="rules" :model="goods" label-width="150px">
        <el-form-item label="商品编号" prop="goodsSn">
          <el-input v-model="goods.goodsSn"/>
        </el-form-item>
        <el-form-item label="商品名称" prop="title">
          <el-input v-model="goods.title"/>
        </el-form-item>
        <el-form-item label="原始价格" prop="originalPriceRaw">
          <el-input v-model="goods.originalPriceRaw" placeholder="0.00">
            <template slot="append">元</template>
          </el-input>
        </el-form-item>
        <el-form-item label="当前价格" prop="priceRaw">
          <el-input v-model="goods.priceRaw" placeholder="0.00">
            <template slot="append">元</template>
          </el-input>
        </el-form-item>

        <el-form-item label="剩余库存" prop="stock">
          <el-input v-model="goods.stock" placeholder="0">
          </el-input>
        </el-form-item>


        <el-form-item label="是否在售" prop="status">
          <el-radio-group v-model="goods.status">
            <el-radio :label="1">在售</el-radio>
            <el-radio :label="2">下架</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="商品图片">
          <el-upload
            :action="uploadPath"
            :headers="headers"
            :limit="5"
            :on-exceed="uploadOverrun"
            :on-success="handleGalleryUrl"
            :on-remove="handleRemove"
            multiple
            accept=".jpg,.jpeg,.png,.gif"
            list-type="picture-card">
            <i class="el-icon-plus"/>
          </el-upload>
        </el-form-item>

        <el-form-item label="商品单位" prop="unit">
          <el-input v-model="goods.unit" placeholder="件 / 个 / 盒"/>
        </el-form-item>

        <el-form-item label="所属类目" prop="category">
          <el-cascader :options="categoryList" v-model="categoryIds" expand-trigger="hover" @change="handleCategoryChange"/>
        </el-form-item>

        <el-form-item label="商品简介" prop="description">
          <el-input v-model="goods.description"/>
        </el-form-item>

        <el-form-item label="商品详细介绍" prop="detail">
          <editor :init="editorInit" v-model="goods.detail"/>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card class="box-card">
      <h3>商品参数</h3>
      <el-button :plain="true" type="primary" @click="handleAttributeShow">添加</el-button>
      <el-table :data="attributes">
        <el-table-column property="attribute" label="商品参数名称"/>
        <el-table-column property="value" label="商品参数值"/>
        <el-table-column align="center" label="操作" width="100" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button type="danger" size="mini" @click="handleAttributeDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-dialog :visible.sync="attributeVisiable" title="设置商品参数">
        <el-form
          ref="attributeForm"
          :model="attributeForm"
          status-icon
          label-position="left"
          label-width="100px"
          style="width: 400px; margin-left:50px;">
          <el-form-item label="商品参数名称" prop="attribute">
            <el-input v-model="attributeForm.attribute"/>
          </el-form-item>
          <el-form-item label="商品参数值" prop="value">
            <el-input v-model="attributeForm.value"/>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="attributeVisiable = false">取消</el-button>
          <el-button type="primary" @click="handleAttributeAdd">确定</el-button>
        </div>
      </el-dialog>
    </el-card>

    <div class="op-container">
      <el-button @click="handleCancel">取消</el-button>
      <el-button type="primary" @click="handlePublish">上架</el-button>
    </div>

  </div>
</template>

<style>
  .el-card {
    margin-bottom: 10px;
  }

  .el-tag + .el-tag {
    margin-left: 10px;
  }

  .input-new-keyword {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
  }

  .avatar-uploader .el-upload {
    width: 145px;
    height: 145px;
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
import { publishGoods, listCatAndBrand } from '@/api/goods'
import { createStorage, uploadPath } from '@/api/storage'
import Editor from '@tinymce/tinymce-vue'
import { MessageBox } from 'element-ui'
import { getToken } from '@/utils/auth'

export default {
  name: 'GoodsCreate',
  components: { Editor },

  data() {
    return {
      uploadPath,
      categoryList: [],
      categoryIds: [],
      goods: {imgs: [] },
      attributeVisiable: false,
      attributeForm: { attribute: '', value: '' },
      attributes: [],
      rules: {
        status :[{ required: true, message: '请选择商品状态', trigger: 'blur' }],
        goodsSn: [{ required: true, message: '商品编号不能为空', trigger: 'blur' }],
        title: [{ required: true, message: '商品名称不能为空', trigger: 'blur' }],
        description: [{ required: true, message: '商品描述不能为空', trigger: 'blur' }],
        priceRaw: [{ required: true, message: '商品现价不能为空', trigger: 'blur' }],
        originalPriceRaw :[{ required: true, message: '商品原价不能为空', trigger: 'blur' }],
        stock: [{ required: true, message: '剩余库存不能为空', trigger: 'blur' }],
        unit :[{ required: true, message: '物件单位不能为空', trigger: 'blur' }],
//        category :[{ required: true, message: '商品分类不能为空', trigger: 'blur' }],
        detail: [{ required: true, message: '请填写商品详情', trigger: 'blur' }],
      },
      editorInit: {
        language: 'zh_CN',
        convert_urls: false,
        plugins: ['advlist anchor autolink autosave code codesample colorpicker colorpicker contextmenu directionality emoticons fullscreen hr image imagetools importcss insertdatetime link lists media nonbreaking noneditable pagebreak paste preview print save searchreplace spellchecker tabfocus table template textcolor textpattern visualblocks visualchars wordcount'],
        toolbar: ['searchreplace bold italic underline strikethrough alignleft aligncenter alignright outdent indent  blockquote undo redo removeformat subscript superscript code codesample', 'hr bullist numlist link image charmap preview anchor pagebreak insertdatetime media table emoticons forecolor backcolor fullscreen'],
        images_upload_handler: function(blobInfo, success, failure) {
          const formData = new FormData()
          formData.append('file', blobInfo.blob())
          createStorage(formData).then(res => {
            success(res.data.url)
          }).catch(() => {
            failure('上传失败，请重新上传')
          })
        }
      }
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
    this.init()
  },

  methods: {
    init: function() {
      listCatAndBrand().then(response => {
        this.categoryList = response.data.data
      })
    },
    handleCategoryChange(value) {
      this.goods.categoryId = value[value.length - 1]
    },
    handleCancel: function() {
      this.$router.push({ path: '/goods/goods' })
    },
    handlePublish: function() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          if (!this.goods.categoryId) {
            this.$notify.error({
              title: '失败',
              message: '请选择分类'
            })
          } else {
            this.goods.price = parseInt(this.goods.priceRaw * 100)
            this.goods.originalPrice = parseInt(this.goods.originalPriceRaw * 100)
            const finalGoods = {
              goods: this.goods,
              specifications: this.specifications,
              products: this.products,
              attributes: this.attributes
            }
            publishGoods(finalGoods).then(response => {
              this.$notify.success({
                title: '成功',
                message: '创建成功'
              })
              this.$router.push({ path: '/goods/list' })
            }).catch(response => {
              MessageBox.alert('业务错误：' + response.data.errmsg, '警告', {
                confirmButtonText: '确定',
                type: 'error'
              })
            })
          }
        } else {
          this.$notify.error({
            title: '失败',
            message: '请完善表单'
          })
        }
      })

    },
    uploadPicUrl: function(response) {
      this.goods.picUrl = response.url
    },
    uploadOverrun: function() {
      this.$message({
        type: 'error',
        message: '上传文件个数超出限制!最多上传5张图片!'
      })
    },
    handleGalleryUrl(response, file, fileList) {
      if (response.errno === 200) {
        this.goods.imgs.push(response.url)
        this.goods.img = this.goods.imgs[0]
      }
    },
    handleRemove: function(file, fileList) {
      for (var i = 0; i < this.goods.imgs.length; i++) {
        // 这里存在两种情况
        // 1. 如果所删除图片是刚刚上传的图片，那么图片地址是file.response.url
        //    此时的file.url虽然存在，但是是本机地址，而不是远程地址。
        // 2. 如果所删除图片是后台返回的已有图片，那么图片地址是file.url
        var url
        if (file.response === undefined) {
          url = file.url
        } else {
          url = file.response.url
        }

        if (this.goods.imgs[i] === url) {
          this.goods.imgs.splice(i, 1)
        }
      }
    },
    specChanged: function(label) {
      if (label === false) {
        this.specifications = [{ specification: '规格', value: '标准', picUrl: '' }]
        this.products = [{ id: 0, specifications: ['标准'], price: 0.00, number: 0, url: '' }]
      } else {
        this.specifications = []
        this.products = []
      }
    },
    uploadSpecPicUrl: function(response) {
      this.specForm.picUrl = response.url
    },
    handleSpecificationShow() {
      this.specForm = { specification: '', value: '', picUrl: '' }
      this.specVisiable = true
    },
    handleSpecificationAdd() {
      var index = this.specifications.length - 1
      for (var i = 0; i < this.specifications.length; i++) {
        const v = this.specifications[i]
        if (v.specification === this.specForm.specification) {
          index = i
        }
      }

      this.specifications.splice(index + 1, 0, this.specForm)
      this.specVisiable = false

      this.specToProduct()
    },
    handleSpecificationDelete(row) {
      const index = this.specifications.indexOf(row)
      this.specifications.splice(index, 1)
      this.specToProduct()
    },
    specToProduct() {
      if (this.specifications.length === 0) {
        return
      }
      // 根据specifications创建临时规格列表
      var specValues = []
      var spec = this.specifications[0].specification
      var values = []
      values.push(0)

      for (var i = 1; i < this.specifications.length; i++) {
        const aspec = this.specifications[i].specification

        if (aspec === spec) {
          values.push(i)
        } else {
          specValues.push(values)
          spec = aspec
          values = []
          values.push(i)
        }
      }
      specValues.push(values)

      // 根据临时规格列表生产货品规格
      // 算法基于 https://blog.csdn.net/tyhj_sf/article/details/53893125
      var productsIndex = 0
      var products = []
      var combination = []
      var n = specValues.length
      for (var s = 0; s < n; s++) {
        combination[s] = 0
      }
      var index = 0
      var isContinue = false
      do {
        var specifications = []
        for (var x = 0; x < n; x++) {
          var z = specValues[x][combination[x]]
          specifications.push(this.specifications[z].value)
        }
        products[productsIndex] = { id: productsIndex, specifications: specifications, price: 0.00, number: 0, url: '' }
        productsIndex++

        index++
        combination[n - 1] = index
        for (var j = n - 1; j >= 0; j--) {
          if (combination[j] >= specValues[j].length) {
            combination[j] = 0
            index = 0
            if (j - 1 >= 0) {
              combination[j - 1] = combination[j - 1] + 1
            }
          }
        }
        isContinue = false
        for (var p = 0; p < n; p++) {
          if (combination[p] !== 0) {
            isContinue = true
          }
        }
      } while (isContinue)

      this.products = products
    },
    handleProductShow(row) {
      this.productForm = Object.assign({}, row)
      this.productVisiable = true
    },
    uploadProductUrl: function(response) {
      this.productForm.url = response.data.url
    },
    handleProductEdit() {
      for (var i = 0; i < this.products.length; i++) {
        const v = this.products[i]
        if (v.id === this.productForm.id) {
          this.products.splice(i, 1, this.productForm)
          break
        }
      }
      this.productVisiable = false
    },
    handleAttributeShow() {
      this.attributeForm = {}
      this.attributeVisiable = true
    },
    handleAttributeAdd() {
      this.attributes.unshift(this.attributeForm)
      this.attributeVisiable = false
    },
    handleAttributeDelete(row) {
      const index = this.attributes.indexOf(row)
      this.attributes.splice(index, 1)
    }
  }
}
</script>
