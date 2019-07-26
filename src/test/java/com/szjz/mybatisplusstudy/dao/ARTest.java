package com.szjz.mybatisplusstudy.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.szjz.mybatisplusstudy.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * author:szjz
 * date:2019/7/25
 *
 * Active Record
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ARTest {

    /**
     *
     */
    @Test
    public void insert() {
        User user = new User();
        user.setAge(32);
        user.setCreateTime(LocalDateTime.now());
        user.setManagerId(1088248166370832385l);
        user.setName("小明");
        boolean insert = user.insert();
        System.err.println(insert);
    }


    @Test
    public void deleteById(){
        User user = new User();
        boolean b = user.deleteById(1154657307670392834l);
        System.err.println(b);
    }

    @Test
    public void selectById(){
        User user = new User();
        User b = user.selectById(1087982257332887553l);
        System.err.println(b);
    }

    @Test
    public void selectById01(){
        User user = new User();
        user.setId(1087982257332887553l);
        User b = user.selectById();
        System.err.println(b);
    }

    @Test
    public void selectAll(){
        User user = new User();
        List<User> b = user.selectAll();
        System.err.println(b);
    }

    //存在就更新 不存在就插入
    @Test
    public void insertOrUpdate(){
        User user = new User();
        user.setId(111l);
        user.setName("hha");
        boolean b = user.insertOrUpdate();
        System.err.println(b);
    }

    @Test
    public void updateById(){
        User user = new User();
        user.setId(1087982257332887553l);
        user.setName("小BOSS");
        boolean b = user.updateById();
        System.err.println(b);
    }


}