<template>
  <div class="app-container">
    <el-card class="box-card">
      <h3>商品（Spu）介绍</h3>
      <el-form ref="dataForm" :rules="rules" :model="product" label-width="150px">
        <el-form-item label="商品名称" prop="title">
          <el-input v-model="product.title" />
        </el-form-item>
        <el-form-item label="原始价格" prop="originalPriceRaw">
          <el-input v-model="product.originalPriceRaw" placeholder="0.00">
            <template slot="append">元</template>
          </el-input>
        </el-form-item>
        <el-form-item label="当前价格" prop="priceRaw">
          <el-input v-model="product.priceRaw" placeholder="0.00">
            <template slot="append">元</template>
          </el-input>
        </el-form-item>
        <el-form-item label="VIP价格" prop="vipPriceRaw">
          <el-input v-model="product.vipPriceRaw" placeholder="0.00">
            <template slot="append">元</template>
          </el-input>
        </el-form-item>

        <el-form-item label="剩余库存" prop="stock">
          <el-input v-model="product.stock" :disabled="true" placeholder="0"/>
        </el-form-item>

        <el-form-item label="运费模板" prop="freightTemplate">
          <el-select v-model="product.freightTemplateId" placeholder="选择商品运费模板">
            <el-option v-for="(item, index) in freightList" :key="index" :label="item.title" :value="item.id"/>
          </el-select>
        </el-form-item>

        <el-form-item label="是否在售" prop="status">
          <el-radio-group v-model="product.status">
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
          <el-input v-model="product.unit" placeholder="件 / 个 / 盒" />
        </el-form-item>

        <el-form-item label="所属类目" prop="category">
          <el-cascader
            :options="categoryList"
            :props="{label:'title', value:'id', children:'childrenList'}"
            v-model="categoryIds"
            expand-trigger="hover"
            @change="handleCategoryChange"
          />
        </el-form-item>

        <el-form-item label="商品简介" prop="description">
          <el-input v-model="product.description" />
        </el-form-item>

        <el-form-item label="商品详细介绍" prop="detail">
          <editor :init="editorInit" v-model="product.detail" />
        </el-form-item>

      </el-form>
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

    <el-card class="box-card">
      <h3>规格维度</h3>
      <el-button :plain="true" type="primary" @click="createSpecDialogShow">添加</el-button>
      <el-table :data="specList">
        <el-table-column property="id" label="维度Id"/>
        <el-table-column property="title" label="维度名称" />
        <el-table-column property="gmtCreate" label="创建时间" >
          <template slot-scope="scope">{{ scope.row.gmtCreate | formatTime }}</template>
        </el-table-column>
        <el-table-column
          align="center"
          label="操作"
          width="180"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="updateSpecDialogShow(scope.row)">修改</el-button>
            <el-button type="danger" size="mini" @click="handleSpecDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 添加规格的Dialog -->
      <el-dialog :visible.sync="specVisiable" :title="specStatusDialogMap[specStatus]">
        <el-form
          ref="specForm"
          :model="specForm"
          :rules="specRules"
          status-icon
          label-position="left"
          label-width="100px"
          style="width: 400px; margin-left:50px;"
        >
          <el-input v-model="specForm.id" type="hidden" />
          <el-form-item label="类型名称" prop="title">
            <el-input v-model="specForm.title" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="specVisiable = false">取消</el-button>
          <el-button type="primary" @click="handleSpecAdd">确定</el-button>
        </div>
      </el-dialog>
    </el-card>

    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <h4>商品(Sku)详情</h4>
        <el-button :plain="true" type="primary" @click="skuBatchPriceVisiable = true">批量定价</el-button>
      </div>
      <div v-for="(item, index) in specList" :key="index" class="tag-group">
        <el-tag
          size="medium"
          type="warning"
        >
          {{ item.title }}
        </el-tag>
        <el-tag
          v-for="tag in item.values"
          :key="tag"
          :disable-transitions="false"
          size="medium"
          closable
          @close="handleSkuTagClose(index, tag, item)">
          {{ tag }}
        </el-tag>
        <el-input
          v-if="item.inputSkuTagVisible"
          v-model="item.inputSkuTagValue"
          class="input-new-tag"
          size="small"
          maxlength="30"
          @keyup.enter.native="handleSkuTagInputConfirm(index, item)"
          @blur="handleSkuTagInputConfirm(index, item)"
        />
        <el-button v-else class="button-new-tag" size="small" @click="handleSkuTagCreate(item)">+ 添加</el-button>

      </div>
      <el-table
        :data="skuTableData"
        :span-method="skuTableSpanMethod"
        border
        style="width: 100%; margin-top: 20px">
        <el-table-column
          v-for="(item, index) in specList"
          :key="index"
          :prop="'id_' + item.title"
          :label="item.title"
          width="120"/>
        <el-table-column
          prop="id"
          label="SkuId"
          width="80"/>
        <el-table-column
          width="155"
          prop="price"
          label="￥价格(元)">
          <template slot-scope="scope">
            <el-input-number v-model="scope.row.priceRaw" :precision="2" :min="0.01" :max="2147483647" size="small" />
          </template>
        </el-table-column>
        <el-table-column
          prop="originalPrice"
          width="155"
          label="￥VIP价(元)">
          <template slot-scope="scope">
            <el-input-number v-model="scope.row.vipPriceRaw" :precision="2" :min="0.01" :max="2147483647" size="small" />
          </template>
        </el-table-column>
        <el-table-column
          prop="originalPrice"
          width="155"
          label="￥吊牌价(元)">
          <template slot-scope="scope">
            <el-input-number v-model="scope.row.originalPriceRaw" :precision="2" :min="0.01" :max="2147483647" size="small" />
          </template>
        </el-table-column>
        <el-table-column
          :label="'库存(' + (product.unit ? product.unit : '件') + ')'"
          width="155"
          prop="stock">
          <template slot-scope="scope">
            <el-input-number v-model="scope.row.stock" :precision="0" :max="2147483647" :min="0" size="small" />
          </template>
        </el-table-column>
        <el-table-column
          label="重量(克)"
          width="155"
          prop="stock">
          <template slot-scope="scope">
            <el-input-number v-model="scope.row.weight" :precision="0" :max="2147483647" :min="0" size="small" />
          </template>
        </el-table-column>
        <el-table-column
          prop="img"
          width="85"
          label="SKU图片">
          <template slot-scope="scope">
            <!-- on-success 接收的是一个变量，而非事件，也就是说是一个函数指针，所以无法将row给传过去 -->
            <el-upload
              :headers="headers"
              :show-file-list="false"
              :on-success="(e, file) => {
                handleSkuImgSuccess(e, file, scope)
              }"
              :before-upload="beforSkuImgUpload"
              :action="uploadPath"
              accept=".jpg, .jpeg, .png, .gif"
              class="sku-uploader">
              <img v-if="scope.row.img" :src="scope.row.img" class="sku">
              <i v-else class="el-icon-plus sku-uploader-icon"/>
            </el-upload>
          </template>
        </el-table-column>
        <el-table-column
          prop="barCode"
          width="210"
          label="条形码">
          <template slot-scope="scope">
            <el-input v-model="scope.row.barCode" maxlength="200">
              <el-button slot="append" @click="randonBarcode(scope.row)">随机</el-button>
            </el-input>
          </template>
        </el-table-column>
        <el-table-column
          prop=""
          label=""/>
      </el-table>

      <!-- 批量定价Dialog -->
      <el-dialog :visible.sync="skuBatchPriceVisiable" title="批量定价">
        <el-form
          ref="skuBatchPriceForm"
          :model="skuBatchPriceForm"
          status-icon
          label-position="left"
          label-width="100px"
          style="width: 400px; margin-left:50px;"
        >
          <el-form-item label="现价(￥)" prop="price">
            <el-input-number v-model="skuBatchPriceForm.price" :precision="2" :min="0.01" :max="2147483647" size="small" />
          </el-form-item>
          <el-form-item label="VIP价格(￥)" prop="vipPrice">
            <el-input-number v-model="skuBatchPriceForm.vipPrice" :precision="2" :min="0.01" :max="2147483647" size="small" />
          </el-form-item>
          <el-form-item label="吊牌价(￥)" prop="originalPrice">
            <el-input-number v-model="skuBatchPriceForm.originalPrice" :precision="2" :min="0.01" :max="2147483647" size="small" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="skuBatchPriceVisiable = false">取消</el-button>
          <el-button type="primary" @click="handleBatchPrice">确定</el-button>
        </div>
      </el-dialog>
    </el-card>

    <div class="op-container">
      <el-button @click="handleCancel">取消</el-button>
      <el-button v-if="!product.id" type="primary" @click="handleCreate">保存商品</el-button>
      <el-button v-if="product.id" type="primary" @click="handleEdit">更新商品</el-button>
    </div>
  </div>
