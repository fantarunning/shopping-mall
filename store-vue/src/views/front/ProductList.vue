<script setup>
import {onMounted, ref} from "vue";
import request from "@/axios/request.js";
import {Search} from "@element-plus/icons-vue";
// 图片地址
const url=ref("/logo.png")
const list = ref([])
// 查询条件
const goodsname  = ref("")
// 响应式数据
const activeIndex = ref('1');

const products = ref({
  id: "",
  goodsname: "",
  price: "",
  intro: "",
  imgurl: "",
});
// 查询商品信息
const getList = () => {
  request({
    url: '/goodsmanage/goods',
    method: 'get',
    params: {
      goodsName: goodsname.value,
    }
  }).then((res) => {
    // console.log(res)
    list.value = res.data.data;
  })
}


//生命周期函数
onMounted(() => {
  getList();
})
</script>

<template>
  <el-container class="app-container">
    <!-- 主要内容区域 -->
    <el-main class="app-main">
      <!--    搜索框-->
      <el-col style="display: flex; justify-content: center; align-items: center; flex-direction: column;">
        <el-form-item style="width: 100%; max-width: 800px; text-align: center;">
          <el-image style="width: 90px; height: 100px;" :src="url"  />
          <el-input style="margin-left: 70px;width: 450px; height: 45px" placeholder="请输入搜索内容"></el-input>
          <el-button type="primary" :icon="Search" style="margin-left: 40px;height: 45px;width: 90px;" @click="search">搜索</el-button>
        </el-form-item>
      </el-col>
      <!-- 这里可以放置商品列表、详情等 -->
      <div class="product-list">
        <!-- 商品项示例 -->
        <div class="product-item" v-for="product in list" :key="product.id">
          <img :src="product.imgurl" alt="" class="product-image">
          <h3 class="product-name">{{ product.goodsname }}</h3>
          <p class="product-price">¥{{ product.price }}</p>
          <p class="product-description">{{ product.intro }}</p>
          <!-- 可以添加更多商品信息，如购买按钮等 -->
        </div>
        <!-- 商品项结束 -->
      </div>
    </el-main>
  </el-container>
</template>


<style>
.app-main {
  padding: 20px;
}

.product-list {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
}

.product-item {
  width: calc(33.333% - 20px); /* 根据需要调整宽度和间距 */
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  overflow: hidden;
  text-align: center;
}

.product-image {
  width: 100%;
  height: auto;
}

.product-name {
  margin: 10px 0;
  font-size: 16px;
  font-weight: bold;
}

.product-price {
  color: #f56c6c;
  font-size: 18px;
  font-weight: 900; /* 粗细 */
}

.product-description {
  color: #666;
  font-size: 12px;
}
</style>