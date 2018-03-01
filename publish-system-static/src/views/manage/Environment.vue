<template>
    <section>
        <!--工具条-->
        <el-row :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-col :span="12">
                <el-form :inline="true" :model="filters" class="grid-content bg-purple">
                    <el-form-item>
                        <el-input v-model="filters.keyword" placeholder="关键字"></el-input>
                    </el-form-item>
                </el-form>
            </el-col>
            <el-col :span="1" :offset="11">
                <el-form class="grid-content bg-purple-light">
                    <el-form-item>
                        <el-button type="primary" @click="handleAdd">新增</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>

        <!--列表-->
        <el-table :data="environmentList" highlight-current-row v-loading="table.loading" element-loading-text="拼命加载中" @selection-change="selsChange" style="width: 100%;">
            <el-table-column type="selection" width="55">
            </el-table-column>
            <el-table-column type="index" width="60">
            </el-table-column>
            <el-table-column prop="uniqueName" label="唯一标识" min-width="100" sortable>
            </el-table-column>
            <el-table-column prop="ip" label="ip" min-width="100" sortable>
            </el-table-column>
            <el-table-column prop="userName" label="用户名" sortable>
            </el-table-column>
            <el-table-column prop="status" label="状态" sortable>
            </el-table-column>
            <el-table-column prop="remark" label="说明" min-width="100" sortable>
            </el-table-column>
            <el-table-column prop="updateUser" label="修改人" width="180" sortable>
            </el-table-column>
            <el-table-column prop="updateTime" label="修改时间" width="180" sortable>
            </el-table-column>
            <el-table-column label="操作" min-width="150">
                <template scope="scope">
                    <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                    <el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
                </template>
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
                    :total="environmentList.length" style="float:right">
            </el-pagination>
        </el-col>

        <!--新增、编辑界面-->
        <el-dialog :title="form.title" v-model="form.visible" :close-on-click-modal="false">
            <el-form :model="form.data " label-width="100px" :rules="form.rules" ref="form">
                <el-form-item label="唯一标识" prop="uniqueName">
                    <el-input v-model="form.data.uniqueName" auto-complete="off" placeholder="发布中环境的唯一标识"></el-input>
                </el-form-item>
                <el-form-item label="ip" prop="ip">
                    <el-input v-model="form.data.ip" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="用户名" prop="userName">
                    <el-input v-model="form.data.userName" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input type=password v-model="form.data.password" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="状态">
                    <el-input v-model="form.data.status" auto-complete="off" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="说明">
                    <el-input type="textarea" v-model="form.data.remark"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="form.visible = false">取 消</el-button>
                <!--<el-button type="danger" @click="handleDel(form.data)" v-show="form.data.id != ''">删除</el-button>-->
                <el-button type="primary" @click.native="submit" :loading="form.loading">
                    <div>{{form.btnText}}</div>
                </el-button>
            </div>
        </el-dialog>

    </section>
</template>

