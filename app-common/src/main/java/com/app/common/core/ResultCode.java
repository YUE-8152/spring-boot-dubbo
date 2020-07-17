package com.app.common.core;

/**
 * 响应码枚举，参考HTTP状态码的语义
 */
public enum ResultCode {
    SUCCESS(200, "成功"),

    FAIL(400, "失败"),

    UNAUTHORIZED(401, "未认证（签名错误）"),

    PARAMETERS_IS_REQUIRE(4021, "未认证（签名错误）"),

    PARAMETERS_NOT_VALID(4022, "未认证（签名错误）"),

    NOT_FOUND(404, "接口不存在"),

    INTERNAL_SERVER_ERROR(500, "服务器内部错误");

    private final int code;
    private final String msg;

    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int code() {
        return code;
    }

    public String msg() {
        return msg;
    }
}
