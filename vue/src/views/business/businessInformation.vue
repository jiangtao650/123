<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form
        ref="form"
        :model="tempForm"
        label-width="80px"
        label-position="left"
        style="width: 400px; margin-left:50px;"
      >
        <el-form-item label="商户名称">
          <el-input v-model="tempForm.businessName" size="medium"></el-input>
        </el-form-item>
        <el-form-item label="商户电话">
          <el-input v-model="tempForm.businessPhone" placeholder="请输入联系方式"></el-input>
        </el-form-item>
        <el-form-item label="商户地址">
          <el-input v-model="tempForm.businessAddress" placeholder="请输入商铺地址"></el-input>
        </el-form-item>
        <el-form-item label="营业时间">
          <el-col :span="11">
            <el-time-select
              v-model="tempForm.businessOpenTime"
              placeholder="开店时间"
              :picker-options="{
                start:'00:00',
                step:'00:15',
                end:'23:45'}"
              style="width: 100%;"
            ></el-time-select>
          </el-col>
          <el-col class="line" :span="2">---</el-col>
          <el-col :span="11">
            <el-time-select
              v-model="tempForm.businessCloseTime"
              placeholder="打烊时间"
              :picker-options="{
                start:'00:00',
                step:'00:15',
                end:'23:45',
                minTime:tempForm.businessOpenTime}"
              style="width: 100%;"
            ></el-time-select>
          </el-col>
        </el-form-item>
        <el-form-item label="商店简介">
          <el-input type="textarea" rows="3" placeholder="请选择" v-model="tempForm.businessBrief"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="selectBusiness">重置</el-button>
          <el-button type="danger" @click="updateBusiness">保存</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      tempForm: {
        businessName: "",
        businessPhone: "",
        businessAddress: "",
        businessOpenTime: "",
        businessCloseTime: "",
        businessBrief: "",
        userId: this.$store.getters.userId
      },
      user: {
        userId: this.$store.getters.userId
      }
    };
  },
  created() {
    this.selectBusiness();
  },
  methods: {
    selectBusiness() {
      this.api({
        url: "/addBusiness/selectBusiness",
        method: "get",
        params: this.user
      }).then(response => {
        console.log("返回值：" + response);
        this.tempForm.businessName = response["businessName"];
        this.tempForm.businessPhone = response["businessPhone"];
        this.tempForm.businessAddress = response["businessAddress"];
        this.tempForm.businessOpenTime = response["businessOpenTime"];
        this.tempForm.businessCloseTime = response["businessCloseTime"];
        this.tempForm.businessBrief = response["businessBrief"];
      });
    },
    updateBusiness() {
      let _vue = this;
      this.$confirm("确定保存?", "提示", {
        confirmButtonText: "确定",
        showCancelButton: false,
        type: "warning"
      }).then(() => {
        this.api({
          url: "/addBusiness/updateBusinessInformation",
          method: "post",
          data: this.tempForm
        }).then(result => {
          this.$message({
            message: result,
            type: "success"
          });
          console.log("傳回值：" + result);
          this.tempForm.businessName = result["businessName"];
          this.tempForm.businessPhone = result["businessPhone"];
          this.tempForm.businessAddress = result["businessAddress"];
          this.tempForm.businessOpenTime = result["businessOpenTime"];
          this.tempForm.businessCloseTime = result["businessCloseTime"];
          this.tempForm.businessBrief = result["businessBrief"];
          this.selectBusiness();
        });
      });
    }
  }
};
</script>
