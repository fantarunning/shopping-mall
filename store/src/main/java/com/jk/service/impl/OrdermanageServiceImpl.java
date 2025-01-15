package com.jk.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jk.mapper.OrdermanageDao;
import com.jk.entity.Ordermanage;
import com.jk.service.OrdermanageService;
import org.springframework.stereotype.Service;

/**
 * (Ordermanage)表服务实现类
 *
 * @since 2024-12-18 16:24:29
 */
@Service("ordermanageService")
public class OrdermanageServiceImpl extends ServiceImpl<OrdermanageDao, Ordermanage> implements OrdermanageService {

}

