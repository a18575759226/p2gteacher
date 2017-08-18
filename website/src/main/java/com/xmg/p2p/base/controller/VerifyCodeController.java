package com.xmg.p2p.base.controller;

import com.xmg.p2p.base.service.IVerifyCodeService;
import com.xmg.p2p.base.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lanxw on 2017/7/26.
 */
@Controller
public class VerifyCodeController {
    @Autowired
    private IVerifyCodeService verifyCodeService;
    @RequestMapping("sendVerifyCode")
    @ResponseBody
    public AjaxResult sendVerifyCode(String phoneNumber){
        AjaxResult result = null;
        try{
            verifyCodeService.sendVerifyCode(phoneNumber);
            result = new AjaxResult(true,"发送成功");
        }catch(Exception e){
            e.printStackTrace();
            result = new AjaxResult(e.getMessage());
        }
        return result;
    }
}
