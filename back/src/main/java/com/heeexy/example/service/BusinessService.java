package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.vo.ImageVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface BusinessService {

    JSONObject selectBusiness(JSONObject jsonObject);

    JSONObject updateBusinessInformation(JSONObject jsonObject);

    JSONObject listCommodity(JSONObject jsonObject);

    JSONObject addCommodity(JSONObject jsonObject);

    JSONObject deleteCommodity(JSONObject jsonObject);

    JSONObject updateCommodity(JSONObject jsonObject);

    JSONObject selectCommodity(JSONObject jsonObject);

    JSONObject listCategory(JSONObject jsonObject);

    JSONObject addCategory(JSONObject jsonObject);

    JSONObject deleteCategory(JSONObject jsonObject);

    JSONObject updateCategory(JSONObject jsonObject);

    JSONObject selectCategory(JSONObject jsonObject);

    JSONObject importExcel(JSONObject jsonObject1);

    JSONObject mallCategoryDisplay(JSONObject jsonObject);

    JSONObject walkHorseLight(JSONObject jsonObject);

    JSONObject commodityDetails(JSONObject jsonObject);

    JSONObject shoppingCart(JSONObject jsonObject);

    JSONObject addShoppingCart(JSONObject jsonObject);

    JSONObject deleteShoppingCart(JSONObject jsonObject);
}