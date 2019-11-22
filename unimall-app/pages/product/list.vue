<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-input
        v-model="listQuery.title"
        clearable
        class="filter-item"
        style="width: 200px;"
        placeholder="请输入商品名称"
      />
      <el-button v-permission="['operation:goods:list']" class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">查找</el-button>
      <el-button v-permission="['operation:goods:create']" class="filter-item" type="primary" icon="el-icon-edit" @click="handleCreate">添加</el-button>
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
          </el-form>
        </template>
      </el-table-column>

      <el-table-column align="center" label="商品编号" prop="id" />

      <el-table-column align="center" min-width="100" label="名称" prop="title" />

      <el-table-column align="center" property="img" label="图片">
        <template slot-scope="scope">
          <img :src="scope.row.img" width="40" >
        </template>
      </el-table-column>

      <el-table-column align="center" label="详情" prop="detail">
        <template slot-scope="scope">
          <el-dialog :visible.sync="detailDialogVisible" title="商品详情">
            <div v-html="goodsDetail" />
          </el-dialog>
          <el-button type="primary" size="mini" @click="showDetail(scope.row)">查看</el-button>
        </template>
      </el-table-column>

      <el-table-column align="center" label="原价" prop="originalPrice" />

      <el-table-column align="center" label="现价" prop="price" />

      <el-table-column align="center" label="VIP价格" prop="vipPrice" />

      <el-table-column align="center" label="是否在售" prop="status">
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.status == 1 ? 'success' : 'error' "
          >{{ scope.row.status == 1 ? '在售' : '未售' }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column
        align="center"
        max-width="300"
        min-width="300"
        label="描述"
        prop="description"
      />

      <el-table-column align="center" label="操作" width="200" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-permission="['operation:goods:edit']" type="primary" size="mini" @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button v-permission="['operation:goods:delete']" type="danger" size="mini" @click="handleDelete(scope.row)">删除</el-button>
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
import { listGoods, deleteGoods, detailGoods } from '@/api/goods'
import BackToTop from '@/components/BackToTop'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination

export default {
  name: 'GoodsList',
  components: { BackToTop, Pagination },
  data() {
    return {
      list: [],
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        title: undefined,
        sort: 'id',
        order: 'desc'
      },
      goodsDetail: '',
      detailDialogVisible: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      listGoods(this.listQuery)
        .then(response => {
          response.data.data.items.forEach(item => {
            item.price = item.price / 100
            item.originalPrice = item.originalPrice / 100
            item.vipPrice = item.vipPrice / 100
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
    handleCreate() {
      this.$router.push({ path: '/goods/upsert' })
    },
    handleUpdate(row) {
      this.$router.push({ path: '/goods/upsert', query: { id: row.id }})
    },
    showDetail(row) {
      if (!this.detailDialogVisible) {
        detailGoods(row.id)
          .then(response => {
            this.goodsDetail = response.data.data.detail.replace(/<img/g, "<img style='max-width:100%;height:auto;line-height:0px'")
            this.detailDialogVisible = true
          })
      }
    },
    handleDelete(row) {
      this.$confirm('此操作将永久删除商品--' + row.title + '--, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteGoods(row.id)
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
    }
  }
}
</script>
