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

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.*;


/**
 * author:szjz
 * date:2019/7/25
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperInsertQueryTest {


    @Autowired
    private UserMapper userMapper;


    /**
     * 插入保存对象
     */
    @Test
    public void insert() {
        User user = new User();
        user.setAge(32);
        user.setCreateTime(LocalDateTime.now());
        user.setManagerId(1088248166370832385l);

        user.setName("小明");
        int rows = userMapper.insert(user);
        System.out.println(rows);
    }


    /**
     * 查询所有
     */
    @Test
    public void select() {
        List<User> users = userMapper.selectList(null);
        Assert.notNull(users, "没有数据");
        System.err.println(users);
    }

    /**
     * 指定ID查询
     */
    @Test
    public void selectById() {
        User user = userMapper.selectById(1088248166370832385l);
        System.err.println(user);

    }

    /**
     * 通过id集合查询
     */
    @Test
    public void selectByIds() {
        List<Long> list = Arrays.asList(1088248166370832385l, 1094590409767661570l);
        List<User> users = userMapper.selectBatchIds(list);
        users.forEach(user -> System.err.println(user.getName()));
    }

    /**
     * 通过map 指定对象查询
     */
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


    @Test
    public void selectByWrapperAllEq() {

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        Map<String, Object> map = new HashMap<>();
        map.put("name", "王天风");
        map.put("age", null);
        //第二个参数默认为true  SELECT id,name,age,email,manager_id,create_time FROM user WHERE name = ? AND age IS NULL
//        wrapper.allEq(map);
        //第二个参数为false 忽略参数为key=null的条件 SELECT id,name,age,email,manager_id,create_time FROM user WHERE name = ?
//        wrapper.allEq(map,false);

        //过滤掉name字段 并忽略age=null这个条件   SELECT id,name,age,email,manager_id,create_time FROM user
        wrapper.allEq((k, v) -> !k.equals("name"), map, false);

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
     * return users 没有选择的列显示为null
     * <p>
     * SELECT id,name FROM user WHERE name LIKE ? AND age < ?
     * User(id=1094590409767661570, name=张雨琪, age=null, email=null, managerId=null, createTime=null, remark=null)
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
     * 选择指定的列输出
     * return Maps 没有选择的列不再结果中出现
     * <p>
     * SELECT id,name FROM user WHERE name LIKE ? AND age < ?
     * {name=张雨琪, id=1094590409767661570}
     */
    @Test
    public void selectByWrapperMaps() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("id", "name")
                .like("name", "雨")
                .lt("age", 40);
        List<Map<String, Object>> users = userMapper.selectMaps(wrapper);
        users.forEach(user -> System.err.println(user));
    }

    /**
     * 按照直属上级分组，查询每组的平均年龄、最大年龄、最小年龄。 并且只取年龄总和小于100的组
     * <p>
     * SELECT avg(age) avg_age,min(age) min_age,max(age) max_age,sum(age) sum_age
     * FROM user
     * WHERE 1=1 GROUP BY manager_id HAVING sum(age)<?
     * <p>
     * {max_age=40, avg_age=40.0000, sum_age=40, min_age=40}
     * {max_age=25, avg_age=25.0000, sum_age=25, min_age=25}
     */
    @Test
    public void selectByWrapperMaps01() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("avg(age) avg_age", "min(age) min_age", "max(age) max_age", "sum(age) sum_age")
                .groupBy("manager_id")
                .having("sum(age)<{0}", 100);

        List<Map<String, Object>> users = userMapper.selectMaps(wrapper);
        users.forEach(user -> System.err.println(user));
    }


    /**
     * User(id=1094590409767661570, name=张雨琪, age=null, email=null, managerId=null, createTime=null, remark=null)
     */
    @Test
    public void selectByWrapperObjs() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("id", "name")
                .like("name", "雨")
                .lt("age", 40)
        ;
        List<Object> users = userMapper.selectObjs(wrapper);
        users.forEach(System.err::println);
    }

    /**
     * 查询总数
     * SELECT COUNT(1) FROM user WHERE name LIKE ?
     */
    @Test
    public void selectByWrapperCount() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("name", "雨")
//                .lt("age", 40)
        ;
        Integer count = userMapper.selectCount(wrapper);
        System.err.println("查询总数：" + count);
    }


    @Test
    public void selectByWrapperOne() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("name", "雨")
//                .lt("age", 40)
        ;
        //只允许返回一个结果返回两个就报错 可用于数据查重 有异常则有重复数据
        User user = null;
        try {
            user = userMapper.selectOne(wrapper);
        } catch (Exception e) {
            System.out.println("数据重复,该数据已存在");
        }
        System.err.println(user);
    }

    /**
     * 查询名字中含有“雨”,年龄小于40
     * 不输出指定的列
     * <p>
     * SELECT id,name,age,email FROM user WHERE name LIKE ? AND age < ?
     * User(id=1094590409767661570, name=张雨琪, age=31, email=zjq@baomidou.com, managerId=null, createTime=null, remark=null)
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


    /**
     * 通过实体设置对应的参数，最终将实体参数拼接为where条件 并将实体作为参数传进wrapper
     * 名字中含有“雨”,并且年龄大于等于40
     */
    @Test
    public void selectByWrapperEntity() {

        User user1 = new User();
        user1.setName("雨");
        user1.setAge(40);

        QueryWrapper<User> wrapper = new QueryWrapper<>(user1);
//        wrapper.like("name", "雨").lt("age", 40);

        List<User> users = userMapper.selectList(wrapper);
        users.forEach(user -> System.err.println(user.getName()));
    }


    /**
     * Lambda  防止列名输入出错
     */
    @Test
    public void selectLambda() {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new QueryWrapper<User>().lambda();
        lambdaQueryWrapper
                .like(User::getName, "雨")
                .lt(User::getAge, 40);
        List<User> users = userMapper.selectList(lambdaQueryWrapper);
        users.forEach(user -> System.err.println(user));
    }

    /**
     * 名字为王姓且(年龄小于40或者邮箱不为空)
     * name like '王%' and(age<40 or email is not null)
     */
    @Test
    public void selectLambda01() {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new QueryWrapper<User>().lambda();
        lambdaQueryWrapper
                .likeRight(User::getName, "王")
                .and(lqw -> lqw.lt(User::getAge, 40).or().isNotNull(User::getEmail))
        ;
        List<User> users = userMapper.selectList(lambdaQueryWrapper);
        users.forEach(user -> System.err.println(user));
    }


    /**
     * 3.0.7 才开始有这个对象 利用了建造者模式 实现链式调用
     */
    @Test
    public void selectLambda02() {
        List<User> users = new LambdaQueryChainWrapper<User>(userMapper)
                .like(User::getName, "雨")
                .ge(User::getAge, 20)
                .list();

        users.forEach(user -> System.err.println(user));
    }


    /**
     * 分页查询
     * 使用分页之前必须先配置分页拦截器 com.szjz.mybatisplusstudy.configuration.MybatisPlusConfig
     */
    @Test
    public void selectPage() {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new QueryWrapper<User>().lambda();
        lambdaQueryWrapper.ge(User::getAge, 26);

        //第三个参数为是否查询总页数
        Page<User> userIPage = new Page<>(1, 2, true);

        IPage<User> iPage = userMapper.selectPage(userIPage, lambdaQueryWrapper);

        System.err.println("总页数：" + iPage.getPages());
        System.err.println("总记录数：" + iPage.getTotal());
        List<User> records = iPage.getRecords();
        records.forEach(System.err::println);
    }
}