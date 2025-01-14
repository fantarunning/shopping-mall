<script setup>

import {Goods, House, Promotion, User,Avatar,UserFilled,Tickets,Notebook,ChatLineRound} from "@element-plus/icons-vue";

</script>

<template>
  <!--      侧边导航栏-->
  <el-aside width="200px" >
    <el-menu default-active="1" style="min-height: 100%;overflow-x: hidden;"
             class="el-menu"
             background-color="#F8F8FF"
             text-color="grey"
             router>
        <el-menu-item index="/manage/index" >
          <el-icon><House /></el-icon>主页</el-menu-item>
      <el-menu-item index="/user/index" >
        <el-icon><House /></el-icon>前台页面</el-menu-item>
        <el-menu-item index="/manage/goods">
          <el-icon><Goods /></el-icon>
          <span>商品管理</span>
        </el-menu-item>
      <el-sub-menu index="2">
        <template #title><el-icon><User /></el-icon>用户管理</template>
        <el-menu-item index="/manage/user"><el-icon><UserFilled /></el-icon>用户信息</el-menu-item>
        <el-menu-item index="/manage/admin">
          <el-icon><Avatar /></el-icon>
          <span>管理员信息</span>
        </el-menu-item>
      </el-sub-menu>
      <el-menu-item index="/manage/order" ><el-icon><Promotion /></el-icon>订单管理</el-menu-item>
      <el-menu-item index="/manage/notice"><el-icon><Notebook /></el-icon>公告管理</el-menu-item>
      <el-menu-item index="/manage/popular"><el-icon><Tickets /></el-icon>科普管理</el-menu-item>
      <el-menu-item index="/manage/comment"><el-icon><ChatLineRound /></el-icon>评论管理</el-menu-item>
    </el-menu>
  </el-aside>
</template>


<style scoped>
.el-menu{
  height: 100vh;
}
</style>