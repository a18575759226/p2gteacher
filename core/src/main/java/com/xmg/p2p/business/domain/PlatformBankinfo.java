package com.xmg.p2p.business.domain;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.xmg.p2p.base.domain.BaseDomain;

import lombok.Getter;
import lombok.Setter;

/**
 * 平台账户信息
 * 
 * @author xmg
 *
 */
@Setter
@Getter
public class PlatformBankinfo extends BaseDomain {

	private String bankName;
	private String accountName;
	private String accountNumber;
	private String forkName;

	public String getJsonString() {
		Map<String, Object> json = new HashMap<>();
		json.put("id", id);
		json.put("bankName", bankName);
		json.put("accountName", accountName);
		json.put("accountNumber", accountNumber);
		json.put("forkName", forkName);
		return JSON.toJSONString(json);
	}

}
