package com.jk.common.model;

import cn.hutool.core.map.MapUtil;

import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Map;

/**
 * @author momomian
 * @version 1.0
 * @date 2020/3/20
 * @description 接口响应结果
 **/
//@ApiModel(value = "响应结果")

public class ApiResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    //@ApiModelProperty(value = "响应码")
    private Integer code = 200;

    // @ApiModelProperty(value = "提示消息")
    private String msg;

    //@ApiModelProperty(value = "请求路径")
    private String path;

    //@ApiModelProperty(value = "响应数据")
    private T data;

    //@ApiModelProperty(value = "其他数据")
    private Map<String, Object> extra;

    //@ApiModelProperty(value = "响应时间 ms")
    private long timestamp;


    public ApiResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ApiResult() {
    }

    public ApiResult<T> code(Integer code) {
        this.code = code;
        return this;
    }

    public ApiResult<T> msg(String msg) {
        this.msg = msg;
        return this;
    }

    public ApiResult<T> path(String path) {
        this.path = path;
        return this;
    }

    public  ApiResult<T> data(T data) {
        this.data = data;
        return this;
    }

    public ApiResult<T> extra(String key, Object object) {
        if (this.extra == null) {
            this.extra = MapUtil.newHashMap();
        }
        extra.put(key, object);
        return this;
    }

    public static <T> ApiResult<T> ok() {
        return new ApiResult<T>().code(ResCodeEnum.SUCCESS.getCode()).msg(ResCodeEnum.SUCCESS.getDescription());
    }

    public static <T> ApiResult<T> ok(T data) {
        return new ApiResult<T>().code(ResCodeEnum.SUCCESS.getCode()).msg(ResCodeEnum.SUCCESS.getDescription()).data(data);
    }


    public static <T> ApiResult<T> fail() {
        return new ApiResult<T>().code(ResCodeEnum.FAIL.getCode()).msg(ResCodeEnum.FAIL.getDescription());
    }

    /**
     * 自定义返回码
     */
    public static <T> ApiResult<T> ok(Integer code, String msg) {
        return new ApiResult<T>().code(code).msg(msg);
    }




    public static <T> ApiResult<T> error(String msg) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
    }

    public static <T> ApiResult<T> error(int code) {
        return error(code, ResCodeEnum.getDescription(code));
    }

    public static <T> ApiResult<T> error(int code, String msg) {
        return new ApiResult<T>().code(code).msg(msg);
    }

    /**
     * 授权过期
     */
    public static <T> ApiResult<T> expired(String msg) {
        return new ApiResult<T>().code(HttpStatus.UNAUTHORIZED.value()).msg(msg);
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getPath() {
        return path;
    }

    public T getData() {
        return data;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
