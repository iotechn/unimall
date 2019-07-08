<template>
  <div class="app-container">

    <!-- 查询和其他操作 -->
    <!--<div class="filter-container">-->
    <!--<el-input v-model="listQuery.id" clearable class="filter-item" style="width: 200px;" placeholder="请输入类目ID"/>-->
    <!--<el-input v-model="listQuery.title" clearable class="filter-item" style="width: 200px;" placeholder="请输入类目名称"/>-->
    <!--<el-button v-permission="['admin:category:list']" class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">查找</el-button>-->
    <!--<el-button v-permission="['admin:category:create']" class="filter-item" type="primary" icon="el-icon-edit" @click="handleCreate">添加</el-button>-->
    <!--</div>-->

    <!-- 查询结果 -->
    <el-table v-loading="listLoading" :data="list" size="small" element-loading-text="正在查询中。。。" border fit
              highlight-current-row>

      <el-table-column v-for="(col,index) in head" :key="index" align="center" :label="col" :prop="col"/>

      <el-table-column align="center" label="操作" width="200" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-permission="['admin:nearbyform:complete']" type="primary" size="mini"
                     @click="handleComplete(scope.row)">{{scope.row.completed ? '已成交' : '标记成交'}}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit"
                @pagination="getList"/>


  </div>
</template>

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

<script>
  import {formlist, formcomplete} from '@/api/nearby'
  import {getToken} from '@/utils/auth'
  import Pagination from '@/components/Pagination' // Secondary package based on el-pagination

  export default {
    name: 'Category',
    components: {Pagination},
    data() {
      return {
        head: [],
        list: undefined,
        total: 0,
        listLoading: true,
        listQuery: {
          page: 1,
          limit: 20,
          id: undefined,
          title: undefined
        },
        dialogFormVisible: false,
        dialogStatus: '',
        textMap: {
          update: '编辑',
          create: '创建'
        },
        downloadLoading: false
      }
    },
    computed: {
      headers() {
        return {
          'accessToken': getToken()
        }
      }
    },
    created() {
      this.getList()
    },
    methods: {
      getList() {
        this.listLoading = true
        formlist(this.listQuery)
          .then(response => {
            let listtemp = response.data.data.items
            if (listtemp.length > 0) {

              //若存在则渲染表头
              let h = []
              for (var name in listtemp[0].body) {
                h.push(name)
              }
              this.head = h
            }
            this.total = response.data.data.total
            this.listLoading = false
            let newlist = []
            for (var i = 0; i < listtemp.length; i++) {
              listtemp[i].body.completed = listtemp[i].completed
              newlist.push(listtemp[i].body)
            }
            this.list = newlist
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
      handleComplete(row) {
        if (!row.completed) {
          this.$confirm('您确定要标记这个预申请已经成交吗？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            formcomplete(row._id).then(response => {
              this.$notify.success({
                title: '成功',
                message: '标记成功完成！'
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
        }

      }
    }
  }
</script>
