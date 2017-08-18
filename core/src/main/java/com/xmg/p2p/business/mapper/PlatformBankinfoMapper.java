package com.xmg.p2p.business.mapper;

import java.util.List;

import com.xmg.p2p.business.domain.PlatformBankinfo;
import com.xmg.p2p.business.query.PlatformBankinfoQueryObject;

public interface PlatformBankinfoMapper {
	int insert(PlatformBankinfo record);

	PlatformBankinfo selectByPrimaryKey(Long id);

	List<PlatformBankinfo> selectAll();

	int updateByPrimaryKey(PlatformBankinfo record);

	int queryForCount(PlatformBankinfoQueryObject qo);

	List<PlatformBankinfo> query(PlatformBankinfoQueryObject qo);
}