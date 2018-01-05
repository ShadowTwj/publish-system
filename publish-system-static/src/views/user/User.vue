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
        <el-table :data="userList" highlight-current-row v-loading="table.loading" element-loading-text="拼命加载中" @selection-change="selsChange" style="width: 100%;">
            <el-table-column type="selection" width="55">
            </el-table-column>
            <el-table-column type="index" width="60">
            </el-table-column>
            <el-table-column prop="account" label="账号" sortable>
            </el-table-column>
            <!--<el-table-column prop="password" label="密码" sortable>-->
            <!--</el-table-column>-->
            <el-table-column prop="nickname" label="昵称" sortable>
            </el-table-column>
            <el-table-column prop="token" label="token" min-width="100" sortable>
            </el-table-column>
            <el-table-column prop="creater" label="创建人" sortable>
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间" min-width="150" sortable>
            </el-table-column>
            <el-table-column prop="updateTime" label="修改时间" min-width="150" sortable>
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
            <el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0">批量删除</el-button>
            <!--分页-->
            <el-pagination
                    @current-change="handleCurrentChange"
                    @size-change="handleSizeChange"
                    :current-page="table.page" :page-sizes="[10, 25, 50]" :page-size="table.size"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="userList.length" style="float:right">
            </el-pagination>
        </el-col>

        <!--编辑界面-->
        <el-dialog title="编辑" v-model="editFormVisible" :close-on-click-modal="false">
            <el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
                <el-form-item label="账号">
                    <el-input v-model="editForm.account" auto-complete="off" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input type="password" v-model="editForm.password" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="确认密码" prop="checkPass">
                    <el-input type="password" v-model="editForm.checkPass" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="昵称">
                    <el-input v-model="editForm.nickname" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="token">
                    <el-input v-model="editForm.token" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="创建人">
                    <el-input v-model="editForm.creater" auto-complete="off" :disabled="true"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="editFormVisible = false">取消</el-button>
                <el-button type="primary" @click.native="editSubmit" :loading="editLoading" :disabled="isDisabled">提交</el-button>
            </div>
        </el-dialog>

        <!--新增界面-->
        <el-dialog title="新增" v-model="addFormVisible" :close-on-click-modal="false">
            <el-form :model="addForm" label-width="80px" :rules="addFormRules" status-icon ref="addForm">
                <el-form-item label="账号" prop="account">
                    <el-input v-model="addForm.account" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <!--<el-radio-group v-model="addForm.password">-->
                    <el-input type="password" v-model="addForm.password" auto-complete="off"></el-input>
                    <!--</el-radio-group>-->
                </el-form-item>
                <el-form-item label="确认密码" prop="checkPass">
                    <el-input type="password" v-model="addForm.checkPass" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="昵称">
                    <el-input v-model="addForm.nickname" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="token">
                    <el-input v-model="addForm.token" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="创建人">
                    <el-input v-model="addForm.creater" auto-complete="off" :disabled="true"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="addFormVisible = false">取消</el-button>
                <el-button type="primary" @click.native="addSubmit" :loading="addLoading" :disabled="isDisabled">提交</el-button>
            </div>
        </el-dialog>
    </section>
</template>

