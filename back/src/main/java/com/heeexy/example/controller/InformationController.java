package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/information")
public class InformationController {
    @Autowired
    private InformationService informationService;

    @GetMapping("/list")
    public JSONObject lists() {
        return informationService.list();
    }

    @PostMapping("/select")
    public JSONObject select(@RequestBody JSONObject jsonObject) {
        return informationService.list(jsonObject);
    }

    @PostMapping("/update")
    public JSONObject update(@RequestBody JSONObject jsonObject) {
        return informationService.update(jsonObject);
    }

    @PostMapping("/delete")
    public JSONObject delete(@RequestBody JSONObject jsonObject) {
        return informationService.update(jsonObject);
    }
}
