package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisMessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Member;
import com.atguigu.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * description:
 * Created by Kris on 2021/1/31
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Reference
    private MemberService memberService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/check.do")
    public Result check(HttpServletResponse response, @RequestBody Map map) {

//        获取页面端的手机号
        String telephone = (String) map.get("telephone");
//        获取页面端输入的验证码
        String validateCode = (String) map.get("validateCode");
//        从jedis中获取验证码判断是否相等
        String codeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
        if (codeInRedis == null || !codeInRedis.equals(validateCode)) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }else {
            Member member =memberService.findByTelephone(telephone);
            if (member == null) {
                member = new Member();
                member.setPhoneNumber(telephone);
                member.setRegTime(new Date());
                memberService.add(member);
            }
        }
        Cookie cookie = new Cookie("login_member_telephone", telephone);
        cookie.setPath("/");
        cookie.setMaxAge(60*60*24*30);
        response.addCookie(cookie);
        return new Result(true, MessageConstant.LOGIN_SUCCESS);
    }
}
