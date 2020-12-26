package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

public interface RegisterDao {
    int queryExistUsername(JSONObject jsonObject);

    void insertUserCustomer(JSONObject jsonObject);

    String selectId(JSONObject jsonObject);

    void insertCustomer(JSONObject jsonObject);

    void insertBusiness(JSONObject newPerson);
}
