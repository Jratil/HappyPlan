package top.ratil.mapper;

import top.ratil.entity.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    User selectByEmail(String userEmail);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}