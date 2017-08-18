package com.xmg.p2p.business.query;

import com.xmg.p2p.base.query.QueryObject;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BidRequestQueryObject extends QueryObject {

	private int bidRequestState = -1;
	private int[] bidRequestStates;

	private String orderBy;// 排序的列
	private String orderType;// 排序的规则
}
