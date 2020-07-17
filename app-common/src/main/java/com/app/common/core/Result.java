package com.app.common.core;

import com.alibaba.fastjson.JSON;

/**
 * 统一API响应结果封装
 */
public class Result<T> {
    private int status;
    private String msg;
    private T data;
    private boolean isSuccess;

    public Result setStatus(ResultCode resultCode) {
        this.status = resultCode.code();
        return this;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public Result setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result setData(T data) {
        this.data = data;
        return this;
    }

    public boolean getIsSuccess() {
        return isSuccess;
    }

    public Result setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
