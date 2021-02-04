package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisConstant;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Setmeal;
import com.atguigu.service.SetMealService;
import com.atguigu.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

/**
 * description:
 * Created by Kris on 2021/1/23
 */
@RestController
@RequestMapping("/setmeal")
public class SetMealController {
    @Reference
    private SetMealService setMealService;

    @Autowired
    private JedisPool jedisPool;
    @RequestMapping("/upload.do")
    public Result upload(@RequestParam("imgFile")MultipartFile imgFile)  {
            try {
                //获取原始文件名
                String originalFilename = imgFile.getOriginalFilename();
                int lastIndexOf = originalFilename.lastIndexOf(".");
                //获取文件后缀
                String suffix = originalFilename.substring(lastIndexOf);
                //使用UUID随机产生文件名称，防止同名文件覆盖
                String fileName = UUID.randomUUID().toString() + suffix;
                QiniuUtils.upload2Qiniu(imgFile.getBytes(), fileName);
                //图片上传成功
                Result result = new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, fileName);
                //将上传图片名称存入Redis，基于Redis的Set集合存储
                jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES, fileName);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                //图片上传失败
                return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
            }
        }



        @RequestMapping("/add.do")
    public Result add(@RequestBody Setmeal setmeal,Integer[] travelgroupIds){
        try {
            setMealService.add(setmeal,travelgroupIds);
        } catch (Exception e) {
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = setMealService.findPage(queryPageBean);
        return pageResult;
    }
}
