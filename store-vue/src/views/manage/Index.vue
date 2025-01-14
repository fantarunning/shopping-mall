<script setup>
import { reactive, onMounted,ref} from 'vue'
import { Memo } from '@element-plus/icons-vue'
import * as echarts from 'echarts'
import request from "@/axios/request.js";

// 格式化日期时间
const formattedDate=()=>{
  const date = new Date();
  const year = date.getFullYear();
  const month = (date.getMonth() + 1).toString().padStart(2, '0');
  const day = date.getDate().toString().padStart(2, '0');
  const hour = date.getHours().toString().padStart(2, '0');
  const minute = date.getMinutes().toString().padStart(2, '0');
  const second = date.getSeconds().toString().padStart(2, '0');
  const formattedDate = `${year}-${month}-${day} ${hour}:${minute}:${second}`;
  return formattedDate;

}
let date=formattedDate();
const loginInfo=ref({
  loginTime: date,
  loginPlace: "云南-昆明"
})
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
    // console.log(res)
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
    // console.log(res)
    // list.value = res.data.data;
    total.value = res.data.count;
    console.log(total.value)
    // name.value=""
  })
}
// 图表1：月度销售额
const initCharts1 = () => {
  const myChart = echarts.init(document.getElementById('salesVolume'))
  myChart.setOption({
    color: ['#1493fa'],
    title: { text: '2024年月度销售额' },
    xAxis: {
      data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
      name: '月份',
      axisLabel: {
        interval: 0
      },
    },
    yAxis: {
      name: '单位（千万元）',
    },
    grid: {
      left: '3%',
      right: '8%',
      bottom: '5%',
      containLabel: true,
    },
    legend: {},
    series: [
      {
        data: [6, 7, 8.5, 8, 9, 10, 13, 12, 10, 16, 15, 14],
        type: 'line',
        name: '销售额',
        smooth: true,
        label: {
          show: true,
          position: 'top',
        }
      }
    ]
  })
  // 图表自适应大小
  window.onresize = () => {
    myChart.resize()
  }
}
// 图表2：2024年订单数量
const initCharts2 = () => {
  const myChart = echarts.init(document.getElementById('orderQuantity'))
  myChart.setOption({
    title: { text: '2024年订单数量' },
    color: ['#1493fa'],
    grid: {
      left: '3%',
      right: '8%',
      bottom: '3%',
      containLabel: true,
    },
    xAxis: {
      type: 'category',
      data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
      name: '月份',
      // 类目轴中在 boundaryGap 为 true 的时候有效，可以保证刻度线和标签对齐
      axisTick: {
        alignWithLabel: true,
      },
      axisLabel: {
        interval: 0,rotate: 45 // 设置刻度标签旋转角度为45度
      },
    },
    legend: {},
    yAxis: {
      name: '单位（个）',
    },
    series: [
      {
        data: [400, 450, 300, 230, 250, 300, 400, 350, 160, 350, 380, 400],
        type: 'bar',
        barWidth: '60%',
        name: '订单总数',
        label: {
          show: true,
          position: 'top',
        }
      }
    ]
  })
  // 图表自适应大小
  window.onresize = () => {
    myChart.resize()
  }
}

//生命周期函数
onMounted(() => {
  getList();
  getNoticeList()
  // 初始化图表
  initCharts1()
  initCharts2()
})
</script>

<template>

  <el-row :gutter="20">
    <!--    登录信息-->
    <el-col :span="6">
      <el-card class="box-card">
      <div style="display: flex">
        <div class="card" style="flex: 50%; height: 400px;width: 200px">
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
    <!-- 单月统计信息展示 -->
    <el-col :span="18" >
      <el-card class="box-card">
        <template #header>
          <div class="card-header">
            6月统计信息
          </div>
        </template>
        <div class="info" style="height: 340px">
          <el-row :gutter="24">
            <!-- 商品数量 -->
            <el-col :span="8">
              <div class="card-container">
                <div class="card-left-container" style="background-color: #EEAD0E;">
                  <el-icon :size="90">
                    <Memo />
                  </el-icon>
                </div>
                <div class="card-right-container">
                  <p>商品数量(个)</p>
                  <p class="number">{{ total }}</p>
                </div>
              </div>
            </el-col>
            <!-- 商品分类数量 -->
            <el-col :span="8">
              <div class="card-container">
                <div class="card-left-container" style="background-color: #AB82FF;">
                  <el-icon :size="90">
                    <Memo />
                  </el-icon>
                </div>
                <div class="card-right-container">
                  <p>商品分类数量(个)</p>
                  <p class="number">20</p>
                </div>
              </div>
            </el-col>
            <!-- 用户访问次数 -->
            <el-col :span="8">
              <div class="card-container">
                <div class="card-left-container" style=" background-color: #63B8FF;">
                  <el-icon :size="90">
                    <Memo />
                  </el-icon>
                </div>
                <div class="card-right-container">
                  <p>用户访问次数(次)</p>
                  <p class="number">121</p>
                </div>
              </div>
            </el-col>
            <!-- 订单数量 -->
            <el-col :span="8">
              <div class="card-container">
                <div class="card-left-container" style="background-color: #EEDAFF;">
                  <el-icon :size="90">
                    <Memo />
                  </el-icon>
                </div>
                <div class="card-right-container">
                  <p>用户成交订单数(个)</p>
                  <p class="number">{{ total }}</p>
                </div>
              </div>
            </el-col>
            <!-- 评论数目 -->
            <el-col :span="8">
              <div class="card-container">
                <div class="card-left-container" style="background-color: #AABBFF;">
                  <el-icon :size="90">
                    <Memo />
                  </el-icon>
                </div>
                <div class="card-right-container">
                  <p>商品获评数量(条)</p>
                  <p class="number">20</p>
                </div>
              </div>
            </el-col>
            <!-- 评价平均评分 -->
            <el-col :span="8">
              <div class="card-container">
                <div class="card-left-container" style=" background-color: #BFBBDF;">
                  <el-icon :size="90">
                    <Memo />
                  </el-icon>
                </div>
                <div class="card-right-container">
                  <p>商品平均评分(分)</p>
                  <p class="number" >4.9</p>
                </div>
              </div>
            </el-col>
          </el-row>
        </div>
      </el-card>
    </el-col>

  </el-row>
  <!-- 图表区域 -->
  <el-row :gutter="20" style="margin-top: 10px">
    <el-col :span="12">
      <!-- 通过折线图展示2024年月度销售额 -->
      <el-card class="box-card">
        <div id="salesVolume" style="width: auto; height:400px;">

        </div>
      </el-card>
    </el-col>
    <el-col :span="12">
      <!-- 通过柱状图展示2024年订单数量 -->
      <el-card class="box-card">
        <div id="orderQuantity" style="width: auto; height:400px;">

        </div>
      </el-card>
    </el-col>
  </el-row>
</template>

<style scoped>
.card-right-container {
  display: flex;
  justify-content: space-between; /* 使子元素之间的空间均匀分布 */
  align-items: center; /* 垂直方向上居中对齐 */
}

.number {
  margin-left: 10px;
}
.card-container{
  margin-top: 15px;
}
</style>