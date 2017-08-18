package com.xmg.p2p.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lanxw on 2017/7/26.
 */
//localhost:8088/sendSms.do
@Controller
public class MeiLianController {
    @RequestMapping("sendSms")
    @ResponseBody
    public String sendSms(String phoneNumber,String username,String password,String apiKey,String content){
        System.out.println("需要发送的手机号码:"+phoneNumber);
        System.out.println("平台账号:"+username);
        System.out.println("平台密码:"+password);
        System.out.println("平台密钥:"+apiKey);
        System.out.println("发送内容:"+content);
        System.out.println("短信网关让运营商发送短信到指定用户的手机上......");
        return "success:msgid";
    }
}
