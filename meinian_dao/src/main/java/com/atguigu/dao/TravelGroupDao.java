package com.atguigu.dao;

import com.atguigu.pojo.TravelGroup;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * description:
 * Created by Kris on 2021/1/22
 */
public interface TravelGroupDao {
    public void add(TravelGroup travelGroup);

    public void setTravelGroupAndTravelItem(Map<String, Object> map);

    public Page<TravelGroup> findPage(String queryString);

    public TravelGroup findById(Integer id);

    public List<Integer> findTravelItemIdByTravelgroupId(Integer id);

    public void deleteTravelGroupAndTravelItemByTravelGroupId(Integer id);

    public void edit(TravelGroup travelGroup);

    public void deleteGroupById(Integer id);

    public void deleteGroupAndItemById(Integer id);

    public List<TravelGroup> findAll();

    List<TravelGroup> findTravelGroupListById(Integer id);
}
