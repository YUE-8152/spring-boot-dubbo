package com.app.common.core;

import com.github.pagehelper.PageInfo;

/**
 * 响应结果生成工具
 */
public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public static Result success() {
        return new Result()
                .setStatus(ResultCode.SUCCESS)
                .setMsg(DEFAULT_SUCCESS_MESSAGE)
                .setIsSuccess(true);
    }

    public static Result success(PageInfo pageInfo) {
        return new Result()
                .setStatus(ResultCode.SUCCESS)
                .setMsg(DEFAULT_SUCCESS_MESSAGE)
                .setIsSuccess(true)
                .setData(new BaseResult(pageInfo.getList(), pageInfo.getTotal()));
    }

    public static <T> Result<T> success(T data) {
        return new Result()
                .setStatus(ResultCode.SUCCESS)
                .setMsg(DEFAULT_SUCCESS_MESSAGE)
                .setData(data)
                .setIsSuccess(true);
    }

    public static <T> Result<T> success(T data, String msg) {
        return new Result()
                .setStatus(ResultCode.SUCCESS)
                .setMsg(msg)
                .setData(data)
                .setIsSuccess(true);
    }

    public static Result fail(String message) {
        return new Result()
                .setStatus(ResultCode.FAIL)
                .setMsg(message)
                .setIsSuccess(false);
    }

    public static class BaseResult {
        private Object list;
        private Long total;

        public Object getList() {
            return list;
        }

        public void setList(Object list) {
            this.list = list;
        }

        public Long getTotal() {
            return total;
        }

        public void setTotal(Long total) {
            this.total = total;
        }

        public BaseResult() {
        }

        public BaseResult(Object list, Long total) {
            this.list = list;
            this.total = total;
        }
    }
}
