<template>
    <section>
        <el-steps :active="active" :process-status="status" :finish-status="finishStatus" align-center style="margin-top: 20px">
            <el-step v-for="item in steps" :title="item"></el-step>
        </el-steps>

        <!--accordion展开-->
        <el-collapse v-model="activeName" accordion>
            <template v-for="o in logs">
                <el-collapse-item :title="o.stepOrder + '.' + o.stepName + ' - ' + o.createTime " :name="o.stepOrder">
                    <div v-html="o.stepLog"></div>
                </el-collapse-item>
            </template>
        </el-collapse>
    </section>
</template>

<script>
    import {getPublishLog} from '../../api/api';

    export default {
        data() {
            return {
                status: '',
                finishStatus: 'success',
                steps: ['开始', '获取代码', '编译代码', '部署项目', '完成'],
                active: 0,

                //定时器id
                stopId: '',

                //展开行
                activeName: '',
                logs: [],
            }
        },

        methods: {
            initData() {
                this.getLog();
                this.pollingData();
            },

            pollingData() {
                this.stopId = setInterval(this.getLog, 5000);
            },

            getLog() {
                const publishHistoryId = this.$route.params.publishHistoryId;
                getPublishLog(publishHistoryId).then((response) => {
                    this.logs = response;

                    let lastLog = response[response.length - 1];
                    if (lastLog.status === 1) {
                        clearInterval(this.stopId);
                        this.status = 'error';
                        this.active = lastLog.stepOrder - 1;
                    } else {
                        this.active = lastLog.stepOrder;
                        if (lastLog.stepOrder === 5) {
                            clearInterval(this.stopId);
                        }
                    }
                });
            }
        },

        mounted() {
            this.initData();
        }
    }

</script>