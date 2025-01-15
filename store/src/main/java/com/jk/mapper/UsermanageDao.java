package com.jk.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jk.entity.Usermanage;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户信息表(Usermanage)表数据库访问层
 *
 * @since 2024-12-18 16:23:58
 */
@Mapper
public interface UsermanageDao extends BaseMapper<Usermanage> {

}

