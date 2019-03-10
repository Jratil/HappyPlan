package top.ratil.controller.front.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.ratil.entity.Account;
import top.ratil.entity.User;
import top.ratil.service.ForgetService;
import top.ratil.utils.FieldErrorUtil;
import top.ratil.utils.PackageJsonUtil;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: HappyPlan
 * @description: 忘记密码Controller
 * @author: Ratil
 * @create: 2018-08-19 13:27
 **/
@Controller
public class ForgetController {
    @Autowired
    ForgetService forgetService;
    @Autowired
    private PackageJsonUtil<Account> packageJson;

    @RequestMapping("forget")
    @ResponseBody
    public Map<String, Object> forget(@Valid Account account,
                                      BindingResult errorResult, String verifyCode) throws Exception {
        Map<String, Object> errorMap = new HashMap<>();

        String VERIFY_CODE = VerifyCodeController.VERIFY_CODE;

        if (verifyCode == null || !verifyCode.equals(VERIFY_CODE)) {
            errorMap.put("verifyCode", "验证码错误");
            return packageJson.failStatus(errorMap);
        }

        //如果验证出错
        if (errorResult.hasErrors()) {
            for (FieldError field: errorResult.getFieldErrors()) {
                System.out.println(field.getDefaultMessage());
                errorMap.put(field.getField(), field.getDefaultMessage());
            }
            return packageJson.failStatus(errorMap);
        }
        if (!account.getUserEmail().equals(VerifyCodeController.USER_EMAIL)) {
            errorMap.put("userEmail", "请输入正确的邮箱");
            return packageJson.failStatus(errorMap);
        }

        //检查邮箱是否存在或者更新出错
        boolean flag = forgetService.forgetPassword(account.getUserEmail(), account.getUserPassword());
        if (!flag) {
            errorMap.put("error", "请检查邮箱是否存在");
            return packageJson.failStatus(errorMap);
        }
        return packageJson.successStatus(null);
    }
}
