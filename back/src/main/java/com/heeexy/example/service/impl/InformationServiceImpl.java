package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heeexy.example.dao.InformationDao;
import com.heeexy.example.service.InformationService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class InformationServiceImpl implements InformationService {
    @Autowired
    private InformationDao informationDao;

    @Override
    public JSONObject list() {
        List<JSONObject> list = informationDao.list();
        return CommonUtil.successJson(list);
    }

    @Override
    public JSONObject list(JSONObject jsonObject) {
        JSONObject json = informationDao.list(jsonObject);
        Date time1=json.getDate("update_time");
        Date time2=json.getDate("create_time");
        String t1=(time1.getYear()+1900)+"年"+time1.getMonth()+"月"+time1.getDay()+"日"+time1.getHours()+" H"+time1.getMinutes()+" min"+time1.getSeconds()+"s";
        String t2=(time2.getYear()+1900)+"年"+time2.getMonth()+"月"+time2.getDay()+"日"+time2.getHours()+" H"+time2.getMinutes()+" min"+time2.getSeconds()+"s";
        json.put("update_time",t1);
        json.put("create_time",t2);
        return CommonUtil.successJson(json);
    }

    @Override
    public JSONObject update(JSONObject jsonObject) {
        informationDao.update(jsonObject);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject delete(JSONObject jsonObject) {
        informationDao.delete(jsonObject);
        return CommonUtil.successJson();
    }
}
