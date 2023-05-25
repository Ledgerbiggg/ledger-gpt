package com.ledger.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ledger.common.Result;
import com.ledger.domain.User;

/**
 * @author ledger
 * @version 1.0
 **/
public interface UserService extends IService<User> {
    Result<User> getUserInfo();

    Result<String> getAvatar();

    Result<String> changeImage(User user);
}
