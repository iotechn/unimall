<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-input
        v-model="listQuery.id"
        clearable
        class="filter-item"
        style="width: 200px;"
        placeholder="请输入用户Id"
      />
      <el-input
        v-model="listQuery.name"
        clearable
        class="filter-item"
        style="width: 200px;"
        placeholder="请输入昵称"
      />
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">查找</el-button>
      <!--<el-button class="filter-item" type="primary" icon="el-icon-edit" @click="handleCreate">添加</el-button>-->
      <!--<el-button :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload">导出</el-button>-->
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
      <el-table-column align="center" width="100px" label="用户ID" prop="id" sortable />

      <el-table-column align="center" label="昵称" prop="nickName" />

      <el-table-column align="center" label="手机号码" prop="mobile" />

      <el-table-column align="center" label="性别" prop="gender">
        <template slot-scope="scope">
          <el-tag>{{ genderDic[scope.row.gender]?genderDic[scope.row.gender]:'未知' }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="积分" prop="integral" />

      <el-table-column align="center" label="省份" prop="province" />
      <el-table-column align="center" label="城市" prop="city" />

      <el-table-column align="center" label="状态" prop="status">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.status === 1? '正常使用': '被封禁' }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column
        align="center"
        label="操作"
        min-width="200"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            v-permission="['admin:user:status']"
            type="primary"
            size="mini"
            @click="handleStatus(scope.row)"
          >{{ scope.row.status === 0? '激活' : '冻结' }}</el-button>
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
  </div>
</template>

<script>
import { fetchList, activeUser } from '@/api/user'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination

export default {
  name: 'User',
  components: { Pagination },
  data() {
    return {
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        username: undefined,
        id: undefined
      },
      downloadLoading: false,
      genderDic: ['女', '男']
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchList(this.listQuery)
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
    handleStatus(row) {
      activeUser(row.id, row.status === 1 ? 0 : 1).then(res => {
        this.$notify.success({
          title: '成功',
          message: '状态更改成功'
        })
        this.getList()
      })
    }
  }
}
</script>
