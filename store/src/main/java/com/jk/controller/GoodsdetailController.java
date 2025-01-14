package com.jk.controller;

import com.jk.entity.Goodsmanage;
import com.jk.service.GoodsmanageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/goods")
public class GoodsdetailController {
    @Autowired
    private GoodsmanageService goodsmanageService;


    @GetMapping("/{id}")
    public Goodsmanage getGoodsById(@PathVariable Long id) {
        return goodsmanageService.getById(id);
    }

    @PostMapping("/cart")
    public void addToCart(@RequestBody CartRequest cartRequest) {
        // 处理加入购物车的逻辑
//        goodsmanageService.addToCart(cartRequest);
    }

    @PostMapping("/order")
    public void createOrder(@RequestBody OrderRequest orderRequest) {
        // 处理创建订单的逻辑
//        goodsmanageService.createOrder(orderRequest);
    }

    // DTOs for requests
    public static class CartRequest {
        private Long goodsId;
        private int count;
        private String standard;

        // Getters and Setters
    }

    public static class OrderRequest {
        private Long goodsId;
        private int count;
        private String standard;

        // Getters and Setters
    }
}
