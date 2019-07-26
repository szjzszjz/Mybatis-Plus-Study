package com.szjz.mybatisplusstudy.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;
import com.szjz.mybatisplusstudy.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * author:szjz
 * date:2019/7/26
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperDeleteTest {


    @Autowired
    private UserMapper userMapper;


    /**
     * 根据ID更新保存对象
     * UPDATE user SET age=?, email=? WHERE id=?
     */
    @Test
    public void delete() {
        int i = userMapper.deleteById(1154307286332661762l);
        System.err.println("影响数据：" + i);

    }


    @Test
    public void deleteByMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name","小明");

        int i = userMapper.deleteByMap(map);
        System.err.println("影响数据：" + i);

    }

    @Test
    public void deleteByIds() {
        int i = userMapper.deleteBatchIds(Arrays.asList(1154652983242285057l,1154653217850773505l));
        System.err.println("影响数据：" + i);

    }

    @Test
    public void deleteByLambda() {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getName,"小强");
        int i = userMapper.delete(wrapper);
        System.err.println("影响数据：" + i);

    }
}