<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <!--<el-input v-model="listQuery.userId" clearable class="filter-item" style="width: 200px;" placeholder="请输入用户ID"/>-->
      <el-input
        v-model="listQuery.orderNo"
        clearable
        class="filter-item"
        style="width: 200px"
        placeholder="请输入订单编号"
      />
      <el-select
        v-model="listQuery.status"
        clearable
        style="width: 200px"
        class="filter-item"
        placeholder="请选择订单状态"
      >
        <el-option
          v-for="(key, value) in statusMap"
          :key="key"
          :label="key"
          :value="value"
        />
      </el-select>
      <el-button
        v-permission="['operation:order:list']"
        class="filter-item"
        type="primary"
        :icon="Search"
        @click="handleFilter"
        >查找</el-button
      >
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <br />
      <el-date-picker
        v-model="downData.gmtStart"
        type="datetime"
        class="filter-item"
        style="width: 200px"
        placeholder="选择开始日期"
        format="YYYY-MM-DD HH:mm:ss"
      />
      至
      <el-date-picker
        v-model="downData.gmtEnd"
        type="datetime"
        class="filter-item"
        style="width: 200px"
        placeholder="选择结束日期"
        format="YYYY-MM-DD HH:mm:ss"
      />

      <el-select
        v-model="downData.status"
        style="width: 200px"
        class="filter-item"
        placeholder="待出库"
      >
        <el-option
          v-for="(key, value) in statusMap"
          :key="key"
          :label="key"
          :value="value"
        />
      </el-select>
      <el-button
        :loading="downloadLoading"
        class="filter-item"
        type="primary"
        :icon="Download"
        @click="downExcelBtn"
        >导出</el-button
      >
      <el-button
        :loading="downloadLoading"
        class="filter-item"
        type="primary"
        :icon="Download"
        @click="downExcelStatisticsBtn"
        >汇总</el-button
      >
    </div>

    <!-- 查询结果 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      :cell-class-name="tableCellClassName"
      size="small"
      height="500px"
      element-loading-text="正在查询中。。。"
      border
      fit
      highlight-current-row
    >
      <el-table-column type="expand">
        <template #default="scope">
          <el-form label-position="right" class="table-expand">
            <el-form-item label="父单编号">
              <span>{{ scope.row.parentOrderNo }}</span>
            </el-form-item>
          </el-form>
          <el-form label-position="right" class="table-expand">
            <el-form-item label="联系人名">
              <span>{{ scope.row.consignee }}</span>
            </el-form-item>
          </el-form>
          <el-form label-position="right" class="table-expand">
            <el-form-item label="联系电话">
              <span>{{ scope.row.phone }}</span>
            </el-form-item>
          </el-form>
          <el-form label-position="right" class="table-expand">
            <el-form-item label="客服备注">
              <span>{{
                scope.row.adminMono ? scope.row.adminMono : '无'
              }}</span>
            </el-form-item>
          </el-form>
          <el-form
            v-if="scope.row.status === 60 || scope.row.refundReason"
            label-position="right"
            class="table-expand"
          >
            <el-form-item label="退款原因">
              <span>{{
                scope.row.refundReason
                  ? scope.row.refundReason
                  : '未填写退款原因'
              }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>

      <el-table-column
        align="center"
        width="210"
        label="订单编号"
        prop="orderNo"
      />

      <el-table-column align="center" width="80" label="用户ID" prop="userId" />

      <el-table-column
        align="center"
        width="120"
        label="订单状态"
        prop="status"
      >
        <template #default="scope">
          <el-tag :type="statusColor(scope.row.status)">{{
            orderStatusFilter(scope.row.status)
          }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column
        align="center"
        width="110"
        label="支付渠道"
        prop="payChannel"
      >
        <template #default="scope">
          <el-tag :type="payChannelColor(scope.row.payChannel)">{{
            payChannelFilter(scope.row.payChannel)
          }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column
        align="center"
        width="100"
        label="订单金额"
        prop="actualPrice"
      >
        <template #default="scope"
          >¥ {{ scope.row.actualPrice / 100.0 }}</template
        >
      </el-table-column>

      <!--<el-table-column align="center" label="支付金额" prop="actualPrice"/>-->

      <el-table-column
        align="center"
        width="140"
        label="创建时间"
        prop="gmtCreate"
      >
        <template #default="scope">{{ scope.row.gmtCreate }}</template>
      </el-table-column>

      <el-table-column
        align="center"
        width="140"
        label="物流渠道"
        prop="shipCode"
      >
        <template #default="scope">
          <el-tag
            v-if="scope.row.status <= 20 || scope.row.status >= 80"
            type="info"
            >未发货</el-tag
          >

          <el-tag v-else>{{ shipCodeFilter(scope.row.shipCode) }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column
        align="center"
        width="200"
        label="物流单号"
        prop="shipNo"
      />

      <el-table-column align="center" width="200" label="备注" prop="mono" />

      <el-table-column
        align="center"
        label="操作"
        width="300"
        class-name="small-padding fixed-width"
      >
        <template #default="scope">
          <el-button
            v-permission="['operation:order:detail']"
            type="primary"
            size="mini"
            @click="handleDetail(scope.row)"
            >详情</el-button
          >
          <el-button
            v-permission="['operation:order:detail']"
            v-if="scope.row.status === 20"
            type="primary"
            size="mini"
            @click="downOrderExcelBtn(scope.row)"
            >配送单</el-button
          >
          <el-button
            v-permission="['operation:order:ship']"
            v-if="scope.row.status === 20"
            type="primary"
            size="mini"
            @click="handleShip(scope.row)"
            >发货</el-button
          >
          <el-button
            v-permission="['operation:order:refund']"
            v-if="scope.row.status === 60"
            type="primary"
            size="mini"
            @click="handleRefund(scope.row)"
            >退款</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      v-model:page="listQuery.page"
      v-model:limit="listQuery.limit"
      @pagination="getList"
    />

    <!-- 订单详情对话框 -->
    <el-dialog v-model="orderDialogVisible" title="订单详情" width="800">
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
          <el-tag>{{ orderStatusFilter(orderDetail.status) }}</el-tag>
        </el-form-item>
        <el-form-item label="支付渠道">
          <el-tag>{{ payChannelFilter(orderDetail.payChannel) }}</el-tag>
        </el-form-item>
        <el-form-item label="用户留言">
          <span>{{ orderDetail.mono }}</span>
        </el-form-item>
        <el-form-item label="客服备注">
          <span>{{ orderDetail.adminMono }}</span>
          <el-button
            type="primary"
            :icon="Edit"
            @click="adminMonoDialogVisible = true"
            >编辑</el-button
          >
        </el-form-item>
        <el-form-item label="配送费用">
          {{
            orderDetail.freightPrice > 0
              ? orderDetail.freightPrice / 100.0
              : '免运费'
          }}
        </el-form-item>
        <el-form-item label="收货信息">
          <span>（收货人）{{ orderDetail.consignee }}</span>
          <span>（手机号）{{ orderDetail.phone }}</span>
          <span
            >（地址）{{ orderDetail.province }}{{ orderDetail.city
            }}{{ orderDetail.county }}{{ orderDetail.address
            }}{{ orderDetail.address }}</span
          >
        </el-form-item>
        <el-form-item label="商品信息">
          <el-table
            :data="orderDetail.skuList"
            size="small"
            border
            fit
            highlight-current-row
          >
            <el-table-column align="center" label="名称" prop="spuTitle" />
            <el-table-column align="center" label="规格" prop="title" />
            <el-table-column align="center" label="商品数量" prop="num" />
            <el-table-column align="center" label="单位" prop="unit" />
            <el-table-column align="center" label="总价（单价*数量）">
              <template #default="scope">
                <span>{{ (scope.row.price * scope.row.num) / 100 }}</span>
              </template>
            </el-table-column>
          </el-table>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-dialog v-model="adminMonoDialogVisible" title="客服备注">
      <el-form
        status-icon
        label-position="left"
        label-width="100px"
        style="width: 400px; margin-left: 50px"
      >
        <el-form-item label="等级" prop="adminMonoLevel">
          <el-radio-group v-model="orderDetail.adminMonoLevel">
            <el-radio :label="0">无色</el-radio>
            <el-radio :label="1">绿色</el-radio>
            <el-radio :label="2">橙黄</el-radio>
            <el-radio :label="3">红色</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="内容" prop="adminMono">
          <el-input v-model="orderDetail.adminMono" />
        </el-form-item>
      </el-form>
      <template #footer class="dialog-footer">
        <el-button @click="adminMonoDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleAdminMono">确定</el-button>
      </template>
    </el-dialog>

    <!-- 发货对话框 -->
    <el-dialog v-model="shipDialogVisible" title="发货">
      <el-form
        ref="shipForm"
        :model="shipForm"
        :rules="shipRules"
        status-icon
        label-position="left"
        label-width="100px"
        style="width: 400px; margin-left: 50px"
      >
        <el-form-item label="快递公司" prop="shipCode">
          <el-select v-model="shipForm.shipCode" placeholder="请选择快递公司">
            <el-option
              v-for="(key, value) in shipCodeMap"
              :key="key"
              :label="key"
              :value="value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="快递编号" prop="shipNo">
          <el-input
            :disabled="shipForm.shipCode === 'NONE'"
            v-model="shipForm.shipNo"
          />
        </el-form-item>
      </el-form>
      <template #footer class="dialog-footer">
        <el-button @click="shipDialogVisible = false">取消</el-button>
        <el-button :disabled="shipSubmiting" type="primary" @click="confirmShip"
          >确定</el-button
        >
      </template>
    </el-dialog>

    <!-- 退款对话框 -->
    <el-dialog v-model="refundDialogVisible" title="退款">
      <el-form
        ref="refundForm"
        :model="refundForm"
        :rules="refundRules"
        status-icon
        label-position="left"
        label-width="100px"
        style="width: 400px; margin-left: 50px"
      >
        <el-form-item label="tips">
          <span>允许退款后钱会退回对方账户，请确认您已经收到退货</span>
        </el-form-item>
        <el-form-item label="操作"> </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script setup>
import { Delete, Edit, Search, Download, Upload } from '@element-plus/icons-vue'
import { ElNotification } from 'element-plus'
import { ref, onMounted } from 'vue'
import {
  listOrder,
  shipOrder,
  refundOrder,
  detailOrder,
  getExcelInfo,
  editAdminMono,
  getExcelStatistics,
} from '@/api/order'
import Pagination from '@/components/Pagination'
import checkPermission from '@/utils/permission'

// 状态映射表
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
  90: '已取消(系统)',
}

// 物流代码映射表
const shipCodeMap = {
  NONE: '无需物流公司',
  SF: '顺丰速运',
  HTKY: '百世快递',
  ZTO: '中通快递',
  STO: '申通快递',
  YTO: '圆通速递',
  YD: '韵达速递',
  YZPY: '邮政快递包裹',
  EMS: 'EMS',
  HHTT: '天天快递',
  JD: '京东快递',
  UC: '优速快递',
  DBL: '德邦快递',
  ZJS: '宅急送',
  TNT: 'TNT快递',
}

// 支付渠道映射表
const payChannelMap = {
  WX: '微信支付',
  ALI: '支付宝',
  OFFLINE: '线下支付',
}

// 定义响应式数据
const excelData = ref({
  barcode: undefined,
  name: undefined,
  specifications: undefined,
  unit: undefined,
  num: undefined,
  address: undefined,
})
const excelDataList = ref([])
const downData = ref({
  status: '',
  gmtStart: undefined,
  gmtEnd: undefined,
})
const list = ref(undefined)
const total = ref(0)
const listLoading = ref(true)
const listQuery = ref({
  page: 1,
  limit: 20,
  id: undefined,
  name: undefined,
})
const orderDialogVisible = ref(false)
const orderDetail = ref({})
const refundForm = ref({
  orderNo: undefined,
  type: 0,
})
const refundDialogVisible = ref(false)
const refundSubmiting = ref(false)
const adminMonoDialogVisible = ref(false)
const downloadLoading = ref(false)
const shipForm = ref({
  orderNo: '',
  shipCode: 'NONE',
  shipNo: '',
})
const shipDialogVisible = ref(false)
const shipSubmiting = ref(false)

// 表单验证规则
const refundRules = {
  orderNo: [
    { required: true, message: '请使用非IE浏览器重试', trigger: 'blur' },
  ],
  type: [{ required: true, message: '请选择退货类型', trigger: 'blur' }],
}
const shipRules = {
  shipCode: [{ required: true, message: '请选择物流类型', trigger: 'blur' }],
  orderNo: [
    { required: true, message: '请使用非IE浏览器重试', trigger: 'blur' },
  ],
}

// 获取订单列表
const getList = async () => {
  listLoading.value = true
  try {
    const response = await listOrder(listQuery.value)
    list.value = response.data.data.items
    total.value = response.data.data.total
  } catch (error) {
    list.value = []
    total.value = 0
  } finally {
    listLoading.value = false
  }
}

// 处理筛选
const handleFilter = () => {
  listQuery.value.page = 1
  getList()
}

// 查看订单详情
const handleDetail = async (row) => {
  const response = await detailOrder(row.id)
  orderDetail.value = response.data.data
  orderDialogVisible.value = true
}

// 处理发货操作
const handleShip = (row) => {
  shipDialogVisible.value = true
  shipForm.value.orderNo = row.orderNo
  shipForm.value.shipCode = ''
  shipForm.value.shipNo = ''
}

// 确认发货
const confirmShip = async () => {
  // 这里需要手动实现表单验证逻辑
  if (shipForm.value.shipCode !== 'NONE' && !shipForm.value.shipNo) {
    ElNotification.error({
      title: '失败',
      message: '请填写运单号',
    })
  } else {
    shipSubmiting.value = true
    try {
      await shipOrder(shipForm.value)
      shipSubmiting.value = false
      shipDialogVisible.value = false
      ElNotification.success({
        title: '成功',
        message: '确认发货成功！',
      })
      getList()
    } catch (response) {
      shipSubmiting.value = false
      ElNotification.error({
        title: '失败',
        message: response.data.errmsg,
      })
    }
  }
}

// 处理客服备注
const handleAdminMono = async () => {
  if (!orderDetail.value) {
    ElNotification.error({
      title: '失败',
      message: '数据不对，请刷新页面重试',
    })
    return
  }
  try {
    await editAdminMono(
      orderDetail.value.id,
      orderDetail.value.adminMonoLevel,
      orderDetail.value.adminMono
    )
    ElNotification.success({
      title: '成功',
      message: '客服备注成功！',
    })
    getList()
    adminMonoDialogVisible.value = false
  } catch (response) {
    shipSubmiting.value = false
    ElNotification.error({
      title: '失败',
      message: response.data.errmsg,
    })
  }
}

// 处理退款操作
const handleRefund = (row) => {
  const obj = {
    orderNo: row.orderNo,
    sum: row.payPrice / 100.0,
    type: 0,
  }
  refundForm.value = { ...obj }
  refundDialogVisible.value = true
  shipForm.value.shipCode = 'NONE'
}

// 确认退款
const confirmRefund = async () => {
  // 这里需要手动实现表单验证逻辑
  refundSubmiting.value = true
  const obj = {
    type: refundForm.value.type,
    orderNo: refundForm.value.orderNo,
  }
  if (refundForm.value.sum) {
    obj['sum'] = parseInt(refundForm.value.sum * 100)
  }
  try {
    await refundOrder(obj)
    refundSubmiting.value = false
    refundDialogVisible.value = false
    ElNotification.success({
      title: '成功',
      message: '确认退款成功！',
    })
    getList()
  } catch (response) {
    refundSubmiting.value = false
    ElNotification.error({
      title: '失败',
      message: response.data.errmsg,
    })
  }
}

// 订单表格中下载
const downOrderExcelBtn = async (row) => {
  const response = await detailOrder(row.id)
  const temp = response.data.data
  const obj = {}
  obj.address = temp.province + temp.city + temp.county + temp.address
  obj.orderNo = temp.orderNo
  obj.phone = temp.phone
  obj.mono = temp.mono
  obj.adminMono = temp.adminMono
  obj.area = temp.province + temp.city + temp.county
  obj.consignee = temp.consignee
  for (let j = 0; j < temp.skuList.length; j++) {
    const sku = temp.skuList[j]
    obj.unit = sku.unit
    obj.num = sku.num
    obj.specifications = sku.title
    obj.barcode = sku.barCode
    obj.name = sku.spuTitle
    const copy = { ...obj }
    excelDataList.value.push(copy)
  }
  handleDownload(excelDataList.value)
  excelDataList.value = []
}

// 选择条件下载
const downExcelBtn = async () => {
  downloadLoading.value = true
  const dataInfo = {
    status: downData.value.status,
  }
  if (downData.value.gmtStart) {
    dataInfo['gmtStart'] = downData.value.gmtStart.getTime()
  }
  if (downData.value.gmtEnd) {
    dataInfo['gmtEnd'] = downData.value.gmtEnd.getTime()
  }
  try {
    const response = await getExcelInfo(dataInfo)
    if (response.data.data == null) {
      ElNotification.error({
        title: '失败',
        message: '没有信息可以打印',
      })
    }
    const data = response.data.data
    for (let i = 0; i < data.length; i++) {
      const temp = data[i]
      const obj = {}
      obj.address = temp.province + temp.city + temp.county + temp.address
      obj.orderNo = temp.orderNo
      obj.phone = temp.phone
      obj.mono = temp.mono
      obj.adminMono = temp.adminMono
      obj.area = temp.province + temp.city + temp.county
      obj.consignee = temp.consignee
      for (let j = 0; j < temp.skuList.length; j++) {
        const sku = temp.skuList[j]
        obj.unit = sku.unit
        obj.num = sku.num
        obj.specifications = sku.title
        obj.barcode = sku.barCode
        obj.name = sku.spuTitle
        const copy = { ...obj }
        excelDataList.value.push(copy)
      }
    }
    handleDownload(excelDataList.value)
    excelDataList.value = []
    downloadLoading.value = false
  } catch (response) {
    downloadLoading.value = false
    ElNotification.error({
      title: '失败',
      message: response.data.errmsg,
    })
  }
}

// 根据订单状态返回颜色类名
const statusColor = (status) => {
  switch (status) {
    case 10:
    case 80:
    case 90:
      return 'info'
    case 30:
    case 40:
    case 50:
      return 'success'
    case 20:
      return 'warning'
    case 60:
    case 70:
      return 'danger'
    default:
      return 'primary'
  }
}

// 根据支付渠道返回颜色类名
const payChannelColor = (channel) => {
  if (!channel) {
    return 'info'
  }
  if (channel === 'WX') {
    return 'success'
  } else if (channel === 'OFFLINE') {
    return 'warning'
  } else if (channel === 'ALI') {
    return 'primary'
  } else {
    return 'danger'
  }
}

// 处理下载
const handleDownload = (data) => {
  console.log(data, '222')

  import('@/vendor/Export2Excel').then((excel) => {
    console.log(excel, '11111111111')
    const tHeader = [
      '订单编号',
      '商品名称',
      '规格',
      '单位',
      '配送数量',
      '配送地址',
      '收货人',
      '联系方式',
      '区域划分',
      '客服备注',
      '用户备注',
    ]
    const filterVal = [
      'orderNo',
      'name',
      'specifications',
      'unit',
      'num',
      'address',
      'consignee',
      'phone',
      'area',
      'mono',
      'adminMono',
    ]
    excel.export_json_to_excel2(tHeader, data, filterVal, '订单信息')
  })
}

// 订单汇总下载
const downExcelStatisticsBtn = async () => {
  const param = {}
  if (downData.value.gmtStart) {
    param['gmtStart'] = downData.value.gmtStart.getTime()
  }
  if (downData.value.gmtEnd) {
    param['gmtEnd'] = downData.value.gmtEnd.getTime()
  }
  try {
    const response = await getExcelStatistics(param)
    if (response.data.data == null || response.data.data.length === 0) {
      ElNotification.error({
        title: '失败',
        message: '没有信息可以打印',
      })
    } else {
      handleStatisticsDownload(response.data.data)
    }
  } catch (response) {
    downloadLoading.value = false
    ElNotification.error({
      title: '失败',
      message: response.data.errmsg,
    })
  }
}

// 处理统计信息下载
const handleStatisticsDownload = (data) => {
  import('@/vendor/Export2Excel').then((excel) => {
    const tHeader = ['商品名称', 'spuId', '规格', 'skuId', '数量']
    const filterVal = ['spuTitle', 'spuId', 'skuTitle', 'skuId', 'num']
    excel.export_json_to_excel2(tHeader, data, filterVal, '统计信息')
  })
}

// 表格单元格类名处理
const tableCellClassName = ({ row, column, rowIndex, columnIndex }) => {
  if (columnIndex === 1) {
    if (row.adminMonoLevel) {
      if (row.adminMonoLevel === 1) {
        return 'success-row'
      } else if (row.adminMonoLevel === 2) {
        return 'warning-row'
      } else if (row.adminMonoLevel === 3) {
        return 'danger-row'
      }
    }
    return ''
  }
}

// 生命周期钩子，组件挂载时获取订单列表
onMounted(() => {
  getList()
})

// 手动实现过滤器
const orderStatusFilter = (status) => {
  return statusMap[status]
}

const payChannelFilter = (channel) => {
  const str = payChannelMap[channel]
  if (str) {
    return str
  }
  return '未支付'
}

const shipCodeFilter = (shipCode) => {
  if (shipCode) {
    return shipCodeMap[shipCode]
  }
  return '无需物流公司'
}

// 导出权限判断函数
const checkPermissionFn = checkPermission
</script>
<style>
.el-table .danger-row {
  background: rgb(201, 143, 143);
}

.el-table .warning-row {
  background: rgb(197, 175, 142);
}

.el-table .success-row {
  background: rgb(166, 202, 149);
}
</style>
