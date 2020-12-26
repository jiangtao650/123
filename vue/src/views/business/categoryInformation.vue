<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :model="listQuery" label-width="80px" :inline="true">
        <el-form-item>
          <el-button type="primary" icon="plus" @click="showCreate">添加</el-button>
        </el-form-item>
        <el-form-item>
        <el-input v-model="input" placeholder="请输入内容" clearable></el-input>
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
      <el-table-column align="center" prop="goodsCategoryName" label="商品种类" style="width: 60px;"></el-table-column>
      <el-table-column align="center" prop="goodsCategoryId" label="种类ID" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="管理" width="200">
        <template slot-scope="scope">
          <el-button type="primary" icon="edit" @click="updateClick(scope.$index)">修改</el-button>
          <el-button type="danger" icon="edit" @click="deleteCategory(scope.$index)">删除</el-button>
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
        ref="tempCategoryPage"
        class="small-space"
        :model="tempCategoryPage"
        label-position="left"
        label-width="100px"
        style="width: 300px; margin-left:50px;"
        size=mini
      >
        <el-form-item label="商品种类" prop="goodsCategoryName" >
          <el-input type="text" v-model="tempCategoryPage.goodsCategoryName"></el-input>
        </el-form-item>
        <el-form-item label="种类ID" prop="goodsCategoryId">
          <el-input type="text" v-model="tempCategoryPage.goodsCategoryId"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="success" @click="addPageTable">创 建</el-button>
      </div>
    </el-dialog>
    <el-dialog :title="textMap[dialog]" :visible.sync="dialogUpdateVisible">
      <el-form
        :rules="rules"
        ref="tempCategoryPage"
        class="middle-space"
        :model="tempCategoryPage"
        label-position="left"
        label-width="100px"
        style="width: 300px; margin-left:50px;"
        size="mini"
      >
        <el-form-item label="商品种类" prop="goodsCategoryName">
          <el-input type="text" v-model="tempCategoryPage.goodsCategoryName"></el-input>
        </el-form-item>
        <el-form-item label="种类ID" prop="goodsCategoryId">
          <el-input type="text" v-model="tempCategoryPage.goodsCategoryId"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialog=='updateCategory'" type="success" @click="updateCategory">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  data() {
    return {
      input:"",
      totalCount: 0, //分页组件--数据总条数
      list: [], //表格的数据
      listLoading: false, //数据加载等待动画
      listQuery: {
        pageNum: 1, //页码
        pageRow: 10, //每页条数
        input:""
      },
      dialogStatus: "create",
      dialog: "update",
      dialogFormVisible:false,
      dialogUpdateVisible: false,
      textMap: {
        update: "编辑",
        create: "创建商品种类",
        updateCategory:"修改商品种类"
      },
      tempCategoryPage: {
        goodsCategoryName: "",
        goodsCategoryId: "",
      },
      rules: {
        goodsCategoryName: [
          {
            required: true,
            message: "请输入商品种类名称",
            trigger: "change"
          }
        ],
        goodsCategoryId: [
          {
            required: true,
            message: "请输入商品种类ID",
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
        url: "/addBusiness/listCategory",
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
    updateClick($index){
        console.log(this.list[$index])   
        this.tempCategoryPage.goodsCategoryName= this.list[$index].goodsCategoryName;
        this.tempCategoryPage.goodsCategoryId=this.list[$index].goodsCategoryId;
        this.dialog = "updateCategory";
        this.dialogUpdateVisible = true;
    },
    addPageTable() {
      this.$refs["tempCategoryPage"].validate(valid => {
        if (valid) {
          this.api({
            url: "/addBusiness/addCategory",
            method: "post",
            data: this.tempCategoryPage
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
    deleteCategory($index) {
      this.api({
        url: "/addBusiness/deleteCategory",
        method: "post",
        data: { goodsCategoryName: this.list[$index].goodsCategoryName }
      }).then(() => {
        this.getList();
        this.dialogFormVisible = false;
      });
    },
    updateCategory($index) {
      let _vue = this;
      this.$confirm("确定保存?", "提示", {
        confirmButtonText: "确定",
        showCancelButton: false,
        type: "warning"
      }).then(() => {
        this.$refs["tempCategoryPage"].validate(valid => {
          if (valid) {
            console.log(this.tempCategoryPage)
            this.api({
              url: "/addBusiness/updateCategory",
              method: "post",
              data: this.tempCategoryPage  
            }).then(result => {
              this.getList();
              this.dialogUpdateVisible = false;
              this.$message({
                message: result,
                type: "success"
              });
            });
          } else {
            console.log("error submit!!");
            return false;
          }
        });
      });
    },
    select(){
      this.listQuery.input= this.input,
      this.api({
        url: "/addBusiness/selectCategory",
        method: "post",
        data: this.listQuery
      }).then((result) => {
        this.listLoading = false;
        this.list=result.list;
        console.log( this.list);
        this.dialogUpdateVisible = false;
        this.totalCount = data.totalCount;
        this.list = result.list;
        this.totalCount = result.totalCount;
      });
    },
  }
};
</script>
