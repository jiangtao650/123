<template>
  <div class="app-container">
    <div class="filter-container">
      <div>
        <span
          style="font-size:50px;text-align:center;display:block;margin-top:50px;font-weight:bold;"
        >Thank You！</span>
      </div>
      <div>
        <span
          style="font-size:20px;text-align:center;display:block;margin-top:50px;font-weight:bold"
        >尊敬的{{singleOrder.customerName}}：</span>
      </div>
      <div>
        <span
          style="font-size:15px;text-align:center;display:block;margin-top:10px"
        >感谢您对MaetS Games商城的光顾！</span>
      </div>
      <div>
        <span
          style="font-size:30px;text-align:center;display:block;margin-top:30px;font-weight:bold"
        >发票ID：</span>
      </div>
      <div>
        <span
          style="font-size:15px;text-align:center;display:block;margin-top:10px;color:#B2B2B2;"
        >(请保留本收据，以备后用。)</span>
      </div>
    </div>
    <div style="width:600px;margin-top:100px;text-align:center;display:block;margin:auto;">
      <div>
        <span
          style="color:#B2B2B2;font-size:15px;text-align:left;display:block;margin-top:30px;font-weight:bold"
        >您的订单信息：</span>
      </div>
      <el-divider></el-divider>
      <table>
        <tr>
          <td>
            <span
              style="font-size:15px;text-align:left;display:block;margin-top:30px;font-weight:bold;width:300px"
            >订单ID：</span>
            <br />
            <span
              style="font-size:15px;text-align:left;display:block;margin-top:30px;"
            >{{singleOrder.orderNumbers}}</span>
          </td>
          <td>
            <span
              style="font-size:15px;text-align:left;display:block;margin-top:30px;font-weight:bold"
            >账单地址：</span>
            <br />
            <span
              style="font-size:15px;text-align:left;display:block;margin-top:30px;"
            >{{singleOrder.customerEmail}}</span>
          </td>
        </tr>
        <tr>
          <td>
            <span
              style="font-size:15px;text-align:left;display:block;margin-top:15px;font-weight:bold"
            >订购日期：</span>
            <br />
            <span
              style="font-size:15px;text-align:left;display:block;margin-top:15px;"
            >{{singleOrder.createdTime}}</span>
          </td>
          <td>
            <span
              style="font-size:15px;text-align:left;display:block;margin-top:15px;font-weight:bold"
            >来源：</span>
            <br />
            <span style="font-size:15px;text-align:left;display:block;margin-top:5px;">maetS Store</span>
          </td>
        </tr>
      </table>
      <div style="width:600px;margin-top:100px;text-align:center;display:block;margin:auto;">
        <div>
          <span
            style="color:#B2B2B2;font-size:15px;text-align:left;display:block;margin-top:15px;font-weight:bold"
          >以下是您订购的物品：</span>
        </div>
        <el-divider></el-divider>
        <el-table
          highlight-current-row
          :data="list"
          fit
          v-loading.body="listLoading"
          element-loading-text="拼命加载中"
          style="width: 100%"
        >
          <el-table-column
            align="center"
            prop="pictureUrl"
            label="商品"
            style="width:120px;height:150"
          >
            <template slot-scope="scope">
              <img :src="scope.row.pictureUrl" width="100px" height="150px" />
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            prop="goodsName"
            label="名称"
            style="width:120px;height:150"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="purchaseNumbers"
            label="数量"
            style="width:120px;height:150"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="goodsPrice"
            label="单价"
            style="width:120px;height:150"
          ></el-table-column>
          <el-table-column align="center" prop="subtotal" label="小计" style="width:120px;height:150"></el-table-column>
        </el-table>
        <div style="width:600px;margin-top:20px;text-align:right;display:block;">
          总计：￥
          <span>{{totalPrice}}</span>
        </div>
        <el-divider></el-divider>
        <div>
          <span
            style="width:600px;text-align:center;display:block;font-size:10px;"
          >所有在MaetS Games商城购买的游戏都可以在购买后的14天内退款（预购游戏则可在发售后14天内退款），但前提是游戏时间不得超过2小时。</span>
        </div>
        <el-divider></el-divider>
        <div>
          <span
            style="width:600px;text-align:center;display:block;font-size:10px;font-weight:bold"
          >MaetS Games 科技有限公司</span>
          <span
            style="width:600px;text-align:center;display:block;font-size:10px;"
          >广东省东莞市东城区东平街君豪商业中心20层达内教育CGB2008班</span>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
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
        var price=0;
        for(var i=o;i<data[0].length;i++){
          
        }
      });
    }
  }
};
</script>
