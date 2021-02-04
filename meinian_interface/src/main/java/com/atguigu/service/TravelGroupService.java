package com.atguigu.service;

import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.TravelGroup;

import java.util.List;

/**
 * description:
 * Created by Kris on 2021/1/22
 */
public interface TravelGroupService {
    public void add(TravelGroup travelGroup, Integer[] travelItemIds);

    public PageResult findPage(QueryPageBean queryPageBean);

    public TravelGroup findById(Integer id);

    public List<Integer> findTravelItemIdByTravelgroupId(Integer id);

//    public void deleteTravelGroupAndTravelItemByTravelGroupId(Integer id);

    public  void edit(Integer[] travelItemIds,TravelGroup travelGroup);

    public void deleteGroupAndItem(Integer id);

    public List<TravelGroup> findALL();
}
