package com.jk.controller;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jk.entity.Usermanage;
import com.jk.service.UsermanageService;
import com.jk.utils.ResultUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 用户信息表(Usermanage)表控制层
 *
 * @since 2024-12-18 16:23:55
 */
@RestController
@RequestMapping("usermanage")
public class UsermanageController {
    /**
     * 服务对象
     */
    @Resource
    private UsermanageService usermanageService;

    @GetMapping("/list")
    public List<Usermanage> list(){
        return usermanageService.list();
    }

    //    分页查询
    @GetMapping("/page")
    public ResultUtil list(Integer page, Integer limit,
                           String UserName,String Role){
        System.out.println(UserName+","+Role+","+page+","+limit);
//        传入分页属性
        Page<Usermanage> objectPage = new Page<>(page, limit);
//        查询条件
        QueryWrapper<Usermanage> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(UserName),"name",UserName)
                .like(StringUtils.isNotBlank(Role),"role",Role)
                .orderByDesc("id");
//        分页查询
        Page<Usermanage> Page = usermanageService.page(objectPage, wrapper);
//        获取数据总条数
        long total = Page.getTotal();
//        数据
        List<Usermanage> usersList = Page.getRecords();

        return new ResultUtil(200,total,usersList);
    }

    //    提交数据-->新增、修改
    @PostMapping("/save")
    public ResultUtil add(@RequestBody Usermanage Usermanage){
        if (Usermanage.getId()==null){//新增
            try {
                Date date = new Date();
                Usermanage.setCreatetime(date);
                usermanageService.save(Usermanage);
                return new  ResultUtil(200,"新增成功");
            }catch (Exception e){
                return ResultUtil.error(e.getMessage());
            }
        }else {
            try {
                usermanageService.updateById(Usermanage);
                return new  ResultUtil(200,"修改成功");
            }catch (Exception e){
                return ResultUtil.error(e.getMessage());
            }

        }
    }
    //    删除
    @GetMapping("/del")
    public String del(int id){
        boolean b = usermanageService.removeById(id);
        if (b){
            return "1";
        }else {
            return "0";
        }
    }
    //    批量删除
    @PostMapping("/delBatch")
    public ResultUtil delBatch(@RequestBody List<Integer> ids){

        usermanageService.removeByIds(ids);
        return new ResultUtil(200,"批量删除成功！");
    }

    @PostMapping("/upload")
    public String upload(MultipartFile file) {
        System.out.println(123);
        //原始文件名
        String originalFilename = file.getOriginalFilename();
        //获取原文件的类型
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //使用UUID重新生成文件名，防止文件名称重复造成文件覆盖
        String fileName = UUID.randomUUID() + suffix;
        //创建一个目录对象，用于存储上传文件
        File dir = new File("E:\\images\\");
        //判断当前目录是否存在：目录不存在，需要创建
        if (!dir.exists()) dir.mkdirs();
        try {
            //保存文件到指定目录
            file.transferTo(new File("E:\\images\\" + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            //输入流，通过输入流读取文件内容
            bis = new BufferedInputStream(new FileInputStream("E:\\images\\" + name));
            //输出流，通过输出流将文件写回浏览器
            bos = new BufferedOutputStream(response.getOutputStream());
            // 设置相应类型
            response.setContentType("image/png");
            int i = 0;
            while ((i = bis.read()) != -1) {
                bos.write(i);
            }
            bos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) bos.close();
                if (bis != null) bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 导出接口
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<Usermanage> list = usermanageService.list();
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
        String fileName = URLEncoder.encode("商城用户表", "UTF-8");
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
        List<Usermanage> list = reader.readAll(Usermanage.class);

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
        usermanageService.saveBatch(list);
        return ResultUtil.ok("上传成功！");
    }
}

