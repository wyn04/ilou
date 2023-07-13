//定义路由
import Index from "../pages/IndexPage.vue";
import Admin from "../pages/admin/AdminPage.vue";

const routes = [
    { path: '/', component: Index },
    { path: '/admin', component: Admin },
]

export default routes;