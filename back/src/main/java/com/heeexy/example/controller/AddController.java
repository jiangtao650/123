package com.heeexy.example.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.AddService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/addNewPage")
public class AddController {
    @Autowired
    private AddService addService;

    @GetMapping("/addList")
    public JSONObject addList(HttpServletRequest request) {
        JSONObject jsonObject = CommonUtil.request2Json(request);
        return addService.listPage(jsonObject);
    }

    @PostMapping("/deletePage")
    public JSONObject deletePage(@RequestBody JSONObject jsonObject) {
        return addService.deletePage(jsonObject);
    }

    @PostMapping("/addPage")
    public JSONObject addPage(@RequestBody JSONObject jsonObject) {
        return addService.addPage(jsonObject);
    }

    @PostMapping("/selectContent")
    public JSONObject selectContent(@RequestBody JSONObject jsonObject) {
        return addService.selectContent(jsonObject);
    }}
