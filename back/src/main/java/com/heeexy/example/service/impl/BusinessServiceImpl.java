package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.BusinessDao;
import com.heeexy.example.service.BusinessService;
import com.heeexy.example.util.CommonUtil;
import com.heeexy.example.util.constants.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BusinessServiceImpl implements BusinessService {
    @Autowired
    private BusinessDao businessDao;

    @Override
    public JSONObject selectBusiness(JSONObject jsonObject) {
        JSONObject jsonObject1 = businessDao.selectBusiness(jsonObject);
        return CommonUtil.successJson(jsonObject1);
    }

    @Override
    public JSONObject updateBusinessInformation(JSONObject jsonObject) {
        businessDao.updateBusinessInformation(jsonObject);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject listCommodity(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        //封装分页数据
        int count = businessDao.pageNum(jsonObject);
        //统计当前条件下的查询数据总个数
        Session session = SecurityUtils.getSubject().getSession();
        JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_PERMISSION);
        jsonObject.put("userId", userInfo.get("userId"));
        List<JSONObject> list = businessDao.allNum(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    @Override
    public JSONObject addCommodity(JSONObject jsonObject) {
        Session session = SecurityUtils.getSubject().getSession();
        JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_PERMISSION);
        jsonObject.put("userId", userInfo.get("userId"));
        businessDao.selectCommodityId(jsonObject);
        businessDao.selectCategoryId(jsonObject);
        if (null != businessDao.selectCommodityId(jsonObject)) {
            System.out.println("该商品已录入！");
            return CommonUtil.successJson();
        }
        ;
        if (null == businessDao.selectCategoryId(jsonObject)) {
            System.out.println("未找到该商品种类，请先录入商品种类！");
            return CommonUtil.successJson();
        }
        jsonObject.put("pictureUrl", jsonObject.get("pictureUrl"));
        String[] categoryInformation = {"goodsName", "goodsPrice", "goodsNumbers", "goodsCategoryId",
                "goodsSaleStatus", "goodsBrief"};
        for (int i = 0; i < categoryInformation.length; i++) {
            jsonObject.put(categoryInformation[i], jsonObject.get(categoryInformation[i]));
        }

        businessDao.addCommodity(jsonObject);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject deleteCommodity(JSONObject jsonObject) {
        Session session = SecurityUtils.getSubject().getSession();
        JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_PERMISSION);
        jsonObject.put("userId", userInfo.get("userId"));
        businessDao.deleteCommodity(jsonObject);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject updateCommodity(JSONObject jsonObject) {
        JSONObject id = businessDao.selectId(jsonObject);
        jsonObject.put("id", id);
        Session session = SecurityUtils.getSubject().getSession();
        JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_PERMISSION);
        jsonObject.put("userId", userInfo.get("userId"));
        jsonObject.put("pictureUrl", jsonObject.get("pictureUrl"));
        String[] categoryInformation = {"goodsName", "goodsPrice", "goodsNumbers", "goodsCategoryId",
                "goodsSaleStatus", "goodsBrief"};
        for (int i = 0; i < categoryInformation.length; i++) {
            jsonObject.put(categoryInformation[i], jsonObject.get(categoryInformation[i]));
        }
        System.out.println(jsonObject);
        businessDao.updateCommodity(jsonObject);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject selectCommodity(JSONObject jsonObject) {
        Session session = SecurityUtils.getSubject().getSession();
        JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_PERMISSION);
        jsonObject.put("userId", userInfo.get("userId"));
        int count = businessDao.selectListCount(jsonObject);
        List<JSONObject> list = businessDao.selectCommodityName(jsonObject);
        System.out.println(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    @Override
    public JSONObject listCategory(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = businessDao.categoryCount(jsonObject);
        Session session = SecurityUtils.getSubject().getSession();
        JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_PERMISSION);
        jsonObject.put("userId", userInfo.get("userId"));
        List<JSONObject> list = businessDao.categoryList(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    @Override
    public JSONObject addCategory(JSONObject jsonObject) {
        Session session = SecurityUtils.getSubject().getSession();
        JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_PERMISSION);
        jsonObject.put("userId", userInfo.get("userId"));
        JSONObject categoryName = businessDao.categoryName(jsonObject);
        System.out.println(categoryName);
        if (null != categoryName) {
            System.out.println("该商品种类已录入！");
            return CommonUtil.successJson();
        }
        String[] categoryInformation = {"goodsCategoryName", "goodsCategoryId"};
        for (int i = 0; i < categoryInformation.length; i++) {
            jsonObject.put(categoryInformation[i], jsonObject.get(categoryInformation[i]));
        }
        businessDao.addCategory(jsonObject);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject deleteCategory(JSONObject jsonObject) {
        Session session = SecurityUtils.getSubject().getSession();
        JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_PERMISSION);
        jsonObject.put("userId", userInfo.get("userId"));
        businessDao.deleteCategory(jsonObject);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject updateCategory(JSONObject jsonObject) {
        JSONObject id = businessDao.selectId(jsonObject);
        jsonObject.put("id", id);
        Session session = SecurityUtils.getSubject().getSession();
        JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_PERMISSION);
        jsonObject.put("userId", userInfo.get("userId"));
        String[] categoryInformation = {"goodsCategoryName", "goodsCategoryId"};
        for (int i = 0; i < categoryInformation.length; i++) {
            jsonObject.put(categoryInformation[i], jsonObject.get(categoryInformation[i]));
        }
        businessDao.updateCategory(jsonObject);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject selectCategory(JSONObject jsonObject) {
        Session session = SecurityUtils.getSubject().getSession();
        JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_PERMISSION);
        jsonObject.put("userId", userInfo.get("userId"));
        int count = businessDao.selectCategoryListCount(jsonObject);
        List<JSONObject> list = businessDao.selectCategoryName(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    @Override
    public JSONObject importExcel(JSONObject jsonObject1) {
        for (int i = 0; i < jsonObject1.getInteger("rows"); i++) {
            JSONObject jsonObject0 = (JSONObject) jsonObject1.get("jsonObject" + i);
            System.out.println(jsonObject0);
            businessDao.importExcel(jsonObject0);
        }
        return CommonUtil.successJson();
    }

    /*
     * 获取分类搜索的清单与数据
     *
     * */
    @Override
    public JSONObject mallCategoryDisplay(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = businessDao.categoryCount(jsonObject);
        Session session = SecurityUtils.getSubject().getSession();
        JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_PERMISSION);
        jsonObject.put("userId", userInfo.get("userId"));
        jsonObject.put("goodsCategoryId", jsonObject.get("0"));
        List<JSONObject> list = businessDao.mallCategoryDisplay(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    @Override
    public JSONObject walkHorseLight(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = businessDao.categoryCount(jsonObject);
        List<JSONObject> list = businessDao.walkHorseLight(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    @Override
    public JSONObject commodityDetails(JSONObject jsonObject) {

        JSONObject jsonObject1 = businessDao.commodityDetails(jsonObject);
        return CommonUtil.successJson(jsonObject1);
    }

    /**
     * @return com.alibaba.fastjson.JSONObject
     * @Author Tao:3301846209@qq.com
     * @Description: {
     * //TODO
     * }                购物车
     * @Date 11:29 2020/12/19
     * @Param [jsonObject]
     **/
    @Override
    public JSONObject shoppingCart(JSONObject jsonObject) {
        Session session = SecurityUtils.getSubject().getSession();
        JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_PERMISSION);
        jsonObject.put("userId", userInfo.get("userId"));
        CommonUtil.fillPageParam(jsonObject);
        int count = 0;
        List<JSONObject> list = businessDao.shoppingCart(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    @Override
    public JSONObject addShoppingCart(JSONObject jsonObject) {
        Session session = SecurityUtils.getSubject().getSession();
        JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_PERMISSION);
        jsonObject.put("userId", userInfo.get("userId"));
        businessDao.addShoppingCart(jsonObject);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject deleteShoppingCart(JSONObject jsonObject) {
        Session session = SecurityUtils.getSubject().getSession();
        JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_PERMISSION);
        jsonObject.put("userId", userInfo.get("userId"));
        businessDao.deleteShoppingCart(jsonObject);
        return CommonUtil.successJson();
    }
}