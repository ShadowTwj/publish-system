<template>
    <section>
        <el-form ref="form" label-width="120px">
            <el-form-item label="GitHub Token">
                <el-input style="width: 300px" v-model="token"></el-input>
                <div>
                    注意：请前往 <a href="https://github.com/settings/tokens" target="_blank">https://github.com/settings/tokens</a> 获取token
                </div>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="updateToken">更新TOKEN</el-button>
            </el-form-item>
        </el-form>
    </section>
</template>

<script>
    import util from '../../utils/util'
    import {initToken, updateToken} from '../../api/api'

    export default {
        data() {
            return {
                token: '',
                userName: '',
            }
        },
        methods: {
            initToken() {
                initToken(this.userName).then((response) => {
                    if (response.type === "success") {
                        this.token = response.data;
                    } else if (response.type === "error") {
                        util.error(response.message);
                    } else {
                        util.warning(response.message);
                    }
                }, () => {
                    util.error();
                });

            },
            updateToken() {
                var param = {account: this.userName, token: this.token};
                updateToken(param).then((response) => {
                    if (response.type === "success") {
                        util.success(response.message);
                        this.$router.push({path: 'project'});
                    } else if (response.type === "error") {
                        util.error(response.message);
                    } else {
                        util.warning(response.message);
                    }
                }, () => {
                    util.error();
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
            this.getUserName();
            this.initToken();
        }
    }
</script>