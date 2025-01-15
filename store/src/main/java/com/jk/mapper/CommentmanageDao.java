package com.jk.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jk.entity.Commentmanage;
import org.apache.ibatis.annotations.Mapper;

/**
 * 留言表(Commentmanage)表数据库访问层
 *
 * @since 2024-12-19 15:54:09
 */
@Mapper
public interface CommentmanageDao extends BaseMapper<Commentmanage> {

}

