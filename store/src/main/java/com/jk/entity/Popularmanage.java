package com.jk.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;


/**
 * (Popularmanage)表实体类
 *

 * @since 2024-12-19 15:49:27
 */
@SuppressWarnings("serial")
public class Popularmanage extends Model<Popularmanage> {
//id
@TableId(type = IdType.AUTO)
    private Integer id;
//科普主题
    private String title;
//内容
    private String content;
//图片或视频
    private String url;
//科普类型
    private String type;
//发布人
    private String author;
//发布时间
    private String time;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}

