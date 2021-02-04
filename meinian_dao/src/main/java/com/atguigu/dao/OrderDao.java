package com.atguigu.dao;

import com.atguigu.pojo.Order;

import java.util.List;
import java.util.Map;

/**
 * description:
 * Created by Kris on 2021/1/29
 */
public interface OrderDao {
    List<Order> findByCondition(Order order);

    void add(Order order);

    Map findById4Detail(Integer id);
}
