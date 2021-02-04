package com.atguigu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.OrderSettingDao;
import com.atguigu.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description:
 * Created by Kris on 2021/1/28
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Override
    public void add(List<OrderSetting> orderSettings) {

        for (OrderSetting orderSetting :orderSettings) {
            long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
            if(count>0){
                orderSettingDao.editNumberByOrderDate(orderSetting);
            }else {
                orderSettingDao.add(orderSetting);
            }
        }
    }

    @Override
    public List<Map> getOrderSettingByMonth(String date) {
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(date);
        List<Map> data = new ArrayList<>();
        for (OrderSetting orderSetting: list){
            Map orderSettingMap = new HashMap();
//            获取当前日期
            orderSettingMap.put("date", orderSetting.getOrderDate().getDate());
//            获取可预约人数
            orderSettingMap.put("number", orderSetting.getNumber());
//            获取已经预约人数
            orderSettingMap.put("reservations", orderSetting.getReservations());
            data.add(orderSettingMap);
        }
        return data;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        OrderSetting orderSettingByOrderDate = orderSettingDao.getOrderSettingByOrderDate(orderSetting.getOrderDate());
                if(orderSettingByOrderDate==null){
                    orderSettingDao.add(orderSetting);
                }else {
                    if (orderSettingByOrderDate.getReservations()>orderSetting.getNumber()){
                        throw new RuntimeException("数据不符合");
                    }else {
                        orderSettingDao.editNumberByOrderDate(orderSetting);
                    }
                }
//        OrderSetting orderSetting1=  orderSettingDao.getOrderSettingByOrderDate(orderSetting.getOrderDate());
//        //orderSetting1 使用已预约人数 和页面传过来的可预约人数比较
//        if(orderSetting1==null){
//            orderSettingDao.add(orderSetting);
//        }else{
//            if(orderSetting1.getReservations()>orderSetting.getNumber()){
//                throw  new RuntimeException("不允许修改");
//            }else{
//                orderSettingDao.editNumberByOrderDate(orderSetting);
//            }
//        }
    }


}
