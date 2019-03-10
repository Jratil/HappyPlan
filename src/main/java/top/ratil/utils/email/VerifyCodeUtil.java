package top.ratil.utils.email;

import org.springframework.stereotype.Component;

/**
 * @program: HappyThings
 * @description: 生成验证码
 * @author: Ratil
 * @create: 2018-07-25 10:29
 **/
@Component
public class VerifyCodeUtil {

    private static String verifyCode;

    public void setVerifyCode() {
        //返回生成4位数的验证码
        verifyCode = String.valueOf(Math.round(Math.random() * (9000 - 1000) + 1000));
    }

    public String getVerifyCode() {
        return verifyCode;
    }
}
