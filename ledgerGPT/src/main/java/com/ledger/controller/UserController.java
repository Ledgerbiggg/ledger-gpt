package com.ledger.controller;

import com.ledger.common.Result;
import com.ledger.domain.User;
import com.ledger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ledger
 * @version 1.0
 **/
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getInfo")
    public Result<User> getUserInfo(){
        return userService.getUserInfo();
    }
    @GetMapping("/getAvatar")
    public Result<String> getAvatar(){
        return userService.getAvatar();
    }
    @PostMapping("/editInfo")
    public Result<String> changeImage(@RequestBody User user){
        return userService.changeImage(user);
    }

}
