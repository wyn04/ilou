import { createApp } from 'vue'
// import './style.css'
import 'ant-design-vue/dist/antd.css';
import Antd from 'ant-design-vue';
import App from './App.vue'
import * as VueRouter from 'vue-router';
import routes from "./config/route";
import VueClipboard from 'vue-clipboard2'


const app = createApp(App);

app.use(Antd);
app.use(VueClipboard);

const router = VueRouter.createRouter({
    // 4. 内部提供了 history 模式的实现。为了简单起见，我们在这里使用 hash 模式。
    history: VueRouter.createWebHistory(),
    routes, // `routes: routes` 的缩写
})
app.use(router)
app.mount('#app');
