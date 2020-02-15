<template>
  <div class="app-container">
    <el-card class="box-card">
      <h3>商品（Spu）介绍</h3>
      <el-form ref="dataForm" :rules="rules" :model="goods" label-width="150px">
        <el-form-item label="商品名称" prop="title">
          <el-input v-model="goods.title" />
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
        <el-form-item label="VIP价格" prop="vipPriceRaw">
          <el-input v-model="goods.vipPriceRaw" placeholder="0.00">
            <template slot="append">元</template>
          </el-input>
        </el-form-item>

        <el-form-item label="剩余库存" prop="stock">
          <el-input v-model="goods.stock" :disabled="true" placeholder="0"/>
        </el-form-item>

        <el-form-item label="运费模板" prop="freightTemplate">
          <el-select v-model="goods.freightTemplateId" placeholder="选择商品运费模板">
            <el-option v-for="(item, index) in freightList" :key="index" :label="item.freightTemplateDO.templateName" :value="item.freightTemplateDO.id"/>
          </el-select>
        </el-form-item>

        <el-form-item label="是否在售" prop="status">
          <el-radio-group v-model="goods.status">
            <el-radio :label="1">在售</el-radio>
            <el-radio :label="0">下架</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="商品图片">
          <el-upload
            :action="uploadPath"
            :headers="headers"
            :limit="5"
            :file-list="imgsFileList"
            :on-exceed="uploadOverrun"
            :on-success="handleimgsUrl"
            :on-remove="handleRemove"
            multiple
            accept=".jpg, .jpeg, .png, .gif"
            list-type="picture-card"
          >
            <i class="el-icon-plus" />
          </el-upload>
        </el-form-item>

        <el-form-item label="商品单位" prop="unit">
          <el-input v-model="goods.unit" placeholder="件 / 个 / 盒" />
        </el-form-item>

        <el-form-item label="所属类目" prop="category">
          <el-cascader
            :options="categoryList"
            v-model="categoryIds"
            expand-trigger="hover"
            @change="handleCategoryChange"
          />
        </el-form-item>

        <el-form-item label="商品简介" prop="description">
          <el-input v-model="goods.description" />
        </el-form-item>

        <el-form-item label="商品详细介绍" prop="detail">
          <editor :init="editorInit" v-model="goods.detail" />
        </el-form-item>
      </el-form>
    </el-card>

    <el-card class="box-card">
      <h3>商品类型(sku)</h3>
      <el-button :plain="true" type="primary" @click="createSkuDialogShow">添加</el-button>
      <el-table :data="skuList">
        <el-table-column property="id" label="SkuId"/>
        <el-table-column property="barCode" label="Sku条形码" />
        <el-table-column property="title" label="类型名" />
        <el-table-column property="originalPriceRaw" label="原价" />
        <el-table-column property="priceRaw" label="现价" />
        <el-table-column property="vipPriceRaw" label="VIP价" />
        <el-table-column property="stock" label="库存" />
        <el-table-column property="freezeStock" label="冻结库存" />
        <el-table-column
          align="center"
          label="操作"
          width="180"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="updateSkuDialogShow(scope.row)">修改</el-button>
            <el-button type="danger" size="mini" @click="handleSkuDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 添加SKU的Dialog -->
      <el-dialog :visible.sync="skuVisiable" :title="skuStatusDialogMap[skuStatus]">
        <el-form
          ref="skuForm"
          :model="skuForm"
          :rules="skuRules"
          status-icon
          label-position="left"
          label-width="100px"
          style="width: 400px; margin-left:50px;"
        >
          <el-input v-model="skuForm.id" type="hidden" />
          <el-form-item label="类型条码" prop="barCode">
            <el-input v-model="skuForm.barCode" />
          </el-form-item>
          <el-form-item label="类型名称" prop="title">
            <el-input v-model="skuForm.title" />
          </el-form-item>
          <el-form-item label="图片(可空)">
            <el-upload
              :action="uploadPath"
              :show-file-list="false"
              :on-success="handleSkuImgSuccess"
              class="avatar-uploader">
              <img v-if="skuForm.img" :src="skuForm.img" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"/>
            </el-upload>
          </el-form-item>
          <el-form-item label="原始价格(元)" prop="originalPriceRaw">
            <el-input-number v-model="skuForm.originalPriceRaw" placeholder="0.00"/>
          </el-form-item>
          <el-form-item label="当前价格(元)" prop="priceRaw">
            <el-input-number v-model="skuForm.priceRaw" placeholder="0.00"/>
          </el-form-item>
          <el-form-item label="VIP价格(元)" prop="vipPriceRaw">
            <el-input-number v-model="skuForm.vipPriceRaw" placeholder="0.00"/>
          </el-form-item>
          <el-form-item label="库存" prop="stock">
            <el-input v-model="skuForm.stock" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="skuVisiable = false">取消</el-button>
          <el-button type="primary" @click="handleSkuAdd">确定</el-button>
        </div>
      </el-dialog>
    </el-card>

    <el-card class="box-card">
      <h3>商品参数</h3>
      <el-button :plain="true" type="primary" @click="handleAttributeShow">添加</el-button>
      <el-table :data="attributes">
        <el-table-column property="attribute" label="商品参数名称" />
        <el-table-column property="value" label="商品参数值" />
        <el-table-column
          align="center"
          label="操作"
          width="100"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
            <el-button type="danger" size="mini" @click="handleAttributeDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-dialog :visible.sync="attributeVisiable" title="设置商品参数">
        <el-form
          ref="attributeForm"
          :model="attributeForm"
          :rules="attrRules"
          :data = "attributes"
          status-icon
          label-position="left"
          label-width="100px"
          style="width: 400px; margin-left:50px;"
        >
          <el-form-item label="商品参数名称" prop="attribute">
            <el-input v-model="attributeForm.attribute" />
          </el-form-item>
          <el-form-item label="商品参数值" prop="value">
            <el-input v-model="attributeForm.value" />
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
      <el-button v-if="!goods.id" type="primary" @click="handleCreate">保存商品</el-button>
      <el-button v-if="goods.id" type="primary" @click="handleEdit">更新商品</el-button>
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
import { detailGoods, editGoods, createGoods } from '@/api/goods'
import { categoryTree } from '@/api/category'
import { listFreight } from '@/api/freight'
import { uploadPath, createStorage } from '@/api/storage'
import Editor from '@tinymce/tinymce-vue'
import { MessageBox } from 'element-ui'
import { getToken } from '@/utils/auth'

