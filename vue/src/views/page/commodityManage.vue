<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :model="listQuery" label-width="80px" :inline="true">
        <el-form-item>
          <el-button type="primary" icon="plus" @click="showCreate">添加</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="plus" @click="select">搜索</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-table
      :data="list"
      v-loading.body="listLoading"
      element-loading-text="拼命加载中"
      border
      fit
      highlight-current-row
    >
      <el-table-column align="center" label="序号" width="80">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"></span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="menu_code" label="Code" style="width: 60px;"></el-table-column>
      <el-table-column align="center" prop="menu_name" label="Name" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="管理" width="200">
        <template slot-scope="scope">
          <el-button type="danger" icon="edit" @click="deleteAddPage(scope.$index)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="listQuery.pageNum"
      :page-size="listQuery.pageRow"
      :total="totalCount"
      :page-sizes="[10, 20, 50, 100]"
      layout="total, sizes, prev, pager, next, jumper"
    ></el-pagination>
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form
        :rules="rules"
        ref="tempAddPage"
        class="small-space"
        :model="tempAddPage"
        label-position="left"
        label-width="60px"
        style="width: 300px; margin-left:50px;"
      >
        <el-form-item label="Code" prop="menu_code">
          <el-input type="text" v-model="tempAddPage.menu_code"></el-input>
        </el-form-item>
        <el-form-item label="Name" prop="menu_name">
          <el-input type="text" v-model="tempAddPage.menu_name"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="success" @click="addPageTable">创 建</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  data() {
    return {
      totalCount: 0, //分页组件--数据总条数
      list: [], //表格的数据
      listLoading: false, //数据加载等待动画
      listQuery: {
        pageNum: 1, //页码
        pageRow: 50, //每页条数
        name: ""
      },
      dialogStatus: "create",
      dialogFormVisible: false,
      textMap: {
        update: "编辑",
        create: "创建权限"
      },
      tempAddPage: {
        menu_code: "",
        menu_name: ""
      },
      rules: {
        menu_code: [
          {
            required: true,
            message: "请输入Code",
            trigger: "change"
          }
        ],
        menu_name: [
          {
            required: true,
            message: "请输入Name",
            trigger: "change"
          }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    select() {},
    getList() {
      this.listLoading = true;
      this.api({
        url: "/addNewPage/addList",
        method: "get",
        params: this.listQuery
      }).then(data => {
        this.listLoading = false;
        this.list = data.list;
        this.totalCount = data.totalCount;
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
    showCreate() {
      //显示新增对话框
      this.dialogStatus = "create";
      this.dialogFormVisible = true;
    },
    addPageTable() {
      this.$refs["tempAddPage"].validate(valid => {
        if (valid) {
          this.api({
            url: "/addNewPage/addPage",
            method: "post",
            data: this.tempAddPage
          }).then(() => {
            this.getList();
            this.dialogFormVisible = false;
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    deleteAddPage($index) {
      this.api({
        url: "/addNewPage/deletePage",
        method: "post",
        data: { menu_code: this.list[$index].menu_code }
      }).then(() => {
        this.getList();
        this.dialogFormVisible = false;
      });
    }
  }
};
</script>
