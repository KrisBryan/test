package com.atguigu.dao;

import com.atguigu.pojo.Permission;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * description:
 * Created by Kris on 2021/1/31
 */
@Repository
public interface PermissionDao {
    Set<Permission> findPermissionsByRoleId(Integer roleId);
}
