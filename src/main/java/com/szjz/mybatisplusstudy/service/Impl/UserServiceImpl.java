package com.szjz.mybatisplusstudy.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.szjz.mybatisplusstudy.dao.UserMapper;
import com.szjz.mybatisplusstudy.entity.User;
import com.szjz.mybatisplusstudy.service.UserService;
import org.springframework.stereotype.Service;

/**
 * author:szjz
 * date:2019/7/26
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
