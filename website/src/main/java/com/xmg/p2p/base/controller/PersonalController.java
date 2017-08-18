package com.xmg.p2p.base.controller;

import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.AjaxResult;
import com.xmg.p2p.base.util.RequireLogin;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lanxw on 2017/7/25.
 */
@Controller
public class PersonalController {
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IUserinfoService userinfoService;
    @RequireLogin
    @RequestMapping("personal")
    public String personal(Model model){
        model.addAttribute("account",accountService.getCurrent());
        model.addAttribute("userinfo",userinfoService.getCurrent());
        return "personal";
    }
    @RequestMapping("bindPhone")
    @ResponseBody
    public AjaxResult bindPhone(String phoneNumber,String verifyCode){
        AjaxResult result = null;
        try{
            userinfoService.bindPhone(phoneNumber,verifyCode);
            result = new AjaxResult(true,"绑定成功");
        }catch(Exception e){
            e.printStackTrace();
            result = new AjaxResult(e.getMessage());
        }
        return result;
    }
    @RequestMapping("bindEmail")
    public String bindEmail(Model model,String key){
        AjaxResult result = null;
        try{
            userinfoService.bindEmail(key);
            model.addAttribute("success",true);
        }catch(Exception e){
            model.addAttribute("success",false);
            model.addAttribute("msg",e.getMessage());
        }
        return "checkmail_result";
    }
}
