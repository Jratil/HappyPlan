package top.ratil.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

public class User {
    private Integer userId;

    @Pattern(regexp = "[\\u4e00-\\u9fa5a-zA-Z0-9_]{4,10}", message = "必须为4-10位的字母或下划线或中文")
    private String userName;

    @NotBlank(message = "请输入正确的邮箱")
    @Email(message = "请输入正确的邮箱")
    private String userEmail;

    @Size(max = 20, min = 5, message = "请输入5-20个长度的密码")
    private String userPassword;

    private String userGender;

    private String userBirthday;

    private Date userCreateTime;

    public User(Integer userId, @Pattern(regexp = "[\\u4e00-\\u9fa5a-zA-Z0-9_]{4,10}", message = "必须为4-10位的字母或下划线或中文") String userName, @NotBlank(message = "请输入正确的邮箱") @Email(message = "请输入正确的邮箱") String userEmail, @Size(max = 20, min = 5, message = "请输入5-20个长度的密码") String userPassword, String userGender, String userBirthday, Date userCreateTime) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userGender = userGender;
        this.userBirthday = userBirthday;
        this.userCreateTime = userCreateTime;
    }

    public User() {
        super();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail == null ? null : userEmail.trim();
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender == null ? null : userGender.trim();
    }

    public String getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday == null ? null : userBirthday.trim();
    }

    public Date getUserCreateTime() {
        return userCreateTime;
    }

    public void setUserCreateTime(Date userCreateTime) {
        this.userCreateTime = userCreateTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userGender='" + userGender + '\'' +
                ", userBirthday='" + userBirthday + '\'' +
                ", userCreateTime=" + userCreateTime +
                '}';
    }
}