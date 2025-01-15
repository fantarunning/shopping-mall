package com.jk.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jk.mapper.CartitemDao;
import com.jk.entity.Cartitem;
import com.jk.service.CartitemService;
import org.springframework.stereotype.Service;

/**
 * (Cartitem)表服务实现类
 *
 * @author
 * @since 2024-12-30 21:38:43
 */
@Service("cartitemService")
public class CartitemServiceImpl extends ServiceImpl<CartitemDao, Cartitem> implements CartitemService {

}

