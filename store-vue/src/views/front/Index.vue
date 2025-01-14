<script setup>
import { reactive, onMounted,ref} from 'vue'
import request from "@/axios/request.js";
import CarouselWithSwiper from '@/views/front/CarouselWithSwiper.vue';
import ProductList from "@/views/front/ProductList.vue";
import {Search} from "@element-plus/icons-vue";
// 图片地址
const url=ref("/logo.png")

const data = reactive({
  user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
  noticeData: []
})
// 查询公告信息
const getNoticeList = () => {
  request({
    url: '/noticemanage/page',
    method: 'get',
    params: {
      page: 1,
      limit: 4,
    }
  }).then((res) => {
    data.noticeData = res.data.data;
  })
}

// 商品数量
const total=ref(0)
// 将用户信息从浏览器缓存中取出
const user=ref(JSON.parse(localStorage.getItem('xm-user') || '{}'))
// 查询商品信息
const getList = () => {
  request({
    url: '/goodsmanage/page',
    method: 'get',
    params: {
      page: 1,
      limit: 10,
      goodsName: name.value,
    }
  }).then((res) => {
    total.value = res.data.count;
    console.log(total.value)
    // name.value=""
  })
}

//生命周期函数
onMounted(() => {
  getList();
  getNoticeList()
})
</script>

<template>

<!--    :gutter="20"设置网格布局中列与列之间的间距-->
  <el-row :gutter="20">
    <!--    公告信息-->
    <el-col :span="6">
      <el-card class="box-card">
      <div style="display: flex">
        <div class="card" style="flex: 50%; height: 460px;width: 200px">
          <div style="font-weight: bold; font-size: 18px; padding: 10px 0 30px 10px">系统公告</div>
          <el-timeline style="max-width: 600px">
            <el-timeline-item
                v-for="(item, index) in data.noticeData"
                :key="index"
                :timestamp="item.time"
            >
              {{ item.title }}
            </el-timeline-item>
          </el-timeline>
        </div>
        <div style="flex: 50%"></div>
      </div>
      </el-card>
    </el-col>
    <el-col :span="18">
      <!-- 轮播图 -->
      <el-col  >
        <el-card class="box-card">
          <div style="height: 50px;background-color:#b3c0d1 ; font-size: 16px; line-height: 50px;font-weight: 500;">
            &nbsp;&nbsp;&nbsp;&nbsp;热&nbsp;卖&nbsp;产&nbsp;品&nbsp;推&nbsp;荐&nbsp;！快来看看吧~
          </div>
          <CarouselWithSwiper class="carousel-height"/>
        </el-card>
      </el-col>
    </el-col>
  </el-row>
  <!-- 科普区域 -->
  <el-row :gutter="20" style="margin-top: 10px">
    <!--    科普信息-->
    <el-col :span="6">
      <el-card class="box-card">
        <div style="display: flex">
          <div class="card" style="flex: 50%;width: 200px">
            <div style="font-weight: bold; font-size: 18px; padding: 10px 0 30px 10px">香薰原料科普</div>
            <el-timeline style="max-width: 600px">
              <el-timeline-item
                  v-for="(item, index) in data.noticeData"
                  :key="index"
                  :timestamp="item.time"
              >
                {{ item.title }}
              </el-timeline-item>
            </el-timeline>
          </div>
          <div style="flex: 50%"></div>
        </div>
      </el-card>
    </el-col>
<!--    商品推荐-->
    <el-col :span="18">
      <el-card>
     <ProductList></ProductList>
      </el-card>
    </el-col>
  </el-row>
</template>

<style scoped>
.carousel-height {
  height: 400px;
}
</style>