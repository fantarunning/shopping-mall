package com.jk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jk.entity.Admin;
import org.apache.ibatis.annotations.Mapper;


/**
 * 管理员表(Admin)表数据库访问层
 *
 * @author makejava
 * @since 2024-12-02 10:41:22
 */
@Mapper
public interface AdminDao extends BaseMapper<Admin> {

}

