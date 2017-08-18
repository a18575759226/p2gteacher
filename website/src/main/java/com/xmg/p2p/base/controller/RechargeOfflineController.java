package com.xmg.p2p.base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmg.p2p.base.util.AjaxResult;
import com.xmg.p2p.business.domain.RechargeOffline;
import com.xmg.p2p.business.service.IPlatformBankinfoService;
import com.xmg.p2p.business.service.IRechargeOfflineService;

/**
 * 线下充值控制器
 * 
 * @author xmg
 *
 */
@Controller
public class RechargeOfflineController {

	@Autowired
	private IRechargeOfflineService offlineService;

	@Autowired
	private IPlatformBankinfoService platfromBankinfoService;

	/**
	 * 线下充值界面S
	 */
	@RequestMapping("recharge")
	public String rechargePage(Model model) {
		model.addAttribute("banks", this.platfromBankinfoService.listAll());
		return "recharge";
	}

	/**
	 * 提交线下充值单
	 */
	@RequestMapping("recharge_save")
	@ResponseBody
	public AjaxResult apply(RechargeOffline offline) {
		this.offlineService.apply(offline);
		return new AjaxResult();
	}

}
