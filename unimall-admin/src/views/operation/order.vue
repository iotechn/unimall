<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <!--<el-input v-model="listQuery.userId" clearable class="filter-item" style="width: 200px;" placeholder="请输入用户ID"/>-->
      <el-input
        v-model="listQuery.orderNo"
        clearable
        class="filter-item"
        style="width: 200px;"
        placeholder="请输入订单编号"
      />
      <el-select
        v-model="listQuery.status"
        style="width: 200px"
        class="filter-item"
        placeholder="请选择订单状态"
      >
        <el-option v-for="(key, value) in statusMap" :key="key" :label="key" :value="value" />
      </el-select>
      <el-button
        v-permission="['operation:order:list']"
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="handleFilter"
      >查找</el-button>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <br>
      <el-date-picker
        v-model="downData.gmtStart"
        type="datetime"
        style="width: 200px"
        class="filter-item"
        default-time="00:00:00"
        placeholder="选择开始日期"
      />
      至
      <el-date-picker
        v-model="downData.gmtEnd"
        type="datetime"
        style="width: 200px"
        class="filter-item"
        default-time="00:00:00"
        placeholder="选择结束日期"
      />
      <el-select
        v-model="downData.status"
        style="width: 200px"
        class="filter-item"
        placeholder="待出库"
      >
        <el-option v-for="(key, value) in statusMap" :key="key" :label="key" :value="value" />
      </el-select>
      <el-button :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="downExcelBtn">导出</el-button>
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
      <el-table-column align="center" width="180" label="订单编号" prop="orderNo" />

      <el-table-column align="center" width="80" label="用户ID" prop="userId" />

      <el-table-column align="center" width="120" label="订单状态" prop="status">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.status | orderStatusFilter }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" width="110" label="支付渠道" prop="payChannel">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.payChannel | payChannelFilter }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" width="100" label="订单金额" prop="actualPrice">
        <template slot-scope="scope">¥ {{ scope.row.actualPrice / 100.0 }}</template>
      </el-table-column>

      <!--<el-table-column align="center" label="支付金额" prop="actualPrice"/>-->

      <el-table-column align="center" width="140" label="创建时间" prop="gmtCreate">
        <template slot-scope="scope">{{ scope.row.gmtCreate | formatTime }}</template>
      </el-table-column>

      <el-table-column align="center" width="140" label="物流渠道" prop="shipCode">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.shipCode | shipCodeFilter }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" width="200" label="物流单号" prop="shipCode"/>

      <el-table-column align="center" width="200" label="备注" prop="mono"/>

      <el-table-column align="center" label="操作" width="300" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-permission="['operation:order:detail']"
            type="primary"
            size="mini"
            @click="handleDetail(scope.row)"
          >详情</el-button>
          <el-button
            v-permission="['operation:order:detail']"
            v-if="scope.row.status===20"
            type="primary"
            size="mini"
            @click="downOrderExcelBtn(scope.row)"
          >配送单</el-button>
          <el-button
            v-permission="['operation:order:ship']"
            v-if="scope.row.status===20"
            type="primary"
            size="mini"
            @click="handleShip(scope.row)"
          >发货</el-button>
          <el-button
            v-permission="['operation:order:refund']"
            v-if="scope.row.status===60"
            type="primary"
            size="mini"
            @click="handleRefund(scope.row)"
          >退款</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="listQuery.page"
      :limit.sync="listQuery.limit"
      @pagination="getList"
    />

    <!-- 订单详情对话框 -->
    <el-dialog :visible.sync="orderDialogVisible" title="订单详情" width="800">
      <el-form :data="orderDetail" label-position="left">
        <el-form-item label="订单编号">
          <span>{{ orderDetail.orderNo }}</span>
        </el-form-item>
        <el-form-item label="用户Id">
          <span>{{ orderDetail.userId }}</span>
        </el-form-item>
        <el-form-item label="订单渠道">
          <span>{{ orderDetail.channel }}</span>
        </el-form-item>
        <el-form-item label="订单状态">
          <template>
            <el-tag>{{ orderDetail.status | orderStatusFilter }}</el-tag>
          </template>
        </el-form-item>
        <el-form-item label="支付渠道">
          <template>
            <el-tag>{{ orderDetail.payChannel | payChannelFilter }}</el-tag>
          </template>
        </el-form-item>
        <el-form-item label="用户留言">
          <span>{{ orderDetail.mono }}</span>
        </el-form-item>
        <el-form-item label="收货信息">
          <span>（收货人）{{ orderDetail.consignee }}</span>
          <span>（手机号）{{ orderDetail.phone }}</span>
          <span>（地址）{{ orderDetail.province }}{{ orderDetail.city }}{{ orderDetail.county }}{{ orderDetail.address }}{{ orderDetail.address }}</span>
        </el-form-item>
        <el-form-item label="商品信息">
          <el-table :data="orderDetail.skuList" size="small" border fit highlight-current-row>
            <el-table-column align="center" label="名称" prop="spuTitle" />
            <el-table-column align="center" label="规格" prop="title" />
            <el-table-column align="center" label="商品数量" prop="num" />
            <el-table-column align="center" label="单位" prop="unit" />
            <el-table-column align="center" label="总价（单价*数量）">
              <template slot-scope="scope">
                <span>{{ scope.row.price * scope.row.num / 100 }}</span>
              </template>
            </el-table-column>
          </el-table>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 发货对话框 -->
    <el-dialog :visible.sync="shipDialogVisible" title="发货">
      <el-form
        ref="shipForm"
        :model="shipForm"
        :rules="shipRules"
        status-icon
        label-position="left"
        label-width="100px"
        style="width: 400px; margin-left:50px;"
      >
        <el-form-item label="快递公司" prop="shipCode">
          <el-select v-model="shipForm.shipCode" placeholder="请选择快递公司">
            <el-option v-for="(key, value) in shipCodeMap" :key="key" :label="key" :value="value"/>
          </el-select>
        </el-form-item>
        <el-form-item label="快递编号" prop="shipNo">
          <el-input :disabled="shipForm.shipCode === 'NONE'" v-model="shipForm.shipNo" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="shipDialogVisible = false">取消</el-button>
        <el-button :disabled="shipSubmiting" type="primary" @click="confirmShip">确定</el-button>
      </div>
    </el-dialog>

    <!-- 退款对话框 -->
    <el-dialog :visible.sync="refundDialogVisible" title="退款">
      <el-form
        ref="refundForm"
        :model="refundForm"
        :rules="refundRules"
        status-icon
        label-position="left"
        label-width="100px"
        style="width: 400px; margin-left:50px;"
      >
        <el-form-item label="tips">
          <span>允许退款后钱会退回对方账户，请确认您已经收到退货</span>
        </el-form-item>
        <el-form-item label="操作">
          <el-radio v-model="refundForm.type" label="0">拒绝退款</el-radio>
          <el-radio v-model="refundForm.type" label="1">允许退款</el-radio>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="refundDialogVisible = false">取消</el-button>
        <el-button :disabled="refundSubmiting" type="primary" @click="confirmRefund">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<style>
