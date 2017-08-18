package com.xmg.p2p.base.service;

import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.VedioAuthQueryObject;

public interface IVedioAuthService {

	PageResult query(VedioAuthQueryObject qo);

	/**
	 * 视频审核
	 * 
	 * @param loginInfoValue
	 * @param state
	 * @param remark
	 */
	void audit(long loginInfoValue, int state, String remark);
}
