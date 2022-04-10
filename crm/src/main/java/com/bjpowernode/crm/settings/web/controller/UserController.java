package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.comons.contants.Contants;
import com.bjpowernode.crm.comons.domain.ReturnObject;
import com.bjpowernode.crm.comons.utils.DateUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/settings/qx/user/toLogin.do")
    public String toLogin() {
        return "settings/qx/user/login";

    }

    @RequestMapping("/settings/qx/usr/login.do")
    @ResponseBody
    public Object login(String loginAct, String loginPwd, String isRemPwd, HttpServletResponse response, HttpServletRequest request, HttpSession session) {
        //封装参数
        Map<String, Object> map = new HashMap<>();
        map.put("loginAct", loginAct);
        map.put("loginPwd", loginPwd);
        //调用service方法,查询用户
        User user = userService.queryUserByLoginActAndPwd(map);
        //调用返回的实体类对象
        ReturnObject returnObject = new ReturnObject();
        //根据查询结果，生成响应信息
        if (user == null) {
            //登录失败，
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("用户名或密码错误");
        } else {
            //进一步判断
            //user.getExpireTime();
            //new Date();
            //把时间转化为2020-20-20的形式进行比较
            String nowStr = DateUtils.formateDateTime(new Date());
            if (nowStr.compareTo(user.getExpireTime()) > 0) {
                //比较当前登录时间和账号有效时间
                //登录失败，账号过期
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("该账号已过期");

            } else if ("0".equals(user.getLockState())) {
                //比较登录状态
                //登录失败，状态被锁定
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("该账号已被锁定");

            } else if (!user.getAllowIps().contains(request.getRemoteAddr())) {
                //调用当前用户的ip地址,request.getRemoteAddr()
                //比较当前用户的IP地址和系统规定的ip地址登录范围
                //登录失败，ip受限
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("当前ip地址不被允许");

            } else {
                //登录成功
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);

                //把user保存在session中
                session.setAttribute(Contants.SESSION_USER,user);

                //如果需要记住密码，则往外写cookie
                if("true".equals(isRemPwd)){
                    Cookie c1 = new Cookie("loginAct",user.getLoginAct());
                    c1.setMaxAge(10*24*60*60);
                    response.addCookie(c1);
                    Cookie c2 = new Cookie("loginPwd", user.getLoginPwd());
                    c2.setMaxAge(10*24*60*60);
                    response.addCookie(c2);
                }else {
                    //把原先的cookie删除
                    Cookie c1 = new Cookie("loginAct", "1");
                    c1.setMaxAge(0);
                    response.addCookie(c1);
                    Cookie c2 = new Cookie("loginPwd", "1");
                    c2.setMaxAge(0);
                    response.addCookie(c2);
                }
            }
        }
        return returnObject;
    }

    @RequestMapping("/settings/qx/user/logout.do")
    public String logout(HttpServletResponse response,HttpSession session){
        //清空cookie
        Cookie c1 = new Cookie("loginAct", "1");
        c1.setMaxAge(0);
        response.addCookie(c1);
        Cookie c2 = new Cookie("loginPwd", "1");
        c2.setMaxAge(0);
        response.addCookie(c2);
        //销毁session
        session.invalidate();
        //跳转到首页,重定向
        return "redirect:/";//借助springmvc重定向
    }
}
