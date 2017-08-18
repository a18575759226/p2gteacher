package com.xmg.p2p.mgrsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.service.IRealAuthService;
import com.xmg.p2p.base.service.IUserFileService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.AjaxResult;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.business.domain.BidRequest;
import com.xmg.p2p.business.query.BidRequestQueryObject;
import com.xmg.p2p.business.service.IBidRequestService;

/**
 * 后台借款相关控制器
 * 
 * @author xmg
 *
 */
@Controller
public class BidRequestController {

	@Autowired
	private IBidRequestService bidRequestService;

	@Autowired
	private IUserinfoService userinfoService;

	@Autowired
	private IRealAuthService realAuthService;

	@Autowired
	private IUserFileService userFileService;

	/**
	 * 发标前审核列表
	 * 
	 * @return
	 */
	@RequestMapping("bidrequest_publishaudit_list")
	public String publishList(@ModelAttribute("qo") BidRequestQueryObject qo, Model model) {
		qo.setBidRequestState(BidConst.BIDREQUEST_STATE_PUBLISH_PENDING);
		model.addAttribute("pageResult", this.bidRequestService.query(qo));
		return "bidrequest/publish_audit";
	}

	/**
	 * 发标前审核
	 */
	@RequestMapping("bidrequest_publishaudit")
	@ResponseBody
	public AjaxResult publishAudit(Long id, int state, String remark) {
		this.bidRequestService.publishAudit(id, state, remark);
		return new AjaxResult();
	}

	/**
	 * 后台查看借款明细
	 */
	@RequestMapping("borrow_info")
	public String borrowInfo(Long id, Model model) {
		BidRequest br = this.bidRequestService.get(id);
		Userinfo borrowUser = this.userinfoService.get(br.getCreateUser().getId());

		model.addAttribute("bidRequest", br);
		model.addAttribute("userInfo", borrowUser);
		model.addAttribute("audits", this.bidRequestService.listAuditsByBidRequestId(id));
		model.addAttribute("realAuth", this.realAuthService.get(borrowUser.getRealAuthId()));
		model.addAttribute("userFiles", this.userFileService.listFilesByUser(borrowUser.getId()));
		return "bidrequest/borrow_info";
	}

}
