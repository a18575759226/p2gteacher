package com.xmg.p2p.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.business.domain.PlatformBankinfo;
import com.xmg.p2p.business.mapper.PlatformBankinfoMapper;
import com.xmg.p2p.business.query.PlatformBankinfoQueryObject;
import com.xmg.p2p.business.service.IPlatformBankinfoService;

@Service
public class PlatformBankinfoServiceImpl implements IPlatformBankinfoService {
	@Autowired
	private PlatformBankinfoMapper platformBankinfoMapper;

	@Override
	public PageResult query(PlatformBankinfoQueryObject qo) {
		int count = this.platformBankinfoMapper.queryForCount(qo);
		if (count > 0) {
			List<PlatformBankinfo> list = this.platformBankinfoMapper.query(qo);
			return new PageResult(list, count, qo.getCurrentPage(), qo.getPageSize());
		}
		return PageResult.empty(qo.getPageSize());
	}

	@Override
	public List<PlatformBankinfo> listAll() {
		return this.platformBankinfoMapper.selectAll();
	}

	@Override
	public void saveOrUpdate(PlatformBankinfo p) {
		if (p.getId() != null) {
			this.platformBankinfoMapper.updateByPrimaryKey(p);
		} else {
			this.platformBankinfoMapper.insert(p);
		}
	}

}
