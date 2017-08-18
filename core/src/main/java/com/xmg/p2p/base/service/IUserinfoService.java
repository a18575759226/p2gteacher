package com.xmg.p2p.base.service;

import com.xmg.p2p.base.domain.Userinfo;

public interface IUserinfoService {
	int insert(Userinfo record);

	void update(Userinfo record);

	public Userinfo getCurrent();

	/**
	 * 手机绑定的方法
	 * 
	 * @param phoneNumber
	 * @param verifyCode
	 */
	void bindPhone(String phoneNumber, String verifyCode);

	/**
	 * 邮箱绑定的方法
	 * 
	 * @param key
	 */
	void bindEmail(String key);

	/**
	 * 填写基本资料
	 * 
	 * @param userinfo
	 */
	void updateBasicInfo(Userinfo userinfo);

	Userinfo get(Long id);
}
