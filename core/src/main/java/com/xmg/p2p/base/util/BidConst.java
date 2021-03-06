package com.xmg.p2p.base.util;

import java.math.BigDecimal;

/**
 * 系统中的常量
 * 
 * @author Administrator
 * 
 */
public class BidConst {

	/**
	 * 定义存储精度
	 */
	public static final int STORE_SCALE = 4;
	/**
	 * 定义运算精度
	 */
	public static final int CAL_SCALE = 8;
	/**
	 * 定义显示精度
	 */
	public static final int DISPLAY_SCALE = 2;

	/**
	 * 定义系统级别的0
	 */
	public static final BigDecimal ZERO = new BigDecimal("0.0000");

	/**
	 * 定义初始授信额度
	 */
	public static final BigDecimal INIT_BORROW_LIMIT = new BigDecimal("5000.0000");
	/**
	 * 默认管理员的账号
	 */
	public static final String DEFAULT_ADMIN_USERNAME = "admin";
	/**
	 * 默认管理员的密码
	 */
	public static final String DEFAULT_ADMIN_PASSWORD = "1111";
	/**
	 * 短信有效时间,单位:分钟
	 */
	public static final int MESSAGER_VAILD_TIME = 3;
	/**
	 * 邮件的有效时间 单位:天
	 */
	public static final int EMAIL_VAILD_DAY = 5;
	/**
	 * 信用借款最新的风控材料分数
	 */
	public static final int CREDIT_BORROW_SCORE = 30;
	/**
	 * 发送短信的时间间隔 单位:秒
	 */
	public static long MESSAGE_INTERVAL = 90;

	// --------------------还款类型---------------------------
	public final static int RETURN_TYPE_MONTH_INTEREST_PRINCIPAL = 0; // 还款方式
																		// 按月分期还款(等额本息)
	public final static int RETURN_TYPE_MONTH_INTEREST = 1; // 还款方式
															// 按月到期还款(每月还利息,到期还本息)
	// ---------------------标的类型--------------------------
	public final static int BIDREQUEST_TYPE_NORMAL = 0; // 普通信用标

	// ---------------------借款状态---------------------------
	public final static int BIDREQUEST_STATE_PUBLISH_PENDING = 0; // 待发布
	public final static int BIDREQUEST_STATE_BIDDING = 1; // 招标中
	public final static int BIDREQUEST_STATE_UNDO = 2; // 已撤销
	public final static int BIDREQUEST_STATE_BIDDING_OVERDUE = 3; // 流标
	public final static int BIDREQUEST_STATE_APPROVE_PENDING_1 = 4; // 满标1审
	public final static int BIDREQUEST_STATE_APPROVE_PENDING_2 = 5; // 满标2审
	public final static int BIDREQUEST_STATE_REJECTED = 6; // 满标审核被拒绝
	public final static int BIDREQUEST_STATE_PAYING_BACK = 7; // 还款中
	public final static int BIDREQUEST_STATE_COMPLETE_PAY_BACK = 8; // 已还清
	public final static int BIDREQUEST_STATE_PAY_BACK_OVERDUE = 9; // 逾期
	public final static int BIDREQUEST_STATE_PUBLISH_REFUSE = 10; // 发标审核拒绝状态

	public static final BigDecimal SMALLEST_BID_AMOUNT = new BigDecimal("50.0000");// 系统规定的最小投标金额
	public static final BigDecimal SMALLEST_BIDREQUEST_AMOUNT = new BigDecimal("500.0000");// 系统规定的最小借款金额
	public static final BigDecimal SMALLEST_CURRENT_RATE = new BigDecimal("5.0000");// 系统最小借款利息
	public static final BigDecimal MAX_CURRENT_RATE = new BigDecimal("20.0000");// 系统最大借款利息

}
