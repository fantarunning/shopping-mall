package com.jk.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jk.entity.Admin;
import com.jk.service.AdminService;
import com.jk.utils.ResultUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * 管理员表(Admin)表控制层
 *
 * @author makejava
 * @since 2024-12-02 10:41:22
 */
@RestController
@RequestMapping("admin")
public class AdminController {
    /**
     * 服务对象
     */
    @Resource
    private AdminService adminService;

    //    删除
    @GetMapping("/del")
    public ResultUtil del(int id){
        try{
            boolean b = adminService.removeById(id);
            return new ResultUtil(200,"删除成功！");
        }catch (Exception e){
            return new ResultUtil(500,"删除失败！");
        }
    }



    //    批量删除
    @PostMapping("/delBatch")
    public ResultUtil delBatch(@RequestBody List<Integer> ids){
        try {
            boolean b = adminService.removeByIds(ids);
            return new ResultUtil(200,"批量删除成功！");
        }catch (Exception e){
            return new ResultUtil(500,"批量删除失败！");
        }

    }

    //    分页查询
    @GetMapping("/page")
    public ResultUtil list( Integer page,Integer limit,
                            String adminName){
        System.out.println(adminName+","+","+page+","+limit);
//        传入分页属性
        Page<Admin> objectPage = new Page<>(page, limit);
//        查询条件
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(adminName),"name",adminName)
                .orderByDesc("id");
//        分页查询
        Page<Admin> adminPage = adminService.page(objectPage, wrapper);
//        获取数据总条数
        long total = adminPage.getTotal();
//        数据
        List<Admin> adminList = adminPage.getRecords();

        return new ResultUtil(200,total,adminList);
    }

    //    提交数据-->新增、修改
    @PostMapping("/save")
    public ResultUtil add(@RequestBody Admin admin){
        if (admin.getId()==null){//新增
            try {
                adminService.save(admin);
                return new ResultUtil(200,"添加成功！");
            }catch (Exception e){
                return new ResultUtil(500,"添加失败！");
            }
        }else {
            try {
                adminService.updateById(admin);
                return new ResultUtil(200,"修改成功！");
            }catch (Exception e){
                return new ResultUtil(500,"修改失败！");
            }
        }
    }

}

