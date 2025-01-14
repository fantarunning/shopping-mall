package com.jk.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jk.entity.Noticemanage;
import com.jk.service.NoticemanageService;
import com.jk.utils.ResultUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * (Noticemanage)表控制层
 *
 * @since 2024-12-19 15:54:32
 */
@RestController
@RequestMapping("noticemanage")
public class NoticemanageController {
    /**
     * 服务对象
     */
    @Resource
    private NoticemanageService noticemanageService;
    //    删除
    @GetMapping("/del")
    public ResultUtil del(int id){
        try {
            noticemanageService.removeById(id);
            return new  ResultUtil(200,"删除成功");
        }catch (Exception e){
            return ResultUtil.error(e.getMessage());
        }
    }

    //    批量删除
    @PostMapping("/delBatch")
    public ResultUtil delBatch(@RequestBody List<Integer> ids){
        try {
            boolean b = noticemanageService.removeByIds(ids);
            return new  ResultUtil(200,"删除成功");
        }catch (Exception e){
            return ResultUtil.error(e.getMessage());
        }
    }

    //    分页查询
    @GetMapping("/page")
    public ResultUtil list( Integer page,Integer limit, String title){
        System.out.println(title+","+page+","+limit);
//        传入分页属性
        Page<Noticemanage> objectPage = new Page<>(page, limit);
//        查询条件
        QueryWrapper<Noticemanage> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(title),"title",title)
                .orderByDesc("id");
//        分页查询
        Page<Noticemanage> noticePage = noticemanageService.page(objectPage, wrapper);
//        获取数据总条数
        long total = noticePage.getTotal();
//        数据
        List<Noticemanage> noticeList = noticePage.getRecords();

        return new ResultUtil(200,total,noticeList);
    }

    //    提交数据-->新增、修改
    @PostMapping("/save")
    public ResultUtil add(@RequestBody Noticemanage noticemanage){
        if (noticemanage.getId()==null){//新增
            try {
                Date date=new Date();
                noticemanage.setTime(date);
                noticemanageService.save(noticemanage);
                return new  ResultUtil(200,"新增成功");
            }catch (Exception e){
                return ResultUtil.error(e.getMessage());
            }
        }else {
            try {
                noticemanageService.updateById(noticemanage);
                return new  ResultUtil(200,"修改成功");
            }catch (Exception e){
                return ResultUtil.error(e.getMessage());
            }

        }
    }

}

