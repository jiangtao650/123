package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.WebSocketServer.WebSocketServer;
import com.heeexy.example.dao.OrderDao;
import com.heeexy.example.service.OrderService;
import com.heeexy.example.util.CommonUtil;
import com.heeexy.example.util.constants.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Resource
    WebSocketServer webSocketServer;

    /**
     * @return com.alibaba.fastjson.JSONObject
     * @Author Tao:3301846209@qq.com
     * @Description: {
     * //TODO
     * }
     * @Date 10:53 2020/12/21
     * @Param [jsonObject]
     **/
    @Override
    public JSONObject submitOrder(JSONObject jsonObject) {
        Session session = SecurityUtils.getSubject().getSession();
        JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_PERMISSION);
        /*获取订单号：前八位为日期，后面12位为0+11位uuid*/
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        String time = format.format(date);
        int hashCodeV = UUID.randomUUID()
                .toString().hashCode();
        if (hashCodeV < 0) {
            hashCodeV = -hashCodeV;
        }

        jsonObject.getJSONObject("settlement").get("address");//地址
        jsonObject.getJSONObject("settlement").get("customer");//顾客名称
        jsonObject.getJSONObject("settlement").get("phoneNumbers");//顾客电话
        jsonObject.getJSONObject("settlement").get("paymentWay");//支付方式

        jsonObject.getJSONArray("orderList").getJSONObject(0).get("goodsName");
        jsonObject.getJSONArray("orderList").getJSONObject(0).get("goodsPrice");
        jsonObject.getJSONArray("orderList").getJSONObject(0).get("goodsSalesStatus");
        jsonObject.getJSONArray("orderList").getJSONObject(0).get("pictureUrl");
        jsonObject.getJSONArray("orderList").getJSONObject(0).get("goodsStyle");
        jsonObject.getJSONArray("orderList").getJSONObject(0).get("purchaseNumbers");
        jsonObject.getJSONArray("orderList").getJSONObject(0).get("customerComment");
        int[] subtotal = new int[jsonObject.getJSONArray("orderList").size()];
        for (int i = 0; i < jsonObject.getJSONArray("orderList").size(); i++) {
            /*注入顾客id*/
            jsonObject.getJSONArray("orderList").getJSONObject(i).put("customerId", userInfo.get("userId"));
            /*注入订单号*/
            jsonObject.getJSONArray("orderList").getJSONObject(i).put("orderNumbers", time + String.format("%011d", hashCodeV));
            /*注入顾客地址*/
            jsonObject.getJSONArray("orderList").getJSONObject(i).put("customerAddress", jsonObject.getJSONObject("settlement").get("address"));
            /*注入顾客联系方式*/
            jsonObject.getJSONArray("orderList").getJSONObject(i).put("customerPhoneNumber", jsonObject.getJSONObject("settlement").get("phoneNumbers"));
            /*注入顾客姓名*/
            jsonObject.getJSONArray("orderList").getJSONObject(i).put("customerName", jsonObject.getJSONObject("settlement").get("customer"));
            /*注入支付方式*/
            jsonObject.getJSONArray("orderList").getJSONObject(i).put("paymentWay", jsonObject.getJSONObject("settlement").get("paymentWay"));
            /*搜索并注入商家id*/
            jsonObject.getJSONArray("orderList").getJSONObject(i).put("businessId", orderDao.selectBusinessId(jsonObject.getJSONArray("orderList").getJSONObject(i)));
            /*获取该商品的购买花费小结*/
            jsonObject.getJSONArray("orderList").getJSONObject(i).put("subtotal",
                    (jsonObject.getJSONArray("orderList").getJSONObject(i).getInteger("goodsPrice"))
                            * (jsonObject.getJSONArray("orderList").getJSONObject(i).getInteger("purchaseNumbers")));
            /*为每一个订单中注入当次下单的总价格*/
            jsonObject.getJSONArray("orderList").getJSONObject(i).put("totalPrice", jsonObject.getInteger("totalPrice"));
            orderDao.createOrder(jsonObject.getJSONArray("orderList").getJSONObject(i));

        }
        orderDao.createOrderList(jsonObject.getJSONArray("orderList").getJSONObject(0));
        return CommonUtil.successJson(jsonObject.getJSONArray("orderList").getJSONObject(0));
    }

    @Override
    public JSONObject listOrder(JSONObject jsonObject) {
        Session session = SecurityUtils.getSubject().getSession();
        JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_PERMISSION);
        jsonObject.put("userId", userInfo.get("userId"));
        List<JSONObject> list = orderDao.selectCustomerInformation(jsonObject);
        return CommonUtil.successJson(list);
    }

    @Override
    public JSONObject submitPaymentWay(JSONObject jsonObject) {
        orderDao.submitPaymentWay(jsonObject);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject singleOrder(JSONObject jsonObject) {
        Session session = SecurityUtils.getSubject().getSession();
        JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_PERMISSION);
        jsonObject.put("userId", userInfo.get("userId"));
        CommonUtil.fillPageParam(jsonObject);
        int count = orderDao.singleOrderCount(jsonObject);
        List<JSONObject> list = orderDao.singleOrder(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    @Override
    public JSONObject orderManage(JSONObject jsonObject) {
        Session session = SecurityUtils.getSubject().getSession();
        JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_PERMISSION);
        jsonObject.put("userId", userInfo.get("userId"));
        CommonUtil.fillPageParam(jsonObject);
        int count = orderDao.orderCount(jsonObject);
        List<JSONObject> list = orderDao.orderList(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    @Override
    public JSONObject sendMessageToBusiness(JSONObject jsonObject) {
        String toBusinessId = orderDao.orderSelectBusinessId(jsonObject);
        String message = "您有新的订单，请及时处理！！！";
        try {
            WebSocketServer.sendMessageTo(message, toBusinessId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return CommonUtil.successJson();
    }

}
