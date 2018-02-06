<template>
    <el-form ref="form" :model="form.data" :rules="form.rules" label-width="80px" @submit.prevent="onSubmit" style="margin:20px;width:60%;min-width:600px;">

        <el-form-item label="选择项目" prop="projectId">
            <el-select v-model="form.data.projectId" :loading="init.loading" loading-text="加载中" filterable placeholder="请选择" @change="handleProjectChange">
                <el-option
                        v-for="item in init.projects"
                        :label="item.uniqueName"
                        :value="item.id">
                </el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="活动时间">
            <el-col :span="11">
                <el-date-picker type="date" placeholder="选择日期" v-model="form.date1" style="width: 100%;"></el-date-picker>
            </el-col>
            <el-col class="line" :span="2">-</el-col>
            <el-col :span="11">
                <el-time-picker type="fixed-time" placeholder="选择时间" v-model="form.date2" style="width: 100%;"></el-time-picker>
            </el-col>
        </el-form-item>
        <el-form-item label="即时配送">
            <el-switch on-text="" off-text="" v-model="form.delivery"></el-switch>
        </el-form-item>
        <el-form-item label="活动性质">
            <el-checkbox-group v-model="form.type">
                <el-checkbox label="美食/餐厅线上活动" name="type"></el-checkbox>
                <el-checkbox label="地推活动" name="type"></el-checkbox>
                <el-checkbox label="线下主题活动" name="type"></el-checkbox>
                <el-checkbox label="单纯品牌曝光" name="type"></el-checkbox>
            </el-checkbox-group>
        </el-form-item>
        <el-form-item label="特殊资源">
            <el-radio-group v-model="form.resource">
                <el-radio label="线上品牌商赞助"></el-radio>
                <el-radio label="线下场地免费"></el-radio>
            </el-radio-group>
        </el-form-item>
        <el-form-item label="活动形式">npm
            <el-input type="textarea" v-model="form.desc"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary">立即创建</el-button>
            <el-button @click.native.prevent>取消</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
    import {publishInit} from '../../api/api'

    export default {
        data() {
            return {
                init: {
                    loading: true,
                    projects: [],
                },
                form: {
                    data: {
                        projectId: '',
                    },
                    rules: {
                        projectId: [
                            {type: 'number', required: true, message: '请选择项目', trigger: 'change'}
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
                    } else {

                    }
                    this.init.loading = false;
                });
            },
            handleProjectChange() {

            },
            onSubmit() {
                console.log('submit!');
            }
        },
        mounted() {
            this.initData();
        }
    }

</script>