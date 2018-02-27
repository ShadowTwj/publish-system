import Vue from "vue";
// import VueResource from "vue-resource";
import {MessageBox, Notification} from "element-ui";

// Vue.use(VueResource);

export default {
    success: function (msg) {
        Notification.success({
            title: '成功',
            message: msg
        });
    },
    warning: function (msg) {
        Notification.warning({
            title: '警告',
            message: msg,
            duration: 30000
        });
    },
    error: function (msg) {
        Notification.error({
            title: '错误',
            message: msg,
            duration: 30000
        })
    },
};