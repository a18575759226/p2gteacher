package com.xmg.p2p.mgrsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmg.p2p.base.util.AjaxResult;
import com.xmg.p2p.business.domain.PlatformBankinfo;
import com.xmg.p2p.business.query.PlatformBankinfoQueryObject;
import com.xmg.p2p.business.service.IPlatformBankinfoService;

/**
 * 平台账户信息控制器
 * 
 * @author xmg
 *
 */
@Controller
public class PlatformBankinfoController {

	@Autowired
	private IPlatformBankinfoService platformBankinfoService;
	
	@RequestMapping("companyBank_list")
	public String list(@ModelAttribute("qo") PlatformBankinfoQueryObject qo, Model model) {
		model.addAttribute("pageResult",this.platformBankinfoService.query(qo));
		return "platformbankinfo/list";
	}
	
	/**
	 * 添加或者编辑
	 */
	@RequestMapping("companyBank_update")
	@ResponseBody
	public AjaxResult saveOrUpdate(PlatformBankinfo p){
		this.platformBankinfoService.saveOrUpdate(p);
		return new AjaxResult();
	}

}
