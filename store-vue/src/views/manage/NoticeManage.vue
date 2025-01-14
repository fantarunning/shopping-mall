<script setup>
import {onMounted, ref} from "vue";
import request from "@/axios/request.js";
import {ElMessage, ElMessageBox} from "element-plus";
import {
  Delete,
  Edit,
  Message,
  Search,
  Plus,
  House,
  User,
  Promotion,
  Goods,
  Download,
  UploadFilled
} from "@element-plus/icons-vue";

const list = ref([])
// 查询条件
const title = ref("")
// 模态框是否显示
const dialogVisible = ref(false);
// 公告的信息
const notice = ref({
  id: "",
  title: "",
  content: "",
  time:"",
  noticeType:""
})

// 表单校验规则
const rules = {
  title: [
    {required: true, message: "请输入标题", trigger: "blur"}
  ],
  content: [
    {required: true, message: "请输入内容", trigger: "blur"}
  ],
  noticeType: [
    {required: true, message: "请输入广告类型", trigger: "blur"}
  ],
}

// 表单引用
const noticeForm = ref();
// 分页参数
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)

// 保存选中的数据
const multipleSelection = ref([])

// 查询公告信息
const getList = () => {
  request({
    url: '/noticemanage/page',
    method: 'get',
    params: {
      page: pageNum.value,
      limit: pageSize.value,
      title: title.value,
    }
  }).then((res) => {
    // console.log(res)
    list.value = res.data.data;
    total.value = res.data.count;
    // name.value=""
  })
}

// 改变每页数据量
const handleSizeChange = (pSize) => {
  // console.log(pSize+`items per page`)
  pageSize.value = pSize;
  getList()
}
// 改变当前页
const handleCurrentChange = (pNum) => {
  // console.log(`current page: `+pNum)
  pageNum.value = pNum;
  getList()
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
          url: '/noticemanage/del',
          method: 'get',
          params: {id: id}
        }).then(res => {
          if (res.data.code == 200) {
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

// 搜索
const search = () => {
  getList()
}

// 新增
const add = () => {
  dialogVisible.value = true
}
// 数据提交
const save = () => {
//   校验表单是否合法
  noticeForm.value.validate((flag, err) => {
    if (flag) {//验证通过
      request({
        url: '/noticemanage/save',
        method: 'post',
        data: notice.value
      }).then(res => {
        getList()
        notice.value = {}
        dialogVisible.value = false
        ElMessage({
          type: 'success',
          message: '保存成功',
        })
      })
    } else {
      ElMessage({
        type: 'error',
        message: '验证没通过！',
      })
    }
  })
}
// 修改
const edit = (item) => {
  // 深拷贝
  const newObj = Object.assign({}, item)
  dialogVisible.value = true
  notice.value = newObj
}
// 获取已选择的数据
const handleSelectionChange = (val) => {
  // console.log(val)
  multipleSelection.value = val;
}
// 批量删除
const delBatch = () => {
// 获取需要删除的数据的id--数组
  let ids = multipleSelection.value.map(item => item.id);
  // console.log("ids",ids)
  if (ids.length != 0) {
    ElMessageBox.confirm(
        '是否确认删除？',
        'Warning',
        {
          confirmButtonText: 'OK',
          cancelButtonText: 'Cancel',
          type: 'warning',
        }
    ).then(() => {
      request({
        url: '/noticemanage/delBatch',
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
  } else {
    ElMessage({
      type: 'error',
      message: '请先选择数据！'
    })
  }
}

//生命周期函数
onMounted(() => {
  getList();
})
</script>

<template>
  <!--  搜索、新增、批量删除-->
  <el-form-item label="标题" style="margin-top: 20px">
    <el-input v-model="title" style="width: 200px" placeholder="请输入标题"></el-input>
    <el-button type="primary" :icon="Search" style="margin-left: 10px" @click="search">搜索</el-button>
    <el-button type="primary" :icon="Plus" style="margin-left: 10px" @click="add">新增</el-button>
    <el-button type="danger" :icon="Delete" style="margin-left: 10px" @click="delBatch">批量删除</el-button>

  </el-form-item>
  <!--  数据表格-->
  <el-table :data="list" width="100%" stripe @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="55"/>
    <el-table-column type="index" label="序号" width="80" :index="1"></el-table-column>
    <el-table-column prop="title" label="标题"></el-table-column>
    <el-table-column prop="content" label="内容" width="220" show-overflow-tooltip></el-table-column>
    <el-table-column prop="time" label="发布时间" width="200" show-overflow-tooltip ></el-table-column>
    <el-table-column prop="noticeType" label="公告类型" width="100" show-overflow-tooltip></el-table-column>
    <el-table-column label="操作" width="300" fixed="right">
      <template #default="scope">
        <el-button type="primary" :icon="Edit" @click="edit(scope.row)">修改</el-button>
        <el-button type="danger" :icon="Delete" @click="del(scope.row.id)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
  <!--  数据分页-->
  <div style="padding: 10px">
    <el-pagination
        v-model:current-page="pageNum"
        v-model:page-size="pageSize"
        :page-sizes="[10, 20, 30, 40]"
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
      :title="notice.id ? '修改信息' : '新增信息'"
      width="600"
      ref="noticeForm">
    <el-form ref="noticeForm" :model="notice" :rules="rules" label-width="auto" style="padding: 20px">
      <el-form-item prop="title" label="公告标题">
        <el-input v-model="notice.title" placeholder="请输入公告标题"></el-input>
      </el-form-item>
      <el-form-item prop="content" label="公告内容">
        <el-input type="textarea" :rows="4" v-model="notice.content" placeholder="请输入公告内容"></el-input>
      </el-form-item>
      <el-form-item label="公告类型"  prop="noticeType">
        <el-select v-model="notice.noticeType" placeholder="请选择公告类型">
          <el-option label="招聘公告" value="招聘公告"/>
          <el-option label="香薰赛事" value="香薰赛事"/>
        </el-select>
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