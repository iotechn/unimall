<template>
  <div class="app-container">

    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <!--<el-input v-model="listQuery.userId" clearable class="filter-item" style="width: 200px;" placeholder="请输入用户ID"/>-->
      <el-input v-model="listQuery.orderId" clearable class="filter-item" style="width: 200px;" placeholder="请输入订单编号"/>
      <el-select v-model="listQuery.status" style="width: 200px" class="filter-item" placeholder="请选择订单状态">
        <el-option v-for="(key, value) in statusMap" :key="key" :label="key" :value="value"/>
      </el-select>
      <el-button v-permission="['admin:order:list']" class="filter-item" type="primary" icon="el-icon-search"
                 @click="handleFilter">查找
      </el-button>
      <!--<el-button :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload">导出</el-button>-->
    </div>

    <!-- 查询结果 -->
    <el-table v-loading="listLoading" :data="list" size="small" element-loading-text="正在查询中。。。" border fit
              highlight-current-row>

      <el-table-column align="center" min-width="75" max-width="75" label="订单编号" prop="orderId"/>

      <el-table-column align="center" label="用户ID" prop="userId"/>

      <el-table-column align="center" label="订单状态" prop="status">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.status | orderStatusFilter }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="支付方式" prop="payway">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.payway | orderPaywayFilter }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="订单金额" prop="totalPrice"/>

      <!--<el-table-column align="center" label="支付金额" prop="actualPrice"/>-->

      <el-table-column align="center" label="创建时间" prop="gmtCreate">
        <template slot-scope="scope">
          {{ scope.row.gmtCreate | formatTime }}
        </template>
      </el-table-column>

      <!--<el-table-column align="center" label="物流单号" prop="shipSn"/>-->

      <!--<el-table-column align="center" label="物流渠道" prop="shipChannel"/>-->

      <el-table-column align="center" label="操作" width="200" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-permission="['admin:order:detail']" type="primary" size="mini"
                     @click="handleDetail(scope.row)">详情
          </el-button>
          <el-button v-permission="['admin:order:ship']" v-if="scope.row.status==20" type="primary"
                     size="mini" @click="handleShip(scope.row)">发货
          </el-button>
          <!--<el-button v-permission="['POST /admin/order/refund']" v-if="scope.row.status==20" type="primary"-->
          <!--size="mini" @click="handleRefund(scope.row)">退款-->
          <!--</el-button>-->
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit"
                @pagination="getList"/>

    <!-- 订单详情对话框 -->
    <el-dialog :visible.sync="orderDialogVisible" title="订单详情" width="800">

      <el-form :data="orderDetail" label-position="left">
        <el-form-item label="订单编号">
          <span>{{ orderDetail.order.orderId }}</span>
        </el-form-item>
        <el-form-item label="订单状态">
          <template slot-scope="scope">
            <el-tag>{{ orderDetail.order.status | orderStatusFilter }}</el-tag>
          </template>
        </el-form-item>
        <el-form-item label="支付方式">
          <template slot-scope="scope">
            <el-tag>{{ orderDetail.order.payway | orderPaywayFilter }}</el-tag>
          </template>
        </el-form-item>
        <el-form-item label="订单用户">
          <span>{{ orderDetail.user.nickname }}</span>
        </el-form-item>
        <el-form-item label="用户留言">
          <span>{{ orderDetail.order.mono }}</span>
        </el-form-item>
        <el-form-item label="收货信息">
          <span>（收货人）{{ orderDetail.order.contactName }}</span>
          <span>（手机号）{{ orderDetail.order.contactPhone }}</span>
          <span>（地址）{{ orderDetail.order.address }}</span>
        </el-form-item>
        <el-form-item label="商品信息">
          <el-table :data="orderDetail.orderGoods" size="small" border fit highlight-current-row>
            <el-table-column align="center" label="商品名称" prop="goodsTitle"/>
            <el-table-column align="center" label="商品数量" prop="num"/>
            <el-table-column align="center" label="总价（单价*数量）" prop="totalPrice"/>
          </el-table>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 发货对话框 -->
    <!--<el-dialog :visible.sync="shipDialogVisible" title="发货">-->
    <!--<el-form ref="shipForm" :model="shipForm" status-icon label-position="left" label-width="100px"-->
    <!--style="width: 400px; margin-left:50px;">-->
    <!--<el-form-item label="快递公司" prop="shipChannel">-->
    <!--<el-input v-model="shipForm.shipChannel"/>-->
    <!--</el-form-item>-->
    <!--<el-form-item label="快递编号" prop="shipSn">-->
    <!--<el-input v-model="shipForm.shipSn"/>-->
    <!--</el-form-item>-->
    <!--</el-form>-->
    <!--<div slot="footer" class="dialog-footer">-->
    <!--<el-button @click="shipDialogVisible = false">取消</el-button>-->
    <!--<el-button type="primary" @click="confirmShip">确定</el-button>-->
    <!--</div>-->
    <!--</el-dialog>-->

    <!-- 退款对话框 -->
    <el-dialog :visible.sync="refundDialogVisible" title="退款">
      <el-form ref="refundForm" :model="refundForm" status-icon label-position="left" label-width="100px"
               style="width: 400px; margin-left:50px;">
        <el-form-item label="退款金额" prop="refundMoney">
          <el-input v-model="refundForm.refundMoney" :disabled="true"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="refundDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmRefund">确定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<style>

