<script setup>
import {onMounted, ref} from "vue";
import request from "@/axios/request.js";
import {ElMessage, ElMessageBox} from "element-plus";
import {Delete, Edit, Search, Plus,Download,UploadFilled} from "@element-plus/icons-vue";


const list = ref([])
//订单信息
const orders = ref({
  id: "",
  picture: "",
  orderNum: "",
  goodsname: "",
  type: "",
  price: "",
  number:"",
  priceAll:"",
  status:"",
  people:"",
  createTime:""
})
// 查询条件
const orderNum=ref("")
// 模态框是否显示
const dialogVisible=ref(false);
// 分页参数
const pageNum=ref(1)
const pageSize=ref(5)
const total=ref(0)
// 表单校验规则
const rules = {
  orderNum: [
    {required: true, message: "请输入订单号", trigger: "blur"}
  ],
  price: [
    {required: true, message: "请输入价格", trigger: "blur"}
  ],
  goodsname: [
    {required: true, message: "请输入商品名称", trigger: "blur"}
  ],
  type: [
    {required: true, message: "请输入类别", trigger: "blur"}
  ],
  number: [
    {required: true, message: "请输入购买数量", trigger: "blur"}
  ],
  status: [
    {required: true, message: "请选择状态", trigger: "blur"}
  ],
  people: [
    {required: true, message: "请填写购买人", trigger: "blur"}
  ]
}
// 表单引用
const orderForm=ref();
// 保存选中的数据
const multipleSelection=ref([])

// 查询订单信息
const getList = () => {
  request({
    url: '/ordermanage/page',
    method: 'get',
    params: {
      page: pageNum.value,
      limit: pageSize.value,
      orderNum: orderNum.value,
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
            url: '/ordermanage/del',
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
  orderForm.value.validate((flag,err)=>{
    if (flag){//验证通过
      request({
        url: '/ordermanage/save',
        method: 'post',
        data:orders.value
      }).then(res => {
        getList()
        orders.value=""
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
  orders.value=newObj
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
          url: '/ordermanage/delBatch',
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
    window.open(`http://localhost:8080/ordermanage/export`)
  }
// 图片上传后的钩子函数-->回写图片地址
  const handleAvatarSuccess=(res)=>{
    console.log(res)
    orders.value.picture=res.msg
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
  <el-form-item label="订单编号" style="margin-top: 20px">
    <el-input v-model="orderNum" style="width: 200px" placeholder="请输入订单编号"></el-input>
    <el-button type="primary" :icon="Search" style="margin-left: 10px" @click="search">搜索</el-button>
    <el-button type="primary" :icon="Plus" style="margin-left: 10px" @click="add">新增</el-button>
    <el-button type="danger" :icon="Delete" style="margin-left: 10px" @click="delBatch">批量删除</el-button>
    <el-upload :action="'http://' + 'localhost:8080/ordermanage/import'" :show-file-list="false" accept="xlsx"
               :on-success="handleExcelImportSuccess" style="display: inline-block">
      <el-button  type="primary" style="margin-left: 10px" :icon="UploadFilled">导入</el-button>

    </el-upload>
    <el-button  type="warning" style="margin-left: 10px" :icon="Download"  @click="exp">导出</el-button>
  </el-form-item>

  <!--  数据表格-->
  <el-table :data="list" width="100%" stripe   @selection-change="handleSelectionChange">
    <el-table-column type="selection"  width="55"/>
    <el-table-column type="index" label="序号" width="50" :index="1"></el-table-column>
    <el-table-column label="商品图片" width="100">
      <template #default="scope">
        <el-image style="width: 100px; height: 80px; padding: 5px;" :src="scope.row.picture" :zoom-rate="1.2" :max-scale="7" :min-scale="0.2" :preview-src-list="[scope.row.picture]" :preview-teleported="true" />
      </template>
    </el-table-column>
    <el-table-column prop="orderNum" label="订单编号"></el-table-column>
    <el-table-column prop="goodsname" label="商品名称" width="100" show-overflow-tooltip></el-table-column>
    <el-table-column prop="type" label="商品类型"></el-table-column>
    <el-table-column prop="price" label="单价"></el-table-column>
    <el-table-column prop="number" label="数量" width="50"></el-table-column>
    <el-table-column prop="priceAll" label="总价"></el-table-column>
    <el-table-column prop="status" label="状态" ></el-table-column>
    <el-table-column prop="people" label="购买人"></el-table-column>
    <el-table-column prop="createTime" label="订单创建时间" ></el-table-column>
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

      :title="orders.id ? '修改商品' : '新增商品'"
      width="500"
      ref="orderForm"
  >
    <el-form :model="orders" :rules="rules" ref="orderForm" >
      <!--      图片上传-->
      <el-form-item label="商品图片">
        <el-upload
            class="avatar-uploader"
            :action="'http://' + 'localhost:8080/files/upload'"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
        >
          <img v-if="orders.picture" :src="orders.picture" class="picture" style="width: 300px"/>
          <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
        </el-upload>
      </el-form-item>
      <el-form-item label="订单编号"  prop="orderNum">
        <el-input v-model="orders.orderNum" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="商品名称"  prop="goodsname">
        <el-input v-model="orders.goodsname" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="商品类别"  prop="type">
        <el-select v-model="orders.type" placeholder="请选择商品类别">
          <el-option label="固体香薰" value="固体香薰"/>
          <el-option label="液体香薰" value="液体香薰"/>
          <el-option label="燃体香薰" value="燃体香薰"/>
          <el-option label="工具" value="工具"/>
          <el-option label="原料" value="原料"/>
        </el-select>
      </el-form-item>
      <el-form-item label="商品单价"  prop="price">
        <el-input v-model="orders.price" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="购买数量"  prop="number">
        <el-input v-model="orders.number" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="总价"  prop="priceAll">
        <el-input v-model="orders.priceAll" autocomplete="off" />
      </el-form-item>
      <el-form-item label="订单状态"  prop="status">
        <el-select v-model="orders.status" placeholder="请选择订单状态">
          <el-option label="已支付" value="已支付"/>
          <el-option label="未支付" value="未支付"/>
          <el-option label="已发货" value="已发货"/>
          <el-option label="未发货" value="未发货"/>
          <el-option label="已完成" value="已完成"/>
          <el-option label="已取消" value="已取消"/>
        </el-select>
      </el-form-item>
      <el-form-item label="购买人"  prop="people">
        <el-input v-model="orders.people" autocomplete="off"/>
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