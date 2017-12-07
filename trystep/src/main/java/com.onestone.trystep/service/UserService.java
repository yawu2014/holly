package com.onestone.trystep.service;

import com.holly.bean.User;

import java.util.List;

/**
 * @Author liuyj
 * @Description:
 * @date 2017/12/7 15:19
 */

public interface UserService {
    public boolean insert(User u);

    public List<User> findAll();

    public List<User> findByUserIds(List<Integer> ids);

    public void transactionTestSucess();

    public void transactionTestFailure() throws IllegalAccessException;
}
