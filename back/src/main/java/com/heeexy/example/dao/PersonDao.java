package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface PersonDao {
    JSONObject personInformation(JSONObject jsonObject);

    void recharge(JSONObject jsonObject);

    JSONObject selectCustomerBalance(JSONObject jsonObject);

    JSONObject selectPassword(JSONObject jsonObject);

    List<JSONObject> selectOrderInformation(JSONObject jsonObject);

    int updatePassword(JSONObject jsonObject);

    void insertInformation(JSONObject jsonObject);

    int selectPersonId(JSONObject jsonObject);

    void updateInformation(JSONObject jsonObject);

    JSONObject selectPeronInformation(JSONObject jsonObject);

    void updateCustomerName(JSONObject jsonObject);
}
