package com.xmg.p2p.base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.service.ISystemDictionaryItemService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.AjaxResult;

/**
 * Created by lanxw on 2017/7/27.
 */
@Controller
public class BasicInfoCotnroller {
	@Autowired
	private IUserinfoService userinfoService;
	@Autowired
	private ISystemDictionaryItemService systemDictionaryItemService;

	@RequestMapping("basicInfo")
	public String basicInfo(Model model) {
		model.addAttribute("userinfo", userinfoService.getCurrent());
		model.addAttribute("educationBackgrounds", systemDictionaryItemService.listItemBySn("educationBackground"));
		model.addAttribute("incomeGrades", systemDictionaryItemService.listItemBySn("incomeGrade"));
		model.addAttribute("marriages", systemDictionaryItemService.listItemBySn("marriage"));
		model.addAttribute("kidCounts", systemDictionaryItemService.listItemBySn("kidCount"));
		model.addAttribute("houseConditions", systemDictionaryItemService.listItemBySn("houseCondition"));
		return "userInfo";
	}

	@RequestMapping("basicInfo_save")
	@ResponseBody
	public AjaxResult basicInfoSave(Userinfo userinfo) {
		AjaxResult result = null;
		try {
			userinfoService.updateBasicInfo(userinfo);
			result = new AjaxResult(true, "保存成功");
		} catch (Exception e) {
			result = new AjaxResult(e.getMessage());
		}
		return result;
	}
}
