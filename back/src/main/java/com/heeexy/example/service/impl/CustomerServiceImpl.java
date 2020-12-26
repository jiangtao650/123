package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.CustomerDao;
import com.heeexy.example.service.CustomerService;
import com.heeexy.example.util.CommonUtil;
import com.heeexy.example.util.constants.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao customerDao;

    @Override
    public JSONObject addCustomer(JSONObject jsonObject) {
        Session session= SecurityUtils.getSubject().getSession();
        JSONObject userInfo=(JSONObject) session.getAttribute(Constants.SESSION_USER_INFO);
        jsonObject.put("userId",userInfo.get("userId"));
        JSONObject customerName=customerDao.checkSingleName(jsonObject);
        if(null!=customerName){
            System.out.println("顾客姓名重复，请重新输入！");
            return CommonUtil.successJson();
        }
        jsonObject.put("customerName",jsonObject.get("customerName"));
        jsonObject.put("customerPhoneNumber",jsonObject.get("customerPhoneNumber"));
        customerDao.addCustomer(jsonObject);
        return CommonUtil.successJson();
    }
}
