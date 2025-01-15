package com.jk.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jk.mapper.UserDao;
import com.jk.entity.User;
import com.jk.service.UserService;

import org.springframework.stereotype.Service;


/**
 * 管理员表(User)表服务实现类
 *
 * @author makejava
 * @since 2024-12-23 00:24:52
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {


}

