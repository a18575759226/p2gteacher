package com.xmg.p2p.business.mapper;

import java.util.List;

import com.xmg.p2p.business.domain.BidRequestAudit;

public interface BidRequestAuditMapper {

	int insert(BidRequestAudit record);

	BidRequestAudit selectByPrimaryKey(Long id);

	/**
	 * 列出一个借款的所有审核对象列表
	 * 
	 * @param id
	 * @return
	 */
	List<BidRequestAudit> listAuditsByBidRequestId(Long id);

}