package com.xmg.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by lanxw on 2017/7/25.
 */
@Setter@Getter
public class IpLog  extends BaseDomain  {
    public static final int LOGIN_SUCCESS = 0;//登陆成功
    public static final int LOGIN_FAILED = 1;//登陆失败
    private String ip;
    private String username;
    private Date loginTime;
    private int state;
    private int userType;
    public String getDisplayState(){
        return this.state==LOGIN_SUCCESS?"登陆成功":"登陆失败";
    }
    public String getDisplayUserType(){
        return this.userType==Logininfo.USERTYPE_USER?"普通用户":"管理员";
    }
}
