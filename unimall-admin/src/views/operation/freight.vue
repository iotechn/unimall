<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <!--<el-input v-model="listQuery.userId" clearable class="filter-item" style="width: 200px;" placeholder="请输入用户ID"/>-->
      <el-button
        v-permission="['operation:freight:create']"
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="createHandle"
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
      <el-table-column align="center" label="模板编号" prop="freightTemplateDO.id" />

      <el-table-column align="center" label="模板名称" width="300" prop="freightTemplateDO.templateName" />
      <el-table-column align="center" label="宝贝地址" prop="freightTemplate.DOspuLocation" />

      <el-table-column align="center" label="发货期限" prop="freightTemplateDO.deliveryDeadline">
        <template slot-scope="scope">{{ scope.row.freightTemplateDO.deliveryDeadline }}天</template>
      </el-table-column>
      <el-table-column align="center" label="默认包邮门栏" prop="freightTemplateDO.defaultFreePrice" >
        <template slot-scope="scope">{{ scope.row.freightTemplateDO.defaultFreePrice | defaultFreePriceFilter }}</template>
      </el-table-column>
      <el-table-column align="center" label="默认计费首次发货数量" prop="freightTemplateDO.defaultFirstNum" >
        <template slot-scope="scope">{{ scope.row.freightTemplateDO.defaultFirstNum }}件</template>
      </el-table-column>
      <el-table-column align="center" label="默认计费首次发货价格" prop="freightTemplateDO.defaultFirstMoney">
        <template slot-scope="scope">{{ scope.row.freightTemplateDO.defaultFirstMoney }}元</template>
      </el-table-column>
      <el-table-column align="center" label="默认计费续件数量" prop="freightTemplateDO.defaultContinueNum" >
        <template slot-scope="scope">{{ scope.row.freightTemplateDO.defaultContinueNum }}件</template>
      </el-table-column>
      <el-table-column align="center" label="默认计费续件价格" prop="freightTemplateDO.defaultContinueMoney">
        <template slot-scope="scope">{{ scope.row.freightTemplateDO.defaultContinueMoney }}元</template>
      </el-table-column>
      <!-- <el-table-column align="center" label="指定地区数量" prop="freightTemplateCarriageDOList.length"/> -->
      <el-table-column align="center" label="操作" width="300" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-permission="['operation:appraise:delete']"
            type="primary"
            size="mini"
            @click="(scope.row)"
          >阅读</el-button>
          <el-button
            v-permission="['operation:appraise:delete']"
            type="primary"
            size="mini"
            @click="(scope.row)"
          >修改</el-button>
          <el-button
            v-permission="['operation:appraise:delete']"
            type="danger"
            size="mini"
            @click="(scope.row)"
          >删除</el-button>
        </template>

      </el-table-column>
    </el-table>

    <!-- 添加或修改对话框 -->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" :fullscreen="true">
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="dataForm"
        status-icon
        label-position="left"
        label-width="100px"
        style="width: 700px; margin-left:50px;"
      >
        <el-form-item label="隐藏的用户id" prop="id" hidden>
          <el-input v-model="dataForm.id" clearable placeholder=""/>
        </el-form-item>
        <el-form-item label="模板名称" prop="templateName">
          <el-input v-model="dataForm.templateName" clearable placeholder=""/>
        </el-form-item>
        <el-form-item label="发货地址" prop="spuLocation">
          <el-input v-model="dataForm.spuLocation" clearable placeholder=""/>
        </el-form-item>
        <el-form-item label="发货期限" prop="deliveryDeadline">
          <el-input v-model="dataForm.deliveryDeadline" clearable placeholder="">
            <template slot="append">天</template>
          </el-input>
        </el-form-item>
        <el-form-item label="是否包邮" prop="isFree">
          <template>
            <el-radio-group v-model="dataForm.isFree">
              <el-radio :label="-1">坚决不包邮</el-radio>
              <el-radio :label="0">卖家承担包邮</el-radio>
              <el-radio :label="1">设定满额包邮</el-radio>
            </el-radio-group>
          </template>
        </el-form-item>
        <el-form-item v-if="dataForm.isFree === 1" label="默认包邮额度" prop="defaultFreePrice">
          <el-input v-model="dataForm.defaultFreePrice" placeholder="默认包邮额度">
            <template slot="append">元</template>
          </el-input>
        </el-form-item>
        <el-form-item label="计费首次数量" prop="defaultFirstNum">
          <el-input v-model="dataForm.defaultFirstNum" clearable placeholder="">
            <template slot="append">件</template>
          </el-input>
        </el-form-item>
        <el-form-item label="计费首次价格" prop="defaultFirstPrice">
          <el-input v-model="dataForm.defaultFirstPrice" clearable placeholder="">
            <template slot="append">元</template>
          </el-input>
        </el-form-item>
        <el-form-item label="计费续件数量" prop="defaultContinuePrice">
          <el-input v-model="dataForm.defaultContinuePrice" clearable placeholder="">
            <template slot="append">件</template>
          </el-input>
        </el-form-item>
        <el-form-item label="计费续件价格" prop="defaultContinueNum">
          <el-input v-model="dataForm.defaultContinuePrice" clearable placeholder="">
            <template slot="append">元</template>
          </el-input>
        </el-form-item>
        <el-form-item label="指定地区价格" prop="defaultContinueNum" style="width:150%">
          <el-button :plain="true" type="primary" @click="handleSpecified">添加</el-button>
          <el-table :data="dataForm.freightTemplateCarriageDOList">
            <el-table-column property="designatedArea" label="指定省份" />
            <el-table-column property="firstNum" label="首次数量" />
            <el-table-column property="firstMoney" label="首次价格" />
            <el-table-column property="continueNum" label="续件数量" />
            <el-table-column property="continueMoney" label="续件价格" />
            <el-table-column property="freePrice" label="满额包邮门栏" />
            <el-table-column
              align="center"
              label="操作"
              width="100"
              class-name="small-padding fixed-width"
            >
              <template slot-scope="scope">
                <el-button type="danger" size="mini" @click="handleSkuDelete(scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>

          <!-- 添加指定地区的Dialog -->
          <el-dialog :visible.sync="specVisiable" :modal="false" :append-to-body="true" title="添加指定地区">
            <el-form
              ref="specForm"
              :model="specForm"
              :rules="specRules"
              status-icon
              label-position="left"
              label-width="100px"
              style="width: 400px; margin-left:50px;"
            >

              <el-form-item label="包邮门栏" prop="freePrice">
                <el-input v-model="specForm.freePrice" clearable />
              </el-form-item>
              <el-form-item label="首次数量" prop="firstNum">
                <el-input v-model="specForm.firstNum" clearable >
                  <template slot="append">件</template>
                </el-input>
              </el-form-item>
              <el-form-item label="首次价格" prop="firstMoney">
                <el-input v-model="specForm.firstMoney" placeholder="" clearable>
                  <template slot="append">元</template>
                </el-input>
              </el-form-item>
              <el-form-item label="续件数量" prop="continueNum">
                <el-input v-model="specForm.continueNum" placeholder="" clearable>
                  <template slot="append">件</template>
                </el-input>
              </el-form-item>
              <el-form-item label="续件价格" prop="continueMoney">
                <el-input v-model="specForm.continueMoney" placeholder="" clearable>
                  <template slot="append">元</template>
                </el-input>
              </el-form-item>
              <el-form-item label="指定地区" prop="designatedAreaList" style="width:150%">
                <template>
                  <el-checkbox-group
                    v-model="specForm.designatedAreaList">
                    <el-checkbox v-for="(item, index) in provs" :label="item" :key="index" >{{ item }}</el-checkbox>
                  </el-checkbox-group>
                </template>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="specVisiable = false">取消</el-button>
              <el-button type="primary" @click="handleSpecAdd">确定</el-button>
            </div>
          </el-dialog>

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
</style>

