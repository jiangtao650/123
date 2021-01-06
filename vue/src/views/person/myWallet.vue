<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form
        ref="form"
        :model="myWallet"
        label-width="100px"
        label-position="left"
        style="width: 200px; margin-left:50px;"
      >
        <el-form-item label="账户昵称：">
          <el-input type="text" v-model="myWallet.nickname" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="账户余额：">
          <el-input type="text" v-model="myWallet.customerBalance" :disabled="true"></el-input>
        </el-form-item>
      </el-form>
    </div>
    <el-form
      ref="forms"
      :model="myWallet"
      label-width="100px"
      label-position="left"
      style="width: 600px; margin-left:50px;"
    >
      <el-form-item label="充值金额：">
        <el-input
          type="text"
          v-model="myWallet.rechargeCustomerBalance"
          placeholder="请输入充值金额"
          style="width: 200px;"
        ></el-input>
        <div class="right-items" style="float: right;">
          <el-button type="danger" @click="popUp" round>确认</el-button>
        </div>
      </el-form-item>
    </el-form>
    <el-form>
      <el-form-item>
        <el-button type="warning" @click="buttonOne">100</el-button>
        <el-button type="warning" @click="buttonTwo">250</el-button>
        <el-button type="warning" @click="buttonThree">388</el-button>
        <el-button type="warning" @click="buttonFour">520</el-button>
        <el-button type="warning" @click="buttonFive">648</el-button>
        <el-button plain type="info" @click="buttonSix">白嫖1000</el-button>
      </el-form-item>
    </el-form>
    <el-form>
      <el-form-item label="选择支付方式"></el-form-item>
    </el-form>
    <el-form>
      <el-radio-group v-model="payment">
        <el-radio :label="1" @change="selectRecharge">
          <img
            src="http://image.jiangtao.com/wechat.png"
            style="width: 80px;height: 80px;padding-top: 5px;"
            alt
          />
        </el-radio>
        <el-radio :label="2">
          <img
            src="http://image.jiangtao.com/zhifubao.png"
            style="width: 80px;height: 80px;padding-top: 5px;"
            alt
          />
        </el-radio>
        <el-radio :label="3">
          <img
            src="http://image.jiangtao.com/jd.png"
            style="width: 80px;height: 80px;padding-top: 5px;"
            alt
          />
        </el-radio>
        <el-radio :label="4">
          <img
            src="http://image.jiangtao.com/yinlian.png"
            style="width: 80px;height: 80px;padding-top: 5px;"
            alt
          />
        </el-radio>
        <el-radio :label="5">
          <img
            src="http://image.jiangtao.com/paypal.png"
            style="width: 80px;height: 80px;padding-top: 5px;"
            alt
          />
        </el-radio>
      </el-radio-group>
    </el-form>
    <el-form
      ref="formed"
      :model="myWallet"
      label-width="100px"
      label-position="left"
      style="width: 600px; margin-left:50px;"
    ></el-form>
  </div>
</template>
<script>
export default {
  data() {
    return {
      listLoading: false, //数据加载等待动画
      payment: "5",
      dialogFormVisible: false,
      originalMyWallet: "",
      myWallet: {
        nickname: "",
        customerBalance: "",
        rechargeCustomerBalance: "0",
        password: "",
        inputValue: ""
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    verifyPassword() {
      this.listLoading = true;
      this.api({
        url: "/person/selectPassword",
        method: "get"
      }).then(data => {
        this.myWallet.password = data["password"];
        if (this.myWallet.password == this.myWallet.inputValue) {
          this.$message({
            message: "恭喜您，充值成功！"
          });
          this.recharge();
        } else {
          this.$message({
            message: "密码错误！"
          });
        }
      });
    },
    popUp() {
      this.$prompt("请输入账户密码", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消"
      }).then(({ value }) => {
        this.verifyPassword();
        this.myWallet.inputValue = value;
      });
    },
    selectRecharge: function() {
      this.dialogFormVisible = true;
    },
    recharge() {
      this.listLoading = true;
      console.log(
        "this.myWallet.customerBalance=" + this.myWallet.customerBalance
      );
      console.log(
        "this.myWallet.rechargeCustomerBalance=" +
          this.myWallet.rechargeCustomerBalance
      );
      this.api({
        //recharge充值的意思
        url: "/person/recharge",
        method: "post",
        data: {
          customerBalance:
            this.myWallet.rechargeCustomerBalance + this.originalMyWallet
        }
      }).then(result => {
        this.listLoading = false;
        this.getList();
      });
    },
    getList() {
      this.listLoading = true;
      this.api({
        url: "/person/personInformation",
        method: "get",
        params: this.myWallet
      }).then(data => {
        this.listLoading = false;
        this.myWallet.nickname = data["nickName"];
        this.myWallet.customerBalance = data["customerBalance"];
        this.originalMyWallet = data["customerBalance"];
        console.log("this.originalMyWallet=" + this.originalMyWallet);
      });
    },
    changeList(id) {
      if (this.a == id) {
        this.a = !this.a;
      } else {
        this.a = id;
      }
    },
    buttonOne() {
      this.myWallet.rechargeCustomerBalance = 100;
    },
    buttonTwo() {
      this.myWallet.rechargeCustomerBalance = 250;
    },
    buttonThree() {
      this.myWallet.rechargeCustomerBalance = 388;
    },
    buttonFour() {
      this.myWallet.rechargeCustomerBalance = 520;
    },
    buttonFive() {
      this.myWallet.rechargeCustomerBalance = 648;
    },
    buttonSix() {
      this.$notify({
        message: "你在想屁吃！"
      });
    }
  }
};
</script>