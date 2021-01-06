package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.OrderService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/orderManage")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/submitOrder")
    public JSONObject submitOrder(@RequestBody JSONObject jsonObject) {
        return orderService.submitOrder(jsonObject);
    }
    @GetMapping("/listOrder")
        public JSONObject listOrder(HttpServletRequest request){
        JSONObject jsonObject=CommonUtil.request2Json(request);
        return orderService.listOrder(jsonObject);
    }
    @PostMapping("/submitPaymentWay")
    public JSONObject submitPaymentWay(@RequestBody JSONObject jsonObject){
        return orderService.submitPaymentWay(jsonObject);
    }
    @GetMapping("/singleOrder")
    public JSONObject singleOrder(HttpServletRequest request){
        JSONObject jsonObject=CommonUtil.request2Json(request);
        return orderService.singleOrder(jsonObject);
    }
    @GetMapping("/orderManage")
    public JSONObject orderManage(HttpServletRequest request){
        JSONObject jsonObject=CommonUtil.request2Json(request);
        return orderService.orderManage(jsonObject);
    }
    @GetMapping("/sendMessageToBusiness")
    public JSONObject sendMessageToBusiness(HttpServletRequest request){
        JSONObject jsonObject=CommonUtil.request2Json(request);
        return orderService.sendMessageToBusiness(jsonObject);
    }
}
