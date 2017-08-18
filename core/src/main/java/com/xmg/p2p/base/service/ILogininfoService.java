package com.xmg.p2p.base.service;

import com.xmg.p2p.base.domain.Logininfo;

/**
 * Created by lanxw on 2017/7/22.
 */
public interface ILogininfoService {
    /**
     * 注册的业务方法
     * @param username
     * @param password
     * @return
     */
    public Logininfo register(String username,String password);

    /**
     * 判断数据库中是否已经有该用户名
     * @param username
     * @return
     */
    boolean checkUsername(String username);

    /**
     * 登录方法
     * @param username
     * @param password
     * @return
     */
    Logininfo login(String username, String password,String ip,int userType);

    /**
     * 初始化管理员
     */
    void initAdmin();
}
