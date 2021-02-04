package com.atguigu.service;

import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.pojo.TravelItem;

import java.util.List;

/**
 * description:
 * Created by Kris on 2021/1/20
 */
public interface TravelItemService {
    public void add(TravelItem travelItem);

    public PageResult findPage(QueryPageBean queryPageBean);

    public void deleteById(Integer id);

    public TravelItem findById(Integer id);

    public void edit(TravelItem travelItem);

    public List<TravelItem> findAll();

}
