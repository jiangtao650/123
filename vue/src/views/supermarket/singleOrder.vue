<template>
<div class="app-container">
  <div class="filter-container">
    <body>
      <el-form :inline="true">
        <el-form-item>
          <router-link :to="{name:'maetS商城'}">
            <el-button type="primary" icon="plus">返回商城</el-button>
          </router-link>
        </el-form-item>
        <el-form-item>
          <span style="text-align: center;font-size:24px;">订单信息</span>
        </el-form-item>
        <el-divider></el-divider>
      </el-form>
      <el-divider></el-divider>
      <el-form
        ref="singleOrder"
        class="middle-space"
        :model="singleOrder"
        label-position="center"
        style="width: 1000px; margin-left:150px;margin-top:50px"
        size="medium"
      >
        <el-form-item label="下单时间：">
          <span>{{singleOrder.createdTime}}</span>
        </el-form-item>
        <el-form-item label="订单号：">
          <span>{{orderNumbers}}</span>
        </el-form-item>
        <el-form-item label="收货人：">
          <span>{{singleOrder.customerName}}</span>
        </el-form-item>
        <el-form-item label="订单金额：￥">
          <span>{{singleOrder.totalPrice}}</span>
        </el-form-item>
        <el-form-item label="支付方式：">
          <span v-if="singleOrder.paymentWay==0">线上支付</span>
          <span v-if="singleOrder.paymentWay==1">货到付款</span>
        </el-form-item>
      </el-form>
      <el-table
        highlight-current-row
        :data="list"
        fit
        v-loading.body="listLoading"
        element-loading-text="拼命加载中"
        style="width: 100%"
      >
        <el-table-column align="center" prop="pictureUrl" style="width: 150px;">
          <template slot-scope="scope">
            <img :src="scope.row.pictureUrl" width="100px" height="150px" />
          </template>
        </el-table-column>
        <el-table-column align="center" prop="goodsName" style="width: 60px;"></el-table-column>
        <el-table-column align="center" prop="customerName" style="width: 30px;"></el-table-column>
        <el-table-column align="center" prop="purchaseNumbers" style="width: 30px;"></el-table-column>
        <el-table-column align="center" style="width: 60px;">
          <template slot-scope="scope">
            <span width="100px" height="150px">￥{{scope.row.purchaseNumbers*scope.row.goodsPrice}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="goodsName" style="width: 60px;">
          <template slot-scope="scope">
            <el-button
              type="danger"
              @click="deleteCommodity(scope.$index)"
              size="mini"
              round
              style="margin:10px"
            >删除</el-button>
            <el-button
              type="danger"
              @click="moveAttention(scope.$index)"
              size="mini"
              round
              style="margin:10px"
            >评价</el-button>
            <el-button
              type="danger"
              @click="moveAttention(scope.$index)"
              size="mini"
              round
              style="margin:10px"
            >订单详情</el-button>
            <el-button
              type="danger"
              @click="moveAttention(scope.$index)"
              size="mini"
              round
              style="margin:10px"
            >立即购买</el-button>
          </template>
        </el-table-column>
      </el-table>
    </body>
  </div>
</div>
</template>
<script>
export default {
  data() {
    return {
      orderNumbers: "",
      list: [], //表格的数据
      listLoading: false, //数据加载等待动画
      isIndeterminate: false,
      singleOrder: {
        goodsId: "",
        goodsName: "",
        goodsPrice: "",
        goodsSaleStatus: "",
        pictureUrl: "",
        goodsStyle: "",
        purchaseNumbers: "",
        subTotal: "",
        customerName: "",
        customerPhoneNumber: "",
        customerAddress: "",
        createdTime: "",
        totalPrice: "",
        paymentWay: "",
        customerComment: "",
        businessName: ""
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.listLoading = true;
      this.orderNumbers = this.$route.query.orderNumbers;
      this.singleOrder.totalPrice = this.$route.query.totalPrice;
      this.api({
        url: "/orderManage/singleOrder",
        method: "get",
        params: this.orderNumbers
      }).then(data => {
        this.listLoading = false;
        this.list = data;
        this.singleOrder.createdTime = data[0].createdTime;
        this.singleOrder.customerName = data[0].customerName;
        this.singleOrder.paymentWay = data[0].paymentWay;
      });
    },
    moveAttention() {},
    deleteCommodity($index) {}
  }
};
</script>
<style scoped>
/* ==========================结算===================== */
#account-box {
  width: 850px;
  height: 200px;
  margin-left: auto;
  margin-right: auto;
  position: relative;
  /* background-color: sandybrown; */
}

#account-box button:first-child {
  color: darkgray;
  border: 0;
  outline: 0;
  background-color: rgba(0, 0, 0, 0);
  margin-top: 15px;
  font-size: 17px;
}

#account-box button:last-child {
  color: white;
  border: 0;
  outline: 0;
  background-color: red;
  width: 120px;
  height: 45px;
  font-size: 22px;
  position: absolute;
  right: 0;
  top: 15px;
}

#account-box > div {
  position: absolute;
  right: 220px;
  top: 15px;
}

#count {
  color: red;
}

#total {
  color: red;
  font-weight: 900;
  font-size: 20px;
}
</style>