</template>

<script>
import { detailProduct, editProduct, createProduct } from '@/api/product'
import { categoryTree } from '@/api/category'
import { listFreight } from '@/api/freight'
import { uploadPath, createStorage } from '@/api/storage'
import Editor from '@tinymce/tinymce-vue'
import { MessageBox } from 'element-ui'
import { getToken } from '@/utils/auth'

export default {
  name: 'ProductUpsert',
  components: { Editor },
  data() {
    return {
      uploadPath,
      freightList: [],
      imgsFileList: [],
      categoryList: [],
      categoryIds: [],
      skuTableData: [],
      product: {
        imgList: [],
        type: 0
      },
      attributeVisiable: false,
      attributeForm: { attribute: '', value: '' },
      attributes: [],
      skuBatchPriceVisiable: false,
      skuBatchPriceForm: {},
      specVisiable: false,
      specForm: { title: undefined },
      skuList: [],
      specList: [],
      skuStatusDialogMap: { 'create': '创建sku', 'update': '更新sku' },
      specStatusDialogMap: { 'create': '创建规格', 'update': '更新规格' },
      skuStatus: '',
      specStatus: '',
      rules: {
        status: [
          { required: true, message: '请选择商品状态', trigger: 'blur' }
        ],
        title: [
          { required: true, message: '商品名称不能为空', trigger: 'blur' }
        ],
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
      specRules: {
        title: [
          { required: true, message: '规格维度标题不能为空', trigger: 'blur' }
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
        ADMINTOKEN: getToken()
      }
    }
  },
  created() {
    this.init()
  },
  methods: {
    init: function() {
      const productId = this.$route.query.id
      if (productId) {
        detailProduct(productId).then(response => {
          // 将分转换为元
          response.data.data.priceRaw = response.data.data.price / 100
          response.data.data.originalPriceRaw = response.data.data.originalPrice / 100
          response.data.data.vipPriceRaw = response.data.data.vipPrice / 100
          // 深拷贝
          this.product = JSON.parse(JSON.stringify(response.data.data))
          this.attributes = response.data.data.attributeList ? response.data.data.attributeList : []
          this.categoryIds = response.data.data.categoryIds
          this.skuList = response.data.data.skuList
          for (let i = 0; i < response.data.data.specificationList.length; i++) {
            response.data.data.specificationList[i].values = []
            response.data.data.specificationList[i].inputSkuTagValue = false
            response.data.data.specificationList[i].inputSkuTagValue = ''
          }
          // spu规格列表
          this.specList = response.data.data.specificationList
          this.imgsFileList = []
          for (var i = 0; i < this.product.imgList.length; i++) {
            this.imgsFileList.push({
              url: this.product.imgList[i]
            })
          }
          let stock = 0
          if (this.product.skuList) {
            for (var j = 0; j < this.product.skuList.length; j++) {
              this.skuList[j].priceRaw = this.skuList[j].price / 100
              this.skuList[j].originalPriceRaw = this.skuList[j].originalPrice / 100
              this.skuList[j].vipPriceRaw = this.skuList[j].vipPrice / 100
              stock += this.skuList[j].stock
            }
          }
          this.product.stock = stock
          if (this.product.skuList) {
            this.product.skuList.forEach(item => {
              this.product.stock += item.stock
              item.priceRaw = item.price / 100
              item.originalPriceRaw = item.originalPrice / 100
              item.vipPriceRaw = item.vipPrice / 100
              const tempArray = item.specification.split(',')
              for (let i = 0; i < tempArray.length; i++) {
                const skuSpecTemp = tempArray[i]
                const singleArray = skuSpecTemp.split('_')
                item['id_' + singleArray[0]] = singleArray[1]
                for (let j = 0; j < this.specList.length; j++) {
                  if (this.specList[j].title === singleArray[0]) {
                    // 若是同一个Key。则将value追加到其values里面
                    if (this.specList[j].values) {
                      if (this.specList[j].values.indexOf(singleArray[1]) < 0) {
                        this.specList[j].values.push(singleArray[1])
                      }
                    } else {
                      this.specList[j].inputSkuTagVisible = false
                      this.specList[j].inputSkuTagValue = ''
                      this.specList[j].values = [singleArray[1]]
                    }
                  }
                }
              }
            })
          }
          this.skuTableData = JSON.parse(JSON.stringify(this.product.skuList))
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
      this.product.categoryId = value[value.length - 1]
    },
    handleCancel: function() {
      this.$router.push({ path: '/product/list' })
    },
    handleCreate: function() {
      this.handleUpsert('create')
    },
    handleEdit: function() {
      this.handleUpsert('edit')
    },
    /**
     * 执行更新添加操作，传入操作action字符串: 'create', 'edit'
     */
    handleUpsert(action) {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          if (this.product.freightTemplateId === undefined || this.product.freightTemplateId === null) {
            this.$notify.error({
              title: '失败',
              message: '请添加运费模板'
            })
          } else if (this.skuTableData.length === 0) {
            this.$notify.error({
              title: '失败',
              message: '请添加类型（Sku）'
            })
          } else if (this.categoryIds.length !== 3) {
            this.$notify.error({
              title: '失败',
              message: '请选择（第三级）类目'
            })
          } else if (this.product.imgList.length === 0) {
            this.$notify.error({
              title: '失败',
              message: '请上传至少一张图片'
            })
          } else {
            this.product.categoryId = this.categoryIds[2]
            this.product.price = parseInt(this.product.priceRaw * 100)
            this.product.originalPrice = parseInt(this.product.originalPriceRaw * 100)
            this.product.vipPrice = parseInt(this.product.vipPriceRaw * 100)
            this.product.specificationList = this.specList
            const skuList = []
            for (let i = 0; i < this.skuTableData.length; i++) {
              let title = ''
              let specification = ''
              for (const field in this.skuTableData[i]) {
                if (field.startsWith('id_')) {
                  title += (this.skuTableData[i][field] + ',')
                  specification += (field.substring(3) + '_' + this.skuTableData[i][field] + ',')
                }
              }
              title = title.substring(0, title.length - 1)
              specification = specification.substring(0, specification.length - 1)
              const sku = {
                originalPrice: parseInt(this.skuTableData[i].originalPriceRaw * 100),
                price: parseInt(this.skuTableData[i].priceRaw * 100),
                vipPrice: parseInt(this.skuTableData[i].vipPriceRaw * 100),
                stock: this.skuTableData[i].stock,
                barCode: this.skuTableData[i].barCode,
                weight: this.skuTableData[i].weight,
                img: this.skuTableData[i].img,
                title,
                specification
              }
              if (this.skuTableData[i].id) {
                // 编辑SKU情况下，加上SkuId
                sku.id = this.skuTableData[i].id
              }
              skuList.push(sku)
            }
            const finalProduct = {
              ...this.product,
              attributeList: this.attributes,
              skuList: skuList
            }
            let method = createProduct
            let successWords = '创建成功'
            if (action === 'edit') {
              method = editProduct
              successWords = '编辑成功'
            }
            method(finalProduct)
              .then(response => {
                this.$notify.success({
                  title: '成功',
                  message: successWords
                })
                this.$router.push({ path: '/product/list' })
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
    // SKU相关
    handleSkuTagClose(index, tag, item) {
      item.values.splice(item.values.indexOf(tag), 1)
      this.loadSkuTable()
    },
    skuTableSpanMethod({ row, column, rowIndex, columnIndex }) {
      if (columnIndex < this.specList.length) {
        // 若是前N列
        // 后面N - 1个
        let ji = 1
        for (let z = columnIndex + 1; z < this.specList.length; z++) {
          ji = ji * this.specList[z].values.length
        }
        if (rowIndex % ji === 0) {
          return {
            rowspan: ji, // 后面的乘积
            colspan: 1
          }
        } else {
          return {
            rowspan: 0,
            colspan: 0
          }
        }
      } else {
        return {
          rowspan: 1,
          colspan: 1
        }
      }
    },
    loadSkuTable() {
      // 从specList中获取数据
      const attrList = this.specList
      const cacheTable = this.skuTableData
      let newTable = []

      for (let i = 0; i < attrList.length; i++) {
        const attr = attrList[i]
        const tmpArray = []

        if (i === 0) {
          for (let j = 0; j < attr.values.length; j++) {
            const tempTemp = []
            tempTemp.push(attr.values[j])
            tmpArray.push(tempTemp)
          }
          newTable = tmpArray
        } else {
          for (let z = 0; z < newTable.length; z++) {
            for (let j = 0; j < attr.values.length; j++) {
              const tempTemp = Object.assign([], newTable[z])
              tempTemp.push(attr.values[j])
              tmpArray.push(tempTemp)
            }
          }
          newTable = tmpArray
        }
      }
      const finalNewTable = []
      for (let i = 0; i < newTable.length; i++) {
        const obj = {}
        for (let j = 0; j < newTable[i].length; j++) {
          obj['id_' + attrList[j].title] = newTable[i][j]
        }
        for (let j = 0; j < cacheTable.length; j++) {
          let equals = true
          for (let z = 0; z < this.specList.length; z++) {
            const title = this.specList[z].title
            if (cacheTable[j]['id_' + title] !== obj['id_' + title]) {
              equals = false
            }
          }
          if (equals) {
            obj.originalPrice = cacheTable[j].originalPrice
            obj.price = cacheTable[j].price
            obj.originalPriceRaw = cacheTable[j].originalPriceRaw
            obj.vipPrice = cacheTable[j].vipPrice
            obj.vipPriceRaw = cacheTable[j].vipPriceRaw
            obj.priceRaw = cacheTable[j].priceRaw
            obj.stock = cacheTable[j].stock
            obj.barCode = cacheTable[j].barCode
            obj.weight = cacheTable[j].weight
            obj.id = cacheTable[j].id
          }
        }
        // 从缓存表中加上默认的值
        finalNewTable.push(obj)
      }
      // 变成对象
      this.skuTableData = finalNewTable
    },
    handleSkuTagInputConfirm(index, item) {
      const inputSkuTagValue = item.inputSkuTagValue
      if (item.values.indexOf(inputSkuTagValue) > -1) {
        this.$notify.warning({
          title: '失败',
          message: '请不要添加重复属性'
        })
      } else {
        if (inputSkuTagValue) {
          item.values.push(inputSkuTagValue)
        }
      }
      item.inputSkuTagVisible = false
      item.inputSkuTagValue = ''
      this.loadSkuTable()
    },
    handleSkuTagCreate(item) {
      item.inputSkuTagVisible = true
      this.$forceUpdate()
    },
    // 规格相关代码START
    createSpecDialogShow() {
      this.specStatus = 'create'
      this.specForm = {}
      this.specVisiable = true
    },
    updateSpecDialogShow(row) {
      this.specStatus = 'update'
      this.specForm = Object.assign({}, row)
      this.specVisiable = true
    },
    handleSpecAdd() {
      this.$refs['specForm'].validate(valid => {
        if (valid) {
          const title = this.specForm.title
          // 重复校验
          if (this.specList.indexOf(title) >= 0) {
            this.$notify.error({
              title: '失败',
              message: '规格维度不能重复'
            })
            return
          }
          const obj = Object.assign({}, this.specForm)
          obj.values = []
          obj.inputSkuTagVisible = false
          obj.gmtCreate = new Date().getTime()
          this.specList.push(obj)
          this.specVisiable = false
        }
      })
    },
    handleSpecDelete(row) {
      const index = this.specList.indexOf(row)
      this.specList.splice(index, 1)
    },
    randonBarcode(row) {
      row.barCode = new Date().getTime() + ''
      this.$forceUpdate()
    },
    handleBatchPrice() {
      for (let i = 0; i < this.skuTableData.length; i++) {
        if (this.skuBatchPriceForm.price) {
          this.skuTableData[i].priceRaw = this.skuBatchPriceForm.price
        }
        if (this.skuBatchPriceForm.vipPrice) {
          this.skuTableData[i].vipPriceRaw = this.skuBatchPriceForm.vipPrice
        }
        if (this.skuBatchPriceForm.originalPrice) {
          this.skuTableData[i].originalPriceRaw = this.skuBatchPriceForm.originalPrice
        }
      }
      this.skuBatchPriceVisiable = false
    },
    /** ******************* 图片上传相关 *********************/
    uploadOverrun: function() {
      this.$message({
        type: 'error',
        message: '上传文件个数超出限制!最多上传5张图片!'
      })
    },
    handleimgsUrl(response, file, fileList) {
      if (response.errno === 200) {
        this.product.imgList.push(response.url)
        this.product.img = this.product.imgList[0]
      }
    },
    handleRemove: function(file, fileList) {
      for (var i = 0; i < this.product.imgList.length; i++) {
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

        if (this.product.imgList[i] === url) {
          this.product.imgList.splice(i, 1)
        }
      }
      if (this.product.imgList.length > 0) {
        this.product.img = this.product.imgList[0]
      }
    },
    beforSkuImgUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 1
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 1MB!')
      }
      return isLt2M
    },
    handleSkuImgSuccess(e, file, scope) {
      this.$set(this.skuTableData[scope.$index], 'img', e.url)
    }

  }
}
</script>

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

.sku-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.sku-uploader .el-upload:hover {
  border-color: #20a0ff;
}
.sku-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 60px;
  height: 60px;
  line-height: 60px;
  text-align: center;
}

.sku {
  width: 60px;
  height: 60px;
  display: block;
}

.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}
</style>
