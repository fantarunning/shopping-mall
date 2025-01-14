package com.jk.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jk.entity.User;
import org.apache.ibatis.annotations.Mapper;


/**
 * 管理员表(User)表数据库访问层
 *
 * @author makejava
 * @since 2024-12-23 00:24:52
 */
@Mapper
public interface UserDao extends BaseMapper<User> {

}