</style>

<script>
import { listOrder, shipOrder, refundOrder, detailOrder, getExcelInfo } from '@/api/order'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination
import checkPermission from '@/utils/permission' // 权限判断函数

const statusMap = {
  10: '未付款',
  12: '正在拼团',
  20: '待出库',
  30: '待收货',
  40: '待评价',
  50: '已完成',
  60: '退款中',
  70: '已退款',
  80: '已取消',
  90: '已取消(系统)'
}

const shipCodeMap = {
  'NONE': '无需物流公司',
  'SF': '顺丰速运',
  'HTKY': '百世快递',
  'ZTO': '中通快递',
  'STO': '申通快递',
  'YTO': '圆通速递',
  'YD': '韵达速递',
  'YZPY': '邮政快递包裹',
  'EMS': 'EMS',
  'HHTT': '天天快递',
  'JD': '京东快递',
  'UC': '优速快递',
  'DBL': '德邦快递',
  'ZJS': '宅急送',
  'TNT': 'TNT快递'
}

const payChannelMap = {
  WX: '微信支付',
  ALI: '支付宝',
  OFFLINE: '线下支付'
}

export default {
  name: 'Order',
  components: { Pagination },
  filters: {
    orderStatusFilter(status) {
      return statusMap[status]
    },
    payChannelFilter(channel) {
      const str = payChannelMap[channel]
      if (str) {
        return str
      }
      return '未支付'
    },
    shipCodeFilter(shipCode) {
      if (shipCode) {
        return shipCodeMap[shipCode]
      }
      return '未发货'
    }
  },
  data() {
    return {
      excelData: {
        barcode: undefined,
        name: undefined,
        specifications: undefined,
        unit: undefined,
        num: undefined,
        address: undefined
      },
      excelDataList: [],
      downData: {
        status: '',
        gmtStart: undefined,
        gmtEnd: undefined
      },
      shipCodeMap,
      list: undefined,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        id: undefined,
        name: undefined,
        orderStatusArray: [],
        sort: 'id',
        order: 'desc'
      },
      statusMap,
      orderDialogVisible: false,
      orderDetail: {},
      refundForm: {
        orderNo: undefined,
        type: '0'
      },
      refundDialogVisible: false,
      refundSubmiting: false,
      downloadLoading: false,
      shipForm: {
        orderNo: undefined,
        shipCode: 'NONE',
        shipNo: undefined
      },
      shipDialogVisible: false,
      shipSubmiting: false,
      refundRules: {
        orderNo: [
          { required: true, message: '请使用非IE浏览器重试', trigger: 'blur' }
        ],
        type: [
          { required: true, message: '请选择退货类型', trigger: 'blur' }
        ]
      },
      shipRules: {
        shipCode: [
          { required: true, message: '请选择物流类型', trigger: 'blur' }
        ],
        orderNo: [
          { required: true, message: '请使用非IE浏览器重试', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    checkPermission,
    getList() {
      this.listLoading = true
      listOrder(this.listQuery)
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
    handleDetail(row) {
      detailOrder(row.id).then(response => {
        this.orderDetail = response.data.data
      })
      this.orderDialogVisible = true
    },
    handleShip(row) {
      this.shipDialogVisible = true
      this.shipForm.orderNo = row.orderNo
    },
    confirmShip() {
      this.$refs['shipForm'].validate(valid => {
        if (valid) {
          this.shipSubmiting = true
          if (this.shipForm.shipCode !== 'NONE' && !this.shipForm.shipNo) {
            this.$notify.error({
              title: '失败',
              message: '请填写运单号'
            })
          } else {
            shipOrder(this.shipForm)
              .then(response => {
                this.shipSubmiting = false
                this.shipDialogVisible = false
                this.$notify.success({
                  title: '成功',
                  message: '确认发货成功！'
                })
                this.getList()
              })
              .catch(response => {
                this.shipSubmiting = false
                this.$notify.error({
                  title: '失败',
                  message: response.data.errmsg
                })
              })
          }
        }
      })
    },
    handleRefund(row) {
      this.refundForm.orderNo = row.orderNo
      this.refundDialogVisible = true
      this.shipForm.shipCode = 'NONE'
    },
    confirmRefund() {
      this.$refs['refundForm'].validate(valid => {
        if (valid) {
          this.refundSubmiting = true
          refundOrder(this.refundForm)
            .then(response => {
              this.refundSubmiting = false
              this.refundDialogVisible = false
              this.$notify.success({
                title: '成功',
                message: '确认退款成功！'
              })
              this.getList()
            })
            .catch(response => {
              this.refundSubmiting = false
              this.$notify.error({
                title: '失败',
                message: response.data.errmsg
              })
            })
        }
      })
    },
    // 订单表格中下载
    downOrderExcelBtn(row) {
      detailOrder(row.id).then(response => {
        var temp = response.data.data
        this.downData.address = temp.province + temp.city + temp.county + temp.address
        for (var j = 0; j < temp.skuList.length; j++) {
          var sku = temp.skuList[j]
          this.downData.unit = sku.unit
          this.downData.num = sku.num
          this.downData.specifications = sku.title
          this.downData.barcode = sku.barCode
          this.downData.name = sku.spuTitle
          var copy = Object.assign({}, this.downData)
          this.excelDataList.push(copy)
        }
        this.handleDownload(this.excelDataList)
        this.excelDataList = []
      })
    },
    // 选择条件下载
    downExcelBtn() {
      this.downloadLoading = true
      const dataInfo = Object.assign({}, {
        status: this.downData.status,
        gmtStart: this.downData.gmtStart.getTime(),
        gmtEnd: this.downData.gmtEnd.getTime()
      })
      getExcelInfo(dataInfo).then(response => {
        if (response.data.data == null) {
          this.$notify.error({
            title: '失败',
            message: '没有信息可以打印'
          })
        }

        var data = response.data.data
        for (var i = 0; i < data.length; i++) {
          var temp = data[i]
          this.downData.address = temp.province + temp.city + temp.county + temp.address
          for (var j = 0; j < temp.skuList.length; j++) {
            var sku = temp.skuList[j]
            this.downData.unit = sku.unit
            this.downData.num = sku.num
            this.downData.specifications = sku.title
            this.downData.barcode = sku.barCode
            this.downData.name = sku.spuTitle
            var copy = Object.assign({}, this.downData)
            this.excelDataList.push(copy)
          }
        }
        this.handleDownload(this.excelDataList)
        this.excelDataList = []
        this.downloadLoading = false
      })
        .catch(response => {
          this.downloadLoading = false
          this.$notify.error({
            title: '失败',
            message: response.data.errmsg
          })
        })
    },
    handleDownload(data) {
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = [
          '商品编码',
          '国际条码',
          '商品名称',
          '规格',
          '单位',
          '包装系数',
          '配送数量',
          '件数',
          '配送机构',
          '备注',
          '配送规格',
          '零售价'
        ]
        const filterVal = [
          'productCode',
          'barcode',
          'name',
          'specifications',
          'unit',
          'coefficient',
          'num',
          'num',
          'address',
          'note',
          'deliveryLine',
          'retailPrice'
        ]
        excel.export_json_to_excel2(tHeader, data, filterVal, '订单信息')
      })
    }
  }
}
</script>
