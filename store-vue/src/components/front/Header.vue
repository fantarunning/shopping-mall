<script setup>
import { ref } from "vue";
import router from "@/router/router.js";
// 图片地址
const url=ref("/logo1.png")
// 将用户信息从浏览器缓存中取出
const user=ref(JSON.parse(localStorage.getItem('xm-user') || '{}'))

// 退出登录
const logout=()=>{
//   删除缓存中的信息
  localStorage.removeItem('xm-user');
//   回到登录页面
  router.push("/login");
}
</script>

<template>
  <!--  顶部导航栏-->
  <el-menu
      router
      class="el-menu-demo custom-menu"
      mode="horizontal"
      text-color="grey"
      background-color="#F5F5F5"
      :ellipsis="false"
  >
    <div class="menu-left">
    <el-menu-item index="1">
      <el-image style="width: 60px; height: 60px;border-radius: 50%;" :src="url"  />
      ~ || ~香薰商城平台
    </el-menu-item>
    </div>
    <div class="menu-right">
    <el-menu-item index="/user/index">首页
    </el-menu-item>
    <el-menu-item index="/user/shopping">
      <span>购物商城</span>
    </el-menu-item>
    <el-menu-item index="/user/cart">
      <span>购物车</span>
    </el-menu-item>
    <el-menu-item index="">
      <span>超话</span>
    </el-menu-item>
    <el-menu-item index="">
      <span>问答社区</span>
    </el-menu-item>
      <el-menu-item index="">
        <span>消息通知</span>
      </el-menu-item>
    <el-menu-item index="/user/detail">
      <span>我的</span>
    </el-menu-item>
    <el-sub-menu index="10">
      <template #title>
        <img :src="user.avatar" width="40px" height="40px" style="border-radius: 50%;margin-right: 5px" />
        {{ user.name }}
      </template>
      <el-menu-item index="/user/personal">个人中心</el-menu-item>
      <el-menu-item index="/user/password">账号与安全</el-menu-item>
      <el-menu-item index="/user/password">消息通知</el-menu-item>
      <el-menu-item index="10-3" @click="logout">退出登录</el-menu-item>
    </el-sub-menu>
    </div>
  </el-menu>
</template>

<style scoped>
 .custom-menu {
   display: flex;
   justify-content: space-between; /* 在父容器中使用 space-between 使左右两部分自动拉开距离 */
   align-items: center; /* 垂直居中 */
 }

.menu-left {
  flex: 0 0 auto; /* 左侧部分不占据多余空间 */
}

.menu-right {
  display: flex;
  justify-content: flex-end; /* 右侧部分内部元素右对齐 */
  align-items: center; /* 垂直居中 */
}

.menu-right .el-menu-item,
.menu-right .el-sub-menu {
  margin-left: 10px; /* 根据需要调整右侧元素之间的间距 */
}
</style>