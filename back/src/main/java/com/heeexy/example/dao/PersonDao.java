package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface PersonDao {
    JSONObject personInformation(JSONObject jsonObject);

    void recharge(JSONObject jsonObject);

    JSONObject selectCustomerBalance(JSONObject jsonObject);

    JSONObject selectPassword(JSONObject jsonObject);

    List<JSONObject> selectOrderInformation(JSONObject jsonObject);
}
