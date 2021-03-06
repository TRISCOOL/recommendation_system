package io.recommendation.common.service;

import io.recommendation.common.bean.User;

public interface UserService {
    User findUserByAccount(String account);

    boolean register(User user);
}
