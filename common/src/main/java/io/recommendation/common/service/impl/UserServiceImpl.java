package io.recommendation.common.service.impl;

import io.recommendation.common.bean.User;
import io.recommendation.common.mapper.UserMapper;
import io.recommendation.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserByAccount(String account) {
        return userMapper.findUserByAccount(account);
    }

    @Override
    public boolean register(User user) {

        User user1 = userMapper.findUserByAccount(user.getAccount());
        if (user1 != null){
            return false;
        }

        int result = userMapper.insert(user);
        if (result != 0){
            return true;
        }
        return false;
    }
}
