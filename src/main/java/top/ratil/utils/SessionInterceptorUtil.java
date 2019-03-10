package top.ratil.utils;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: HappyPlan
 * @description: 拦截器, 拦截所有请求, 判断session是否存在(判断是否登录)
 * @author: Ratil
 * @create: 2018-08-27 21:29
 **/
public class SessionInterceptorUtil implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (UserSessionUtil.getUserSession() == null) {
            response.setHeader("sessionStatus", "timeout");
            System.out.println("没有登录, 回到登录界面");
            response.sendRedirect("/plan");
            return false;
        }
        System.out.println("登录了, 不拦截");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("拦截成功");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
