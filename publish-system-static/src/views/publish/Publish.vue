<template>
    <section>
        <el-form ref="form" :model="form.data" :rules="form.rules" label-width="80px" style="margin:20px;width:70%;min-width:600px;">

            <el-form-item label="选择项目" prop="projectId">
                <el-select v-model="form.data.projectId" :loading="init.loading" loading-text="加载中" filterable placeholder="请选择" @change="handleProjectChange">
                    <el-option
                            v-for="item in init.projects"
                            :label="item.uniqueName"
                            :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="选择分支" prop="branch">
                <el-select v-model="form.data.branch" :loading="init.loading" loading-text="加载中" filterable placeholder="请选择" :disabled="form.data.projectId === ''">
                    <el-option-group v-for="code in init.codes" :label="code.group">
                        <el-option v-for="item in code.options" :label="item" :value="item">
                        </el-option>
                    </el-option-group>
                </el-select>
            </el-form-item>

            <el-form-item label="选择配置" prop="publishConfList">
                <el-col :span="24">
                    <el-form :inline="true">
                        <el-form-item>
                            <el-tooltip class="item" effect="dark" content="选择项目后允许添加配置" placement="top-start">
                                <el-button @click="handleAdd" :disabled="form.data.projectId === ''">新增</el-button>
                            </el-tooltip>
                        </el-form-item>
                    </el-form>
                </el-col>
                <el-table ref="config" :loading="init.loading" element-loading-text="拼命加载中" border :data="init.publishConfList" @selection-change="handleSelectionChange">
                    <el-table-column type="selection" width="50"></el-table-column>
                    <el-table-column prop="environmentUniqueName" label="环境"></el-table-column>
                    <el-table-column prop="replicas" label="实例数量" show-overflow-tooltip></el-table-column>
                    <el-table-column prop="remark" label="备注" show-overflow-tooltip></el-table-column>
                    <el-table-column label="操作">
                        <template scope="scope">
                            <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
                            <!--<el-button size="small" @click="handleRouteRule(scope.row)" v-if="scope.row.gray">路由规则</el-button>-->
                        </template>
                    </el-table-column>
                </el-table>
            </el-form-item>

            <el-form-item label="即时配送">
                <el-switch on-text="" off-text="" v-model="form.delivery"></el-switch>
            </el-form-item>

            <el-form-item label="备注">
                <el-input type="textarea" v-model="form.data.remark"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSubmit" :disabled="form.data.projectId === ''">立即发布</el-button>
                <el-button @click="publishHistory">发布历史</el-button>
            </el-form-item>
        </el-form>

        <!--新增编辑界面-->
        <el-dialog :visible.sync="config.dialogVisible" :title="config.title" v-model="config.visible">
            <el-form :model="config.data" label-width="100px" :rules="config.rules" ref="config">

                <el-form-item label="选择环境" prop="environmentId">
                    <el-select v-model="config.data.environmentId" :loading="config.loading" loading-text="加载中" filterable placeholder="请选择">
                        <el-option
                                v-for="item in init.environmentList"
                                :label="item.uniqueName"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="访问路径">
                    <el-input v-model="config.data.tomcatContextPath" auto-complete="off" placeholder="'/'可以带也可以不带" style="width:200px;"></el-input>
                </el-form-item>

                <el-form-item label="实体个数">
                    <el-input-number v-model="config.data.replicas" :min="1" :max="10"></el-input-number>
                </el-form-item>

                <el-form-item label="端口">
                    <el-input v-model="config.data.ports" auto-complete="off" :disabled="true"></el-input>
                </el-form-item>

                <el-form-item label="说明">
                    <el-input type="textarea" v-model="config.data.remark"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="config.visible = false">取 消</el-button>
                <el-button type="danger" @click="handleDel(config.data.id)" v-show="config.data.id != ''" :loading="config.loading">删除</el-button>
                <el-button type="primary" @click.native="submit" :loading="config.loading">
                    <div>{{config.btnText}}</div>
                </el-button>
            </div>
        </el-dialog>
    </section>
</template>

