package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.PersonService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping("/personInformation")
    public JSONObject personInformation(HttpServletRequest request) {
        JSONObject jsonObject = CommonUtil.request2Json(request);
        return personService.personInformation(jsonObject);
    }

    @PostMapping("/recharge")
    public JSONObject recharge(@RequestBody JSONObject jsonObject) {
        return personService.recharge(jsonObject);
    }

    @GetMapping("/selectPassword")
    public JSONObject selectPassword(HttpServletRequest request) {
        JSONObject jsonObject = CommonUtil.request2Json(request);
        return personService.selectPassword(jsonObject);
    }

    @GetMapping("/sendMessage")
    public JSONObject sendMessage(HttpServletRequest request) {
        JSONObject jsonObject = CommonUtil.request2Json(request);
        return personService.sendMessage(jsonObject);
    }

    @PostMapping("/updatePassword")
    public JSONObject updatePassword(@RequestBody JSONObject jsonObject) {
        return personService.updatePassword(jsonObject);
    }

    @PostMapping("/updateInformation")
    public JSONObject updateInformation(@RequestBody JSONObject jsonObject) {
        return personService.updateInformation(jsonObject);
    }
    @GetMapping("/selectPersonInformation")
    public JSONObject selectPersonInformation(HttpServletRequest request){
        JSONObject jsonObject=CommonUtil.request2Json(request);
        return personService.selectPersonInformation(jsonObject);
    }
}
