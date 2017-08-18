package com.xmg.p2p.base.domain;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;

/**
 * 风控材料
 * 
 * @author xmg
 *
 */
@Setter
@Getter
public class UserFile extends BaseAuditDomain {

	private String img;
	private SystemDictionaryItem fileType;
	private int score;

	public String getJsonString() {
		Map<String, Object> json = new HashMap<>();
		json.put("id", id);
		json.put("applier", this.applier.getUsername());
		json.put("fileType", this.fileType.getTitle());
		json.put("img", img);
		return JSON.toJSONString(json);
	}

}
