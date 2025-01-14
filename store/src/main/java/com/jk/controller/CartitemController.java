package com.jk.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jk.entity.Cartitem;
import com.jk.service.CartitemService;
import com.jk.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Cartitem)表控制层
 *
 * @author
 * @since 2024-12-30 21:38:43
 */
@RestController
@RequestMapping("cartitem")
public class CartitemController{
    /**
     * 服务对象
     */
    @Resource
    private CartitemService cartitemService;


    // 根据购物车 ID 查询
    @GetMapping("/{cartId}")
    public Result selectById(@PathVariable Integer cartId) {
        Cartitem cart = cartitemService.getById(cartId);
        return Result.success(cart);
    }

    // 添加购物车项
    @PostMapping
    public Result addCart(@RequestBody Cartitem cart) {
        cartitemService.save(cart);
        return Result.success();
    }

    // 更新购物车项
    @PutMapping
    public Result updateCart(@RequestBody Cartitem cart) {
        cartitemService.updateById(cart);
        return Result.success();
    }

    // 删除购物车项
    @DeleteMapping("/{cartId}")
    public Result deleteCart(@PathVariable Integer cartId) {
        cartitemService.removeById(cartId);
        return Result.success();
    }

    // 根据用户 ID 查询购物车列表
    @GetMapping("/user/{userId}")
    public Result selectByUserId(@PathVariable Integer userId) {
        List<Cartitem> cartList = cartitemService.list(new QueryWrapper<Cartitem>().eq("user_id", userId));
        return Result.success(cartList);
    }


}

