<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-select
        v-model="listQuery.status"
        clearable
        style="width: 200px"
        class="filter-item"
        placeholder="请选择广告状态"
      >
        <el-option v-for="(key,index) in adStatusMap" :key="index" :label="key.name" :value="key.value" />
      </el-select>
      <el-select
        v-model="listQuery.type"
        clearable
        style="width: 200px"
        class="filter-item"
        placeholder="请选择广告类型"
      >
        <el-option v-for="(key,index) in adTypeMap" :key="index" :label="key.name" :value="key.value" />
      </el-select>
      <el-button
        v-permission="['promote:advert:query']"
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="handleFilter"
      >查找</el-button>

      <el-button
        v-permission="['promote:advert:create']"
        class="filter-item"
        type="primary"
        icon="el-icon-edit"
        @click="handleCreate"
      >添加</el-button>
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
      <el-table-column align="center" width="80" label="广告ID" prop="id" />

      <el-table-column align="center" label="广告标题" prop="title" />

      <el-table-column align="center" label="广告图片" width="120" prop="imgUrl">
        <template slot-scope="scope">
          <el-popover v-if="scope.row.imgUrl !== USELESS_PIC" placement="right" trigger="hover">
            <!--trigger属性值：hover、click、focus 和 manual-->
            <img :src="scope.row.imgUrl" height="230" >
            <img slot="reference" :src="scope.row.imgUrl" height="23" >
          </el-popover>
          <el-tag v-else type="info">类型无需图片</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="广告类型" prop="type">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.type | adTypeFilter }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="广告状态" width="100" prop="status">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status === 1 ? 'success' : 'info'">{{ scope.row.status | adStatusFilter }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="关联类型" width="120" prop="unionType">
        <template slot-scope="scope">
          <el-tag type="warning">{{ scope.row.unionType | adUnionTypeFilter }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="关联值[ID]" prop="unionValue" />

      <el-table-column align="center" width="160" label="创建时间" prop="gmtCreate">
        <template slot-scope="scope">{{ scope.row.gmtCreate | formatTime }}</template>
      </el-table-column>

      <el-table-column align="center" label="操作" width="200" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-permission="['promote:advert:update']"
            type="primary"
            size="mini"
            @click="handleUpdate(scope.row)"
          >编辑</el-button>
          <el-button
            v-permission="['promote:advert:delete']"
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
        v-loading="dialogLoading"
        ref="dataForm"
        :rules="rules"
        :model="dataForm"
        status-icon
        label-position="left"
        label-width="100px"
        style="width: 400px; margin-left:50px;"
      >
        <el-form-item label="id" prop="id" hidden>
          <el-input v-model="dataForm.id" />
        </el-form-item>
        <el-form-item label="广告标题" prop="title">
          <el-input v-model="dataForm.title" />
        </el-form-item>
        <el-form-item label="广告类型" prop="type">
          <el-select v-model="dataForm.type" placeholder="请选择" @change="adTypeChange">
            <el-option v-for="(key, index) in adTypeMap" :key="index" :label="key.name" :value="key.value" />
          </el-select>
        </el-form-item>
        <el-form-item :hidden="currentType && !currentType.picRequire" label="广告图片" prop="imgUrl">
          <el-upload
            :headers="headers"
            :action="uploadPath"
            :show-file-list="false"
            :on-success="uploadSuccessHandle"
            :before-upload="onBeforeUpload"
            class="avatar-uploader"
            accept=".jpg, .jpeg, .png, .gif"
          >
            <img v-if="dataForm.imgUrl" ref="adImg" :src="dataForm.imgUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon" />
          </el-upload>
        </el-form-item>
        <el-form-item label="广告状态" prop="status">
          <el-switch
            v-model="dataForm.status"
            :active-value="1"
            :inactive-value="0"
            active-color="#13ce66"/>
        </el-form-item>
        <el-form-item label="关联类型" prop="unionType">
          <!-- dataForm.type && (adTypeMap[dataForm.type - 1].unionTypes.indexOf(item.value) < 0) -->
          <el-select :disabled="!dataForm.type" v-model="dataForm.unionType" :placeholder="dataForm.type ? '点击时跳转目标.' : '请先选择广告类型'">
            <el-option
              v-for="item in adUnionTypeMap"
              :disabled="currentType && currentType.unionTypes.indexOf(item.value) < 0"
              :key="item.value"
              :label="item.name"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item v-if="dataForm.unionType === 1" label="商品" prop="unionValue">
          <el-cascader
            :options="spuTree"
            v-model="cascaderData"
            placeholder="关联至商品"
            @change="unionValueChange"
          />
        </el-form-item>
        <el-form-item v-if="dataForm.unionType === 2" label="类目" prop="unionValue">
          <el-cascader
            :options="categoryTree"
            v-model="cascaderData"
            placeholder="关联至类目"
            filterable
            @change="unionValueChange"
          />
        </el-form-item>
        <el-form-item v-if="dataForm.unionType === 3" label="关键字" prop="unionValue">
          <el-input v-model="dataForm.unionValue" placeholder="点击广告后，用户将得到“此关键字”的商品搜索结果" />
        </el-form-item>
        <el-form-item v-if="dataForm.unionType === 4" label="功能页面" prop="unionValue">
          <el-select v-model="dataForm.unionValue" placeholder="请选择一个功能页面">
            <el-option
              v-for="item in functionPages"
              :key="item.value"
              :label="item.name"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button v-if="dialogStatus=='create'" :loading="submiting" type="primary" @click="createData">确定</el-button>
        <el-button v-else :loading="submiting" type="primary" @click="updateData">确定</el-button>
      </div>
    </el-dialog>
    <!-- 用于计算颜色均值的画布 -->
    <canvas ref="canvas" hidden/>
  </div>
</template>

<script>
import { listAdvert, createAdvert, editAdvert, deleteAdvert, getImageColor } from '@/api/advert'
import { getSpuBigTree } from '@/api/product'
import { uploadPath } from '@/api/storage'
import { getToken } from '@/utils/auth'
import Pagination from '@/components/Pagination'
import ElOption from '../../../node_modules/element-ui/packages/select/src/option' // Secondary package based on el-pagination

const adTypeMap = [
  { value: 1, unionTypes: [1, 2, 3, 4], picRequire: true, name: '轮播' },
  { value: 2, unionTypes: [2], picRequire: true, name: '分类精选' },
  { value: 3, unionTypes: [1, 2, 3, 4], picRequire: true, name: '横幅' },
  { value: 4, unionTypes: [1, 2, 3, 4], picRequire: true, name: '首页轮播下5按钮' },
  { value: 6, unionTypes: [1, 2, 3, 4], picRequire: false, name: '公告' },
  { value: 9, unionTypes: [1], picRequire: false, name: '商品推荐' }]
const adUnionTypeMap = [
  { value: 1, name: '商品' },
  { value: 2, name: '类目' },
  { value: 3, name: '关键字' },
  { value: 4, name: '功能页面' }]
const adStatusMap = [{ value: 0, name: '冻结' }, { value: 1, name: '激活' }]
const functionPages = [{ value: '/pages/product/groupshop', name: '团购列表' }, { value: '/pages/coupon/list', name: '优惠券列表' }]

export default {
  name: 'Advert',
  components: {
    ElOption,
    Pagination
  },
  filters: {
    adTypeFilter(code) {
      for (let i = 0; i < adTypeMap.length; i++) {
        if (adTypeMap[i].value === code) {
          return adTypeMap[i].name
        }
      }
      return '无效'
    },
    adUnionTypeFilter(code) {
      for (let i = 0; i < adUnionTypeMap.length; i++) {
        if (adUnionTypeMap[i].value === code) {
          return adUnionTypeMap[i].name
        }
      }
      return '无效'
    },
    adStatusFilter(code) {
      return adStatusMap[code]['name']
    }
  },
  data() {
    return {
      // 常量 START
      uploadPath,
      functionPages,
      adUnionTypeMap,
      adTypeMap,
      adStatusMap,
      USELESS_PIC: 'https://www.baidu.com/img/flexible/logo/pc/result.png',
      // 常量 END
      dialogLoading: false,
      spuTree: [],
      categoryTree: [],
      cascaderData: [],
      list: [],
      total: 0,
      listLoading: true,
      listQuery: {
        pageNo: 1,
        limit: 20,
        status: undefined,
        type: undefined
      },
      dataForm: {
        id: undefined,
        type: undefined,
        title: undefined,
        unionType: undefined,
        unionValue: '',
        imgUrl: undefined,
        status: 1,
        color: undefined
      },
      oldPic: '',
      currentType: undefined,
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '创建'
      },
      rules: {
        title: [{ required: true, message: '广告标题不能为空', trigger: 'blur' }],
        imgUrl: [{ required: true, message: '广告图片不能为空', trigger: 'blur' }],
        type: [{ required: true, message: '请选择广告类型', trigger: 'blur' }],
        status: [{ required: true, message: '请确定广告状态', trigger: 'blur' }],
        unionType: [{ required: true, message: '请选择广告关联类型', trigger: 'blur' }],
        unionValue: [{ required: true, message: '请关联一个(商品、类目、页面等具体值)', trigger: 'blur' }]
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
    getList() {
      this.listLoading = true
      listAdvert(this.listQuery)
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
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    resetForm() {
      this.dataForm = {
        id: undefined,
        type: undefined,
        title: undefined,
        unionType: undefined,
        unionValue: '',
        imgUrl: undefined,
        status: 1,
        color: undefined
      }
    },
    adTypeChange(type) {
      this.dataForm.unionType = undefined
      this.dataForm.unionValue = ''
      this.cascaderData = []
      for (let i = 0; i < this.adTypeMap.length; i++) {
        if (this.adTypeMap[i].value === type) {
          this.currentType = adTypeMap[i]
          if (!this.adTypeMap[i].picRequire) {
            // 随便填一张图片
            const defalutPic = this.USELESS_PIC
            if (this.dataForm.imgUrl && this.dataForm.imgUrl !== defalutPic) {
              this.oldPic = this.dataForm.imgUrl
            }
            this.dataForm.imgUrl = defalutPic
          } else {
            // 若需要图片，将旧图片赋值给他
            if (this.oldPic) {
              this.dataForm.imgUrl = this.oldPic
            } else {
              this.dataForm.imgUrl = ''
            }
          }
        }
      }
    },
    unionValueChange(e) {
      this.dataForm.unionValue = e[e.length - 1].substring(2)
    },
    refreshOptions(row) {
      if (this.spuTree.length === 0) {
        this.dialogLoading = true
        getSpuBigTree().then(response => {
          this.dialogLoading = false
          this.categoryTree = this.clearSpu(JSON.parse(JSON.stringify(response.data.data)))
          this.spuTree = response.data.data
          if (row) {
            if (row.unionType === 1) {
              // 产品
              this.cascaderData = this.getCascaderData('G_' + row.unionValue)
            } else if (row.unionType === 2) {
              this.cascaderData = this.getCascaderData('C_' + row.unionValue)
            }
          }
        })
          .catch(failres => {
            this.dialogLoading = false
          })
      } else {
        if (row) {
          if (row.unionType === 1) {
            // 产品
            this.cascaderData = this.getCascaderData('G_' + row.unionValue)
          } else if (row.unionType === 2) {
            this.cascaderData = this.getCascaderData('C_' + row.unionValue)
          }
        }
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
          this.submiting = true
          createAdvert(this.dataForm)
            .then(response => {
              this.getList()
              this.dialogFormVisible = false
              this.$notify.success({
                title: '成功',
                message: '创建成功'
              })
              this.submiting = false
            })
            .catch(response => {
              this.$notify.error({
                title: '失败',
                message: response.data.errmsg
              })
              this.submiting = false
            })
        }
      })
    },
    // 点击编辑按钮时的处理
    handleUpdate(row) {
      this.dataForm = Object.assign({}, row)
      this.refreshOptions(row)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.oldPic = this.dataForm.imgUrl
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
      for (let i = 0; i < adTypeMap.length; i++) {
        if (adTypeMap[i].value === row.dataForm.type) {
          this.currentType = adTypeMap[i]
        }
      }
    },
    updateData() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          editAdvert(this.dataForm)
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
      this.$confirm('此操作将永久删除该广告--' + row.title + '--, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteAdvert(row.id, row.type)
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
    },
    // 上传图片了处理图片
    uploadSuccessHandle(e, file) {
      const that = this
      this.dataForm.imgUrl = e.url
      this.dialogFormVisible = false
      this.dialogFormVisible = true
      const img = new Image()
      // 加载完成执行
      img.src = e.url
      img.setAttribute('crossOrigin', 'anonymous')
      img.src = e.url + '?time=' + new Date().valueOf()
      img.onload = function(e) {
        var canvas = that.$refs.canvas
        that.dataForm.color = getImageColor(canvas, img)
      }
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
    },
    // ////// Function
    /**
     * 清理掉Spu 作为CategoryTree使用
     */
    clearSpu(data) {
      if (data === null || data === undefined) {
        return data
      }
      for (let i = 0; i < data.length; i++) {
        if (data[i].children.length === 0 || data[i].children[0].value.indexOf('G_') > -1) {
          data[i].children = undefined
        } else {
          this.clearSpu(data[i].children)
        }
      }
      return data
    },
    /**
     * 传入一个Id，从树中查找出选择路径
     */
    getCascaderData(id) {
      for (let i = 0; i < this.spuTree.length; i++) {
        if (this.spuTree[i].value === id) {
          return [id]
        }
        for (let j = 0; j < this.spuTree[i].children.length; j++) {
          if (this.spuTree[i].children[j].value === id) {
            return [this.spuTree[i].value, id]
          }
          for (let k = 0; k < this.spuTree[i].children[j].children.length; k++) {
            if (this.spuTree[i].children[j].children[k].value === id) {
              return [this.spuTree[i].value, this.spuTree[i].children[j].value, id]
            }
            for (let z = 0; z < this.spuTree[i].children[j].children[k].children.length; z++) {
              if (this.spuTree[i].children[j].children[k].children[z].value === id) {
                return [this.spuTree[i].value, this.spuTree[i].children[j].value, this.spuTree[i].children[j].children[k].value, id]
              }
            }
          }
        }
      }
      return []
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
</style>
