<script setup>
import { ref, onMounted } from 'vue';
import request from "@/axios/request.js";
import {useRoute} from "vue-router";
import {ElMessage} from 'element-plus';

const route = useRoute();
const good = ref({});
const count = ref(1);
const checkedStandard = ref('');
const amount = ref(0);

// 获取商品ID
const goodsId = route.params.id; // 假设商品ID通过路由参数传递

// 获取商品详情
const fetchGoodDetails = async () => {
  try {
    const response = await request.get(`/api/goods/${id}`);
    if (response.data.code === '200') { // 根据你的后端API响应格式调整
      good.value = response.data.data; // 假设后端返回的数据在data字段中
      amount.value = good.value.amount; // 设置库存
    } else {
      ElMessage.error(response.data.msg);
    }
  } catch (error) {
    console.error("Error fetching goods details:", error);
    ElMessage.error('获取商品详情失败');
  }
};

// 加入购物车
const addToCart = async () => {
  try {
    const response = await request.post('/api/cart', {
      goodsId: good.value.id,
      count: count.value,
      standard: checkedStandard.value
    });
    if (response.data.code === '200') {
      ElMessage.success('商品已加入购物车');
    } else {
      ElMessage.error(response.data.msg);
    }
  } catch (error) {
    console.error("Error adding to cart:", error);
    ElMessage.error('加入购物车失败');
  }
};

// 购买功能
const goToOrder = async () => {
  try {
    // 这里通常会有更多的逻辑，比如校验用户是否登录，获取用户信息等
    const response = await request.post('/api/order', {
      goodsId: good.value.id,
      count: count.value,
      standard: checkedStandard.value,
      // 可能还需要添加用户ID、收货地址等字段
    });
    if (response.data.code === '200') {
      ElMessage.success('已创建订单');
      // 可能需要跳转到订单详情页或其他页面
    } else {
      ElMessage.error(response.data.msg);
    }
  } catch (error) {
    console.error("Error creating order:", error);
    ElMessage.error('创建订单失败');
  }
};

// 在组件挂载时获取商品详情
onMounted(() => {
  fetchGoodDetails();
});

</script>
<template>
  <div class="main-box">
    <div>
      <div class="image-container">
        <img :src="good.imgurl" alt="商品图片"/>
      </div>
      <!--      商品名与描述-->
      <div>
        <span style="font-size: 22px"><strong>{{ good.goodsname }}</strong></span>
        <br/>
      </div>
      <div style="margin-top: 20px">
        <span style="font-size: 17px">{{ good.intro }}</span>
      </div>
      <!--      价格盒子-->
      <div class="price-box" v-if="good.discount < 1">
        <dl>
          <div>
            <dt>原价</dt>
            <dd style="text-decoration: line-through">
              <b>{{ good.price }}</b>元
            </dd>
          </div>
          <div>
            <dt>折扣</dt>
            <dd>{{ good.discount }}</dd>
          </div>
          <div>
            <dt>现价</dt>
            <dd style="color: red; font-size: 25px">
              <b>{{ (good.price * good.discount).toFixed(2) }}</b>元
            </dd>
          </div>
        </dl>
      </div>
      <div class="price-box" v-if="good.discount === 1">
        <dl>
          <div>
            <dt>价格</dt>
            <dd style="color: red; font-size: 25px">￥ <b>{{ good.price }}</b></dd>
          </div>
        </dl>
      </div>
      <!--      月销量-->
      <div style="margin-top: 20px">
        <span>月销量：</span>
        <span>{{ good.sales }}</span><br/>
        <span style="height: 40px" v-if="amount > 0">库存：{{ amount }}</span>
      </div>
      <!--      选择规格-->
      <div style="margin-top: 15px; height: 50px" v-if="good.type.length !== 0">
        <!-- el-radio-group单选按钮组-->
        <el-radio-group v-model="checkedStandard">
          <el-radio-button v-for="(standard, index) in good.type" :key="index" :label="standard.value">{{
              standard.value
            }}
          </el-radio-button>
        </el-radio-group>
      </div>
      <!--      选择数量-->
      <div style="margin-top: 20px">
        <el-input-number v-model="count" controls-position="right" :min="1" :max="amount"></el-input-number>
      </div>
      <!--      购买按钮组-->
      <div style="margin-top: 30px">
        <el-button type="success" @click="goToOrder" style="font-size: 22px">购买</el-button>
        <el-button type="primary" @click="addToCart" style="font-size: 22px">加入购物车</el-button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.main-box {
  width: 1060px;
  margin: 20px auto;
  padding: 30px;
  background-color: #ffffff;
  overflow: hidden;
}

.image {
  height: 100%;
  width: 350px;
}

.image-container {
  width: 420px; /* 设置容器的固定宽度 */
  height: 420px; /* 设置容器的固定高度 */
  overflow: hidden; /* 隐藏超出容器尺寸的部分 */
  text-align: center;
  margin-left: 80px;
  margin-top: 30px;
  display: inline-block;
  overflow: hidden;
}

.image-container img {
  display: block; /* 将图片显示为块级元素 */
  width: 100%; /* 图片宽度设置为100%，使其填满容器的宽度 */
  height: auto; /* 根据图片的宽高比，自动调整高度 */
  object-fit: cover; /* 将图片按比例缩放，以覆盖整个容器尺寸 */
}

.detail-box {
  width: 420px;
  display: inline-block;
  margin-left: 50px;
  overflow: hidden;
}

.price-box {
  background-color: #e9e9e9;
  border-radius: 5px;
  font: 12px/1.5 "Microsoft Yahei", tahoma, arial;
  padding-bottom: 1px;
  padding-top: 1px;
  margin-right: 20px;
  margin-top: 30px;
}

.price-box div {
  line-height: 20px;
  margin-left: 8px;
  margin-bottom: 5px;
}

.price-box dl dt {
  float: left;
  font-size: 14px;
  line-height: 20px;
}

.price-box dl dd {
  font-size: 18px;
  line-height: 20px;
}

.button {
  width: 130px;
  height: 45px;
  background-color: #96e2e0;
  color: #710a0a;
}

.standard {
  height: 30px;
  margin-right: 10px;
}
</style>