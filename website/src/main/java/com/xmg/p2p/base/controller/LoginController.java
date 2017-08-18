package com.xmg.p2p.base.controller;

import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.service.ILogininfoService;
import com.xmg.p2p.base.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lanxw on 2017/7/23.
 */
@Controller
public class LoginController {
    @Autowired
    private ILogininfoService logininfoService;
    @RequestMapping("login")
    @ResponseBody
    public AjaxResult login(String username, String password, HttpServletRequest request){
        AjaxResult result = null;
        Logininfo logininfo = logininfoService.login(username,password,request.getRemoteAddr(),Logininfo.USERTYPE_USER);
        if(logininfo!=null){
            result = new AjaxResult(true,"登录成功");
        }else{
            result = new AjaxResult("账号密码有误!");
        }
        return result;
    }
}
