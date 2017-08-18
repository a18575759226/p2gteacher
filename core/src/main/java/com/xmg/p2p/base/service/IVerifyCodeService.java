package com.xmg.p2p.base.service;

/**
 * Created by lanxw on 2017/7/26.
 */
public interface IVerifyCodeService {
    /**
     * 给指定的手机发送验证码
     * @param phoneNumber
     */
    public void sendVerifyCode(String phoneNumber);

    /**
     * 验证前台传入的验证是否有效
     * @param phoneNumber
     * @param verifyCode
     * @return
     */
    boolean validate(String phoneNumber, String verifyCode);
}
