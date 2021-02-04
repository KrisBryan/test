package com.atguigu.dao;

import com.atguigu.pojo.User;

/**
 * description:
 * Created by Kris on 2021/1/31
 */
public interface UserDao {
    User findUserByUsername(String username);
}
