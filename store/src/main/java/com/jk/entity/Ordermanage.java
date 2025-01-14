package com.jk.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * (Ordermanage)表实体类
 *
 * @since 2024-12-19 15:49:08
 */
@SuppressWarnings("serial")
public class Ordermanage extends Model<Ordermanage> {
//id
@TableId(type = IdType.AUTO)
    private Integer id;
//商品图片
    private String picture;
//订单编号
    private String orderNum;
//商品名称
    private String goodsname;
//商品类型
    private String type;
//单价
    private Double price;
//数量
    private Integer number;
//总价
    private Double priceAll;
//订单状态
    private String status;
//购买人
    private String people;
//订单创建时间
    private Date createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Double getPriceAll() {
        return priceAll;
    }

    public void setPriceAll(Double priceAll) {
        this.priceAll = priceAll;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


}

