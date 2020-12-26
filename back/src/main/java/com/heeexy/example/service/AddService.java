package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

public interface AddService {

    JSONObject listPage(JSONObject jsonObject);

    JSONObject deletePage(JSONObject jsonObject);

    JSONObject addPage(JSONObject jsonObject);

    JSONObject selectContent(JSONObject jsonObject);
}
