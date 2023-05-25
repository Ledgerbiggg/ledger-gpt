package com.ledger;

import com.ledger.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
class LedgerGptApplicationTests {

//     自动装配数据源
    @Autowired
    UserService userService;

    @Test
    void contextLoads() throws SQLException {
        System.out.println(userService);
    }

}
