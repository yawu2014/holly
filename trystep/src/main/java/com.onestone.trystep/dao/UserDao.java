package com.onestone.trystep.dao;

import com.holly.bean.User;

import java.util.List;

/**
 * @Author liuyj
 * @Description:
 * @date 2017/12/7 15:15
 */

public interface UserDao {
    Integer insert(User u);

    List<User> findAll();

    List<User> findByUserIds(List<Integer> userIds);
}
