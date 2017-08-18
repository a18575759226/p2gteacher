package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.domain.IpLog;
import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.mapper.LogininfoMapper;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.service.IIpLogService;
import com.xmg.p2p.base.service.ILogininfoService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.base.util.MD5;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by lanxw on 2017/7/22.
 */
@Service
public class LogininfoServiceImpl implements ILogininfoService {
    @Autowired
    private LogininfoMapper logininfoMapper;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IUserinfoService userinfoService;
    @Autowired
    private IIpLogService ipLogService;
    @Override
    public Logininfo register(String username, String password) {
        //判断用户名是否已经注册,如果已经注册,抛出异常
        int ret = logininfoMapper.selectCountByUsername(username);
        if(ret>0){
            //已经存在,抛出异常
            throw new RuntimeException("用户名已存在");
        }else{
            //如果没有注册,把当前用户注册的信息封装logininfo对象,保存入库
            Logininfo info = new Logininfo();
            info.setUsername(username);
            info.setPassword(MD5.encode(password));
            info.setState(Logininfo.STATE_NORMAL);
            //设置用户的类型
            info.setUserType(Logininfo.USERTYPE_USER);
            logininfoMapper.insert(info);
            //初始化Account和Userinfo对象
            Account account = new Account();
            account.setId(info.getId());
            accountService.insert(account);
            Userinfo userinfo = new Userinfo();
            userinfo.setId(info.getId());
            userinfoService.insert(userinfo);
            return info;
        }
    }

    @Override
    public boolean checkUsername(String username) {
        //logininfoMapper.selectCountByUsername(username)<=0//-->返回true,没有有用户名
        ////-->返回false,有用户
        //前台true,校验成功 ---->没有该用户名
        //前台false,检验失败---> 数据库中已经有该用户名
        return  logininfoMapper.selectCountByUsername(username)<=0;
    }

    @Override
    public Logininfo login(String username, String password,String ip,int userType) {
        //根据账号和密码去数据库中查询对应的对象(密码加密)
        Logininfo logininfo = logininfoMapper.login(username,MD5.encode(password),userType);
        IpLog ipLog = new IpLog();
        ipLog.setLoginTime(new Date());
        ipLog.setIp(ip);
        ipLog.setUsername(username);
        ipLog.setUserType(userType);
        if(logininfo!=null){
            //如果有这条记录,说明登录成功,需要把登录信息放入到session中
            UserContext.setCurrent(logininfo);
            ipLog.setState(IpLog.LOGIN_SUCCESS);
        }else{
            ipLog.setState(IpLog.LOGIN_FAILED);
            //如果登录失败,抛出异常
            //throw new RuntimeException("账号密码有误.");
        }
        ipLogService.insert(ipLog);
        return logininfo;
    }

    @Override
    public void initAdmin() {
        //1.根据类型判断是否已经有管理员
        int ret = logininfoMapper.selectCountByUserType(Logininfo.USERTYPE_MANAGER);
        //2,如果没有就创建一个新的管理员
        if(ret<=0){
            Logininfo logininfo = new Logininfo();
            logininfo.setUsername(BidConst.DEFAULT_ADMIN_USERNAME);
            logininfo.setPassword(MD5.encode(BidConst.DEFAULT_ADMIN_PASSWORD));
            logininfo.setState(Logininfo.STATE_NORMAL);
            logininfo.setUserType(Logininfo.USERTYPE_MANAGER);
            logininfoMapper.insert(logininfo);
        }
    }
}
