package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IdDao {
    void InsertidDao(JSONObject jsonObject);

    JSONObject findcode(JSONObject code);

    int findId();
}
