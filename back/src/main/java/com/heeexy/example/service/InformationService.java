package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

public interface InformationService {
    JSONObject list();

    JSONObject list(JSONObject jsonObject);

    JSONObject update(JSONObject jsonObject);

    JSONObject delete(JSONObject jsonObject);

}
