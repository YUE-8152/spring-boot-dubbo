package com.app.common.core;

/**
 * @ProjectName: app-provider
 * @Package: com.app.common.core
 * @ClassName: ServiceException
 * @Author: YX
 * @Description: 自定义全局异常
 * @Date: 2020/7/17 10:57
 * @Version: 1.0
 */
public class ServiceException extends RuntimeException {
    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
