package com.xmg.p2p.mgrsite.controller;

import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.service.ILogininfoService;
import com.xmg.p2p.base.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 后台登陆控制器
 */
@Controller
public class LoginController {
    @Autowired
    private ILogininfoService logininfoService;
    @RequestMapping("login")
    @ResponseBody
    public AjaxResult login(String username, String password, HttpServletRequest request){
        AjaxResult result =null;
        Logininfo login = logininfoService.login(username, password, request.getRemoteAddr(), Logininfo.USERTYPE_MANAGER);
        if(login==null){
            result = new AjaxResult("账号密码有误");
        }else{
            result = new AjaxResult(true,"登陆成功");
        }
        return result;
    }
    @RequestMapping("main")
    public String main(){
        return "main";
    }
}
