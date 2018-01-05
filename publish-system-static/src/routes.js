import Login from './views/Login.vue'
import NotFound from './views/404.vue'
import Home from './views/Home.vue'
import Main from './views/Main.vue'
import User from './views/user/User.vue'
import History from './views/publish/History.vue'
import Publish from './views/publish/Publish.vue'
import Project from './views/manage/Project.vue'
import Page5 from './views/manage/Page5.vue'
import Page6 from './views/manage/Page6.vue'
import echarts from './views/statistics/echarts.vue'

let routes = [
    {
        path: '/login',
        component: Login,
        name: '',
        hidden: true
    },
    {
        path: '/404',
        component: NotFound,
        name: '',
        hidden: true
    },
    //{ path: '/main', component: Main },
    {
        path: '/',
        component: Home,
        name: '服务发布',
        iconCls: 'el-icon-message',//图标样式class
        children: [
            { path: '/main', component: Main, name: '主页', hidden: true },
            { path: '/publish', component: Publish, name: '发布服务' },
            { path: '/history', component: History, name: '发布历史' },
        ]
    },
    {
        path: '/',
        component: Home,
        name: '管理',
        iconCls: 'fa fa-id-card-o',
        children: [
            { path: '/Project', component: Project, name: '项目管理' },
            { path: '/page5', component: Page5, name: '环境管理' },
            { path: '/page6', component: Page6, name: '绑定token' },
        ]
    },
    {
        path: '/',
        component: Home,
        name: '',
        iconCls: 'fa fa-address-card',
        leaf: true,//只有一个节点
        children: [
            { path: '/user', component: User, name: '用户管理' }
        ]
    },
    {
        path: '/',
        component: Home,
        name: '发布统计',
        iconCls: 'fa fa-bar-chart',
        children: [
            { path: '/echarts', component: echarts, name: 'echarts' }
        ]
    },
    {
        path: '*',
        hidden: true,
        redirect: { path: '/404' }
    }
];

export default routes;