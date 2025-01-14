<template>
  <div class="carousel-container">
    <el-carousel :interval="4000" arrow="always" style="height: 100%">
      <el-carousel-item v-for="(item, index) in items" :key="index" style="height: 400px">
<!--        <h5>{{ item.title }}</h5>-->
        <img :src="item.image" class="carousel-image" />
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script>
export default {
  data() {
    return {
      items: [
        {
          // title: 'Slide 1',
          image: 'https://www.scent-e.com/Uploads/201904/5ca570ec2fb2f.jpg'
        },
        {
          // title: 'Slide 2',
          image: 'https://img.zcool.cn/community/01hr9skcvdbadsxyxzlehv3535.jpg?x-oss-process=image/auto-orient,1/resize,m_lfit,w_1280,limit_1/sharpen,100'
        },
        {
          // title: 'Slide 3',
          image: 'https://img.zcool.cn/community/018d145bc99417a801213dead127a8.png?x-oss-process=image/auto-orient,1/resize,m_lfit,w_1280,limit_1/sharpen,100/quality,q_100'
        }
      ]
    };
  }
};
</script>

<style scoped>
.carousel-container {
  width: 924px;
  margin: auto;
}

.carousel-image {
  width: 100%;
  height: 397px;
  object-fit: cover;
}
</style>