<script>
    import {publishInit, publishInitBranch, publishInitConfig, publishInitEnvironment, publishAddConfig, publishEditConfig, publishDelConfig} from '../../api/api';
    import util from '../../utils/util';

    export default {
        data() {
            return {
                userName: '',
                init: {
                    loading: true,
                    projects: [],
                    codes: [],
                    publishConfList: [],
                    environmentList: [],
                },
                form: {
                    loading: false,
                    data: {
                        projectId: '',
                        branch: '',
                        configId: '',
                        remark: ''
                    },
                    rules: {
                        projectId: [
                            {type: 'number', required: true, message: '请选择项目', trigger: 'change'}
                        ],
                        branch: [
                            {type: "string", required: true, message: '请选择分支', trigger: 'blur'}
                        ],
                        publishConfList: [
                            {type: 'array', required: true, message: '请勾选配置信息', trigger: 'blur'}
                        ],
                    }
                },
                config: {
                    title: '',
                    dialogVisible: false,
                    visible: false,
                    btnText: '提交',
                    loading: false,
                    data: {
                        id: '',
                        projectId: '',
                        environmentId: '',
                        environmentUniqueName: '',
                        tomcatContextPath: '',
                        replicas: '',
                        ports: '',
                        remark: '',
                        createUser: '',
                        createTime: '',
                        updateUser: '',
                        updateTime: ''
                    },
                    rules: {
                        environmentId: [
                            {type: 'number', required: true, message: '请选择环境', trigger: 'blur'}
                        ],
                    }
                }
            }
        },
        methods: {
            initData() {
                this.init.loading = true;
                publishInit().then((response) => {
                    if (response.type === 'success') {
                        this.init.projects = response.data.projects;
                    } else if (response.type === 'error') {
                        util.error(response.message);
                    } else {
                        util.warning(response.message);
                    }
                    this.init.loading = false;
                }, () => {//响应错误回调
                    util.error()
                });
                this.init.loading = false;
            },
            handleProjectChange(v) {
                this.init.loading = true;
                this.init.codes = [];
                this.form.data.branch = '';
                this.getBranch(v);
                this.getConfig(v);
                this.init.loading = false;
            },
            //获取branch和tag
            getBranch(v) {
                let params = {projectId: v, userName: this.userName};
                publishInitBranch(params).then((response) => {
                    if (response.type === 'success') {
                        this.init.codes = response.data.codes;
                    } else if (response.type === 'error') {
                        util.error(response.message);
                    } else {
                        util.warning(response.message);
                    }
                    this.init.loading = false;
                }, () => {
                    util.error();
                });
            },
            //获取当前项目的publish config
            getConfig(v) {
                publishInitConfig(v).then((response) => {
                    if (response.type === 'success') {
                        this.init.publishConfList = response.data.publishConfList;
                    } else if (response.type === 'error') {
                        util.error(response.message);
                    } else {
                        util.warning(response.message);
                    }
                    this.init.loading = false;
                }, () => {
                    util.error();
                });
            },
            handleSelectionChange(v) {

            },
            //获取环境
            getEnvironment() {
                publishInitEnvironment().then((response) => {
                    if (response.type === 'success') {
                        this.init.environmentList = response.data;
                    } else if (response.type === 'error') {
                        util.error(response.message);
                    } else {
                        util.warning(response.message);
                    }
                    this.config.loading = false;
                });
            },
            //新增
            handleAdd() {
                this.config.dialogVisible = true;
                this.config.visible = true;
                this.config.title = '新增';
                this.config.loading = true;
                //获取环境
                this.getEnvironment();
                this.config.data.id = '';
                this.config.data.projectId = this.form.data.projectId;
                this.config.data.environmentId = '';
                this.config.data.environmentUniqueName = '';
                this.config.data.tomcatContextPath = '';
                this.config.data.replicas = '';
                this.config.data.ports = '';
                this.config.data.remark = '';
                this.config.data.createUser = this.userName;
                this.config.data.createTime = '';
                this.config.data.updateUser = this.userName;
                this.config.data.updateTime = '';
            },
            //编辑
            handleEdit(row) {
                this.config.dialogVisible = true;
                this.config.visible = true;
                this.config.title = '编辑';
                this.config.loading = true;
                this.getEnvironment();
                this.config.data.id = row.id;
                this.config.data.projectId = row.projectId;
                this.config.data.environmentId = row.environmentId;
                this.config.data.environmentUniqueName = row.environmentUniqueName;
                this.config.data.tomcatContextPath = row.tomcatContextPath;
                this.config.data.replicas = row.replicas;
                this.config.data.ports = row.ports;
                this.config.data.remark = row.remark;
                this.config.data.createUser = row.userName;
                this.config.data.createTime = row.createTime;
                this.config.data.updateUser = this.userName;
                this.config.data.updateTime = row.updateTime;
            },
            //删除
            handleDel(id) {
                this.$confirm('确认删除该配置吗?', '提示', {type: 'warning'}).then(() => {
                    this.config.loading = true;
                    publishDelConfig(id).then((response) => {
                        if (response.type === "success") {
                            util.success(response.message);
                        } else if (response.type === "error") {
                            util.error(response.message);
                        } else {
                            util.warning(response.message);
                        }
                        this.getConfig(this.config.data.projectId);
                    }, () => {
                        util.error();
                    });
                    this.config.loading = false;
                    this.config.visible = false;
                });
            },
            submit() {
                this.$refs.config.validate((valid) => {
                    if (valid) {
                        this.$confirm('确认提交吗?', '提示', {}).then(() => {
                            this.config.loading = true;
                            this.config.btnText = '提交中';
                            var formData = this.config.data;
                            let param = Object.assign({}, formData);
                            if (this.config.data.id) {
                                publishEditConfig(param).then((response) => {
                                    if (response.type === "success") {
                                        util.success(response.message);
                                    } else if (response.type === "error") {
                                        util.error(response.message);
                                    } else {
                                        util.warning(response.message);
                                    }
                                    this.getConfig(this.config.data.projectId);
                                }, () => {
                                    util.error();
                                });
                            } else {
                                publishAddConfig(param).then((response) => {
                                    if (response.type === "success") {
                                        util.success(response.message);
                                    } else if (response.type === "error") {
                                        util.error(response.message);
                                    } else {
                                        util.warning(response.message);
                                    }
                                    this.getConfig(this.config.data.projectId);
                                }, () => {
                                    util.error();
                                });
                            }
                            this.config.loading = false;
                            this.config.btnText = '提交';
                            this.config.visible = false;
                            this.config.dialogVisible = false;
                        });
                    }
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
            onSubmit() {
                console.log('submit!');
            },
            publishHistory() {

            }
        },
        mounted() {
            this.initData();
            this.getUserName();
        }
    }

</script>