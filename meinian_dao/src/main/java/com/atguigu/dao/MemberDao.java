package com.atguigu.dao;

import com.atguigu.pojo.Member;

/**
 * description:
 * Created by Kris on 2021/1/29
 */
public interface MemberDao {
    public void add(Member member);

    public Member findByTelephone(String telephone);
}
