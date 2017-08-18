package com.xmg.p2p.business.mapper;

import com.xmg.p2p.business.domain.RechargeOffline;

public interface RechargeOfflineMapper {
	int insert(RechargeOffline record);

	RechargeOffline selectByPrimaryKey(Long id);

	int updateByPrimaryKey(RechargeOffline record);
}