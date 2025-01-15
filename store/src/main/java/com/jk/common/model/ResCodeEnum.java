package com.jk.common.model;


import lombok.extern.slf4j.Slf4j;

/**
 * @author momomian
 * @version 1.0
 * @date 2020/5/22
 * @description 统一返回状态码枚举
 **/
@Slf4j
public enum ResCodeEnum {

    // 操作成功
    SUCCESS(200, "操作成功"),

    // 操作失败
    FAIL(-1,"操作失败"),
    ERROR3000(3000,"未知错误"),
    ERROR3002(3002,"未登录"),
    ERROR3003(3003,"已经登录"),
    ERROR3004(3004,"参数有误"),
    ERROR3005(3005,"登录过期"),
    ERROR3006(3006,"日期格式不正确"),
    ERROR3023(3023,"数据校验异常");

    private Integer code;
    private String  description;


    ResCodeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static String getDescription(int code) {
        for (ResCodeEnum resCode : ResCodeEnum.values()) {
            if (resCode.getCode() == code) {
                return resCode.description;
            }
        }
        log.debug("[ResCodeEnum] No code match .");
        return null;
    }

}
