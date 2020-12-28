package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.PersonService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping("/personInformation")
    public JSONObject personInformation(HttpServletRequest request){
        JSONObject jsonObject= CommonUtil.request2Json(request);
        return personService.personInformation(jsonObject);
    }
    @PostMapping("/recharge")
    public JSONObject recharge(@RequestBody JSONObject jsonObject){
        return personService.recharge(jsonObject);
    }
    @GetMapping("/selectPassword")
    public JSONObject selectPassword(HttpServletRequest request){
        JSONObject jsonObject=CommonUtil.request2Json(request);
        return personService.selectPassword(jsonObject);
    }
    @GetMapping("/sendMessage")
    public JSONObject sendMessage(HttpServletRequest request){
        JSONObject jsonObject=CommonUtil.request2Json(request);
        return personService.sendMessage(jsonObject);
    }
    @PostMapping("/updatePassword")
    public JSONObject updatePassword(@RequestBody JSONObject jsonObject){
        return personService.updatePassword(jsonObject);
    }
}
