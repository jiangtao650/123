<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form label-width="80px" :inline="true">
        <el-form-item>
          <router-link :to="{name:'maetS商城'}">
            <el-button type="primary" icon="plus">返回</el-button>
          </router-link>
        </el-form-item>
        <el-form-item>
          <span :v-model="details.goodsName" style="large"></span>
        </el-form-item>
      </el-form>
      <el-form style="margin-left:60px" :inline="true">
        <el-form-item>
          <div class="gamesCover">
            <el-image style="width: 210px; height: 315px" :src="details.pictureUrl"></el-image>
          </div>
        </el-form-item>
        <el-form-item style="margin-left:60px">
          <span v-text="details.goodsName" style="text-align: left;display:block;"></span>
          <span v-text="'售价：'+details.goodsPrice" style="text-align: left;display:block;"></span>
          <span v-text="'类型：'+details.goodsCategoryName" style="text-align: left;display:block;"></span>
          <span v-text="'状态：'+details.goodsSaleStatus" style="text-align: left;display:block;"></span>
          <span v-text="'简介：'+details.goodsBrief" style="text-align: left;display:block;"></span>
          <span v-text="'上市:'" style="text-align: left;display:block;"></span>
          <span v-text="'标签:'" style="text-align: left;display:block;"></span>
          <el-row style="margin:10px">
            <el-button type="danger" icon="plus" @click="sub(details)" size="mini" round>-</el-button>
            <el-input
              type="text"
              v-bind:value="details.purchaseNumbers"
              size="small"
              style="width:50px;"
            ></el-input>
            <el-button type="danger" icon="plus" @click="add(details)" size="mini" round>+</el-button>
          </el-row>
          <el-button type="warning" round @click="joinShoppingCart">加入购物车</el-button>
          <el-button type="danger" round>立即购买</el-button>
        </el-form-item>
        <el-form-item style=" width:400px;height:300px;margin-left:60px">
          <el-carousel :interval="4000" type="card" weight="300px" height="bannerHeight+'px'">
            <el-carousel-item v-for="details in lightImage" :key="details.id">
              <el-row :gutter="12">
                <el-col style=" width:200px;height:340px;margin-bottom: 20px;">
                  <a target="_blank">
                    <img
                      ref="bannerHeight"
                      :src="details.pictureUrl"
                      :fit="contain"
                      style="height:100%;width:100%"
                    />
                  </a>
                </el-col>
              </el-row>
            </el-carousel-item>
          </el-carousel>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      contain: "",
      dialogImageUrl: "",
      list: [], //表格的数据
      listLoading: false, //数据加载等待动画
      dialogStatus: "create",
      dialog: "update",
      dialogFormVisible: false,
      details: {
        id: "",
        goodsName: "",
        goodsPrice: "",
        goodsNumbers: "",
        goodsCategoryId: "",
        goodsSaleStatus: "",
        goodsBrief: "",
        goodsCategoryName: "",
        pictureUrl: "",
        commodityId: "",
        goodsStyle: "",
        purchaseNumbers: 1
      },
      lightImage: {
        videoUrl: "",
        screenshotsUrl: ""
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.details.id = this.$route.query.id;
      this.listLoading = true;
      this.api({
        url: "/addBusiness/commodityDetails",
        method: "get",
        params: this.details.id
      }).then(data => {
        this.listLoading = false;
        this.details.goodsName = data.goodsName;
        this.details.goodsPrice = data.goodsPrice;
        this.details.goodsNumbers = data.goodsNumbers;
        this.details.goodsSaleStatus = data.goodsSaleStatus;
        this.details.goodsBrief = data.goodsBrief;
        this.details.goodsCategoryName = data.goodsCategoryName;
        this.details.pictureUrl = data.pictureUrl;
      });
    },
    add: function(details) {
      if (this.details.purchaseNumbers >= 10) {
        this.details.purchaseNumbers = 10;
      } else {
        this.details.purchaseNumbers++;
      }
    },
    sub: function(details) {
      if (this.details.purchaseNumbers <= 1) {
        this.details.purchaseNumbers = 1;
      } else {
        this.details.purchaseNumbers--;
      }
    },
    joinShoppingCart() {
      this.listLoading = true;
      this.api({
        url: "/addBusiness/addShoppingCart",
        method: "post",
        data: this.details
      }).then(result => {
        this.$message({
          message: "加入购物车成功！",
          type: "success"
        });
      });
    }
  }
};
</script>