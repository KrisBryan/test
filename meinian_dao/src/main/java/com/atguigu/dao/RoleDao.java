package com.atguigu.dao;


import com.atguigu.pojo.Role;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * description:
 * Created by Kris on 2021/1/31
 */
@Repository
public interface RoleDao {
    Set<Role> findRolesByUserId(Integer userId);
}
