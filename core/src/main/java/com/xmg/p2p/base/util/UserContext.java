package com.xmg.p2p.base.util;

import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.vo.VerifyCodeVo;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by lanxw on 2017/7/23.
 */
public class UserContext {
    public static final String USERINSESSION = "logininfo";
    public static final String VERIFYCODEVOINSESSION = "verifyCode_in_session";
    public static HttpSession getSession(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
    }
    /**
     * 设置当前的登陆用户到session中
     * @param current
     */
    public static void setCurrent(Logininfo current) {
       getSession().setAttribute(USERINSESSION,current);
    }

    /**
     * 从session中获取当前登陆的用户
     * @return
     */
    public static Logininfo getCurrent(){
        return (Logininfo) getSession().getAttribute(USERINSESSION);
    }

    /**
     * 把验证码VO对象放入到session中
     * @param verifyCodeVo
     */
    public static void setVerifyCodeVo(VerifyCodeVo verifyCodeVo) {
        getSession().setAttribute(VERIFYCODEVOINSESSION,verifyCodeVo);
    }

    /**
     * 从session中获取验证对象
     * @return
     */
    public static VerifyCodeVo getVerifyCodeVo(){
        return (VerifyCodeVo) getSession().getAttribute(VERIFYCODEVOINSESSION);
    }
}
