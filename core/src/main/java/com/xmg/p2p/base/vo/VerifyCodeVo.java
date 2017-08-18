package com.xmg.p2p.base.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lanxw on 2017/7/26.
 */
@Setter@Getter
public class VerifyCodeVo implements Serializable{
    private String phoneNumber;//发送短信的手机
    private String verifyCode;//验证码
    private Date sendTime;//发送的时间
}
