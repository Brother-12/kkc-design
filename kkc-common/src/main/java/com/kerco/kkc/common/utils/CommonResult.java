package com.kerco.kkc.common.utils;

import java.io.Serializable;

/**
 * 统一返回结果
 * @param <T>
 */
public class CommonResult<T> implements Serializable {
    private long timestamp;
    private int code;
    private String message;
    private T data;
    public CommonResult() {}
    public CommonResult(int code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }
    /**
     * 成功
     */
    public static <T> CommonResult<T> success(T data) {
        CommonResult<T> result = new CommonResult<T>();
        result.setCode(200);
        result.setMessage("success");
        result.setData(data);
        result.setTimestamp(System.currentTimeMillis());
        return result;
    }

    /**
     * 成功
     */
    public static <T> CommonResult<T> success() {
        CommonResult<T> result = new CommonResult<T>();
        result.setCode(200);
        result.setMessage("success");
        result.setTimestamp(System.currentTimeMillis());
        return result;
    }


    /**
     * 失败
     */
    public static <T> CommonResult<T> error(int code, String message) {
        return new CommonResult(code, message);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

