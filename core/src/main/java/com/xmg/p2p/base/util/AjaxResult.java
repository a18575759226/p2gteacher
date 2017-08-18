package com.xmg.p2p.base.util;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AjaxResult {
	private boolean success = true;
	private String msg;

	public AjaxResult() {

	}

	public AjaxResult(boolean success, String msg) {
		super();
		this.success = success;
		this.msg = msg;
	}

	public AjaxResult(String msg) {
		super();
		this.msg = msg;
		this.success = false;
	}

	public void setMessage(String msg) {
		this.msg = msg;
		this.success = false;
	}
}
