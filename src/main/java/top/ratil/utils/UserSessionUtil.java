package top.ratil.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.ratil.entity.User;

import javax.servlet.http.HttpSession;

/**
 * @program: HappyPlan
 * @description: session操作工具类
 * @author: Ratil
 * @create: 2018-08-19 16:50
 **/
public class UserSessionUtil {
    public static HttpSession getSession() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        return requestAttributes.getRequest().getSession();
    }

    public static void setUserSession(User user) {
        getSession().setAttribute("user", user);
    }

    public static User getUserSession() {
        return (User) getSession().getAttribute("user");
    }

    public static void destorySession() {
        getSession().removeAttribute("user");
    }
}
