package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.TravelItem;
import com.atguigu.service.TravelItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * description:
 * Created by Kris on 2021/1/20
 */
@RestController
@RequestMapping("/travelItem")
public class TravelItemController {
    @Reference
    private TravelItemService travelItemService;

    @RequestMapping("/add.do")
    @PreAuthorize("hasAuthority('TRAVELITEM_ADD')")//权限校验
    public Result add(@RequestBody TravelItem travelItem) {
        try {
            travelItemService.add(travelItem);
        } catch (Exception e) {
            e.printStackTrace();
//            当add添加失败后执行 返回值信息同下
            return new Result(false, MessageConstant.ADD_TRAVELGROUP_FAIL);
        }
//       当add添加成功后执行 这里返回的两个值分别要对应到前端，给前端Vue语句一个true返回结果和一个返回消息信息
        return new Result(true, MessageConstant.ADD_TRAVELITEM_SUCCESS);
    }

    @RequestMapping("/findPage.do")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {

        PageResult pageResult = travelItemService.findPage(queryPageBean);
        return pageResult;
    }
    @RequestMapping("/delete.do")
//    TRAVELITEM_DELETE
    @PreAuthorize("hasAuthority('')")
    public Result delete(Integer id){
        try {
            travelItemService.deleteById(id);
            return new Result(true, MessageConstant.DELETE_TRAVELITEM_SUCCESS);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_TRAVELITEM_FAIL);
        }
    }

    @RequestMapping("/findById.do")
    public Result findById(Integer id){
        TravelItem travelItem = travelItemService.findById(id);
        return new Result(true,MessageConstant.QUERY_TRAVELITEM_SUCCESS,travelItem);
    }

    @RequestMapping("/edit.do")
    @PreAuthorize("hasAuthority('TRAVELITEM_EDIT')")//权限校验
    public Result edit(@RequestBody TravelItem travelItem){
        travelItemService.edit(travelItem);
        return new Result(true, MessageConstant.EDIT_TRAVELITEM_SUCCESS);
    }

    @RequestMapping("/findAll.do")
    public Result findAll(){
        List<TravelItem> lists = travelItemService.findAll();
        return new Result(true, MessageConstant.QUERY_TRAVELGROUP_SUCCESS, lists);
    }
}
