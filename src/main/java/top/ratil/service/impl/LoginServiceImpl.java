package top.ratil.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.ratil.mapper.UserMapper;
import top.ratil.service.LoginService;
import top.ratil.utils.MD5Util;
import top.ratil.utils.UserSessionUtil;

/**
 * @program: HappyThings
 * @description: 登录Service
 * @author: Ratil
 * @create: 2018-07-23 18:32
 **/
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 登录
     *
     * @param email    登录邮箱
     * @param password 登录密码
     * @return
     */
    @Override
    public boolean login(String email, String password) throws Exception {
        boolean flag = verify(email, password);
        if (flag) {
            UserSessionUtil.setUserSession(userMapper.selectByEmail(email));
        }
        return flag;
    }

    /**
     * 登录验证
     *
     * @param email    登录邮箱
     * @param password 登录密码
     * @return
     */
    @Override
    public boolean verify(String email, String password) throws Exception {

        //如果邮箱不存在
        if (userMapper.selectByEmail(email) == null) {
            return false;
        }
        password = MD5Util.textToMD5(password);
        //获取查询到的邮箱的密码
        String selectPassword = userMapper.selectByEmail(email).getUserPassword();
        //如果密码不正确
        if (!password.equals(selectPassword)) {
            return false;
        }
        return true;
    }
}
