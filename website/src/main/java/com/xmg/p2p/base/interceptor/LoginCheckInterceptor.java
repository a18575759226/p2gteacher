package com.xmg.p2p.base.interceptor;

import com.xmg.p2p.base.util.RequireLogin;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lanxw on 2017/7/26.
 */
public class LoginCheckInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(handler.getClass());
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //获取到当前请求的方法对象.
            //判断该方法上是否有ReuqireLogin注解
            //如果有,需要登录控制,判断session是否有登陆用户,如果有放行,如果没有,跳转login.html
            if(handlerMethod.getMethodAnnotation(RequireLogin.class)!=null){
                if(UserContext.getCurrent()==null){
                    response.sendRedirect("/login.html");
                    return false;
                }
            }
        }
        return true;
    }
}
