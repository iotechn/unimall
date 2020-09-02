<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-input v-model="listQuery.title" clearable class="filter-item" style="width: 200px;" placeholder="请输入优惠券标题" />
      <el-select v-model="listQuery.type" clearable style="width: 200px" class="filter-item" placeholder="请选择优惠券类型" >
        <el-option v-for="(item,index) in couponTypeMap" :key="index" :label="item.name" :value="item.value" />
      </el-select>
      <el-select v-model="listQuery.status" clearable style="width: 200px" class="filter-item" placeholder="请选择优惠券状态" >
        <el-option v-for="(item,index) in couponStatusMap" :key="index" :label="item.name" :value="item.value" />
      </el-select>
      <el-button
        v-permission="['promote:coupon:query']"
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="handleFilter"
      >查找</el-button>
      <el-button
        v-permission="['promote:coupon:create']"
        class="filter-item"
        type="primary"
        icon="el-icon-edit"
        @click="handleCreate"
      >添加</el-button>
      <!-- <el-button
        :loading="downloadLoading"
        class="filter-item"
        type="primary"
        icon="el-icon-download"
        @click="handleDownload"
      >当前页导出</el-button> -->
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
      style="white-space: pre-line"
    >
      <el-table-column align="center" label="优惠券ID" prop="id" sortable />

      <el-table-column align="center" label="优惠券名称" prop="title" />

      <el-table-column align="center" label="优惠券类型" prop="type">
        <template slot-scope="scope">{{ couponTypeMap[scope.row.type-1]?couponTypeMap[scope.row.type-1].name:'错误类型' }}</template>
      </el-table-column>

      <el-table-column align="center" label="介绍" prop="description" />

      <el-table-column align="center" label="优惠券数量" prop="total">
        <template slot-scope="scope">{{ scope.row.total >= 0 ? scope.row.total : "不限" }}</template>
      </el-table-column>

      <el-table-column align="center" label="剩余数量" prop="surplus">
        <template slot-scope="scope">{{ scope.row.surplus >= 0 ? scope.row.surplus : "不限" }}</template>
      </el-table-column>

      <el-table-column align="center" label="每人限领" prop="limit">
        <template slot-scope="scope">{{ scope.row.limit >= 0 ? scope.row.limit : "不限" }}</template>
      </el-table-column>

      <el-table-column align="center" label="满减金额" prop="discount">
        <template slot-scope="scope">减免{{ scope.row.discount }}元</template>
      </el-table-column>

      <el-table-column align="center" label="最低消费" prop="min">
        <template slot-scope="scope">满{{ scope.row.min }}元可用</template>
      </el-table-column>

      <el-table-column align="center" label="状态" prop="status">
        <template slot-scope="scope">
          <el-tag> {{ scope.row.status | formatStatus }} </el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="使用类目名称" prop="categoryTitle" width="100">
        <template slot-scope="scope">
          <el-tag> {{ scope.row.categoryTitle != null?scope.row.categoryTitle:(scope.row.categoryId ? '类目已删除' : "全部类目") }} </el-tag>
        </template>
      </el-table-column>

      <el-table-column v-show="false" align="center" label="使用类目ID" prop="categoryId" width="100" />

      <el-table-column align="center" label="领券相对天数" prop="days">
        <template slot-scope="scope">{{ scope.row.days != null ? scope.row.days : "无" }}</template>
      </el-table-column>
      <el-table-column align="center" label="领券开始时间" prop="gmtStart">
        <template slot-scope="scope">{{ scope.row.gmtStart | formatGmt }}</template>
      </el-table-column>
      <el-table-column align="center" label="领券结束时间" prop="gmtEnd">
        <template slot-scope="scope">{{ scope.row.gmtEnd | formatGmt }}</template>
      </el-table-column>

      <el-table-column align="center" label="操作" width="300" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-permission="['promote:coupon:update']"
            type="primary"
            size="mini"
            @click="handleStatus(scope.row)"
          >{{ scope.row.status | formatStatusBtn }}</el-button>
          <el-button
            v-permission="['promote:coupon:update']"
            type="info"
            size="mini"
            @click="handleRead(scope.row)"
          >查看</el-button>
          <el-button
            v-permission="['promote:coupon:delete']"
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
        <el-form-item label="优惠券名称" prop="title">
          <el-input v-model="dataForm.title" :disabled="dialogStatus === 'update'"/>
        </el-form-item>
        <el-form-item label="优惠卷类型" prop="type">
          <el-select v-model="dataForm.type" :disabled="dialogStatus === 'update'">
            <el-option v-for="(item,index) in couponTypeOptions" :key="index" :label="item.name" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="介绍" prop="description" >
          <el-input v-model="dataForm.description" :disabled="dialogStatus === 'update'"/>
        </el-form-item>
        <el-form-item label="优惠券数量" prop="total">
          <el-input v-model="dataForm.total" :disabled="dialogStatus === 'update'">
            <template slot="append">张</template>
          </el-input>
        </el-form-item>
        <el-form-item label="每人限领" prop="limit">
          <el-input v-model="dataForm.limit" :disabled="dialogStatus === 'update'">
            <template slot="append">张</template>
          </el-input>
        </el-form-item>
        <el-form-item label="优惠金额" prop="discount">
          <el-input v-model="dataForm.discount" :disabled="dialogStatus === 'update'">
            <template slot="append">元</template>
          </el-input>
        </el-form-item>
        <el-form-item label="最低消费" prop="min">
          <el-input v-model="dataForm.min" :disabled="dialogStatus === 'update'">
            <template slot="append">元</template>
          </el-input>
        </el-form-item>
        <el-form-item label="优惠卷状态" prop="status">
          <el-select v-model="dataForm.status" :disabled="dialogStatus === 'update'">
            <el-option v-for="(item,index) in couponStatusOptions" :key="index" :label="item.name" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="有效期">
          <el-radio-group v-model="dataForm.timeType" :disabled="dialogStatus === 'update'">
            <el-radio-button :label="0">领券相对天数</el-radio-button>
            <el-radio-button :label="1">指定绝对时间</el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item v-show="dataForm.timeType === 0" prop="days">
          <el-input v-model="dataForm.days" :disabled="dialogStatus === 'update'">
            <template slot="append">天</template>
          </el-input>
        </el-form-item>
        <el-form-item v-show="dataForm.timeType === 1" prop="time">
          <el-col :span="11" >
            <el-date-picker
              :disabled="dialogStatus === 'update'"
              v-model="dataForm.gmtStart"
              type="datetime"
              placeholder="选择日期"
              style="width: 100%;"
            />
          </el-col>
          <el-col :span="2" class="line">至</el-col>
          <el-col :span="11">
            <el-date-picker
              :disabled="dialogStatus === 'update'"
              v-model="dataForm.gmtEnd"
              type="datetime"
              placeholder="选择日期"
              style="width: 100%;"
            />
          </el-col>
        </el-form-item>
        <el-form-item label="商品限制范围">
          <el-radio-group v-model="dataForm.goodsType" :disabled="dialogStatus === 'update'">
            <el-radio-button :label="0">全场通用</el-radio-button>
            <el-radio-button :label="1">指定分类</el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item v-if="dialogStatus === 'update'" v-show="dataForm.goodsType === 1" label="优惠类目">
          <el-input v-model="dataForm.categoryTitle" :disabled="dialogStatus === 'update'"/>
        </el-form-item>
        <el-form-item v-if="dialogStatus === 'create'" v-show="dataForm.goodsType === 1" label="优惠类目">
          <el-cascader
            v-model="dataForm.categoryTitle"
            :options="options"
            :props="{label:'title', value:'id', children:'childrenList', checkStrictly: true}"
            placeholder="优惠类目"
            filterable
            @change="handleLink"
          />

        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button v-if="dialogStatus=='create'" type="primary" @click="createData">确定</el-button>
        <!-- <el-button v-else type="primary" @click="updateData">确定</el-button> -->
      </div>
    </el-dialog>
  </div>
