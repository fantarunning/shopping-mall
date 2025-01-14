<script setup>
import {ref} from "vue";
// 注册子组件
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import Aside from "@/components/Aside.vue";
import router from "@/router/router.js";
// 判断是否登录
const user=ref(JSON.parse(localStorage.getItem('xm-user') || '{}'))
console.log("111",user.value)
if (user.value.name==null){//如果没有登录则跳转到登录页面
  router.push("/login")
}
</script>

<template>
  <!-- container布局 -->
  <div class="common-layout">
    <el-container>
      <!--      顶部导航栏-->
      <el-header><Header></Header></el-header>
      <el-container>
        <!--        侧边导航栏-->
        <el-aside width="200px"><Aside></Aside></el-aside>
        <el-container>
          <!--          内容显示区域-->
          <el-main>
            <!--            路由视图-->
            <router-view></router-view>
          </el-main>
          <!--          底部导航栏-->
          <el-footer><Footer></Footer></el-footer>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<style scoped>

</style>