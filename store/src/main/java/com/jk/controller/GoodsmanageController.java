package com.jk.controller;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jk.entity.Goodsmanage;
import com.jk.service.GoodsmanageService;
import com.jk.utils.ResultUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import static com.jk.utils.ResultUtil.ok;

/**
 * (Goodsmanage)表控制层
 *
 * @since 2024-12-18 16:27:04
 */
@RestController
@RequestMapping("goodsmanage")
public class GoodsmanageController {
    /**
     * 服务对象
     */
    @Resource
    private GoodsmanageService goodsmanageService;

//用户界面商品展示
    @GetMapping("/goods")
    public ResultUtil getGoodsList(@RequestParam(required = false) String goodsName) {
        // 构建查询条件
        QueryWrapper<Goodsmanage> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(goodsName)) {
            wrapper.like("goodsname", goodsName);
        }
        wrapper.orderByDesc("id");
        // 执行查询
        List<Goodsmanage> goodsList = goodsmanageService.list(wrapper);
        return new ResultUtil(0, "查询成功", goodsList);
    }



    //    删除
    @GetMapping("/del")
    public ResultUtil del(int id){
        try {
            goodsmanageService.removeById(id);
            return new  ResultUtil(200,"删除成功");
        }catch (Exception e){
            return ResultUtil.error(e.getMessage());
        }
    }


    //    批量删除
    @PostMapping("/delBatch")
    public ResultUtil delBatch(@RequestBody List<Integer> ids){
        try {
            goodsmanageService.removeByIds(ids);
            return new  ResultUtil(200,"删除成功");
        }catch (Exception e){
            return ResultUtil.error(e.getMessage());
        }


    }

    //    分页查询
    @GetMapping("/page")
    public ResultUtil list( Integer page,Integer limit, String goodsName){
        System.out.println(goodsName+","+page+","+limit);
//        传入分页属性
        Page<Goodsmanage> objectPage = new Page<>(page, limit);
//        查询条件
        QueryWrapper<Goodsmanage> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(goodsName),"goodsname",goodsName)
                .orderByDesc("id");
//        分页查询
        Page<Goodsmanage> goodsPage = goodsmanageService.page(objectPage, wrapper);
//        获取数据总条数
        long total = goodsPage.getTotal();
//        数据
        List<Goodsmanage> goodsList = goodsPage.getRecords();
        return new ResultUtil(200,total,goodsList);
    }

    //    提交数据-->新增、修改
    @PostMapping("/save")
    public ResultUtil add(@RequestBody Goodsmanage goodsmanage){
        if (goodsmanage.getId()==null){//新增
            try {
                goodsmanageService.save(goodsmanage);
                return new  ResultUtil(200,"新增成功");
            }catch (Exception e){
                return ResultUtil.error(e.getMessage());
            }
        }else {
            try {
                goodsmanageService.updateById(goodsmanage);
                return new  ResultUtil(200,"修改成功");
            }catch (Exception e){
                return ResultUtil.error(e.getMessage());
            }

        }
    }



    /**
     * 导出接口
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<Goodsmanage> list = goodsmanageService.list();
        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);
        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("商品信息表", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
    }

    /**
     * excel 导入
     * @param file
     * @throws Exception
     */
    @PostMapping("/import")
    public ResultUtil imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 方式1：(推荐) 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
        List<Goodsmanage> list = reader.readAll(Goodsmanage.class);
        System.out.println(list);
        goodsmanageService.saveBatch(list);
        return ok("上传成功！");
    }

}

