package com.xmg.p2p.business.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.xmg.p2p.base.domain.BaseDomain;
import com.xmg.p2p.base.domain.Logininfo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Bid extends BaseDomain {
	private BigDecimal actualRate;
	private BigDecimal availableAmount;
	private Long bidRequestId;
	private String bidRequestTitle;
	private Logininfo bidUser;
	private Date bidTime;
	private int bidRequestState;
}
