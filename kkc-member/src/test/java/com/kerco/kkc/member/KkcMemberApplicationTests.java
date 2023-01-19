package com.kerco.kkc.member;

import com.kerco.kkc.member.entity.User;
import com.kerco.kkc.member.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class KkcMemberApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {

    }
}
