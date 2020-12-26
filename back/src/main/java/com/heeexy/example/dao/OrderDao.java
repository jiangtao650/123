package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface OrderDao {
    JSONObject selectCustomerAddress(JSONObject jsonObject);

    void createOrder(JSONObject jsonObject);

    List<JSONObject> selectCustomerInformation(JSONObject jsonObject);

    int submitPaymentWay(JSONObject jsonObject);

    Object selectBusinessId(Object object);

    List<JSONObject> singleOrder(JSONObject jsonObject);
}
