package com.atguigu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.TravelItemDao;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.pojo.TravelItem;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * description:
 * Created by Kris on 2021/1/19
 */
@Service(interfaceClass = TravelItemService.class)
//添加事务注解
@Transactional
public class TravelItemServiceImpl implements TravelItemService {
    @Autowired
    private TravelItemDao travelItemDao;

    @Override
    public void add(TravelItem travelItem) {
        travelItemDao.add(travelItem);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page page = travelItemDao.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void deleteById(Integer id) {
//        查看中间表是否有id，若存在则抛异常
        long count = travelItemDao.findCountByTravelItemItemId(id);
        if(count>0){
            throw new RuntimeException("无法删除");
        }
        travelItemDao.deleteById(id);
    }

    @Override
    public TravelItem findById(Integer id) {
        return travelItemDao.findById(id);
    }

    @Override
    public void edit(TravelItem travelItem) {
        travelItemDao.edit(travelItem);
    }

    @Override
    public List<TravelItem> findAll() {
        return travelItemDao.findAll();
    }
}
