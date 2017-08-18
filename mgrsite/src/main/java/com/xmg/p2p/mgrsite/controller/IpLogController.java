package com.xmg.p2p.mgrsite.controller;

import com.xmg.p2p.base.query.IpLogQueryObject;
import com.xmg.p2p.base.service.IIpLogService;
import com.xmg.p2p.base.service.ILogininfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lanxw on 2017/7/26.
 */
@Controller
public class IpLogController {
    @Autowired
    private IIpLogService ipLogService;
    @RequestMapping("ipLog")
    public String ipLog_list(@ModelAttribute("qo") IpLogQueryObject qo, Model model){
        model.addAttribute("pageResult",ipLogService.queryPage(qo));
        return "ipLog/list";
    }
}
