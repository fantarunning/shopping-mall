<script setup>
import {onMounted, ref} from "vue";
import request from "@/axios/request.js";
import {ElMessage, ElMessageBox} from "element-plus";
import {Delete, Edit, Search, Plus,Download,UploadFilled} from "@element-plus/icons-vue";


const list = ref([])
//订单信息
const users = ref({
  id: "",
  name: "",
  phone: "",
  avatar: "",
  email: "",
  role: "",
  money:"",
  sex:"",
  password:"",
  intro:"",
  createtime:""
})
// 查询条件
const username=ref("")
// 模态框是否显示
const dialogVisible=ref(false);
// 分页参数
const pageNum=ref(1)
const pageSize=ref(5)
const total=ref(0)
// 表单校验规则
const rules = {
  name: [
    {required: true, message: "请输入名称", trigger: "blur"}
  ],
  phone: [
    {required: true, message: "请输入电话号码", trigger: "blur"}
  ],
  avatar: [
    {required: true, message: "请输入头像", trigger: "blur"}
  ],
  email: [
    {required: true, message: "请输入邮箱", trigger: "blur"}
  ],
  role: [
    {required: true, message: "请设置角色", trigger: "blur"}
  ],
  money: [
    {required: true, message: "已充值的钱包金额", trigger: "blur"}
  ],
  sex: [
    {required: true, message: "请选择性别", trigger: "blur"}
  ],
  password: [
    {required: true, message: "请输入登录密码", trigger: "blur"}
  ],
  intro: [
    {required: true, message: "请填写描述", trigger: "blur"}
  ],
}
// 表单引用
const userForm=ref();
// 保存选中的数据
const multipleSelection=ref([])

// 查询订单信息
const getList = () => {
  request({
    url: '/usermanage/page',
    method: 'get',
    params: {
      page: pageNum.value,
      limit: pageSize.value,
      UserName: username.value
    }
  }).then((res) => {
    console.log(res)
    list.value = res.data.data;
    total.value = res.data.count;
    // name.value=""
  })
}
// 根据id删除
const del = (id) => {
  // console.log(id)
  ElMessageBox.confirm(
      '是否确认删除？',
      'Warning',
      {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'warning',
      }
  )
      .then(() => {
        request({
          url: '/usermanage/del',
          method: 'get',
          params: {id: id}
        }).then(res => {
          if (res.data == 1) {
            ElMessage({
              type: 'success',
              message: '删除成功！',
            })
          } else {
            ElMessage({
              type: 'error',
              message: '删除失败！',
            })
          }
          getList()
        })
      })
      .catch(() => {
        ElMessage({
          type: 'warning',
          message: '取消删除！',
        })
      })
}

// 改变每页数据量
const handleSizeChange = (pSize) => {
  // console.log(pSize+`items per page`)
  pageSize.value=pSize;
  getList()
}
// 改变当前页
const handleCurrentChange = (pNum) => {
  // console.log(`current page: `+pNum)
  pageNum.value=pNum;
  getList()
}

// 搜索
const search=()=>{
  getList()
}

// 新增
const add=()=>{
  dialogVisible.value=true
}
// 数据提交
const save=()=>{
//   校验表单是否合法
  userForm.value.validate((flag,err)=>{
    if (flag){//验证通过
      request({
        url: '/usermanage/save',
        method: 'post',
        data:users.value
      }).then(res => {
        getList()
        users.value=""
        dialogVisible.value=false
        ElMessage({
          type: 'success',
          message: '保存成功',
        })
      })
    }else {
      ElMessage({
        type: 'error',
        message: '验证没通过！',
      })
    }
  })
}
// 修改
const edit=(item)=>{
  // 深拷贝
  const newObj = Object.assign({}, item)
  dialogVisible.value=true
  users.value=newObj
}
// 获取已选择的数据
const handleSelectionChange=(val)=>{
  multipleSelection.value=val;
}
// 批量删除
const delBatch=()=>{
// 获取需要删除的数据的id--数组
  let ids=multipleSelection.value.map(item =>item.id);
  // console.log("ids",ids)
  if (ids.length!=0){
    ElMessageBox.confirm(
        '是否确认删除？',
        'Warning',
        {
          confirmButtonText: 'OK',
          cancelButtonText: 'Cancel',
          type: 'warning',
        }
    ).then(()=>{
      request({
        url: '/usermanage/delBatch',
        method: 'post',
        data: ids
      }).then(res => {
        ElMessage({
          type: 'success',
          message: '删除成功！',
        })
        getList()
        // console.log(res)
      })
    }).catch(() => {
      ElMessage({
        type: 'warning',
        message: '取消删除！',
      })
    })
  }else {
    ElMessage({
      type: 'error',
      message:'请先选择数据！'
    })
  }
}
// 导入成功后重新加载数据
const handleExcelImportSuccess=()=>{
  getList();
  ElMessage({
    type: 'success',
    message: '上传成功！',
  })
}

