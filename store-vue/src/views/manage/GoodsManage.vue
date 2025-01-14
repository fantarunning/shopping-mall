<script setup>
import {onMounted, ref} from "vue";
import request from "@/axios/request.js";
import {ElMessage, ElMessageBox} from "element-plus";
import {
  Delete,
  Edit,
  Search,
  Plus,
  Download,
  UploadFilled
} from "@element-plus/icons-vue";

const list = ref([])
// 查询条件
const goodsname  = ref("")
// 模态框是否显示
const dialogVisible = ref(false);
// 商品的信息
const goods = ref({
  id: "",
  goodsname: "",
  price: "",
  intro: "",
  amount: "",
  type: "",
  imgurl: "",
  remark: "未上架"
})
// 表单引用
const goodsForm = ref();
// 分页参数
const pageNum = ref(1)
const pageSize = ref(4)
const total = ref(0)

// 保存选中的数据
const multipleSelection = ref([])

// 表单校验规则
const rules = {
  goodsname: [
    {required: true, message: "请输入名称", trigger: "blur"}
  ],
  price: [
    {required: true, message: "请输入价格", trigger: "blur"}
  ],
  intro: [
    {required: true, message: "请输入简介", trigger: "blur"}
  ],
  amount: [
    {required: true, message: "请输入库存", trigger: "blur"}
  ],
  type: [
    {required: true, message: "请选择类别", trigger: "blur"}
  ],
  remark: [
    {required: true, message: "请选择状态", trigger: "blur"}
  ]
}

// 查询商品信息
const getList = () => {
  request({
    url: '/goodsmanage/page',
    method: 'get',
    params: {
      page: pageNum.value,
      limit: pageSize.value,
      goodsName: goodsname.value,
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
// 根据id删除
const del = (id) => {
  ElMessageBox.confirm(
      '是否确认删除？',
      'Warning',
      {confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'warning',
      }).then(() => {
        request({
          url: '/goodsmanage/del',
          method: 'get',
          params: {id: id}
        }).then(res => {
          if (res.data.code == 200) {
            ElMessage({
              type: 'success',
              message: '删除成功！',
            })} else {
            ElMessage({
              type: 'error',
              message: '删除失败！',
            })}getList()
        })}).catch(() => {
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
  goodsForm.value.validate((flag, err) => {
    if (flag) {//验证通过
      request({
        url: '/goodsmanage/save',
        method: 'post',
        data: goods.value
      }).then(res => {
        getList()
        goods.value = {}
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
  goods.value = newObj
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
        url: '/goodsmanage/delBatch',
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
  window.open(`http://localhost:8080/goodsmanage/export`)
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


// 图片上传之前的钩子--》判断文件是否是图片、图片大小是否符合要求
const beforeAvatarUpload = (rawFile) => {
  console.log("类型：", rawFile.type)
  console.log(rawFile.type !== 'image/png');
  // 获取文件后缀
  let testmsg = rawFile.name.substring(rawFile.name.lastIndexOf('.') + 1)
  console.log("后缀：", testmsg)
  // 判断文件类型
  const extension = testmsg === 'jpg' || testmsg === 'png'
      || testmsg === 'jpeg' || testmsg === 'gif'
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
  goods.value.imgurl = res.msg
}

//生命周期函数
onMounted(() => {
  getList();
})
</script>

<template>
  <el-form-item label="名称" style="margin-top: 20px">
    <el-input v-model="goodsname " style="width: 200px" placeholder="请输入商品名称"></el-input>
    <el-button type="primary" :icon="Search" style="margin-left: 10px" @click="search">搜索</el-button>
    <el-button type="primary" :icon="Plus" style="margin-left: 10px" @click="add">新增</el-button>
    <el-button type="danger" :icon="Delete" style="margin-left: 10px" @click="delBatch">批量删除</el-button>
    <el-upload :action="'http://' + 'localhost:8080/goodsmanage/import'"
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
    <!--  <el-table-column prop="id" label="ID"></el-table-column>-->
    <el-table-column prop="goodsname" label="商品名称"></el-table-column>
    <el-table-column prop="price" label="单价"></el-table-column>
    <el-table-column prop="intro" label="简介" width="100" show-overflow-tooltip></el-table-column>
    <el-table-column prop="amount" label="库存"></el-table-column>
    <el-table-column prop="type" label="类别"></el-table-column>
    <el-table-column prop="remark" label="状态"></el-table-column>
    <el-table-column label="图片" width="120">
      <template #default="scope">
        <el-image style="width: 100px; height: 80px; padding: 5px;" :src="scope.row.imgurl" :zoom-rate="1.2"
                  :max-scale="7" :min-scale="0.2" :preview-src-list="[scope.row.imgurl]" :preview-teleported="true"/>
      </template>
    </el-table-column>

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
        :page-sizes="[4,10, 20, 30, 40]"
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
      :title="goods.id ? '修改商品' : '新增商品'"
      width="500"
      ref="goodsForm">
    <el-form :model="goods" :rules="rules" ref="goodsForm" label-width="auto">
      <el-form-item label="商品名称" prop="goodsname">
        <el-input v-model="goods.goodsname" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="商品单价" prop="price">
        <el-input v-model="goods.price" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="商品简介" prop="intro">
        <el-input v-model="goods.intro" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="商品库存" prop="amount">
        <el-input v-model="goods.amount" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="商品类别" prop="type">
        <el-select v-model="goods.type" placeholder="请选择商品类别">
          <el-option label="液体香薰" value="液体香薰"/>
          <el-option label="固体香薰" value="固体香薰"/>
          <el-option label="燃体香薰" value="燃体香薰"/>
          <el-option label="工具" value="工具"/>
          <el-option label="原料" value="原料"/>
        </el-select>
      </el-form-item>
      <!--  单选框-->
      <el-form-item label="状态" prop="remark">
        <el-radio-group v-model="goods.remark">
          <el-radio value="已上架">已上架</el-radio>
          <el-radio value="未上架">未上架</el-radio>
          <el-radio value="已下架">已下架</el-radio>
        </el-radio-group>
      </el-form-item>
      <!--      图片上传-->
      <el-form-item label="商品图片">
        <el-upload
            class="avatar-uploader"
            :action="'http://' + 'localhost:8080/files/upload'"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
        >
          <img v-if="goods.imgurl" :src="goods.imgurl" class="avatar" style="width: 300px"/>
          <el-icon v-else class="avatar-uploader-icon">
            <Plus/>
          </el-icon>
        </el-upload>
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