package top.ratil.controller.front.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.ratil.entity.User;
import top.ratil.entity.Account;
import top.ratil.service.LoginService;
import top.ratil.utils.PackageJsonUtil;
import top.ratil.utils.UserSessionUtil;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: HappyThings
 * @description: 登录Controller
 * @author: Ratil
 * @create: 2018-07-23 18:42
 **/
@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private PackageJsonUtil<Account> packageJson;

    /**
     * 登录
     *
     * @param account   登录的邮箱
     * @return 返回登录状态
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(@Valid Account account,
                                     BindingResult errorResult) throws Exception {
        Map<String, Object> errorMap = new HashMap<>();

        //没填写邮箱或密码
        if(errorResult.hasErrors()) {
            for (FieldError field: errorResult.getFieldErrors()) {
                System.out.println(field.getDefaultMessage());
                errorMap.put(field.getField(), field.getDefaultMessage());
            }
            System.out.println("没输入");
            return packageJson.failStatus(errorMap);
        }
        //判断账号密码是否正确
        boolean flag = loginService.login(account.getUserEmail(), account.getUserPassword());
        if (!flag) {
            errorMap.put("userPassword", "邮箱或密码错误!");
            return packageJson.failStatus(errorMap);
        }
        return packageJson.successStatus(null);
    }

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> logout() {
        UserSessionUtil.destorySession();
        return packageJson.successStatus(null);
    }
}
