package com.ledger.intercepter;

import cn.hutool.core.util.RandomUtil;
import com.ledger.domain.User;
import com.ledger.service.UserService;
import com.ledger.threa.UserThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ledger
 * @version 1.0
 **/
@Component
@Slf4j
public class ECInterceptor implements HandlerInterceptor {

    private static UserService userService;
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    //注入进去
    @Autowired
    public void setUserService(UserService userService) {
        ECInterceptor.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authorization = request.getHeader(AUTHORIZATION_HEADER);
        if (authorization != null) {
            authorization = authorization.replace(BEARER_PREFIX,"");
        }
        User user = userService.getById(authorization);
        if (user == null) {
            String string = RandomUtil.randomString(8);
            response.setHeader("Access-Control-Expose-Headers", "ledger");
            response.addHeader("ledger", string);
            //新账户
            User newUser = new User();
            newUser.setId(string);
            userService.save(newUser);
            UserThread.setCurrentUser(string);
            return true;
        }
        //老账户
        UserThread.setCurrentUser(authorization);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserThread.clear();
    }
}
