<script>
export default {
    data() {
        return {
            list: [], //表格的数据
            listLoading: false, //数据加载等待动画
            isIndeterminate: false,
            singleOrder: {
                goodsId: "",
                goodsName: "",
                goodsPrice: "",
                goodsSaleStatus: "",
                pictureUrl: "",
                goodsStyle: "",
                purchaseNumbers: "",
                subTotal: "",
                customerName: "",
                customerPhoneNumber: "",
                customerAddress: "",
                createdTime: "",
                totalPrice: "",
                paymentWay: "",
                customerComment: "",
                businessName: ""
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
                url: "/orderManage/singleOrder",
                method: "get",
                params: this.orderNumbers
            }).then(data => {
                this.listLoading = false;
                this.list = data;
                this.singleOrder.
            });
        }
    }
};
</script>