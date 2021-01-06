package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

public interface PersonService {

    JSONObject personInformation(JSONObject jsonObject);

    JSONObject recharge(JSONObject jsonObject);

    JSONObject selectPassword(JSONObject jsonObject);

    JSONObject sendMessage(JSONObject jsonObject);

    JSONObject updatePassword(JSONObject jsonObject);

    JSONObject updateInformation(JSONObject jsonObject);

    JSONObject selectPersonInformation(JSONObject jsonObject);
}
