package com.xmg.p2p.base.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.mapper.AccountMapper;
import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.base.service.IAccountService;
@Service
public class AccountServiceImpl implements IAccountService {
	@Autowired
	private AccountMapper accountMapper;

	public int insert(Account record) {
		return accountMapper.insert(record);
	}

	public Account selectByPrimaryKey(Long id) {
		return accountMapper.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKey(Account record) {
		int ret = accountMapper.updateByPrimaryKey(record);
		if(ret<=0){
			//没有更新到,出现乐观锁的问题,抛出异常,事务回滚
			throw new RuntimeException("乐观锁,accountId:"+record.getId());
		}
		return ret;
	}

	@Override
	public Account getCurrent() {
		return accountMapper.selectByPrimaryKey(UserContext.getCurrent().getId());
	}
}
