package com.holly.test;

import com.holly.bean.User;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.onestone.trystep.service.UserService;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/5 15:25
 */

public class HystrixTest extends ApplicationTestBase {
    @Autowired
    UserService userService;
    @Test
    public void testHystrix(){

    }
    class GetUserCommand extends HystrixCommand<User>{
        private Integer uid ;
        protected GetUserCommand(Integer uid) {
            super(HystrixCommandGroupKey.Factory.asKey("hystrixTest"));
        }

        @Override
        protected User run() throws Exception {
            List<Integer> ids = Lists.newArrayList();
            ids.add(uid);
            return userService.findByUserIds(ids).get(0);
        }
    }
}
