package com.app.ctrl.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.app.common.core.Result;
import com.app.common.core.ResultCode;
import com.app.ctrl.common.jwt.LoginUser;
import com.app.ctrl.common.jwt.UserContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("-------------请求接口：{}，请求IP：{}，", request.getRequestURI(), getIpAddress(request));
        //验证签名
        String token = request.getHeader("token");
        logger.warn("--------- 获取到前端传递的token  -------------" + token);
        if (StringUtils.isEmpty(token)) {

                    logger.error("签名认证失败，请求接口：{}，请求IP：{}，", request.getRequestURI(), getIpAddress(request));
                    Result result = new Result();
                    result.setStatus(ResultCode.UNAUTHORIZED).setMsg(ResultCode.UNAUTHORIZED.msg()).setIsSuccess(false);
                    responseResult(response, result);
                    return false;
        }
        String userId = validateToken(token);
        if (!StringUtils.isEmpty(userId)) {
            if (handler instanceof HandlerMethod) {
                LoginUser loginUser = savaLoginInfo(userId);
                //存储登录相关信息
                UserContext.setUser(loginUser);
            }
            return true;
        } else {
            logger.error("--------- 解析token后无法获取用户信息  ---------");
            logger.warn("解析token失败，无法获取用户信息，请求接口：{}，请求IP：{}，请求参数：{}",
                    request.getRequestURI(), getIpAddress(request), JSON.toJSONString(request.getParameterMap()));

            Result result = new Result();
            result.setStatus(ResultCode.UNAUTHORIZED).setMsg(ResultCode.UNAUTHORIZED.msg()).setIsSuccess(false);
            responseResult(response, result);
            return false;
        }
    }

    private void responseResult(HttpServletResponse response, Result result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }

    /**
     * 验证生成的Token是否有效
     *
     * @param token
     * @return
     */
    private String validateToken(String token) {
        logger.info("------------ 开始解析token --------------");
        String userId = null;
        return userId;
    }

    private LoginUser savaLoginInfo(String userId) {
        // 3.根据用户Id获取用户信息
        JSONObject user = null;
        LoginUser loginUser = new LoginUser();
//        loginUser.setUserId(userId);
//        loginUser.setMobile(user.getString("mobile"));
//        loginUser.setOpenId(user.getString("openid"));
//        loginUser.setPassword(user.getString("password"));
        logger.info("-------------- 解析token完成   获取的用户信息为 " + loginUser.toString());
        return loginUser;
    }

    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 如果是多级代理，那么取第一个ip为客户端ip
        if (ip != null && ip.indexOf(",") != -1) {
            ip = ip.substring(0, ip.indexOf(",")).trim();
        }

        return ip;
    }
}
