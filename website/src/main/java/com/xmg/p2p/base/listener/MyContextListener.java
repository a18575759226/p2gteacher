package com.xmg.p2p.base.listener;

import com.xmg.p2p.base.util.UserContext;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by lanxw on 2017/7/23.
 */
public class MyContextListener implements ServletRequestListener {
    @Override
    public void requestDestroyed(ServletRequestEvent sre) {

    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
      /*  HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        UserContext.local.set(request);

        System.out.println("放入的request对象"+request);*/
    }
}
