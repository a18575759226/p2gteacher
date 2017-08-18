package com.xmg.p2p.mgrsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmg.p2p.base.query.VedioAuthQueryObject;
import com.xmg.p2p.base.service.IVedioAuthService;
import com.xmg.p2p.base.util.AjaxResult;

/**
 * 视频审核控制器
 * 
 * @author xmg
 *
 */
@Controller
public class VedioAuthController {

	@Autowired
	private IVedioAuthService vedioAuthService;

	@RequestMapping("vedioAuth")
	public String vedioAuthList(@ModelAttribute("qo") VedioAuthQueryObject qo, Model model) {
		model.addAttribute("pageResult", this.vedioAuthService.query(qo));
		return "vedioAuth/list";
	}
	
	/**
	 * 视频审核
	 */
	@RequestMapping("vedioAuth_audit")
	@ResponseBody
	public AjaxResult audit(long loginInfoValue,int state,String remark){
		this.vedioAuthService.audit(loginInfoValue,state,remark);
		return new AjaxResult();
	}

}