</template>

<style>
.el-table .cell {
  white-space: pre-line;
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
  width: 120px;
  height: 120px;
  display: block;
}
</style>

<script>
import { listCoupon, createCoupon, deleteCoupon, activeCoupon } from '@/api/coupon'
import { categoryTree } from '@/api/category'
import { formatDateAndTime } from '@/filters'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination

export default {
  name: 'Coupon',
  components: { Pagination },
  filters: {
    formatGmt(time) {
      if (time == null || time === '') {
        return '无'
      }
      return formatDateAndTime(time)
    },
    formatStatus(status) {
      if (status === 0) {
        return '下架'
      } else if (status === 1) {
        return '正常'
      } else if (status < 0) {
        return '已过期'
      } else {
        return '错误状态'
      }
    },
    formatStatusBtn(status) {
      if (status === 1) {
        return '冻结'
      } else if (status === 0) {
        return '激活'
      } else if (status < 0) {
        return '已过期'
      } else {
        return '错误状态'
      }
    }
  },
  data() {
    return {
      couponTypeMap: [{ value: 1, name: '满减卷' }, { value: '', name: '全部' }],
      couponStatusMap: [{ value: 0, name: '下架' }, { value: 1, name: '正常' }, { value: -1, name: '已过期' }, { value: '', name: '全部' }],
      couponStatusOptions: [{ value: 1, name: '正常' }, { value: 0, name: '下架' }],
      couponTypeOptions: [{ value: 1, name: '满减卷' }],
      list: undefined,
      total: 0,
      listLoading: true,
      listQuery: {
        pageNo: 1,
        limit: 20,
        type: undefined,
        status: undefined,
        title: undefined
      },
      options: [],
      dataForm: {
        id: undefined,
        title: undefined,
        type: 1,
        description: undefined,
        total: 0,
        discount: 0,
        limit: 0,
        min: 0,
        status: 1,
        tag: undefined,
        categoryId: undefined,
        categoryTitle: undefined,
        goodsType: 0,
        goodsValue: undefined,
        timeType: 1,
        days: undefined,
        gmtStart: null,
        gmtEnd: null
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '创建'
      },
      rules: {
        title: [{ required: true, message: '优惠券标题不能为空', trigger: 'blur' }],
        type: [{ required: true, message: '优惠券类型不能为空', trigger: 'blur' }],
        total: [{ required: true, message: '优惠券总量不能为空', trigger: 'blur' }, { pattern: /^[0-9]*$/, message: '请输入整数' }, { min: 1, max: 9, message: '大于1,小于1亿' }],
        limit: [{ required: true, message: '优惠券限领不能为空', trigger: 'blur' }, { pattern: /^[0-9]*$/, message: '请输入整数' }, { min: 1, max: 9, message: '大于1,小于1亿' }],
        discount: [{ required: true, message: '优惠券折扣金额不能为空', trigger: 'blur' }, { pattern: /^[0-9]*$/, message: '请输入整数' }, { min: 1, max: 9, message: '大于1,小于1亿' }],
        min: [{ required: true, message: '优惠券使用门栏不能为空', trigger: 'blur' }, { pattern: /^[0-9]*$/, message: '请输入整数' }, { min: 1, max: 9, message: '大于1,小于1亿' }],
        status: [{ required: true, message: '优惠券状态不能为空', trigger: 'blur' }]
      },
      downloadLoading: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      listCoupon(this.listQuery)
        .then(response => {
          // 为过期优惠卷赋负值
          response.data.data.items.forEach(item => {
            var now = new Date()
            if (item.gmtEnd < now) {
              item.status = -1
            }
            item.discount = item.discount / 100
            item.min = item.min / 100
          })
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
      this.listQuery.pageNo = 1
      this.getList()
    },
    resetForm() {
      this.dataForm = {
        id: undefined,
        title: undefined,
        desc: undefined,
        tag: undefined,
        total: 0,
        discount: 0,
        min: 0,
        limit: 0,
        type: 1,
        status: 1,
        goodsType: 0,
        goodsValue: undefined,
        timeType: 0,
        days: undefined,
        categoryId: undefined,
        categoryTitle: undefined,
        gmtStart: null,
        gmtEnd: null
      }
    },
    refreshOptions() {
      if (this.options.length === 0) {
        categoryTree().then(response => {
          this.options = response.data.data
        })
      }
    },
    handleLink(e) {
      if (e !== undefined) {
        const tag = e[e.length - 1]
        this.dataForm.categoryId = tag // 回调指定分类
      }
    },
    handleCreate() {
      this.resetForm()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.refreshOptions()
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      // 时间
      if (this.dataForm.days == null && (this.dataForm.gmtStart == null || this.dataForm.gmtEnd == null)) {
        this.$notify.error({
          title: '失败',
          message: '请填写优惠卷使用区间'
        })
        return false
      }
      // 使用时间类型，不能为空，值要互斥
      if (this.dataForm.timeType === 1) {
        this.dataForm.days = undefined
        if (this.dataForm.gmtEnd < this.dataForm.gmtStart || this.dataForm.gmtStart == null || this.dataForm.gmtEnd == null) {
          this.$notify.error({
            title: '失败',
            message: '请正确选择日期'
          })
          return false
        }
        var start = new Date(this.dataForm.gmtStart)
        var end = new Date(this.dataForm.gmtEnd)
        this.dataForm.gmtStart = start.getTime()
        this.dataForm.gmtEnd = end.getTime()
      } else {
        if (this.dataForm.days == null || this.dataForm.days === '0') {
          this.$notify.error({
            title: '失败',
            message: '请正确填写时长,不能为空或为0'
          })
          return false
        }
        this.dataForm.gmtStart = undefined
        this.dataForm.gmtEnd = undefined
      }
      // 判定全场通用，类型置为空
      if (parseInt(this.dataForm.goodsType) === 0) {
        this.dataForm.categoryId = undefined
      }

      // 判定满减门栏与折扣金额，如果折扣金额高于满减门栏，返回错误
      if (parseInt(this.dataForm.discount) > parseInt(this.dataForm.min)) {
        this.$notify.error({
          title: '失败',
          message: '满减门栏不能低于折扣金额，但是可以等于'
        })
        return false
      }
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          this.dataForm.discount = this.dataForm.discount * 100
          this.dataForm.min = this.dataForm.min * 100
          createCoupon(this.dataForm)
            .then(response => {
              this.getList()
              this.dialogFormVisible = false
              this.$notify.success({
                title: '成功',
                message: '创建优惠券成功'
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
    handleRead(row) {
      this.dataForm = Object.assign({}, row)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.refreshOptions()
      if (this.dataForm.days === 0 || this.dataForm.days == null) {
        this.dataForm.timeType = 1
      } else {
        this.dataForm.timeType = 0
      }
      if (this.dataForm.categoryId === '' || this.dataForm.categoryId == null) {
        this.dataForm.goodsType = 0
      } else {
        this.dataForm.goodsType = 1
      }
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    // 本来用来修改的，但是技术有限，暂且放置
    // updateData() {
    //   this.$refs['dataForm'].validate(valid => {
    //     if (valid) {
    //       updateCoupon(this.dataForm)
    //         .then(() => {
    //           for (const v of this.list) {
    //             if (v.id === this.dataForm.id) {
    //               const index = this.list.indexOf(v)
    //               this.list.splice(index, 1, this.dataForm)
    //               break
    //             }
    //           }
    //           this.dialogFormVisible = false
    //           this.$notify.success({
    //             title: '成功',
    //             message: '更新优惠券成功'
    //           })
    //         })
    //         .catch(response => {
    //           this.$notify.error({
    //             title: '失败',
    //             message: response.data.errmsg
    //           })
    //         })
    //     }
    //   })
    // },
    // 删除优惠卷
    handleDelete(row) {
      this.$confirm('此操作将永久删除该优惠卷' + row.title + ', 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteCoupon(row)
          .then(response => {
            this.$notify.success({
              title: '成功',
              message: '删除优惠券成功'
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
    // 改变优惠卷状态
    handleStatus(row) {
      // 如果优惠卷已经过期
      if (row.status < 0) {
        this.$notify.error({
          title: '失败',
          message: '过期优惠卷，建议删除'
        })
        return false
      }
      this.dataForm = Object.assign({}, row)
      this.dataForm.status = this.dataForm.status === 1 ? 0 : 1
      activeCoupon(this.dataForm)
        .then(response => {
          this.$notify.success({
            title: '成功',
            message: '修改优惠券成功'
          })
          // this.getList()
          row.status = row.status === 1 ? 0 : 1
        })
        .catch(response => {
          this.$notify.error({
            title: '失败',
            message: response.data.errmsg
          })
        })
    },
    // 到处excl表
    handleDownload() {
      var temp = new Date()
      var date = temp.getFullYear() + '-' + temp.getMonth() + '-' + temp.getDate()
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = [
          '优惠券ID',
          '名称',
          '介绍',
          '类型',
          '最低消费',
          '减免金额',
          '每人限领',
          '优惠券数量',
          '剩余数量',
          '状态',
          '使用类目'
        ]
        const filterVal = [
          'id',
          'title',
          'description',
          'type',
          'min',
          'discount',
          'limit',
          'total',
          'surplus',
          'status',
          'cateotry'
        ]
        excel.export_json_to_excel2(
          tHeader,
          this.list,
          filterVal,
          date + ' 优惠券信息'
        )
        this.downloadLoading = false
      })
    }
  }
}
</script>
