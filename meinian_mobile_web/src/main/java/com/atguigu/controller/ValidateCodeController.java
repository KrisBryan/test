package com.atguigu.controller;

import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisMessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.utils.SMSUtils;
import com.atguigu.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * description:
 * Created by Kris on 2021/1/29
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
    @Autowired
    private JedisPool jedisPool;
    @RequestMapping("/send4Order.do")
    public Result send4Order(String telephone) {
//        生成四位验证码
        Integer code = ValidateCodeUtils.generateValidateCode(4);
        try {
//            发送短信
            SMSUtils.sendShortMessage(telephone, code.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        System.out.println("发送验证码为："+code);
        jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_ORDER, 300, String.valueOf(code));

        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
//发送登录短信
    @RequestMapping("/send4Login.do")
    public Result send4Login(String telephone) {
        Integer code = ValidateCodeUtils.generateValidateCode(4);

        try {
            SMSUtils.sendShortMessage(telephone, code.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        System.out.println("发送的手机登录验证码为：" + code);

        jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_LOGIN, 300, code.toString());

        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
