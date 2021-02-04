package com.atguigu.service;


import com.atguigu.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * description:
 * Created by Kris on 2021/1/28
 */

public interface OrderSettingService {
    void add(List<OrderSetting> orderSettings);

    List<Map> getOrderSettingByMonth(String date);

    void editNumberByDate(OrderSetting orderSetting);


}
