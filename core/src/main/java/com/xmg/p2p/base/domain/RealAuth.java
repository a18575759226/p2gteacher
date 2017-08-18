package com.xmg.p2p.base.domain;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by lanxw on 2017/7/27.
 */
@Setter
@Getter
public class RealAuth extends BaseAuditDomain {
	public static final int SEX_MALE = 0;// 性别男
	public static final int SEX_FEMALE = 1;// 性别女

	private String realName;// 真实姓名
	private int sex;// 性别
	private String idNumber;// 身份证
	private String bornDate;// 出生日期
	private String address;// 身份证地址
	private String image1;// 身份证正面
	private String image2;// 身份证反面
	
	public String getJsonString() {
		Map<String, Object> json = new HashMap<>();
		json.put("id", id);
		json.put("username", this.applier.getUsername());
		json.put("realname", realName);
		json.put("idNumber", idNumber);
		json.put("sex", this.getSexDisplay());
		json.put("birthDate", bornDate);
		json.put("address", address);
		json.put("image1", image1);
		json.put("image2", image2);
		return JSON.toJSONString(json);
	}

	public String getSexDisplay() {
		return sex == SEX_MALE ? "男" : "女";
	}
}
