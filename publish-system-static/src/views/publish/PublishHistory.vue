<template>
    <section>
        <!--工具条-->
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-form :inline="true" :model="filters">
                <el-form-item>
                    <el-input v-model="filters.keyword" placeholder="关键字"></el-input>
                </el-form-item>
            </el-form>
        </el-col>

        <!--列表-->
        <template>
            <el-table :data="publishHistoryList" highlight-current-row v-loading="table.loading" style="width: 100%;">
                <el-table-column type="index" width="60">
                </el-table-column>
                <el-table-column prop="projectName" label="项目名称" sortable>
                </el-table-column>
                <el-table-column prop="environmentName" label="环境名称" sortable>
                </el-table-column>
                <el-table-column prop="branch" label="代码版本" sortable>
                </el-table-column>
                <el-table-column prop="createUser" label="发布人" sortable>
                </el-table-column>
                <el-table-column label="发布结果" sortable>
                    <template scope="scope">
                        <el-tag v-if="scope.row.status === 0" type="primary">进行中</el-tag>
                        <el-tag v-else-if="scope.row.status === 1" type="success">成功</el-tag>
                        <el-tag v-else-if="scope.row.status === -1" type="danger">失败</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="remark" label="发布备注" sortable>
                </el-table-column>
                <el-table-column prop="createTime" label="发布时间" min-width="100" sortable>
                </el-table-column>
            </el-table>

            <!--工具条-->
            <el-col :span="24" class="toolbar" style="padding-bottom:10px;">
                <!--分页-->
                <el-pagination
                        @current-change="handleCurrentChange"
                        @size-change="handleSizeChange"
                        :current-page="table.page" :page-sizes="[10, 25, 50]" :page-size="table.size"
                        layout="total, sizes, prev, pager, next, jumper"
                        :total="publishHistoryList.length" style="float:right">
                </el-pagination>
            </el-col>
        </template>

    </section>
</template>
<script>
    import {getPublishHistory} from '../../api/api';

    export default {
        data() {
            return {
                filters: {
                    keyword: ''
                },
                table: {
                    loading: false,
                    page: 1,
                    size: 10,
                    data: []
                }
                // publishHistory: {
                //     id: '',
                //     publishConfId: '',
                //     projectId: '',
                //     projectName: '',
                //     environmentId: '',
                //     environmentName: '',
                //     branch: '',
                //     remark: '',
                //     status: '',
                //     costTime: '',
                //     createUser: '',
                //     createTime: '',
                //     updateUser: '',
                //     updateTime: ''
                // }
            }
        },
        computed:{
            publishHistoryList:function () {
                var self = this, data = self.table.data, page = self.table.page, size = self.table.size, keyword = self.filters.keyword;
                var v = data.filter(function (p) {
                    return (p.projectName && p.projectName.indexOf(keyword) !== -1)
                        || (p.environmentName && p.environmentName.indexOf(keyword) !== -1)
                        || (p.branch && p.branch.indexOf(keyword) !== -1)
                        || (p.createUser && p.createUser.indexOf(keyword) !== -1)
                        || (p.remark && p.remark.indexOf(keyword) !== -1)
                        || (p.createTime && p.createTime.indexOf(keyword) !== -1)
                });
                v = v.slice((page - 1) * size, page * size);
                return v;
            }
        },
        methods: {
            getPublishHistory() {
                this.table.loading = true;
                getPublishHistory().then((data) => {
                    this.table.data = data;
                    this.table.loading = false;
                })
            },
            handleCurrentChange(val) {
                this.table.page = val;
            },
            handleSizeChange(val) {
                this.table.size = val;
            },
        },
        mounted() {
            this.getPublishHistory();
        }
    };

</script>

<style scoped>

</style>