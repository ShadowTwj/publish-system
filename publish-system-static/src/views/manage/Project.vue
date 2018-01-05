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
        <el-table :data="projectList" highlight-current-row v-loading="table.loading" element-loading-text="拼命加载中" @selection-change="selsChange" style="width: 100%;">
            <el-table-column type="selection" width="55">
            </el-table-column>
            <el-table-column type="index" width="60">
            </el-table-column>
            <el-table-column prop="uniqueName" label="唯一标识" min-width="100" sortable>
            </el-table-column>
            <el-table-column prop="git" label="git地址" min-width="250" sortable>
            </el-table-column>
            <el-table-column prop="module" label="子模块" sortable>
            </el-table-column>
            <el-table-column prop="manager" label="负责人" sortable>
            </el-table-column>
            <el-table-column prop="remark" label="说明" min-width="100" sortable>
            </el-table-column>
            <!--<el-table-column prop="createTime" label="创建时间" width="180" sortable>-->
            <!--</el-table-column>-->
            <!--<el-table-column prop="updateTime" label="修改时间" width="180" sortable>-->
            <!--</el-table-column>-->
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
                    :total="projectList.length" style="float:right">
            </el-pagination>
        </el-col>

        <!--新增、编辑界面-->
        <el-dialog :title="form.title" v-model="form.visible" :close-on-click-modal="false">
            <el-form :model="form.data " label-width="100px" :rules="form.rules" ref="form">
                <el-form-item label="唯一标识" prop="uniqueName">
                    <el-input v-model="form.data.uniqueName" auto-complete="off" placeholder="发布中项目的唯一标识"></el-input>
                </el-form-item>
                <el-form-item label="Git地址" prop="git">
                    <el-input v-model="form.data.git" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="子模块">
                    <el-input v-model="form.data.module" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="负责人" prop="manager">
                    <el-input v-model="form.data.manager" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="说明">
                    <el-input type="textarea" v-model="form.data.remark"></el-input>
                </el-form-item>
                <!--<el-form-item label="限制发布时间">-->
                <!--<el-col :span="18">-->
                <!--<el-checkbox v-model="form.data.timeRestrict">发布时间窗口, 周一至周六：晚22:00-早6:00, 周日: 中午12:00-周一6:00</el-checkbox>-->
                <!--</el-col>-->
                <!--</el-form-item>-->
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
    import {getProjectList, addProject, editProject, removeProject} from '../../api/api'

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
                        git: '',
                        module: '',
                        manager: '',
                        remark: '',
                        createUser: '',
                        updateUser: ''
                    },
                    rules: {
                        uniqueName: [
                            {required: true, message: '请输入名称', trigger: 'blur'}
                        ],
                        git: [
                            {required: true, message: '请输入Git地址', trigger: 'blur'}
                        ],
                        manager: [
                            {required: true, message: '请输入负责人,用[,]分隔', trigger: 'blur'}
                        ]
                    }
                }
            };
        },
        computed: {
            projectList: function () {
                var self = this, data = self.table.data, page = self.table.page, size = self.table.size, keyword = self.filters.keyword;
                var v = data.filter(function (p) {
                    return (p.uniqueName && p.uniqueName.indexOf(keyword) !== -1)
                        || (p.git && p.git.indexOf(keyword) !== -1)
                        || (p.module && p.module.indexOf(keyword) !== -1)
                        || (p.manager && p.manager.indexOf(keyword) !== -1)
                });
                v = v.slice((page - 1) * size, page * size);
                return v;
            }
        },
        methods: {
            getProject() {
                this.table.loading = true;
                getProjectList().then((data) => {
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
                this.form.data.git = '';
                this.form.data.module = '';
                this.form.data.manager = '';
                this.form.data.remark = '';
                this.form.data.createUser = this.userName;
                this.form.data.updateUser = this.userName;
            },
            handleEdit: function (index, row) {
                this.form.visible = true;
                this.form.title = '编辑';
                this.form.data = this.table.data[index];
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
                                editProject(param).then((data) => {
                                    let {message, type} = data;
                                    this.$message({
                                        showClose: true,
                                        message: message,
                                        type: type
                                    });
                                    this.getProject();
                                });
                            } else {
                                addProject(param).then((data) => {
                                    let {message, type} = data;
                                    this.$message({
                                        showClose: true,
                                        message: message,
                                        type: type
                                    });
                                    this.getProject();
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
                    removeProject(para).then((res) => {
                        this.table.loading = false;
                        //NProgress.done();
                        let {message, type} = res;
                        this.$message({
                            showClose: true,
                            message: message,
                            type: type
                        });
                        this.getProject();
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
            this.getProject();
            this.getUserName();
        }
    }

</script>