<script setup>
import {onMounted, ref} from "vue";
import request from "../../axios/request.js";
import {ElMessage, ElMessageBox} from "element-plus";
import {
  Delete,
  Edit,
  Search,
  Plus,
  Download,
  UploadFilled, Message
} from "@element-plus/icons-vue";

const list = ref([])
// 查询条件
const content  = ref("")
// 商品的信息
const comment = ref({
  id: "",
  fid: "",
  level: "",
  username: "",
  content: "",
  goodsid: "",
  picurl: "",
  score: "",
  createtime: ""
})

// 分页参数
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)

// 保存选中的数据
const multipleSelection = ref([])
// 查询商品信息
const getList = () => {
  request({
    url: '/commentmanage/page',
    method: 'get',
    params: {
      page: pageNum.value,
      limit: pageSize.value,
      Content: content.value,
    }
  }).then((res) => {
    // console.log(res)
    list.value = res.data.data;
    total.value = res.data.count;
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
//详情
const detail =()=>{

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
          url: '/commentmanage/del',
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
// 获取已选择的数据
const handleSelectionChange = (val) => {
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
        url: '/commentmanage/delBatch',
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
// 导入成功后重新加载数据
const handleExcelImportSuccess = () => {
  getList();
  ElMessage({
    type: 'success',
    message: '上传成功！',
  })
}

// 导出数据
const exp = () => {
  window.open(`http://localhost:8080/commentmanage/export`)
}


// excle导入前验证-->文件类别、文件大小
const cheackExcle = (rawFile) => {
  // console.log(rawFile.size)
  // 获取文件后缀
  let testmsg = rawFile.name.substring(rawFile.name.lastIndexOf('.') + 1)
  if (testmsg !== 'xlsx') {
    ElMessage.error('只能上传后缀为.xlsx的excle文件')
    return false
  } else if (rawFile.size / 1024 / 1024 > 2) {
    ElMessage.error('文件大小不能超过2M')
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
  <el-form-item label="名称" style="margin-top: 20px">
    <el-input v-model="content " style="width: 200px" placeholder="请输入查询的评论内容"></el-input>
    <el-button type="primary" :icon="Search" style="margin-left: 10px" @click="search">搜索</el-button>
    <el-button type="danger" :icon="Delete" style="margin-left: 10px" @click="delBatch">批量删除</el-button>
    <el-upload :action="'http://' + 'localhost:8080/commentmanage/import'"
               :show-file-list="false"
               accept="xlsx"
               :before-upload="cheackExcle"
               :on-success="handleExcelImportSuccess"
               style="display: inline-block">
      <el-button type="primary" style="margin-left: 10px" :icon="UploadFilled">导入</el-button>
    </el-upload>
    <el-button type="warning" style="margin-left: 10px" :icon="Download" @click="exp">导出</el-button>
  </el-form-item>
  <!--  //表格-->
  <el-table :data="list" width="100%" stripe @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="55"/>
    <el-table-column type="index" label="序号" width="80" :index="1"></el-table-column>
    <el-table-column prop="fid" label="父ID"></el-table-column>
    <el-table-column prop="level" label="层级"></el-table-column>
    <el-table-column prop="username" label="用户名" width="100" show-overflow-tooltip></el-table-column>
    <el-table-column prop="content" label="评论内容"></el-table-column>
    <el-table-column prop="goodsid" label="商品id"></el-table-column>
    <el-table-column label="图片" width="120">
      <template #default="scope">
        <el-image style="width: 100px; height: 80px; padding: 5px;" :src="scope.row.picurl" :zoom-rate="1.2"
                  :max-scale="7" :min-scale="0.2" :preview-src-list="[scope.row.picurl]" :preview-teleported="true"/>
      </template>
    </el-table-column>
    <el-table-column prop="score" label="评分"></el-table-column>
    <el-table-column prop="createtime" label="发布时间"></el-table-column>
    <el-table-column label="操作" width="200" fixed="right">
      <template #default="scope">
        <el-button type="danger" :icon="Delete" @click="del(scope.row.id)">删除</el-button>
        <el-button type="warning" :icon="Message" @click="detail(scope.row)">详情</el-button>
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