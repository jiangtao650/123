<template>
  <div class="app-wrapper" :class="{hideSidebar:!sidebar.opened}">
    <sidebar class="sidebar-container"></sidebar>
    <div class="main-container">
      <navbar></navbar>
      <app-main></app-main>
    </div>
  </div>
</template>

<script>
import { Navbar, Sidebar, AppMain } from "@/views/layout/components";

export default {
  name: "layout",
  components: {
    Navbar,
    Sidebar,
    AppMain
  },
  computed: {
    sidebar() {
      return this.$store.state.app.sidebar;
    },
    // getWsMsg() {
    //   return this.$store.state.webSocketMsg;
    // }
  },
  // watch: {
  //   getWsMsg: {
  //     handler: function(newVal) {
  //       console.log("newVal=" + newVal);
  //       alert("接收到webSocket推送" + newVal);
  //       this.open();
  //     }
  //   }
  // },
  // methods: {
  //   open() {
  //     this.$confirm("新的订单，是否接收?", "提示", {
  //       confirmButtonText: "接收",
  //       cancelButtonText: "拒接",
  //       type: "warning"
  //     })
  //       .then(() => {
  //         this.$message({
  //           type: "success",
  //           message: "接收成功!"
  //         });
  //       })
  //       .catch(() => {
  //         this.$message({
  //           type: "info",
  //           message: "订单已拒接"
  //         });
  //       });
  //   }
  // }
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
@import "src/styles/mixin.scss";
.app-wrapper {
  @include clearfix;
  position: relative;
  height: 100%;
  width: 100%;
}
</style>
