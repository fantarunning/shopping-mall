package com.jk.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * (Noticemanage)表实体类
 *
 * @since 2024-12-19 15:48:53
 */
@SuppressWarnings("serial")
public class Noticemanage extends Model<Noticemanage> {
//id
@TableId(type = IdType.AUTO)
    private Integer id;
//公告标题
    private String title;
//公告内容
    private String content;
//发布时间
    private Date time;
//公告类型
    private String noticeType;


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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

}

