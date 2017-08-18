package com.xmg.p2p.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xmg.p2p.base.domain.MailVerify;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.mapper.UserinfoMapper;
import com.xmg.p2p.base.service.IEmailService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.service.IVerifyCodeService;
import com.xmg.p2p.base.util.BitStatesUtils;
import com.xmg.p2p.base.util.UserContext;

@Service
public class UserinfoServiceImpl implements IUserinfoService {
	@Autowired
	private UserinfoMapper userinfoMapper;
	@Autowired
	private IVerifyCodeService verifyCodeService;
	@Autowired
	private IEmailService emailService;

	public int insert(Userinfo record) {
		return userinfoMapper.insert(record);
	}
	
	public void update(Userinfo record) {
		int ret = userinfoMapper.updateByPrimaryKey(record);
		if (ret <= 0) {
			// 没有更新到,出现乐观锁的问题,抛出异常,事务回滚
			throw new RuntimeException("乐观锁,userinfoId:" + record.getId());
		}
	}

	@Override
	public Userinfo getCurrent() {
		return userinfoMapper.selectByPrimaryKey(UserContext.getCurrent().getId());
	}

	@Override
	public Userinfo get(Long id) {
		return this.userinfoMapper.selectByPrimaryKey(id);
	}

	@Override
	public void bindPhone(String phoneNumber, String verifyCode) {
		// 1.传入的手机和session中记录的手机是否一致,验证码是否一致,验证码是否在有效期 之内.
		boolean valid = verifyCodeService.validate(phoneNumber, verifyCode);
		if (valid) {
			Userinfo userinfo = this.getCurrent();
			// 2.获取出当前登陆用户的基本信息对象userinfo对象,判断这个对象是否已经绑定了手机,抛出异常
			if (!userinfo.getIsBindPhone()) {
				// 3.如果没有绑定,给userinfo添加手机号码,状态码中添加手机认证的状态吗.
				userinfo.setPhoneNumber(phoneNumber);
				userinfo.addState(BitStatesUtils.OP_BIND_PHONE);
				// 把userinfo的信息更新到数据库中
				this.update(userinfo);
			} else {
				throw new RuntimeException("已经绑定过手机,不需要重复绑定");
			}
		} else {
			throw new RuntimeException("验证码验证失败!");
		}

	}

	@Override
	public void bindEmail(String key) {
		// 1.调用emailService的方法验证邮箱是否在有效期之内(返回mailVerify),如果返回的返回mailVerify==null,过了有效期,如果没有过有效期,mailVerify
		MailVerify mailVerify = emailService.validate(key);
		if (mailVerify == null) {
			throw new RuntimeException("邮箱认证失败");
		}
		// 2.根据返回的mailVerify获取到userId--->userinfo对象,给userinfo对象添加邮箱,添加邮箱认证的状态
		Userinfo userinfo = userinfoMapper.selectByPrimaryKey(mailVerify.getUserId());
		// 判断是否已经邮箱认证了,如果已经认证,抛出异常
		if (!userinfo.getIsBindEmail()) {
			userinfo.addState(BitStatesUtils.OP_BIND_EMAIL);
			this.update(userinfo);
		} else {
			throw new RuntimeException("邮箱已经认证,不要重复认证");
		}

	}

	@Override
	public void updateBasicInfo(Userinfo userinfo) {
		Userinfo current = this.getCurrent();
		current.setEducationBackground(userinfo.getEducationBackground());
		current.setHouseCondition(userinfo.getHouseCondition());
		current.setIncomeGrade(userinfo.getIncomeGrade());
		current.setKidCount(userinfo.getKidCount());
		current.setMarriage(userinfo.getMarriage());
		if (!current.getIsBasicInfo()) {
			// 第一次的时候给用户添加上的基本信息认证
			current.addState(BitStatesUtils.OP_BASIC_INFO);
		}
		this.update(current);
	}

}
