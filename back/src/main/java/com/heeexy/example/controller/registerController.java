package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class registerController {
    @Autowired
    private RegisterService registerService;
    @PostMapping("/insertNewPerson")
    public JSONObject insertCustomer(@RequestBody JSONObject jsonObject){
        return registerService.insertCustomer(jsonObject);
    }
}
