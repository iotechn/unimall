<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-select
        v-model="listQuery.status"
        style="width: 200px"
        class="filter-item"
        placeholder="请选择用户状态"
      >
        <el-option v-for="(key,index) in statusDic" :key="index" :label="key.name" :value="key.value" />
      </el-select>
      <el-select
        v-model="listQuery.level"
        style="width: 200px"
        class="filter-item"
        placeholder="请选择用户会员等级"
      >
        <el-option v-for="(key,index) in levelDic" :key="index" :label="key.name" :value="key.value" />
      </el-select>
      <el-select
        v-model="listQuery.gender"
        style="width: 200px"
        class="filter-item"
        placeholder="请选择用户性别"
      >
        <el-option v-for="(key,index) in genderDic" :key="index" :label="key.name" :value="key.value" />
      </el-select>
      <el-input
        v-model="listQuery.id"
        clearable
        class="filter-item"
        style="width: 200px;"
        placeholder="请输入用户Id"
      />
      <el-input
        v-model="listQuery.nickname"
        clearable
        class="filter-item"
        style="width: 200px;"
        placeholder="请输入昵称"
      />
      <el-button
        v-permission="['system:user:query']"
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="handleFilter"
      >查找</el-button>
      <!--添加用户-->
      <!-- <el-button
        v-permission="['system:user:create']"
        class="filter-item"
        type="primary"
        icon="el-icon-edit"
        @click="handleCreate"
      >添加用户</el-button> -->
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

      <el-table-column align="center" label="昵称" prop="nickname" />

      <el-table-column align="center" label="手机号码" prop="phone" />

      <el-table-column align="center" label="性别" prop="gender">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.gender | genderDicFilter }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="会员等级" prop="level">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.level | levelDicFilter }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="生日" prop="birthday">
        <template slot-scope="scope">{{ scope.row.birthday | formatDate }}</template>
      </el-table-column>

      <el-table-column align="center" label="状态" prop="status">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.status === 0? '冻结': '激活' }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="上次登录时间" prop="gmtLastLogin">
        <template slot-scope="scope">{{ scope.row.gmtLastLogin | formatTime }}</template>
      </el-table-column>

      <el-table-column align="center" label="上次登录IP" prop="lastLoginIp" />

      <el-table-column align="center" label="操作" width="300" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-permission="['system:user:update']"
            type="primary"
            size="mini"
            @click="handleStatus(scope.row)"
          >{{ scope.row.status === 0? '激活' : '冻结' }}</el-button>
          <el-button
            v-permission="['system:user:update']"
            type="primary"
            size="mini"
            @click="handleUpdate(scope.row)"
          >编辑</el-button>
          <el-button
            v-permission="['system:user:delete']"
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
        <el-form-item label="隐藏的用户id" prop="id" hidden>
          <el-input v-model="dataForm.id" />
        </el-form-item>
        <el-form-item label="用户昵称" prop="nickname">
          <el-input v-model="dataForm.nickname" />
        </el-form-item>
        <el-form-item label="用户手机号" prop="phone">
          <el-input v-model="dataForm.phone" />
        </el-form-item>
        <el-form-item label="用户密码" prop="password">
          <el-input v-model="dataForm.password" />
        </el-form-item>
        <el-form-item label="用户性别" prop="gender">
          <el-select v-model="dataForm.gender" placeholder="请选择">
            <el-option v-for="(key, index) in genderDic" :key="index" :label="key.name" :value="key.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="用户等级" prop="level">
          <el-select v-model="dataForm.level" placeholder="请选择">
            <el-option v-for="(key, index) in levelDic" :key="index" :label="key.name" :value="key.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="用户状态" prop="status">
          <el-select v-model="dataForm.status" placeholder="请选择">
            <el-option v-for="(key, index) in statusDic" :key="index" :label="key.name" :value="key.value" />
          </el-select>
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

<script>
import { listUser, activeUser, createUser, updateUser, deleteUser } from '@/api/user'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination

const genderDic = [{ value: 1, name: '女' }, { value: 2, name: '男' }, { value: '', name: '全部' }]
const levelDic = [{ value: 0, name: '普通会员' }, { value: 1, name: 'VIP会员' }, { value: '', name: '全部' }]
const statusDic = [{ value: 0, name: '冻结' }, { value: 1, name: '激活' }, { value: '', name: '全部' }]
export default {
  name: 'User',
  components: { Pagination },
  filters: {
    genderDicFilter(code) {
      if (code === 1 || code === 2) {
        return genderDic[code - 1].name
      } else {
        return '未知性别'
      }
    },
    levelDicFilter(code) {
      if (code === 0 || code === 1) {
        return levelDic[code].name
      } else {
        return '未知等级'
      }
    }
  },
  data() {
    return {
      list: [],
      total: 0,
      listLoading: true,
      listQuery: {
        pageNo: 1,
        limit: 20,
        nickname: undefined,
        id: undefined,
        status: undefined,
        level: undefined,
        gender: undefined
      },
      dataForm: {
        id: undefined,
        level: undefined,
        phone: undefined,
        gender: undefined,
        nickname: undefined,
        birthday: undefined,
        status: undefined,
        password: undefined
      },
      downloadLoading: false,
      genderDic,
      levelDic,
      statusDic,
      textMap: { update: '编辑', create: '创建' },
      dialogFormVisible: false,
      dialogStatus: '',
      rules: {
        nickname: [{ required: true, message: '用户昵称不能为空', trigger: 'blur' }],
        phone: [{ required: true, message: '用户手机不能为空', trigger: 'blur' }, { pattern: /^1[3456789]\d{9}$/, message: '请输入正确电话' }],
        gender: [{ required: true, message: '请选择用户性别类型', trigger: 'blur' }],
        level: [{ required: true, message: '请选择用户会员等级状态', trigger: 'blur' }],
        status: [{ required: true, message: '请选择用户状态', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }, { pattern: /^[0-9a-zA-Z$./]{8}/, message: '至少八个数字和字母' }]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      listUser(this.listQuery)
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
    resetForm() {
      this.dataForm = {
        id: undefined,
        level: undefined,
        phone: undefined,
        gender: undefined,
        nickname: undefined,
        birthday: undefined,
        status: undefined
      }
    },
    handleFilter() {
      this.listQuery.pageNo = 1
      this.getList()
    },
    handleStatus(row) {
      this.resetForm()
      this.dataForm.id = row.id
      this.dataForm.status = row.status === 1 ? 0 : 1
      activeUser(this.dataForm)
        .then(res => {
          this.$notify.success({
            title: '成功',
            message: '状态更改成功'
          })
          this.getList()
        })
        .catch(() => {
          this.list = []
          this.total = 0
          this.listLoading = false
        })
    },
    handleCreate() {
      this.resetForm()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          createUser(this.dataForm)
            .then(response => {
              this.list.unshift(response.data.data)
              this.dialogFormVisible = false
              this.$notify.success({
                title: '成功',
                message: '创建成功'
              })
              this.listQuery.nickname = this.dataForm.nickname
              this.getList()
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
    handleUpdate(row) {
      this.dataForm = Object.assign({}, row)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          updateUser(this.dataForm)
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
                message: '更新用户成功'
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
      this.$confirm('此操作将永久删除该用户---' + row.nickname + '---, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteUser(row.id, row.nickname)
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
