package com.heeexy.example.dao;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface AddDao {

    List<JSONObject> pageNum(JSONObject jsonObject);

    int pageAllNum(JSONObject jsonObject);

    void deletePage(JSONObject jsonObject);

    JSONObject findCode(JSONObject jsonObject);

    int findId();

    void InsertIdDao(JSONObject jsonObject);

    JSONObject selectContent(JSONObject jsonObject);

    int numSelect(JSONObject jsonObject);

    List<JSONObject> numListSelect(JSONObject jsonObject);
}
