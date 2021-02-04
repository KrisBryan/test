package com.atguigu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.constant.RedisConstant;
import com.atguigu.dao.SetMealDao;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.Setmeal;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description:
 * Created by Kris on 2021/1/23
 */
@Service(interfaceClass = SetMealService.class)
@Transactional
public class SetMealServiceImpl implements SetMealService {
    @Autowired
    private SetMealDao setMealDao;

    @Autowired
    JedisPool jedisPool;

    @Override
    public void add(Setmeal setmeal, Integer[] travelgroupIds) {
        setMealDao.add(setmeal);
        if(travelgroupIds != null && travelgroupIds.length > 0){
            //绑定套餐和跟团游的多对多关系
            setSetmealAndTravelGroup(setmeal.getId(),travelgroupIds);
        }
        //将图片名称保存到Redis
        savePic2Redis(setmeal.getImg());
    }
    //将图片名称保存到Redis
    private void savePic2Redis(String pic){
//        if(pic!=null){
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,pic);
//
//        }
    }


    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        //使用分页插件 PageHelper
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<Setmeal> page = setMealDao.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    private void setSetmealAndTravelGroup(Integer id, Integer[] travelgroupId) {
        for (Integer checkGroupID : travelgroupId) {
            Map<String, Object> map = new HashMap<>();
            map.put("travelgroup_id", checkGroupID);
            map.put("setmeal_id", id);
            setMealDao.setSetMealAndTravelGroup(map);
        }
    }

//移动端
    @Override
    public List<Setmeal> findAll() {
        return setMealDao.findAll();
    }

//    查看当前套餐的详细信息
    @Override
    public Setmeal findById(Integer id) {
        return setMealDao.findById(id);
    }
}
