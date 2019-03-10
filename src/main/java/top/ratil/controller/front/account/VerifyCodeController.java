package top.ratil.controller.front.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.ratil.entity.Account;
import top.ratil.service.RegisterService;
import top.ratil.utils.PackageJsonUtil;

import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: HappyPlan
 * @description: 验证码
 * @author: Ratil
 * @create: 2018-08-19 13:46
 **/
@Controller
public class VerifyCodeController {

    public static String VERIFY_CODE;

    public static String USER_EMAIL;
    @Autowired
    private RegisterService registerService;
    @Autowired
    private PackageJsonUtil<Account> packageJson;

    /**
     * 发送验证码Controller
     *
     * @param email 接收者的邮箱
     * @return 返回的验证码是否为空
     * 不为空则发送成功
     * 否则发送验证码失败
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     */
    @RequestMapping("verifyCode")
    @ResponseBody
    public Map<String, Object> sendVerifyCode(String email) throws UnsupportedEncodingException, MessagingException {
        Map<String, Object> map = new HashMap<>();

        //验证是否是正确的邮箱
        String regexEmail = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        if (!Pattern.matches(regexEmail, email)) {
            map.put("error", "邮箱有误");
            return packageJson.failStatus(map);
        }
        USER_EMAIL = email;
        VERIFY_CODE = registerService.sendVerifyCode(email);
        if (VERIFY_CODE == null) {
            return packageJson.failStatus(null);
        }
        deleteVerifyCode();
        return packageJson.successStatus(null);
    }

    public void deleteVerifyCode() {
        System.out.println("1分钟后重置验证码");
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                VERIFY_CODE = null;
                System.out.println("重置验证码完成");
            }
        }, 60 * 1000 * 2);
    }
}
