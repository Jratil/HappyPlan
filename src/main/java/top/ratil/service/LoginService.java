package top.ratil.service;

public interface LoginService {

    /**
     * 登录
     * @param email 登录邮箱
     * @param password 登录密码
     * @return 返是否登录成功
     */
    boolean login(String email, String password) throws Exception;

    /**
     * 验证账号密码
     * @param email 登录邮箱
     * @param password 登录密码
     * @return 返回账号密码是否正确
     */
    boolean verify(String email, String password) throws Exception;
}
