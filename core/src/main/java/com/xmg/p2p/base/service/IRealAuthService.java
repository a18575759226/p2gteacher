package com.xmg.p2p.base.service;

import com.xmg.p2p.base.domain.RealAuth;
import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.RealAuthQueryObject;

public interface IRealAuthService {

	RealAuth get(Long realAuthId);

	/**
	 * 保存实名认证流程对象
	 * 
	 * @param realAuth
	 */
	void save(RealAuth realAuth);

	PageResult query(RealAuthQueryObject qo);

	/**
	 * 实名认证审核
	 * 
	 * @param id
	 * @param remark
	 * @param state
	 */
	void audit(Long id, String remark, int state);
}
