package com.xmg.p2p.business.service;

import java.util.List;

import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.business.domain.PlatformBankinfo;
import com.xmg.p2p.business.query.PlatformBankinfoQueryObject;

/**
 * 平台银行账户信息管理
 * 
 * @author xmg
 *
 */
public interface IPlatformBankinfoService {
	PageResult query(PlatformBankinfoQueryObject qo);

	void saveOrUpdate(PlatformBankinfo p);

	List<PlatformBankinfo> listAll();
}
