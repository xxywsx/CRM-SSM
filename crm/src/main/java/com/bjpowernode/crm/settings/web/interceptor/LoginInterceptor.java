package com.bjpowernode.crm.settings.web.interceptor;

import com.bjpowernode.crm.comons.contants.Contants;
import com.bjpowernode.crm.settings.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //如果用户没有登录，则跳转到登录页面,查看session里面是否有user的相关信息
        HttpSession session = httpServletRequest.getSession();
        User user = (User) session.getAttribute(Contants.SESSION_USER);
        if(user==null){
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath());//手动重定向需要加项目的名称
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
