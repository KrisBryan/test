package com.atguigu.service;

import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.Setmeal;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * description:
 * Created by Kris on 2021/1/23
 */
public interface SetMealService {
    public void add(Setmeal setmeal, Integer[] travelgroupIds);

    public PageResult findPage(QueryPageBean queryPageBean);

    List<Setmeal> findAll();

    Setmeal findById(Integer id);
}
