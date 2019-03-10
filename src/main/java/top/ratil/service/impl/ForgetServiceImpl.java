package top.ratil.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.ratil.entity.User;
import top.ratil.mapper.UserMapper;
import top.ratil.service.ForgetService;
import top.ratil.utils.MD5Util;

/**
 * @program: HappyPlan
 * @description: 重新设置密码
 * @author: Ratil
 * @create: 2018-08-18 21:57
 **/
@Service
public class ForgetServiceImpl implements ForgetService {
    @Autowired
    UserMapper userMapper;

    User user;

    @Override
    public boolean forgetPassword(String email, String password) throws Exception {
        //判断查询是否为空
        if (verify(email)) {
            return false;
        }
        password = MD5Util.textToMD5(password);
        user.setUserPassword(password);
        return userMapper.updateByPrimaryKeySelective(user) > 0;
    }

    @Override
    public boolean verify(String email) {

        user = userMapper.selectByEmail(email);
        //如果邮箱不存在
        return user == null;
    }
}
