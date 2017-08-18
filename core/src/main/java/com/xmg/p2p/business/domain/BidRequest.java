package com.xmg.p2p.business.domain;

import static com.xmg.p2p.base.util.BidConst.BIDREQUEST_STATE_APPROVE_PENDING_1;
import static com.xmg.p2p.base.util.BidConst.BIDREQUEST_STATE_APPROVE_PENDING_2;
import static com.xmg.p2p.base.util.BidConst.BIDREQUEST_STATE_BIDDING;
import static com.xmg.p2p.base.util.BidConst.BIDREQUEST_STATE_BIDDING_OVERDUE;
import static com.xmg.p2p.base.util.BidConst.BIDREQUEST_STATE_COMPLETE_PAY_BACK;
import static com.xmg.p2p.base.util.BidConst.BIDREQUEST_STATE_PAYING_BACK;
import static com.xmg.p2p.base.util.BidConst.BIDREQUEST_STATE_PAY_BACK_OVERDUE;
import static com.xmg.p2p.base.util.BidConst.BIDREQUEST_STATE_PUBLISH_PENDING;
import static com.xmg.p2p.base.util.BidConst.BIDREQUEST_STATE_PUBLISH_REFUSE;
import static com.xmg.p2p.base.util.BidConst.BIDREQUEST_STATE_REJECTED;
import static com.xmg.p2p.base.util.BidConst.BIDREQUEST_STATE_UNDO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.xmg.p2p.base.domain.BaseDomain;
import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.business.util.CalculatetUtil;

import lombok.Getter;
import lombok.Setter;

/**
 * 借款对象
 * 
 * @author xmg
 *
 */
@Setter
@Getter
public class BidRequest extends BaseDomain {
	private int version;
	private int returnType;
	private int bidRequestType;
	private int bidRequestState;
	private BigDecimal bidRequestAmount;
	private BigDecimal currentRate;
	private BigDecimal minBidAmount;
	private int monthes2Return;
	private int bidCount;
	private BigDecimal totalRewardAmount;
	private BigDecimal currentSum = BidConst.ZERO;
	private String title;
	private String description;
	private String note;
	private Date disableDate;
	private int disableDays;
	private Logininfo createUser;
	private List<Bid> bids=new ArrayList<>();
	private Date applyTime;
	private Date publishTime;

	public String getJsonString() {
		Map<String, Object> json = new HashMap<>();
		json.put("id", id);
		json.put("username", this.createUser.getUsername());
		json.put("title", title);
		json.put("bidRequestAmount", bidRequestAmount);
		json.put("currentRate", currentRate);
		json.put("monthes2Return", monthes2Return);
		json.put("returnType", this.getReturnTypeDisplay());
		json.put("totalRewardAmount", totalRewardAmount);
		return JSON.toJSONString(json);
	}

	/**
	 * 投标进度
	 */
	public int getPersent() {
		return this.currentSum.divide(bidRequestAmount, BidConst.DISPLAY_SCALE, RoundingMode.HALF_UP)
				.multiply(CalculatetUtil.ONE_HUNDRED).intValue();
	}

	/**
	 * 得到还差多少钱
	 * 
	 * BigDecimal.add(BigDecimal); BigDecimal.subtract(BigDecimal);
	 * BigDecimal.multiply(BigDecimal).setScale(scale,RoundingModel);
	 * BigDecimal.divide(BigDecimal,scale,RoundingModel);
	 * 
	 * @return
	 */
	public BigDecimal getRemainAmount() {
		return this.bidRequestAmount.subtract(this.currentSum);
	}

	public String getReturnTypeDisplay() {
		return this.returnType == BidConst.RETURN_TYPE_MONTH_INTEREST_PRINCIPAL ? "等额本息" : "先息后本";
	}

	public String getBidRequestStateDisplay() {
		switch (this.bidRequestState) {
		case BIDREQUEST_STATE_PUBLISH_PENDING:
			return "待发布";
		case BIDREQUEST_STATE_BIDDING:
			return "招标中";
		case BIDREQUEST_STATE_UNDO:
			return "已撤销";
		case BIDREQUEST_STATE_BIDDING_OVERDUE:
			return "流标";
		case BIDREQUEST_STATE_APPROVE_PENDING_1:
			return "满标一审";
		case BIDREQUEST_STATE_APPROVE_PENDING_2:
			return "满标二审";
		case BIDREQUEST_STATE_REJECTED:
			return "满标审核被拒";
		case BIDREQUEST_STATE_PAYING_BACK:
			return "还款中";
		case BIDREQUEST_STATE_COMPLETE_PAY_BACK:
			return "完成";
		case BIDREQUEST_STATE_PAY_BACK_OVERDUE:
			return "逾期";
		case BIDREQUEST_STATE_PUBLISH_REFUSE:
			return "发标拒绝";
		default:
			return "";
		}
	}
}
