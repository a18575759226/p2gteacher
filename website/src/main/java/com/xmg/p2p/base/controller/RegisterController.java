package com.xmg.p2p.base.controller;

import com.xmg.p2p.base.service.ILogininfoService;
import com.xmg.p2p.base.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lanxw on 2017/7/22.
 */
@Controller
public class RegisterController {
    @Autowired
    private ILogininfoService logininfoService;
    @RequestMapping("register")
    @ResponseBody
    public AjaxResult register(String username,String password){
        AjaxResult result = null;
        try{
            logininfoService.register(username,password);
            result = new AjaxResult(true,"注册成功");
        }catch(Exception e){
            result = new AjaxResult(e.getMessage());
        }
        return result;
    }
    @RequestMapping("checkUsername")
    @ResponseBody
    public boolean checkUsername(String username){
        boolean result = false;
        result = logininfoService.checkUsername(username);
        return result;
    }
}
