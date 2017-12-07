package com.onestone.trystep.service.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.holly.bean.User;
import com.onestone.trystep.dao.UserDao;
import com.onestone.trystep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author liuyj
 * @Description:
 * @date 2017/12/7 15:21
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    public boolean insert(User u) {
        DruidDataSource dataSource;
        com.mysql.jdbc.Driver driver;
        return userDao.insert(u)>0?true:false;
    }

    public List<User> findAll() {
        return null;
    }

    public List<User> findByUserIds(List<Integer> ids) {
        return null;
    }

    public void transactionTestSucess() {

    }

    public void transactionTestFailure() throws IllegalAccessException {

    }
}
