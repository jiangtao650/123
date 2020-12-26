package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.RegisterDao;
import com.heeexy.example.service.RegisterService;
import com.heeexy.example.util.CommonUtil;
import com.heeexy.example.util.constants.ErrorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private RegisterDao registerDao;

    @Override
    public JSONObject insertCustomer(JSONObject jsonObject) {
        int exist = registerDao.queryExistUsername(jsonObject);
        if (exist > 0) {
            return CommonUtil.errorJson(ErrorEnum.E_10009);
        }

            registerDao.insertUserCustomer(jsonObject);
            registerDao.selectId(jsonObject);
        int roleId =  jsonObject.getInteger("roleId");
            JSONObject newPerson = new JSONObject();
            newPerson.put("newPersonId", registerDao.selectId(jsonObject));
        if (roleId == 7) {
            newPerson.put("customerName",jsonObject.get("nickname"));
            String[] customerForm = {"nickname", "customerPhoneNumber", "customerBalance"};
            for (int i = 0; i < customerForm.length; i++) {
                newPerson.put(customerForm[i], jsonObject.get(customerForm[i]));
            }
            System.out.println(newPerson);
            registerDao.insertCustomer(newPerson);
        }else if(roleId==6){
            String[] businessForm ={"businessName","businessPhone","businessAddress","businessOpenTime","businessCloseTime","businessBrief"};
            for(int i=0;i<businessForm.length;i++){
                newPerson.put(businessForm[i],jsonObject.get(businessForm[i]));
            }
            registerDao.insertBusiness(newPerson);
        }
        return CommonUtil.successJson();
    }

}
