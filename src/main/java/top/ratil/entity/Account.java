package top.ratil.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @program: HappyPlan
 * @description: 邮箱密码
 * @author: Ratil
 * @create: 2018-09-04 22:01
 **/
public class Account {

    @Email(message = "请输入正确的邮箱")
    @NotBlank(message = "请输入的正确邮箱")
    private String userEmail;

    @Size(max = 20, min = 5, message = "请输入5-20个长度的密码")
    private String userPassword;

    public Account(@Email(message = "请输入正确的邮箱") @NotBlank(message = "请输入的正确邮箱") String userEmail, @NotBlank(message = "请输入密码") String userPassword) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
