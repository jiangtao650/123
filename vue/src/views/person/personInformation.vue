<template>
  <div class="app-container">
    <div class="filter-container">
      <!-- <div class="background">
        <img :src="imgSrc" width="100%" height="100%" alt />
      </div> -->
      <el-form :inline="true">
        <el-form-item>
          <router-link :to="{name:'修改信息'}">
            <el-button type="primary" icon="plus">修改信息</el-button>
          </router-link>
        </el-form-item>
      </el-form>
      <table style="margin-left:100px;margin-top:20px">
        <tr>
          <td>
            <el-image style="width: 300px; height: 300px" :src="personInformation.headPortrait"></el-image>
          </td>
          <td>
            <table>
              <tr>
                <td>
                  <span
                    style="margin-bottom:20px;margin-bottom:30px;margin-top:200px;margin-left:50px;font-size:20px;font-weight:blod"
                  >{{personInformation.customerName}}</span>
                </td>
              </tr>
              <tr>
                <td>
                  <el-image style="width: 150px; height: 150px" :src="personInformation.pictureOne"></el-image>
                </td>
                <td>
                  <el-image style="width: 150px; height: 150px" :src="personInformation.pictureTwo"></el-image>
                </td>
                <td>
                  <el-image
                    style="width: 150px; height: 150px"
                    :src="personInformation.pictureThree"
                  ></el-image>
                </td>
                <td>
                  <el-image
                    style="width: 150px; height: 150px"
                    :src="personInformation.pictureFour"
                  ></el-image>
                </td>
                <td>
                  <el-image
                    style="width: 150px; height: 150px"
                    :src="personInformation.pictureFive"
                  ></el-image>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <table style="margin-left:100px;margin-top:20px">
        <tr>
          <td style="margin-top:50px;width:500px">
            <span
              style="margin-top:50px;float:left;font-size:15px;font-weight:blod"
            >用户昵称：{{personInformation.customerName}}</span>
            <br />
          </td>
          <td style="margin-top:15px">
            <span style="margin-top:50px;float:left;font-size:15px;font-weight:blod">
              性别：
              <el-tag v-if="personInformation.gender==1">男</el-tag>
              <el-tag v-if="personInformation.gender==2" type="danger">女</el-tag>
            </span>
          </td>
        </tr>
        <tr>
          <td style="margin-top:15px">
            <span
              style="margin-top:50px;float:left;font-size:15px;font-weight:blod"
            >联系方式：{{personInformation.customerPhoneNumber}}</span>
          </td>
          <td style="margin-top:15px">
            <span
              style="margin-top:50px;float:left;font-size:15px;font-weight:blod"
            >出生日期：{{personInformation.birthday}}</span>
          </td>
        </tr>
        <tr>
          <td style="margin-top:15px">
            <span
              style="margin-top:50px;float:left;font-size:15px;font-weight:blod"
            >位置：{{personInformation.location}}</span>
          </td>
          <td style="margin-top:15px">
            <span
              style="margin-top:50px;float:left;font-size:15px;font-weight:blod"
            >公司：{{personInformation.company}}</span>
          </td>
        </tr>
      </table>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      imgSrc: require("../../assets/1.jpg"),
      dialogVisible: false,
      disabled: false,
      dialogImageUrl: "",
      listLoading: false, //数据加载等待动画
      isIndeterminate: false,
      personInformation: {
        customerName: "",
        customerPhoneNumber: "",
        customerId: "",
        email: "",
        headPortrait: "",
        gender: "",
        birthday: "",
        location: "",
        company: "",
        pictureOne: "",
        pictureTwo: "",
        pictureThree: "",
        pictureFour: "",
        pictureFive: ""
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.listLoading = true;
      this.api({
        url: "/person/selectPersonInformation",
        method: "get"
      }).then(data => {
        console.log(data);
        this.listLoading = false;
        this.personInformation = data;
      });
    },
    handleAvatarSuccess(param) {
      console.log(param);
      this.imageUrl = param.info.pictureUrl;
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    }
  }
};
</script>
<style>
.background {
  width: 100%;
  height: 100%; /**宽高100%是为了图片铺满屏幕 */
  z-index: -1;
  position: absolute;
}
.filter-container {
  z-index: 1;
  position: absolute;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden; 
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 250px;
  height: 250px;
  line-height: 250px;
  text-align: center;
}
.avatar {
  width: 250px;
  height: 250px;
  display: block;
}
</style>