<script>
    import util from '../../common/js/util'
    //import NProgress from 'nprogress'
    import {getUserList, findAccount, removeUser, batchRemoveUser, editUser, addUser} from '../../api/api';

    export default {
        data() {
            //当账号表单失去焦点时判断账号是否为空和是否存在，当账号为空或存在时不能提交
            var accountValidate = (rule, value, callback) => {
                if (value.trim().length > 0) {
                    findAccount(value).then((data) => {
                        if (data.type === 'success') {
                            this.accountDisabled = true;
                            if (this.accountDisabled && this.passwordDisabled && this.checkPassDisabled) {
                                this.isDisabled = false;
                            }
                            callback();
                        } else if (data.type === 'warning') {
                            this.accountDisabled = false;
                            this.isDisabled = true;
                            callback(new Error('该账号已存在'));
                        }
                    })
                } else {
                    this.isDisabled = true;
                    this.accountDisabled = false;
                    callback(new Error('请输入账号'));
                }
            };
            //当密码为空或小于6位时提示并且不能提交
            var passwordValidate = (rule, value, callback) => {
                if (value.trim().length > 0) {
                    if (value.trim().length < 6) {
                        this.isDisabled = true;
                        this.passwordDisabled = false;
                        callback(new Error('密码长度不能少于6位'));
                    } else {
                        this.passwordDisabled = true;
                        if (this.accountDisabled && this.passwordDisabled && this.checkPassDisabled) {
                            this.isDisabled = false;
                        }
                        callback();
                    }
                } else {
                    this.isDisabled = true;
                    this.passwordDisabled = false;
                    callback(new Error('请输入密码'));
                }
            };
            //当确认密码为空或不等于密码时提示并且不能提交
            var checkPassValidate = (rule, value, callback) => {
                if (value.trim().length > 0) {
                    if (value !== this.addForm.password) {
                        this.isDisabled = true;
                        this.checkPassDisabled = false;
                        callback(new Error('两次输入的密码不一致'));
                    } else {
                        this.checkPassDisabled = true;
                        if (this.accountDisabled && this.passwordDisabled && this.checkPassDisabled) {
                            this.isDisabled = false;
                        }
                        callback();
                    }
                } else {
                    this.isDisabled = true;
                    this.checkPassDisabled = false;
                    callback(new Error('请再次输入密码'));
                }
            };
            var editPasswordValidate = (rule, value, callback) => {
                if (value.trim().length > 0) {
                    if (value.trim().length < 6) {
                        this.isDisabled = true;
                        this.passwordDisabled = false;
                        callback(new Error('密码长度不能少于6位'));
                    } else {
                        this.passwordDisabled = true;
                        if (this.passwordDisabled && this.checkPassDisabled) {
                            this.isDisabled = false;
                        }
                        callback();
                    }
                } else {
                    this.isDisabled = true;
                    this.passwordDisabled = false;
                    callback(new Error('请输入密码'));
                }
            };
            var editCheckPassValidate = (rule, value, callback) => {
                if (value.trim().length > 0) {
                    if (value !== this.editForm.password) {
                        this.isDisabled = true;
                        this.checkPassDisabled = false;
                        callback(new Error('两次输入的密码不一致'));
                    } else {
                        this.checkPassDisabled = true;
                        if (this.passwordDisabled && this.checkPassDisabled) {
                            this.isDisabled = false;
                        }
                        callback();
                    }
                } else {
                    this.isDisabled = true;
                    this.checkPassDisabled = false;
                    callback(new Error('请再次输入密码'));
                }
            };
            return {
                filters: {
                    keyword: ''
                },
                sels: [],//列表选中列

                table: {
                    loading: false,
                    page: 1,
                    size: 10,
                    data: []
                },

                editFormVisible: false,//编辑界面是否显示
                editLoading: false,
                editFormRules: {
                    password: [
                        {validator: editPasswordValidate, required: true, trigger: 'blur'}
                    ],
                    checkPass: [
                        {validator: editCheckPassValidate, required: true, trigger: 'blur'}
                    ]
                },
                //编辑界面数据
                editForm: {
                    id: '',
                    account: '',
                    password: '',
                    checkPass: '',
                    nickname: '',
                    token: '',
                    creater: ''
                },

                addFormVisible: false,//新增界面是否显示
                addLoading: false,
                //自定义校验规则，校验账号和密码
                addFormRules: {
                    /*
                    validator校验器
                    required提示必填
                    trigger: 'blur'当失去焦点就进行一次验证
                     */
                    account: [
                        {validator: accountValidate, required: true, trigger: 'blur'}
                    ],
                    password: [
                        {validator: passwordValidate, required: true, trigger: 'blur'}
                    ],
                    checkPass: [
                        {validator: checkPassValidate, required: true, trigger: 'blur'}
                    ]
                },
                //新增界面数据
                addForm: {
                    account: '',
                    password: '',
                    checkPass: '',
                    nickname: '',
                    token: '',
                    creater: ''
                },
                //当前用户名
                userName: '',
                //是否可以提交
                isDisabled: true,
                //判断当前账号是否可以提交
                accountDisabled: false,
                //判断当前密码是否可以提交
                passwordDisabled: false,
                //判断当前确认密码是否可以提交
                checkPassDisabled: false,
            };
        },
        computed: {
            userList: function () {
                var self = this, data = self.table.data, page = self.table.page, size = self.table.size, keyword = self.filters.keyword;
                var v = data.filter(function (p) {
                    return (p.account && p.account.indexOf(keyword) !== -1)
                        || (p.password && p.password.indexOf(keyword) !== -1)
                        || (p.nickname && p.nickname.indexOf(keyword) !== -1)
                        || (p.creater && p.creater.indexOf(keyword) !== -1)
                });
                v = v.slice((page - 1) * size, page * size);
                return v;
            }
        },
        methods: {
            handleCurrentChange(val) {
                this.table.page = val;
            },
            handleSizeChange(val) {
                this.table.size = val;
            },
            //获取所有用户列表
            getUsers() {
                this.table.loading = true;
                //NProgress.start();
                getUserList().then((res) => {
                    this.table.data = res.data;
                    this.table.loading = false;
                    //NProgress.done();
                });
            },
            //获取用户列表(分页)
            // getUsersList() {
            //     let para = {
            //         page: this.page,
            //         name: this.filters.name
            //     };
            //     this.table.loading = true;
            //     //NProgress.start();
            //     getUserListPage(para).then((res) => {
            //         this.total = res.data.total;
            //         this.users = res.data.users;
            //         this.table.loading = false;
            //         //NProgress.done();
            //     });
            // },
            //删除
            handleDel: function (index, row) {
                this.$confirm('确认删除该记录吗?', '提示', {
                    type: 'warning'
                }).then(() => {
                    this.table.loading = true;
                    //NProgress.start();
                    let para = {id: row.id.toString()};
                    removeUser(para).then((res) => {
                        this.table.loading = false;
                        //NProgress.done();
                        let {message, type} = res;
                        this.$message({
                            showClose: true,
                            message: message,
                            type: type
                        });
                        this.getUsers();
                    });
                }).catch(() => {

                });
            },
            //显示编辑界面
            handleEdit: function (index, row) {
                this.editFormVisible = true;
                this.editForm = Object.assign({}, row);
                this.editForm.checkPass = '';
                this.isDisabled = true;
                this.passwordDisabled = true;
                this.checkPassDisabled = false;
            },
            //显示新增界面
            handleAdd: function () {
                this.addFormVisible = true;
                this.isDisabled = true;
                this.accountDisabled = false;
                this.passwordDisabled = false;
                this.checkPassDisabled = false;
                this.addForm = {
                    account: '',
                    password: '',
                    checkPass: '',
                    nickname: '',
                    token: '',
                    creater: this.userName
                };
            },
            //编辑
            editSubmit: function () {
                this.$refs.editForm.validate((valid) => {
                    if (valid) {
                        this.$confirm('确认提交吗？', '提示', {}).then(() => {
                            this.editLoading = true;
                            //NProgress.start();
                            let para = Object.assign({}, this.editForm);
                            para.birth = (!para.birth || para.birth == '') ? '' : util.formatDate.format(new Date(para.birth), 'yyyy-MM-dd');
                            editUser(para).then((res) => {
                                this.editLoading = false;
                                //NProgress.done();
                                this.$message({
                                    showClose: true,
                                    message: '提交成功',
                                    type: 'success'
                                });
                                this.$refs['editForm'].resetFields();
                                this.editFormVisible = false;
                                this.getUsers();
                            });
                        });
                    }
                });
            },
            //新增
            addSubmit: function () {
                this.$refs.addForm.validate((valid) => {
                    if (valid) {
                        this.$confirm('确认提交吗？', '提示', {}).then(() => {
                            this.addLoading = true;
                            //NProgress.start();
                            let para = Object.assign({}, this.addForm);
                            // para.birth = (!para.birth || para.birth == '') ? '' : util.formatDate.format(new Date(para.birth), 'yyyy-MM-dd');
                            addUser(para).then((data) => {
                                this.addLoading = false;
                                //NProgress.done();
                                let {message, type} = data;
                                this.$message({
                                    showClose: true,
                                    message: message,
                                    type: type
                                });
                                this.$refs['addForm'].resetFields();
                                this.addFormVisible = false;
                                this.getUsers();
                            });
                        });
                    }
                });
            },
            selsChange: function (sels) {
                this.sels = sels;
            },
            //批量删除
            batchRemove: function () {
                var ids = this.sels.map(item => item.id).toString();
                this.$confirm('确认删除选中记录吗？', '提示', {
                    type: 'warning'
                }).then(() => {
                    this.table.loading = true;
                    //NProgress.start();
                    let para = {ids: ids};
                    batchRemoveUser(para).then((res) => {
                        this.table.loading = false;
                        //NProgress.done();
                        let {message, type} = res;
                        this.$message({
                            showClose: true,
                            message: message,
                            type: type
                        });
                        this.getUsers();
                    });
                }).catch(() => {

                });
            },
            //获取当前用户名
            getUserName: function () {
                var user = sessionStorage.getItem('user');
                if (user) {
                    user = JSON.parse(user);
                    this.userName = user.nickname;
                }
            },
        },
        mounted() {
            this.getUsers();
            this.getUserName();
        }
    }

</script>

<style scoped>

</style>