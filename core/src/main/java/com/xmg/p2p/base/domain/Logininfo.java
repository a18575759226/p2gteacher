package com.xmg.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by lanxw on 2017/7/22.
 * 登陆实体
 */
@Setter@Getter
public class Logininfo  extends BaseDomain {
    public static final int STATE_NORMAL = 0;
    public static final int STATE_LOCK = 1;
    public static final int USERTYPE_USER = 0;//普通用户
    public static final int USERTYPE_MANAGER = 1;//管理员
    private String username;
    private String password;
    private int state;
    private int userType;
}
