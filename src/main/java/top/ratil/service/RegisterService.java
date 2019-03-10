package top.ratil.service;

import top.ratil.entity.User;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface RegisterService {

    /**
     * 注册
     * @param user 注册信息
     * @return 返回注册的状态码
     */
    boolean register(User user) throws Exception;

    /**
     * 验证注册信息
     * @param user 注册信息
     * @return 返回注册状态
     */
    boolean verify(User user) throws Exception;


    /**
     * 注册时发送验证码
     *
     * @param receiver 接收邮箱
     * @return 发送验证码成功
     *          则返回验证码
     *          否则返回 null
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     */
    String sendVerifyCode(String receiver) throws  UnsupportedEncodingException, MessagingException;
}
