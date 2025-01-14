<script setup>
import { ref, onMounted } from 'vue';
import request from "@/axios/request.js";
import { ElMessage } from 'element-plus';

const cartItems = ref([]);
const total = ref(0);

const fetchCart = async () => {
  try {
    const response = await request.get('/cartitem/cart');
    cartItems.value = response.data;
    updateTotal();
  } catch (error) {
    console.error('Failed to fetch cart:', error);
    ElMessage.error('获取购物车失败');
  }
};

const addToCart = async (productId, quantity) => {
  try {
    const response = await request.post('/cart/add', { productId, quantity });
    await fetchCart();
    ElMessage.success('商品已添加到购物车');
  } catch (error) {
    console.error('Failed to add to cart:', error);
    ElMessage.error('添加商品到购物车失败');
  }
};

const removeFromCart = async (productId) => {
  try {
    await request.post('/cart/remove', { productId });
    await fetchCart();
    ElMessage.success('商品已从购物车移除');
  } catch (error) {
    console.error('Failed to remove from cart:', error);
    ElMessage.error('从购物车移除商品失败');
  }
};

const updateTotal = () => {
  total.value = cartItems.value.reduce((sum, item) => sum + item.price * item.quantity, 0);
};

const checkout = () => {
  // 实现结账逻辑，这里可以跳转到结账页面或执行结账操作
  ElMessage.info('当前示例中未实现结账逻辑');
};

onMounted(fetchCart);

// 示例：在其他地方调用 addToCart 方法
// addToCart(1, 2);
</script>

<template>
  <el-container>
    <el-header>
      <h2>购物车</h2>
    </el-header>
    <el-main>
      <el-card v-if="cartItems.length > 0">
        <el-table :data="cartItems" style="width: 100%">
          <el-table-column prop="name" label="商品名称" width="180"></el-table-column>
          <el-table-column prop="quantity" label="数量" width="120"></el-table-column>
          <el-table-column prop="price" label="价格" width="120"></el-table-column>
          <el-table-column label="操作" width="180">
            <template #default="scope">
              <el-button type="text" size="small" @click="removeFromCart(scope.row.id)">移除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-row>
          <el-col :span="12"><h3>总计: ¥{{ total }}</h3></el-col>
          <el-col :span="12" class="text-right">
            <el-button type="primary" @click="checkout">结账</el-button>
          </el-col>
        </el-row>
      </el-card>
      <el-card v-else>
        <p>购物车是空的</p>
      </el-card>
    </el-main>
  </el-container>
</template>

<style scoped>
.text-right {
  text-align: right;
}
</style>