package com.xmg.p2p.business.domain;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.xmg.p2p.base.domain.BaseAuditDomain;

import lombok.Getter;
import lombok.Setter;

/**
 * 线下充值单对象
 * 
 * @author xmg
 *
 */
@Setter
@Getter
public class RechargeOffline extends BaseAuditDomain {

	private PlatformBankinfo bankinfo;
	private String tradeCode;// 交易号
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date tradeTime;// 充值时间
	private BigDecimal amount;
	private String note;// 充值说明

}
