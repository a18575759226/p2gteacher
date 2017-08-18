package com.xmg.p2p.mgrsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmg.p2p.base.query.RealAuthQueryObject;
import com.xmg.p2p.base.service.IRealAuthService;
import com.xmg.p2p.base.util.AjaxResult;

/**
 * 后台实名认证审核控制器
 * 
 * @author xmg
 *
 */
@Controller
public class RealAuthController {

	@Autowired
	private IRealAuthService realAuthService;

	/**
	 * 实名认证审核列表
	 */
	@RequestMapping("realAuth")
	public String realAuthList(@ModelAttribute("qo") RealAuthQueryObject qo, Model model) {
		model.addAttribute("pageResult", this.realAuthService.query(qo));
		return "realAuth/list";
	}

	/**
	 * 实名认证审核
	 */
	@RequestMapping("realAuth_audit")
	@ResponseBody
	public AjaxResult audit(Long id, String remark, int state) {
		this.realAuthService.audit(id, remark, state);
		return new AjaxResult();
	}

}
