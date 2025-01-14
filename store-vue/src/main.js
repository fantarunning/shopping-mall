import { createApp } from 'vue'
import './style.css'
// 导入element-plu
import ElementPlus from 'element-plus'
// 导入路由
import router from './router/router.js'
// 导入css样式
import 'element-plus/dist/index.css'
import App from './App.vue'
// 挂载element-plu和router
createApp(App).use(ElementPlus).use(router).mount('#app')