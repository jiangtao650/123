package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

public interface OrderService {

    JSONObject submitOrder(JSONObject jsonObject);

    JSONObject listOrder(JSONObject jsonObject);

    JSONObject submitPaymentWay(JSONObject jsonObject);

    JSONObject singleOrder(JSONObject jsonObject);
}
