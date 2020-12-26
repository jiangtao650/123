<template>
  <div class="app-container">
    <el-form :inline="true">
      <el-form-item>
        <router-link :to="{name:'购物车'}">
          <el-button type="primary" icon="plus">返回</el-button>
        </router-link>
      </el-form-item>
      <el-form-item>
        <span></span>
      </el-form-item>
      <el-divider></el-divider>
      <el-steps :active="1" finish-status="success" simple>
        <el-step title="我的购物车"></el-step>
        <el-step title="填写核对订单信息"></el-step>
        <el-step title="成功提交订单！"></el-step>
      </el-steps>
    </el-form>
    <el-form
      ref="settlement"
      class="middle-space"
      :model="settlement"
      label-position="center"
      style="width: 1000px; margin-left:150px;margin-top:50px"
      size="medium"
    >
      <el-form-item label="收货人信息：">
        <el-select v-model="settlement.customerAddress" style="width:400px" @change="changeSelect">
          <el-option
            v-for="(settlement,index) in options"
            :key="index"
            :label="settlement.customerName+' '+settlement.customerAddress+' '+settlement.customerPhoneNumber"
            :value="settlement.id"
            :disabled="settlement.disabled"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="支付方式：">
        <div>
          <el-radio
            v-model="radio"
            label="0"
            @change="onlinePayment"
            border
            style="margin-left:15px"
          >在线支付</el-radio>
          <el-radio v-model="radio" label="1" @change="arrivePayment" border>货到付款</el-radio>
        </div>
      </el-form-item>
    </el-form>
    <div class="orderList">
      <h3>送货清单</h3>
    </div>
    <el-divider></el-divider>
    <el-table :data="orderList" fit style="width:100%" highlight-current-row>
      <el-table-column align="center" prop="pictureUrl" style="width:150px;">
        <template slot-scope="scope">
          <img :src="scope.row.pictureUrl" width="100px" height="150px" />
        </template>
      </el-table-column>
      <el-table-column align="center" prop="goodsName" style="width:60px;"></el-table-column>
      <el-table-column align="center" prop="goodsStyle" style="width:60px;"></el-table-column>
      <el-table-column align="center" prop="goodsPrice" style="width:30px;">
        <template slot-scope="scope">
          <span>￥{{scope.row.goodsPrice}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="purchaseNumbers" style="width:30px"></el-table-column>
      <el-table-column align="center" prop="goodsSaleStatus" style="width:40px;">
        <template slot-scope="scope">
          <span v-if="scope.row.goodsSaleStatus==1">有货</span>
          <span v-if="scope.row.goodsSaleStatus==2">无货</span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="customerComment" style="width:100px;">
        <template slot-scope="scope">
          <el-input type="textarea" v-model="scope.row.customerComment" placeholder="建议留言前与商家沟通好！"></el-input>
        </template>
      </el-table-column>
    </el-table>
    <div slot="footer" class="dialog-footer" style="display: flex;justify-content:flex-end">
      <div id="account-box" style="display: inline;">
        <div>
          <span id="count" style="margin:10px">{{totalCount}}</span>件商品，总商品金额：
          <span id="totalprice" style="margin:10px">￥{{totalPrice}}</span>
          <br />
          <span id="discount" style="margin:10px">折扣：￥-0.00</span>
          <br />
          <span id="freight" style="margin:10px">运费：￥0.00</span>
        </div>
      </div>
    </div>
    <el-divider></el-divider>
    <div slot="footer" class="dialog-footer" style="display: flex;justify-content:flex-end">
      <div id="account-box" style="display: inline;">
        <div>
          <span id="toPayTotal">应付总额：</span>
          <span id="total">￥{{totalPrice}}</span>
          <br />
          <span id="address">寄送至：{{this.settlement.address}}</span>
          <br />
          <span id="recipients">收件人：{{this.settlement.customer}} {{this.settlement.phoneNumbers}}</span>
          <el-divider></el-divider>
          <el-button type="danger" @click="submitOrder">提交订单</el-button>
          <span></span>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      radio: "0",
      iconData: "",
      iconData1: "",
      select: "",
      contain: "",
      totalCount: 0,
      totalPrice: 0,
      customerBalance: 0,
      password: "",
      inputValue: "",
      listLoading: false, //数据加载等待动画
      orderList: {
        goodsId: "",
        goodsName: "",
        goodsPrice: "",
        goodsSaleStatus: "",
        pictureUrl: "",
        goodsStyle: "",
        purchaseNumbers: "",
        customerComment: ""
      },
      settlement: {
        defaultAddress: "",
        id: "",
        customerAddress: "",
        customerPhoneNumber: "",
        customerName: "",
        address: "",
        customer: "",
        phoneNumbers: "",
        paymentWay: 0,
        orderNumbers: "",
        orderUuid: ""
      },
      options: [{}]
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.orderList = this.$route.query.orderList;
      this.totalCount = this.$route.query.totalCount;
      this.totalPrice = this.$route.query.totalPrice;
      //查询列表
      this.listLoading = true;
      this.api({
        url: "/orderManage/listOrder",
        method: "get"
      }).then(data => {
        this.listLoading = false;
        this.options = data;
      });
    },
    changeSelect(val) {
      this.select = this.val;
      this.api({
        url: "/orderManage/listOrder",
        method: "get",
        params: { addressNumbers: this.val }
      }).then(data => {
        this.listLoading = false;
        this.settlement.address = data[0].customerAddress;
        this.settlement.customer = data[0].customerName;
        this.settlement.phoneNumbers = data[0].customerPhoneNumber;
      });
    },
    onlinePayment() {
      this.settlement.paymentWay = 0;
    },
    arrivePayment() {
      this.settlement.paymentWay = 1;
    },
    verifyPassword() {
      this.listLoading = true;
      this.api({
        url: "/person/selectPassword",
        method: "get"
      }).then(data => {
        this.password = data["password"];
        if (this.password == this.inputValue) {
          if (this.customerBalance >= this.totalPrice) {
            var balance = this.customerBalance - this.totalPrice;
            // 修改余额api
            this.api({
              url: "/person/recharge",
              method: "post",
              data: { customerBalance: balance }
            }).then(result => {
              this.listLoading = false;
            });
            // 将订单导入数据库
            this.api({
              url: "/orderManage/submitOrder",
              method: "post",
              data: {
                settlement: this.settlement,
                orderList: this.orderList,
                totalPrice: this.totalPrice
              }
            }).then(result => {
              this.listLoading = false;
              this.settlement.orderNumbers = result.orderNumbers;
              this.$router.push({
                path: "submitSuccess",
                query: {
                  orderNumbers: result.orderNumbers,
                  totalPrice: this.totalPrice
                }
              });
              //为客户发送订单邮件
              this.api({
                url: "/person/sendMessage",
                method: "get",
                params: {
                  orderNumbers: result.orderNumbers,
                  subject: "MaetS Games订单发票"
                }
              }).then(data => {
                this.listLoading = false;
                this.$message({
                  type:"success",
                  message:"data.sendMessage"
                })
              });
            });
          } else {
            this.$confirm("您的余额不足，请及时充值！", "提示", {
              confirmButtonText: "前往充值",
              cancelButtonText: "考虑考虑",
              type: "warning"
            })
              .then(() => {
                this.$message({
                  type: "warning",
                  message: "正在前往充值页面！"
                });
                this.listLoading = true;
                this.$router.push({
                  path: "/person/myWallet"
                });
              })
              .catch(() => {
                this.$message({
                  type: "waining",
                  message: "未完成的订单将为您保存十分钟，请尽快处理！"
                });
              });
            this.$message({
              message: "您的余额不足，请及时充值！",
              type: "success"
            });
          }
        } else {
          this.$message({
            message: "密码错误！"
          });
        }
      });
    },
    submitOrder() {
      this.listLoading = true;
      this.api({
        url: "/person/personInformation",
        method: "get"
      }).then(data => {
        this.listLoading = false;
        this.customerBalance = data["customerBalance"];
      });
      this.listLoading = true;
      //输入密码进行确认
      this.$prompt("请输入账户密码", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消"
      }).then(({ value }) => {
        this.verifyPassword();
        this.inputValue = value;
      });
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