<script>
import { listFreight } from '@/api/freight'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination

export default {
  name: 'Order',
  components: { Pagination },
  filters: {
    defaultFreePriceFilter(code) {
      if (code < 0) {
        return '不包邮'
      } else if (code === 0) {
        return '包邮'
      } else {
        return '满' + code + '元包邮'
      }
    }
  },
  data() {
    return {
      list: undefined,
      total: 0,
      listLoading: true,
      carriage: undefined,
      dialogStatus: '',
      dialogFormVisible: false,
      specList: undefined,
      specVisiable: false,
      listQuery: {
      },
      textMap: { update: '编辑', create: '创建' },
      dataForm: {
        id: undefined,
        templateName: undefined,
        spuLocation: undefined,
        isFree: 1,
        deliveryDeadline: 1,
        defaultFreePrice: 1,
        defaultFirstPrice: 0,
        defaultFirstNum: 1,
        defaultContinuePrice: 0,
        defaultContinueNum: 1,
        freightTemplateCarriageDOList: []
      },
      specForm: {
        designatedAreaList: [],
        designatedArea: undefined,
        freePrice: undefined,
        firstNum: undefined,
        firstMoney: undefined,
        continueNum: undefined,
        continueMoney: undefined
      },
      provs: ['北京市', '天津市', '河北省', '山西省', '内蒙古自治区', '辽宁省', '吉林省', '黑龙江省', '上海市', '江苏省', '浙江省', '安徽省', '福建省', '江西省', '山东省', '河南省', '湖北省', '湖南省', '广东省', '广西壮族自治区', '海南省', '重庆市', '四川省', '贵州省', '云南省', '西藏自治区', '陕西省', '甘肃省', '青海省', '宁夏回族自治区', '新疆维吾尔自治区', '台湾省', '香港特别行政区', '澳门特别行政区'],
      rules: {
        id: [{ required: true, message: '用户昵称不能为空', trigger: 'blur' }],
        templateName: [{ required: true, message: '用户昵称不能为空', trigger: 'blur' }],
        spuLocation: [{ required: true, message: '用户昵称不能为空', trigger: 'blur' }]
      },
      specRules: {
        freePrice: [{ required: true, message: '用户昵称不能为空', trigger: 'blur' }, { pattern: /^[0-9]*$/, message: '请输入整数' }, { min: 1, max: 9, message: '大于1,小于1亿' }],
        firstNum: [{ required: true, message: '用户昵称不能为空', trigger: 'blur' }, { pattern: /^[0-9]*$/, message: '请输入整数' }, { min: 1, max: 9, message: '大于1,小于1亿' }],
        firstMoney: [{ required: true, message: '用户昵称不能为空', trigger: 'blur' }, { pattern: /^[0-9]*$/, message: '请输入整数' }, { min: 1, max: 9, message: '大于1,小于1亿' }],
        continueNum: [{ required: true, message: '用户昵称不能为空', trigger: 'blur' }, { pattern: /^[0-9]*$/, message: '请输入整数' }, { min: 1, max: 9, message: '大于1,小于1亿' }],
        continueMoney: [{ required: true, message: '用户昵称不能为空', trigger: 'blur' }, { pattern: /^[0-9]*$/, message: '请输入整数' }, { min: 1, max: 9, message: '大于1,小于1亿' }]
      }

    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      listFreight(this.listQuery)
        .then(response => {
          this.list = response.data.data
          this.listLoading = false
        })
        .catch(() => {
          this.list = []
          this.listLoading = false
        })
    },
    resetData() {

    },
    resetSpec() {
      this.specForm = {
        designatedAreaList: [],
        designatedArea: undefined,
        freePrice: undefined,
        firstNum: undefined,
        firstMoney: undefined,
        continueNum: undefined,
        continueMoney: undefined
      }
    },
    // handleDelete(row) {
    //   this.$confirm('此操作将永久删除该评论' + row.id + ', 是否继续?', '提示', {
    //     confirmButtonText: '确定',
    //     cancelButtonText: '取消',
    //     type: 'warning'
    //   }).then(() => {
    //     deleteAppraise(row)
    //       .then(response => {
    //         this.$notify.success({
    //           title: '成功',
    //           message: '删除优惠券成功'
    //         })
    //         const index = this.list.indexOf(row)
    //         this.list.splice(index, 1)
    //       })
    //       .catch(response => {
    //         this.$notify.error({
    //           title: '失败',
    //           message: response.data.errmsg
    //         })
    //       })
    //   }).catch(() => {
    //     return false
    //   })
    // },
    createHandle() {
      this.resetData()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {

    },
    handleSpecified() {
      this.specVisiable = true
      this.$nextTick(() => {
        this.$refs['specForm'].clearValidate()
      })
    },
    updateData() {

    },
    handleSpecAdd() {
      this.specForm.designatedArea = this.specForm.designatedAreaList.join(',')
      var temp = Object.assign({}, this.specForm)
      this.dataForm.freightTemplateCarriageDOList.unshift(temp)
      this.specVisiable = false
    }
  }
}
</script>
