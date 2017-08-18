package com.xmg.p2p.business.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.business.domain.RechargeOffline;
import com.xmg.p2p.business.mapper.RechargeOfflineMapper;
import com.xmg.p2p.business.service.IRechargeOfflineService;

@Service
public class RechargeOfflineServiceImpl implements IRechargeOfflineService {
	@Autowired
	private RechargeOfflineMapper rechargeOfflineMapper;

	@Override
	public void apply(RechargeOffline offline) {
		RechargeOffline r = new RechargeOffline();
		r.setAmount(offline.getAmount());
		r.setApplier(UserContext.getCurrent());
		r.setApplyTime(new Date());
		r.setBankinfo(offline.getBankinfo());
		r.setNote(offline.getNote());
		r.setState(RechargeOffline.STATE_NORMAL);
		r.setTradeCode(offline.getTradeCode());
		r.setTradeTime(offline.getTradeTime());
		this.rechargeOfflineMapper.insert(r);
	}

}
