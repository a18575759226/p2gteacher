package com.xmg.p2p.base.domain;

import com.xmg.p2p.base.util.BitStatesUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by lanxw on 2017/7/23.
 */
@Setter
@Getter
public class Userinfo extends BaseDomain {
	private int version;
	private long bitState;
	private String realName;
	private String idNumber;
	private String phoneNumber;
	private int score;// 风控材料分数
	private Long realAuthId;// 实名认证ID
	private SystemDictionaryItem incomeGrade;
	private SystemDictionaryItem marriage;
	private SystemDictionaryItem kidCount;
	private SystemDictionaryItem educationBackground;
	private SystemDictionaryItem houseCondition;

	/**
	 * 给当前的用户添加一个新的状态码
	 * 
	 * @param bitState
	 */
	public void addState(long bitState) {
		this.bitState = BitStatesUtils.addState(this.bitState, bitState);
	}

	public boolean getIsBindPhone() {
		return BitStatesUtils.hasState(this.bitState, BitStatesUtils.OP_BIND_PHONE);
	}

	public boolean getIsBindEmail() {
		return BitStatesUtils.hasState(this.bitState, BitStatesUtils.OP_BIND_EMAIL);
	}

	public boolean getIsBasicInfo() {
		return BitStatesUtils.hasState(this.bitState, BitStatesUtils.OP_BASIC_INFO);
	}

	public boolean getIsRealAuth() {
		return BitStatesUtils.hasState(this.bitState, BitStatesUtils.OP_REAL_AUTH);
	}

	public boolean getIsVedioAuth() {
		return BitStatesUtils.hasState(this.bitState, BitStatesUtils.OP_VEDIO_AUTH);
	}

	public boolean getHasBidRequestInProcess() {
		return BitStatesUtils.hasState(this.bitState, BitStatesUtils.HAS_BIDREQUEST_IN_PROCESS);
	}
}
