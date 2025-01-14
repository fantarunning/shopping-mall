package com.jk.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jk.entity.Account;
import com.jk.entity.Admin;
import com.jk.entity.User;
import com.jk.service.UserService;
import com.jk.utils.ResultUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 管理员表(User)表控制层
 *
 * @since 2024-12-23 00:24:52
 */
@RestController
@RequestMapping("user")
public class UserController  {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;


    //    删除
    @GetMapping("/del")
    public ResultUtil del(int id){
        try{
            boolean b = userService.removeById(id);
            return new ResultUtil(200,"删除成功！");
        }catch (Exception e){
            return new ResultUtil(500,"删除失败！");
        }
    }


    //    批量删除
    @PostMapping("/delBatch")
    public ResultUtil delBatch(@RequestBody List<Integer> ids){
        try {
            boolean b = userService.removeByIds(ids);
            return new ResultUtil(200,"批量删除成功！");
        }catch (Exception e){
            return new ResultUtil(500,"批量删除失败！");
        }

    }

    //    分页查询
    @GetMapping("/page")
    public ResultUtil list( Integer page,Integer limit,
                            String UserName){
        System.out.println(UserName+","+","+page+","+limit);
//        传入分页属性
        Page<User> objectPage = new Page<>(page, limit);
//        查询条件
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(UserName),"username",UserName)
                .orderByDesc("id");
//        分页查询
        Page<User> Page = userService.page(objectPage, wrapper);
//        获取数据总条数
        long total = Page.getTotal();
//        数据
        List<User> List = Page.getRecords();

        return new ResultUtil(200,total,List);
    }

    //    提交数据-->新增、修改
    @PostMapping("/save")
    public ResultUtil add(@RequestBody User user){
        if (user.getId()==null){//新增
            try {
                userService.save(user);
                return new ResultUtil(200,"添加成功！");
            }catch (Exception e){
                return new ResultUtil(500,"添加失败！");
            }
        }else {
            try {
                userService.updateById(user);
                return new ResultUtil(200,"修改成功！");
            }catch (Exception e){
                return new ResultUtil(500,"修改失败！");
            }
        }
    }


}

