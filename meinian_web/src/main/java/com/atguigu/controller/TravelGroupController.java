package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.service.TravelGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * description:不做业务处理，只向service传想要的数据
 * Created by Kris on 2021/1/22
 */
@RequestMapping("/travelgroup")
@RestController
public class TravelGroupController {
    @Reference
    private TravelGroupService travelGroupService;

    @RequestMapping("/add.do")
    public Result add(@RequestBody TravelGroup travelGroup, Integer[] travelItemIds) {
        try {
            travelGroupService.add(travelGroup, travelItemIds);
        } catch (Exception e) {
            return new Result(false, MessageConstant.ADD_TRAVELGROUP_FAIL);
        }
        return new Result(true, MessageConstant.ADD_TRAVELGROUP_SUCCESS);
    }

    @RequestMapping("/findPage.do")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = travelGroupService.findPage(queryPageBean);
        return pageResult;
    }

    @RequestMapping("/findTravelItemIdByTravelgroupId.do")
    public List<Integer> findTravelItemIdByTravelgroupId(Integer id) {
        List<Integer> list = travelGroupService.findTravelItemIdByTravelgroupId(id);
        return list;
    }

    @RequestMapping("/findById.do")
    public Result findById(Integer id) {
        TravelGroup travelGroup = null;
        try {
            travelGroup = travelGroupService.findById(id);
        } catch (Exception e) {
            return new Result(false, MessageConstant.QUERY_TRAVELGROUP_FAIL);
        }
        return new Result(true, MessageConstant.QUERY_TRAVELGROUP_SUCCESS, travelGroup);
    }

    @RequestMapping("/edit.do")
    public Result edit(Integer[] travelItemIds, @RequestBody TravelGroup travelGroup) {
        try {
            travelGroupService.edit(travelItemIds, travelGroup);
        } catch (Exception e) {
            return new Result(false, MessageConstant.EDIT_TRAVELGROUP_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_TRAVELGROUP_SUCCESS);
    }

    @RequestMapping("/deleteGroup.do")
    public Result deleteGroup(Integer id) {
        try {
            travelGroupService.deleteGroupAndItem(id);
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_TRAVELGROUP_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_TRAVELGROUP_SUCCESS);
    }

    @RequestMapping("/findAll.do")
    public Result findAll() {
        List<TravelGroup> travelGroupList = travelGroupService.findALL();
        if (travelGroupList != null && travelGroupList.size() > 0) {
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, travelGroupList);
        }
        return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
    }
}
