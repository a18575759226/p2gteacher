package com.xmg.p2p.business.service;

import com.xmg.p2p.business.domain.RechargeOffline;

public interface IRechargeOfflineService {
	/**
	 * 提交线下充值单
	 * 
	 * @param offline
	 */
	void apply(RechargeOffline offline);

}
