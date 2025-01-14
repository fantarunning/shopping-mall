<script setup>
import {ref} from "vue";
import {User,Lock} from "@element-plus/icons-vue";
import request from "@/axios/request.js";
import router from "@/router/router.js";
import {ElMessage} from "element-plus";

const user=ref({
  username:"",
  password:"",
  role:""
})
const rules={
  username:[
    {required:true,message:"请输入账号",trigger:"blur"}
  ],
  password:[
    {required:true,message:"请输入密码",trigger:"blur"}
  ],
  role:[
    {required:true,message:"请选择角色",trigger:"blur"}
  ]
}
// 表单引用
const formRef=ref()
// 登录验证
const login=()=>{
  formRef.value.validate((flag)=>{
    if(flag){
      request({
        url:"/login",
        method:"post",
        data:user.value
      }).then(res => {
        console.log(res)
        if (res.data.code==200){
          const userData = res.data.data;
          const userRole = userData.role; // 提取角色信息
          ElMessage.success("登录成功！")
          // 将用户信息保存到浏览器缓存中
          localStorage.setItem('xm-user', JSON.stringify(res.data.data))
          // 跳转首页
          if(userRole=="ADMIN"){
            router.push("/manage/index")
          }else{
            router.push("/user/index")
          }
        }else {
          ElMessage.error(res.data.msg)
        }
      })
    }
  })
}

</script>

<template>
  <div class="login-container">
    <div class="login-box">
      <div style="font-weight: bold; font-size: 24px; text-align: center; margin-bottom: 30px; color: #1450aa">欢 迎 登 录</div>
      <el-form ref="formRef" :model="user" :rules="rules">
        <el-form-item prop="username">
          <el-input :prefix-icon="User" size="large" v-model="user.username" placeholder="请输入账号"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input show-password :prefix-icon="Lock" size="large" v-model="user.password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item prop="role">
          <el-select size="large" v-model="user.role" placeholder="请选择角色">
            <el-option value="ADMIN" label="管理员"></el-option>
            <el-option value="USER" label="用户"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button size="large" type="primary" style="width: 100%" @click="login">登 录</el-button>
        </el-form-item>
        <div style="text-align: right">
          还没有账号？请 <router-link to="/register"><b style="color: dodgerblue">注册</b></router-link>
        </div>
      </el-form>
    </div>
  </div>
<!--  //background: linear-gradient(to top, #7f7fd5, #86a8e7, #91eae4);-->
</template>

<style scoped>
.login-container {
  height: 100vh;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
  background-image: url('@/assets/login.png');
  background-repeat:no-repeat;
  background-size:cover;
}
.login-box {
  width: 350px;
  padding: 30px;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  background-color: rgba(255, 255, 255, 0.5);
}
</style>