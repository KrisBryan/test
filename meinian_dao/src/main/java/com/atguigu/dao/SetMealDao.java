package com.atguigu.dao;

import com.atguigu.pojo.Setmeal;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * description:
 * Created by Kris on 2021/1/23
 */
public interface SetMealDao {
    public void add(Setmeal setmeal);

    public void setSetMealAndTravelGroup(Map<String, Object> map);

    public Page<Setmeal> findPage(String queryString);

    List<Setmeal> findAll();

    Setmeal findById(Integer id);
}
