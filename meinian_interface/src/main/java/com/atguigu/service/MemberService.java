package com.atguigu.service;

import com.atguigu.pojo.Member;

/**
 * description:
 * Created by Kris on 2021/1/31
 */
public interface MemberService {
    Member findByTelephone(String telephone);

    void add(Member member);
}
