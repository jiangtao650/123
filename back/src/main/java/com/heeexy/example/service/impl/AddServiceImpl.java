package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.AddDao;
import com.heeexy.example.service.AddService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AddServiceImpl implements AddService {
    @Autowired
    private AddDao addDao;
    @Override
    public JSONObject listPage(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        //封装分页数据
        int count = addDao.pageAllNum(jsonObject);
        //统计当前条件下的查询数据总个数
        List<JSONObject> list = addDao.pageNum(jsonObject);
        //获取当前条件下所有数据
        return CommonUtil.successPage(jsonObject, list, count);
    }

    @Override
    public JSONObject addPage(JSONObject jsonObject) {
        JSONObject num = addDao.findCode(jsonObject);
        if ( null!=num ) {
            System.out.println("已存在！");
            return CommonUtil.successJson();
        }
        int id1 = (addDao.findId() / 100 + 1) * 100;

        jsonObject.put("id1", id1 + 1);
        jsonObject.put("id2", id1 + 2);
        jsonObject.put("id3", id1 + 3);
        jsonObject.put("pc1", jsonObject.getString("menu_code") + ":list");
        jsonObject.put("pc2", jsonObject.getString("menu_code") + ":add");
        jsonObject.put("pc3", jsonObject.getString("menu_code") + ":update");
        addDao.InsertIdDao(jsonObject);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject selectContent(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        //封装分页数据
        int count = addDao.numSelect(jsonObject);
        //统计当前条件下的查询数据总个数
        List<JSONObject> list = addDao.numListSelect(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }
    @Override
    public JSONObject deletePage(JSONObject jsonObject) {
        addDao.deletePage(jsonObject);
        return CommonUtil.successJson();
    }
}
