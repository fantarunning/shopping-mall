import {createRouter,createWebHashHistory} from "vue-router";
// 在router.js文件中创建路由实例对象
const router=createRouter({
    history:createWebHashHistory(),
    routes:[//路由规则
        {path:"/",redirect:"/login"},
        {path:"/login",component:()=>import('@/views/Login.vue')},
        {path:"/404",component:()=>import('@/views/404.vue')},
        {path:"/register",component:()=>import('@/views/Register.vue')},
        {path: '/:pathMatch(.*)', redirect: '/404' },
        //管理端
        {path:"/manage",component:()=>import('@/views/Manage.vue'),
            children:[//子路由
                {path:"index",component:()=>import('@/views/manage/Index.vue')},
                {path:"goods",component:()=>import('@/views/manage/GoodsManage.vue')},
                {path:"admin",component:()=>import('@/views/manage/Admin.vue')},
                {path:"user",component:()=>import('@/views/manage/User.vue')},
                {path:"personal",component:()=>import('@/views/manage/Personal.vue')},
                {path:"password",component:()=>import('@/views/manage/Password.vue')},
                {path:"notice",component:()=>import('@/views/manage/NoticeManage.vue')},
                {path:"order",component:()=>import('@/views/manage/OrderManage.vue')},
                {path:"popular",component:()=>import('@/views/manage/PopularManage.vue')},
                {path:"comment",component:()=>import('@/views/manage/CommentManage.vue')},
                {path:"userAll",component:()=>import('@/views/manage/UserManage.vue')},
            ]
        },
        //客户端
        {path:"/user",component:()=>import('@/views/Front.vue'),
            children:[//子路由
                {path:"index",component:()=>import('@/views/front/Index.vue')},
                {path:"password",component:()=>import('@/views/front/Password.vue')},
                {path:"personal",component:()=>import('@/views/front/Personal.vue')},
                {path:"shopping",component:()=>import('@/views/front/ProductList.vue')},
                {path:"cart",component:()=>import('@/views/front/Cart.vue')},
                {path:"detail",component:()=>import('@/views/front/GoodDetail.vue')},
            ]
        },

    ]
})
// 输出实例对象
export default router;