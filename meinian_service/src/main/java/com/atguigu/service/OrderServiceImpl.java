package com.atguigu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.constant.MessageConstant;
import com.atguigu.dao.MemberDao;
import com.atguigu.dao.OrderDao;
import com.atguigu.dao.OrderSettingDao;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Member;
import com.atguigu.pojo.Order;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * description:
 * Created by Kris on 2021/1/29
 */
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderSettingDao orderSettingDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;

    @Override
    public Result order(Map map) throws Exception {

//        检查当前日期是否进行了预约设置
//        获取当前日期
        String orderDate = (String) map.get("orderDate");
//        对日期进行格式转换
        Date date = DateUtils.parseString2Date(orderDate);
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(date);
        if (orderSetting == null) {
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        } else {
            int number = orderSetting.getNumber();
            int reservations = orderSetting.getReservations();
//            验证当前预约是否已满
            if (reservations >= number) {
                return new Result(false, MessageConstant.ORDER_FULL);
            }
        }

//        获取手机号
        String telephone = (String) map.get("telephone");
        //（2）使用手机号，查询会员表，判断当前预约人是否是会员
        // 根据手机号，查询会员表，判断当前预约人是否是会员
        Member member = memberDao.findByTelephone(telephone);
        if (member != null) {
            Integer memberId = member.getId();
            Integer setmealId = Integer.parseInt((String) map.get("setmealId"));
            Order order = new Order(memberId, date, null, null, setmealId);
            // 根据预约信息查询是否已经预约
            List<Order> list = orderDao.findByCondition(order);
            // 判断是否已经预约list不等于null，说明已经预约，不能重复预约
            if (list != null && list.size() > 0) {
                return new Result(false, MessageConstant.HAS_ORDERED);
            }
        }else {
            member = new Member();
            member.setName((String) map.get("name"));
            member.setSex((String) map.get("sex"));
            member.setPhoneNumber((String) map.get("telephone"));
            member.setIdCard((String) map.get("idCard"));
            member.setRegTime(new Date()); // 会员注册时间，当前时间
            memberDao.add(member);
        }

        //（3）可以进行预约，更新预约设置表中预约人数的值，使其的值+1
        //可以预约，设置预约人数加一
        orderSetting.setReservations(orderSetting.getReservations() + 1);
        orderSettingDao.editReservationsByOrderDate(orderSetting);
        //（4）可以进行预约，向预约表中添加1条数据
        //保存预约信息到预约表
        Order order = new Order();
        order.setMemberId(member.getId()); //会员id
        order.setOrderDate(date); // 预约时间
        order.setOrderStatus(Order.ORDERSTATUS_NO); // 预约状态（已出游/未出游）
        order.setOrderType((String)map.get("orderType"));
        order.setSetmealId(Integer.parseInt((String)map.get("setmealId")));
        orderDao.add(order);

        return new Result(true, MessageConstant.ORDER_SUCCESS, order);
    }

    public Map findById4Detail(Integer id) throws Exception {
        Map map = orderDao.findById4Detail(id);
        if (map != null) {
            Date orderDate = (Date) map.get("orderDate");
            map.put("orderDate", DateUtils.parseDate2String(orderDate));
            return map;
        }
        return map;
    }
}
