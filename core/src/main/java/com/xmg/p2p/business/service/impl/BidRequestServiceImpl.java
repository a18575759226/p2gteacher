package com.xmg.p2p.business.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.base.util.BitStatesUtils;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.business.domain.BidRequest;
import com.xmg.p2p.business.domain.BidRequestAudit;
import com.xmg.p2p.business.mapper.BidRequestAuditMapper;
import com.xmg.p2p.business.mapper.BidRequestMapper;
import com.xmg.p2p.business.query.BidRequestQueryObject;
import com.xmg.p2p.business.service.IBidRequestService;
import com.xmg.p2p.business.util.CalculatetUtil;

@Service
public class BidRequestServiceImpl implements IBidRequestService {
	@Autowired
	private BidRequestMapper bidRequestMapper;

	@Autowired
	private IUserinfoService userinfoService;

	@Autowired
	private IAccountService accountService;

	@Autowired
	private BidRequestAuditMapper auditMapper;

	@Override
	public PageResult query(BidRequestQueryObject qo) {
		int count = this.bidRequestMapper.queryForCount(qo);
		if (count > 0) {
			List<BidRequest> list = this.bidRequestMapper.query(qo);
			return new PageResult(list, count, qo.getCurrentPage(), qo.getPageSize());
		}
		return PageResult.empty(qo.getPageSize());
	}

	@Override
	public BidRequest get(Long id) {
		return this.bidRequestMapper.selectByPrimaryKey(id);
	}

	@Override
	public void update(BidRequest br) {
		int ret = this.bidRequestMapper.updateByPrimaryKey(br);
		if (ret <= 0) {
			throw new RuntimeException("乐观锁失败,BidRequest:" + br.getId());
		}
	}

	@Override
	public List<BidRequestAudit> listAuditsByBidRequestId(Long id) {
		return this.auditMapper.listAuditsByBidRequestId(id);
	}

	@Override
	public List<BidRequest> listIndex(int pageSize) {
		BidRequestQueryObject qo = new BidRequestQueryObject();
		qo.setBidRequestStates(new int[] { BidConst.BIDREQUEST_STATE_BIDDING, BidConst.BIDREQUEST_STATE_PAYING_BACK,
				BidConst.BIDREQUEST_STATE_COMPLETE_PAY_BACK });
		qo.setPageSize(pageSize);
		qo.setOrderBy("br.bidrequeststate");
		qo.setOrderType("ASC");

		return this.bidRequestMapper.query(qo);
	}

	@Override
	public void apply(BidRequest br) {
		// 判断
		Userinfo current = this.userinfoService.getCurrent();
		Account account = this.accountService.getCurrent();
		// 申请借款的4个条件判断;
		if (current.getIsBasicInfo() && current.getIsRealAuth() && current.getIsVedioAuth()
				&& current.getScore() >= BidConst.CREDIT_BORROW_SCORE && !current.getHasBidRequestInProcess()// 没有借款在审核流程当中;
				&& br.getBidRequestAmount().compareTo(BidConst.SMALLEST_BIDREQUEST_AMOUNT) >= 0 // 系统最小借款金额<=借款金额
				&& br.getBidRequestAmount().compareTo(account.getRemainBorrowLimit()) <= 0// 借款金额<=剩余信用额度
				&& br.getCurrentRate().compareTo(BidConst.SMALLEST_CURRENT_RATE) >= 0// 系统最小借款利率<=借款利率
				&& br.getCurrentRate().compareTo(BidConst.MAX_CURRENT_RATE) <= 0// 借款利率<=系统最大借款利率
				&& br.getMinBidAmount().compareTo(BidConst.SMALLEST_BID_AMOUNT) >= 0// 最小投标>=系统最小投标
		) {
			// 创建一个新的借款对象,并设置相关属性;
			BidRequest b = new BidRequest();
			b.setApplyTime(new Date());
			b.setBidRequestAmount(br.getBidRequestAmount());
			b.setBidRequestState(BidConst.BIDREQUEST_STATE_PUBLISH_PENDING);
			b.setBidRequestType(BidConst.BIDREQUEST_TYPE_NORMAL);
			b.setCreateUser(UserContext.getCurrent());
			b.setCurrentRate(br.getCurrentRate());
			b.setDescription(br.getDescription());
			b.setDisableDays(br.getDisableDays());
			b.setMinBidAmount(br.getMinBidAmount());
			b.setMonthes2Return(br.getMonthes2Return());
			b.setReturnType(br.getReturnType());
			b.setTitle(br.getTitle());
			b.setTotalRewardAmount(CalculatetUtil.calTotalInterest(b.getReturnType(), b.getBidRequestAmount(),
					b.getCurrentRate(), b.getMonthes2Return()));
			// 保存
			this.bidRequestMapper.insert(b);
			// 借款人添加状态吗;
			current.setBitState(
					BitStatesUtils.addState(current.getBitState(), BitStatesUtils.HAS_BIDREQUEST_IN_PROCESS));
			this.userinfoService.update(current);
		}
	}

	@Override
	public void publishAudit(Long id, int state, String remark) {
		BidRequest br = this.bidRequestMapper.selectByPrimaryKey(id);
		// 判断，借款是否处于发表前审核
		if (br != null && br.getBidRequestState() == BidConst.BIDREQUEST_STATE_PUBLISH_PENDING) {
			// 创建一个审核对象，并设置相关属性；
			BidRequestAudit audit = new BidRequestAudit();
			audit.setApplier(br.getCreateUser());
			audit.setApplyTime(br.getApplyTime());
			audit.setAuditor(UserContext.getCurrent());
			audit.setAuditTime(new Date());
			audit.setAuditType(BidRequestAudit.TYPE_PUBLISH_AUDIT);
			audit.setBidRequestId(id);
			audit.setRemark(remark);
			audit.setState(state);
			this.auditMapper.insert(audit);
			// 审核通过
			if (state == BidRequestAudit.STATE_PASS) {
				// 修改借款状态
				br.setBidRequestState(BidConst.BIDREQUEST_STATE_BIDDING);
				// 设置借款属性；
				br.setPublishTime(new Date());
				br.setDisableDate(DateUtils.addDays(new Date(), br.getDisableDays()));
				br.setNote(remark);
			} else {
				// 审核拒绝
				// 设置状态；
				br.setBidRequestState(BidConst.BIDREQUEST_STATE_PUBLISH_REFUSE);
				// 去掉状态吗
				Userinfo borrowUser = this.userinfoService.get(br.getCreateUser().getId());
				borrowUser.setBitState(
						BitStatesUtils.removeState(borrowUser.getBitState(), BitStatesUtils.HAS_BIDREQUEST_IN_PROCESS));
				this.userinfoService.update(borrowUser);
			}
			this.update(br);
		}
	}
}
