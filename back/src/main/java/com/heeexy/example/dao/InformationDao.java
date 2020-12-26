package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;
import java.util.List;

public interface InformationDao {
    List<JSONObject> list();
    JSONObject list(JSONObject jsonObject);
    void update(JSONObject jsonObject);
    void delete(JSONObject jsonObject);
}
