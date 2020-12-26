package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.idService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/id")
public class idController {
    @Autowired
    private idService idService;
    @RequiresPermissions("id:add")
    @PostMapping("/addPage")
    public JSONObject addPage(@RequestBody JSONObject jsonObject){
        return idService.addPage(jsonObject);
    }

}
