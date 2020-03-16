<template>
    <div class="app-container">
        <!-- 查询和其他操作 -->
        <div class="filter-container">
            <#list columnDefinitionList! as column>
            <#if column.query>
            <el-input
                    v-model="listQuery.${column.alias}"
                    clearable
                    class="filter-item"
                    style="width: 200px;"
                    placeholder="请输入${column.chinese}"
            />
            </#if>
            </#list>
            <el-button v-permission="['${folder}:${serviceLowCaseName}:list']" class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">查找</el-button>
            <el-button v-permission="['${folder}:${serviceLowCaseName}:create']" class="filter-item" type="primary" icon="el-icon-edit" @click="handleCreate">添加</el-button>
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
            <#list columnDefinitionList! as column>
            <#if column.showInList>
            <#if column.money>
            <el-table-column align="center" label="${column.chinese}" prop="${column.alias}" >
                <template slot-scope="scope">{{ scope.row.${column.alias} / 100 }}</template>
            </el-table-column>
            <#elseif column.clazz?starts_with("java.util.Date")>
            <el-table-column align="center" label="${column.chinese}" prop="${column.alias}" >
                <template slot-scope="scope">{{ scope.row.${column.alias} | formatTime }}</template>
            </el-table-column>
            <#else>
            <el-table-column align="center" label="${column.chinese}" prop="${column.alias}" />
            </#if>
            </#if>
            </#list>
            <el-table-column align="center" label="操作" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                    <el-button v-permission="['${folder}:${serviceLowCaseName}:edit']" type="primary" size="mini" @click="handleUpdate(scope.row)">编辑</el-button>
                    <el-button v-permission="['${folder}:${serviceLowCaseName}:delete']" type="danger" size="mini" @click="handleDelete(scope.row)">删除</el-button>
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
                <el-form-item label="id" prop="id" hidden>
                    <el-input v-model="dataForm.id" />
                </el-form-item>
                <#list columnDefinitionList! as column>
                <#if column.insertColumn>
                <el-form-item label="${column.chinese}" prop="${column.alias}">
                    <el-input v-model="dataForm.${column.alias}" />
                </el-form-item>
                </#if>
                </#list>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取消</el-button>
                <el-button v-if="dialogStatus=='create'" :loading="submiting" type="primary" @click="createData">确定</el-button>
                <el-button v-else type="primary" :loading="submiting" @click="updateData">确定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import {
        list${doName},
        create${doName},
        update${doName},
        delete${doName}
    } from '@/api/${pageName}'
    import Pagination from '@/components/Pagination'
    export default {
        name: '${doName}',
        components: { Pagination },
        data() {
            return {
                list: null,
                total: 0,
                listLoading: true,
                listQuery: {
                    page: 1,
                    limit: 20
                },
                dataForm: {
                    id: undefined
                },
                dialogFormVisible: false,
                submiting: false,
                dialogStatus: '',
                textMap: {
                    update: '编辑',
                    create: '创建'
                },
                rules: {
                    <#list columnDefinitionList! as column>
                    <#if column.insertColumn && column.notnull>
                    ${column.alias}: [{ required: true, message: '${column.chinese}名称不能为空', trigger: 'blur' }],
                    </#if>
                    </#list>
                }
            }
        },
        created() {
            this.getList()
        },
        methods: {
            getList() {
                this.listLoading = true
                list${doName}(this.listQuery).then(response => {
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
            resetForm() {
                this.dataForm = {
                    id: undefined
                }
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
                        this.submiting = true
                        create${doName}(this.dataForm).then(response => {
                            this.list.unshift(response.data.data)
                            this.dialogFormVisible = false
                            this.$notify.success({
                                title: '成功',
                                message: '添加成功'
                            })
                            this.submiting = false
                        })
                        .catch(response => {
                            this.$notify.error({
                                title: '失败',
                                message: response.data.errmsg
                            })
                            this.submiting = false
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
                        this.submiting = true
                        update${doName}(this.dataForm).then(() => {
                            for (const v of this.list) {
                                if (v.id === this.dataForm.id) {
                                    const index = this.list.indexOf(v)
                                    this.list.splice(index, 1, this.dataForm)
                                    break
                                }
                            }
                            this.dialogFormVisible = false
                            this.submiting = false
                            this.$notify.success({
                                title: '成功',
                                message: '更新成功'
                            })
                        })
                        .catch(response => {
                            this.$notify.error({
                                title: '失败',
                                message: response.data.errmsg
                            })
                            this.submiting = false
                        })
                    }
                })
            },
            handleDelete(row) {
                this.$confirm('此操作将永久删除该记录---' + row.id + '---, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    delete${doName}(row.id).then(response => {
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
