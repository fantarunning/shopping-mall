package com.jk.controller;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jk.entity.Commentmanage;
import com.jk.service.CommentmanageService;
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

/**
 * 留言表(Commentmanage)表控制层
 *
 * @since 2024-12-19 15:54:09
 */
@RestController
@RequestMapping("commentmanage")
public class CommentmanageController {
    /**
     * 服务对象
     */
    @Resource
    private CommentmanageService commentmanageService;

    //    删除
    @GetMapping("/del")
    public ResultUtil del(int id){
        try {
            commentmanageService.removeById(id);
            return new  ResultUtil(200,"删除成功");
        }catch (Exception e){
            return ResultUtil.error(e.getMessage());
        }
    }


    //    批量删除
    @PostMapping("/delBatch")
    public ResultUtil delBatch(@RequestBody List<Integer> ids){
        try {
            commentmanageService.removeByIds(ids);
            return new  ResultUtil(200,"删除成功");
        }catch (Exception e){
            return ResultUtil.error(e.getMessage());
        }


    }

    //    分页查询
    @GetMapping("/page")
    public ResultUtil list( Integer page,Integer limit, String Content){
        System.out.println(Content+","+page+","+limit);
//        传入分页属性
        Page<Commentmanage> objectPage = new Page<>(page, limit);
//        查询条件
        QueryWrapper<Commentmanage> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(Content),"content",Content)
                .orderByDesc("id");
//        分页查询
        Page<Commentmanage> Page = commentmanageService.page(objectPage, wrapper);
//        获取数据总条数
        long total = Page.getTotal();
//        数据
        List<Commentmanage> List = Page.getRecords();

        return new ResultUtil(200,total,List);
    }

    //    提交数据-->新增、修改
    @PostMapping("/save")
    public ResultUtil add(@RequestBody Commentmanage commentmanage){
        if (commentmanage.getId()==null){//新增
            try {
                commentmanageService.save(commentmanage);
                return new  ResultUtil(200,"新增成功");
            }catch (Exception e){
                return ResultUtil.error(e.getMessage());
            }
        }else {
            try {
                commentmanageService.updateById(commentmanage);
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
        List<Commentmanage> list = commentmanageService.list();
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
        String fileName = URLEncoder.encode("商城评论表", "UTF-8");
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
        List<Commentmanage> list = reader.readAll(Commentmanage.class);

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
        commentmanageService.saveBatch(list);
        return ResultUtil.ok("上传成功！");
    }
}

