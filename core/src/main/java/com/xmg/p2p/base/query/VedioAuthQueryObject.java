package com.xmg.p2p.base.query;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.xmg.p2p.base.util.DateUtil;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VedioAuthQueryObject extends QueryObject {

	private int state = -1;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date beginDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endDate;

	public Date getEndDate() {
		return DateUtil.getEndDate(endDate);
	}
	
}
