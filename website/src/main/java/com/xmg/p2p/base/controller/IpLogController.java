package com.xmg.p2p.base.controller;

import com.xmg.p2p.base.query.IpLogQueryObject;
import com.xmg.p2p.base.service.IIpLogService;
import com.xmg.p2p.base.util.RequireLogin;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lanxw on 2017/7/25.
 */
@Controller
public class IpLogController {
    @Autowired
    private IIpLogService ipLogService;
    @RequireLogin
    @RequestMapping("ipLog")
    public String ipLog_list(@ModelAttribute("qo") IpLogQueryObject qo, Model model){
        //设置当前用户的username到qo中
        qo.setUsername(UserContext.getCurrent().getUsername());
        model.addAttribute("pageResult",ipLogService.queryPage(qo));
        return "iplog_list";
    }
}
