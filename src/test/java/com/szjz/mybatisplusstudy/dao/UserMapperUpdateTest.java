package com.szjz.mybatisplusstudy.dao;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.UpdateChainWrapper;
import com.szjz.mybatisplusstudy.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * author:szjz
 * date:2019/7/26
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperUpdateTest {


    @Autowired
    private UserMapper userMapper;


    /**
     * 根据ID更新保存对象
     * UPDATE user SET age=?, email=? WHERE id=?
     */
    @Test
    public void update() {
        User user = new User();
        user.setId(1088248166370832385l);
        user.setAge(26);
        user.setEmail("wtf2@baomidou.com");
        int i = userMapper.updateById(user);
        System.err.println("影响数据：" + i);

    }


    /**
     * 自由更新
     */
    @Test
    public void updateByWrapper() {

//        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
//        updateWrapper.eq("name","王天风");
        LambdaUpdateWrapper<User> updateWrapper = new UpdateWrapper<User>().lambda();
        updateWrapper.eq(User::getName, "王天风");

        User user = new User();
        user.setAge(27);
        user.setEmail("wtf3@baomidou.com");

        int i = userMapper.update(user, updateWrapper);
        System.err.println("影响数据：" + i);

    }


    /**
     * 简洁自由更新 lambda
     */
    @Test
    public void updateByWrapperLambda() {
        LambdaUpdateWrapper<User> updateWrapper = new UpdateWrapper<User>().lambda();
        updateWrapper
                .eq(User::getName, "王天风")
                .set(User::getAge, 28);
        int i = userMapper.update(null, updateWrapper);
        System.err.println("影响数据：" + i);

    }

    /**
     * 简洁自由更新 lambda链式调用 创建者模式
     */
    @Test
    public void updateByWrapperLambdaChain() {
        boolean update = new LambdaUpdateChainWrapper<User>(userMapper)
                .eq(User::getName, "王天风")
                .set(User::getAge, 28)
                .update();
        String res = update == true ? "成功" : "失败";
        System.err.println("更新：" + res);

    }


}