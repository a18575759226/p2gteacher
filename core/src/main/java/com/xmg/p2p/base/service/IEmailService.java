package com.xmg.p2p.base.service;

import com.xmg.p2p.base.domain.MailVerify;

/**
 * Created by lanxw on 2017/7/26.
 */
public interface IEmailService {
    /**
     * 给指定的邮箱发送验证码
     * @param email
     */
    public void sendEmail(String email);

    /**
     * 验证邮箱是否已经过了有效期,如果过了,返回null
     * @param key
     * @return
     */
    MailVerify validate(String key);
}
