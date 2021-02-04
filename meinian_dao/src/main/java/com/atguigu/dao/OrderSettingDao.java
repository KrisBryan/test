package com.atguigu.dao;

import com.atguigu.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * description:
 * Created by Kris on 2021/1/28
 */
public interface OrderSettingDao {
    void add(OrderSetting orderSetting);

    long findCountByOrderDate(Date orderDate);

    void editNumberByOrderDate(OrderSetting orderSetting);

    List<OrderSetting> getOrderSettingByMonth(@Param("date") String date);

    OrderSetting getOrderSettingByOrderDate(Date orderDate);

    OrderSetting findByOrderDate(Date date);

    void editReservationsByOrderDate(OrderSetting orderSetting);
}
