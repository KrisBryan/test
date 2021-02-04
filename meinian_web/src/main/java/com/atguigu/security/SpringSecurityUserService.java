package com.atguigu.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.pojo.Permission;
import com.atguigu.pojo.Role;
import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * description:
 * Created by Kris on 2021/1/31
 */
@Component
public class SpringSecurityUserService implements UserDetailsService {
    @Reference
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= userService.findUserByUsername(username);
        if(user == null){
            //用户名不存在，抛出异常UsernameNotFoundException
            return null;
        }
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        Set<Role> roles = user.getRoles();
        for(Role role : roles){
            Set<Permission> permissions = role.getPermissions();
            for(Permission permission : permissions){
                //授权
                list.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }
        /**
         * User()
         * 1：指定用户名
         * 2：指定密码（SpringSecurity会自动对密码进行校验）
         * 3：传递授予的角色和权限
         */
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(username,user.getPassword(),list);
        return userDetails;

    }
}
