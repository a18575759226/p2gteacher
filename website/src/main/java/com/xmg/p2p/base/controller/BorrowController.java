package com.xmg.p2p.base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.service.IRealAuthService;
import com.xmg.p2p.base.service.IUserFileService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.business.domain.BidRequest;
import com.xmg.p2p.business.service.IBidRequestService;

/**
 * Created by lanxw on 2017/7/27.
 * 
 * 借款相关控制器
 */
@Controller
public class BorrowController {
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IUserinfoService userinfoService;
	@Autowired
	private IBidRequestService bidRequestService;
	@Autowired
	private IRealAuthService realAuthService;
	@Autowired
	private IUserFileService userFileService;

	/**
	 * 我要借款首页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("borrow")
	public String borrow_list(Model model) {
		Logininfo current = UserContext.getCurrent();
		if (current == null) {
			return "redirect:borrow.html";
		} else {
			model.addAttribute("account", accountService.getCurrent());
			model.addAttribute("userinfo", userinfoService.getCurrent());
			model.addAttribute("creditBorrowScore", BidConst.CREDIT_BORROW_SCORE);
			return "borrow";
		}
	}

	/**
	 * 点击申请贷款
	 */
	@RequestMapping("borrowInfo")
	public String toBorrowApplyPage(Model model) {
		// 判断用户是否能够借款
		Userinfo userinfo = this.userinfoService.getCurrent();
		if (userinfo.getIsBasicInfo() && userinfo.getIsRealAuth() && userinfo.getIsVedioAuth()
				&& userinfo.getScore() >= BidConst.CREDIT_BORROW_SCORE) {
			// 如果能:
			// 如果当前用户没有借款,就往borrow_apply.ftl
			if (!userinfo.getHasBidRequestInProcess()) {
				model.addAttribute("minBidRequestAmount", BidConst.SMALLEST_BIDREQUEST_AMOUNT);
				model.addAttribute("account", this.accountService.getCurrent());
				model.addAttribute("minBidAmount", BidConst.SMALLEST_BID_AMOUNT);
				return "borrow_apply";
			} else {
				// 否则borrow_apply_result.ftl
				return "borrow_apply_result";
			}
		} else {
			// 如果不能回到借款的首页
			return "redirect:/borrow.do";
		}
	}

	/**
	 * 提交借款申请
	 */
	@RequestMapping("borrow_apply")
	public String borrowApply(BidRequest br) {
		this.bidRequestService.apply(br);
		return "redirect:/borrowInfo.do";
	}

	/**
	 * 前台借款详情
	 */
	@RequestMapping("borrow_info")
	public String borrowInfo(Long id, Model model) {
		BidRequest br = this.bidRequestService.get(id);
		Userinfo borrowUser = this.userinfoService.get(br.getCreateUser().getId());
		model.addAttribute("bidRequest", br);
		model.addAttribute("userInfo", borrowUser);
		model.addAttribute("realAuth", this.realAuthService.get(borrowUser.getRealAuthId()));
		model.addAttribute("userFiles", this.userFileService.listFilesByUser(borrowUser.getId()));

		Logininfo current = UserContext.getCurrent();
		if (current != null && current.getId().equals(borrowUser.getId())) {
			// 已经登陆，是借款人自己
			model.addAttribute("self", true);
		} else if (current != null) {
			// 已经登陆，是投资人
			model.addAttribute("account", this.accountService.getCurrent());
		}
		return "borrow_info";
	}
}
