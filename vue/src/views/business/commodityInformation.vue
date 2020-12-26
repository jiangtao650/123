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
        <el-form-item>
          <el-upload
            ref="upload"
            action="/api/addBusiness/importExcel"
            :auto-upload="false"
            accept=".xlsx, .xls"
            :on-success="uploadSuccess"
            :on-error="uploadError"
          >
            <el-button type="primary" slot="trigger">批量导入</el-button>
            <el-button type="success" @click="submitUpload">上传文件</el-button>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>
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
      <el-table-column align="center" prop="goodsName" label="商品名称" style="width: 60px;"></el-table-column>
      <el-table-column align="center" prop="goodsPrice" label="单价" style="width: 60px;"></el-table-column>
      <el-table-column align="center" prop="goodsNumbers" label="库存" style="width: 60px;"></el-table-column>
      <el-table-column align="center" prop="goodsCategoryName" label="所属种类" style="width: 60px;"></el-table-column>
      <el-table-column align="center" prop="goodsSaleStatus" label="销售状态" style="width: 60px;">
        <template slot-scope="scope">
          <span v-if="scope.row.goodsSaleStatus==1">上架</span>
          <span v-if="scope.row.goodsSaleStatus==2">下架</span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="goodsBrief" label="商品简介" style="width: 60px;"></el-table-column>
      <el-table-column align="center" prop="pictureUrl" label="商品图片" style="width: 60px;">
        <template slot-scope="scope">
          <img :src="scope.row.pictureUrl" width="60" height="90" />
        </template>
      </el-table-column>
      <el-table-column align="center" label="管理" width="200">
        <template slot-scope="scope">
          <el-button type="primary" icon="edit" @click="updatePage(scope.$index)">修改</el-button>
          <el-button type="danger" icon="edit" @click="deleteAddPage(scope.$index)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 以上为展示内容 -->
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
        label-width="100px"
        style="width: 300px; margin-left:50px;"
        size="mini"
      >
        <el-form-item label="商品名称" prop="goodsName">
          <el-input type="text" v-model="tempAddPage.goodsName"></el-input>
        </el-form-item>
        <el-form-item label="价格" prop="goodsPrice">
          <el-input type="text" v-model="tempAddPage.goodsPrice"></el-input>
        </el-form-item>
        <el-form-item label="数量" prop="goodsNumbers">
          <el-input-number
            v-model="tempAddPage.goodsNumbers"
            @change="handleChange"
            :min="0"
            :max="1000"
            size="small"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="所属种类" prop="goodsCategoryId">
          <el-select v-model="tempAddPage.goodsCategoryId" placeholder="请选择商品种类">
            <el-option
              v-for="tempAddPage in options"
              :key="tempAddPage.goodsCategoryId"
              :label="tempAddPage.goodsCategoryName"
              :value="tempAddPage.goodsCategoryId"
              :disabled="tempAddPage.disabled"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="商品状态" prop="goodsSaleStatus">
          <el-select v-model="tempAddPage.goodsSaleStatus" placeholder="请选择商品状态">
            <el-option
              v-for="item in option"
              :key="item.value"
              :label="item.label"
              :value="item.value"
              :disabled="item.disabled"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="简介" prop="goodsBrief">
          <el-input
            type="textarea"
            :rows="2"
            placeholder="请输入商家简介……"
            v-model="tempAddPage.goodsBrief"
            :autosize="{ minRows: 2, maxRows: 4}"
          ></el-input>
        </el-form-item>
        <el-form-item label="商品图片" prop="pictureName">
          <el-upload
            ref="upload"
            action="/api/addBusiness/pictureUpload"
            :on-preview="handlePreview"
            :limit="1"
            :on-exceed="handleExceed"
            :on-success="imageSuccess"
            accept="image/*"
          >
            <el-button size="small" type="primary">上传图片</el-button>
            <div slot="tip" class="el-upload__tip">仅支持jpg、png格式</div>
            <!-- on-preview 点击已上传的文件链接时的钩子, 可以通过 file.response 拿到服务端返回数据 
          on-remove	文件列表移除文件时的钩子
          file-list	上传的文件列表
          on-success	文件上传成功时的钩子
            on-exceed	文件超出个数限制时的钩子-->
            <!-- <div slot="tip" class="el-upload__tip">仅支持jpg、png格式</div> -->
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-if="dialogStatus=='create'" type="success" @click="addPageTable">创 建</el-button>
        <el-button @click="dialogFormVisible = false">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 以上为添加按钮的功能 -->
    <el-dialog :title="textMap[dialog]" :visible.sync="dialogUpdateVisible">
      <el-form
        :rules="rules"
        ref="tempAddPage"
        class="middle-space"
        :model="tempAddPage"
        label-position="left"
        label-width="100px"
        style="width: 300px; margin-left:50px;"
        size="mini"
      >
        <el-form-item label="商品名称" prop="goodsName">
          <el-input type="text" v-model="tempAddPage.goodsName"></el-input>
        </el-form-item>
        <el-form-item label="价格" prop="goodsPrice">
          <el-input type="text" v-model="tempAddPage.goodsPrice"></el-input>
        </el-form-item>
        <el-form-item label="数量" prop="goodsNumbers">
          <el-input-number
            v-model="tempAddPage.goodsNumbers"
            @change="handleChange"
            :min="0"
            :max="1000"
            size="small"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="所属种类" prop="goodsCategoryId">
          <el-select v-model="tempAddPage.goodsCategoryId" placeholder="请选择商品种类">
            <el-option
              v-for="item in options"
              :key="item.goodsCategoryId"
              :label="item.goodsCategoryName"
              :value="item.goodsCategoryId"
              :disabled="item.disabled"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="商品状态" prop="goodsSaleStatus">
          <el-select v-model="tempAddPage.goodsSaleStatus" placeholder="请选择商品状态">
            <el-option
              v-for="item in option"
              :key="item.value"
              :label="item.label"
              :value="item.value"
              :disabled="item.disabled"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="简介" prop="goodsBrief">
          <el-input
            type="textarea"
            :rows="2"
            placeholder="请输入商家简介……"
            v-model="tempAddPage.goodsBrief"
            :autosize="{ minRows: 2, maxRows: 4}"
          ></el-input>
        </el-form-item>
        <el-form-item label="商品图片" prop="pictureName">
          <el-upload
            ref="upload"
            action="/api/addBusiness/pictureUpload"
            :on-preview="handlePreview"
            :limit="1"
            :on-exceed="handleExceed1"
            :on-success="imageSuccess"
            accept="image/*"
          >
            <el-button size="small" type="primary">更换图片</el-button>
            <div slot="tip" class="el-upload__tip">仅支持jpg、png格式</div>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogUpdateVisible = false">取 消</el-button>
        <el-button v-if="dialog=='updateCommodity'" type="success" @click="updateCommodity">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  data() {
    return {
      dialogVisible: false,
      dialogImageUrl: "",
      input: "",
      change: "",
      textarea: "",
      totalCount: 0, //分页组件--数据总条数
      list: [], //表格的数据
      listLoading: false, //数据加载等待动画
      listQuery: {
        pageNum: 1, //页码
        pageRow: 10, //每页条数
        name: "",
        input: ""
      },
      fileList: [{}],
      options: [
        {
          value: "goodsCategoryName",
          label: "goodsCategoryId"
        }
      ],
      option: [
        {
          value: "1",
          label: "上架"
        },
        {
          value: "2",
          label: "下架"
        }
      ],
      dialogStatus: "create",
      dialog: "update",
      dialogFormVisible: false,
      dialogUpdateVisible: false,
      textMap: {
        update: "编辑",
        create: "添加商品",
        updateCommodity: "商品信息修改"
      },
      tempAddPage: {
        goodsName: "",
        goodsPrice: "",
        goodsNumbers: "",
        goodsCategoryId: "",
        goodsSaleStatus: "",
        goodsBrief: "",
        goodsCategoryName: "",
        pictureUrl: "",
        commodityId: ""
      },
      rules: {
        goodsName: [
          {
            required: true,
            message: "请输入商品名称",
            trigger: "change"
          }
        ],
        goodsPrice: [
          {
            required: true,
            message: "请输入商品价格",
            trigger: "change"
          }
        ],
        goodsNumbers: [
          {
            required: true,
            message: "请输入商品数量",
            trigger: "change"
          }
        ],
        goods_category_id: [
          {
            required: true,
            message: "请输入商品所属种类",
            trigger: "change"
          }
        ],
        goodsSaleStatus: [
          {
            required: true,
            message: "请输入商品销售状态",
            trigger: "change"
          }
        ],
        goodsBrief: [
          {
            required: true,
            message: "请输入商品简介",
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
    submitUpload() {
      this.$refs.upload.submit();
    },
    uploadSuccess() {
      this.$message.warning("文件导入成功!");
    },
    uploadError() {
      this.$message.warning("文件导入失败！");
    },
    handlePreview(file, fileList) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
      console.log("handlePreview");
      // 选中时执行
    },
    handleExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择 1 个文件，本次选择了 ${
          files.length
        } 个文件，共选择了 ${files.length + fileList.length} 个文件`
      );
    },
    handleExceed1(files, fileList) {
      this.$message.warning(
        `当前限制选择 5 个文件，本次选择了 ${
          files.length
        } 个文件，共选择了 ${files.length + fileList.length} 个文件`
      );
    },
    imageSuccess(param) {
      this.tempAddPage.pictureUrl = param.info.pictureUrl;
      console.log(this.tempAddPage.pictureUrl);
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}?`);
    },
    handleChange(value) {},
    getList() {
      this.listLoading = true;
      this.api({
        url: "/addBusiness/listCommodity",
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
      this.api({
        url: "/addBusiness/listCategory",
        method: "get"
      }).then(result => {
        this.options = result.list;
      });
    },
    updatePage($index) {
      this.api({
        url: "/addBusiness/listCategory",
        method: "get"
      }).then(result => {
        this.options = result.list;
      });
      (this.tempAddPage.goodsName = this.list[$index].goodsName),
        (this.tempAddPage.goodsPrice = this.list[$index].goodsPrice),
        (this.tempAddPage.goodsNumbers = this.list[$index].goodsNumbers),
        (this.tempAddPage.goodsCategoryId = this.list[$index].goodsCategoryId),
        (this.tempAddPage.goodsSaleStatus = this.list[$index].goodsSaleStatus),
        (this.tempAddPage.goodsBrief = this.list[$index].goodsBrief),
        (this.tempAddPage.commodityId = this.list[$index].commodityId),
        (this.tempAddPage.pictureUrl = this.list[$index].pictureUrl),
        (this.dialog = "updateCommodity");
      this.dialogUpdateVisible = true;
    },
    addPageTable() {
      this.$refs["tempAddPage"].validate(valid => {
        if (valid) {
          this.api({
            url: "/addBusiness/addCommodity",
            method: "post",
            data: this.tempAddPage
          }).then(() => {
            console.log(this.tempAddPage);
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
        url: "/addBusiness/deleteCommodity",
        method: "post",
        data: { goodsName: this.list[$index].goodsName }
      }).then(() => {
        this.getList();
        this.dialogFormVisible = false;
      });
    },
    updateCommodity($index) {
      let _vue = this;
      this.$confirm("确定保存?", "提示", {
        confirmButtonText: "确定",
        showCancelButton: false,
        type: "warning"
      }).then(() => {
        this.$refs["tempAddPage"].validate(valid => {
          if (valid) {
            this.api({
              url: "/addBusiness/updateCommodity",
              method: "post",
              data: this.tempAddPage
            }).then(result => {
              console.log(this.tempAddPage);
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
    select() {
      (this.listQuery.input = this.input),
        this.api({
          url: "/addBusiness/selectCommodity",
          method: "post",
          data: this.listQuery
        }).then(result => {
          this.listLoading = false;
          this.dialogFormVisible = false;
          this.list = result.list; //list作为其中的key 直接用点来获取
          this.totalCount = result.totalCount;
        });
    }
  }
};
</script>