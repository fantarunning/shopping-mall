package com.jk.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jk.entity.Account;
import com.jk.entity.Admin;
import com.jk.entity.User;
import com.jk.service.AdminService;
import com.jk.service.UserService;
import com.jk.utils.ResultUtil;
import com.jk.utils.TokenUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping
public class WebController {

    @Resource
    private AdminService adminService;
    @Resource
    private UserService userService;
//    登录
    @PostMapping("/login")
    public ResultUtil login(@RequestBody Account account) {
        System.out.println(account);
//验证是否是管理员
        if ("ADMIN".equals(account.getRole())) {
            //查询条件
            QueryWrapper<Admin> wrapper = new QueryWrapper<>();
            wrapper.eq("username", account.getUsername());
            Admin admin = adminService.getOne(wrapper);
            System.out.println("111："+admin);
            if (admin == null) {
                return ResultUtil.error("账号不存在！");
            }
            if (!admin.getPassword().equals(account.getPassword())) {
                return ResultUtil.error("密码错误！");
            }
//生成token
            String token = TokenUtils.createToken(admin.getId()
                    + "-" + admin.getRole(), admin.getPassword());
            admin.setToken(token);
            return new ResultUtil(200,"登录成功！",admin);
//验证是否是用户
        }else if ("USER".equals(account.getRole())){
            //查询条件
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("username", account.getUsername());
            User user = userService.getOne(wrapper);
            System.out.println("111："+user);
            if (user == null) {
                return ResultUtil.error("账号不存在！");
            }
            if (!user.getPassword().equals(account.getPassword())) {
                return ResultUtil.error("密码错误！");
            }
//            生成token
            String token = TokenUtils.createToken(user.getId() + "-" + user.getRole(), user.getPassword());
            user.setToken(token);
            return new ResultUtil(200,"登录成功！",user);
        }

        return new ResultUtil(400,"角色不正确！");
    }
//注册
    @PostMapping("/register")
    public ResultUtil registerUser(@RequestBody Account account) {
        try {
            // 检查用户是否已存在
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("username", account.getUsername());
            if (userService.getOne(wrapper)!=null) {
                return ResultUtil.error("该账户已存在，请重新创建账户！");
            }
            User users= new User();
            users.setUsername(account.getUsername());
            users.setPassword(account.getPassword());
            users.setName(account.getUsername());
            users.setRole("USER");
            // 保存新用户
            userService.save(users);
            return new ResultUtil(200, "注册成功！");
        } catch (Exception e) {
            return ResultUtil.error(e.getMessage());
        }


    }

//    修改密码
    @PostMapping("/updatePassword")
    public ResultUtil updatePassword(@RequestBody Account account) {
        System.out.println(account);
        if ("ADMIN".equals(account.getRole())) {
            //查询条件
            QueryWrapper<Admin> wrapper = new QueryWrapper<>();
            wrapper.eq("username", account.getUsername());
            Admin admin = adminService.getOne(wrapper);
            if (admin == null) {
                return ResultUtil.error("账号不存在！");
            }
            if (!admin.getPassword().equals(account.getPassword())) {
                return ResultUtil.error("原始密码错误！");
            }
//            修改密码
            admin.setPassword(account.getNewPassword());
            adminService.updateById(admin);
            return new ResultUtil(200,"修改成功！");
        }else if  ("USER".equals(account.getRole())) {
            //查询条件
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("username", account.getUsername());
            User user = userService.getOne(wrapper);
            if (user == null) {
                return ResultUtil.error("账号不存在！");
            }
            if (!user.getPassword().equals(account.getPassword())) {
                return ResultUtil.error("原始密码错误！");
            }
//            修改密码
            user.setPassword(account.getNewPassword());
            userService.updateById(user);
            return new ResultUtil(200,"修改成功！");
        }
        return new ResultUtil(500,"角色不匹配！");
    }
}
