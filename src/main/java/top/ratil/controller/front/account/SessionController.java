package top.ratil.controller.front.account;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.ratil.entity.User;
import top.ratil.utils.UserSessionUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: HappyPlan
 * @description: 所有有关session的操作
 * @author: Ratil
 * @create: 2018-08-25 22:28
 **/
@Controller
public class SessionController {

    @RequestMapping("getUserId")
    @ResponseBody
    public Integer getUserSession() {
        return UserSessionUtil.getUserSession().getUserId();
    }
}
