package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisMessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Order;
import com.atguigu.service.OrderService;
import com.atguigu.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * description:
 * Created by Kris on 2021/1/29
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private OrderService orderService;

    @RequestMapping("/submit.do")
    public Result submitOrder(@RequestBody Map map) {
        // ① 在页面获取手机号
        String telephone = (String) map.get("telephone");
        // ② 在页面获取验证码
        String validateCode = (String) map.get("validateCode");
        //从Redis中获取缓存的验证码，key为手机号+RedisConstant.SENDTYPE_ORDER
        String codeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);

//        校验手机验证码
        if (codeInRedis == null || !codeInRedis.equals(validateCode)) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        Result result = null;
        try {
            map.put("orderType", Order.ORDERTYPE_WEIXIN);
            result = orderService.order(map);
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
        if (result.isFlag()) {
            String orderDate = (String) map.get("orderDate");
            try {
                SMSUtils.sendShortMessage(telephone,orderDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @RequestMapping("/findById.do")
    public Result findById(Integer id) {
        Map map = null;
        try {
            map = orderService.findById4Detail(id);
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ORDER_FAIL);
        }
    }
}
