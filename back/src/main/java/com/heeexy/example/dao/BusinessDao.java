package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;
import java.util.List;

public interface BusinessDao {
    JSONObject selectBusiness(JSONObject jsonObject);

    void updateBusinessInformation(JSONObject jsonObject);

    int pageNum(JSONObject jsonObject);

    List<JSONObject> allNum(JSONObject jsonObject);

    JSONObject selectCommodityId(JSONObject jsonObject);

    void addCommodity(JSONObject jsonObject);

    void deleteCommodity(JSONObject jsonObject);

    List<JSONObject> selectCommodityName(JSONObject jsonObject);

    JSONObject selectId(JSONObject jsonObject);

    void updateCommodity(JSONObject id);

    int selectListCount(JSONObject jsonObject);

    int categoryCount(JSONObject jsonObject);

    List<JSONObject> categoryList(JSONObject jsonObject);

    JSONObject categoryName(JSONObject jsonObject);

    void addCategory(JSONObject jsonObject);

    void deleteCategory(JSONObject jsonObject);

    void updateCategory(JSONObject jsonObject);

    int selectCategoryListCount(JSONObject jsonObject);

    List<JSONObject> selectCategoryName(JSONObject jsonObject);

    JSONObject selectCategoryId(JSONObject jsonObject);

    void importExcel(JSONObject jsonObject0);

    List<JSONObject> mallCategoryDisplay(JSONObject jsonObject);

    int[] selectAllId();

    List<JSONObject> walkHorseLight(JSONObject jsonObject);

    JSONObject commodityDetails(JSONObject jsonObject);

    List<JSONObject> shoppingCart(JSONObject jsonObject);

    void addShoppingCart(JSONObject jsonObject);

    void deleteShoppingCart(JSONObject jsonObject);
}
