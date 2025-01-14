package com.jk.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jk.dao.UsermanageDao;
import com.jk.entity.Usermanage;
import com.jk.service.UsermanageService;
import org.springframework.stereotype.Service;

/**
 * 用户信息表(Usermanage)表服务实现类
 *
 * @since 2024-12-18 16:24:00
 */
@Service("usermanageService")
public class UsermanageServiceImpl extends ServiceImpl<UsermanageDao, Usermanage> implements UsermanageService {

}

