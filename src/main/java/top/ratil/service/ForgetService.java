package top.ratil.service;

public interface ForgetService {

    /**
     * 重设密码
     *
     * @param email
     * @param password
     * @return
     */
    boolean forgetPassword(String email, String password) throws Exception;

    /**
     * 验证账号密码
     *
     * @param email
     * @return
     */
    boolean verify(String email);
}
