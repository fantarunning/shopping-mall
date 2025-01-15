package com.jk.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jk.mapper.CommentmanageDao;
import com.jk.entity.Commentmanage;
import com.jk.service.CommentmanageService;
import org.springframework.stereotype.Service;

/**
 * 留言表(Commentmanage)表服务实现类
 *
 * @author makejava
 * @since 2024-12-19 15:54:09
 */
@Service("commentmanageService")
public class CommentmanageServiceImpl extends ServiceImpl<CommentmanageDao, Commentmanage> implements CommentmanageService {

}