</style>

<script>
  import {listOrder, shipOrder, refundOrder, detailOrder} from '@/api/order'
  import Pagination from '@/components/Pagination' // Secondary package based on el-pagination
  import checkPermission from '@/utils/permission' // 权限判断函数

  const statusMap = {
    10: '未付款',
    20: '待出库',
    30: '配送中',
    40: '待确认',
    50: '已完成',
    60: '取消中',
    70: '已取消'
  }

  const paywyMap = {
    1: '积分支付',
    2: '线下支付',
    4: '在线支付'
  }

  export default {
    name: 'Order',
    components: {Pagination},
    filters: {
      orderStatusFilter(status) {
        return statusMap[status]
      },
      orderPaywayFilter(code) {
        return paywyMap[code]
      },
      formatTime(time) {
        let d =  new Date(time)
        return (
          d.getFullYear() + '-' +
          d.getMonth() + 1 + '-' +
          d.getDate() + ' ' +
          d.getHours() + ':' +
          d.getMinutes()
        )
      }
    },
    data() {
      return {
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
        orderDetail: {
          order: {},
          user: {},
          orderGoods: []
        },
        refundForm: {
          orderId: undefined,
          refundMoney: undefined
        },
        refundDialogVisible: false,
        downloadLoading: false
      }
    },
    created() {
      this.getList()
    },
    methods: {
      checkPermission,
      getList() {
        this.listLoading = true
        listOrder(this.listQuery).then(response => {
          this.list = response.data.data.items
          this.total = response.data.data.total
          this.listLoading = false
        }).catch(() => {
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

        this.$confirm('您确定要发货 ' + row.contactName + ' 的订单吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          shipOrder(row.id).then(response => {
            this.$notify.success({
              title: '成功',
              message: '确认发货成功'
            })
            this.getList()
          }).catch(response => {
            this.$notify.error({
              title: '失败',
              message: response.data.errmsg
            })
          })
        }).catch(() => {
        });

      },
      confirmRefund() {
        this.$refs['refundForm'].validate((valid) => {
          if (valid) {
            refundOrder(this.refundForm).then(response => {
              this.refundDialogVisible = false
              this.$notify.success({
                title: '成功',
                message: '确认退款成功'
              })
              this.getList()
            }).catch(response => {
              this.$notify.error({
                title: '失败',
                message: response.data.errmsg
              })
            })
          }
        })
      },
      handleDownload() {
        this.downloadLoading = true
        import('@/vendor/Export2Excel').then(excel => {
          const tHeader = ['订单ID', '订单编号', '用户ID', '订单状态', '是否删除', '收货人', '收货联系电话', '收货地址']
          const filterVal = ['id', 'orderSn', 'userId', 'orderStatus', 'isDelete', 'consignee', 'mobile', 'address']
          excel.export_json_to_excel2(tHeader, this.list, filterVal, '订单信息')
          this.downloadLoading = false
        })
      }
    }
  }
</script>
