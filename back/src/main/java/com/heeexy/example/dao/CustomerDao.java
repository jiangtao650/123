package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

public interface CustomerDao {
    JSONObject checkSingleName(JSONObject jsonObject);

    void addCustomer(JSONObject jsonObject);
}
