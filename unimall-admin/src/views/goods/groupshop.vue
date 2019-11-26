<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-input
        v-model="listQuery.spuName"
        clearable
        class="filter-item"
        style="width: 200px;"
        placeholder="请输入商品名称"
      />
      <el-input
        v-model="listQuery.id"
        clearable
        class="filter-item"
        style="width: 200px;"
        placeholder="请输入团购ID"
      />
      <el-select v-model="listQuery.status" clearable style="width: 200px" class="filter-item" placeholder="请选择团购状态" >
        <el-option v-for="(item,index) in GroupShopStatusMap" :key="index" :label="item.name" :value="item.value" />
      </el-select>
      <el-button v-permission="['operation:groupShop:query']" class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">查找</el-button>
      <el-button v-permission="['operation:groupShop:create']" class="filter-item" type="primary" icon="el-icon-edit" @click="openSpuCreateDialog">添加</el-button>
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
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" class="table-expand">
            <el-form-item label="商品单位">
              <span>{{ props.row.unit }}</span>
            </el-form-item>
            <el-form-item label="类目ID">
              <span>{{ props.row.categoryId }}</span>
            </el-form-item>
            <el-form-item label="商品sku属性">
              <el-table :data="props.row.groupShopSkuDTOList">
                <el-table-column align="center" prop="skuId" label="skuId"/>
                <el-table-column align="center" prop="title" label="skuTitle"/>
                <el-table-column align="center" prop="skuGroupShopPrice" label="团购价格(元)"/>
                <el-table-column align="center" prop="stock" label="库存"/>
                <el-table-column align="center" prop="originalPrice" label="原始价格(元)"/>
                <el-table-column align="center" prop="price" label="售卖价格(元)"/>
                <el-table-column align="center" prop="vipPrice" label="会员价格(元)"/>
                <el-table-column align="center" prop="freezeStock" label="冻结库存"/>
                <el-table-column align="center" prop="barCode" label="国际条码"/>
              </el-table>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>

      <el-table-column align="center" label="团购ID" prop="id" />

      <el-table-column align="center" label="商品(SPU)ID" prop="spuId" />

      <el-table-column align="center" min-width="100" label="名称" prop="title" />

      <el-table-column align="center" property="img" label="图片">
        <template slot-scope="scope">
          <img :src="scope.row.img" width="40" >
        </template>
      </el-table-column>

      <el-table-column align="center" label="到期人数未满,是否自动退款" prop="status">
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.automaticRefund == 1 ? 'success' : 'error' "
          >{{ scope.row.automaticRefund == 1 ? '退款' : '发货' }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="活动状态" prop="status">
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.status == 1 ? 'success' : 'error' "
          >{{ scope.row.status == 1 ? '进行' : '冻结' }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="团购开始时间" prop="gmtStart" width="85">
        <template slot-scope="scope">{{ scope.row.gmtStart | formatGmt }}</template>
      </el-table-column>

      <el-table-column align="center" label="团购结束时间" prop="gmtEnd" width="85">
        <template slot-scope="scope">{{ scope.row.gmtEnd | formatGmt }}</template>
      </el-table-column>

      <el-table-column align="center" label="当前人数" prop="alreadyBuyNumber" />

      <el-table-column align="center" label="目标人数" prop="minimumNumber" />

      <el-table-column align="center" label="操作" width="200" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-permission="['operation:groupShop:edit']" v-show="scope.row.status == 0" type="primary" size="mini" @click="openSpuUpdateDialog(scope.row)">编辑</el-button>
          <el-button v-permission="['operation:groupShop:delete']" v-show="scope.row.status == 0" type="danger" size="mini" @click="handleDelete(scope.row)">删除</el-button>
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

    <el-tooltip placement="top" content="返回顶部">
      <back-to-top :visibility-height="100" />
    </el-tooltip>

    <!-- 添加对话框 -->
    <el-dialog :title="dialogTitleMap[dialogStatus]" :visible.sync="dialogFormVisible" :fullscreen="true" :close-on-press-escape="false" center>
      <el-card class="box-card">
        <h3>商品属性(spu)</h3>
        <el-form
          ref="dataForm"
          :rules="rules"
          :model="dataForm"
          status-icon
          label-position="left"
          label-width="150px"
          style="width: 700px; margin-left:50px;"
        >
          <el-form-item label="团购商品">
            <el-cascader
              v-model="goodsOption"
              :options="goodsOptions"
              placeholder="团购商品"
              filterable
              @change="chooseGoods" />
          </el-form-item>

          <el-form-item label="团购时间">
            <el-col :span="11" >
              <el-date-picker
                v-model="dataForm.gmtStart"
                type="datetime"
                placeholder="选择日期"
                style="width: 100%;" />
            </el-col>
            <el-col :span="2" class="line">&nbsp;&nbsp;&nbsp;&nbsp; 至</el-col>
            <el-col :span="11">
              <el-date-picker
                v-model="dataForm.gmtEnd"
                type="datetime"
                placeholder="选择日期"
                style="width: 100%;" />
            </el-col>
          </el-form-item>

          <el-form-item label="团购基础人数" >
            <el-input-number v-model="dataForm.minimumNumber" :precision="0" controls-position="right" />
          </el-form-item>

          <el-form-item label="活动结束时人数没有达到基础人数处理">
            <el-radio-group v-model="dataForm.automaticRefund" >
              <el-radio-button :label="1">自动退款,我不发货</el-radio-button>
              <el-radio-button :label="0">不退款,我要发货</el-radio-button>
            </el-radio-group>
          </el-form-item>
        </el-form>
      </el-card>

      <el-card class="box-card">
        <h3>商品类型(sku)</h3>
        <!-- <el-button :plain="true" type="primary" @click="handleSkuShow">添加</el-button> -->
        <el-table :data="dataForm.skuList">
          <el-table-column property="id" label="SkuId"/>
          <el-table-column property="barCode" label="Sku条形码" />
          <el-table-column property="title" label="类型名" />
          <el-table-column property="stock" label="库存" />
          <el-table-column property="price" label="现价/元" />
          <el-table-column property="skuGroupShopPrice" label="团购价格/元" />
          <el-table-column align="center" label="操作" width="100" class-name="small-padding fixed-width" >
            <template slot-scope="scope">
              <el-button type="primary" size="mini" @click="openSkuGroupShopPriceUpdateDialog(scope.row)">更新</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>

      <!-- 更新团购价格的Dialog -->
      <el-dialog :visible.sync="groupShopUpdateSkuPriceDialog" :modal="true" :append-to-body="true" :close-on-click-modal = "false" top="10vh" width="70%" title="更新团购价格">
        <el-form
          ref="specForm"
          :model="skuForm"
          status-icon
          label-position="left"
          label-width="100px"
          style="width: 400px; margin-left:50px;"
        >
          <el-form-item label="团购价格" >
            <el-input-number v-model="skuForm.skuGroupShopPrice" :precision="2" controls-position="right" />元
          </el-form-item>
          <el-form-item label="现价">
            {{ skuForm.price }}元
          </el-form-item>
          <el-form-item label="sku类型名">
            {{ skuForm.title }}元
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="groupShopUpdateSkuPriceDialog = false">取消</el-button>
          <el-button type="primary" @click="skuGroupShopUpdate()">确定</el-button>
        </div>
      </el-dialog>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button v-if="dialogStatus === 'create'" type="primary" @click="createOrUpdateData">创建</el-button>
        <el-button v-if="dialogStatus === 'update'" type="primary" @click="createOrUpdateData">更新</el-button>
      </div>

    </el-dialog>
  </div>
</template>

<style>
.table-expand {
  font-size: 0;
}

.table-expand label {
  width: 100px;
  color: #99a9bf;
}

.table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
}

.gallery {
  width: 80px;
  margin-right: 10px;
}
</style>

<script>
import { detailGoods } from '@/api/goods'
import { queryGroupShop, createGroupShop, deleteGroupShop, editGroupShop } from '@/api/group_shop'
import BackToTop from '@/components/BackToTop'
import { spuTree } from '@/api/goods'
import { formatDateAndTime } from '@/filters'
import { clearTreeEmptyChildren } from '@/utils/index'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination

export default {
  name: 'GoodsList',
  components: { BackToTop, Pagination },
  filters: {
    formatGmt(time) {
      if (time == null || time === '') {
        return '无'
      }
      return formatDateAndTime(time)
    }
  },
  data() {
    return {
      list: [],
      total: 0,
      listLoading: true,
      dialogFormVisible: false,
      GroupShopStatusMap: [{ value: 0, name: '冻结' }, { value: 1, name: '活动' }],
      listQuery: {
        page: 1,
        limit: 20,
        spuName: undefined,
        status: undefined,
        id: undefined
      },
      dialogTitleMap: { create: '创建团购商品', update: '修改团购商品' },
      dialogStatus: 'create',
      rules: {},
      dataForm: {
        spuId: undefined,
        gmtStart: undefined,
        gmtEnd: undefined,
        minimumNumber: 1,
        automaticRefund: 1,
        groupShopSkus: [],
        skuList: []
      },
      goodsOptions: [],
      goodsOption: undefined,
      groupShopUpdateSkuPriceDialog: false,
      skuForm: {}
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      queryGroupShop(this.listQuery)
        .then(response => {
          response.data.data.items.forEach(temp => {
            temp.groupShopSkuDTOList.forEach(item => {
              item.price = item.price / 100
              item.originalPrice = item.originalPrice / 100
              item.vipPrice = item.vipPrice / 100
              item.skuGroupShopPrice = item.skuGroupShopPrice / 100
            })
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
      this.listQuery.page = 1
      this.getList()
    },
    refreshDataForm() {
      this.dataForm = {
        spuId: undefined,
        gmtStart: undefined,
        gmtEnd: undefined,
        minimumNumber: 1,
        automaticRefund: 1,
        skuList: [],
        groupShopSkus: []
      }
    },
    openSpuCreateDialog() {
      this.refreshDataForm()
      this.refreshGoodsOptions()
      this.dialogStatus = 'create'
      this.goodsOption = undefined
      this.dialogFormVisible = true
    },
    openSpuUpdateDialog(data) {
      this.refreshDataForm()
      this.refreshGoodsOptions()
      this.dialogStatus = 'update'
      this.goodsOption = 'G_' + data.spuId
      this.dataForm = Object.assign({}, {
        id: data.id,
        spuId: data.spuId,
        automaticRefund: data.automaticRefund,
        gmtStart: new Date(data.gmtStart),
        gmtEnd: new Date(data.gmtEnd),
        minimumNumber: data.minimumNumber,
        skuList: data.groupShopSkuDTOList,
        groupShopSkus: []
      })
      this.dialogFormVisible = true
    },
    handleDelete(row) {
      this.$confirm('此操作将永久删除商品--' + row.title + '--, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteGroupShop(row.id)
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
    refreshGoodsOptions() {
      if (this.goodsOptions.length === 0) {
        spuTree().then(response => {
          this.goodsOptions = clearTreeEmptyChildren(response.data.data)
        })
      }
    },
    chooseGoods(e) {
      // 1. 验证是否是商品
      if (e !== undefined) {
        const tag = e[e.length - 1]
        if (this.goodsOption === undefined || this.goodsOption === null || this.goodsOption.length <= 3) {
          this.$notify.error({
            title: '提示',
            message: '请选择商品'
          })
          return false
        }

        // 2. 向后台根据商品ID获取商品信息
        this.dataForm.spuId = tag.substring(2)
        detailGoods(this.dataForm.spuId).then(response => {
          response.data.data.skuList.forEach(item => {
            item.price = item.price / 100
            item.originalPrice = item.originalPrice / 100
            item.vipPrice = item.vipPrice / 100
            item.skuGroupShopPrice = item.price
          })
          this.dataForm.skuList = response.data.data.skuList
          console.log(this.dataForm.skuList)
        }).catch(response => {
          this.$notify.error({
            title: '失败',
            message: response.data.errmsg + ',请重新选择'
          })
        })
      }
    },
    createOrUpdateData() {
      if (this.dataForm.spuId === null || this.dataForm.spuId === undefined || this.dataForm.skuList === null || this.dataForm.skuList === undefined || this.dataForm.skuList.length === 0) {
        this.$notify.error({
          title: '提示',
          message: '请选择商品'
        })
        return false
      }

      if (this.dataForm.gmtStart === null || this.dataForm.gmtStart === undefined || this.dataForm.gmtEnd === null || this.dataForm.gmtEnd === undefined || this.dataForm.gmtStart >= this.dataForm.gmtEnd) {
        this.$notify.error({
          title: '提示',
          message: '请选择活动时间,且开始时间小于结束时间'
        })
        return false
      }
      // this.dataForm.gmtStart = this.dataForm.gmtStart.getTime()
      // this.dataForm.gmtEnd = this.dataForm.gmtEnd.getTime()

      // 提取skuList中的属性并改名
      for (var i = 0; i < this.dataForm.skuList.length; i++) {
        var temp = {}
        temp.skuId = this.dataForm.skuList[i].id
        temp.skuGroupShopPrice = this.dataForm.skuList[i].skuGroupShopPrice * 100
        this.dataForm.groupShopSkus.push(temp)
      }
      this.dataForm.groupShopSkuList = JSON.stringify(this.dataForm.groupShopSkus)
      const requestForm = {
        id: this.dataForm.id,
        gmtStart: this.dataForm.gmtStart.getTime(),
        gmtEnd: this.dataForm.gmtEnd.getTime(),
        groupShopSkuList: this.dataForm.groupShopSkuList,
        minimumNumber: this.dataForm.minimumNumber,
        spuId: this.dataForm.spuId,
        automaticRefund: this.dataForm.automaticRefund
      }
      if (this.dialogStatus === 'create') {
        createGroupShop(requestForm)
          .then(response => {
            this.$notify.success({
              title: '成功',
              message: '团购商品录入成功!'
            })
            this.getList()
          })
          .catch(response => {
            this.$notify.error({
              title: '提示',
              message: response.data.errmsg
            })
          })
      } else if (this.dialogStatus === 'update') {
        editGroupShop(requestForm)
          .then(response => {
            this.$notify.success({
              title: '成功',
              message: '团购商品更新成功!'
            })
            this.getList()
          })
          .catch(response => {
            this.$notify.error({
              title: '提示',
              message: response.data.errmsg
            })
          })
      }

      this.dialogFormVisible = false
    },
    openSkuGroupShopPriceUpdateDialog(data) {
      this.skuForm = undefined
      this.groupShopUpdateSkuPriceDialog = true
      this.skuForm = data
    },
    skuGroupShopUpdate(data) {
      this.groupShopUpdateSkuPriceDialog = false
    }
  }
}
</script>
