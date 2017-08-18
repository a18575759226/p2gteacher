package com.xmg.p2p.base.controller;

import com.xmg.p2p.base.service.IEmailService;
import com.xmg.p2p.base.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lanxw on 2017/7/26.
 */
@Controller
public class EmailController {
    @Autowired
    private IEmailService emailService;
    @RequestMapping("sendEmail")
    @ResponseBody
    public AjaxResult sendEmail(String email){
        AjaxResult result = null;
        try{
            emailService.sendEmail(email);
            result = new AjaxResult(true,"发送邮件成功");
        }catch(Exception e){
            e.printStackTrace();
            result = new AjaxResult(e.getMessage());
        }
        return result;
    }
}
