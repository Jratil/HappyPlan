package top.ratil.controller.front.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.ratil.entity.User;
import top.ratil.service.RegisterService;
import top.ratil.utils.FieldErrorUtil;
import top.ratil.utils.PackageJsonUtil;

import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: HappyThings
 * @description: 注册Controller
 * @author: Ratil
 * @create: 2018-07-24 18:21
 **/
@Controller
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    @Autowired
    private PackageJsonUtil<User> packageJson;

    /**
     * 注册
     *
     * @param user        注册信息
     * @param errorResult 获取注册时出现的错误
     * @return 返回注册结果
     */
    @RequestMapping("register")
    @ResponseBody
    public Map<String, Object> register(@Valid User user,
                                        BindingResult errorResult,String verifyCode) throws Exception {
        Map<String, Object> errorMap = new HashMap<>();

        //获取生成的验证码
        String VERIFY_CODE = VerifyCodeController.VERIFY_CODE;
        System.out.println("验证码是" + verifyCode + "--" + VERIFY_CODE);

        //输入的验证码出错
        if (verifyCode == null || !verifyCode.equals(VERIFY_CODE)) {
            errorMap.put("verifyCode", "你输入的验证码有误!");
            return packageJson.failStatus(errorMap);
        }

        //获取账号注册时间
        Date time = new Date();
        user.setUserCreateTime(time);

        //如果注册信息不符合要求
        if (errorResult.hasErrors()) {
            for (FieldError field: errorResult.getFieldErrors()) {
                System.out.println(field.getDefaultMessage());
                errorMap.put(field.getField(), field.getDefaultMessage());
            }
//            System.out.println("注册信息有误");
            return packageJson.failStatus(errorMap);
        }

        if (!user.getUserEmail().equals(VerifyCodeController.USER_EMAIL)) {
            errorMap.put("userEmail", "请输入正确的邮箱");
            return packageJson.failStatus(errorMap);
        }

        if(!registerService.register(user)) {
            errorMap.put("error", "邮箱已存在");
            return packageJson.failStatus(errorMap);
        }
        return packageJson.successStatus(null);
    }
}
