package com.xmg.p2p.mgrsite.controller;

import com.xmg.p2p.base.domain.SystemDictionary;
import com.xmg.p2p.base.query.SystemDictionaryQueryObject;
import com.xmg.p2p.base.service.ISystemDictionaryItemService;
import com.xmg.p2p.base.service.ISystemDictionaryService;
import com.xmg.p2p.base.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lanxw on 2017/7/27.
 */
@Controller
public class SystemDictionaryController {
    @Autowired
    private ISystemDictionaryService systemDictionaryService;
    @RequestMapping("systemDictionary_list")
    public String systemDictionary_list(Model model, @ModelAttribute("qo") SystemDictionaryQueryObject qo){
        model.addAttribute("pageResult",systemDictionaryService.queryPage(qo));
        return "systemdic/systemDictionary_list";
    }
    @RequestMapping("systemDictionary_update")
    @ResponseBody
    public AjaxResult systemDictionaryUpdate(SystemDictionary systemDictionary){
        AjaxResult result = null;
        try{
            systemDictionaryService.saveOrUpdate(systemDictionary);
            result = new AjaxResult(true,"处理成功");
        }catch(Exception e){
            result = new AjaxResult(e.getMessage());
        }
        return result;
    }
}
