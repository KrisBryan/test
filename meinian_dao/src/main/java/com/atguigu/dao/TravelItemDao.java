package com.atguigu.dao;

import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.pojo.TravelItem;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * description:
 * Created by Kris on 2021/1/20
 */
public interface TravelItemDao {
    public void add(TravelItem travelItem);

    public Page findPage(String queryPageBean);

    public void deleteById(Integer id);

    public long findCountByTravelItemItemId(Integer id);

    public TravelItem findById(Integer id);

    public void edit(TravelItem travelItem);

    public List<TravelItem> findAll();

//    List<TravelItem> findTravelItemListById(Integer id);
}
