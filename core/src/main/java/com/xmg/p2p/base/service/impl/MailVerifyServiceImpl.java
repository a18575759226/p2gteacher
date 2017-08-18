package com.xmg.p2p.base.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xmg.p2p.base.domain.MailVerify;
import com.xmg.p2p.base.mapper.MailVerifyMapper;
import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.base.service.IMailVerifyService;
@Service
public class MailVerifyServiceImpl implements IMailVerifyService {
	@Autowired
	private MailVerifyMapper mailVerifyMapper;

	public int insert(MailVerify record) {
		return mailVerifyMapper.insert(record);
	}

	public MailVerify selectByUUID(String uuid) {
		return mailVerifyMapper.selectByUUID(uuid);
	}

}
