package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.IdDao;
import com.heeexy.example.service.idService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class idServiceImpl implements idService {
    @Autowired
    private IdDao idDao;

    @Override
    public JSONObject addPage(JSONObject jsonObject) {
        /*1.判断code是否已经存在，先进查询code，判断是否存在，存在则返回提示，不存在则继续
         * 2.插入
         *   id，取最大值并且（/100+1）*100+1
         *   permissioncode   permissionname
         *   requiredpermission
         * 3.
         * */
        JSONObject num = idDao.findcode(jsonObject);
        if ( null!=num ) {
            System.out.println("已存在！");
            return CommonUtil.successJson();
        }
        int id1 = (idDao.findId() / 100 + 1) * 100;

        jsonObject.put("id1", id1 + 1);
        jsonObject.put("id2", id1 + 2);
        jsonObject.put("id3", id1 + 3);
        jsonObject.put("pc1", jsonObject.getString("menu_code") + "list");
        jsonObject.put("pc2", jsonObject.getString("menu_code") + "add");
        jsonObject.put("pc3", jsonObject.getString("menu_code") + "update");
        idDao.InsertidDao(jsonObject);
        return CommonUtil.successJson();
    }

}
