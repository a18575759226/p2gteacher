package com.xmg.p2p.mgrsite.controller;

import com.xmg.p2p.base.domain.SystemDictionary;
import com.xmg.p2p.base.domain.SystemDictionaryItem;
import com.xmg.p2p.base.query.SystemDictionaryItemQueryObject;
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
public class SystemDictionaryItemController {
    @Autowired
    private ISystemDictionaryService systemDictionaryService;
    @Autowired
    private ISystemDictionaryItemService systemDictionaryItemService;
    @RequestMapping("systemDictionaryItem_list")
    public String systemDictionaryItem_list(Model model, @ModelAttribute("qo") SystemDictionaryItemQueryObject qo){
        model.addAttribute("pageResult",systemDictionaryItemService.queryPage(qo));
        model.addAttribute("systemDictionaryGroups",systemDictionaryService.selectAll());
        return "systemdic/systemDictionaryItem_list";
    }
    @RequestMapping("systemDictionaryItem_update")
    @ResponseBody
    public AjaxResult systemDictionaryItemUpdate(SystemDictionaryItem systemDictionaryItem){
        AjaxResult result = null;
        try{
            systemDictionaryItemService.saveOrUpdate(systemDictionaryItem);
            result = new AjaxResult(true,"处理成功");
        }catch(Exception e){
            e.printStackTrace();
            result = new AjaxResult(e.getMessage());
        }
        return result;
    }
}