export default {
  name: 'GoodsUpsert',
  components: { Editor },
  data() {
    return {
      uploadPath,
      freightList: [],
      imgsFileList: [],
      categoryList: [],
      categoryIds: [],
      goods: {
        imgList: [],
        priceRaw: 0
      },
      attributeVisiable: false,
      attributeForm: { attribute: '', value: '' },
      attributes: [],
      skuVisiable: false,
      skuForm: { img: undefined },
      skuList: [],
      skuStatusDialogMap: { 'create': '创建sku', 'update': '更新sku' },
      skuStatus: '',
      rules: {
        status: [
          { required: true, message: '请选择商品状态', trigger: 'blur' }
        ],
        title: [
          { required: true, message: '商品名称不能为空', trigger: 'blur' }
        ],
        // description: [
        //   { required: true, message: '商品描述不能为空', trigger: 'blur' }
        // ],
        priceRaw: [
          { required: true, message: '商品现价不能为空', trigger: 'blur' }
        ],
        originalPriceRaw: [
          { required: true, message: '商品原价不能为空', trigger: 'blur' }
        ],
        vipPriceRaw: [
          { required: true, message: '商品ViP价格不能为空', trigger: 'blur' }
        ],
        unit: [
          { required: true, message: '物件单位不能为空', trigger: 'blur' }
        ],
        detail: [{ required: true, message: '请填写商品详情', trigger: 'blur' }]
      },
      skuRules: {
        barCode: [
          { required: true, message: '类型条码不能为空', trigger: 'blur' }
        ],
        title: [
          { required: true, message: '类型名称不能为空', trigger: 'blur' }
        ],
        priceRaw: [
          { required: true, message: '类型现价不能为空', trigger: 'blur' }
        ],
        originalPriceRaw: [
          { required: true, message: '类型原价不能为空', trigger: 'blur' }
        ],
        vipPriceRaw: [
          { required: true, message: '类型VIP价格不能为空', trigger: 'blur' }
        ],
        stock: [
          { required: true, message: '类型库存不能为空', trigger: 'blur' }
        ]
      },
      attrRules: {
        attribute: [
          { required: true, message: '属性名称不能为空', trigger: 'blur' }
        ],
        value: [
          { required: true, message: '属性值不能为空', trigger: 'blur' }
        ]
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
        accessToken: getToken()
      }
    }
  },
  created() {
    this.init()
  },
  methods: {
    init: function() {
      const goodsId = this.$route.query.id
      if (goodsId) {
        detailGoods(goodsId).then(response => {
          response.data.data.priceRaw = response.data.data.price / 100
          response.data.data.originalPriceRaw = response.data.data.originalPrice / 100
          response.data.data.vipPriceRaw = response.data.data.vipPrice / 100
          this.goods = Object.assign({}, response.data.data)
          this.attributes = response.data.data.attributeList ? response.data.data.attributeList : []
          this.categoryIds = response.data.data.categoryIds.reverse()
          this.skuList = response.data.data.skuList
          this.imgsFileList = []
          for (var i = 0; i < this.goods.imgList.length; i++) {
            this.imgsFileList.push({
              url: this.goods.imgList[i]
            })
          }
          if (this.goods.skuList) {
            for (var j = 0; j < this.goods.skuList.length; j++) {
              this.skuList[j].priceRaw = this.skuList[j].price / 100
              this.skuList[j].originalPriceRaw = this.skuList[j].originalPrice / 100
              this.skuList[j].vipPriceRaw = this.skuList[j].vipPrice / 100
            }
          }
        })
      }

      categoryTree().then(response => {
        this.categoryList = response.data.data
      })

      listFreight().then(response => {
        this.freightList = response.data.data
      })
    },
    handleCategoryChange(value) {
      this.goods.categoryId = value[value.length - 1]
    },
    handleCancel: function() {
      this.$router.push({ path: '/goods/list' })
    },
    handleCreate: function() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          if (this.goods.freightTemplateId === undefined || this.goods.freightTemplateId === null) {
            this.$notify.error({
              title: '失败',
              message: '请添加运费模板'
            })
          } else if (this.skuList.length === 0) {
            this.$notify.error({
              title: '失败',
              message: '请添加类型（Sku）'
            })
          } else if (this.categoryIds.length !== 3) {
            this.$notify.error({
              title: '失败',
              message: '请选择类目'
            })
          } else if (this.goods.imgList.length === 0) {
            this.$notify.error({
              title: '失败',
              message: '请上传至少一张图片'
            })
          } else {
            this.goods.categoryId = this.categoryIds[2]
            this.goods.price = parseInt(this.goods.priceRaw * 100)
            this.goods.originalPrice = parseInt(this.goods.originalPriceRaw * 100)
            this.goods.vipPrice = parseInt(this.goods.vipPriceRaw * 100)
            const finalGoods = {
              ...this.goods,
              attributeList: this.attributes,
              skuList: this.skuList
            }
            createGoods(finalGoods)
              .then(response => {
                this.$notify.success({
                  title: '成功',
                  message: '添加成功'
                })
                this.$router.push({ path: '/goods/list' })
              })
              .catch(response => {
                MessageBox.alert('业务错误：' + response.data.errmsg, '警告', {
                  confirmButtonText: '确定',
                  type: 'error'
                })
              })
          }
        } else {
          this.$notify.error({
            title: '失败',
            message: '请完善以上表单'
          })
        }
      })
    },
    handleEdit: function() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          if (this.skuList.length === 0) {
            this.$notify.error({
              title: '失败',
              message: '请添加类型（Sku）'
            })
          } else if (this.categoryIds.length !== 3) {
            this.$notify.error({
              title: '失败',
              message: '请选择类目'
            })
          } else if (this.goods.imgList.length === 0) {
            this.$notify.error({
              title: '失败',
              message: '请上传至少一张图片'
            })
          } else {
            this.goods.categoryId = this.categoryIds[2]
            this.goods.price = parseInt(this.goods.priceRaw * 100)
            this.goods.originalPrice = parseInt(this.goods.originalPriceRaw * 100)
            this.goods.vipPrice = parseInt(this.goods.vipPriceRaw * 100)
            const finalGoods = {
              ...this.goods,
              attributeList: this.attributes,
              skuList: this.skuList
            }
            editGoods(finalGoods)
              .then(response => {
                this.$notify.success({
                  title: '成功',
                  message: '编辑成功'
                })
                this.$router.push({ path: '/goods/list' })
              })
              .catch(response => {
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
    uploadOverrun: function() {
      this.$message({
        type: 'error',
        message: '上传文件个数超出限制!最多上传5张图片!'
      })
    },
    handleimgsUrl(response, file, fileList) {
      if (response.errno === 200) {
        this.goods.imgList.push(response.url)
        this.goods.img = this.goods.imgList[0]
      }
    },
    handleRemove: function(file, fileList) {
      for (var i = 0; i < this.goods.imgList.length; i++) {
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

        if (this.goods.imgList[i] === url) {
          this.goods.imgList.splice(i, 1)
        }
      }
      if (this.goods.imgList.length > 0) {
        this.goods.img = this.goods.imgList[0]
      }
    },
    handleSkuImgSuccess(e, file) {
      this.skuForm.img = e.url
      this.skuVisiable = false
      this.skuVisiable = true
    },
    handleAttributeShow() {
      this.attributeForm = {}
      this.attributeVisiable = true
    },
    handleAttributeAdd() {
      this.$refs['attributeForm'].validate(valid => {
        if (valid) {
          this.attributes.unshift(this.attributeForm)
          this.attributeVisiable = false
        }
      })
    },
    handleAttributeDelete(row) {
      const index = this.attributes.indexOf(row)
      this.attributes.splice(index, 1)
    },
    createSkuDialogShow() {
      this.skuStatus = 'create'
      this.skuForm = {}
      this.skuVisiable = true
    },
    updateSkuDialogShow(row) {
      this.skuStatus = 'update'
      this.skuForm = Object.assign({}, row)
      this.skuVisiable = true
    },
    handleSkuAdd() {
      this.$refs['skuForm'].validate(valid => {
        if (valid) {
          if (this.skuForm.id) {
            let index = -1
            for (let i = 0; i < this.skuList.length; i++) {
              if (this.skuList[i].id === this.skuForm.id) {
                index = i
              }
            }
            if (index > -1) {
              this.skuForm.price = parseInt(this.skuForm.priceRaw * 100)
              this.skuForm.originalPrice = parseInt(this.skuForm.originalPriceRaw * 100)
              this.skuForm.vipPrice = parseInt(this.skuForm.vipPriceRaw * 100)
              var temp1 = Object.assign({}, this.skuForm)
              this.skuList.splice(index, 1, temp1)
              this.skuVisiable = false
            }
          } else {
            this.skuForm.price = parseInt(this.skuForm.priceRaw * 100)
            this.skuForm.originalPrice = parseInt(this.skuForm.originalPriceRaw * 100)
            this.skuForm.vipPrice = parseInt(this.skuForm.vipPriceRaw * 100)
            var temp = Object.assign({}, this.skuForm)
            this.skuList.unshift(temp)
            this.skuVisiable = false
          }
        }
      })
    },
    handleSkuDelete(row) {
      const index = this.skuList.indexOf(row)
      this.skuList.splice(index, 1)
    }

  }
}
</script>