// 导出数据
const exp=()=>{
  window.open(`http://localhost:8080/usermanage/export`)
}
// 图片上传后的钩子函数-->回写图片地址
const handleAvatarSuccess=(res)=>{
  console.log(res)
  users.value.avatar=res.msg
}
// 图片上传之前的钩子--》判断文件是否是图片、图片大小是否符合要求
const beforeAvatarUpload=(rawFile)=>{
  console.log("类型：",rawFile.type)
  console.log(rawFile.type!=='image/png');
  // 获取文件后缀
  let testmsg = rawFile.name.substring(rawFile.name.lastIndexOf('.') + 1)
  console.log("后缀：",testmsg)
  // 判断文件类型
  const extension =testmsg === 'jpg' || testmsg === 'png' || testmsg === 'jpeg' || testmsg === 'gif'
  if (!extension){//不符合要求
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

//生命周期函数
onMounted(() => {
  getList();
})
</script>

<template>
  <el-form-item label="用户" style="margin-top: 20px">
    <el-input v-model="username" style="width: 200px" placeholder="请输入名称"></el-input>
    <el-button type="primary" :icon="Search" style="margin-left: 10px" @click="search">搜索</el-button>
    <el-button type="primary" :icon="Plus" style="margin-left: 10px" @click="add">新增</el-button>
    <el-button type="danger" :icon="Delete" style="margin-left: 10px" @click="delBatch">批量删除</el-button>
    <el-upload :action="'http://' + 'localhost:8080/usermanage/import'" :show-file-list="false" accept="xlsx"
               :on-success="handleExcelImportSuccess" style="display: inline-block">
      <el-button  type="primary" style="margin-left: 10px" :icon="UploadFilled">导入</el-button>

    </el-upload>
    <el-button  type="warning" style="margin-left: 10px" :icon="Download"  @click="exp">导出</el-button>
  </el-form-item>

  <!--  数据表格-->
  <el-table :data="list" width="100%" stripe   @selection-change="handleSelectionChange">
    <el-table-column type="selection"  width="55"/>
    <el-table-column type="index" label="序号" width="50" :index="1"></el-table-column>
    <el-table-column prop="name" label="名称"></el-table-column>
    <el-table-column label="头像" width="100">
      <template #default="scope">
        <el-image style="width: 100px; height: 80px; padding: 5px;" :src="scope.row.avatar" :zoom-rate="1.2"
                  :max-scale="7" :min-scale="0.2" :preview-src-list="[scope.row.avatar]" :preview-teleported="true" />
      </template>
    </el-table-column>
    <el-table-column prop="phone" label="电话号码" width="100" show-overflow-tooltip></el-table-column>
    <el-table-column prop="email" label="邮箱"></el-table-column>
    <el-table-column prop="role" label="角色"></el-table-column>
    <el-table-column prop="money" label="钱包" width="50"></el-table-column>
    <el-table-column prop="sex" label="性别"></el-table-column>
    <el-table-column prop="password" label="密码" ></el-table-column>
    <el-table-column prop="intro" label="个人描述"></el-table-column>
    <el-table-column prop="createtime" label="创建时间" ></el-table-column>
    <el-table-column label="操作" width="200" fixed="right">
      <template #default="scope">
        <el-button type="primary" :icon="Edit" @click="edit(scope.row)">修改</el-button>
        <el-button type="danger" :icon="Delete" @click="del(scope.row.id)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>

  <div style="padding: 10px">
    <el-pagination
        v-model:current-page="pageNum"
        v-model:page-size="pageSize"
        :page-sizes="[5,10,20, 30, 40]"
        size="large"
        background
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    />
  </div>
  <!--模态框-->
  <el-dialog
      v-model="dialogVisible"

      :title="users.id ? '修改信息' : '新增用户'"
      width="500"
      ref="userForm"
  >
    <el-form :model="users" :rules="rules" ref="userForm" >
      <!--      图片上传-->
      <el-form-item label="头像">
        <el-upload
            class="avatar-uploader"
            :action="'http://' + 'localhost:8080/files/upload'"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
        >
          <img v-if="users.avatar" :src="users.avatar" class="avatar" style="width: 300px"/>
          <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
        </el-upload>
      </el-form-item>
      <el-form-item label="名称"  prop="name">
        <el-input v-model="users.name" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="电话号码"  prop="phone">
        <el-input v-model="users.phone" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="邮箱"  prop="email">
        <el-input v-model="users.email" autocomplete="off"/>
      </el-form-item>
      <!--  单选框-->
      <el-form-item label="性别" prop="sex">
        <el-radio-group v-model="users.sex">
          <el-radio value="男">男</el-radio>
          <el-radio value="女">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="钱包"  prop="money">
        <el-input v-model="users.money" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="密码"  prop="password">
        <el-input v-model="users.password" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="角色"  prop="role">
        <el-select v-model="users.role" placeholder="请选择角色">
          <el-option label="管理员" value="管理员"/>
          <el-option label="用户" value="用户"/>
        </el-select>
      </el-form-item>
      <el-form-item label="个人描述"  prop="intro">
        <el-input v-model="users.intro" autocomplete="off"/>
      </el-form-item>

    </el-form>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save">
          提交
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<style scoped>

</style>