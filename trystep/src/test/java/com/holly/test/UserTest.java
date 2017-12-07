package com.holly.test;

import com.holly.bean.User;
import com.onestone.trystep.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserTest extends ApplicationTestBase {
    @Autowired
    UserService userService;
    @Test
    public void testUser(){
        User u = new User();
        u.setAge(23);
        u.setUserId(11);
        u.setName("hello");
        userService.insert(u);
    }

}
