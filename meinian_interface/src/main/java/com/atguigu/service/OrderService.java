package com.atguigu.service;

import com.atguigu.entity.Result;

import java.util.Map;

/**
 * description:
 * Created by Kris on 2021/1/29
 */
public interface OrderService {
    Result order(Map map) throws Exception;

    Map findById4Detail(Integer id) throws Exception;
}
