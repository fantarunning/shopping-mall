package com.jk.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jk.entity.Popularmanage;
import com.jk.service.PopularmanageService;
import com.jk.utils.ResultUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * (Popularmanage)表控制层
 *
 * @since 2024-12-19 15:54:51
 */
@RestController
@RequestMapping("popularmanage")
public class PopularmanageController {
    /**
     * 服务对象
     */
    @Resource
    private PopularmanageService popularmanageService;

    //    删除
    @GetMapping("/del")
    public ResultUtil del(int id){
        try {
            popularmanageService.removeById(id);
            return new  ResultUtil(200,"删除成功");
        }catch (Exception e){
            return ResultUtil.error(e.getMessage());
        }
    }


    //    批量删除
    @PostMapping("/delBatch")
    public ResultUtil delBatch(@RequestBody List<Integer> ids){
        try {
            popularmanageService.removeByIds(ids);
            return new  ResultUtil(200,"删除成功");
        }catch (Exception e){
            return ResultUtil.error(e.getMessage());
        }


    }

    //    分页查询
    @GetMapping("/page")
    public ResultUtil list( Integer page,Integer limit, String popularTitle){
        System.out.println(popularTitle+","+page+","+limit);
//        传入分页属性
        Page<Popularmanage> objectPage = new Page<>(page, limit);
//        查询条件
        QueryWrapper<Popularmanage> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(popularTitle),"title",popularTitle)
                .orderByDesc("id");
//        分页查询
        Page<Popularmanage> popularPage = popularmanageService.page(objectPage, wrapper);
//        获取数据总条数
        long total = popularPage.getTotal();
//        数据
        List<Popularmanage> popularList = popularPage.getRecords();

        return new ResultUtil(200,total,popularList);
    }

    //    提交数据-->新增、修改
    @PostMapping("/save")
    public ResultUtil add(@RequestBody Popularmanage popularmanage){
        if (popularmanage.getId()==null){//新增
            try {
//                Date date=new Date();
//                Popularmanage newPopularmanage=new Popularmanage();
//                newPopularmanage.setTime(date);
//                popularmanageService.save(newPopularmanage);
                popularmanage.setTime(DateUtil.now());
                popularmanageService.save(popularmanage);
                return new  ResultUtil(200,"新增成功");
            }catch (Exception e){
                return ResultUtil.error(e.getMessage());
            }
        }else {
            try {
                popularmanageService.updateById(popularmanage);
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
        List<Popularmanage> list = popularmanageService.list();
        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        /*writer.addHeaderAlias("name", "商品名称");
        writer.addHeaderAlias("price", "单价");
        writer.addHeaderAlias("intro", "简介");
        writer.addHeaderAlias("amount", "库存");
        writer.addHeaderAlias("picurl", "商品图片");
        writer.addHeaderAlias("createtime", "上架时间");
        writer.addHeaderAlias("goodstype", "商品类别");
        writer.addHeaderAlias("salenum", "销量");*/

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("商城科普表", "UTF-8");
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
        List<Popularmanage> list = reader.readAll(Popularmanage.class);

        // 方式2：忽略表头的中文，直接读取表的内容
        /*List<List<Object>> list = reader.read(1);
        List<User> users = CollUtil.newArrayList();
        for (List<Object> row : list) {
            User user = new User();
            user.setUsername(row.get(0).toString());
            user.setPassword(row.get(1).toString());
            user.setNickname(row.get(2).toString());
            user.setEmail(row.get(3).toString());
            user.setPhone(row.get(4).toString());
            user.setAddress(row.get(5).toString());
            user.setAvatarUrl(row.get(6).toString());
            users.add(user);
        }*/
        System.out.println(list);
        popularmanageService.saveBatch(list);
        return ResultUtil.ok("上传成功！");
    }
}

