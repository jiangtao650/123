package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.PersonDao;
import com.heeexy.example.service.PersonService;
import com.heeexy.example.util.CommonUtil;
import com.heeexy.example.util.ThreadTimeout;
import com.heeexy.example.util.constants.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class PsersonServiceImpl implements PersonService {

    @Resource
    TemplateEngine templateEngine;
    @Autowired
    private PersonDao personDao;
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public JSONObject personInformation(JSONObject jsonObject) {
        Session session = SecurityUtils.getSubject().getSession();
        JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_PERMISSION);
        jsonObject.put("userId", userInfo.get("userId"));
        JSONObject jsonObject1 = personDao.personInformation(jsonObject);
        return CommonUtil.successJson(jsonObject1);
    }

    @Override
    public JSONObject recharge(JSONObject jsonObject) {
        Session session = SecurityUtils.getSubject().getSession();
        JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_PERMISSION);
        jsonObject.put("userId", userInfo.get("userId"));
        personDao.recharge(jsonObject);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject selectPassword(JSONObject jsonObject) {
        Session session = SecurityUtils.getSubject().getSession();
        JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_PERMISSION);
        jsonObject.put("userId", userInfo.get("userId"));
        JSONObject jsonObject1 = personDao.selectPassword(jsonObject);
        return CommonUtil.successJson(jsonObject1);
    }

    @Override
    public JSONObject sendMessage(JSONObject jsonObject) {
        JSONObject jsonObject2 = new JSONObject();
        String email = null;
        String mail = null;
        if (jsonObject.getString("subject").equals("邮箱验证码")) {
            //生成六位随机数
            Random random = new Random();
            String code = "";
            for (int i = 0; i < 6; i++) {
                code += random.nextInt(10);
            }
            email = jsonObject.getString("email");
            mail = "【MaetS Games】您的验证码是：" + code + ",该验证码10分钟内有效，请勿泄露！";
            jsonObject2.put("code", code);
        } else {
            Session session = SecurityUtils.getSubject().getSession();
            JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_PERMISSION);
            jsonObject.put("userId", userInfo.get("userId"));
            Random random1 = new Random();
            String code1 = "";
            for (int i = 0; i < 11; i++) {
                code1 += random1.nextInt(10);
            }

            //根据顾客id获取顾客的个人信息
            JSONObject jsonObject1 = personDao.personInformation(jsonObject);
            //搜索到的邮箱号码
            email = jsonObject1.getString("email");
            //利用传回的订单号进行数据库的查询
            List<JSONObject> list = personDao.selectOrderInformation(jsonObject);
            System.out.println(list);
            //通过context构造模板中变量需要的值
            Context ctx = new Context();
            //为模板中的变量赋值
            ctx.setVariable("customerName", jsonObject1.getString("nickName"));
            ctx.setVariable("invoiceId", code1);
            ctx.setVariable("orderNumbers", jsonObject.getString("orderNumbers"));
            ctx.setVariable("customerEmail", list.get(0).getString("customerAddress"));
            ctx.setVariable("createdTime", list.get(0).getString("createdTime"));
            ctx.setVariable("totalPrice", list.get(0).getDoubleValue("totalPrice"));
            ctx.setVariable("lists", list);
            //使用TemplateEngine对模板进行渲染
            mail = templateEngine.process("thymeleaf.html", ctx);
        }
        //建立邮件消息创建对象
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        //发送者
        mailMessage.setFrom("3301846209@qq.com");
        //接受者
        mailMessage.setTo(email);
        //发送的标题
        mailMessage.setSubject(jsonObject.getString("subject"));
        //发送的内容

        mailMessage.setText(mail);

        try {
            /*异步调用多线程*/
            ThreadTimeout threadTimeout = new ThreadTimeout();
            threadTimeout.setJavaMailSender(javaMailSender);
            threadTimeout.setSimpleMailMessage(mailMessage);
            threadTimeout.start();

            System.out.println("发送成功！" + new Date().getTime());
            jsonObject2.put("sendMessage", jsonObject.getString("subject") + "已发送至您的邮箱，请注意查收！");
        } catch (Exception e) {
            System.out.println("发送失败");
            jsonObject2.put("sendMessage", jsonObject.getString("subject") + "发送失败！");
        }
        return CommonUtil.successJson(jsonObject2);
    }

    @Override
    public JSONObject updatePassword(JSONObject jsonObject) {
        String content = null;
        int row = personDao.updatePassword(jsonObject);
        if (row == 0) {
            content = "该用户名不存在，请注册该用户名！";
        } else {
            content = "恭喜您，密码修改成功！";
        }
        return CommonUtil.successJson(content);
    }

    @Override
    public JSONObject updateInformation(JSONObject jsonObject) {
        Session session = SecurityUtils.getSubject().getSession();
        JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_PERMISSION);
        jsonObject.put("userId", userInfo.get("userId"));
       jsonObject.put("birthday",jsonObject.getSqlDate("birthday"));
        personDao.updateInformation(jsonObject);
        personDao.updateCustomerName(jsonObject);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject selectPersonInformation(JSONObject jsonObject) {
        Session session = SecurityUtils.getSubject().getSession();
        JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_PERMISSION);
        jsonObject.put("userId", userInfo.get("userId"));
        JSONObject jsonObject1 = personDao.selectPeronInformation(jsonObject);
        return CommonUtil.successJson(jsonObject1);
    }
}
