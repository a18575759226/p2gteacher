package com.xmg.p2p.base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.business.query.BidRequestQueryObject;
import com.xmg.p2p.business.service.IBidRequestService;

/**
 * 首页控制器
 * 
 * @author xmg
 *
 */
@Controller
public class IndexController {

	@Autowired
	private IBidRequestService bidRequestService;

	/**
	 * 首页
	 */
	@RequestMapping("index")
	public String index(Model model) {
		model.addAttribute("bidRequests", this.bidRequestService.listIndex(5));
		return "main";
	}

	/**
	 * 加载投资列表外面的框框页面
	 */
	@RequestMapping("invest")
	public String investFrame() {
		return "invest";
	}

	/**
	 * 加载投资列表的列表内容
	 */
	@RequestMapping("invest_list")
	public String investList(@ModelAttribute("qo") BidRequestQueryObject qo, Model model) {
		if (qo.getBidRequestState() == -1) {
			qo.setBidRequestStates(new int[] { BidConst.BIDREQUEST_STATE_BIDDING, BidConst.BIDREQUEST_STATE_PAYING_BACK,
					BidConst.BIDREQUEST_STATE_COMPLETE_PAY_BACK });
		}
		model.addAttribute("pageResult", this.bidRequestService.query(qo));
		return "invest_list";
	}

}
