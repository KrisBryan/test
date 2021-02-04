package com.atguigu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.TravelGroupDao;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.TravelGroup;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description:
 * Created by Kris on 2021/1/22
 */
@Service(interfaceClass = TravelGroupService.class)
@Transactional
public class TravelGroupServiceImpl implements TravelGroupService {
    @Autowired
    private TravelGroupDao travelGroupDao;

    @Override
    public void add(TravelGroup travelGroup, Integer[] travelItemIds) {
//        新增跟团游，向t_travelGroup表中添加数据，新增后返回新增的id
        travelGroupDao.add(travelGroup);
//        向跟团游和自由行的中间表t_travelgroup_travelitem添加数据
        setTravelGroupAndTravelItem(travelGroup.getId(), travelItemIds);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        //使用分页插件 PageHelper
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<TravelGroup> page = travelGroupDao.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public TravelGroup findById(Integer id) {
        return travelGroupDao.findById(id);
    }

    @Override
    public List<Integer> findTravelItemIdByTravelgroupId(Integer id) {
        return travelGroupDao.findTravelItemIdByTravelgroupId(id);
    }

//    @Override
//    public void deleteTravelGroupAndTravelItemByTravelGroupId(Integer id) {
//
//    }

    @Override
    public void edit(Integer[] travelItemIds, TravelGroup travelGroup) {
        travelGroupDao.edit(travelGroup);
        travelGroupDao.deleteTravelGroupAndTravelItemByTravelGroupId(travelGroup.getId());
        setTravelGroupAndTravelItem(travelGroup.getId(),travelItemIds);
    }

    @Override
    public void deleteGroupAndItem(Integer id) {
        List<Integer> deleteList = travelGroupDao.findTravelItemIdByTravelgroupId(id);
        if(deleteList!=null&& deleteList.size()>0){
            travelGroupDao.deleteGroupAndItemById(id);
        }
        travelGroupDao.deleteGroupById(id);
    }

    @Override
    public List<TravelGroup> findALL() {
        return travelGroupDao.findAll();
    }

    public void setTravelGroupAndTravelItem(Integer travelGroupId, Integer[] travelItemIds) {
        if (travelItemIds != null && travelItemIds.length > 0) {
            for (Integer travelItemId : travelItemIds) {
                Map<String, Object> map = new HashMap<>();
                map.put("travelGroup", travelGroupId);
                map.put("travelItem", travelItemId);
                travelGroupDao.setTravelGroupAndTravelItem(map);
            }
        }
    }
}
