package com.atguigu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.UserDao;
import com.atguigu.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * description:
 * Created by Kris on 2021/1/31
 */
@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User findUserByUsername(String username) {
        User user = userDao.findUserByUsername(username);
        return user;
    }
}
