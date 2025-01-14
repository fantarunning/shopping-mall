<script setup>

import {Plus} from "@element-plus/icons-vue";
import {ref} from "vue";
import request from "@/axios/request.js";
import router from "@/router/router.js";
import {ElMessage} from "element-plus";
// 管理员的信息
/*const admin = ref({
  id: "",
  username: "",
  password: "",
  name: "",
  avatar: "",
  role: "",
  phone: "",
  email: ""
})*/
// 将用户信息从浏览器缓存中取出
const admin=ref(JSON.parse(localStorage.getItem('xm-user') || '{}'))

// 提交
const onSubmit=()=>{
  //   校验表单是否合法
  adminForm.value.validate((flag) => {
    if (flag) {//验证通过
      request({
        url: '/admin/save',
        method: 'post',
        data: admin.value
      }).then(res => {
        if (res.data.code==200){
          // 退出登录
          ElMessage.success("修改成功")
          localStorage.removeItem('xm-user');
          router.push("/login");
        }

      })
    } else {
      ElMessage({
        type: 'error',
        message: '验证没通过！',
      })
    }
  })
}

// 表单校验规则
const rules = {
  username: [
    {required: true, message: "请输入账号", trigger: "blur"}
  ],
  password: [
    {required: true, message: "请输入密码", trigger: "blur"},
    //正则表达式校验
    {
      pattern: /^\S{6,15}$/,
      message: '密码必须是6-15位非空字符',
      trigger: 'blur'
    }

  ],
  name: [
    {required: true, message: "请输入姓名", trigger: "blur"}
  ],
  role: [
    {required: true, message: "请选择角色", trigger: "blur"}
  ],
  phone: [
    {required: true, message: "请输入电话", trigger: "blur"},
    //正则表达式校验
    {
      pattern: /^1[3456789]\d{9}$/,
      message: '请输入正确的手机号码',
      trigger: 'blur'
    }
  ],
  email: [
    {required: true, message: "请输入邮箱", trigger: "blur"},
    {
      pattern: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
      message: '请输入正确的邮箱',
      trigger: 'blur'
    }
  ]
}

// 表单引用
const adminForm = ref();

// 图片上传之前的钩子--》判断文件是否是图片、图片大小是否符合要求
const beforeAvatarUpload = (rawFile) => {
  console.log("类型：", rawFile.type)
  console.log(rawFile.type !== 'image/png');
  // 获取文件后缀
  let testmsg = rawFile.name.substring(rawFile.name.lastIndexOf('.') + 1)
  console.log("后缀：", testmsg)
  // 判断文件类型
  const extension = testmsg === 'jpg' || testmsg === 'png' || testmsg === 'jpeg' || testmsg === 'gif'
  if (!extension) {//不符合要求
    ElMessage.error('图片必须是jpg/jpeg/png格式!')
    return false
  }
  // 校验文件大小
  if (rawFile.size / 1024 / 1024 > 2) {
    ElMessage.error('图片大小不能超过2MB!')
    return false
  }
  return true
}
// 图片上传后的钩子函数-->回写图片地址
const handleAvatarSuccess = (res) => {
  console.log(res)
  admin.value.avatar = res.msg
}
</script>

<template>
  <el-form :model="admin" :rules="rules" ref="adminForm" label-width="auto" style="width: 400px;">
    <el-form-item label="账号" prop="username">
      <el-input v-model="admin.username" autocomplete="off" readonly/>
    </el-form-item>
<!--    <el-form-item label="密码" prop="password">
      <el-input type="password" v-model="admin.password" autocomplete="off"/>
    </el-form-item>-->
    <el-form-item label="姓名" prop="name">
      <el-input v-model="admin.name" autocomplete="off"/>
    </el-form-item>
    <el-form-item label="电话" prop="phone">
      <el-input v-model="admin.phone" autocomplete="off"/>
    </el-form-item>
    <el-form-item label="邮箱" prop="email">
      <el-input v-model="admin.email" autocomplete="off"/>
    </el-form-item>
    <!--      下拉选择-->
    <el-form-item label="角色" prop="role">
      <el-select v-model="admin.role" placeholder="请选择角色">
        <el-option label="管理员" value="ADMIN"/>
<!--        <el-option label="用户" value="USER"/>-->
        <!--          <el-option label="水果" value="水果"/>-->
      </el-select>
    </el-form-item>
    <!--  单选框-->
    <!--      <el-form-item label="状态" prop="remark">
            <el-radio-group v-model="admin.remark">
              <el-radio value="上架">上架</el-radio>
              <el-radio value="未上架">未上架</el-radio>
            </el-radio-group>
          </el-form-item>-->
    <!--      图片上传-->
    <el-form-item label="头像">
      <el-upload
          class="avatar-uploader"
          :action="'http://' + 'localhost:8080/files/upload'"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
      >
        <img v-if="admin.avatar" :src="admin.avatar" class="avatar" style="width: 300px"/>
        <el-icon v-else class="avatar-uploader-icon">
          <Plus/>
        </el-icon>
      </el-upload>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">提交</el-button>
      <el-button type="danger">取消</el-button>
    </el-form-item>
  </el-form>
</template>

<style scoped>
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>