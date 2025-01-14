package com.jk.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jk.entity.Cartitem;
import org.apache.ibatis.annotations.Mapper;

/**
 * (Cartitem)表数据库访问层
 *
 * @author
 * @since 2024-12-30 21:38:43
 */
@Mapper
public interface CartitemDao extends BaseMapper<Cartitem> {

}

