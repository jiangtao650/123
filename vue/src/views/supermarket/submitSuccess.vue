<template>
  <div class="app-container">
    <el-form :inline="true">
      <el-form-item style="text-align: center;display:block;">
        <span style="text-align: center;display:block;font-size:24px;">订单处理结果</span>
      </el-form-item>
      <el-divider></el-divider>
      <el-steps :active="3" finish-status="success" simple>
        <el-step title="我的购物车"></el-step>
        <el-step title="填写核对订单信息"></el-step>
        <el-step title="成功提交订单！"></el-step>
      </el-steps>
    </el-form>
    <el-form
      class="middle-space"
      label-position="center"
      style="width: 1000px; margin-left:150px;margin-top:100px"
      size="medium"
    >
      <el-form-item>
        <span style="text-align: center;display:block;font-size:40px;font-weight:bold;">订单支付成功！</span>
      </el-form-item>
      <el-form-item>
        <span
          style="margin-top:50px;margin-bottom:40px;text-align: center;display:block;font-size:24px;color:red"
        >支付金额：￥{{this.package.totalPrice}}</span>
      </el-form-item>
      <el-form-item style="text-align: center;display:block;margin:100px;">
        <router-link :to="{name:'查看订单',query:{orderNumbers:this.package.orderNumbers,totalPrice:this.package.totalPrice}}">
          <el-button type="primary" icon="plus" >查看订单</el-button>
        </router-link>
        <router-link :to="{name:'maetS商城'}">
          <el-button type="primary" icon="plus">浏览商城</el-button>
        </router-link>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
export default {
  data() {
    return {
      totalCount: 0,
      listLoading: false, //数据加载等待动画
      package: {
        totalPrice: 0,
        orderNumbers: ""
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.package.orderNumbers = this.$route.query.orderNumbers;
      this.package.totalPrice = this.$route.query.totalPrice;
    }
  }
};
</script>
<style>
#count {
  color: red;
}

#total {
  color: red;
  font-weight: 900;
  font-size: 20px;
}
</style>