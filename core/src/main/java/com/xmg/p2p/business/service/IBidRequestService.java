package com.xmg.p2p.business.service;

import java.util.List;

import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.business.domain.BidRequest;
import com.xmg.p2p.business.domain.BidRequestAudit;
import com.xmg.p2p.business.query.BidRequestQueryObject;

/**
 * 借款相关服务
 * 
 * @author xmg
 *
 */
public interface IBidRequestService {
	PageResult query(BidRequestQueryObject qo);

	void update(BidRequest br);

	/**
	 * 提交借款申请
	 * 
	 * @param br
	 */
	void apply(BidRequest br);

	/**
	 * 发标前审核
	 * 
	 * @param id
	 * @param state
	 * @param remark
	 */
	void publishAudit(Long id, int state, String remark);

	BidRequest get(Long id);

	/**
	 * 列出一个借款的所有审核对象列表
	 * 
	 * @param id
	 * @return
	 */
	List<BidRequestAudit> listAuditsByBidRequestId(Long id);

	/**
	 * 列出首页数据
	 * 
	 * @param i
	 * @return
	 */
	List<BidRequest> listIndex(int i);
}
