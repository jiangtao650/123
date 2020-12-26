<template>
  <div class="app-container">
    <el-form
      ref="supermarket"
      class="middle-space"
      :model="supermarket"
      label-position="center"
      style="width: 1000px; margin-left:150px;"
      size="medium"
    >
      <el-carousel :interval="4000" type="card" height="bannerHeight+'px'">
        <el-carousel-item v-for="supermarket in lightImage" :key="supermarket.commodityId">
          <el-row :gutter="12">
            <el-col :xs="24" :sm="24" :lg="24" :xl="24" style="height:340px;margin-bottom: 20px;">
              <router-link :to="{name:'商品详情',query:{id:supermarket.commodityId}}">
                <a :v-model="supermarket.commodityId" target="_blank">
                  <img
                    ref="bannerHeight"
                    :src="supermarket.pictureUrl"
                    :fit="contain"
                    style="height:100%"
                  />
                </a>
              </router-link>
              <el-footer style="height:100%">
                <span v-text="supermarket.goodsName" style="text-align: center;display:block;"></span>
                <span v-text="supermarket.goodsBrief" style="text-align: center;display:block;"></span>
              </el-footer>
            </el-col>
          </el-row>
        </el-carousel-item>
      </el-carousel>
    </el-form>
    <div>
      <el-divider></el-divider>
    </div>
    <el-menu
      :default-active="activeIndex"
      class="el-menu-demo"
      mode="horizontal"
      @select="handleSelect"
      background-color="#304156"
      text-color="#fff"
      active-text-color="#ffd04b"
    >
      <el-menu-item index="0">热门推荐</el-menu-item>
      <el-submenu index="13">
        <template slot="title">默认排序</template>
        <el-menu-item index="13-1">游戏热度</el-menu-item>
        <el-menu-item index="13-2">玩家评分</el-menu-item>
        <el-menu-item index="13-3">上市时间</el-menu-item>
        <el-menu-item index="13-4">游戏大小</el-menu-item>
        <el-submenu index="13-3">
          <template slot="title">上市时间</template>
          <el-menu-item index="13-3-1">2020-2011</el-menu-item>
          <el-menu-item index="13-3-2">2010-2001</el-menu-item>
        </el-submenu>
      </el-submenu>
      <el-menu-item index="1">动作格斗</el-menu-item>
      <el-menu-item index="2">角色扮演</el-menu-item>
      <el-menu-item index="3">冒险解谜</el-menu-item>
      <el-menu-item index="4">即时战略</el-menu-item>
      <el-menu-item index="5">FPS射击</el-menu-item>
      <el-menu-item index="6">休闲益智</el-menu-item>
      <el-menu-item index="7">竞技体育</el-menu-item>
      <el-menu-item index="8">赛车竞速</el-menu-item>
      <el-menu-item index="9">模拟养成</el-menu-item>
      <el-menu-item index="10">模拟经营</el-menu-item>
      <el-menu-item index="11">其它类型</el-menu-item>
    </el-menu>
    <el-menu>
      <el-row :gutter="1" class="el-row" justify="space-around" align="top">
        <el-col
          :span="4"
          v-for="supermarket in cardFrom"
          :key="supermarket.commodityId"
          :offset="0"
        >
          <el-card :style="{ width:'200px',height:'320px' }">
            <el-container style="height:300px">
              <router-link :to="{name:'商品详情',query:{id:supermarket.commodityId}}">
                <a :v-model="supermarket.commodityId" target="_blank">
                  <img
                    ref="bannerHeight"
                    :src="supermarket.pictureUrl"
                    :fit="contain"
                    style="width:100%"
                  />
                </a>
              </router-link>
              <el-footer style="100px">
                <span></span>
                <span v-text="supermarket.goodsName" style="text-align: center;display:block;"></span>
                <span v-text="'￥'+supermarket.goodsPrice" style="text-align: center;display:block;"></span>
              </el-footer>
            </el-container>
          </el-card>
        </el-col>
      </el-row>
    </el-menu>
    <el-form label-position="right" style="margin-left:400px;">
      <el-form-item>
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="listQuery.pageNum"
          :page-size="listQuery.pageRow"
          :total="totalCount"
          :page-sizes="[30]"
          layout="total, sizes, prev, pager, next, jumper"
        ></el-pagination>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
export default {
  data() {
    return {
      contain: "",
      activeIndex: "0",
      list: [], //表格的数据
      allPermission: [],
      totalCount: 0,
      listLoading: false, //数据加载等待动画
      listQuery: {
        pageNum: 1,
        pageRow: 30
      },
      lightImage: {
        goodsName: "",
        goodsPrice: "",
        pictureUrl: "",
        recommend: 1
      },
      supermarket: {
        goodsName: "",
        goodsPrice: "",
        pictureUrl: "",
        categoryId: "",
        commodityId: ""
      },
      dialogStatus: "create",
      dialogFormVisible: false,
      cardFrom: [
        {
          value: "goodsName",
          label: "goodsPrice"
        }
      ],
      categoryLabel: [
        {
          value: "goodsCategoryId",
          label: "goodsCategoryName"
        }
      ]
    };
  },
  created() {
    this.getList();
    this.walkHorseLight();
  },
  methods: {
    walkHorseLight() {
      this.listLoading = true;
      this.api({
        url: "/addBusiness/walkHorseLight",
        method: "get"
      }).then(data => {
        this.listLoading = false;
        this.lightImage = data.list;
        this.supermarket.commodityId = data.list.commodityId;
      });
    },
    getList() {
      //查询列表
      this.listLoading = true;
      this.api({
        url: "/addBusiness/listCommodity",
        method: "get",
        params: this.listQuery
      }).then(data => {
        this.listLoading = false;
        this.cardFrom = data.list;
        this.totalCount = data.totalCount;
        this.supermarket.commodityId = data.list.commodityId;
        this.categoryLabel=data.list
      });
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
    getIndex($index) {
      //表格序号
      return (this.listQuery.pageNum - 1) * this.listQuery.pageRow + $index + 1;
    },
    handleSelect(key) {
      this.supermarket.categoryId = key;
      if (key == 0) {
        this.getList();
        this.walkHorseLight();
      } else {
        this.api({
          url: "/addBusiness/mallCategoryDisplay",
          methods: "get",
          params: this.supermarket.categoryId
        }).then(result => {
          this.cardFrom = result.list;
        });
      }
    }
  }
};
</script>
<style scoped>
.requiredPerm {
  color: #ff0e13;
}
</style>
<style>
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
</style>