<script>
    import {getEnvironmentList, addEnvironment, editEnvironment, removeEnvironment} from '../../api/api'

    export default {
        data() {
            return {
                filters: {
                    keyword: ''
                },
                sels: [],//列表选中列
                userName: '',
                table: {
                    loading: false,
                    page: 1,
                    size: 10,
                    data: []
                },
                form: {
                    title: '',
                    visible: false,//界面是否显示
                    loading: false,
                    btnText: '提交',
                    data: {
                        id: '',
                        uniqueName: '',
                        ip: '',
                        userName: '',
                        password: '',
                        status: '',
                        remark: '',
                        createTime: '',
                        createUser: '',
                        updateTime: '',
                        updateUser: ''
                    },
                    rules: {
                        uniqueName: [
                            {required: true, message: '请输入名称', trigger: 'blur'}
                        ],
                        ip: [
                            {required: true, message: '请输入ip地址', trigger: 'blur'}
                        ],
                        userName: [
                            {required: true, message: '请输入用户名', trigger: 'blur'}
                        ],
                        password: [
                            {required: true, message: '请输入密码', trigger: 'blur'}
                        ],
                    }
                }
            };
        },
        computed: {
            environmentList: function () {
                var self = this, data = self.table.data, page = self.table.page, size = self.table.size, keyword = self.filters.keyword;
                var v = data.filter(function (p) {
                    return (p.uniqueName && p.uniqueName.indexOf(keyword) !== -1)
                        || (p.ip && p.ip.indexOf(keyword) !== -1)
                        || (p.userName && p.userName.indexOf(keyword) !== -1)
                        || (p.updateUser && p.updateUser.indexOf(keyword) !== -1)
                });
                v = v.slice((page - 1) * size, page * size);
                return v;
            }
        },
        methods: {
            getEnvironment() {
                this.table.loading = true;
                getEnvironmentList().then((data) => {
                    this.table.data = data;
                    this.table.loading = false;
                })
            },
            selsChange: function (sels) {
                this.sels = sels;
            },
            handleCurrentChange(val) {
                this.table.page = val;
            },
            handleSizeChange(val) {
                this.table.size = val;
            },
            handleAdd: function () {
                this.form.visible = true;
                this.form.title = '新增';
                this.form.data.id = '';
                this.form.data.uniqueName = '';
                this.form.data.ip = '';
                this.form.data.userName = '';
                this.form.data.password = '';
                this.form.data.status = '';
                this.form.data.remark = '';
                this.form.data.createTime = '';
                this.form.data.createUser = this.userName;
                this.form.data.updateTime = '';
                this.form.data.updateUser = this.userName;
            },
            handleEdit: function (index, row) {
                this.form.visible = true;
                this.form.title = '编辑';
                this.form.data.id = row.id;
                this.form.data.uniqueName = row.uniqueName;
                this.form.data.ip = row.ip;
                this.form.data.userName = row.userName;
                this.form.data.password = row.password;
                this.form.data.status = row.status;
                this.form.data.remark = row.remark;
                this.form.data.createTime = row.createTime;
                this.form.data.createUser = row.createUser;
                this.form.data.updateTime = row.updateTime;
                this.form.data.updateUser = this.userName;
            },
            submit: function () {
                this.$refs.form.validate((valid) => {
                    if (valid) {
                        this.$confirm('确认提交吗?', '提示', {}).then(() => {
                            this.form.loading = true;
                            this.form.btnText = '提交中';
                            var formData = this.form.data;
                            let param = Object.assign({}, formData);
                            if (formData.id) {
                                editEnvironment(param).then((data) => {
                                    let {message, type} = data;
                                    this.$message({
                                        showClose: true,
                                        message: message,
                                        type: type
                                    });
                                    this.getEnvironment();
                                });
                            } else {
                                addEnvironment(param).then((data) => {
                                    let {message, type} = data;
                                    this.$message({
                                        showClose: true,
                                        message: message,
                                        type: type
                                    });
                                    this.getEnvironment();
                                });
                            }
                            this.form.loading = false;
                            this.form.btnText = '提交';
                            this.form.visible = false;
                        });
                    }
                });
            },
            //删除
            handleDel: function (index, row) {
                this.$confirm('确认删除该记录吗?', '提示', {
                    type: 'warning'
                }).then(() => {
                    this.table.loading = true;
                    //NProgress.start();
                    let para = {id: row.id};
                    removeEnvironment(para).then((res) => {
                        this.table.loading = false;
                        //NProgress.done();
                        let {message, type} = res;
                        this.$message({
                            showClose: true,
                            message: message,
                            type: type
                        });
                        this.getEnvironment();
                    });
                }).catch(() => {

                });
            },
            //获取当前用户名
            getUserName: function () {
                var user = sessionStorage.getItem('user');
                if (user) {
                    user = JSON.parse(user);
                    this.userName = user.account;
                }
            },
        },
        mounted() {
            this.getEnvironment();
            this.getUserName();
        }
    }

</script>