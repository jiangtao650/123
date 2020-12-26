<template>
  <div>
    <div class="login-container">
      <el-form
        autocomplete="on"
        :model="loginForm"
        :rules="loginRules"
        ref="loginForm"
        label-position="left"
        label-width="0px"
        class="card-box login-form"
      >
        <h3 class="title">后台管理系统</h3>
        <el-form-item prop="username">
          <span class="svg-container svg-container_login">
            <svg-icon icon-class="user" />
          </span>
          <el-input v-model="loginForm.username" autocomplete="on" />
        </el-form-item>
        <el-form-item prop="password">
          <span class="svg-container">
            <svg-icon icon-class="password"></svg-icon>
          </span>
          <el-input
            type="password"
            @keyup.enter.native="handleLogin"
            v-model="loginForm.password"
            autocomplete="on"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            style="width:100%;"
            :loading="loading"
            @click.native.prevent="handleLogin"
          >登录</el-button>
        </el-form-item>
        <el-form>
          <el-button type="text" icon="plus" @click="showCreate">注册账号</el-button>
          <el-button type="text" disabled></el-button>
          <el-button type="text" disabled></el-button>
          <el-button type="text" disabled></el-button>
          <el-button type="text" disabled></el-button>
          <el-button type="text" disabled></el-button>
          <el-button type="text" disabled></el-button>
          <el-button type="text" disabled></el-button>
          <el-button type="text" disabled></el-button>
          <el-button type="text" disabled></el-button>
          <el-button type="text" disabled></el-button>
          <el-button type="text" disabled></el-button>
          <el-button type="text" disabled></el-button>
          <el-button type="text" @click="forgetPwd">忘记密码</el-button>
        </el-form>
      </el-form>
    </div>
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form
        class="changeForm"
        :model="tempUser"
        label-position="left"
        label-width="80px"
        style="width: 300px; margin-left:50px;"
        size="mini"
        :rules="passwordRules"
        ref="tempUser"
      >
        <el-tabs v-model="activeName" type="card" @tab-click="customer">
          <!-- 顾客注册弹窗页面 -->
          <el-tab-pane :label="user.customer" name="first">
            <el-form-item label="用户名" required v-if="dialogStatus==='create'">
              <el-input type="text" v-model="tempUser.username" placeholder="请设置您的用户名"></el-input>
            </el-form-item>
            <el-form-item label="密码" v-if="dialogStatus==='create'" prop="password">
              <el-input
                type="password"
                v-model="tempUser.password"
                class="changeInput"
                placeholder="请设置您的密码"
              ></el-input>
              <i class="el-icon-success" v-if="changeFlag == 1" color="#67C23A"></i>
              <i class="el-icon-error" v-else-if="changeFlag == 2" color="#F56C6C"></i>
            </el-form-item>
            <el-form-item label="确认密码" v-if="dialogStatus==='create'" prop="queryPassword">
              <el-input
                type="password"
                v-model="tempUser.queryPassword"
                v-if="dialogStatus==='create'"
                required
                placeholder="请再次输入您的密码"
                class="changeInput"
              ></el-input>
              <i class="el-icon-success" v-if="changeAgainFlag==1" color="#67C23A"></i>
              <i class="el-icon-error" v-else-if="changeAgainFlag==2" color="#F56C6C"></i>
            </el-form-item>
            <el-form-item label="昵称" v-if="dialogStatus==='create'" required>
              <el-input type="text" v-model="tempUser.nickname" placeholder="请设置您的昵称"></el-input>
            </el-form-item>
            <el-form-item label="联系方式" v-if="dialogStatus==='create'" required>
              <el-input type="text" v-model="tempUser.customerPhoneNumber" placeholder="请输入您的联系方式"></el-input>
            </el-form-item>
            <el-form-item label="邮箱号码" v-if="dialogStatus==='create'" required>
              <el-input type="text" v-model="tempUser.email" placeholder="请输入您的邮箱号码"></el-input>
            </el-form-item>
          </el-tab-pane>
          <!-- 商家注册弹框页面 -->
          <el-tab-pane :label="user.business" name="second">
            <el-form-item label="用户名" required v-if="dialogStatus==='create'">
              <el-input type="text" v-model="tempUser.username" placeholder="请设置您的用户名"></el-input>
            </el-form-item>
            <el-form-item label="密码" v-if="dialogStatus==='create'" prop="password">
              <el-input
                type="password"
                v-model="tempUser.password"
                placeholder="请设置您的密码"
                class="changeInput"
              ></el-input>
              <i class="el-icon-success" v-if="changeFlag == 1"></i>
              <i class="el-icon-error" v-else-if="changeFlag == 2"></i>
            </el-form-item>
            <el-form-item label="确认密码" v-if="dialogStatus==='create'" prop="queryPassword">
              <el-input
                type="password"
                v-model="tempUser.queryPassword"
                v-if="dialogStatus==='create'"
                required
                class="changeInput"
                placeholder="请再次输入您的密码"
              ></el-input>
              <i class="el-icon-success" v-if="changeAgainFlag==1"></i>
              <i class="el-icon-error" v-else-if="changeAgainFlag==2"></i>
            </el-form-item>
            <el-form-item label="昵称" v-if="dialogStatus==='create'" required>
              <el-input type="text" v-model="tempUser.nickname" placeholder="请输入内容……"></el-input>
            </el-form-item>
            <el-form-item label="商铺名称" v-if="dialogStatus==='create'" required>
              <el-input type="text" v-model="tempUser.businessName" placeholder="请输入内容……"></el-input>
            </el-form-item>
            <el-form-item label="订购热线" v-if="dialogStatus==='create'" required>
              <el-input type="text" v-model="tempUser.businessPhone" placeholder="请输入手机号码"></el-input>
            </el-form-item>
            <el-form-item label="门店地址" v-if="dialogStatus==='create'" required>
              <el-input type="text" v-model="tempUser.businessAddress" placeholder="请具体到街道、门牌号"></el-input>
            </el-form-item>
            <el-form-item label="营业时间" required>
              <el-col :span="11">
                <el-time-select
                  v-model="tempUser.businessOpenTime"
                  placeholder="开店"
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
                  v-model="tempUser.businessCloseTime"
                  placeholder="打烊"
                  :picker-options="{
                start:'00:00',
                step:'00:15',
                end:'23:45',
                minTime: tempUser.businessOpenTime}"
                  style="width: 100%;"
                ></el-time-select>
              </el-col>
            </el-form-item>
            <el-form-item label="商铺简介" v-if="dialogStatus==='create'" required>
              <el-input
                type="textarea"
                rows="3"
                placeholder="请输入内容……"
                v-model="tempUser.businessBrief"
              ></el-input>
            </el-form-item>
          </el-tab-pane>
        </el-tabs>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus==='create'" type="success" @click="createUser">注册</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  name: "login",
  data() {
    var validatePass = (rule, value, callback) => {
      if (value == "") {
        this.changeFlag = 2;
        callback(new Error("请输入密码"));
      } else {
        if (this.tempUser.password !== "") {
          this.changeFlag = 2;
          this.$refs.tempUser.validateField("queryPassword");
        }
        callback();
        this.changeFlag = 1;
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value == "") {
        this.changeAgainFlag = 2;
        callback(new Error("请再次输入密码"));
      } else if (value !== this.tempUser.password) {
        this.changeAgainFlag = 2;
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
        this.changeAgainFlag = 1;
      }
    };
    return {
      user: {
        customer: "顾客",
        business: "商家"
      },
      changeFlag: 0,
      changeAgainFlag: 0,
      activeName: "first",
      loginForm: {
        username: "admin",
        password: "123456"
      },
      dialogStatus: "create",
      dialogFormVisible: false,
      textMap: {
        create: "注册账号"
      },
      tempUser: {
        email: "",
        username: "",
        password: "",
        nickname: "",
        queryPassword: "",
        roleId: 7,
        userId: "",
        customerPhonenumber: "",
        businessName: "",
        businessPhone: "",
        businessAddress: "",
        businessOpenTime: "",
        businessCloseTime: "",
        businessBrief: ""
      },
      loginRules: {
        username: [
          { required: true, trigger: "blur", message: "请输入用户名" }
        ],
        password: [{ required: true, trigger: "blur", message: "请输入密码" }]
      },
      passwordRules: {
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          {
            min: 6,
            max: 16,
            message: "长度在 6 到 16 个字符",
            trigger: "blur"
          },
          { validator: validatePass, trigger: "blur" }
        ],
        queryPassword: [
          { required: true, message: "请确认密码", trigger: "blur" },
          {
            min: 6,
            max: 16,
            message: "长度在 6 到 16 个字符",
            trigger: "blur"
          },
          { validator: validatePass2, trigger: "blur", required: true }
        ]
      },
      loading: false
    };
  },
  methods: {
    handleSubmit(name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          this.$Message.success("Success!");
        } else {
          this.$Message.error("Fail!");
        }
      });
    },
    handleReset(name) {
      this.$refs[name].resetFields();
    },

    handleClick(tab, event) {
      //console.log(tab, event);     1.顾客与商家的密码判定进行单独检验；3.其他细节
    },
    customer($event) {
      this.changeFlag = 0;
      this.changeAgainFlag = 0;
      var validatePass2 = (rule, value, callback) => {
        if (value == "") {
          callback();
        }
      };
      this.showCreate();
      if ($event.name == "first") {
        this.tempUser.roleId = 7;
      } else if ($event.name == "second") {
        this.tempUser.roleId = 6;
      }
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          this.$store
            .dispatch("Login", this.loginForm)
            .then(data => {
              this.loading = false;
              if ("success" === data.result) {
                this.$router.push({ path: "/" });
              } else {
                this.$message.error("账号/密码错误");
              }
            })
            .catch(() => {
              this.loading = false;
            });
        } else {
          return false;
        }
      });
    },
    forgetPwd() {
      // this.$prompt('请输入邮箱', '提示', {
      //     confirmButtonText: '发送验证码',
      //     cancelButtonText: '取消',
      //     inputPattern: /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
      //     inputErrorMessage: '邮箱格式不正确'
      //   }).then(({ value }) => {
      //     this.api({
      //       url:"/person/sendMessage",
      //       method:"get",
      //       params:{purpose:"忘记密码",email:value,subject:"邮箱验证码"}
      //     })
      //     this.$message({
      //       type: 'success',
      //       message: '你的邮箱是: ' + value
      //     });
      //   }).catch(() => {
      //     this.$message({
      //       type: 'info',
      //       message: '取消输入'
      //     });
      //   });
      this.$message({
        message: "忘了活该！"
      });
    },
    showCreate() {
      this.tempUser.username = "";
      this.tempUser.password = "";
      this.tempUser.queryPassword = "";
      this.tempUser.nickname = "";
      this.tempUser.userId = "";
      this.tempUser.customerPhoneName = "";
      this.tempUser.businessName = "";
      this.tempUser.businessPhone = "";
      this.tempUser.businessAddress = "";
      this.tempUser.businessOpenTime = "";
      this.tempUser.businessCloseTime = "";
      this.tempUser.businessBrief = "";
      this.dialogStatus = "create";
      this.dialogFormVisible = true;
    },
    createUser() {
      //添加新用户
      console.log(this.tempUser);
      this.api({
        url: "/register/insertNewPerson",
        method: "post",
        data: this.tempUser
      }).then(() => {
        this.dialogFormVisible = false;
        this.$message({
          message: "注册成功！",
          type: "success"
        });
      });
    }
  }
};
</script>
<style rel="stylesheet/scss" lang="scss">
@import "../../styles/mixin.scss";
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;

.login-container {
  @include relative;
  height: 100vh;
  background-color: $bg;
  input:-webkit-autofill {
    -webkit-box-shadow: 0 0 0px 1000px #293444 inset !important;
    -webkit-text-fill-color: #fff !important;
  }
  input {
    background: transparent;
    border: 0px;
    -webkit-appearance: none;
    border-radius: 0px;
    padding: 12px 5px 12px 15px;
    color: $light_gray;
    height: 47px;
  }
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;
  }
  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;
  }
  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
    &_login {
      font-size: 20px;
    }
  }
  .title {
    font-size: 26px;
    color: $light_gray;
    margin: 0px auto 40px auto;
    text-align: center;
    font-weight: bold;
  }
  .login-form {
    position: absolute;
    left: 0;
    right: 0;
    width: 400px;
    padding: 35px 35px 15px 35px;
    margin: 120px auto;
  }
  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
  }
  .thirdparty-button {
    position: absolute;
    right: 35px;
    bottom: 28px;
  }
}
</style>
