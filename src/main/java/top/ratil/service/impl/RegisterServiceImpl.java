package top.ratil.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.ratil.entity.User;
import top.ratil.mapper.UserMapper;
import top.ratil.service.RegisterService;
import top.ratil.utils.MD5Util;
import top.ratil.utils.email.EmailUtil;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

/**
 * @program: HappyThings
 * @description: 注册Service
 * @author: Ratil
 * @create: 2018-07-23 18:32
 **/
@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EmailUtil emailUtil;

    /**
     * 注册
     *
     * @param user 注册信息
     * @return 是否插入数据成功
     * true: 成功
     * false: 失败
     * @throws Exception
     */
    @Override
    public boolean register(User user) throws Exception {
        if (verify(user)) {
            return false;
        }
        //获取注册的密码并进行加密
        String password = user.getUserPassword();
        user.setUserPassword(MD5Util.textToMD5(password));
        //开始注册并返回影响行数
        // int count = userMapper.insertSelective(user);
        // return count != 0;
        return userMapper.insertSelective(user) != 0;
    }

    /**
     * 验证注册信息
     *
     * @param user 注册信息
     * @return 返回注册状态
     *          true 不等于null: 邮箱已注册
     *          false 等于null: 邮箱未注册
     */
    @Override
    public boolean verify(User user) throws Exception {
        String email = user.getUserEmail();

        return userMapper.selectByEmail(email) != null;
    }

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
    @Override
    public String sendVerifyCode(String receiver) throws UnsupportedEncodingException, MessagingException {

        // 判断是否发送验证码成功
        if (emailUtil.sendEmail(receiver)) {
            return emailUtil.getVerifyCode();
        }
        return null;
    }
}
