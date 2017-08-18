package com.xmg.p2p.business.domain;

import com.xmg.p2p.base.domain.BaseAuditDomain;

import lombok.Getter;
import lombok.Setter;

/**
 * 借款审核对象
 * 
 * @author xmg
 *
 */
@Setter
@Getter
public class BidRequestAudit extends BaseAuditDomain {

	public static final int TYPE_PUBLISH_AUDIT = 0;// 发标前审核
	public static final int TYPE_FULL1_AUDIT = 1;// 满标一审
	public static final int TYPE_FULL2_AUDIT = 2;// 满标二审

	private Long bidRequestId;
	private int auditType;

	public String getAuditTypeDisplay() {
		switch (this.auditType) {
		case TYPE_PUBLISH_AUDIT:
			return "发标前审核";
		case TYPE_FULL1_AUDIT:
			return "满标一审";
		case TYPE_FULL2_AUDIT:
			return "满标二审";
		default:
			return "";
		}
	}
}
