package com.szjz.mybatisplusstudy.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.szjz.mybatisplusstudy.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.*;


/**
 * author:szjz
 * date:2019/7/25
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTest {

    /**
     *
     */
    @Autowired
    private UserMapper userMapper;

    @Test
    public void select() {
        List<User> users = userMapper.selectList(null);
        Assert.notNull(users, "没有数据");
        System.err.println(users);
    }

    @Test
    public void insert() {
        User user = new User();
        user.setAge(32);
        user.setCreateTime(LocalDateTime.now());
        user.setManagerId(1088248166370832385l);
        user.setName("小强");
        int rows = userMapper.insert(user);
        System.out.println(rows);
    }

    @Test
    public void selectById() {
        User user = userMapper.selectById(1088248166370832385l);
        System.err.println(user);

    }

    @Test
    public void selectByIds() {
        List<Long> list = Arrays.asList(1088248166370832385l, 1094590409767661570l);
        List<User> users = userMapper.selectBatchIds(list);
        users.forEach(user -> System.err.println(user.getName()));
    }

    @Test
    public void selectByMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "王天风");
        List<User> users = userMapper.selectByMap(map);
        users.forEach(user -> System.err.println(user.getName()));
    }

    /**
     * 查询名字中含有“雨”,年龄小于40
     */
    @Test
    public void selectByWrapper() {

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("name", "雨").lt("age", 40);
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(user -> System.err.println(user.getName()));
    }

    /**
     * 查询名字中含有“雨”,年龄小于40大于20 email not null
     */
    @Test
    public void selectByWrapper01() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("name", "雨")
//                .lt("age", 40)
//                .gt("age", 20)
                .between("age", 20, 40) //[1,3]
                .isNotNull("email");
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(user -> System.err.println(user.getName()));
    }

    /**
     * 名字为王姓或者年龄大于等于25，按照年龄降序排列，年龄相同按照id升序排列
     * name like '王%' or age>=40 order by age desc,id asc
     */
    @Test
    public void selectByWrapper02() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.likeRight("name", "王")
                .or().ge("age", 25)
                .orderByDesc("age")
                .orderByAsc("id");
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(user -> System.err.println(user.getName()));
    }


    /**
     * 创建日期为2019年2月14日 并且直属上级为名字为王姓
     * date_format(create_time,'%Y-%m-%d') and manager_id in(select id from user where name like '王%')
     */
    @Test
    public void selectByWrapper03() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.apply("date_format(create_time,'%Y-%m-%d')={0}", "2019-02-14")
                .inSql("manager_id", "select id from user where name like '王%'");

        List<User> users = userMapper.selectList(wrapper);
        users.forEach(user -> System.err.println(user.getName()));
    }

    /**
     * 名字为王姓并且（年龄小于40或者邮箱不为空）
     * name like '王%' and(age<40 or email is not null)
     */
    @Test
    public void selectByWrapper04() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.likeRight("name", "王")
                .and(qw -> qw.lt("age", 40).or().isNotNull("email"));

        List<User> users = userMapper.selectList(wrapper);
        users.forEach(user -> System.err.println(user.getName()));
    }

    /**
     * 名字为王姓或者（年龄小于40并且年龄大于20并且邮箱不为空）
     * name like '王%' or(age<40 and age >20 and email is not null)
     */
    @Test
    public void selectByWrapper05() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.likeRight("name", "王")
                .or(qw -> qw.lt("age", 40).gt("age", 20).isNotNull("email"));

        List<User> users = userMapper.selectList(wrapper);
        users.forEach(user -> System.err.println(user.getName()));
    }

    /**
     * (年龄小于40或者邮箱不为空) 并且名字为王姓
     * （age<40 or email is not null）and name like '王%'
     * and 优先级大于 or  所有加括号和不加括号结果不同
     */
    @Test
    public void selectByWrapper06() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
//        wrapper.nested(qw -> qw.lt("age", 40).or().isNotNull("email"))
//                .likeRight("name", "王");
        wrapper.likeRight("name", "王")
                .and(qw -> qw.lt("age", 40).or().isNotNull("email"))
        ;

        List<User> users = userMapper.selectList(wrapper);
        users.forEach(user -> System.err.println(user.getName()));
    }

    /**
     * 年龄为 30、31、34、35
     * age in(30、31、34、35)
     */
    @Test
    public void selectByWrapper07() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.in("age", Arrays.asList(30, 31, 34, 35));
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(user -> System.err.println(user.getName()));
    }

    /**
     * 获取年龄为 40、31、34、35的其中的一条数据
     * age in(40、31、34、35)
     */
    @Test
    public void selectByWrapper08() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.in("age", Arrays.asList(40, 31, 34, 35)).last("limit 1");
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(user -> System.err.println(user.getName()));
    }


    /**
     * 查询名字中含有“雨”,年龄小于40
     * 选择指定的列输出
     */
    @Test
    public void selectByWrapperSupper() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("id", "name")
                .like("name", "雨")
                .lt("age", 40);
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(user -> System.err.println(user));
    }

    /**
     * 查询名字中含有“雨”,年龄小于40
     * 不输出指定的列
     */
    @Test
    public void selectByWrapperSupper01() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select(User.class, info -> !info.getColumn().equals("create_time") && !info.getColumn().equals("manager_id"))
                .like("name", "雨")
                .lt("age", 40);
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(user -> System.err.println(user));
    }

    /**
     * 多条件动态查询
     */
    @Test
    public void testCondition() {
        condition(null, "l");
    }

    public void condition(String name, String email) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(name), "name", name)
                .like(StringUtils.isNotEmpty(email), "email", email);

        List<User> users = userMapper.selectList(wrapper);
        users.forEach(user -> System.err.println(user.getName()));
    }

}