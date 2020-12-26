<template>
<div class="app-container">
  <div class="filter-container">
    <body>
      <el-table
        highlight-current-row
        :data="list"
        fit
        v-loading.body="listLoading"
        element-loading-text="拼命加载中"
        style="width: 100%"
        v-model="isAllCheck"
        @selection-change="selectedAll"
      >
        <el-table-column
          type="selection"
          class="Select"
          style="width:10%;"
          v-model="shoppingCart.isChecked"
        ></el-table-column>
        <el-table-column align="center" prop="goodsId" label="商品编号" style="hidden"></el-table-column>
        <el-table-column align="center" prop="pictureUrl" label="商品" style="width: 150px;">
          <template slot-scope="scope">
            <img :src="scope.row.pictureUrl" width="100px" height="150px" />
          </template>
        </el-table-column>
        <el-table-column align="center" prop="goodsName" label="商品名称" style="width: 60px;"></el-table-column>
        <el-table-column align="center" prop="goodsStyle" label="所选款式" style="width: 20px;"></el-table-column>
        <el-table-column align="center" prop="goodsSaleStatus" label="商品状态" style="width: 30px;">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.goodsSaleStatus==1">有货</el-tag>
            <el-tag type="info" v-if="scope.row.goodsSaleStatus==2">无货</el-tag>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="goodsPrice" label="单价" style="width: 40px;">
          <template slot-scope="scope">
            <span width="100px" height="150px">￥{{scope.row.goodsPrice}}</span>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          prop="purchaseNumbers"
          label="数量"
          style="width: 40px;"
          :inline="true"
        >
          <template slot-scope="scope">
            <el-button
              type="text"
              icon="el-icon-caret-left"
              @click="sub(scope.$index)"
              size="mini"
              circle
            ></el-button>
            <el-input
              type="text"
              v-bind:value="scope.row.purchaseNumbers"
              size="mini"
              style="width:50px;"
            ></el-input>
            <el-button
              type="text"
              icon="el-icon-caret-right"
              @click="add(scope.$index)"
              size="mini"
              circle
            ></el-button>
          </template>
        </el-table-column>
        <el-table-column align="center" label="小计" style="width: 60px;">
          <template slot-scope="scope">
            <span width="100px" height="150px">￥{{scope.row.purchaseNumbers*scope.row.goodsPrice}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="goodsName" label="操作" style="width: 60px;">
          <template slot-scope="scope">
            <el-button
              type="danger"
              @click="deleteCommodity(scope.$index)"
              size="medium"
              circle
              icon="el-icon-delete"
              style="margin:10px"
            ></el-button>
            <el-button
              class="{'attention':flag,'unAttention':!flag}"
              id="1"
              type="danger"
              v-bind:icon="iconData"
              @click="moveAttention(scope.$index)"
              size="medium"
              round
            >{{attention}}</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer" style="display: flex;justify-content:flex-end">
        <div id="account-box" style="display: inline;">
          <div>
            共选中
            <span id="count">{{totalCount}}</span>件商品，总金额：
            <span id="total">￥{{totalPrice}}</span>
          </div>
          <router-link
            :to="{name:'结算页',query:{orderList:this.paymentList,totalCount:this.totalCount,totalPrice:this.totalPrice}}"
          >
            <el-button type="success" @click="submitOrder">去下单</el-button>
          </router-link>
        </div>
      </div>
    </body>
  </div>
</div>
</template>
<script>
export default {
  data() {
    return {
      orderNumbers: "",
      iconData: "el-icon-star-off",
      attention: "关注",
      flag: false,
      checkNum: 0,
      isAllCheck: false,
      list: [], //表格的数据
      paymentList: [],
      listLoading: false, //数据加载等待动画
      isIndeterminate: false,
      totalCount: 0,
      totalPrice: 0,
      orderList: "orderList",
      shoppingCart: {
        isChecked: false,
        goodsId: "",
        pitchOn: "",
        goodsName: "",
        goodsPrice: "",
        goodsSaleStatus: "",
        pictureUrl: "",
        goodsStyle: "",
        purchaseNumbers: "",
        subTotal: ""
      },
      list: []
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.listLoading = true;
      this.api({
        url: "/addBusiness/shoppingCart",
        method: "get"
      }).then(data => {
        this.listLoading = false;
        this.list = data.list;
      });
    },
    selectedAll(val) {
      this.paymentList = val;
      this.totalPrice = 0;
      this.totalCount = val.length;
      for (var i = 0; i < val.length; i++) {
        this.totalPrice += val[i].goodsPrice * val[i].purchaseNumbers;
      }
    },
    add: function($index) {
      if (this.list[$index].purchaseNumbers >= 10) {
        this.list[$index].purchaseNumbers = 10;
      } else {
        this.list[$index].purchaseNumbers++;
      }
    },
    sub: function($index) {
      if (this.list[$index].purchaseNumbers <= 1) {
        this.list[$index].purchaseNumbers = 1;
      } else {
        this.list[$index].purchaseNumbers--;
      }
    },
    handleSizeChange(val) {
      //改变每页数量
      this.listQuery.pageRow = val;
      this.handleFilter();
    },
    handleCurrentChange(val) {
      //改变页码
      this.listQuery.pageNum = val;
      this.getList();
    },
    handleFilter() {
      //改变了查询条件,从第一页开始查询
      this.listQuery.pageNum = 1;
      this.getList();
    },
    moveAttention() {
      this.flag = !this.flag;
      this.iconData = "el-icon-star-off";
      if (this.flag == true) {
        this.attention = "已关注";
        this.iconData = "el-icon-star-on";
      } else if (this.flag == false) {
        this.attention = "关注";
        this.iconData = "el-icon-star-off";
      }
    },
    deleteCommodity($index) {
      console.log(this.list[$index].goodsName);
      this.api({
        url: "/addBusiness/deleteShoppingCart",
        method: "post",
        data: { goodsName: this.list[$index].goodsName }
      }).then(() => {
        this.getList();
        this.$message({
          message: "删除成功！",
          type: "success"
        });
        this.dialogFormVisible = false;
      });
    },
    submitOrder() {
      this.listLoading = true;
    }
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
