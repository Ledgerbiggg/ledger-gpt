package com.ledger.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ledger.common.Result;
import com.ledger.domain.User;
import com.ledger.mapper.UserMapper;
import com.ledger.service.UserService;
import com.ledger.threa.UserThread;
import org.springframework.stereotype.Service;

/**
 * @author ledger
 * @version 1.0
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public Result<User> getUserInfo() {
        String currentUser = UserThread.getCurrentUser();
        User user = getById(currentUser);
        return Result.success(user);
    }

    @Override
    public Result<String> getAvatar() {
        String currentUser = UserThread.getCurrentUser();
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getId,currentUser);
        User one = getOne(userLambdaQueryWrapper);
        return Result.success(one.getAvatarUrl());
    }

    @Override
    public Result<String> changeImage(User user) {
        String currentUser = UserThread.getCurrentUser();
        user.setId(currentUser);
        LambdaUpdateWrapper<User> userLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        userLambdaUpdateWrapper.eq(User::getId,currentUser);
        boolean updateById = update(user,userLambdaUpdateWrapper);
        return updateById?Result.success("保存成功"):Result.error("保存失败");
    }
}
