package com.atguigu.service;

import com.atguigu.pojo.User;

/**
 * description:
 * Created by Kris on 2021/1/31
 */
public interface UserService {
     User findUserByUsername(String username);
}
