package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Setmeal;
import com.atguigu.service.SetMealService;
import org.apache.zookeeper.data.Id;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * description:
 * Created by Kris on 2021/1/28
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealMobileController {
    @Reference
    SetMealService setMealService;
    @RequestMapping("/getSetmeal.do")
    public Result getSetmeal(){
        List<Setmeal> setmeals = setMealService.findAll();
        return new Result(true,MessageConstant.QUERY_SETMEALLIST_SUCCESS,setmeals);
    }

    @RequestMapping("/findById.do")
    public Result findById(Integer id){
        try {
            Setmeal setmeal = setMealService.findById(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }


    }
}
