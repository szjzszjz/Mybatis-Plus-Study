package com.szjz.mybatisplusstudy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.szjz.mybatisplusstudy.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;


/**
 * author:szjz
 * date:2019/7/26
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void getOne() {
        //传入false 若查询的数量大于一个 会给出警告 但是不会报错 默认返回第一条数据
        User one = userService.getOne(new QueryWrapper<User>().lambda().gt(User::getAge, 25), false);
        System.err.println(one);
    }

    /**
     * 批量保存
     */
    @Test
    public void saveBatch() {
        User user = new User();
        user.setName("马虎");
        user.setAge(44);

        User user1 = new User();
        user1.setName("马虎蛋");
        user1.setAge(55);

        List<User> users = Arrays.asList(user, user1);

        boolean b = userService.saveBatch(users);
        //默认以id为主键 没有就插入 有就更新
        boolean b1 = userService.saveOrUpdateBatch(users);
        System.err.println(b);
    }


    @Test
    public void lambdaChainSelect(){
        List<User> list = userService.lambdaQuery().gt(User::getAge, 25).list();
        list.forEach(System.out::println);
    }

    @Test
    public void lambdaChainUpdate(){
        boolean update = userService.lambdaUpdate().eq(User::getAge, 40).set(User::getAge,100).update();
        System.err.println(update);
    }

    @Test
    public void lambdaChainRemove(){
        boolean remove = userService.lambdaUpdate().eq(User::getAge, 44).remove();
        System.err.println(remove);
    }

}